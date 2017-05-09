/*
 * FormPrintEngineiTextImageTest.java
 *
 */

package org.jaffa.modules.printing.services;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;
import org.jaffa.unittest.ContextWrapper;
import org.jaffa.unittest.UnitTestUtil;

/** Test that images get read and put on pages correctly
 *
 * @author  PaulE
 */
public class FormPrintEngineiTextImageTest extends TestCase {
    
    public static final String REFERENCE_PDF = UnitTestUtil.getDataDirectory() + "/resources/reference/Test_Form_Images.pdf";
    public static final String TEMPLATE_PDF = UnitTestUtil.getDataDirectory() + "/resources/templates/Test_Form_Images.pdf";
    public static final String OUTPUT_PDF = UnitTestUtil.getTempDirectory() + "/Test_Form_Images.pdf";
    
    private static final Logger log = Logger.getLogger(FormPrintEngineiTextImageTest.class);
    
    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new ContextWrapper(new TestSuite(FormPrintEngineiTextImageTest.class));
    }
    
    
    /** Creates new FormPrintServiceTest
     * @param name The name of the test case.
     */
    public FormPrintEngineiTextImageTest(String name) {
        super(name);
    }
    
    /** Generate just the output file, but specify what the file name should be */
    public void testGenerateForm() throws Exception {
        log.info("RUN: "+getName());
        
        try {
            IFormPrintEngine engine = FormPrintFactory.newInstance(FormPrintFactory.ENGINE_TYPE_ITEXT);
            // Assume it worked if there were no errors...as we don't know what file it wrote
            assertNotNull("No Engine Instance was returned", engine);
            
            engine.setTemplateName(TEMPLATE_PDF);
            engine.setDataSource(new ImageDom());
            engine.generate();
            

            // Make sure engine can write out to a named file
            File out = engine.writeForm( new File(OUTPUT_PDF) );
            assertNotNull("writePdf(File) did not return a file", out);
            assertTrue("writePdf(File) did not create the file",out.exists());
            log.debug("Wrote File : " + out.getAbsolutePath());

            // Get the reference file
            File ref = new File(REFERENCE_PDF);
            assertTrue("Missing Reference file : " + REFERENCE_PDF, ref.exists());
            assertEquals("File not same size as reference file",ref.length(),out.length());

            //Compare to reference file - the won't be exact because of the data
            double similar = UnitTestUtil.arePdfsSimilar(ref, out);
            log.debug("Generated File match was " + similar);
            assertTrue("Generated File is not similar, manually review the delta",similar<1.0);

            // Clean files on sucess
            out.delete();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Test Failed - " + e.getLocalizedMessage(), e);
            throw e;
        }
    }

}
