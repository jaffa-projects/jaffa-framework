/*
 * FormPrintServiceTest.java
 *
 * Created on September 9, 2004, 8:21 AM
 */

package org.jaffa.modules.printing.services;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.jaffa.unittest.UnitTestUtil;

/**
 *
 * @author  PaulE
 */
public class DomainDataBeanTest extends TestCase {

    private static final Logger log = Logger.getLogger(DomainDataBeanTest.class);

    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new FormDataWrapper(new TestSuite(DomainDataBeanTest.class));
    }
    
    /** Creates new FormAssemblyTest
     * @param name The name of the test case.
     */
    public DomainDataBeanTest(String name) {
        super(name);
    }

    /** Generate a valid PDF document with the iText engine
     * This will test the DomainDataBean factory
     */
    public void testOutputDomainForm() throws Exception {
        log.info("RUN: "+getName());
        try {
            FormPrintRequest req = new FormPrintRequest();
            // Mandatory
            req.setFormName(FormDataWrapper.FORM_NAME3);
            // Any properties to be reflected onto the data bean so it can be retrieved
            final LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
            p.put("formName", FormDataWrapper.FORM_NAME3);
            req.setKeys(p);
            req.setSaveFileName( File.createTempFile("unittest-", ".pdf", new File(UnitTestUtil.getTempDirectory())).getAbsolutePath() );

            // Run the process, and get the generated file
            FormProcessor.process(req);
            File out = new File(req.getSaveFileName());

            // Assume it worked if there were no errors...as we don't know what file it wrote
            assertTrue("No file was generated", out.exists());
            assertEquals("File is wrong size - " + out.length(), 49836L, out.length()); 
        } catch (Exception e) {
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }


}
