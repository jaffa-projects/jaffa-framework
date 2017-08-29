package org.jaffa.rules.commons;

import org.jaffa.rules.meta.MetaDataRepository;
import org.jaffa.rules.realm.RealmRepository;
import org.jaffa.rules.rulemeta.RuleRepository;
import org.jaffa.rules.variation.VariationRepository;
import org.jaffa.util.DefaultEntityResolver;
import org.jaffa.util.DefaultErrorHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;

public class AopConstants {

    public static final String DEFAULT_AOP_PATTERN = "classpath*:**/*-aop.xml";
    private static final String RULE_META_TAG = "jaffa.rulemeta";
    private static final String RULE_TAG = "jaffa.rules";
    private static final String REALMS_TAG = "jaffa.realms";
    private static final String VARIATIONS_TAG = "jaffa.variations";

    /**
     * Creates a Map of compiled XPath Expressions and the associated Repository that the elements found in AOP.XML
     * files should be passed to for further processing.
     *
     * @return A Map of AOP XPath element expressions and associated repositories
     */
    public static Map<XPathExpression, AbstractLoader> createRepoMap() throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();

        Map<XPathExpression, AbstractLoader> response = new HashMap<>();
        response.put(xPath.compile("/aop/metadata[@tag='" + RULE_META_TAG + "']"), RuleRepository.instance());
        response.put(xPath.compile("/aop/metadata[@tag='" + RULE_TAG + "']"), MetaDataRepository.instance());
        response.put(xPath.compile("/aop/metadata[@tag='" + REALMS_TAG + "']"), RealmRepository.instance());
        response.put(xPath.compile("/aop/metadata[@tag='" + VARIATIONS_TAG + "']"), VariationRepository.instance());

        return response;
    }

    /**
     * Creates a DocumentBuilder that is configured to read Xml Documents in a common way.
     *
     * @return A configured parser
     */
    public static DocumentBuilder createParser() throws ParserConfigurationException {
        // Create a factory object for creating DOM parsers
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Specifies that the parser produced by this factory will validate documents as they are parsed.
        factory.setValidating(false);

        // Now use the factory to create a DOM parser
        DocumentBuilder parser = factory.newDocumentBuilder();

        // Specifies the EntityResolver to resolve DTD used in XML documents
        parser.setEntityResolver(new DefaultEntityResolver());

        // Specifies the ErrorHandler to handle warning/error/fatalError conditions
        parser.setErrorHandler(new DefaultErrorHandler());

        return parser;

    }
}
