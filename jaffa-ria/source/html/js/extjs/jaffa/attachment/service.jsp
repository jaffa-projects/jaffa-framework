<%-- ---------------------------------------------------------------------------------------
The functionality provided by this JSP is controlled by the 'command' parameter. It can be:
1) update: Create or Update an Attachment.
  - INPUT: File to be uploaded, if any, and Form fields for the Attachment record.
  - OUTPUT: Returns JSON indicating success/failure.

2) viewAttachmentData: Returns the uploaded contents of an Attachment.
  - INPUT: The attachmentId for identifying an Attachment record.
  - OUTPUT: The uploaded contents of an Attachment.
---------------------------------------------------------------------------------------- --%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.FileUploadException"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="org.jaffa.components.attachment.apis.AttachmentService"%>
<%@page import="org.jaffa.components.attachment.apis.data.AttachmentCriteria"%>
<%@page import="org.jaffa.components.attachment.apis.data.AttachmentGraph"%>
<%@page import="org.jaffa.components.attachment.apis.data.AttachmentQueryResponse"%>
<%@page import="org.jaffa.components.attachment.apis.data.AttachmentUpdateResponse"%>
<%@page import="org.jaffa.components.finder.CriteriaField"%>
<%@page import="org.jaffa.components.finder.StringCriteriaField"%>
<%@page import="org.jaffa.util.StringHelper"%>

<%!
private static final Logger log = Logger.getLogger("js/extjs/jaffa/attachment/service.jsp");

/** A convenience method to trim a String, and to return a NULL for empty Strings. */
private String handleParam(String param) {
    if (param != null)
        param = param.trim();
    return param != null && param.length() > 0 ? param : null;
}

/** A convenience method to create an AttachmentGraph from the input parameters. */
private AttachmentGraph createGraph(Map<String, String> parameterMap) {
    AttachmentGraph graph = new AttachmentGraph();
    if (parameterMap.containsKey("attachmentId"))
        graph.setAttachmentId(parameterMap.get("attachmentId"));
    if (parameterMap.containsKey("serializedKey"))
        graph.setSerializedKey(parameterMap.get("serializedKey"));
    if (parameterMap.containsKey("originalFileName"))
        graph.setOriginalFileName(parameterMap.get("originalFileName"));
    if (parameterMap.containsKey("attachmentType"))
        graph.setAttachmentType(parameterMap.get("attachmentType"));
    if (parameterMap.containsKey("contentType"))
        graph.setContentType(parameterMap.get("contentType"));
    if (parameterMap.containsKey("description"))
        graph.setDescription(parameterMap.get("description"));
    if (parameterMap.containsKey("remarks"))
        graph.setRemarks(parameterMap.get("remarks"));
    if (parameterMap.containsKey("supercededBy"))
        graph.setSupercededBy(parameterMap.get("supercededBy"));
    return graph;
}

/**
 * Parses the input request, loading the parameters and files into the input Map and List respectively.
 */
private void loadParametersAndFiles(HttpServletRequest request, Map<String, String> parameterMap, List<FileItem> files) throws FileUploadException {
    // During file-upload, a multi-part request will be passed in.
    // Parse the multi-part request using commons-fileupload to create
    // a Map of request parameters and a List of files being uploaded.
    DiskFileUpload upload = new DiskFileUpload();
    if (upload.isMultipartContent(request)) {
        List<FileItem> multiPartItems = upload.parseRequest(request);
        for (FileItem multiPartItem : multiPartItems) {
            if (multiPartItem.isFormField())
                parameterMap.put(multiPartItem.getFieldName(), handleParam(multiPartItem.getString()));
            else
                files.add(multiPartItem);
        }
    }
    
    // For a non-file-upload, a regular request will be passed in.
    // Copy the parameters into the Map.
    for (Object name : request.getParameterMap().keySet())
        parameterMap.put((String) name, handleParam(StringHelper.escapeJavascript(request.getParameter((String) name))));
    
    if (log.isDebugEnabled()) {
        log.debug("Request parameters: " + parameterMap);
        log.debug("Number of Files to be uploaded: " + files.size());
    }
}

/**
 * Create/Update an attachment, and add a JSON string to the response stream with success=true/false and error="error-text".
 */
