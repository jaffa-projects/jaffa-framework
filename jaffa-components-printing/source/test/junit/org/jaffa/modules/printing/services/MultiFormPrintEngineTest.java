/*
 * MultiFormPrintEngineTest.java
 *
 * Created on September 9, 2004, 8:21 AM
 */

package org.jaffa.modules.printing.services;

import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Formatter;
import org.jaffa.modules.printing.domain.FormDefinition;
import org.jaffa.modules.printing.domain.FormDefinitionMeta;
import org.jaffa.modules.printing.domain.FormTemplate;
import org.jaffa.modules.printing.domain.FormTemplateMeta;
import org.jaffa.modules.printing.services.exceptions.DataNotFoundException;
import org.jaffa.modules.printing.services.exceptions.DataNotReadyException;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Criteria;
import org.jaffa.persistence.IPersistent;
import org.jaffa.persistence.UOW;
import org.jaffa.unittest.ContextWrapper;
import org.jaffa.unittest.UnitTestUtil;
import org.jaffa.util.LoggerHelper;
import org.jaffa.util.StringHelper;
import org.jaffa.util.URLHelper;

/**
 *
 * @author  PaulE
 */
public class MultiFormPrintEngineTest extends TestCase {
    
    // Set up logging
    private static final Logger log = Logger.getLogger(MultiFormPrintEngineTest.class);
    //static { org.apache.log4j.BasicConfigurator.configure(); }
    
    public static final String TEMPLATE_PDF = UnitTestUtil.getDataDirectory() + "/resources/templates/InvoiceTemplate.pdf";
    public static final String TEMPLATE_PATH = UnitTestUtil.getDataDirectory() + "/resources/templates";
    
    public static final String ITEXT_REFERENCE_PDF = UnitTestUtil.getDataDirectory() + "/resources/reference/itext-multiform-Invoice.pdf";
    public static final String ITEXT_OUTPUT_PDF = UnitTestUtil.getTempDirectory() + "/itext-multiform-Invoice.pdf";
    
    public static final String PDFLIB_REFERENCE_PDF = UnitTestUtil.getDataDirectory() + "/resources/reference/pdflib-multiform-Invoice.pdf";
    public static final String PDFLIB_OUTPUT_PDF = UnitTestUtil.getTempDirectory() + "/pdflib-multiform-Invoice.pdf";
    
