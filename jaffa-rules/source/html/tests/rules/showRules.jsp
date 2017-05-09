<%@ page import='gnu.trove.TLongObjectHashMap' %>
<%@ page import='gnu.trove.TLongObjectIterator' %>
<%@ page import='java.beans.BeanInfo' %>
<%@ page import='java.beans.Introspector' %>
<%@ page import='java.beans.PropertyDescriptor' %>
<%@ page import='java.util.Arrays' %>
<%@ page import='java.util.Collection' %>
<%@ page import='java.util.Iterator' %>
<%@ page import='java.util.LinkedList' %>
<%@ page import='java.util.Map' %>
<%@ page import='javax.servlet.jsp.JspWriter' %>
<%@ page import='org.jaffa.rules.IObjectRuleIntrospector' %>
<%@ page import='org.jaffa.rules.IPropertyRuleIntrospector' %>
<%@ page import='org.jaffa.rules.RulesEngineFactory' %>
<%@ page import='org.jaffa.rules.jbossaop.interceptors.AbstractRuleInterceptor' %>
<%@ page import='org.jaffa.rules.meta.MetaDataRepository' %>
<%@ page import='org.jaffa.rules.rulemeta.RuleRepository' %>
<%@ page import='org.jaffa.util.StringHelper' %>
<%@ page import='org.jboss.aop.Advised' %>
<%@ page import='org.jboss.aop.AspectManager' %>
<%@ page import='org.jboss.aop.ClassAdvisor' %>
<%@ page import='org.jboss.aop.ConstructionInfo' %>
<%@ page import='org.jboss.aop.MethodInfo' %>
<%@ page import='org.jboss.aop.advice.Interceptor' %>

<%!

/** Convert the input to HTML compatible String. */
private String toHtml(Object obj) {
  return obj == null ? "" : StringHelper.convertToHTML(obj.toString());
}

/** Find the rules for the input Class. */
private void showRules(String className, JspWriter out) throws Exception {
  out.write('\n');
  out.write("    <h3>Checking " + className + "</h3>\n");
  
  // Load the Class
  Class clazz = null;
  try {
    clazz = Class.forName(className);
    showLinkToSuperClass(clazz, out);
  } catch (Exception e) {
    out.write("    <h1>Class Not Found - " + className + "</h1>");
  }
  
  // Show Interceptors bound to the Class
  if (clazz != null) {
    if (Advised.class.isAssignableFrom(clazz)) {
      out.write("    <h3>Class is prepared for Aspects</h3>\n");
      ClassAdvisor advisor = AspectManager.instance().getAdvisor(clazz);
      
      // Show Interceptors bound to Methods of the Class
      showMethodInterceptors(advisor, out);
      
      // Show Interceptors bound to Constructors of the Class
      showConstructorInterceptors(advisor, out);
    } else {
      out.write("    <h3>Class <b>not</b> prepared for Aspects\n</h3>\n");
    }
  }
  
  // Show Object attributes and all the Property attributes
  showObject(className, out);
  showProperties(className, clazz, out);
}

/** Show links to the super-class and all implemented interfaces. */
private void showLinkToSuperClass(Class clazz, JspWriter out) throws Exception {
  if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
    String className = clazz.getSuperclass().getName();
    out.write('\n');
    out.write("    <h3>Extends <a target='_blank' href='showRules.jsp?className=" + className + "'>" + className + "</a></h3>\n");
  }
}

/** Show Interceptors bound to Methods of the Class. */
private void showMethodInterceptors(ClassAdvisor advisor, JspWriter out) throws Exception {
  TLongObjectHashMap methodInfos = advisor.getMethodInterceptors();
  if (methodInfos != null) {
    TLongObjectIterator methodInfoIterator = methodInfos.iterator();
    StringBuilder buf = new StringBuilder();
    int count = 0;
    for (int i = 0; i < methodInfos.size(); i++) {
      methodInfoIterator.advance();
      MethodInfo methodInfo = (MethodInfo) methodInfoIterator.value();
      if (methodInfo.getAdvisedMethod().getDeclaringClass() == advisor.getClazz()) {
        Interceptor[] interceptors = methodInfo.getInterceptors();
        if (interceptors != null && interceptors.length > 0) {
          ++count;
          buf.append("      <li>" + count + ") <b>" + methodInfo.getAdvisedMethod() + "</b> has " + interceptors.length + " interceptor" + (interceptors.length > 1 ? "s" : "") + '\n');
          buf.append("      <ul>\n");
          for (int j = 0; j < interceptors.length; j++) {
            Interceptor interceptor = interceptors[j];
            if (interceptor instanceof AbstractRuleInterceptor) {
              AbstractRuleInterceptor ruleInterceptor = ((AbstractRuleInterceptor) interceptor);
              buf.append("        <li>" + (j+1) + ") Rule <span class='rulename'>" + ruleInterceptor.getName() + "</span>" + (ruleInterceptor.getTargetClassName() != null ? ". targetClassName=" + ruleInterceptor.getTargetClassName() : "") + '\n');
            } else {
              buf.append("        <li>" + (j+1) + ") Interceptor <b>" + interceptor.getName() + "</b> [" + interceptor + "]\n");
            }
          }
          buf.append("      </ul>\n");
        }
      }
    }
    out.write('\n');
    out.write("    Found <b>" + count + "</b> intercepted method" + (count > 1 ? "s" : "") + '\n');
    out.write("    <ul>\n");
    out.write(buf.toString());
    out.write("    </ul>\n");
  }
}

