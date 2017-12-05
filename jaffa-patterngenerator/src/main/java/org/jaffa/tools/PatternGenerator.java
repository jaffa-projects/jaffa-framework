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

package org.jaffa.tools;

import org.jaffa.datatypes.DateTime;
import org.jaffa.tools.common.SourceDecomposerException;
import org.jaffa.tools.common.SourceDecomposerUtils;
import org.jaffa.util.DefaultEntityResolver;
import org.jaffa.util.DefaultErrorHandler;
import org.jaffa.util.URLHelper;
import org.jaffa.util.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.webmacro.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * Use this class to perform code generation based on the input pattern meta data file.
 */
public class PatternGenerator {

    /** The default directory containing the properties files */
    private static final String DEFAULT_PROPERTIES_DIRECTORY = "";
            // TODO - put somewhere besides main/java?, e.g., "../wm/patterns";

    /** The default property file for configuring this class. */
    private static final String PATTERN_GENERATOR_PROPERTIES_FILE =
            DEFAULT_PROPERTIES_DIRECTORY + "PatternGenerator.properties";

    /** The default property file for configuring the WebMacro WM class. */
    private static final String WEB_MACRO_PROPERTIES_FILE =
            DEFAULT_PROPERTIES_DIRECTORY + "WebMacro.properties";

    /** The file containing audit information */
    private static final String AUDIT_LOG_FILE = "audit.log";

    // PatternGenerator properties

    /** The property key for finding the root directory for writing general files. */
    static final String PROPERTY_PROJECT_ROOT_DIRECTORY = "ProjectRootDirectory";

    /** The property key for finding the root directory for writing Java files. */
    static final String PROPERTY_JAVA_ROOT_DIRECTORY = "JavaRootDirectory";

    /** The property key for finding the root directory for writing SQL files. */
    static final String PROPERTY_SQL_ROOT_DIRECTORY = "SqlRootDirectory";

    static final String PROPERTY_TEMP_META_DIRECTORY = "TempMetaDataDirectory";
    static final String PROPERTY_LOG_DIRECTORY = "LogDirectory";

    // XML elements referenced

    private static final String XML_PATTERN_TEMPLATE = "PatternTemplate";
    private static final String XML_DATE_TIME = "DateTime";
    private static final String XML_PREREQUESITES = "PreRequesites";
    private static final String XML_COMPONENTS = "Components";
    private static final String XML_FILE_NAME = "FileName";
    private static final String XML_PACKAGE = "Package";
    private static final String XML_TEMPLATE = "Template";
    private static final String XML_OVERRIDE = "Override";
    private static final String XML_GENERATE = "Generate";
    private static final String XML_GENERATE_TYPE = "Type";
    private static final String XML_GENERATE_WORKING_DIR = "WorkingDirectory";
    private static final String XML_GENERATE_COMMAND_LINE = "CommandLine";
    private static final String XML_GENERATE_CLASS_NAME = "ClassName";
    private static final String XML_GENERATE_ARGUMENTS = "Arguments";
    private static final String XML_SCRATCHPAD_FOR_WM = "ScratchPad";

    // fields
    /** The URL of a file that specifies object-relational mapping metadata. */
    private URL m_patternMetaData = null;

    /** The properties used to configure the PatternGenerator. */
    Properties m_pgProperties = null;

    private String m_auditFile = null;
    private WebMacro m_wm = null;

    /** maps domain models to database table names */
    private Map<String, String> tableNames = new HashMap<>();

    /** map field names to column names, map that to the domain models the
     *  fields are in */
    private Map<String, HashMap<String, String>> columnNames = new HashMap<>();

    /**
     * Creates new PatternGenerator.
     * @param patternMetaData the pattern meta data file to be used for code
     *                        generation.
     * @throws PatternGeneratorException if any error occurs.
     */
    public PatternGenerator(URL patternMetaData)
            throws PatternGeneratorException {
        this(patternMetaData, new Properties());
    }

    /**
     * Creates new PatternGenerator.
     * @param patternMetaData the pattern meta data file to be used for code
     *                        generation.
     * @param initialProperties properties to use to help initialize
     * @throws PatternGeneratorException if any error occurs.
     */
    public PatternGenerator(URL patternMetaData, Properties initialProperties)
            throws PatternGeneratorException {
        try {
            m_patternMetaData = patternMetaData;
            initializePatternGeneratorProperties(initialProperties);

            // set the field - m_auditFile
            String logDir = m_pgProperties.getProperty(PROPERTY_LOG_DIRECTORY);
            if (logDir != null && !logDir.equals("")) {
                m_auditFile = getAbsoluteFileName(logDir, AUDIT_LOG_FILE);
            }

            // Startup WebMacro
            m_wm = new WM(WEB_MACRO_PROPERTIES_FILE);
        } catch (PatternGeneratorException e) {
            throw e;
        } catch (Exception e) {
            throw new PatternGeneratorException(e);
        }
    }

