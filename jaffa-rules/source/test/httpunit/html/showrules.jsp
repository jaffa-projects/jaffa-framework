<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="gnu.trove.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="org.jaffa.rules.aop.interceptors.*"%>
<%@page import="org.jboss.aop.advice.*"%>
<%@page import="org.jboss.aop.*"%>

<html>
<head>
  <title>Show Rules</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body bgcolor="#FFFFFF" leftmargin="20" topmargin="20" marginwidth="20" marginheight="20">
<form action="showrules.jsp" method="post">
Class Name = <input type="text" name="classname" size="100" value="org.jaffa.applications.jaffa.modules.admin.components.usermaintenance.ui.UserMaintenanceComponent">
<input type="submit" value="Go">
</form>
<br>
<%String classname = request.getParameter("classname");
if(classname!=null) {
  Class clazz = null;
  try {
     clazz = Class.forName(classname);
  } catch (NoClassDefFoundError e) {
     out.write("<h1>Class Not Found - "+classname+"</h1>");
  }
 
  if(clazz!=null) {
    out.write("<h3>Inteceptors for " + clazz + "</h3>\n");
    if(Advised.class.isAssignableFrom(clazz)) {
      out.write("Class is prepared for Aspects\n<br>\n");
      ClassAdvisor advisor = AspectManager.instance().getAdvisor(clazz);
      TLongObjectHashMap m = advisor.getMethodInterceptors();
      TLongObjectIterator iterator = m.iterator();
      out.write("Found <b>" + m.size() + "</b> potential intercepted methods\n<br>\n");
      out.write("<ul>\n");
      for (int i=0; i<m.size(); i++) {
        iterator.advance();
        MethodInfo mi = (MethodInfo) iterator.value();
        if(mi.interceptors!=null && mi.interceptors.length > 0) {
          out.write("<li> Method <b>" + mi.advisedMethod + "</b> (" + i +") has " + mi.interceptors.length + " interceptors\n<br>\n");
          out.write("<ul>\n");
          for (int j=0; j<mi.interceptors.length; j++) {
            if( mi.interceptors[j] instanceof PropertyRuleInterceptor) {
              PropertyRuleInterceptor pr = ((PropertyRuleInterceptor)mi.interceptors[j]);
              out.write("<li>" + j +") Property Rule <b>" + pr.toString() + "</b> for " + pr.getTargetPropertyName() + ". Params = " + pr.getParameters()+"\n<br>\n");
            } else if( mi.interceptors[j] instanceof ObjectRuleInterceptor) {
               ObjectRuleInterceptor pr = ((ObjectRuleInterceptor)mi.interceptors[j]);
               out.write("<li>" + j +") Object Rule <b>" + pr.toString() + "</b>. Params = " + pr.getParameters()+"\n<br>\n");
            } else {
               out.write("<li>" + j +") Interceptor <b>" + mi.interceptors[j] + "</b> [" + ((Interceptor)mi.interceptors[j]).getName() + "]\n<br>\n");
            }//for
            out.write("</ul>\n");
          }
        }
      }//for
      out.write("</ul>\n");
           
      Interceptor[][] int2d = advisor.getConstructorInterceptors();
      if(int2d!=null) {
        out.write("Found <b>" + int2d.length + "</b> potential intercepted constructors\n<br>\n");
        out.write("<ul>\n");
        for (int i=0; i<int2d.length; i++) {
          Interceptor[] int1d = int2d[i];
          out.write("<li> Construtor <b>" + advisor.getConstructors()[i] + "</b> has "+int1d.length+" rules\n<br>\n");
          out.write("<ul>\n");
          for (int j=0; j<int1d.length; j++) {
            Interceptor in = int1d[j];
            if( int1d[j] instanceof PropertyRuleInterceptor) {
              PropertyRuleInterceptor pr = ((PropertyRuleInterceptor)int1d[j]);
              out.write("<li>" + i + "," + j +") Property Rule <b>" + pr.toString() + "</b> for " + pr.getTargetPropertyName() + ". Params = " + pr.getParameters()+"\n<br>\n");
            } else if( int1d[j] instanceof ObjectRuleInterceptor) {
              ObjectRuleInterceptor pr = ((ObjectRuleInterceptor)int1d[j]);
              out.write("<li>" + i + "," + j +") Object Rule <b>" + pr.toString() + "</b>. Params = " + pr.getParameters()+"\n<br>\n");
            } else {
              out.write("<li>" + i + "," + j +") Interceptor <b>" + int1d[j] + "</b> [" + int1d[j].getName() + "]\n<br>\n");
            }
          }//for
          out.write("</ul>\n");
        }//for
        out.write("</ul>\n");
      }
    } else
      out.write("Class <b>not</b> prepared for Aspects\n<br>\n");
  }
}
%>
</body>
</html>
