package org.jaffa.api;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ConfigApiCoreTest {
    @Test
    public void testGetFileContents() {
        String testConfigZip = "testConfig.zip";
        try {
            String mfContent = "Context-Salience: 3-CUSTOMCONFIGURE\nImport: true\n";
            createTestConfigZipFile(testConfigZip, mfContent);
            File configFile = new File(testConfigZip);
            FileContents fileContent = ConfigApiCore.getFileContents(configFile);
            assertEquals("true", fileContent.getImportProperty());
            configFile.delete();

            mfContent = "Context-Salience: 3-CUSTOMCONFIGURE\nImport: FALSE\n";
            createTestConfigZipFile(testConfigZip, mfContent);
            configFile = new File(testConfigZip);
            fileContent = ConfigApiCore.getFileContents(configFile);
            assertEquals("false", fileContent.getImportProperty());
            configFile.delete();

            mfContent = "Context-Salience: 3-CUSTOMCONFIGURE\n";
            createTestConfigZipFile(testConfigZip, mfContent);
            configFile = new File(testConfigZip);
            fileContent = ConfigApiCore.getFileContents(configFile);
            assertNull(fileContent.getImportProperty());
            configFile.delete();
        } catch (IOException e) {
        }
    }

    private long createTestConfigZipFile(String zipFile, String manifestContent) throws IOException {
        long filesize = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(manifestContent);

        File f = new File(zipFile);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
        ZipEntry e = new ZipEntry("META-INF/MANIFEST.MF");
        out.putNextEntry(e);

        byte[] data = sb.toString().getBytes();
        out.write(data, 0, data.length);
        out.closeEntry();
        out.close();
        filesize = f.length();

        return filesize;
    }
}