    /**
     * Initialize the properties for the pattern generator by overlaying
     * calling code specified properties over file-specified properties over
     * default properties.
     * @param initialProperties
     * @throws IOException
     * @throws PatternGeneratorException
     */
    private void initializePatternGeneratorProperties(
            Properties initialProperties)
            throws IOException, PatternGeneratorException {
        setDefaultProperties();
        // Add properties from a properties file to m_pgProperties
        try {
            URL pgUrl = URLHelper.newExtendedURL(PATTERN_GENERATOR_PROPERTIES_FILE);
            Properties readProperties = getProperties(pgUrl);
            m_pgProperties.putAll(readProperties);
        } catch (MalformedURLException e) {
            System.out.println("Unable to locate " +
                               PATTERN_GENERATOR_PROPERTIES_FILE +
                               ".  Using default properties.");
            // If we can't find the properties file, we'll just use the user
            // provided properties over the top of default values
        }

        // Add properties provided by the calling code
        if (initialProperties != null) {
            m_pgProperties.putAll(initialProperties);
        }
        checkNecessaryProperties();

    }

    /**
     * Make sure the necessary properties are set.  If not, throw an exception
     * @throws PatternGeneratorException
     */
    private void checkNecessaryProperties() throws PatternGeneratorException {
        String message = null;
        if (m_pgProperties.getProperty(PROPERTY_PROJECT_ROOT_DIRECTORY) == null) {
            if (m_pgProperties.getProperty(PROPERTY_JAVA_ROOT_DIRECTORY) == null
                && m_pgProperties.getProperty(PROPERTY_SQL_ROOT_DIRECTORY) != null) {
                message = String.format(
                        "Either the %s or the %s property must be set.",
                        PROPERTY_PROJECT_ROOT_DIRECTORY,
                        PROPERTY_JAVA_ROOT_DIRECTORY);
            } else if (m_pgProperties.getProperty(PROPERTY_JAVA_ROOT_DIRECTORY) != null
                       && m_pgProperties.getProperty(PROPERTY_SQL_ROOT_DIRECTORY) == null) {
                message = String.format(
                        "Either the %s or the %s property must be set.",
                        PROPERTY_PROJECT_ROOT_DIRECTORY,
                        PROPERTY_SQL_ROOT_DIRECTORY);
            } else if (m_pgProperties.getProperty(PROPERTY_JAVA_ROOT_DIRECTORY) == null
                       && m_pgProperties.getProperty(PROPERTY_SQL_ROOT_DIRECTORY) == null) {
                message = String.format(
                        "Either the %s or both the %s and %s properties must be set.",
                        PROPERTY_PROJECT_ROOT_DIRECTORY,
                        PROPERTY_SQL_ROOT_DIRECTORY,
                        PROPERTY_JAVA_ROOT_DIRECTORY);
            }
        }
        if (message != null) {
            throw new PatternGeneratorException(message);
        }
    }

    /**
     * Set certain properties used by the pattern generator to default values
     */
    private void setDefaultProperties() {
        m_pgProperties = new Properties();
        String tempDir = System.getProperty("java.io.tmpdir");
        m_pgProperties.put(PROPERTY_TEMP_META_DIRECTORY, tempDir);
        m_pgProperties.put(PROPERTY_LOG_DIRECTORY, tempDir);
    }

    /**
     * This method is to be invoked, when done with the code generation.
     * It will free up the resources.
     */
    public void destroy() {
        if (m_wm != null) {
            m_wm.destroy();
            m_wm = null;
        }

        if (m_pgProperties != null) {
            m_pgProperties.clear();
            m_pgProperties = null;
        }

        if (tableNames != null) {
            tableNames.clear();
            tableNames = null;
        }

        if (columnNames != null) {
            columnNames.clear();
            columnNames = null;
        }
    }

    /**
     * Creates a mapping of data needed during code generation.  All domain
     * models passed in will be mapped
     * to their table names.  All fields for each domain model will be mapped
     * to their column names.
     *
     * @param dataSet the set of xml files to create the data mapping from
     */
    public void setData(List<File> dataSet)
            throws IOException, PatternGeneratorException, SAXException,
                   ParserConfigurationException {
        for (File file : dataSet) {

            // get the root object
            URI uri = file.toURI();
            URL url = uri.toURL();
            DatabaseTableInformation tableData = extractModelData(url);

            // get all values in upper case
            String domainObject = tableData.getDomainModelName().toUpperCase();
            String tableName = tableData.getTableName().toUpperCase();
            tableNames.put(domainObject, tableName);

            // add each field column in this domain object
            if (!columnNames.containsKey(domainObject)) {
                columnNames.put(domainObject, new HashMap<String, String>());
            }
            for (Map.Entry<String, String> entry : tableData.getFieldToColumnName().entrySet()) {
                String fieldName = entry.getKey().toUpperCase();
                String columnName = entry.getValue().toUpperCase();
                columnNames.get(domainObject).put(fieldName, columnName);
            }
        }
    }