    public static final String TEMPLATE_VELOCITY = UnitTestUtil.getDataDirectory() + "/resources/templates/ShippingLabelTemplate.vtl";
    public static final String VELOCITY_REFERENCE = UnitTestUtil.getDataDirectory() + "/resources/reference/velocity-multiform-ShippingLabel.txt";
    public static final String VELOCITY_OUTPUT = UnitTestUtil.getTempDirectory() + "/velocity-multiform-ShippingLabel.txt";
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new ContextWrapper(new TestSuite(MultiFormPrintEngineTest.class));
    }
    
    
    /** Creates new FormPrintServiceTest
     * @param name The name of the test case.
     */
    public MultiFormPrintEngineTest(String name) {
        super(name);
    }

    /** Test iText Engine merging two forms together */
    public void testiTextMultiForm() throws Exception {
        log.info("RUN: "+getName());
        try {
            MultiFormPrintEngine engine = new MultiFormPrintEngine();
            // Assume it worked if there were no errors...as we don't know what file it wrote
            assertNotNull("No Engine Instance was returned", engine);
            
            // Set it to use the iText engine
            engine.setEngineType(FormPrintFactory.ENGINE_TYPE_ITEXT);
            
            
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

            // Add two copies of the same form
            String templateName = TEMPLATE_PDF;
            engine.addDocument(templateName,dataSource.getDocumentRoot());
            engine.addDocument(templateName,dataSource.getDocumentRoot());

            
            boolean canPrint = true, canCopy = true, canModify = true;
            engine.setPermissions(canPrint, canCopy, canModify);
            
            engine.generate();
            
            // Make sure the engine generated the correct sized byte stream
            byte[] rawPdf = engine.getGeneratedForm();
            assertNotNull("No PDF was generated", rawPdf);
            log.debug("Generated a PDF document. Size=" + rawPdf.length);
            
            // Make sure engine can write out to a named file
            File out = engine.writeForm(new File(ITEXT_OUTPUT_PDF));
            assertNotNull("writePdf(File) did not return a file", out);
            assertTrue("writePdf(File) did not create the file",out.exists());
            log.debug("Wrote File : " + out.getAbsolutePath());

            // Get the reference file
            File ref = new File(ITEXT_REFERENCE_PDF);
            assertTrue("Missing Reference file : " + ITEXT_REFERENCE_PDF, ref.exists());

            //Compare to reference file - the won't be exact because of the data
            double similar = UnitTestUtil.arePdfsSimilar(ref, out);
            log.debug("Generated File match was " + similar);
            assertTrue("Generated File is not similar, manually review the delta",similar<1.0);

            assertEquals("File not same size as reference file",ref.length(),out.length());

            // Clean files on sucess
            out.delete();
                        
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }
    


    /** Test PDFlib Engine merging two forms together */
    public void testPDFlibMultiForm() throws Exception {
        log.info("RUN: "+getName());
        try {
            MultiFormPrintEngine engine = new MultiFormPrintEngine();
            // Assume it worked if there were no errors...as we don't know what file it wrote
            assertNotNull("No Engine Instance was returned", engine);
            
            // Set it to use the iText engine
            engine.setEngineType(FormPrintFactory.ENGINE_TYPE_PDFLIB);
            engine.setTemplateSearchPath(TEMPLATE_PATH);
            
            
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

            // Add two copies of the same form
            String templateName = TEMPLATE_PDF;
            engine.addDocument(templateName,dataSource.getDocumentRoot());
            engine.addDocument(templateName,dataSource.getDocumentRoot());
            
            boolean canPrint = true, canCopy = true, canModify = true;
            engine.setPermissions(canPrint, canCopy, canModify);
            
            engine.generate();
            
            // Make sure the engine generated the correct sized byte stream
            byte[] rawPdf = engine.getGeneratedForm();
            assertNotNull("No PDF was generated", rawPdf);
            log.debug("Generated a PDF document. Size=" + rawPdf.length);
            
            // Make sure engine can write out to a named file
            File out = engine.writeForm(new File(PDFLIB_OUTPUT_PDF));
            assertNotNull("writePdf(File) did not return a file", out);
            assertTrue("writePdf(File) did not create the file",out.exists());
            log.debug("Wrote File : " + out.getAbsolutePath());

            // Get the reference file
            File ref = new File(PDFLIB_REFERENCE_PDF);
            assertTrue("Missing Reference file : " + PDFLIB_REFERENCE_PDF, ref.exists());
            
            //Compare to reference file - the won't be exact because of the data
            double similar = UnitTestUtil.arePdfsSimilar(ref, out);
            log.debug("Generated File match was " + similar);
            assertTrue("Generated File is not similar, manually review the delta",similar<1.0);

            assertEquals("File not same size as reference file",ref.length(),out.length());
            
            // Clean files on sucess
            out.delete();
                        
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }

    /** Test Velocity Engine merging two forms together */
    public void testVelocityMultiForm() throws Exception {
        log.info("RUN: "+getName());
        try {
            MultiFormPrintEngine engine = new MultiFormPrintEngine();
            // Assume it worked if there were no errors...as we don't know what file it wrote
            assertNotNull("No Engine Instance was returned", engine);
            
            // Set it to use the velocity engine
            engine.setEngineType(FormPrintFactory.ENGINE_TYPE_VELOCITY);
            
            InvoiceDataBean dataSource = new InvoiceDataBean();
            dataSource.setOrderNo(InvoiceDataBean.VALID_LABEL);
            dataSource.populate();

            // Add two copies of the same form
            String templateName = TEMPLATE_VELOCITY;
            engine.addDocument(templateName,((Object[])dataSource.getDocumentRoot())[0]);
            engine.addDocument(templateName,((Object[])dataSource.getDocumentRoot())[1]);

            engine.generate();
            
            // Make sure the engine generated the correct sized byte stream
            byte[] rawPdf = engine.getGeneratedForm();
            assertNotNull("No data was generated", rawPdf);
            log.debug("Generated a text document. Size=" + rawPdf.length);
            
            // Make sure engine can write out the file
            File tempOut = engine.writeForm();
            assertNotNull("writePdf() did not return a file", tempOut);
            assertTrue("writePdf() did not create the file",tempOut.exists());
            log.debug("Wrote File : " + tempOut.getAbsolutePath());

            // Get the reference file
            File ref = new File(VELOCITY_REFERENCE);
            assertTrue("Missing Reference file : " + VELOCITY_REFERENCE, ref.exists());
            assertEquals("File not same size as reference file",ref.length(),tempOut.length());
            // Clean files on sucess
            tempOut.delete();
                        
            // Make sure engine can write out to a named file
            File out = engine.writeForm(new File(VELOCITY_OUTPUT));
            assertNotNull("writePdf(File) did not return a file", out);
            assertTrue("writePdf(File) did not create the file",out.exists());
            log.debug("Wrote File : " + out.getAbsolutePath());

            // Compare to reference file - the won't be exact because of the data
            assertTrue("Generated File is not the same, manually review the delta",UnitTestUtil.areFilesIdentical(ref, out));
            
            assertEquals("File not same size as reference file",ref.length(),out.length());

            // Clean files on sucess
            out.delete();
                        
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }

}
