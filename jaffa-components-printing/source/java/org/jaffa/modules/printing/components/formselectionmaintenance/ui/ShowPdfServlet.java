/*
 * ShowPdfServlet.java
 *
 * Created on December 8, 2006, 11:39 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jaffa.modules.printing.components.formselectionmaintenance.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;

/** Servlet needed for FormSelectionMaintenance to open and display pdf
 *  Include the following in web.xml
 *
 * <servlet>
     <servlet-name>ShowPdfServlet</servlet-name>
     <servlet-class>org.jaffa.modules.printing.components.formselectionmaintenance.ui.ShowPdfServlet</servlet-class>
   </servlet>
  <servlet-mapping>
    <servlet-name>ShowPdfServlet</servlet-name>
    <url-pattern>/ShowPdf</url-pattern>
  </servlet-mapping>

 * @author SrinivasT
 */
@WebServlet(value="/ShowPdf", name="ShowPdfServlet")
public class ShowPdfServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(ShowPdfServlet.class);
    private ServletConfig m_servletConfig = null;

    /** Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        m_servletConfig = config;
    }

    /** Destroys the servlet.
     */
    public void destroy() {

    }

    /** Processes requests for both HTTP GET and POST methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processPdf(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {

        String tmpDir = System.getProperty("java.io.tmpdir");

        String path = request.getParameter("id");

        if(log.isDebugEnabled())
            log.debug("path===>"+path);
        //read the file name.
        File file = new File(tmpDir+File.separator+path);

        if(file==null || !file.exists()) {
           log.error("Download of file " + (file==null?"NULL":file.getAbsolutePath()) + " failed.");
           throw new ServletException("Download Failed, File not available");
        }

        if(log.isDebugEnabled())
            log.debug("Downloading file " + file.getAbsolutePath());


        response.setContentType("application/pdf");


        Boolean attachment = Parser.parseBoolean(request.getParameter("attachment"));

        /* If pdf viewer does not support inline viewing (eg. XPDF on SCO) then open in external application */
        if (attachment != null && attachment.booleanValue())
            response.setHeader("Content-Disposition", "attachment; filename=\""+file.getName()+"\"");
        else
			response.setHeader("Content-Disposition", "inline; filename=\""+file.getName()+"\"");

        try {
            response.setBufferSize(2048);
        } catch (IllegalStateException e) {/*Silent catch*/}

        InputStream in = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();

        try {
            byte[] buffer = new byte[1000];
            while (in.available()>0)
                out.write(buffer, 0, in.read(buffer));
            out.flush();
        } catch (IOException e) {
            log.error("Problem Serving Resource " + file.getAbsolutePath(), e);
        } finally {
            out.close();
            in.close();
        }

        if(log.isDebugEnabled())
            log.debug("Downloaded file " + file.getAbsolutePath());

        if(log.isDebugEnabled())
            log.debug("Deleting file " + file.getAbsolutePath());

        if(!file.delete())
            log.error("Failed to delete file " + file.getAbsolutePath());

        return;
    }

    /**
     * Processes a GET request.
     *
     * @param request the request.
     * @param response the response.
     *
     * @throws ServletException if there is a servlet related problem.
     * @throws IOException if there is an I/O problem.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processPdf(request, response);
    }

    /**
     * Processes a POST request.
     *
     * @param request the request.
     * @param response the response.
     *
     * @throws ServletException if there is a servlet related problem.
     * @throws IOException if there is an I/O problem.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processPdf(request, response);
    }

    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "View a file";
    }


}