    /**
     * Gets the table name for the input model.
     * This can be invoked from a WebMacro pattern with the following:
     * <p/>
     * #$(PatternGenerator.getTableName("modelName"))
     *
     * @param modelName the input model to get the table name for
     * @return the database table name for the input model
     */
    public String getTableName(String modelName) {
        if (!tableNames.containsKey(modelName)) {
            return "";
        }
        return tableNames.get(modelName);
    }

    /**
     * Gets the column name for the input field.
     * This can be invoked from a WebMacro pattern with the following:
     * <p/>
     * #$(PatternGenerator.getColumnName("modelName", "fieldName"))
     *
     * @param modelName the model the field is in
     * @param fieldName the field to get the column name of
     * @return the column name for the input field
     */
    public String getColumnName(String modelName, String fieldName) {
        if (!columnNames.containsKey(modelName) || !columnNames.get(modelName).containsKey(fieldName)) {
            return "";
        }
        return columnNames.get(modelName).get(fieldName);
    }

    /**
     * Get the properties from the file at the indicated URL
     * @param url
     * @return
     * @throws IOException
     * @throws PatternGeneratorException
     */
    private Properties getProperties(URL url)
            throws IOException, PatternGeneratorException {
        Properties properties = new Properties();
        InputStream in = url.openStream();
        properties.load(in);
        in.close();
        if (properties.isEmpty()) {
            throw new PatternGeneratorException("Could not load properties: " + url);
        }
        return properties;
    }


    private String getAbsoluteFileName(String dirName, String fileName) {
        File file = new File(dirName, fileName);
        return file.getAbsolutePath();
    }


    /**
     * This will invoke the Pattern Generation routine.
     *
     * @throws PatternGeneratorException if any error occurs.
     */
    public void processPattern() throws PatternGeneratorException {
        try {
            // Step 1 - Get the patternTemplate from m_patternMetaData
            // Note: The XML should not be validated.. since it may contain WebMacro script variables
            String patternTemplate = null;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            DocumentBuilder parser = factory.newDocumentBuilder();
            parser.setEntityResolver(new DefaultEntityResolver());
            parser.setErrorHandler(new DefaultErrorHandler());
            String metaDataFileName = m_patternMetaData.getFile();
            Document document = parser.parse(metaDataFileName);
            NodeList nodes = document.getElementsByTagName(XML_PATTERN_TEMPLATE);
            if (nodes != null && nodes.getLength() > 0) {
                Node node = nodes.item(0);
                patternTemplate = XmlHelper.getTextTrim(node);
            }
            if (patternTemplate == null || patternTemplate.equals("")) {
                throw new PatternGeneratorException("The " + XML_PATTERN_TEMPLATE + " element is not supplied in the input Meta Data File");
            }


            // Step2 - Generate the TemporaryMetaDataFile from patternTemplate + m_patternMetaData
            Context context = getContextWithData();
            String tempFileName = generateTempFile(patternTemplate, (new File(metaDataFileName)).getName(), context);

            // Step3 - Generate the source code files
            generateSourceFiles(tempFileName, context);

        } catch (PatternGeneratorException e) {
            throw e;
        } catch (Exception e) {
            throw new PatternGeneratorException(e);
        } finally {
            destroy();
        }
    }

    /**
     * Combines data from the metadata file to data from the WM's context,
     * and returns the combined context.
     * @return the combined context from WM and the metadata
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws PatternGeneratorException
     */
    private Context getContextWithData()
            throws ParserConfigurationException, SAXException,
                   IOException, PatternGeneratorException {
        // parse the metaData.. Force XML validation
        Map metaDataMap = DomParser.parse(m_patternMetaData.getFile(), true);

        // strip off the root element from the metaDataMap
        metaDataMap = stripRoot(metaDataMap);
        if (metaDataMap == null) {
            throw new PatternGeneratorException("PatternMetaData file " + m_patternMetaData.getFile() + " is incorrectly formatted");
        }

        // copy metaDataMap to context
        Context context = m_wm.getContext();
        for (Object entries : metaDataMap.entrySet()) {
            Map.Entry entry = (Map.Entry) entries;
            context.put(entry.getKey(), entry.getValue());
        }

        // Add some static data to the context
        context.put(XML_DATE_TIME, new DateTime());
        context.put(PROPERTY_PROJECT_ROOT_DIRECTORY,
                    m_pgProperties.getProperty(PROPERTY_PROJECT_ROOT_DIRECTORY));
        context.put(PROPERTY_JAVA_ROOT_DIRECTORY,
                    m_pgProperties.getProperty(PROPERTY_JAVA_ROOT_DIRECTORY));
        context.put(PROPERTY_SQL_ROOT_DIRECTORY,
                    m_pgProperties.getProperty(PROPERTY_SQL_ROOT_DIRECTORY));

        // This is used by the WM template to put temporary data in a scratchpad Map...
        context.put(XML_SCRATCHPAD_FOR_WM, new HashMap());

        // put an instance of this class into the context so methods can be
        // called to get table and column names
        context.put("PatternGenerator", this);
        return context;
    }