private void update(JspWriter out, Map<String, String> parameterMap, List<FileItem> files) throws IOException {
    AttachmentGraph[] graphs = new AttachmentGraph[files.size() > 0 ? files.size() : 1];
    if (files.size() > 0) {
        int index = 0;
        for (FileItem file : files) {
            AttachmentGraph graph = createGraph(parameterMap);
            graph.setOriginalFileName(file.getName());
            graph.setAttachmentType("E");
            graph.setContentType(file.getContentType());
            graph.setData(file.get());
            graphs[index++] = graph;
        }
    } else
        graphs[0] = createGraph(parameterMap);
    
    if (log.isDebugEnabled())
        log.debug("Invoking AttachmentService with the input: " + graphs);
    AttachmentUpdateResponse[] response = new AttachmentService().update(graphs);
    if (response != null) {
        for (AttachmentUpdateResponse r : response) {
            if (r.getErrors() != null && r.getErrors().length > 0) {
                // Escape single-quotes, if any, in the error message
                String error = StringHelper.escapeJavascript(r.getErrors()[0].getLocalizedMessage());
                if (log.isDebugEnabled())
                    log.debug("Error during creation of Attachment: '" + error + '\'');
                out.println("{success: false, error: '" + error + "'}");
                return;
            }
        }
    }
    
    // Add a JSON string to the response stream to indicate success
    out.println("{success: true}");
}

/**
 * Return the uploaded contents of an attachment. It does the following:
 * - Retrieves the Attachment based on the attachmentId passed in the input parameterMap.
 * - Creates a temp-file with the uploaded data.
 * - Adds originalFileName, contentType and temp-file as request attributes.
 * - Forwards to the "/jaffa/admin/fileexplorer/download" servlet, which will serve the temp-file.
 */
private void viewAttachmentData(PageContext pageContext, Map<String, String> parameterMap) throws IOException, ServletException {
    // Retrieve the attachment
    String attachmentId = parameterMap.get("attachmentId");
    if (attachmentId == null) {
        String s = "The parameter 'attachmentId' is missing";
        log.error(s);
        throw new IllegalArgumentException(s);
    }
    AttachmentCriteria c = new AttachmentCriteria();
    c.setAttachmentId(new StringCriteriaField(CriteriaField.RELATIONAL_EQUALS, attachmentId));
    AttachmentQueryResponse response = new AttachmentService().query(c);
    if (response == null || response.getGraphs() == null || response.getGraphs().length == 0) {
        String s = "The parameter 'attachmentId' is invalid. An Attachment was not found for the value: " + attachmentId;
        log.error(s);
        throw new IllegalArgumentException(s);
    }
    AttachmentGraph graph = response.getGraphs()[0];
    
    // Create a temp-file with the uploaded data
    File attachmentDataFile = null;
    OutputStream outputStream = null;
    try {
        String fileName = graph.getOriginalFileName();
        
        // Determine the suffix
        int i = fileName != null ? fileName.lastIndexOf('.') : -1;
        String suffix = i >= 0 ? fileName.substring(i) : null;
        
        // Create a temporary file with the suffix
        attachmentDataFile = File.createTempFile("jaffa", suffix);
        
        // Add the data to the file
        outputStream = new BufferedOutputStream(new FileOutputStream(attachmentDataFile));
        outputStream.write(graph.getData());
        outputStream.flush();
    } finally {
        try {
            if (outputStream != null)
                outputStream.close();
        } catch (IOException e) {
            // do nothing
        }
        attachmentDataFile.deleteOnExit();
    }
    
    // Add originalFileName, contentType and temp-file as request attributes
    pageContext.getRequest().setAttribute("org.jaffa.applications.jaffa.modules.admin.components.fileexplorer", attachmentDataFile);
    pageContext.getRequest().setAttribute("org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.fileName", graph.getOriginalFileName());
    pageContext.getRequest().setAttribute("org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.contentType", graph.getContentType());
    
    // Forward to the FileExplorer/Download servlet, which will serve the temp-file
    pageContext.forward("/jaffa/admin/fileexplorer/download");
}
%>

<%
String command = null;
Map<String, String> parameterMap = new HashMap<String, String>();
List<FileItem> files = new LinkedList<FileItem>();
loadParametersAndFiles(request, parameterMap, files);
command = parameterMap.get("command");
if ("update".equals(command)) {
    update(out, parameterMap, files);
} else if ("viewAttachmentData".equals(command)) {
    viewAttachmentData(pageContext, parameterMap);
    return; //ensures that nothing is written to the response stream subsequent to a 'forward'
} else {
    String s = "Unsupported value passed for the parameter 'command': " + command;
    log.error(s);
    throw new IllegalArgumentException(s);
}
%>