/** Show Interceptors bound to Constructors of the Class. */
private void showConstructorInterceptors(ClassAdvisor advisor, JspWriter out) throws Exception {
  ConstructionInfo[] constructionInfos = advisor.getConstructionInfos();
  if (constructionInfos != null) {
    StringBuilder buf = new StringBuilder();
    int count = 0;
    for (int i = 0; i < constructionInfos.length; i++) {
      ConstructionInfo constructionInfo = constructionInfos[i];
      if (constructionInfo.getConstructor().getDeclaringClass() == advisor.getClazz()) {
        Interceptor[] interceptors = constructionInfo.getInterceptors();
        if (interceptors != null && interceptors.length > 0) {
          ++count;
          buf.append("      <li>" + count + ") <b>" + constructionInfo.getConstructor() + "</b> has " + interceptors.length + " interceptor" + (interceptors.length > 1 ? "s" : "") + '\n');
          buf.append("      <ul>\n");
          for (int j = 0; j < interceptors.length; j++) {
            Interceptor interceptor = interceptors[j];
            if (interceptor instanceof AbstractRuleInterceptor) {
              AbstractRuleInterceptor ruleInterceptor = ((AbstractRuleInterceptor) interceptor);
              buf.append("        <li>" + (j+1) + ") Rule <span class='rulename'>" + ruleInterceptor.getName() + "</span>" + (ruleInterceptor.getTargetClassName() != null ? ". targetClassName=" + ruleInterceptor.getTargetClassName() : "") + '\n');
            } else {
              buf.append("        <li>" + (j+1) + ") Interceptor <b>" + interceptor.getName() + "</b> [" + interceptor + "]\n");
            }
          }
          buf.append("      </ul>\n");
        }
      }
    }
    out.write('\n');
    out.write("    Found <b>" + count + "</b> intercepted constructor" + (count > 1 ? "s" : "") + '\n');
    out.write("    <ul>\n");
    out.write(buf.toString());
    out.write("    </ul>\n");
  }
}

/** Show Object. */
private void showObject(String className, JspWriter out) throws Exception {
  out.write('\n');
  out.write("    <h3>Introspect Bean</h3>\n");
  out.write("    <table>\n");
  out.write("      <thead>\n");
  out.write("        <tr>\n");
  out.write("          <td>Label</td>\n");
  out.write("          <td>PrimaryKey</td>\n");
  out.write("        </tr>\n");
  out.write("      </thead>\n");
  out.write("      <tbody>\n");
  IObjectRuleIntrospector ori = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(className, null);
  if (ori != null) {
    out.write("        <tr class='row0'>\n");
    out.write("          <td>" + toHtml(ori.getLabel()) + "</td>\n");
    out.write("          <td>" + toHtml(ori.getPrimaryKey()) + "</td>\n");
    out.write("        </tr>\n");
  }
  out.write("      </tbody>\n");
  out.write("    </table>\n");
}