    private Map stripRoot(Map input) {
        Map output = null;
        Iterator itr = input.values().iterator();
        if (itr.hasNext()) {
            Object obj = itr.next();
            if (obj instanceof Map) {
                output = (Map) obj;
            }
        }
        return output;
    }

    // Generates tempFileName
    // Modifies the input object context !!!
    private String generateTempFile(String patternTemplate, String patternMetaData, Context context)
            throws IOException, WebMacroException {
        // write the Output to the temp folder using WebMacro
        String tempFileName = getAbsoluteFileName(m_pgProperties.getProperty(PROPERTY_TEMP_META_DIRECTORY), patternMetaData);
        writeWm(patternTemplate, context, tempFileName);

        // append to the audit.log
        String log = '\n' + new DateTime().toString() + " - Processing PatternTemplate: " +
                patternTemplate + " using PatternMetaData: " + patternMetaData + '\n' +
                "Created temporary file: " + tempFileName;
        writeMessage(m_auditFile, log, true);

        return tempFileName;
    }

    /**
     * Create source files based on the contents of an XML file
     * @param tempFileName the temporary file to be parsed, whose contents will
     *                     determine which files get created.
     * @param context contains information needed for processing, including
     *                information from WebMacro.properties and PatternGenerator
     *                properties.
     * @throws PatternGeneratorException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws WebMacroException
     * @throws SourceDecomposerException
     */
    private void generateSourceFiles(String tempFileName, Context context)
            throws PatternGeneratorException, ParserConfigurationException,
                   SAXException, IOException, WebMacroException,
                   SourceDecomposerException {
        // parse the tempFileName.. Force XML validation
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder parser = factory.newDocumentBuilder();
        parser.setEntityResolver(new DefaultEntityResolver());
        parser.setErrorHandler(new DefaultErrorHandler());
        Document document = parser.parse(tempFileName);

        // check for Prerequisites
        // @todo : this currently only supports java files..
        // Need to enhance this logic to support other files too !!
        String preReqErrLog = null;
        Node preReq = document.getElementsByTagName(XML_PREREQUESITES).item(0);
        NodeList classes = preReq.getChildNodes();
        if (classes != null) {
            for (int i = 0; i < classes.getLength(); i++) {
                Node clazz = classes.item(i);
                if (clazz.getNodeType() == Node.ELEMENT_NODE) {
                    String requiredClassName = XmlHelper.getTextTrim(clazz);
                    try {
                        Class.forName(requiredClassName);
                    } catch (ClassNotFoundException e) {
                        if (preReqErrLog == null) {
                            preReqErrLog = "";
                        }
                        preReqErrLog += "Error: PreRequesite Class " +
                                        requiredClassName + " not found\n";
                    }
                }
            }
        }
        if (preReqErrLog != null) {
            writeMessage(m_auditFile, preReqErrLog, true);
            throw new PatternGeneratorException(preReqErrLog);
        }
        processComponents(context, document);
    }

