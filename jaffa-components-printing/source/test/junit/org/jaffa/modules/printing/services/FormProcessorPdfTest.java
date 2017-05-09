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
import org.jaffa.exceptions.DomainObjectNotFoundException;
import org.jaffa.modules.printing.services.exceptions.DataNotFoundException;
import org.jaffa.modules.printing.services.exceptions.EmailFailedException;
import org.jaffa.unittest.UnitTestUtil;

/**
 *
 * @author  PaulE
 */
public class FormProcessorPdfTest extends TestCase {

    private static final Logger log = Logger.getLogger(FormProcessorPdfTest.class);
    public static final String VALID_EMAIL = "paul.extance@mirotechnologies.com";
    public static final String INVALID_EMAIL = "paul";
    public static final String INVALID_PDF_PRINTER = "DummyPrinter";


    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new FormDataWrapper(new TestSuite(FormProcessorPdfTest.class));
    }
    
    /** Creates new FormAssemblyTest
     * @param name The name of the test case.
     */
    public FormProcessorPdfTest(String name) {
        super(name);
    }

    /** Generate a valid PDF document with the iText engine */
    public void testOutputPdfForm() throws Exception {
        log.info("RUN: "+getName());
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
            p.put("orderNo", InvoiceDataBean.VALID_ORDER_NO);
            req.setKeys(p);
            req.setSaveFileName( File.createTempFile("unittest-", ".pdf", new File(UnitTestUtil.getTempDirectory())).getAbsolutePath() );

            // Run the process, and get the generated file
            FormProcessor.process(req);
            File out = new File(req.getSaveFileName());

            // Assume it worked if there were no errors...as we don't know what file it wrote
            assertTrue("No file was generated", out.exists());
            assertTrue("File seems too small - " + out.length(), out.length() > 57000); 
            assertTrue("File seems too big - " + out.length(), out.length() < 59000); 
        } catch (Exception e) {
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }

    /** Generate a PDF document where the data bean can't find the data */
    public void testNoFormData() throws Exception {
        log.info("RUN: "+getName());
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
            p.put("orderNo", InvoiceDataBean.INVALID_ORDER_NO);
            req.setKeys(p);
            
            FormProcessor.process(req);
            // Assume it failed if there were no errors...as we don't know what file it wrote
            fail("No Exception was rasied for no data available in the bean");
        } catch (ApplicationExceptions e) {
            assertEquals("Wrong No. of Errors",1,e.size());
            for(Iterator i = e.iterator(); i.hasNext();)
                if(i.next() instanceof DataNotFoundException)
                    return; // Correct exception thrown
            fail("Wrong Exception was thrown!");
        } catch (Exception e) {
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }

    /** Generate a PDF output file and e-mail it to a valid recipiant */
    public void testEmailPdfForm() throws Exception {
        log.info("RUN: "+getName());
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
            p.put("orderNo", InvoiceDataBean.VALID_ORDER_NO);
            req.setKeys(p);

            req.setEmailToAddresses(VALID_EMAIL);
            
            FormProcessor.process(req);
            // Assume it worked if there were no errors...as we don't know what file it wrote
        } catch (Exception e) {
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }

    /** Generate a PDF output file and e-mail it to an invalid address */
    public void testBadEmailForm() throws Exception {
        log.info("RUN: "+getName());
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
            p.put("orderNo", InvoiceDataBean.VALID_ORDER_NO);
            req.setKeys(p);

            req.setEmailToAddresses(INVALID_EMAIL);
            
            FormProcessor.process(req);
            // Assume it failed if there were no errors...as we don't know what file it wrote
            fail("No Exception was rasied for a bad e-mail address");
        } catch (ApplicationExceptions e) {
            assertEquals("Wrong No. of Errors",1,e.size());
            //Test for correct exception            
            for(Iterator i = e.iterator(); i.hasNext();) {
                Exception ex = (Exception)i.next();
                if(ex instanceof EmailFailedException )
                    return; // Correct exception thrown
            }
            fail("Wrong Exception was thrown!");
        } catch (Exception e) {
            fail("Wrong Exception was thrown!");
        }
    }


    /** Generate a PDF output file and print one copy to a valid printer, using an OS command */
    public void testPrintPdfOneCopy() throws Exception {
        log.info("RUN: "+getName());
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
            p.put("orderNo", InvoiceDataBean.VALID_ORDER_NO);
            req.setKeys(p);

            // Name of the print queue
            req.setPrinterId(FormDataWrapper.VAILD_PDF_PRINTER);
            // Set copies
            req.setPrintCopies(new Integer(1));
            
            FormProcessor.process(req);
            // Assume it worked if there were no errors...as we don't know what file it wrote
        } catch (Exception e) {
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }

    /** Generate a PDF output file and print one copy to an invalid printer,
     *  make sure the correct error is thrown
     */
    public void testInvalidPrinter() throws Exception {
        log.info("RUN: "+getName());
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
            p.put("orderNo", InvoiceDataBean.VALID_ORDER_NO);
            req.setKeys(p);

            // Name of the print queue
            req.setPrinterId(INVALID_PDF_PRINTER);
            // Set copies
            req.setPrintCopies(new Integer(1));
            
            FormProcessor.process(req);
            // Assume it worked if there were no errors...as we don't know what file it wrote
            fail("No Exception was rasied for an invalid printer");
        } catch (ApplicationExceptions e) {
            assertEquals("Wrong No. of Errors",1,e.size());
            //Test for correct exception            
            for(Iterator i = e.iterator(); i.hasNext();) {
                Exception ex = (Exception)i.next();
                if(ex instanceof DomainObjectNotFoundException )
                    return; // Correct exception thrown
            }
            fail("Wrong Exception was thrown!");
        } catch (Exception e) {
            fail("Wrong Exception was thrown!");
        }
    }

}
