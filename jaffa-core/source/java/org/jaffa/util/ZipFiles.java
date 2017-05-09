package org.jaffa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class ZipFiles {
    private final Logger log = Logger.getLogger(ZipFiles.class);
    
    public void zipFolder(String srcPath, String destPath, String zipFileName) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;
        try {
            log.debug("Creating zip file: " + destPath + File.separator + zipFileName);
            fileWriter = new FileOutputStream(destPath + File.separator + zipFileName);
            zip = new ZipOutputStream(fileWriter);
            log.debug("Adding files from: " + srcPath);
            addFolderToZip("", srcPath, zip);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            zip.flush();
            zip.close();
        }
    }

    static private void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {
        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            if (path.equals("")) {
                zip.putNextEntry(new ZipEntry(folder.getName()));
            } else {
                zip.putNextEntry(new ZipEntry(path + File.separator + folder.getName()));
            }
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
            in.close();
        }
    }

    static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip("", srcFolder + File.separator + fileName, zip);
            } else {
                addFileToZip(path + File.separator + folder.getName(), srcFolder + File.separator + fileName, zip);
            }
        }
    }
}
