/*
 * FormPrintServiceTest.java
 *
 * Created on September 9, 2004, 8:21 AM
 */

package org.jaffa.modules.printing.services;

import java.io.File;
import java.util.Properties;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.jaffa.unittest.ContextWrapper;
import org.jaffa.unittest.UnitTestUtil;

/**
 *
 * @author  PaulE
 */
public class FormPrintEnginePDFlibTest extends TestCase {
    
    public static final String PDFLIB_TEMPLATE_PDF = UnitTestUtil.getDataDirectory() + "/resources/templates/InvoiceTemplate.pdf";
    public static final String PDFLIB_REFERENCE_PDF = UnitTestUtil.getDataDirectory() + "/resources/reference/pdflib-Invoice.pdf";
    public static final String PDFLIB_OUTPUT_PDF = UnitTestUtil.getTempDirectory() + "/pdflib-Invoice.pdf";
    
    public static final long FILE_SIZE = 55609L;
    private static final Logger log = Logger.getLogger(FormPrintEnginePDFlibTest.class);
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new ContextWrapper(new TestSuite(FormPrintEnginePDFlibTest.class));
    }
    
   
    /** Creates new FormPrintServiceTest
     * @param name The name of the test case.
     */
    public FormPrintEnginePDFlibTest(String name) {
        super(name);
    }
    /** Make sure the engine is available */
    public void testCreateEngine() throws Exception {
        log.info("RUN: "+getName());
        try {
            IFormPrintEngine engine = FormPrintFactory.newInstance(FormPrintFactory.ENGINE_TYPE_PDFLIB);
            // Assume it worked if there were no errors...as we don't know what file it wrote
            assertNotNull("No Engine Instance was returned",engine);
            
        } catch (Exception e) {
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }
    
    /** Generate just the output file, but specify what the file name should be */
    public void testGenerateForm() throws Exception {
        log.info("RUN: "+getName());

        try {
            IFormPrintEngine engine = FormPrintFactory.newInstance(FormPrintFactory.ENGINE_TYPE_PDFLIB);
            // Assume it worked if there were no errors...as we don't know what file it wrote
            assertNotNull("No Engine Instance was returned", engine);
             
            String templateName = PDFLIB_TEMPLATE_PDF;
            engine.setTemplateName(templateName);
            assertEquals(templateName, engine.getTemplateName());
            
            /*
            String templateSearchPath = "";
            engine.setTemplateSearchPath(templateSearchPath);
            assertEquals(templateSearchPath, engine.getTemplateSearchPath());
             */

            Properties documentProperties = new Properties();
            documentProperties.setProperty(IFormPrintEngine.DOCUMENT_PROPERTY_AUTHOR, "@author@");
            documentProperties.setProperty(IFormPrintEngine.DOCUMENT_PROPERTY_CREATOR, "@creator@");
            documentProperties.setProperty(IFormPrintEngine.DOCUMENT_PROPERTY_KEYWORDS, "@keywords@");
            documentProperties.setProperty(IFormPrintEngine.DOCUMENT_PROPERTY_SUBJECT, "@subject@");
            documentProperties.setProperty(IFormPrintEngine.DOCUMENT_PROPERTY_TITLE, "@title@");
            engine.setDocumentProperties(documentProperties);
            
            InvoiceDataBean dataSource = new InvoiceDataBean();
            dataSource.setOrderNo(InvoiceDataBean.VALID_ORDER_NO);
            dataSource.populate();
            engine.setDataSource(dataSource.getDocumentRoot());
            assertEquals(engine.getDataSource(), dataSource.getDocumentRoot());
            
            boolean canPrint = true, canCopy = true, canModify = true;
            engine.setPermissions(canPrint, canCopy, canModify);

            engine.generate();

            // Make sure the engine generated the correct sized byte stream
            byte[] rawPdf = engine.getGeneratedForm();
            assertNotNull("No PDF was generated", rawPdf);
            log.debug("Generated a PDF document. Size=" + rawPdf.length);
            
            // Make sure engine can write out the file
            File tempOut = engine.writeForm();
            assertNotNull("writePdf() did not return a file", tempOut);
            assertTrue("writePdf() did not create the file",tempOut.exists());
            log.debug("Wrote File : " + tempOut.getAbsolutePath());

            // Get the reference file
            File ref = new File(PDFLIB_REFERENCE_PDF);
            assertTrue("Missing Reference file : " + PDFLIB_REFERENCE_PDF, ref.exists());
            assertEquals("File not same size as reference file",ref.length(),tempOut.length());
            // Clean files on sucess
            tempOut.delete();
                        
            // Make sure engine can write out to a named file
            File out = engine.writeForm(new File(PDFLIB_OUTPUT_PDF));
            assertNotNull("writePdf(File) did not return a file", out);
            assertTrue("writePdf(File) did not create the file",out.exists());
            log.debug("Wrote File : " + out.getAbsolutePath());
            assertEquals("File not same size as reference file",ref.length(),out.length());

            //Compare to reference file - the won't be exact because of the data
            double similar = UnitTestUtil.arePdfsSimilar(ref, out);
            log.debug("Generated File match was " + similar);
            assertTrue("Generated File is not similar, manually review the delta",similar>1.0);

            // Clean files on sucess
            out.delete();
        
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }
    
}
