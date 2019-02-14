package org.jaffa.security;

import org.jaffa.security.filter.FileFilter;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Test class to test the FileFilter methods
 */
public class FileFilterTest {

    /**
     * Test an expected legal filename against the filterUserInputFilename RegEx
     *
     * Expected: Non-null response
     */
    @Test
    public void filterUserInputFileNameLegalTest() {
        String fileName = "LegalFileName.zip";
        String filteredFileName = FileFilter.filterUserInputFileName(fileName);
        assertNotNull(filteredFileName);
    }

    /**
     * Test the ability of the filterUserInputFileName function to resolve extension case
     *
     * Expected: Non-null response
     */
    @Test
    public void filterUserInputFileNameExtensionCaseTest() {
        String fileName = "LegalFileName.zip";
        String filteredFileName = FileFilter.filterUserInputFileName(fileName);
        assertNotNull(filteredFileName);
    }

    /**
     * Test an expected illegal filename against the filterUserInputFilename RegEx
     *
     * Expected: Null response
     */
    @Test
    public void filterUserInputFileNameIllegalCharactersTest() {
        String fileName = "../IllegalFileName.zip";
        String filteredFileName = FileFilter.filterUserInputFileName(fileName);
        assertNull(filteredFileName);
    }

    /**
     * Test an internationalized filename against the filterUserInputFilename RegEx
     *
     * Expected: Non-null response
     */
    @Test
    public void filterUserInputFileNameInternationalTest() {
        String fileName = "Ab12γλΣÈÞæüŊﺽوح.zﻙp";
        String filteredFileName = FileFilter.filterUserInputFileName(fileName);
        assertNotNull(filteredFileName);
    }

    /**
     * Test an expected legal path against the filterUserInputPath RegEx
     *
     * Expected: Non-null response
     */
    @Test
    public void filterUserInputPathLegalTest() {
        String fileName = "/Legal/Path.Showing_Various-Legal.C/h/ars/main.jsp";
        String filteredFileName = FileFilter.filterUserInputPath(fileName);
        assertNotNull(filteredFileName);
    }

    /**
     * Test an expected illegal path against the filterUserInputPath RegEx
     *
     * Expected: Null response
     */
    @Test
    public void filterUserInputPathWrongIllegalCharacterTest() {
        String fileName = "Illegal/../Path/main.jsp";
        String filteredFileName = FileFilter.filterUserInputPath(fileName);
        assertNull(filteredFileName);
    }
}
