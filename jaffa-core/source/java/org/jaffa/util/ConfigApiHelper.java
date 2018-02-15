/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.jaffa.util;

import org.apache.log4j.Logger;
import org.jaffa.loader.IManager;
import org.jaffa.loader.ManagerRepositoryService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * This class provides static helper methods to perform functions for the ConfigApi endpoints
 * @author Matthew Wayles
 * @version 1.0
 */
public class ConfigApiHelper {
    private static final int BUFFER_SIZE = 1024;
    private static final Logger log = Logger.getLogger(ConfigApiHelper.class);

    /**
     * Verify that the user supplied a file extension in the URL, and add it if not
     * @param input The user URL input
     * @param extension The file extension expected (defaults to ZIP)
     * @return  The user input with appended file extension
     */
    public static String verifyExtension(String input, String extension) {
        if (!input.endsWith(extension)) {
            input += extension;
        }
        return input;
    }

    /**
     * Extract a compressed file's contents to a temporary directory at the provided path
     * @param file  The compressed file to be extracted
     * @return The temporary directory containing the compressed contents
     * @throws IOException  Thrown when the provided compressed file does not exist or cannot be read
     */
    public static File extractToTemporaryDirectory(File file) throws IOException {
        //Create temporary directory
        File tempDir = new File(file.getAbsolutePath().split("\\.")[0]);
        tempDir.mkdir();

        //Extract compressed contents to temporary directory
        ZipInputStream zis = new ZipInputStream(new FileInputStream(file.getAbsolutePath()));
        ZipEntry zipEntry = zis.getNextEntry();
        byte[] buffer = new byte[BUFFER_SIZE];
        while (zipEntry != null) {
            String fileName = zipEntry.getName();
            File newFile = new File(tempDir + File.separator + fileName);
            if (!newFile.exists()) {
                newFile.getParentFile().mkdir();
                newFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }

        //Clean up
        zis.closeEntry();
        zis.close();
        log.debug("Extracted " + file + "to " + tempDir);

        return tempDir;
    }

  /**
   * In certain cases, a configuration archive cannot be immediately removed from the filesystem because
   * its contents are still undergoing removal operations of their own. This method allows the contents to
   * complete their processes and release the file handle so that the zip parent can be removed
   * @param file    The configuration archive to remove
   * @throws IOException    When a file cannot be accessed or operations cannot be performed on it
   */
    public static void removeZipFile(File file) throws IOException {
        try {
            Files.deleteIfExists(file.toPath());
            log.info("Successfully removed " + file.getName() + "from the filesystem");
        }
        catch (IOException ex) {
            removeZipFile(file);
        }
    }

    /**
     * Register configuration files in IManager implementationss, based on the source of the method call
     * @param file  The configuration file to be registered or unregistered
     * @return  Success or failure of the operation
     */
    public static boolean registerResources(File file, String contextSalience) {
        boolean isSuccess = true;
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        for(IManager manager : ManagerRepositoryService.getInstance().getManagerMap().values()) {
            Resource resource = getMetaInfResource(file, resolver, manager);
            try {
                if (resource.getFile().exists()) {
                    manager.registerResource(resource, contextSalience,
                            ContextHelper.getVariationSalience(resource.getURI().toString()));
                    ManagerRepositoryService.getInstance().add(manager.getClass().getSimpleName(), manager);
                    log.debug(resource.getFilename() + " was successfully registered to " + manager);
                }
            } catch (Exception e) {
                isSuccess = false;
                log.error("The resource " + resource.getFilename() + " failed to register", e);
            }
        }
        return isSuccess;
    }

    /**
     * Unregister configuration files in IManager implementations, based on the source of the method call
     * @param file  The configuration file to be registered or unregistered
     * @return  Success or failure of the operation
     */
    public static boolean unregisterResources(File file, String contextSalience) {
        boolean isSuccess = true;
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        for(IManager manager : ManagerRepositoryService.getInstance().getManagerMap().values()) {
            Resource resource = getMetaInfResource(file, resolver, manager);
            try {
                if (resource.getFile().exists()) {
                    manager.unregisterResource(resource, contextSalience,
                            ContextHelper.getVariationSalience(resource.getURI().toString()));
                    log.debug(resource.getFilename() + " was successfully unregistered from " + manager);
                }
            } catch (Exception e) {
                isSuccess = false;
                log.warn("The resource " + resource.getFilename() + " failed to unregister", e);
            }
        }
        return isSuccess;
    }

    /**
     * Remove a directory recursively ("rm -rf").
     * @param dir  Directory to be removed.
     * @throws IOException When a file cannot be accessed or operations cannot be performed on it
     */
    public static void removeDirTree(File dir) throws IOException {

        Files.walk(dir.toPath())
                .map(Path::toFile)
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach(File::delete);
    }


    /**
     * getFileContents() - When given a compressed file, parse through and return an object containing the
     * filename, context-salience from MANIFEST, and an array of configuration file contents
     * @param file  The compressed file to read
     * @return  An object containing the compressed file contents and additional information
     * @throws IOException  Thrown when the compressed file does not exist or cannot be read
     */
    public static FileContentsHelper getFileContents(File file) throws IOException {
        String manifestFile = "META-INF/MANIFEST.MF";
        FileContentsHelper fileContents = new FileContentsHelper();

        ZipFile zipFile = new ZipFile(file);

        fileContents.setName(file.getName());

        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry configFile = entries.nextElement();
            fileContents.addContentsItem(new File(configFile.toString()).getName());
            if (configFile.getName().toUpperCase().equals(manifestFile)) {
                fileContents.setContextSalience(ConfigApiHelper.findContextSalienceInManifest(zipFile));
           }
        }
        zipFile.stream().close();
        zipFile.close();

        return fileContents;
    }

    /**
     * findContextSalienceInManifest() - When given a compressed file, parse its MANIFEST and return the
     * Context-Salience value if it exists
     * @param zipFile   The compressed file containing the MANIFEST file to parse
     * @return  The Context-Salience value retrieved from the MANIFEST file
     * @throws IOException  Thrown when the provided compressed file does not exist or cannot be read
     */
    private static String findContextSalienceInManifest(ZipFile zipFile) throws IOException {
        String contextSalience;

        JarFile jar = new JarFile(zipFile.getName());
        Manifest manifest = jar.getManifest();
        contextSalience = manifest.getMainAttributes().getValue("Context-Salience");
        log.debug("ConfigApi received the following Context-Salience from MANIFEST: " + contextSalience);

        return contextSalience;
    }

  /**
   * getMetaInfoResource - Retrieves resource files from the META-INF directory within a configuration archive
   * @param file    The configuration archive file
   * @param resolver    The resource pattern resolver
   * @param manager The current manager containing the repository to inject resources into
   * @return    The resource file retrieved from META-INF
   */
    private static Resource getMetaInfResource(File file, ResourcePatternResolver resolver, IManager manager) {
        return resolver.getResource("file:" + file.getAbsolutePath() +
                "/META-INF/" + manager.getResourceFileName());
    }
}
