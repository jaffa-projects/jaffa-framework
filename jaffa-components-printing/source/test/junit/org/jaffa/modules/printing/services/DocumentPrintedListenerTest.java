/*
 * FormPrintServiceTest.java
 *
 * Created on September 9, 2004, 8:21 AM
 */

package org.jaffa.modules.printing.services;

import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
public class DocumentPrintedListenerTest extends TestCase {
    
    // Set up logging
    private static final Logger log = Logger.getLogger(MultiFormPrintEngineTest.class);
    //static { org.apache.log4j.BasicConfigurator.configure(); }
    
    public static final String TEMPLATE_PDF = UnitTestUtil.getDataDirectory() + "/resources/template/InvoiceTemplate.pdf";
    public static final String TEMPLATE_PATH = UnitTestUtil.getDataDirectory() + "/resources/template";
    
    public static final String ITEXT_REFERENCE_PDF = UnitTestUtil.getDataDirectory() + "/resources/reference/itext-multiform-Invoice.pdf";
    public static final String ITEXT_OUTPUT_PDF = UnitTestUtil.getTempDirectory() + "/itext-multiform-Invoice.pdf";
    
    public static final String VALID_EMAIL = "paul.extance@mirotechnologies.com";
    public static final String INVALID_EMAIL = "paul";

    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new ContextWrapper(new TestSuite(DocumentPrintedListenerTest.class));
    }
    
    
    /** Creates new FormPrintServiceTest
     * @param name The name of the test case.
     */
    public DocumentPrintedListenerTest(String name) {
        super(name);
    }

    /** Make sure event is fired for Web Publishing */
    public void testSinglePageTest() throws Exception {
        log.info("RUN: "+getName());
        InvoiceDataBean.m_printed = 0;
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
            p.put("orderNo", InvoiceDataBean.VALID_ORDER_NO2);
            req.setKeys(p);
            req.setSaveFileName( File.createTempFile("unittest-", ".pdf", new File(UnitTestUtil.getTempDirectory())).getAbsolutePath() );

            // Run the process, and get the generated file
            FormProcessor.process(req);
            File out = new File(req.getSaveFileName());

            // Assume it worked if there were no errors...as we don't know what file it wrote
            assertTrue("No file was generated", out.exists());
            assertTrue("DocumentPrintedListener not called", InvoiceDataBean.m_printed!=0);
            assertTrue("DocumentPrintedListener called multiple times :"+InvoiceDataBean.m_printed, InvoiceDataBean.m_printed==1);
        } catch (Exception e) {
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }

    /** Make sure event is fired for Email */
    public void testSinglePageEmailTest() throws Exception {
        log.info("RUN: "+getName());
        InvoiceDataBean.m_printed = 0;
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
            p.put("orderNo", InvoiceDataBean.VALID_ORDER_NO2);
            req.setKeys(p);
            req.setEmailToAddresses(VALID_EMAIL);
     
            // Run the process, and check the listener
            FormProcessor.process(req);
            assertTrue("DocumentPrintedListener not called", InvoiceDataBean.m_printed!=0);
            assertTrue("DocumentPrintedListener called multiple times :"+InvoiceDataBean.m_printed, InvoiceDataBean.m_printed==1);
        } catch (Exception e) {
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }

    /** Make sure event is not fired for Email Exception*/
    public void testSinglePageBadEmailTest() throws Exception {
        log.info("RUN: "+getName());
        InvoiceDataBean.m_printed = 0;
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
            p.put("orderNo", InvoiceDataBean.VALID_ORDER_NO2);
            req.setKeys(p);
            req.setEmailToAddresses(INVALID_EMAIL);
     
            // Run the process, and check the listener
            FormProcessor.process(req);
            fail("Exception should have been throw for bad email address");
        } catch (Exception e) {
            assertTrue("DocumentPrintedListener was called on an error", InvoiceDataBean.m_printed==0);
        }
    }
    
    
    /** Make sure event is not fired if the same DataBean is used again in a follow on form */
    public void testMultiDocumentEmailTest() throws Exception {
        log.info("RUN: "+getName());
        //@TODO
    }
    
}
