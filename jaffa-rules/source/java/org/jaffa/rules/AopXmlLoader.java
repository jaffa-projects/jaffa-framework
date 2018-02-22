package org.jaffa.rules;

import org.apache.log4j.Logger;
import org.jaffa.rules.commons.AbstractLoader;
import org.jaffa.rules.commons.AopConstants;
import org.jaffa.util.ContextHelper;
import org.jaffa.util.FileHelper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Recursively identifies and attempts to load Aop XML files from a given path. The loaded
 * identified files are lightly validated as JBoss style AOP XmlDocuments. Each element is
 * provided to the known Aop Repositories which are them responsible for populating based upon
 * their own pattern matching.
 * <p>
 * The Path to find files expects an ant style pattern that can specify resources (classpath)
 * or local file directories. See {link http://docs.spring.io/spring/docs/2.5.x/javadoc-api/org/springframework/core/io/support/PathMatchingResourcePatternResolver.html}
 * for pattern details.
 */
public class AopXmlLoader {
    private static final Logger logger = Logger.getLogger(AopXmlLoader.class);
    private DocumentBuilder builder;
    private PathMatchingResourcePatternResolver resolver;
    private Map<XPathExpression, AbstractLoader> repoMap;
    private static AopXmlLoader aopXmlLoader = new AopXmlLoader();

    /**
     * Made AopXmlLoader Singleton instance by default
     */
    private AopXmlLoader() {

    }

    /**
     * Loads AOP.xml files from the given path. When an invalid file is found, it will be rejected
     * and logged.
     * <p>
     * Its important to make sure that the path that is provided ONLY contains aop.xml files. This method will attempt
     * to load each file regardless of size, which can cause the system to run out of resources.
     *
     * @param aopPaths A list of paths that should be searched for AOP files and loaded into the Repositories
     */
    public void processAopPaths(List<String> aopPaths) throws JaffaRulesFrameworkException {
        resolver = new PathMatchingResourcePatternResolver();

        try {
            builder = AopConstants.createParser();
        } catch (ParserConfigurationException e) {
            throw new JaffaRulesFrameworkException("Unable to create a parser for XmlDocuments. Please verify your classpath is correct.", null, e);
        }

        try {
            repoMap = AopConstants.createRepoMap();
        } catch (XPathExpressionException e) {
            throw new JaffaRulesFrameworkException("Unable to compile AOP XPath Statements. This is most likely a jar version conflict.", null, e);
        }

        for (String path : aopPaths) {
            try {
                processAopPath(path);
            } catch (IOException e) {
                throw new JaffaRulesFrameworkException("Error loading file the AOP path: " + path, null, e);
            }
        }
    }

    /**
     * Given a string representation of the location of aop file(s), will attempt to find matches and load them into the
     * repositories.  The path can be a fixed file path, a folder wich will be recursively searched, or an ant style
     * match pattern for embedded resources.
     *
     * @param path The path to search for aop xml files
     * @throws IOException When a file or resource cannot be directly loaded.
     */
    public void processAopPath(String path) throws IOException {
        // If it is a file or folder, don't bother with the matcher
        File resourcePath = new File(path);

        if (resourcePath.exists() && resourcePath.isFile()) {
            loadStream(new FileInputStream(resourcePath), resourcePath.toURI().toString());
        } else if (resourcePath.exists() && resourcePath.isDirectory()) {
            loadDirectory(resourcePath);
        } else {

            logger.info("Resolving AOP resources using path: " + path);
            // See if the matcher can resolve classpath related paths.
            Resource[] matches = resolver.getResources(path);
            List<ResourceWrapper> resourceWrapperList = new ArrayList<>();
            for (Resource match : matches) {
                resourceWrapperList.add(new ResourceWrapper(match));
            }
            resourceWrapperList.sort(Comparator.comparing(ResourceWrapper::getAopResourcePath));
            if (logger.isDebugEnabled()) {
                logger.debug("Found " + matches.length + " AOP Resource matches.");
            }

            for (ResourceWrapper match : resourceWrapperList) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Attempting to load " + match.toString());
                }
                if (match.getResource() != null) {
                    loadStream(match.getResource().getInputStream(), match.getResource().getURI().toString());
                }
            }

            logger.info("Finished loading resources with path: " + path);
        }
    }

    /**
     * Recursively finds files within a folder path and attempts to load the files into the repositories.
     *
     * @param folder The folder to recursively search
     * @throws FileNotFoundException Thrown when an invalid path is provided
     */
    private void loadDirectory(File folder) throws FileNotFoundException {
        File[] children = folder.listFiles();

        if (children == null) {
            return;
        }

        // alphabetical sort
        FileHelper.sortFiles(children);

        for (File child : children) {
            if (child.isDirectory()) {
                loadDirectory(child);
            } else if (child.getName().endsWith("-aop.xml")) {
                loadStream(new FileInputStream(child), child.toURI().toString());
            }
        }
    }

    /**
     * Attempts to load a given stream into the repositories.  Will perform light validation against the document to
     * see if it appears to be a JBoss AOP xml file.
     *
     * @param aopFileStream An inputStream for the resource that contains the aop content
     * @param reportedPath  A friendly name for the resource for reporting purposes
     */
    private void loadStream(InputStream aopFileStream, String reportedPath) {
        try {

            // attempt to load the stream as a document
            Document aopDocument = builder.parse(aopFileStream);

            boolean foundAtLeastOne = false;
            // loop through the repomaps and assign each element

            if (logger.isDebugEnabled()) {
                logger.debug("Processing AOP XML resourcepath " + reportedPath);
            }

            for (XPathExpression xPathStatement : repoMap.keySet()) {
                AbstractLoader repo = repoMap.get(xPathStatement);

                // pull out the elements that we are looking for based upon
                // the xpath to repo map
                NodeList nodes = (NodeList) xPathStatement.evaluate(aopDocument, XPathConstants.NODESET);

                if (logger.isDebugEnabled()) {
                    logger.debug("Found (" + nodes.getLength() + ") elements for repo " + repo.getClass().getName());
                }

                // add each element to the appropriate repo
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node current = nodes.item(i);
                    repo.load((Element) current, reportedPath);
                    foundAtLeastOne = true;
                }
            }

            if (!foundAtLeastOne && logger.isDebugEnabled()) {
                logger.debug("Could not find any valid elements in file " + reportedPath);
            }
        } catch (XPathExpressionException | JaffaRulesFrameworkException | IOException | SAXException e) {
            logger.error("An Error occurred while attempting to load an AOP resource from:" + reportedPath);
        }
    }


    /**
     * Attempts to unload a given stream into the repositories.  Will perform light validation against the document to
     * see if it appears to be a JBoss AOP xml file.
     *
     * @param reportedPath A friendly name for the resource for reporting purposes
     */
    public void unloadAop(String reportedPath) {
        try {

            if (logger.isDebugEnabled()) {
                logger.debug("Processing AOP XML resourcepath " + reportedPath);
            }

            File resourcePath = new File(reportedPath);

            if (resourcePath.exists() && resourcePath.isDirectory()) {
                unloadDirectory(resourcePath);
            } else {
                for (XPathExpression xPathStatement : repoMap.keySet()) {
                    AbstractLoader repo = repoMap.get(xPathStatement);

                    repo.unload(reportedPath);
                    if (logger.isDebugEnabled()) {
                        logger.debug("unloading Repo " + reportedPath);
                    }
                }
            }
        } catch (JaffaRulesFrameworkException | IOException e) {
            logger.error("An Error occurred while attempting to unload an AOP resource from:" + reportedPath);
        }
    }

    /**
     * Recursively finds files within a folder path and attempts to load the files into the repositories.
     *
     * @param folder The folder to recursively search
     * @throws FileNotFoundException Thrown when an invalid path is provided
     */
    private void unloadDirectory(File folder) throws FileNotFoundException {
        File[] children = folder.listFiles();

        if (children == null) {
            return;
        }

        // alphabetical sort
        FileHelper.sortFiles(children);

        for (File child : children) {
            if (child.isDirectory()) {
                unloadDirectory(child);
            } else if (child.getName().endsWith("-aop.xml")) {
                unloadAop(child.toURI().toString());
            }
        }
    }


    public static AopXmlLoader getInstance() {
        return aopXmlLoader;
    }
}

/**
 * Wrapper class for Spring Resource which is used to sort aop xml files alphabetically
 */
class ResourceWrapper {

    private static final Logger logger = Logger.getLogger(ResourceWrapper.class);

    public ResourceWrapper(Resource resource) {
        this.resource = resource;
    }

    /**
     * holds Spring Resource
     **/
    private Resource resource;

    /**
     * Getter for Resource
     *
     * @return
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Setter for Resource
     *
     * @param resource
     */
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    /**
     * Returns resource path of each resource in context with aop
     *
     * @return
     */
    public String getAopResourcePath() {
        try {
            return getResource() != null && getResource().getURL() != null
                    && getResource().getURL().toString() != null
                    ? ContextHelper.getContextSalience(getResource().getURI().toString()) + File.separator + getResource().getURL().toString().substring(getResource().getURL().toString().indexOf("aop")) : null;
        } catch (IOException e) {
            logger.error("Error while accessing aop xml", e);
        }
        return null;
    }
}
