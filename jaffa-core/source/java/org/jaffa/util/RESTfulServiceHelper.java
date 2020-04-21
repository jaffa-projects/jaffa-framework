package org.jaffa.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateTime;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

public class RESTfulServiceHelper {
    private final static Logger LOGGER = Logger.getLogger(RESTfulServiceHelper.class);

    public static Map<String, Class> getValidFieldNames(Class clazz) throws IllegalAccessException, InstantiationException {
        Map<String, Class> validFieldNames = new HashMap<String, Class>();
        Object instance = clazz.newInstance();
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field f : fields) {
            validFieldNames.put(f.getName(), f.getType());
        }
        return validFieldNames;
    }

    public static <T> T  setValidFieldValues(Map<String, Object> values, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T instance = clazz.newInstance();
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (values.containsKey(f.getName())) {
                boolean accessible = f.isAccessible();
                if (!accessible) {
                    f.setAccessible(true);
                }

                f.set(instance, values.get(f.getName()));
                if (!accessible) {
                    f.setAccessible(accessible);
                }
            }
        }
        return instance;
    }

    public static Map<String, Object> parseJsonFields(String jsonString, Map<String, Class> validFieldNames) throws IOException {
        Map<String, Object> input = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();

        JsonFactory factory = new JsonFactory();
        JsonParser parser  = factory.createParser(jsonString);
        while (!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();

            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String fieldName = parser.getCurrentName();

                if (validFieldNames.keySet().contains(fieldName)) {
                    jsonToken = parser.nextToken();

                    Class clazz = validFieldNames.get(fieldName);
                    if (clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(boolean.class)) {
                        input.put(fieldName, parser.getValueAsBoolean());

                    } else if (clazz.isAssignableFrom(String.class)) {
                        input.put(fieldName, parser.getValueAsString());

                    } else if (clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class)) {
                        input.put(fieldName, parser.getValueAsInt());

                    } else if (clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(double.class)) {
                        input.put(fieldName, parser.getValueAsDouble());

                    } else if (clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class)) {
                        input.put(fieldName, parser.getValueAsLong());

                    } else if (clazz.isAssignableFrom(DateTime.class)) {
                        if (jsonToken.isStructStart()) {
                            while (!jsonToken.isStructEnd()) {
                                jsonToken = parser.nextToken();
                                if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                                    jsonToken = parser.nextToken();
                                    input.put(fieldName, new DateTime(parser.getValueAsLong()));
                                }
                            }
                        } else {
                            try {
                                DateTime dateTime = DateTime.parse(parser.getValueAsString());
                                input.put(fieldName, dateTime);
                            } catch (ParseException e) {
                                throw new IOException("Cannot parse DateTime " + parser.getValueAsString(), e);
                            }
                        }
                    } else if (clazz.isAssignableFrom(String[].class)) {
                        ArrayNode node = mapper.readTree(parser);
                        Iterator<JsonNode> iterator = node.elements();
                        String[] array = new String[node.size()];
                        for (int i = 0; i < node.size(); i++) {
                            if (iterator.hasNext()) {
                                array[i] = iterator.next().asText();
                            }
                        }
                        input.put(fieldName, array);

                    } else if (clazz.isAssignableFrom(List.class)) {
                        ArrayNode node = mapper.readTree(parser);
                        Iterator<JsonNode> iterator = node.elements();
                        List list = new ArrayList();
                        for (int i = 0; i < node.size(); i++) {
                            if (iterator.hasNext()) {
                                JsonNode n = iterator.next();
                                if (n.isContainerNode()) {
                                    Map<Object, Object> map = mapper.readValue(n.toString(), Map.class);
                                    list.add(map);
                                } else {
                                    if ("STRING".equals(n.getNodeType().name())) {
                                        list.add(n.textValue());
                                    } else if ("NUMBER".equals(n.getNodeType().name())) {
                                        if (n.isInt()) {
                                            list.add(n.intValue());
                                        } else if (n.isDouble()) {
                                            list.add(n.doubleValue());
                                        } else if (n.isLong()) {
                                            list.add(n.longValue());
                                        } else if (n.isFloat()) {
                                            list.add(n.floatValue());
                                        } else {
                                            LOGGER.warn("Not supported type. Field name = " + fieldName + ", Value = " + parser.getText() + ", Type = " + clazz.getTypeName());
                                            list.add(n);
                                        }
                                    } else if ("BOOLEAN".equals(n.getNodeType().name())) {
                                        list.add(n.booleanValue());
                                    } else {
                                        LOGGER.warn("Not supported type. Field name = " + fieldName + ", Value = " + parser.getText() + ", Type = " + clazz.getTypeName());
                                        list.add(n);
                                    }
                                }
                            }
                        }
                        input.put(fieldName, list);

                    } else {
                        LOGGER.warn("Not supported type. Field name = " + fieldName + ", Value = " + parser.getText() + ", Type = " + clazz.getTypeName());
                    }
                }
            }
        }

        return input;
    }
}
