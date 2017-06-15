package org.jaffa.struts.tiles;


import org.apache.struts.tiles.DefinitionsFactoryException;
import org.apache.struts.tiles.xmlDefinition.DefinitionsFactory;
import org.apache.struts.tiles.xmlDefinition.XmlDefinitionsSet;
import org.apache.struts.tiles.xmlDefinition.XmlParser;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


/**
 * Custom 'definitions-factory-class' class for reading definitions from classpath.
 * Include the following tag in struts-config.xml to modify definitions-factory-class
 *
 *   <plug-in className="org.apache.struts.tiles.TilesPlugin" >
 *      <set-property property="definitions-factory-class" value="org.jaffa.struts.tiles.JaffaI18nFactorySet" />
 *    </plug-in>
 */
public class JaffaI18nFactorySet extends org.apache.struts.tiles.xmlDefinition.I18nFactorySet{

    /**
     * Names of files containing instances descriptions.
     */
    private List<String> filenames = null;

    protected DefinitionsFactory createDefaultFactory(ServletContext servletContext) throws DefinitionsFactoryException, FileNotFoundException {
        XmlDefinitionsSet rootXmlConfig = this.parseXmlFiles(servletContext, "", (XmlDefinitionsSet)null);
        if(rootXmlConfig == null) {
            throw new FileNotFoundException();
        } else {
            rootXmlConfig.resolveInheritances();
            if(log.isDebugEnabled()) {
                log.debug(rootXmlConfig);
            }

            DefinitionsFactory factory = new DefinitionsFactory(rootXmlConfig);
            if(log.isDebugEnabled()) {
                log.debug("factory loaded : " + factory);
            }

            return factory;
        }
    }

    /**
     * Initialization method.
     * Init the factory by reading appropriate configuration file.
     * This method is called exactly once immediately after factory creation in
     * case of internal creation (by DefinitionUtil).
     * @param servletContext Servlet Context passed to newly created factory.
     * @param proposedFilename File names, comma separated, to use as  base file names.
     * @throws DefinitionsFactoryException An error occur during initialization.
     */
    public void initFactory(
            ServletContext servletContext,
            String proposedFilename)
            throws DefinitionsFactoryException, FileNotFoundException {

        // Init list of filenames
        StringTokenizer tokenizer = new StringTokenizer(proposedFilename, ",");
        this.filenames = new ArrayList(tokenizer.countTokens());
        while (tokenizer.hasMoreTokens()) {
            this.filenames.add(tokenizer.nextToken().trim());
        }
        super.initFactory(servletContext, proposedFilename);
    }

    /**
     * Parse files associated to postix if they exist.
     * For each name in filenames, append postfix before file extension,
     * then try to load the corresponding file.
     * If file doesn't exist, try next one. Each file description is added to
     * the XmlDefinitionsSet description.
     * The XmlDefinitionsSet description is created only if there is a definition file.
     * Inheritance is not resolved in the returned XmlDefinitionsSet.
     * If no description file can be opened and no definiion set is provided, return <code>null</code>.
     * @param postfix Postfix to add to each description file.
     * @param xmlDefinitions Definitions set to which definitions will be added. If <code>null</code>, a definitions
     * set is created on request.
     * @return XmlDefinitionsSet The definitions set created or passed as parameter.
     * @throws DefinitionsFactoryException On errors parsing file.
     */
    protected XmlDefinitionsSet parseXmlFiles(
            ServletContext servletContext,
            String postfix,
            XmlDefinitionsSet xmlDefinitions)
            throws DefinitionsFactoryException {
        return parseXmlFile(servletContext, xmlDefinitions);
    }

    /**
     * Parse specified xml file and find definitions from with in classpath by searching all jars
     * and add definition to specified definitions set.
     * This method is used to load several description files in one instances list.
     * If filename exists and definition set is <code>null</code>, create a new set. Otherwise, return
     * passed definition set (can be <code>null</code>).
     * @param servletContext Current servlet context. Used to open file.
     * @param xmlDefinitions Definitions set to which definitions will be added. If null, a definitions
     * set is created on request.
     * @return XmlDefinitionsSet The definitions set created or passed as parameter.
     * @throws DefinitionsFactoryException On errors parsing file.
     */

    protected XmlDefinitionsSet parseXmlFile(
            ServletContext servletContext,
            XmlDefinitionsSet xmlDefinitions)
            throws DefinitionsFactoryException {

        if(log.isDebugEnabled()){
            log.debug("Parsing tiles definition files from classpath");
        }
        try {
            List<Resource> resourceList = new ArrayList<>();
            for(String filename : filenames) {
                PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                resourceList.addAll(Arrays.asList(resolver.getResources(filename)));
            }
            // Check if parser already exist.
            // Doesn't seem to work yet.
            //if( xmlParser == null )
            if (true) {
                xmlParser = new XmlParser();
                xmlParser.setValidating(isValidatingParser);
            }

            // Check if definition set already exist.
            if (xmlDefinitions == null) {
                xmlDefinitions = new XmlDefinitionsSet();
            }

            if(resourceList!=null && resourceList.size() > 0){
                for(Resource resource : resourceList) {
                    if(log.isDebugEnabled()){
                        log.debug("Parsing tiles definition: "+resource.getFilename());
                    }
                    xmlParser.parse(resource.getInputStream(), xmlDefinitions);
                }
            }else{
                if (log.isDebugEnabled()) {
                    log.debug("Can't open files '" + filenames + "'");
                }
                return xmlDefinitions;
            }

        } catch (SAXException | IOException ex) {
            if (log.isDebugEnabled()) {
                log.debug("Error while parsing files '" + filenames + "'.");
                ex.printStackTrace();
            }
            throw new DefinitionsFactoryException(
                    "Error while parsing files '" + filenames + "'. " + ex.getMessage(),
                    ex);

        }

        return xmlDefinitions;
    }
}