    /**
     * Loop through each component of the pattern metadata file, writing
     * the specified build files.
     * @param context contains information needed for processing, including
     *                information from WebMacro.properties and PatternGenerator
     *                properties.
     * @param document the XML document being processed
     * @throws WebMacroException
     * @throws IOException
     * @throws PatternGeneratorException
     * @throws SourceDecomposerException
     */
    private void processComponents(Context context, Document document)
            throws WebMacroException, IOException, PatternGeneratorException,
                   SourceDecomposerException {
        // loop through each Component
        Node components = document.getElementsByTagName(XML_COMPONENTS).item(0);
        NodeList buildNodes = components.getChildNodes();
        for (int i = 0; i < buildNodes.getLength(); i++) {
            Node buildNode = buildNodes.item(i);
            if (buildNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element build = processBuildNode(context, (Element) buildNode);
            if (build == null)
                continue;

            // Execute the Generate Command if any...
            processGenerate(build);
        }
    }

    /**
     * Get the data from the Build node, and write the file specified in
     * that node.
     * @param context contains information needed for processing, including
     *                information from WebMacro.properties and PatternGenerator
     *                properties.
     * @param buildNode the XML node containing information about building a
     *                  file.  Example Build node:
        <Build>
            <Template>patterns/library/domain_creator_1_1/CreateTable.wm</Template>
            <Package>sql/install/oracle/tables</Package>
            <FileName>SCHEDULED_QUANTITIES.sql</FileName>
            <Override>True</Override>
        </Build>
     * @return the build node
     * @throws WebMacroException
     * @throws IOException
     * @throws PatternGeneratorException
     * @throws SourceDecomposerException
     */
    private Element processBuildNode(Context context, Element buildNode)
            throws WebMacroException, IOException, PatternGeneratorException,
                   SourceDecomposerException {
        String fileNameInXml = XmlHelper.getTextTrim(
                        buildNode.getElementsByTagName(XML_FILE_NAME).item(0));
        String packageName = XmlHelper.getTextTrim(
                buildNode.getElementsByTagName(XML_PACKAGE).item(0));
        String templateName =
                XmlHelper.getTextTrim(
                        buildNode.getElementsByTagName(XML_TEMPLATE).item(0));
        String override = XmlHelper.getTextTrim(
                buildNode.getElementsByTagName(XML_OVERRIDE).item(0));
        context.put(XML_FILE_NAME, fileNameInXml);
        context.put(XML_PACKAGE, packageName);
        String fileName = determineFileToWrite(fileNameInXml, packageName);

        // check if it already exists
        File f = new File(fileName);
        boolean fileExists = f.exists() && f.isFile();

        if (!fileExists) {
            createFile(templateName, context, fileName);
            writeMessage(m_auditFile, "Created: " + fileName, true);
        } else {
            // ask the user what to do in case the file already exists
            if (override.equalsIgnoreCase("ask")) {
                override = askUser(fileName);
            }

            if (override.equalsIgnoreCase("no") || override.equalsIgnoreCase("false")) {
                writeMessage(m_auditFile, "Left untouched: " + fileName, true);
            } else if (override.equalsIgnoreCase("yes") || override.equalsIgnoreCase("true")) {
                createFile(templateName, context, fileName);
                writeMessage(m_auditFile, "Recreated: " + fileName, true);
            } else if (override.equalsIgnoreCase("merge")) {
                mergeFile(templateName, context, fileName);
                writeMessage(m_auditFile, "Recreated with existing customizations: " + fileName, true);
            } else if (override.equalsIgnoreCase("OverrideIfMarkerPresent")) {
                if (SourceDecomposerUtils.isJaffaOverwriteMarkerPresent(f)) {
                    // Override only if the marker is still present in the file.
                    // Absence of the marker indicates that the file has been customized
                    createFile(templateName, context, fileName);
                    writeMessage(m_auditFile, "Recreated: " + fileName, true);
                } else {
                    writeMessage(m_auditFile, "Left untouched: " + fileName, true);
                }
            } else if (override.equalsIgnoreCase("OverrideIfMarkerPresentOrCreateTempFileIfMarkerAbsent")) {
                if (SourceDecomposerUtils.isJaffaOverwriteMarkerPresent(f)) {
                    // Override only if the marker is still present in the file.
                    // Absence of the marker indicates that the file has been customized
                    createFile(templateName, context, fileName);
                    writeMessage(m_auditFile, "Recreated: " + fileName, true);
                } else {
                    // Leave the existing file as is, but create a temp file with a '.new' extension
                    String newFileName = fileName + ".new";
                    createFile(templateName, context, newFileName);
                    writeMessage(m_auditFile, "Created Temp File: " + newFileName, true);
                }
            } else {
                writeMessage(m_auditFile, "ERROR: Unknown override option '" + override + "' passed for file " + fileName + ". Nothing done", true);
                return null;
            }
        }
        return buildNode;
    }

    /**
     * Determine where to write the file, based on the type of the file, and the
     * provided file and package names
     * @param fileName the file name
     * @param packageName the package to contain the file
     * @return the location to be written
     */
    String determineFileToWrite(String fileName, String packageName) {
        String rootDir;
        String projectRootDir =
                m_pgProperties.getProperty(PROPERTY_PROJECT_ROOT_DIRECTORY);

        if (fileName.endsWith(".java")) {
            rootDir = m_pgProperties.getProperty(PROPERTY_JAVA_ROOT_DIRECTORY,
                                                 projectRootDir);
        } else if (fileName.endsWith(".properties")) {
            rootDir = m_pgProperties.getProperty(PROPERTY_JAVA_ROOT_DIRECTORY,
                                                 projectRootDir);
        } else if (fileName.endsWith(".sql")) {
            rootDir = m_pgProperties.getProperty(PROPERTY_SQL_ROOT_DIRECTORY,
                                                 projectRootDir);
        } else {
            rootDir = projectRootDir;
        }

        String dirName = getDirectoryName(rootDir, packageName);
        return dirName + File.separatorChar + fileName;
    }

    private String askUser(String fileName) throws IOException {
        System.out.println("File '" + fileName + "' already exists..");
        System.out.println("1 - Leave It Alone / 2 - Overwrite / 3 - Merge / 4 - OverrideIfMarkerPresent / 5 - OverrideIfMarkerPresentOrCreateTempFileIfMarkerAbsent ?");
        System.out.flush();

        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        label:
        while (true) {
            s = in.readLine();
            switch (s) {
            case "1":
                s = "false";
                break label;
            case "2":
                s = "true";
                break label;
            case "3":
                s = "merge";
                break label;
            case "4":
                s = "OverrideIfMarkerPresent";
                break label;
            case "5":
                s = "OverrideIfMarkerPresentOrCreateTempFileIfMarkerAbsent";
                break label;
            default:
                System.out.println("Enter either 1, 2, 3, 4 or 5");
                break;
            }
        }
        return s;
    }

    /**
     * Create a new file
     * @param templateName the WebMacro template
     * @param context Contextual values for use by the template
     * @param fileName the file to contain the results of applying the template
     * @throws WebMacroException
     * @throws IOException
     * @throws PatternGeneratorException
     */
    private void createFile(String templateName, Context context, String fileName)
            throws WebMacroException, IOException, PatternGeneratorException {
        // create the file
        writeWm(templateName, context, fileName);
        fixEOL(fileName);
    }

    private void mergeFile(String templateName, Context context, String fileName)
            throws WebMacroException, IOException, PatternGeneratorException, SourceDecomposerException {
        // get the new contents
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        writeWm(templateName, context, bos);
        String newContents = bos.toString();

        // merge the contents
        String mergedContents = SourceMerge.performMerge(
                new BufferedReader(new StringReader(newContents)),
                new BufferedReader(new FileReader(fileName)));

        // write to the file
        Writer writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(mergedContents);
        writer.flush();
        writer.close();

        fixEOL(fileName);
    }

    private void fixEOL(String fileName) throws FileNotFoundException, IOException {
        // fix EOL style, br should handle any eol's and then bw will write in the native eol        
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        // we want to know if the file is empty
        boolean isFileEmpty = true;
        while ((line = br.readLine()) != null) {

            // a blank line will not count as content here
            if (isFileEmpty && !line.isEmpty()) {
                isFileEmpty = false;
            }
            bw.write(line);
            bw.newLine();
        }
        br.close();
        bw.close();

        // if the file is empty, delete it
        if (isFileEmpty) {
            File file = new File(fileName);
            if (file.delete()) {
                writeMessage(m_auditFile, fileName + " is empty, and has been deleted.", true);
            }
        } else {
            bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(sw.toString());
            bw.close();
        }
    }

    private String getDirectoryName(String rootDir, String packageName) {
        // Remove the '.' from the package
        String packageNameToDir = packageName.replace('.', File.separatorChar);

        // Append the new package to the rootDir
        if (rootDir.endsWith(File.separator)) {
            rootDir = rootDir + packageNameToDir;
        } else {
            rootDir = rootDir + File.separatorChar + packageNameToDir;
        }

        // Create directoryName if it doesn't already exist
        File dir = new File(rootDir);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }

        return dir.getAbsolutePath();
    }

