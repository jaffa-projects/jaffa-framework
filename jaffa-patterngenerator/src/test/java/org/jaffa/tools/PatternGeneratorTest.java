package org.jaffa.tools;

import org.jaffa.util.URLHelper;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import static org.jaffa.tools.PatternGenerator.PROPERTY_JAVA_ROOT_DIRECTORY;
import static org.jaffa.tools.PatternGenerator.PROPERTY_SQL_ROOT_DIRECTORY;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the PatternGenerator.
 */
public class PatternGeneratorTest {

    /** A temporary folder to collect created files */
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * Creates a pattern generator from a hard-coded test resource
     * @return a pattern generator
     * @throws PatternGeneratorException
     * @throws MalformedURLException
     */
    private PatternGenerator getPatternGenerator()
            throws PatternGeneratorException, IOException {
        File file = new File("src/test/resources/PurchaseRequestDelete.xml");
        String xmlPath = file.getAbsolutePath();
        System.out.println(xmlPath);

        Properties props = new Properties();
        File testFolder = tempFolder.newFolder("testFolder");
        String testFolderName = testFolder.getCanonicalPath();
        String javaFileLocation = testFolderName + File.separator + "javaroot";
        props.put(PROPERTY_JAVA_ROOT_DIRECTORY, javaFileLocation);
        String sqlFileLocation = testFolderName + File.separator + "SQLroot";
        props.put(PROPERTY_SQL_ROOT_DIRECTORY, sqlFileLocation);

        PatternGenerator generator =
                new PatternGenerator(URLHelper.newExtendedURL(xmlPath), props);
        return generator;
    }

    /**
     * Tests the processPattern method, when a single file is to be processed and
     * no optional arguments are supplied for where Java files and SQL files are
     * to be found.
     *
     * This is more of an integration test than a unit test, so is "Ignored"
     * until needed.  Verification of results is manual.
     * @throws Exception
     */
    @Ignore
    public void testProcessPatternUrl() throws Exception {
        //        String classpath = System.getProperty("java.class.path");
        //        System.out.println("classpath = " + classpath);
        // TODO replace the hard-coded paths with something appropriate for your
        // integration test
        String fileName =
                "C:/GOLDesp/Jaffa/trunk/DomainModel/build/patterns"
                + "/domain_creator_1_1/requisitioning/purchase/ScheduledQuantity.xml";
        URL url = URLHelper.newExtendedURL(fileName);
        PatternGenerator patternGenerator = new PatternGenerator(url);
        patternGenerator.processPattern();
    }

    /**
     * Tests the processPattern method, when a single file is to be processed and
     * optional arguments are supplied for where Java files and SQL files are
     * to be found.
     *
     * This is more of an integration test than a unit test, so is "Ignored"
     * until needed.  Verification of results is manual.
     * @throws Exception
     */
    @Ignore
    public void testProcessPatternUrlPropsFile() throws Exception {
        // TODO replace the hard-coded paths with something appropriate for your
        // integration test
        String fileName =
                "C:/GOLDesp/Jaffa/trunk/DomainModel/build/patterns"
                + "/domain_creator_1_1/requisitioning/purchase/ScheduledQuantity.xml";
        URL url = URLHelper.newExtendedURL(fileName);
        Properties props = new Properties();
        props.put(PROPERTY_JAVA_ROOT_DIRECTORY,
                  "C:/GOLDesp/Jaffa/trunk/DomainModel/source");
        props.put(PROPERTY_SQL_ROOT_DIRECTORY, // "SQLroot");
                  "C:/GOLDesp/Jaffa/trunk/DomainModel/source");
        PatternGenerator patternGenerator = new PatternGenerator(url, props);
        patternGenerator.processPattern();
    }

