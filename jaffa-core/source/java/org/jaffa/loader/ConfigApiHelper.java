package org.jaffa.loader;

import org.apache.log4j.Logger;
import org.jaffa.util.ContextHelper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Static helper methods to perform functions for the ConfigApi endpoint methods
 */
public class ConfigApiHelper {
    private static final Logger log = Logger.getLogger(ConfigApiHelper.class);

    /**
     * findContextSalienceInManifest() - When given a compressed file, parse its MANIFEST and return the
     * Context-Salience value if it exists
     * @param zipFile   The compressed file containing the MANIFEST file to parse
     * @return  The Context-Salience value retrieved from the MANIFEST file
     * @throws IOException  Thrown when the provided compressed file does not exist or cannot be read
     */
    public static String findContextSalienceInManifest(ZipFile zipFile) throws IOException {
        String contextSalience;

        JarFile jar = new JarFile(zipFile.getName());
        Manifest manifest = jar.getManifest();
        contextSalience = manifest.getMainAttributes().getValue("Context-Salience");
        log.debug("ConfigApi received the following Context-Salience from MANIFEST: " + contextSalience);

        return contextSalience;
    }


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
        // TODO: Fix harcoded '4' - why 4? Probably should use utility method that searches backwards for '.'.
        File tempDir = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4));
        tempDir.mkdir();

        //Extract compressed contents to temporary directory
        ZipInputStream zis = new ZipInputStream(new FileInputStream(file.getAbsolutePath()));
        ZipEntry zipEntry = zis.getNextEntry();
        // TODO: Fix magic number.
        byte[] buffer = new byte[1024];
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
     * Register configuration files in IManager implementationss, based on the source of the method call
     * @param file  The configuration file to be registered or unregistered
     * @return  Success or failure of the operation
     */
    public static boolean registerResources(File file) {
        boolean isSuccess = true;
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        for(IManager manager : ManagerRepositoryService.getInstance().getManagerMap().values()) {
            Resource resource = getMetaInfResource(file, resolver, manager);
            try {
                if (resource.getFile().exists()) {
                    manager.registerResource(resource, ContextHelper.getContextSalience(resource.getURI().toString()),
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
    public static boolean unregisterResources(File file) {
        boolean isSuccess = true;
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        for(IManager manager : ManagerRepositoryService.getInstance().getManagerMap().values()) {
            Resource resource = getMetaInfResource(file, resolver, manager);
            try {
                if (resource.getFile().exists()) {
                    manager.unregisterResource(resource, ContextHelper.getContextSalience(resource.getURI().toString()),
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
     * After resources have been registered or unregistered, remove the temporary directory containing the extracted
     * ZIP entries
     * @param directory The directory containing the extracted compressed file entries
     * @throws IOException  Thrown when the directory does not exist or cannot be read
     */
    public static boolean removeExtractedFiles(File directory) throws IOException {
        File metaInfDirectory = new File(directory + "/META-INF/");
        File[] children = metaInfDirectory.listFiles();
        if (children != null) {
            for (int i = 0; i < children.length; i++) {
                Files.deleteIfExists(children[i].toPath());
            }
            if (!Files.deleteIfExists(metaInfDirectory.toPath())) {
                log.warn("The temporary files were not successfully removed from " + metaInfDirectory);
            }
            if (!Files.deleteIfExists(directory.toPath())) {
                log.warn("The temporary files were not successfully removed from " + directory);
            }
        }
        return directory.exists();
    }

    /**
     * Remove a directory recursively ("rm -rf").
     * @param dir  Directory to be removed.
     * @throws IOException
     */
    // TODO: Move this to a utility class. Apache commons has this functionality, but the Java 8 version is succinct.
    public static void removeDirTree(File dir) throws IOException {

        Files.walk(dir.toPath())
                .map(Path::toFile)
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach(File::delete);
    }

    // Helper methods...

    private static Resource getMetaInfResource(File file, ResourcePatternResolver resolver, IManager manager) {
        return resolver.getResource("file:" + file.getAbsolutePath() +
                "/META-INF/" + manager.getResourceFileName());
    }
}
