<%-- ----------------------------------------------------------------
  -- Include stuff needed for a typical page using JaffaRIA        --
  --                                                               --
  -- The JAFFA Project can be found at http://jaffa.sourceforge.net--
  -- and is available under the Lesser GNU Public License          --
  -- ------------------------------------------------------------- --%>
<%@ page import = "java.util.*"%>
<%@ page import = "org.jaffa.util.MessageHelper"%>
<%@ page import = "org.jaffa.session.*"%>
<%@ page import = "org.apache.log4j.Logger"%>
<%@ page import="org.jaffa.presentation.portlet.session.LocaleContext" %>

<%@ taglib prefix="jwr" uri="/WEB-INF/jawr-el.tld"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%!
private static Logger log = Logger.getLogger("extjs");
public static final String[] SUPPORTED_LOCALES = new String[] {"af","ar","bg","ca","cs","da","de","el_GR","en_GB","en_US","en","es","fa","fr_CA","fr","gr","he","hr","hu","id","it","ja","ko","lt","lv","mk","nl","no_NB","no_NN","pl","pt_BR","pt","ro","ru","sk","sl","sr_RS","sr","sv_SE","th","tr","ukr","vn","zh_CN","zh_TW"};
public static final String SYMBOL_RULE = "jaffa.datatypes.defaultCurrencySymbol";
public static final String DEFAULT_TIME_FORMAT = "jaffa.datatypes.defaultTimeFormat";

%><%-- ----------------------------------------------------------------
    -- Include Standard handler for unmanaged script errors          --
    -- ------------------------------------------------------------- --%><%
  if(log.isInfoEnabled()&&false) {%>
  <script type="text/javascript">
  window.onerror = function(msg,url,l) {
    alert("There was an error on this page.\n\n"
          +"Error: " + msg + "\n"
          +"URL: " + url + "\n"
          +"Line: " + l + "\n\n"
          +"Click OK to continue.\n\n");
    return true;
  };
  </script><%}%>

  <%-- -------------------------------------------------------------------------------------------------
    -- Add a mask, while the page is being loaded.                                                    --
    -- The mask will be removed after the Viewport is rendered.                                       --
    -- NOTE: Keep the IDs and className in sync with the values in js/extjs/jaffa/state/PageLoader.js --
    -- ---------------------------------------------------------------------------------------------- --%>
  <script type="text/javascript">
    document.write("<div id='jaffa-page-loading-mask'></div>");
    document.write("<div id='jaffa-page-loading-container'><div class='jaffa-page-loading-indicator'><%= MessageHelper.findMessage("label.JaffaRIA.pageLoad", null) %></div></div>");
  </script>

<%-- ----------------------------------------------------------------
  -- Include ExtJS core stuff                                      --
  -- ------------------------------------------------------------- --%>
  <!-- ExtJS Core -->
  <jwr:script src="/bundles/extjs.js" useRandomParam='<%=log.isDebugEnabled()?"false":"true"%>'/>
<%--
   Must override this as by default it tries to access the ExtJS web site
--%>
  <script type="text/javascript">
    Ext.BLANK_IMAGE_URL = "<%=request.getContextPath()%>/js/extjs/resources/images/default/s.gif";
  </script>
<%
String currentLocale = null;
if(LocaleContext.getLocale()!=null) {
    String locale = LocaleContext.getLocale().toString();
    // This allows country locales (like en_US) to default to just language
    // local (like 'en') if there is not a country specific one
    for (String l : SUPPORTED_LOCALES) {
        if (locale.startsWith(l)) {
            currentLocale = l;
            break;
        }
    }
}
if(currentLocale != null){
        log.debug("Request ExtJS Language Resources for " + currentLocale);
        String langFilePath="/js/extjs/locale/ext-lang-"+ currentLocale +".js";
%>
        <!-- Locale Resources for <%= currentLocale %>] -->
        <jwr:script src="<%= langFilePath %>"/>
<%
}
// Figure out the currency symbol to use.
String symbol = (String)ContextManagerFactory.instance().getProperty(SYMBOL_RULE);
if(symbol == null) {
  try {
    Currency c = Currency.getInstance(Locale.getDefault());
    if(c!=null) symbol=c.getSymbol();
  } catch(IllegalArgumentException e) {
    log.warn("Can't detect default currency symbol for default locale " + Locale.getDefault());
  }
}
if(symbol==null) symbol="";

String defaultTimeFormat = (String)ContextManagerFactory.instance().getProperty(DEFAULT_TIME_FORMAT);
%>
  <script type="text/javascript">
<%--
   Hack in case Firebug is not running, so we don't need to remove all the console calls code
--%><%if(!log.isDebugEnabled()) {
    // Even if the console is enabled, if we are not debugging, insert a dummy function soit does not log!
%>  if(console) console.debug=function(){};<%}%>
  if(!console) var console={debug:function(){},info:function(){},warn:function(){},error:function(){},time:function(){},timeEnd:function(){}};

<%--
   Override many aspects of the Ext.util.Format
   - Look at Java's default layout for DateOnly / DateTime and convert them to JavaScript Equivilents for the Locale
   - Set a currency symbol based on an application rule or the default one for the locale of the server (not the user!)
   - Add a default date, dateTime and money formatter/renderer to support the above settings
--%>
  if(Ext.util.Format){
    Ext.util.Format.defaultDateLayout = '<%=org.jaffa.metadata.DateOnlyFieldMetaData.getDateOnlyFormat()
                                          .replace("yyyy","Y")
                                          .replace("yy","y")
                                          .replace("MM","m")
                                          .replace("dd","d")%>';
    Ext.util.Format.defaultDateTimeLayout = '<%=org.jaffa.metadata.DateTimeFieldMetaData.getDateTimeFormat()
                                          .replace("yyyy","Y")
                                          .replace("yy","y")
                                          .replace("MM","m")
                                          .replace("dd","d")
                                          .replace("HH","H")
                                          .replace("mm","i")
                                          .replace("ss","s")%>';
    Ext.util.Format.defaultCurrencySymbol = '<%=symbol%>';
    Ext.util.Format.defaultHoursFormat = '<%=(defaultTimeFormat != null)?defaultTimeFormat : "hh:mm" %>';
  }
  </script>