package org.jaffa.ria.metadata;

import org.jaffa.security.SecurityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityManager.class })
public class ClassMetaDataHelperTest {

    @Test public void testWriteLhsOfAssignment() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        try {
            StringWriter writer = new StringWriter();
            helper.writeLhsOfAssignment("java.util.Map", writer, "OutputClass");
            assertTrue(writer.toString().contains("ClassMetaData['OutputClass'] = "));
        }
        catch (IOException e) {
            fail("No exception should be thrown from writeLhsOfAssignment()");
        }
    }

    @Test public void testWriteLhsOfAssignmentEmptyClassName() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        try {
            StringWriter writer = new StringWriter();
            helper.writeLhsOfAssignment("java.util.Map", writer, "");
            assertTrue("When empty outputClassName is provided, should use className input",
                       writer.toString().contains("ClassMetaData['Map'] = "));
        }
        catch (IOException e) {
            fail("No exception should be thrown from writeLhsOfAssignment()");
        }
    }

    @Test public void testWriteLhsOfAssignmentNullClassName() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        try {
            StringWriter writer = new StringWriter();
            helper.writeLhsOfAssignment("java.util.Map", writer, null);
            assertTrue("When null outputClassName is provided, should use className input",
                       writer.toString().contains("ClassMetaData['Map'] = "));
        }
        catch (IOException e) {
            fail("No exception should be thrown from writeLhsOfAssignment()");
        }
    }

    @Test public void testToHtml() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        assertEquals("toHtml should return an empty string",
                     "", helper.toHtml(null));
        assertEquals("toHtml should return the input string",
                     "bob", helper.toHtml("bob"));
    }

    @Test public void quoteValueByType() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        String trueBoolean = helper.quoteValueByType("true", "boolean");
        assertEquals("true", trueBoolean);
        String fiveNumber = helper.quoteValueByType("5", "number");
        assertEquals("5", fiveNumber);
        String trueString = helper.quoteValueByType("true", "string");
        assertEquals("'true'", trueString);
    }

    @Test public void testToJavaScript() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        Properties props = new Properties();
        props.put("aString", "hello");
        String javaScript = helper.toJavaScript(props);
        assertTrue(javaScript.startsWith("{"));
        assertTrue(javaScript.endsWith("}"));
        assertTrue(javaScript.contains("aString"));
        assertFalse(javaScript.contains("'aString'"));
        assertTrue(javaScript.contains("'hello'"));
        assertFalse(javaScript.contains(","));

        props.put("bString", "bye");
        javaScript = helper.toJavaScript(props);
        assertTrue(javaScript.startsWith("{"));
        assertTrue(javaScript.endsWith("}"));
        assertTrue(javaScript.contains("aString"));
        assertFalse(javaScript.contains("'aString'"));
        assertTrue(javaScript.contains("'hello'"));
        assertTrue(javaScript.contains(","));
        assertTrue(javaScript.contains("bString"));
        assertTrue(javaScript.contains("'bye'"));
    }

    @Test public void testAppendForeignKeyInfoNull() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        StringBuilder builder = new StringBuilder();
        int length1 = builder.length();
        helper.appendForeignKeyInfo(builder, null);
        int length2 = builder.length();
        assertEquals("Null properties input means no foreign keys should be present",
                     length1, length2);
    }

    @Test public void testAppendForeignKeyInfoNone() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        StringBuilder builder = new StringBuilder();
        Properties props = new Properties();
        helper.appendForeignKeyInfo(builder, props);
        String result = builder.toString();
        assertTrue("No foreign keys should be present", result.contains("{}"));
    }

    @Test public void testAppendHyperlinkInfoNull() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        StringBuilder builder = new StringBuilder();
        int length1 = builder.length();
        helper.appendHyperlinkInfo(null, builder, null);
        int length2 = builder.length();
        assertEquals("Null hyperlinkInfo input means nothing should be added to output",
                     length1, length2);
    }

    @Test
    public void testAppendHyperlinkInfo() {
        // Assert : Preparing Service and objects to invoke ClassMetaDataHelper.
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        Properties hyperlinkInfo = new Properties();
        hyperlinkInfo.put("component", "comp1");
        hyperlinkInfo.put("inputs", "input1");
        hyperlinkInfo.put("values", "v1");

        Map<String, String> foreignKeyMappings = new HashMap<>();
        foreignKeyMappings.put("v1", "fkm1");

        StringBuilder builder = new StringBuilder();

        PowerMockito.mockStatic(SecurityManager.class);
        when(SecurityManager.checkComponentAccess(anyString())).thenReturn(true);

        //Act : Invoking actual logic
        helper.appendHyperlinkInfo(foreignKeyMappings, builder, hyperlinkInfo);
        String output = builder.toString();

        //Assert : Verify hyperlink,component,input present in the response
        assertTrue("Hyperlink field name should be in output",
                   output.contains("hyperlink: {"));
        assertTrue("Component field name should be in output",
                   output.contains("component: "));
        assertTrue("Component value should be in output",
                   output.contains("comp1"));
        assertTrue("Inputs field name should be in output",
                   output.contains("inputs:"));
        assertTrue("Inputs value should be in output",
                   output.contains("input1"));
    }

    @Test public void testPropertiesToStringNone() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        Properties props = new Properties();
        String result = helper.propertiesToString(props);
        assertTrue("No properties should be present", result.contains("{}"));
    }

    @Test public void testPropertiesToStringOneDefault() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        Properties props = new Properties();
        props.put("key1", "value1");
        String result = helper.propertiesToString(props);
        assertTrue("Key1 should be present, followed by a colon",
                   result.contains("key1:"));
        assertFalse("By default, key1 should not be surrounded by single quotes",
                   result.contains("'key1'"));
        assertFalse("By default, key1 should not be surrounded by double quotes",
                   result.contains("\"key1\""));
        assertTrue("Value1 should be present, surrounded by single quotes",
                   result.contains("'value1'"));
    }

    @Test public void testPropertiesToStringOneQuotes() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        helper.propertyNameQuote = "\"";
        helper.propertyValueQuote = "\"";
        Properties props = new Properties();
        props.put("key1", "value1");
        String result = helper.propertiesToString(props);
        assertTrue("Key1 should be present, surrounded by double quotes, followed by a colon",
                   result.contains("\"key1\":"));
        assertFalse("By default, key1 should not be surrounded by single quotes",
                   result.contains("'key1'"));
        assertTrue("Value1 should be present, surrounded by double quotes",
                   result.contains("\"value1\""));
        assertFalse("By default, value1 should not be surrounded by single quotes",
                   result.contains("'value1'"));
    }

    @Test public void testPropertiesToStringTwoDefault() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        Properties props = new Properties();
        props.put("key1", "value1");
        props.put("key2", "value2");
        String result = helper.propertiesToString(props);
        assertTrue("Key1 should be present, followed by a colon",
                   result.contains("key1:"));
        assertFalse("By default, key1 should not be surrounded by single quotes",
                    result.contains("'key1'"));
        assertFalse("By default, key1 should not be surrounded by double quotes",
                    result.contains("\"key1\""));
        assertTrue("Value1 should be present, surrounded by single quotes",
                   result.contains("'value1'"));
        assertTrue("Key2 should be present, followed by a colon",
                   result.contains("key2:"));
        assertTrue("Value2 should be present, surrounded by single quotes",
                   result.contains("'value2'"));
        assertTrue("A comma should be present", result.contains(","));
    }


    @Test public void writeAggregateObjectNames() {
        StringWriter writer = new StringWriter();
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        String[] names = { "o1", "o2", "o3"};
        try {
            helper.writeAggregateObjectNames(writer, names);
            String out = writer.toString();
            assertTrue(out.contains("collectionNames"));
            assertTrue(out.contains("o1"));
            assertTrue(out.contains("o3"));
        }
        catch (IOException e) {
            fail("Threw " + e);
        }
    }

    @Test public void testWriteLabelSingleQuote() {
        StringWriter writer = new StringWriter();
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        try {
            helper.writeLabel(writer, "aLabel");
            String labelOutput = writer.toString();
            assertTrue(labelOutput.contains("label:"));
            assertFalse(labelOutput.contains("'label':"));
            assertFalse(labelOutput.contains("\"label\":"));
            assertTrue(labelOutput.contains("'aLabel'"));
        }
        catch (IOException e) {
            fail("testWriteLabel caught " + e);
        }
    }

    @Test public void testWriteKeysSingleQuote() {
        StringWriter writer = new StringWriter();
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        try {
            String[] keys = { "aKey" };
            helper.writeKeys(writer, keys);
            String keyOutput = writer.toString();
            assertTrue(keyOutput.contains("key:"));
            assertFalse(keyOutput.contains("'key':"));
            assertFalse(keyOutput.contains("\"key\":"));
            assertTrue(keyOutput.contains("'aKey'"));

            assertFalse("A single key should not have an opening bracket", keyOutput.contains("["));
            assertFalse("A single key should not have a closing bracket", keyOutput.contains("]"));

            String[] keys2 = { "key1", "key2" };
            helper.writeKeys(writer, keys2);
            keyOutput = writer.toString();
            assertTrue(keyOutput.contains("key:"));
            assertFalse(keyOutput.contains("'key':"));
            assertFalse(keyOutput.contains("\"key\":"));
            assertTrue(keyOutput.contains("'key1',"));
            assertTrue(keyOutput.contains("'key2'"));

            assertTrue("A single key should have an opening bracket", keyOutput.contains("["));
            assertTrue("A single key should have a closing bracket", keyOutput.contains("]"));
        }
        catch (Exception e) {
            fail("testWriteKeys caught " + e);
        }
    }

    @Test public void testAppendPropertyString() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        StringBuilder builder = new StringBuilder();
        helper.appendProperty(builder, "key", "value");
        String prop1 = builder.toString();

        assertTrue("The key should be unquoted", prop1.contains("key:"));
        assertFalse("The key should be unquoted", prop1.contains("'key':"));
        assertFalse(prop1.contains("\"key\":"));

        assertTrue("The string value should be single-quoted", prop1.contains("'value'"));
        assertFalse("The string value should not be double-quoted", prop1.contains("\"value\""));
    }

    @Test public void testAppendPropertyBoolean() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        StringBuilder builder = new StringBuilder();
        helper.appendProperty(builder, "key", true);
        String prop1 = builder.toString();

        assertTrue(prop1.contains("key:"));
        assertFalse("The key should be unquoted", prop1.contains("'key':"));
        assertFalse("The key should be unquoted", prop1.contains("\"key\":"));

        assertTrue(prop1.contains("true"));
        assertFalse("The boolean value should be unquoted", prop1.contains("\"true\""));
        assertFalse("The boolean value should be unquoted", prop1.contains("'true'"));
    }

    @Test public void testAppendPropertyInt() {
        ClassMetaDataHelper helper = new ClassMetaDataHelper();
        StringBuilder builder = new StringBuilder();
        helper.appendProperty(builder, "key", 5);
        String prop1 = builder.toString();

        assertTrue(prop1.contains("key:"));
        assertFalse("The key should be unquoted", prop1.contains("'key':"));
        assertFalse("The key should be unquoted", prop1.contains("\"key\":"));

        assertTrue(prop1.contains("5"));
        assertFalse("The numeric value should be unquoted", prop1.contains("\"5\""));
        assertFalse("The numeric value should be unquoted", prop1.contains("'5'"));
    }

}