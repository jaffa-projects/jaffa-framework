/*
 * ConvertPDFLibBlocksTest.java
 *
 * Created on September 3, 2004, 11:57 AM
 */

package org.jaffa.modules.printing.services;

import java.io.File;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.jaffa.unittest.ContextWrapper;
import org.jaffa.unittest.UnitTestUtil;

/**
 *
 * @author  PaulE
 */
public class ConvertPDFLibBlocksTest extends TestCase {
    public final static Logger log = Logger.getLogger(ConvertPDFLibBlocksTest.class);
    
    public static final String TEMPLATE_PDF = UnitTestUtil.getDataDirectory() + "/resources/templates/InvoiceTemplate.pdf";
    public static final String OUTPUT_FILE = UnitTestUtil.getTempDirectory() + "/InvoiceTemplate.pdf.csv";
    public static final String REFERENCE_OUT = UnitTestUtil.getDataDirectory() + "/resources/reference/InvoiceTemplate.pdf.csv";

    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new ContextWrapper(new TestSuite(ConvertPDFLibBlocksTest.class));
    }

    /** Creates new FormPrintServiceTest
     * @param name The name of the test case.
     */
    public ConvertPDFLibBlocksTest(String name) {
        super(name);
    }

    /** Make sure the engine is available */
    public void testFieldExtraction() throws Exception {
        log.info("RUN: "+getName());
        try {
            File out = new File(OUTPUT_FILE);
            if(out.exists())
                assertTrue("Can't Delete Output File", out.delete());
            ConvertPDFLibBlocks.convert(TEMPLATE_PDF, OUTPUT_FILE);
            
            out = new File(OUTPUT_FILE);
            
            assertTrue("Output File Not Created",out.exists());
            
            assertTrue(UnitTestUtil.areFilesIdentical(out, new File(REFERENCE_OUT)) );
            
        } catch (Exception e) {
            log.fatal("Test Failed", e);
        }
    }
    
}