    /**
     * Writes the output of applying the WebMacro template to the indicated file
     * @param templateFileName the WebMacro template
     * @param context Contextual values for use by the template
     * @param outputFileName the file to contain the results of applying the template
     * @throws WebMacroException
     * @throws IOException
     */
    private void writeWm(String templateFileName, Context context, String outputFileName)
            throws WebMacroException, IOException {
        writeWm(templateFileName, context, new FileOutputStream(outputFileName));
    }

    /**
     * Writes the output of applying the WebMacro template to the indicated file
     * @param templateFileName the WebMacro template
     * @param context Contextual values for use by the template
     * @param outputStream the output stream to contain the results of applying
     *                     the template
     * @throws WebMacroException
     * @throws IOException
     */
    private void writeWm(String templateFileName, Context context, OutputStream outputStream)
            throws WebMacroException, IOException {
        Template template = m_wm.getTemplate(templateFileName);
        FastWriter out = new FastWriter(m_wm.getBroker(), new BufferedOutputStream(outputStream),
                                        FastWriter.SAFE_UNICODE_ENCODING);
        template.write(out, context);
        out.close();
    }

    /**
     * Write a message to the indicated file
     * @param fileName the file to write
     * @param message the message to be written
     * @param append if true, write to the end of the existing file;
     *               otherwise, write to a new file
     * @throws IOException
     */
    private void writeMessage(String fileName, String message, boolean append)
            throws IOException {
        if (fileName != null && !fileName.equals("")
              && message != null && !message.equals("")) {
            PrintWriter out = null;
            if (append) {
                out = new PrintWriter(new FileWriter(fileName, true));
            } else {
                out = new PrintWriter(new FileWriter(fileName));
            }
            out.println(message);
            out.close();
        }
    }