/** Show Properties. */
private void showProperties(String className, Class clazz, JspWriter out) throws Exception {
  out.write('\n');
  out.write("    <h3>Introspect Bean Properties</h3>\n");
  out.write("    <table>\n");
  out.write("      <thead>\n");
  out.write("        <tr>\n");
  out.write("          <td>Name</td>\n");
  out.write("          <td>PropertyType</td>\n");
  out.write("          <td>Hidden</td>\n");
  out.write("          <td>ReadOnly</td>\n");
  out.write("          <td>Mandatory</td>\n");
  out.write("          <td>ForeignKeyInfo</td>\n");
  out.write("          <td>MinLength</td>\n");
  out.write("          <td>MaxLength</td>\n");
  out.write("          <td>MaxFracLength</td>\n");
  out.write("          <td>MinValue</td>\n");
  out.write("          <td>MaxValue</td>\n");
  out.write("          <td>CaseType</td>\n");
  out.write("          <td>Label</td>\n");
  out.write("          <td>Layout</td>\n");
  out.write("          <td>Pattern</td>\n");
  out.write("          <td>ClientRule</td>\n");
  out.write("          <td>CommentStyle</td>\n");
  out.write("        </tr>\n");
  out.write("      </thead>\n");
  out.write("      <tbody>\n");
  String[] propertyNames = determinePropertyNames(className, clazz);
  if (propertyNames != null && propertyNames.length > 0) {
    for (int i = 0; i < propertyNames.length; i++) {
      String propertyName = propertyNames[i];
      IPropertyRuleIntrospector pri = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(className, propertyName, null);
      out.write("        <tr class='row" + i % 2 + "'>\n");
      out.write("          <td>" + toHtml(propertyName) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getPropertyType()) + "</td>\n");
      out.write("          <td class='" + pri.isHidden() + "'>" + pri.isHidden() + "</td>\n");
      out.write("          <td class='" + pri.isReadOnly() + "'>" + pri.isReadOnly() + "</td>\n");
      out.write("          <td class='" + pri.isMandatory() + "'>" + pri.isMandatory() + "</td>\n");
      out.write("          <td>" + toHtml(pri.getForeignKeyInfo()) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getMinLength()) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getMaxLength()) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getMaxFracLength()) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getMinValue()) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getMaxValue()) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getCaseType()) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getLabel()) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getLayout()) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getPattern()) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getClientRule()) + "</td>\n");
      out.write("          <td>" + toHtml(pri.getCommentStyle()) + "</td>\n");
      out.write("        </tr>\n");
    }
  }
  out.write("      </tbody>\n");
  out.write("    </table>\n");
}

/** Determine the properties for the input Class. */
private String[] determinePropertyNames(String className, Class clazz) throws Exception {
  Collection checkedPropertyNames = new LinkedList();
  if (clazz == null) {
    // A virtual class is passed. Find properties for which rules have been declared
    Collection ruleNames = RuleRepository.instance().getRuleNames();
    for (Iterator ruleItr = ruleNames.iterator(); ruleItr.hasNext(); ) {
      String ruleName = (String) ruleItr.next();
      Map propertyRuleMap = MetaDataRepository.instance().getPropertyRuleMap(className, ruleName);
      if (propertyRuleMap != null) {
        for (Iterator propertyItr = propertyRuleMap.keySet().iterator(); propertyItr.hasNext(); ) {
          String propertyName = (String) propertyItr.next();
          if (propertyName != null && !checkedPropertyNames.contains(propertyName))
            checkedPropertyNames.add(propertyName);
        }
      }
    }
  } else {
    // Use the Bean introspector to find the properties
    BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
    if (propertyDescriptors != null) {
      for (int i = 0; i < propertyDescriptors.length; i++)
        checkedPropertyNames.add(propertyDescriptors[i].getName());
    }
  }
  return checkedPropertyNames != null && checkedPropertyNames.size() > 0 ? (String[]) checkedPropertyNames.toArray(new String[checkedPropertyNames.size()]) : null;
}
%>

<html>
  <head>
    <title>Show Rules</title>
    <style>
      body {
      color: black;
      font-size: 10pt;
      font-family: Tahoma;
      }
      .rulename {
      font-weight: bold;
      color: blue;
      }
      .fieldname {
      font-weight: bold;
      color: green;
      }
      table        {        
      font-size: 8pt;
      color: black;
      font-family: Tahoma;
      }
      thead td {
      font-size: 8pt;
      color: black;
      background: #CBE0F5;
      padding: 3px;
      text-align: left;
      border-right: 1px groove #B1CAEA;
      }
      tr {                
      font-size: 8pt;
      color: black;
      background: #f1f1f1;
      padding: 3px;
      }
      tr.row0 {                
      background: #f1f1f1;
      }
      tr.row1 {                
      background: #e2e2e2;
      }
      td {                
      font-size: 8pt;
      border-bottom: 1px solid #C8C8C8;
      border-left: 1px solid #C8C8C8;
      padding: 2px;
      color: black;
      }
      .true {
      font-weight: bold;
      color:red;
      }
      .false {
      color:gray;
      }
    </style>
  </head>
  
  <body>
    <h1>Show Rules</h1>
    <hr>
    <% 
    String className = request.getParameter("className");
    className = className != null ? className.trim() : "";
    %>
    <form action='showRules.jsp' method='post'>
      Class Name:
      <select name="className">
        <%
        out.write('\n');
        Object[] classNames = MetaDataRepository.instance().getRepository().keySet().toArray();
        Arrays.sort(classNames);
        for (int i = 0; i < classNames.length; i++) {
          String c = (String) classNames[i];
          out.write("        <option value='" + c + '\'' + (c.equals(className) ? " SELECTED" : "") + '>' + c + "</option>\n");
        }
        %>
      </select>
      <input type='submit' value='Go'/>
    </form>
    <% if (className != null && className.length() > 0) showRules(className, out); %>
  </body>
</html>
