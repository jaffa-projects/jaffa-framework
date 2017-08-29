package org.jaffa.rules.jbossaop;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.jaffa.rules.JaffaRulesFrameworkException;

import javax.servlet.http.HttpServlet;
import java.util.Arrays;
import java.util.List;

import static org.jaffa.rules.commons.AopConstants.DEFAULT_AOP_PATTERN;

/**
 * An implementation of a Servlet that serves as a Bootstrap to load the AOP Xml files upon container startup.
 * <p>
 * Note that it is recommended to use the spring loader as the AOP files will be loaded earlier on in the startup
 * process, however if servlet support is desired over spring config support, make sure that the property
 * "jaffa.aop.springconfig.disabled" is set to true. Having both paths enabled will cause XML files to be submitted
 * TWICE to the repositories which may or may not support this.
 */
public class AopLoaderBootstrapServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AopLoaderBootstrapServlet.class);

    /**
     * Servlet constructor used to bootstrap the XML load process.
     */
    public AopLoaderBootstrapServlet() {
        String jbossPath = System.getProperty("jboss.aop.path");

        if (Strings.isNullOrEmpty(jbossPath)) {
            jbossPath = DEFAULT_AOP_PATTERN;
        }

        List<String> paths = Arrays.asList(jbossPath.split(";"));

        try {
            new AopXmlLoader(paths);
        } catch (JaffaRulesFrameworkException e) {
            logger.error("An error occurred while attempting to create the AopXmlLoader instance. AOP files may not have been loaded into the appropriate repositories", e);
        }

    }
}