    private void processGenerate(Element build) throws IOException {
        NodeList list = build.getElementsByTagName(XML_GENERATE);
        if (list != null && list.getLength() > 0) {
            Element generate = (Element) list.item(0);
            String type = XmlHelper.getTextTrim(generate.getElementsByTagName(XML_GENERATE_TYPE).item(0));
            if ("OS".equalsIgnoreCase(type)) {
                String workingDir = XmlHelper.getTextTrim(generate.getElementsByTagName(XML_GENERATE_WORKING_DIR).item(0));
                String command = XmlHelper.getTextTrim(generate.getElementsByTagName(XML_GENERATE_COMMAND_LINE).item(0));
                Process process = Runtime.getRuntime().exec(command, null, new File(workingDir));

                // wait for the command to be completed
                try {
                    process.waitFor();
                } catch (InterruptedException e) {
                    // dont do anything
                }
                writeMessage(m_auditFile, "OS: Executed " + command + " with the working dir " + workingDir, true);

            } else if ("JAVA".equalsIgnoreCase(type)) {
                String className = XmlHelper.getTextTrim(generate.getElementsByTagName(XML_GENERATE_CLASS_NAME).item(0));
                String arguments = XmlHelper.getTextTrim(generate.getElementsByTagName(XML_GENERATE_ARGUMENTS).item(0));
                try {
                    // get a handle on the 'public static void main(String[])' method of the class
                    Class clazz = Class.forName(className);
                    Method main = clazz.getMethod("main", new Class[]{String[].class});
                    int mod = main.getModifiers();
                    if (Modifier.isStatic(mod)) {
                        // convert the arguments into a string-array
                        StringTokenizer stz = new StringTokenizer(arguments, " ");
                        String[] argumentArray = new String[stz.countTokens()];
                        int i = -1;
                        while (stz.hasMoreTokens()) {
                            argumentArray[++i] = stz.nextToken();
                        }

                        // execute the method
                        main.invoke(null, new Object[]{argumentArray});
                        writeMessage(m_auditFile, "JAVA: Executed class  " + className + ", using the arguments " + arguments, true);
                    } else {
                        writeMessage(m_auditFile, "JAVA Error: The 'public static void main(String[] args)' method not found for the class " + className, true);
                    }
                } catch (ClassNotFoundException e) {
                    writeMessage(m_auditFile, "JAVA Error: Class " + className + " not found", true);
                } catch (NoSuchMethodException e) {
                    writeMessage(m_auditFile, "JAVA Error: The 'public static void main(String[] args)' method not found for the class " + className, true);
                } catch (IllegalAccessException e) {
                    writeMessage(m_auditFile, "JAVA Error: IllegalAccessException thrown while executing class " + className, true);
                } catch (InvocationTargetException e) {
                    writeMessage(m_auditFile, "JAVA Error: InvocationTargetException thrown while executing class " + className, true);
                }
            } else {
                writeMessage(m_auditFile, "Error: UnSupported Generate Type " + type, true);
            }
        }
    }

    /**
     * Run the pattern generator using the files under this directory.
     * @param dir       the name of a directory under which there may be XML
     *                  files that specify transformations to be applied to other
     *                  files.
     * @param initialProperties properties to use to help initialize
     * @param recursive if true, recursively descend through child
     *                  directories, transforming as you go.  If false, just
     *                  work on this directory.
     */
    private static void executePatternGenerator(File dir,
                                                Properties initialProperties,
                                                boolean recursive) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.flush();
                if (file.isFile()) {
                    executePatternGenerator(file.getAbsolutePath(), initialProperties);
                }
                else if (file.isDirectory() && recursive) {
                    executePatternGenerator(file, initialProperties, recursive);
                }
            }
        }
    }

    /**
     * Run the pattern generator using the specified pattern meta data file.
     * @param resourceName the name of a pattern meta data file
     *                     that specifies files to be
     *                     acted on and the templates to be applied to those files.
     * @param initialProperties properties to use to help initialize
     */
    private static void executePatternGenerator(String resourceName,
                                                Properties initialProperties) {
        PatternGenerator pg = null;
        try {
            URL patternMetaData = URLHelper.newExtendedURL(resourceName);
            System.out.println("Processing the pattern meta data file: " +
                               resourceName + '(' + patternMetaData + ')');
            pg = new PatternGenerator(patternMetaData, initialProperties);
            ArrayList<File> files = new ArrayList<>();
            File file = new File(patternMetaData.getFile());
            files.add(file);
            pg.setData(files);
            pg.processPattern();
        } catch (MalformedURLException e) {
            System.out.println("The pattern meta data file '" + resourceName +
                               "' was either not found or could not be parsed");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pg != null) {
                pg.destroy();
            }
        }
    }

    /**
     * Get the xml with the input url and parse a DatabaseTableInformation object out of it.
     * This will contain the model name, table name and field to column mapping.
     *
     * @param url the url of the xml file to parse into the result
     * @return the object represented by the input xml, this contains database structure information
     */
    private DatabaseTableInformation extractModelData(URL url) throws PatternGeneratorException, ParserConfigurationException, SAXException, IOException {
        DatabaseTableInformation result = new DatabaseTableInformation();

        // parse the xml file
        String file = url.getFile();
        Map dataMap = DomParser.parse(file, true);

        // strip off the root element from the dataMap
        dataMap = stripRoot(dataMap);
        if (dataMap == null) {
            throw new PatternGeneratorException("PatternMetaData file " + file
                                                + " is incorrectly formatted");
        }

        // pull the desired values from the dataMap
        for (Object object : dataMap.entrySet()) {
            Map.Entry entry = (Map.Entry) object;

            // get the name of the domain model
            // get the table name for the domain model
            // get the name and column for each field in the domain model
            if (entry.getKey().equals("DomainObject") && entry.getValue() != null) {
                result.setDomainModelName(entry.getValue().toString());
            } else if (entry.getKey().equals("DatabaseTable") && entry.getValue() != null) {
                result.setTableName(entry.getValue().toString());
            } else if (entry.getKey().equals("Fields") && entry.getValue() != null) {
                LinkedHashMap fieldEntry = (LinkedHashMap) entry.getValue();
                for (Object field : fieldEntry.values()) {
                    LinkedHashMap fieldData = null;
                    if (field instanceof LinkedHashMap) {
                        fieldData = (LinkedHashMap) field;
                    }
                    if (fieldData == null) {
                        continue;
                    }
                    String fieldName = "";
                    if (fieldData.get("Name") != null) {
                        fieldName = fieldData.get("Name").toString();
                    }
                    String columnName = "";
                    if (fieldData.get("DatabaseFieldName") != null) {
                        columnName = fieldData.get("DatabaseFieldName").toString();
                    }
                    result.addFieldColumn(fieldName, columnName);
                }
            }
        }

        return result;
    }

    /**
     * Print out usage information to the standard error stream.
     */
    private static void usage(String[] args) {
        System.err.println("Usage: PatternGenerator <source file or directory of pattern metadata>");
        System.err.println("                        [destination directory for java]");
        System.err.println("                        [destination directory for SQL]");
//        System.err.println("Alternately use: PatternGenerator <a directory with PatternMetaData files> [<-r>]");
        System.err.println("All file system locations are interpreted relative to the class path");
        System.err.print("Actual args: ");
        for (int i = 0; i < args.length; i++) {
            System.err.print(args[i] + " ");
        }
        System.err.println();
        System.exit(1);
    }

    /**
     * This will create an instance of the PatternGenerator, passing the URL
     * corresponding to the input argument.
     * It will then invoke the 'processPattern()' method.
     * If the argument is a directory, then the above process will be invoked
     * for each file in the directory.
     * If the '-r' argument is passed, then it will recursively scan the
     * directory for files, and invoke the pattern generation for each file.
     *
     * @param args This expects at least one argument to be passed in. This
     *             should represent the patternMetaData file, relative to the
     *             classpath. If a directory of passed, then the optional
     *             second argument determines, if a recursive search is to be
     *             performed on the directory.
     */
