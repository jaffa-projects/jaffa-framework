<%-- ---------------------------------------------------------------------------------------- --
  -- JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
  --
  -- This library is free software; you can redistribute it and/or modify it under the terms
  -- of the GNU Lesser General Public License (version 2.1 any later).
  -- 
  -- See http://jaffa.sourceforge.net/site/legal.html for more details.
  -- ---------------------------------------------------------------------------------------- --%>
<%@ page import = "org.apache.log4j.Logger" %>
<%@ page import = "org.jaffa.presentation.portlet.session.UserSession" %>
<%@ page import = "org.jaffa.presentation.portlet.widgets.taglib.ImageTag" %>
<%@ page import = "java.io.BufferedOutputStream" %>
<%! private static Logger log = Logger.getLogger("jaffa.jsp.getImage"); %>
<%
        // get the idPrefix from the request-stream
        String key = request.getParameter(ImageTag.SESSION_ID_PREFIX_NAME);
        if(log.isDebugEnabled())
            log.debug("Image Server... idPrefix = " + key);
        
        // get the image-contents & mimeType from the UserSession
        UserSession us = UserSession.getUserSession(request);
        byte[] imageContents = us.getImageContents(key);
        String mimeType = us.getImageMimeType(key);
        
        // Delete the image from the UserSession.. since it wud not be used anymore
        us.removeImage(key);
        
        // generate the output-stream
        if(log.isDebugEnabled())
            log.debug("Output Image Stream Type:" + mimeType );
        response.setContentType(mimeType);
        response.setContentLength(imageContents.length);
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        bos.write(imageContents);
        bos.close();
        if(log.isDebugEnabled())
            log.debug("Finished Outputting");
%>