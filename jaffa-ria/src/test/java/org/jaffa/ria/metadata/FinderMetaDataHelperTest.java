package org.jaffa.ria.metadata;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FinderMetaDataHelperTest {

    @Test public void testAppendOrderByFields() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        helper.propertyNameQuote = FinderMetaDataHelper.JSON_QUOTE;
        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;
        StringBuilder builder = new StringBuilder();
        String[] keys = new String[] {"key1", "key2"};
        helper.appendOrderByFields(keys, builder);
        String string = builder.toString();
        assertTrue(string.contains("\"orderByFields\": "));
        // TODO test contents
    }

    @Test public void testAppendCombo() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        helper.propertyNameQuote = FinderMetaDataHelper.JSON_QUOTE;
        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;
        StringBuilder builder = new StringBuilder();
        Map<String, Map<String, String>> fieldMap = new HashMap<>();
        Map<String, String> vMap = new HashMap<>();
        fieldMap.put("f1", vMap);
        fieldMap.put("f2", null);
        helper.appendCombo(fieldMap, "aValueField","codeDescFN", builder);
        String string = builder.toString();
        assertTrue(string.contains("\"combo\": {"));
        assertTrue(string.contains("\"columns\": [\"f1\", \"f2\", \"codeDescFN\"]"));
        assertTrue(string.contains("\"config\": {\"valueField\": \"aValueField\","
                                   + " \"displayField\": \"codeDescFN\"}"));
    }

    @Test public void testAppendComboColumns() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        helper.propertyNameQuote = FinderMetaDataHelper.JSON_QUOTE;
        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;
        StringBuilder builder = new StringBuilder();
        Map<String, Map<String, String>> fieldMap = new HashMap<>();
        Map<String, String> vMap = new HashMap<>();
        fieldMap.put("f1", vMap);
        fieldMap.put("f2", null);
        helper.appendComboColumns(fieldMap, "codeDescFN", builder);
        String string = builder.toString();
        assertTrue(string.contains("\"columns\": [\"f1\", \"f2\", \"codeDescFN\"]"));

        builder = new StringBuilder();
        helper.appendComboColumns(fieldMap, null, builder);
        string = builder.toString();
        assertTrue(string.contains("\"columns\": [\"f1\", \"f2\"]"));
    }

    @Test public void testAppendGrid() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        helper.propertyNameQuote = FinderMetaDataHelper.JSON_QUOTE;
        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;
        StringBuilder builder = new StringBuilder();
        Map<String, Map<String, String>> fieldMap = new HashMap<>();
        Map<String, String> vMap = new HashMap<>();
        fieldMap.put("f1", vMap);
        fieldMap.put("f2", null);
        helper.appendGrid(fieldMap, builder);
        String string = builder.toString();
        assertTrue(string.contains("\"grid\": {"));
        assertTrue(string.contains("\"columns\": [\"f1\", \"f2\"]"));
    }

    @Test public void testAppendKeys() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        helper.propertyNameQuote = FinderMetaDataHelper.JSON_QUOTE;
        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;
        String[] keys = new String[] {"key1", "key2"};
        StringBuilder builder = new StringBuilder();
        helper.appendKeys(keys, builder);
        String string = builder.toString();
        assertTrue(string.contains("\"key\": [\"key1\", \"key2\"]"));
    }

    @Test public void testAppendSetElementsJSON() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        helper.propertyNameQuote = FinderMetaDataHelper.JSON_QUOTE;
        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;
        StringBuilder builder = new StringBuilder();
        Map<String, Map<String, String>> fieldMap = new HashMap<>();
        Map<String, String> vMap = new HashMap<>();
        fieldMap.put("f1", vMap);
        fieldMap.put("f2", null);
        helper.appendSetElementsJSON(fieldMap, builder);
        String string = builder.toString();
        assertEquals("\"f1\", \"f2\"", string);
    }

    @Test public void testAppendValueString() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        StringBuilder builder = new StringBuilder();
        helper.appendValue(builder, "value");
        String string = builder.toString();
        assertTrue("The string value should be single-quoted", string.contains("'value'"));
        assertFalse("The string value should not be double-quoted", string.contains("\"value\""));

        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;
        builder = new StringBuilder();
        helper.appendValue(builder, "value");
        string = builder.toString();
        assertFalse("The string value should not be single-quoted", string.contains("'value'"));
        assertTrue("The string value should be double-quoted", string.contains("\"value\""));
    }

    @Test public void testAppendValueNumber() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        StringBuilder builder = new StringBuilder();
        helper.appendValue(builder, 5);
        String string = builder.toString();
        assertEquals("5", string);

        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;
        builder = new StringBuilder();
        helper.appendValue(builder, 5);
        string = builder.toString();
        assertEquals("5", string);
    }

    @Test public void testAppendValueBoolean() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        StringBuilder builder = new StringBuilder();
        helper.appendValue(builder, true);
        String string = builder.toString();
        assertEquals("true", string);

        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;
        builder = new StringBuilder();
        helper.appendValue(builder, true);
        string = builder.toString();
        assertEquals("true", string);
    }

}