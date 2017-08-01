package org.jaffa.util;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;


/**
 * Created by pbagirthi on 7/27/2017.
 */
public class ContextHelper {

    public static final String CONTEXT_SALIENCE = "Context-Salience";

    public static final String META_INF_MANIFEST_FILE = "/META-INF/MANIFEST.MF";

    private static Logger logger = Logger.getLogger(ContextHelper.class);

    public static String getDefaultContext(String contextPath) throws IOException {
        String defaultContext = "100-Highest";

        if (!contextPath.startsWith("jar")) {
            // Class not from JAR
            logger.warn("The Context Path is not frm the JAR");
        } else {
            String manifestPath = contextPath.substring(0, contextPath.lastIndexOf("!") + 1) + META_INF_MANIFEST_FILE;
            if (logger.isDebugEnabled())
                logger.debug("manifestPath={}" + manifestPath);
            defaultContext = getDefaultContextFromManifest(manifestPath);
        }

        return defaultContext;
    }

    public static String getDefaultContextFromManifest(String manifestPath) throws IOException {
        Manifest manifest = new Manifest(new URL(manifestPath).openStream());

        return getContextSalience(manifest);
    }

    public static String getDefaultContextFromManifest(InputStream inputStream) throws IOException {
        Manifest mf = new Manifest();
        //mf.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("META-INF/MANIFEST.MF"));
        mf.read(inputStream);

        return getContextSalience(mf);
    }

    public static String getContextSalience(Manifest manifest) {
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(CONTEXT_SALIENCE);
    }
}
