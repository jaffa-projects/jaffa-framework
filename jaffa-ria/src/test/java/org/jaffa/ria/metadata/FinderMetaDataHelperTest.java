package org.jaffa.ria.metadata;

import org.directwebremoting.Container;
import org.directwebremoting.extend.Creator;
import org.directwebremoting.extend.CreatorManager;
import org.directwebremoting.impl.ContainerUtil;
import org.directwebremoting.impl.DefaultContainer;
import org.jaffa.modules.setup.apis.ValidFieldValueService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

public class FinderMetaDataHelperTest {
    @Mock
    private HttpServletRequest mockedRequest;
    @Mock
    private JspWriter mockedJspWriter;
    @Mock
    private ServletContext mockedServletContext;
    @Mock
    private Container mockedContainer;
    @Mock
    private CreatorManager mockedCreatorManager;
    @Mock
    private Creator mockedCreator;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPerform() throws Exception {
        List<Container> containers = new ArrayList<Container>() {{ add(mockedContainer); }};

        when(mockedRequest.getParameter("component")).thenReturn("Jaffa.Setup.ValidFieldValueService");
        when(mockedServletContext.getContext(anyString())).thenReturn(mockedServletContext);
        when(mockedServletContext.getAttribute(ContainerUtil.ATTRIBUTE_CONTAINER_LIST)).thenReturn(containers);
        when(mockedContainer.getBean(CreatorManager.class.getName())).thenReturn(mockedCreatorManager);
        when(mockedCreatorManager.getCreator(anyString())).thenReturn(mockedCreator);
        when(mockedCreator.getType()).thenReturn(ValidFieldValueService.class);

        FinderMetaDataHelper.perform(mockedRequest, mockedJspWriter, mockedServletContext);
        verify(mockedJspWriter).print(anyString());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPerformNullParameters() throws Exception {
        FinderMetaDataHelper.perform(mockedRequest, mockedJspWriter, mockedServletContext);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPerformUnsafeParameters() throws Exception {
        when(mockedRequest.getParameter("component")).thenReturn("org.jaffa.ValidClass <script src=\"maliciousSrc\"/>");
        FinderMetaDataHelper.perform(mockedRequest, mockedJspWriter, mockedServletContext);
    }

    /**
     * This method checks whether field-value syntax is parsable JSON.
     * @param nameValue a field name followed by a field value, e.g.,
     *                  "name": "Bob".  In some cases, the tested code may add
     *                  a trailing comma in anticipation of subsequent fields.
     *                  If that happens, this convenience method will ignore it.
     */
    private void checkJsonNameValueSyntax(String nameValue) {
        JsonParser jsonParser = new JsonParser();
        // ignore trailing newline
        if (nameValue.endsWith("\n")) {
            nameValue = nameValue.substring(0, nameValue.lastIndexOf('\n'));
        }
        // ignore trailing comma
        if (nameValue.endsWith(",")) {
            nameValue = nameValue.substring(0, nameValue.lastIndexOf(','));
        }
        String jsonObjectString = "{ " + nameValue + " }";
        try {
            jsonParser.parse(jsonObjectString);
        }
        catch (JsonParseException e) {
            fail("Attempt to parse >>" + jsonObjectString + "<< as JSON failed: " + e);
        }
    }

    @Test public void testParseStaticParameters() {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("staticParameters", "prop1=tprop1;prop2=tprop2");
        String staticParameters = FinderMetaDataHelper.parseStaticParameters(parameterMap);
        assertNotNull(staticParameters);
        assertTrue(staticParameters.contains("prop1: {values: [\"tprop1\"]}, prop2: {values: [\"tprop2\"]}"));
        checkJsonNameValueSyntax(staticParameters);
    }

    @Test public void testAppendDwrFunctionInput() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        helper.propertyNameQuote = FinderMetaDataHelper.JSON_QUOTE;
        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;
        StringBuilder builder = new StringBuilder();
        helper.appendDwrFunctionInput("a, \"b\", c", builder);
        String string = builder.toString();
        assertTrue(string.contains("\"DWRFunctionInput\": \"a, \\\"b\\\", c\""));
        checkJsonNameValueSyntax(string);
    }
    @Test public void testAppendOrderByFields() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        helper.propertyNameQuote = FinderMetaDataHelper.JSON_QUOTE;
        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;
        StringBuilder builder = new StringBuilder();
        String[] keys = new String[] {"key1", "key2"};
        helper.appendOrderByFields(keys, builder);
        String string = builder.toString();
        assertTrue(string.contains("\"orderByFields\": \"[{fieldName: \\\"Key1\\\"}, {fieldName: \\\"Key2\\\"}]\""));
        checkJsonNameValueSyntax(string);
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
        checkJsonNameValueSyntax(string);
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
        checkJsonNameValueSyntax(string);
        builder = new StringBuilder();
        helper.appendComboColumns(fieldMap, null, builder);
        string = builder.toString();
        assertTrue(string.contains("\"columns\": [\"f1\", \"f2\"]"));
        checkJsonNameValueSyntax(string);
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
        checkJsonNameValueSyntax(string);
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
        checkJsonNameValueSyntax(string);
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
    @Test public void testAppendFields() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        helper.propertyNameQuote = FinderMetaDataHelper.JSON_QUOTE;
        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;

        StringBuilder builder = new StringBuilder();
        Map<String, Map<String, String>> fieldMap = new HashMap<>();
        Map<String, String> vMap = new HashMap<>();
        vMap.put("vf1", "VV1");
        fieldMap.put("f1", vMap);
        Map<String, Map<String, String>> codeDescFieldMap = new HashMap<>();
        helper.appendFields(fieldMap, "f1", codeDescFieldMap, builder);
        String string = builder.toString();
        assertTrue(string.contains("\"VV1\""));
        assertTrue(string.contains("\"f1\":"));
        assertTrue(string.contains("\"vf1\":"));
        checkJsonNameValueSyntax(string);
    }

    @Test public void testAppendCodeDescs() {
        FinderMetaDataHelper helper = new FinderMetaDataHelper();
        helper.propertyNameQuote = FinderMetaDataHelper.JSON_QUOTE;
        helper.propertyValueQuote = FinderMetaDataHelper.JSON_QUOTE;

        StringBuilder builder = new StringBuilder();
        Map<String, Map<String, String>> fieldMap = new HashMap<>();
        Map<String, String> vMap = new HashMap<>();
        vMap.put("vf1", "VV1");
        fieldMap.put("f1", vMap);
        helper.appendCodeDescFields("f1", fieldMap, builder);
        String string = builder.toString();
        assertTrue(string.contains("\"VV1\""));
        assertTrue(string.contains("\"f1\":"));
        assertTrue(string.contains("\"vf1\":"));
        checkJsonNameValueSyntax(string);
    }


}