    /**
     * Tests the main method, when an entire directory is to be processed and
     * optional arguments are supplied for where Java files and SQL files are
     * to be found.
     * @throws Exception
     */
    @Ignore
    public void testMainPropsFileSelfContained() throws Exception {
        // TODO replace the hard-coded paths with something appropriate for your
        // integration test
        File file = new File("src/test/resources/PurchaseRequestDelete.xml");
        String xmlPath = file.getAbsolutePath();
        Properties props = new Properties();
        File testFolder = tempFolder.newFolder("testFolder");
        String testFolderName = testFolder.getCanonicalPath();
        String javaFilesLocation = testFolderName + File.separator + "javaroot";
        String sqlFilesLocation = testFolderName + File.separator + "SQLroot";
        String[] args = { xmlPath, javaFilesLocation, sqlFilesLocation };
        PatternGenerator.main(args);

        // e.g., C:\Users\you\AppData\Local\Temp\junit7886894553728199142\testFolder\javaroot\java\com\mirotechnologies\requisitioning\purchase\domain
        String genJavaFileName = javaFilesLocation +
           "\\java\\com\\mirotechnologies\\requisitioning\\purchase\\domain\\" +
           "PurchaseRequestDelete.java";
        File genJavaFile = new File(genJavaFileName);
        assertNotNull(genJavaFile);
        assertTrue(genJavaFile.exists());

        // e.g., C:\Users\you\AppData\Local\Temp\junit5951425034439982445\testFolder\SQLroot\sql\install\oracle\tables\PURCHASE_REQUESTS_DEL.sql
        String genSqlFileName = sqlFilesLocation +
            "\\sql\\install\\oracle\\tables\\PURCHASE_REQUESTS_DEL.sql";
        File genSqlFile = new File(genSqlFileName);
        assertNotNull(genSqlFile);
        assertTrue(genSqlFile.exists());
    }

    /**
     * Tests the main method, when an entire directory is to be processed and
     * optional arguments are supplied for where Java files and SQL files are
     * to be found.  Verification of results is manual.
     *
     * This is more of an integration test than a unit test, so is "Ignored"
     * until needed
     * @throws Exception
     */
    @Ignore
    public void testMainPropsDir() throws Exception {
        // TODO replace the hard-coded paths with something appropriate for your
        // integration test
        String[] args = {
                "C:/GOLDesp/Jaffa/trunk/DomainModel/build/patterns"
                + "/domain_creator_1_1/requisitioning/purchase",
                "C:/GOLDesp/Jaffa/trunk/DomainModel/source",
                "C:/GOLDesp/Jaffa/trunk/DomainModel/source"
        };
        PatternGenerator.main(args);
    }

    /**
     * Tests whether Java files should get written to the right place
     * @throws PatternGeneratorException
     * @throws MalformedURLException
     */
    @Ignore
    public void testDetermineFileToWriteJava()
            throws PatternGeneratorException, IOException {
        PatternGenerator generator = getPatternGenerator();
        String javaFileName = "a.java";
        String packageName = "com.somewhere";
        String fileToWrite = generator.determineFileToWrite(javaFileName,
                                                            packageName);
        Object javaRoot = generator.m_pgProperties.get(PROPERTY_JAVA_ROOT_DIRECTORY);
        assertTrue(fileToWrite.contains(javaRoot.toString()));
        assertTrue(fileToWrite.contains("somewhere"));
        assertTrue(fileToWrite.contains(javaFileName));
    }

    /**
     * Tests whether Sql files should get written to the right place
     * @throws PatternGeneratorException
     * @throws MalformedURLException
     */
    @Ignore
    public void testDetermineFileToWriteSql()
            throws PatternGeneratorException, IOException {
        PatternGenerator generator = getPatternGenerator();
        String sqlFileName = "a.sql";
        String packageName = "com.somewhere";
        String fileToWrite = generator.determineFileToWrite(sqlFileName,
                                                            packageName);
        Object sqlRoot = generator.m_pgProperties.get(PROPERTY_SQL_ROOT_DIRECTORY);
        assertTrue(fileToWrite.contains(sqlRoot.toString()));
        assertTrue(fileToWrite.contains("somewhere"));
        assertTrue(fileToWrite.contains(sqlFileName));
    }

}