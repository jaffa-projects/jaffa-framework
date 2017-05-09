/*
 * FormPrintServiceTest.java
 *
 * Created on September 9, 2004, 8:21 AM
 */

package org.jaffa.modules.printing.services;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.modules.printing.services.exceptions.DataNotFoundException;
import org.jaffa.unittest.UnitTestUtil;

/**
 *
 * @author  PaulE
 */
public class FormProcessorLabelTest extends TestCase {

    private static final Logger log = Logger.getLogger(FormProcessorLabelTest.class);
    public static final String VALID_EMAIL = "paul.extance@mirotechnologies.com";
    public static final String INVALID_EMAIL = "paul";
    public static final String INVALID_PDF_PRINTER = "DummyPrinter";


    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new FormDataWrapper(new TestSuite(FormProcessorLabelTest.class));
    }
    
    /** Creates new FormAssemblyTest
     * @param name The name of the test case.
     */
    public FormProcessorLabelTest(String name) {
        super(name);
    }


    /** Generate a valid intermec label with the velocity engine */
    public void testOutputLabel() throws Exception {
        log.info("RUN: "+getName());
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME2);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap p = new LinkedHashMap<String, String>();
            p.put("orderNo", InvoiceDataBean.VALID_LABEL);
            req.setKeys(p);
            req.setSaveFileName( File.createTempFile("unittest-", ".txt", new File(UnitTestUtil.getTempDirectory())).getAbsolutePath() );

            // Run the process, and get the generated file
            FormProcessor.process(req);
            File out = new File(req.getSaveFileName());

            // Assume it worked if there were no errors...as we don't know what file it wrote
            assertTrue("No file was generated", out.exists());
            //assertTrue("File seems too small - " + out.length(), out.length() > 57000); 
            //assertTrue("File seems too big - " + out.length(), out.length() < 59000); 
        } catch (Exception e) {
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }

    /** Generate a valid intermec label with the velocity engine */
    public void testEmailLabel() throws Exception {
        log.info("RUN: "+getName());
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME2);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap p = new LinkedHashMap<String, String>();
            p.put("orderNo", InvoiceDataBean.VALID_LABEL);
            req.setKeys(p);

            req.setEmailToAddresses(VALID_EMAIL);
            
            FormProcessor.process(req);
            // Assume it worked if there were no errors...as we don't know what file it wrote
        } catch (Exception e) {
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }

//    /** Generate a valid intermec label with the velocity engine */
//    public void testPrintLabel() throws Exception {
//        log.info("RUN: "+getName());
//        try {
//            FormPrintRequest req = new FormPrintRequest();
//            // Mandatory
//            req.setFormName(FormDataWrapper.FORM_NAME2);
//            // Any properties to be reflected onto the data bean so it can be retrieved
//            Properties p = new Properties();
//            p.setProperty("orderNo", InvoiceDataBean.VALID_LABEL);
//            req.setKeys(p);
//
//            // Fake out a printer so it can find the correct output type???
//            // Name of the print queue
//            req.setPrinterId(FormDataWrapper.VAILD_LABEL_PRINTER);
//            // Set copies
//            req.setPrintCopies(new Integer(1));            
//            
//            FormProcessor.process(req);
//            // Assume it worked if there were no errors...as we don't know what file it wrote
//        } catch (Exception e) {
//            log.error("Test Failed - " + e.getLocalizedMessage(), e);
//            throw e;
//        }
//    }
}
