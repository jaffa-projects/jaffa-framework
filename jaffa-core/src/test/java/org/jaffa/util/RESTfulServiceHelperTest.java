package org.jaffa.util;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;
import org.jaffa.loader.IRepository;
import org.jaffa.loader.ManagerRepositoryService;
import org.jaffa.loader.config.LocaleResourcesManager;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RESTfulServiceHelperTest {
    boolean booleanField;
    String stringField;
    Integer integerField;
    Double doubleField;
    long longField;
    DateTime dateTimeField;
    String[] strArrField;
    List<String> listStrField;
    List<Integer> listIntegerField;
    List<Double> listDoubleField;
    List<Long> listLongField;
    List<Boolean> listBooleanField;
    List<Map<String, Object>> listMapField;

    @Test
    public void testJsonParseToObject() throws InstantiationException, IllegalAccessException, IOException {
        //prepare test data
        String dateformat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        LocaleResourcesManager localeResourcesManager = mock(LocaleResourcesManager.class);
        IRepository<String> iRepository = mock(IRepository.class);
        when(iRepository.query(anyString())).thenReturn(dateformat);
        when(localeResourcesManager.getLocalePropertiesRepository()).thenReturn(iRepository);
        ManagerRepositoryService.getInstance().add("LocaleResourcesManager", localeResourcesManager);

        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        Date testDate = Calendar.getInstance().getTime();
        String strTestDate = dateFormat.format(testDate);

        String[] strArrField = {"str1", "str2", "str3"};

        List<String> listStrField = new ArrayList<>();
        listStrField.add("str1");
        listStrField.add("str2");

        List<Integer> listIntegerField = new ArrayList<>();
        listIntegerField.add(10);
        listIntegerField.add(20);

        List<Double> listDoubleField = new ArrayList<>();
        listDoubleField.add(10.12);
        listDoubleField.add(20.12);

        List<Long> listLongField = new ArrayList<>();
        listLongField.add(11L);
        listLongField.add(22L);

        List<Boolean> listBooleanField = new ArrayList<>();
        listBooleanField.add(true);
        listBooleanField.add(false);

        List<Map<String, String>> listMapField = new ArrayList<Map<String, String>>();
        Map<String, String> listMapField1 = new HashMap<String, String>();
        listMapField1.put("field11", "value11");
        listMapField1.put("field12", "value12");
        Map<String, String> listMapField2 = new HashMap<String, String>();
        listMapField2.put("field21", "value21");
        listMapField2.put("field22", "value22");
        listMapField.add(listMapField1);
        listMapField.add(listMapField2);

        Gson gson = new Gson();

        //test getValidFieldNames
        Map<String, Class> validFields = RESTfulServiceHelper.getValidFieldNames(RESTfulServiceHelperTest.class);
        assertTrue(validFields.containsKey("listStrField"));
        assertTrue(validFields.containsKey("longField"));
        assertTrue(validFields.containsKey("doubleField"));
        assertTrue(validFields.containsKey("listIntegerField"));
        assertTrue(validFields.containsKey("booleanField"));
        assertTrue(validFields.containsKey("strArrField"));
        assertTrue(validFields.containsKey("dateTimeField"));
        assertTrue(validFields.containsKey("listDoubleField"));
        assertTrue(validFields.containsKey("listLongField"));
        assertTrue(validFields.containsKey("integerField"));
        assertTrue(validFields.containsKey("listBooleanField"));
        assertTrue(validFields.containsKey("listMapField"));
        assertTrue(validFields.containsKey("stringField"));

        //test parseJsonFields
        String jsonString = "{\"booleanField\": \"true\", " +
                "\"stringField\": \"testString\", " +
                "\"integerField\": \"1000\", " +
                "\"doubleField\": \"123.123\", " +
                "\"longField\": \"10000\", " +
                "\"dateTimeField\": \"" + strTestDate + "\", " +
                "\"strArrField\": " + gson.toJson(strArrField) + ", " +
                "\"listStrField\": " + gson.toJson(listStrField) + ", " +
                "\"listIntegerField\": " + gson.toJson(listIntegerField) + ", " +
                "\"listDoubleField\": " + gson.toJson(listDoubleField) + ", " +
                "\"listLongField\": " + gson.toJson(listLongField) + ", " +
                "\"listBooleanField\": " + gson.toJson(listBooleanField) + ", " +
                "\"listMapField\": " + gson.toJson(listMapField) + ", " +
                "\"unknown\": \"unknown\"}";

        Map<String, Object> values = RESTfulServiceHelper.parseJsonFields(jsonString, validFields);
        assertFalse(values.containsKey("unknown"));
        assertEquals("parsedFields: " + values.keySet(), 13, values.size());

        //test setValidFieldValues
        RESTfulServiceHelperTest test = RESTfulServiceHelper.setValidFieldValues(values, RESTfulServiceHelperTest.class);
        assertEquals(true, test.booleanField);
        assertEquals("testString", test.stringField);
        assertEquals(new Integer(1000), test.integerField);
        assertEquals(new Double(123.123), test.doubleField);
        assertEquals(10000, test.longField);
        assertEquals(new DateTime(testDate), test.dateTimeField);
        assertEquals(Arrays.toString(strArrField), Arrays.toString(test.strArrField));
        assertEquals(listStrField, test.listStrField);
        assertEquals(listIntegerField, test.listIntegerField);
        assertEquals(listDoubleField, test.listDoubleField);
        assertEquals(listLongField.toString(), test.listLongField.toString());
        assertEquals(listBooleanField, test.listBooleanField);
        assertEquals(listMapField, test.listMapField);
    }
}