//    public static void oldMmain(String[] args) {
//        if (args.length < 1 || args.length > 2
//            || (args.length == 2 && !args[1].equals("-r"))) {
//            usage();
//        }
//
//        // check if the input is a directory
//        String pathname = args[0];
//        File dir = new File(pathname);
//        if (dir.exists() && dir.isDirectory()) {
//            // loop through all the files of the directory.
//            executePatternGenerator(dir, args.length == 2);
//        } else {
//            // try to get it off the classpath and check again if its a directory
//            try {
//                URL url = URLHelper.newExtendedURL(pathname);
//                File file = new File(url.getFile());
//                if (file.exists() && file.isDirectory()) {
//                    executePatternGenerator(file, args.length == 2);
//                } else {
//                    executePatternGenerator(pathname);
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                System.out.println("The pattern meta data file not found: " + pathname);
//            }
//        }
//    }

    /**
     * Parse the command line arguments and populate a Properties object
     * @param args the command line arguments
     * @return an object containing info from the command line
     */
    private static Properties getPropertiesFromCommandLine(String[] args) {
        Properties initialProperties = new Properties();

        if (args.length > 1) {
            String javaDestination = args[1];
            initialProperties.setProperty(PROPERTY_JAVA_ROOT_DIRECTORY,
                                          javaDestination);
            if (args.length > 2) {
                String sqlDestination = args[2];
                initialProperties.setProperty(PROPERTY_SQL_ROOT_DIRECTORY,
                                              sqlDestination);
            }
        }
        return initialProperties;
    }

    /**
     * This will create an instance of the PatternGenerator, passing the URL
     * corresponding to the input argument.
     * If the argument is a directory, then the above process will be invoked
     * for each file in the directory.
     * @param args This expects at least one argument to be passed in. This
     *             should represent the patternMetaData file, relative to the
     *             classpath.
     *   arg0 = source file or directory.
     *   arg1 = destination directory for java
     *   arg2 = destination directory for sql
     */
    public static void main(String[] args) {
        String classpath = System.getProperty("java.class.path");
        System.out.println("classpath = " + classpath);

        if (args.length < 1 || args.length > 3) {
            usage(args);
        } else {
            String pathname = args[0];
            Properties initialProperties = getPropertiesFromCommandLine(args);
            try {
                File path = new File(pathname);
                // The original implementation gave the option of recursing through
                // directories or just operating on the current directory.
                // Current requirements do not require recursion.
                boolean isRecursive = false;
                if (path.exists() && path.isDirectory()) {
                    // loop through all the files of the directory.
                    executePatternGenerator(path, initialProperties, isRecursive);
                } else {
                    // The URL contains the location of a file that specifies
                    // object-relational mapping metadata.
                    URL url = URLHelper.newExtendedURL(pathname);
                    executePatternGenerator(pathname, initialProperties);
                }
            }
            catch (MalformedURLException e) {
                System.err.println(
                        "No pattern meta data file was found at: " + pathname);
                e.printStackTrace();
            }
        }   // else - legal args
    }

}
