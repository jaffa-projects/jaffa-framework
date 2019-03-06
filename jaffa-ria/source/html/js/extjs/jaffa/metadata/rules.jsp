<%--
rules.jsp

Generates a javascript 'Rules' object that has a get(token) method for getting a rule
It is assumed that all rules for this referencing page are already known and stored
in a text file resource.
If a rule is not know it is automatically added to the serverside list, so next time
this JSP is accessed for that page, the rule will be available.

@param ref Name of the page to get the rules for
@param token Request for a token by a given page which should be cached for later use

Should be included in the main JSP that loads all the java script using the following...

<script type="text/javascript" src="js/extjs/jaffa/rules.jsp?ref=<%=request.getRequestURI().substring(request.getContextPath().length()+1)%>"></script>

--%><%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.net.URL,java.util.*,javax.servlet.http.*" %>
<%@ page import = "org.jaffa.util.URLHelper,org.jaffa.util.MessageHelper,org.jaffa.security.SecurityTag,org.jaffa.session.ContextManagerFactory,org.jaffa.util.StringHelper" %>
<%@ page import = "org.apache.log4j.Logger" %>
<%!
    private static final Logger log = Logger.getLogger("js.extjs.jaffa.rules");

    // Get the full path of where the labels are for this refering page
    String getRuleFileName(String ref, HttpServletRequest request) {
        //String root = (String) Config.getProperty(Config.PROP_DEFAULT_GRID_SETTINGS_URI, DEFAULT_SETTINGS_LOCATION);
        String root = request.getSession().getServletContext().getRealPath("/");
        //String s = root+"/rules/"+ref+".rules";
        String s = root+ref.replace("/", File.separator)+".rules";
        return s;
    }

    // Read all the tokens in from the file
    TreeSet<String> readRuleTokens(String page, HttpServletRequest request) {
        TreeSet<String> out = new TreeSet<String>();
        BufferedReader br = null;
        try {
            File f = new File(getRuleFileName(page, request));
            if(f.exists()) {
                br = new BufferedReader(new FileReader(f));
                String line=null;
                int count=0;
                while ( (line = br.readLine()) != null) {
                    out.add(line);
                }
            }
        } catch (IOException ex) {
            log.error("Failed read token file " + request.getRequestURI(),ex);
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                log.error("Failed read token file " + request.getRequestURI(),ex);
            }
        }
        return out;
    }

    // Write a new token to the end of the file
    synchronized void addRuleToken(String page, HttpServletRequest request, String token) {
        BufferedWriter w = null;
        try {
            TreeSet<String> t = readRuleTokens(page,request);
            t.add(token);
            File f = new File(getRuleFileName(page, request));
            if(!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            // Write out the script
            w = new BufferedWriter( new FileWriter(f, false) );
            for (String tk : t) {
                w.write( tk );
                w.newLine();
            }
            log.debug("Saved Token " + token);
        } catch (IOException ex) {
            log.error("Failed to add token " + token + " to " + request.getRequestURI(),ex);
        } finally {
            try {
                if (w != null) w.close();
            } catch (IOException ex) {
              log.error("Failed to add token " + token + " to " + request.getRequestURI(),ex);
            }
        }
    }
%>
<%
String ref=request.getParameter("ref");
String identifier=request.getParameter("identifier");

//remove everything after the last / (this should be the jsp name
String rulePath = ref.substring(0,ref.lastIndexOf("/"));
//lower case the file path and replace / with .
rulePath = rulePath.toLowerCase().replace("/",".");

String identifierText = (identifier==null||identifier.equals(""))?"":("." + identifier);

String hidePanelListRule = rulePath+identifierText+".hidePanelList";
String hideColumnListRule = rulePath+identifierText+".hideColumnList";
String hideFieldListRule = rulePath+identifierText+".hideFieldList";
String readOnlyFieldListRule = rulePath+identifierText+".readOnlyFieldList";
String hideButtonListRule = rulePath+identifierText+".hideButtonList";

String hidePanelList = (String)ContextManagerFactory.instance().getProperty(hidePanelListRule);
String hideColumnList = (String)ContextManagerFactory.instance().getProperty(hideColumnListRule);
String hideFieldList = (String)ContextManagerFactory.instance().getProperty(hideFieldListRule);
String readOnlyFieldList = (String)ContextManagerFactory.instance().getProperty(readOnlyFieldListRule);
String hideButtonList = (String)ContextManagerFactory.instance().getProperty(hideButtonListRule);


String allowLoadAll = (String)ContextManagerFactory.instance().getProperty("jaffa.widgets.grid.allowLoadAll");

hidePanelList=(hidePanelList==null || hidePanelList.equals("null"))?"":hidePanelList.trim();
hideColumnList=(hideColumnList==null || hideColumnList.equals("null"))?"":hideColumnList.trim();
hideFieldList=(hideFieldList==null || hideFieldList.equals("null"))?"":hideFieldList.trim();
hideButtonList=(hideButtonList==null || hideButtonList.equals("null"))?"":hideButtonList.trim();
readOnlyFieldList=(readOnlyFieldList==null || readOnlyFieldList.equals("null"))?"":readOnlyFieldList.trim();

String token=(String)request.getParameter("token");
if(ref!=null&&token==null) {
    // Read the tokens for this page and create and populate the Labels object
    TreeSet<String> t = readRuleTokens(ref, request);
%>Rules = {
    url: '<%=request.getContextPath()+"/js/extjs/jaffa/metadata/rules.jsp"%>',
    page: '<%=StringHelper.escapeHtml(ref)%>',
    data : new Ext.util.MixedCollection(),
    conn: new Ext.data.Connection({
      url: '<%=request.getContextPath()+"/js/extjs/jaffa/metadata/rules.jsp"%>'
    }),
    get: function(x) {
      if(!Rules.data.containsKey(x)) {
        // make a request for x
        var opt = {
          params: {
            ref: this.page,
            token: x,
            tokenClass: 'ruleToken'
          }
        };
        this.conn.request(opt);

        // store into the token array
        Rules.data.add(x,'?'+x+'?');
        console.debug('Written referenced for new token ',x,' for ',Rules.page);
      }
      return Rules.data.get(x);
    }
};
<%
    // iterate through the rule list and generate the rules
    for(String tk : t) {

    String l = (String)ContextManagerFactory.instance().getProperty(tk);

%>
Rules.data.add("<%=tk%>","<%=StringHelper.escapeJavascript(l)%>");<%
    }
} else if(ref!=null && token!=null && "ruleToken".equals((String) request.getParameter("tokenClass"))) {
    // Add a new token for this page to the server-side file
    addRuleToken(ref, request, token);
}
%>

Rules.data.add("Jaffa.metadata.HidePanelList","<%= hidePanelList %>");
Rules.data.add("Jaffa.metadata.HideColumnList","<%= hideColumnList %>");
Rules.data.add("Jaffa.metadata.HideFieldList","<%= hideFieldList %>");
Rules.data.add("Jaffa.metadata.HideButtonList","<%= hideButtonList %>");
Rules.data.add("Jaffa.metadata.ReadOnlyFieldList","<%= readOnlyFieldList %>");
Rules.data.add("Jaffa.widgets.grid.allowLoadAll",<%= ("true".equals(allowLoadAll))? true : false %>);

