<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page import = "org.apache.log4j.Logger" %>
<%@ page import = "java.util.*" %>
<%@ page import = "org.jaffa.components.lookup.LookupComponent2" %>
<%@ page import = "org.jaffa.util.StringHelper" %>
<%@ page import = "org.jaffa.util.MessageHelper" %>
<%! private static Logger log = Logger.getLogger("jaffa.jsp.lookup"); %>
<html>
<head>
  <script type="text/javascript" src="jaffa/js/panels/header.js"></script>
</head>
<body>
<script language="JavaScript">
function updateField(txtarea,replace,prefix,suffix,newText) {
  if(replace=='insert') {
    replaceSelection(txtarea,prefix+newText+suffix);
  } else if(replace=='append') {
    if(txtarea.value=="")
      txtarea.value+=newText+suffix;
    else
      txtarea.value+=prefix+newText+suffix;
  } else if(replace=='true') {
    txtarea.value=prefix+newText+suffix;
  }
}
window.close();
function replaceSelection(txtarea, newText) {
  // IE
  if(self.opener.document.selection  && browser.isIE) {
    txtarea.focus();
    self.opener.document.selection.createRange().text = newText;
  // Mozilla
  } else if(txtarea.selectionStart || txtarea.selectionStart == '0') {
    var startPos = txtarea.selectionStart;
    var endPos = txtarea.selectionEnd;
    var pre=(txtarea.value).substring(0,startPos);
    var suf=(txtarea.value).substring(endPos);
    txtarea.value=pre+newText+suf;
  }
  // reposition cursor if possible
  if (txtarea.createTextRange)
    txtarea.caretPos = self.opener.document.selection.createRange().duplicate();
}
   
<%
    Map lookupMap = (Map) request.getAttribute(LookupComponent2.LOOKUP_ATTRIBUTE);
    Map returnStyle = (Map) request.getAttribute(LookupComponent2.LOOKUP_STYLE_ATTRIBUTE);
    if (lookupMap != null) {
        for (Iterator i = lookupMap.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            String field = (String) entry.getKey();
            String value = (String) entry.getValue();
            
            // Get return style settings
            Map props = null;
            if(returnStyle!=null)
                props = (Map)returnStyle.get(field);
            String styleReplace=null,stylePrefix=null,styleSuffix = null;
            if(props!=null) {
                styleReplace=(String)props.get("replace");
                stylePrefix=(String)props.get("prefix");
                styleSuffix=(String)props.get("suffix");
            }
            // Set defaults if needed
            if(styleReplace==null)
                styleReplace="true";
            else 
                styleReplace=styleReplace.toLowerCase();
            if(stylePrefix==null)
            	stylePrefix="";
            else            	
                stylePrefix=MessageHelper.replaceTokens(pageContext, stylePrefix);
            if(styleSuffix==null)
                styleSuffix="";
            else    
                styleSuffix=MessageHelper.replaceTokens(pageContext, styleSuffix);
 
            // escape any special characters                    
            value = StringHelper.replace(StringHelper.replace(StringHelper.replace(value, "\\","\\\\"), "'" , "\\'") , "\n" , "\\n");
            stylePrefix = StringHelper.replace(StringHelper.replace(stylePrefix , "'" , "\\'") , "\n" , "\\n");
            styleSuffix = StringHelper.replace(StringHelper.replace(styleSuffix , "'" , "\\'") , "\n" , "\\n");
            
            StringBuffer javascript = new StringBuffer();

            // Handle simple replacement of values
            /* Set the value on the client-window
            * was: self.opener.document.getElementById('{field}').value='{value}';
            * now: updateField(self.opener.document.getElementById('{field}'),{replace},{prefix},{suffix},{value})
            */
            //javascript.append("\nself.opener.document.getElementById('").append(field).append("').value = '").append(value).append("';");
            javascript.append("\nupdateField(self.opener.document.getElementById('")
                      .append(field)
                      .append("'),'")
                      .append(styleReplace)
                      .append("','")
                      .append(stylePrefix)
                      .append("','")
                      .append(styleSuffix)
                      .append("','")
                      .append(value)
                      .append("');");

            // See if the field was a checkbox, and update the visual display
            if (value != null && ("true".equals(value) || "false".equals(value))) {
                /* Set the appropriate value in case the target is a checkbox - html or image
                @todo: This will not work correctly if the user has specified custom-images for the checkbox, since its not possible to obtain those values
                var cbElement = self.opener.document.getElementById('{field}_user');
                if (cbElement != null && cbElement.type == 'checkbox')
                  cbElement.checked='{value}';
                var imageElement = self.opener.document.getElementById('{field}_img');
                if (imageElement != null)
                  imageElement.src='jaffa/imgs/checkbox/check_box.gif' | imageElement.src='jaffa/imgs/checkbox/box.gif';
                */
                javascript.append("\nvar cbElement = self.opener.document.getElementById('")
                .append(field)
                .append("_user');")
                .append("\nif (cbElement != null && cbElement.type == 'checkbox') cbElement.checked=")
                .append(value)
                .append(";");
                javascript.append("\nvar imageElement = self.opener.document.getElementById('")
                .append(field)
                .append("_img');")
                .append("\nif (imageElement != null) imageElement.src='")
                .append("true".equals(value) ? "jaffa/imgs/checkbox/check_box.gif" : "jaffa/imgs/checkbox/box.gif")
                .append("';");
            }
            out.write(javascript.toString());
            if (log.isDebugEnabled())
                log.debug(javascript.toString());
        }
    }
    request.removeAttribute(LookupComponent2.LOOKUP_ATTRIBUTE);
    request.removeAttribute(LookupComponent2.LOOKUP_STYLE_ATTRIBUTE);
%>
</script>
</body>
</html>