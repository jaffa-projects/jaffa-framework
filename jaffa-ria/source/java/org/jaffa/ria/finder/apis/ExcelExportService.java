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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *  this Software without prior written permission. For written permission,
 *  please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *  appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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
package org.jaffa.ria.finder.apis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;

import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.ObjectMorpher;
import net.sf.ezmorph.object.IdentityObjectMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.NewBeanInstanceStrategy;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.jaffa.components.finder.BooleanCriteriaField;
import org.jaffa.components.finder.CurrencyCriteriaField;
import org.jaffa.components.finder.DateOnlyCriteriaField;
import org.jaffa.components.finder.DateTimeCriteriaField;
import org.jaffa.components.finder.DecimalCriteriaField;
import org.jaffa.components.finder.IntegerCriteriaField;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.datatypes.Currency;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Parser;
import org.jaffa.flexfields.FlexClass;
import org.jaffa.flexfields.FlexCriteriaBean;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.BeanHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.jaffa.datatypes.Formatter;

/**
 * A service to generate excel documents.
 * 
 * @author BobbyC
 */
public class ExcelExportService {

    private static final Logger log = Logger.getLogger(ExcelExportService.class);

    /**
     * Converts the input JSON structure into an instance of the beanClassName.
     * If the beanClassName is null, then an instance of org.apache.commons.beanutils.DynaBean will be returned.
     * 
     * @param input
     *            input as a JSON structure.
     * @param beanClassName
     *            the name of the bean class.
     * @return the input JSON structure as an instance of the beanClassName.
     * @throws ClassNotFoundException if the beanClassName is invalid.
     */
    public static Object jsonToBean(String input, String beanClassName) throws ClassNotFoundException {
        if (log.isDebugEnabled())
            log.debug("Converting JSON '" + input + "' into an instance of " + beanClassName);
        registerCustomMorphers();
        Class beanClass = beanClassName != null ? Class.forName(beanClassName) : null;
        JSONObject jsonObject = JSONObject.fromObject(input);
        Object bean = JSONObject.toBean(jsonObject, createJsonConfig(beanClass));
        if (log.isDebugEnabled())
            log.debug("Converted to: " + bean);
        return bean;
    }

    /**
     * Converts the input array JSON structure into an array of instances of the
     * beanClassName. If the beanClassName is null, then an arrays of instances
     * of org.apache.commons.beanutils.DynaBean will be returned.
     * 
     * @param input
     *            input array as a JSON structure.
     * @param beanClassName
     *            the name of the bean class.
     * @return the input array JSON structure as an array of instances of the beanClassName.
     * @throws ClassNotFoundException if the beanClassName is invalid.
     */
    public static Object[] jsonArrayToBeanArray(String input, String beanClassName) throws ClassNotFoundException {
        if (log.isDebugEnabled())
            log.debug("Converting JSON '" + input + "' into an array of instances of " + beanClassName);
        registerCustomMorphers();
        Class beanClass = beanClassName != null ? Class.forName(beanClassName) : null;
        JSONArray jsonArray = JSONArray.fromObject(input);
        Object[] beans = beanClass != null ? (Object[]) JSONArray.toArray(jsonArray, beanClass) : (Object[]) JSONArray.toArray(jsonArray);
        if (log.isDebugEnabled())
            log.debug("Converted to: " + Arrays.toString(beans));
        return beans;
    }

    /**
     * Registers custom Morphers to handle Jaffa's DateTime and DateOnly classes.
     */
    private static void registerCustomMorphers() {
        registerCustomDateMorpher(true);
        registerCustomDateMorpher(false);
    }

    /**
     * Registers a custom Morpher to handle, based on the input flag, either a DateTime or DateOnly class.
     */
    private static void registerCustomDateMorpher(boolean dateTime) {
        final Class targetType = dateTime ? DateTime.class : DateOnly.class;
        Morpher targetMorpher = JSONUtils.getMorpherRegistry().getMorpherFor(targetType);
        if (targetMorpher == null || targetMorpher == IdentityObjectMorpher.getInstance()) {
            synchronized (JSONUtils.getMorpherRegistry()) {
                targetMorpher = JSONUtils.getMorpherRegistry().getMorpherFor(targetType);
                if (targetMorpher == null || targetMorpher == IdentityObjectMorpher.getInstance()) {
                    // Create a custom Morpher
                    targetMorpher = new ObjectMorpher() {

                        /**
                         * Returns the target Class (DateTime.class or DateOnly.class) for conversion.
                         */
                        public Class morphsTo() {
                            return targetType;
                        }

                        /**
                         * Returns true if the Morpher supports conversion from this Class. Only the String class is supported currently.
                         */
                        public boolean supports(Class clazz) {
                            return clazz == String.class;
                        }

                        /**
                         * Morphs the input object into an output object of the supported type.
                         */
                        public Object morph(Object value) {
                            try {
                                String layout = "yyyy-MM-dd'T'HH:mm:ss";
                                return targetType == DateTime.class ? Parser.parseDateTime((String) value, layout) : Parser.parseDateOnly((String) value, layout);
                            } catch (Exception e) {
                                if (log.isDebugEnabled())
                                    log.debug("Error in converting '" + value + "' to " + (targetType == DateTime.class ? "DateTime" : "DateOnly"), e);
                                return value;
                            }
                        }
                    };
                    JSONUtils.getMorpherRegistry().registerMorpher(targetMorpher);
                }
            }
        }
    }

    /**
     * Creates a JsonConfig with the rootClass set to the input. Adds
     * custom-support for handling FlexCriteriaBean.
     */
    private static JsonConfig createJsonConfig(Class rootClass) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(rootClass);
        jsonConfig.setNewBeanInstanceStrategy(new NewBeanInstanceStrategy() {
            public Object newInstance(Class target, JSONObject source) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, InvocationTargetException {
                if (target == FlexCriteriaBean.class) {
                    try {
                        // Determine the name of the associated dynaClass and
                        // use that to instantiate the FlexCriteriaBean
                        JSONObject dynaClassObject = source.getJSONObject("dynaClass");
                        String dynaClassName = dynaClassObject.getString("name");
                        FlexCriteriaBean bean = FlexCriteriaBean.instance(FlexClass.instance(dynaClassName));

                        // Add the criteria elements
                        source.remove("dynaClass");
                        for (Iterator i = source.keys(); i.hasNext();) {
                            String key = (String) i.next();
                            Class propType = bean.getDynaClass() != null && bean.getDynaClass().getDynaProperty(key) != null ? bean.getDynaClass().getDynaProperty(key).getType() : String.class;
                            propType = findCriteriaFieldClass(propType);
                            Object propValue = JSONObject.toBean(source.getJSONObject(key), propType);
                            bean.set(key, propValue);
                        }
                        source.clear();

                        return bean;
                    } catch (Exception e) {
                        String s = "Exception thrown while instantiating FlexCriteriaBean from " + source;
                        log.error(s, e);
                        throw new InvocationTargetException(e, s);
                    }
                }
                return target.newInstance();
            }
        });
        return jsonConfig;
    }

    /** Returns a CriteriaField class corresponding to the input data type. */
    private static Class findCriteriaFieldClass(Class dataType) {
        Class output = null;
        if (dataType == String.class)
            output = StringCriteriaField.class;
        else if (dataType == Boolean.class)
            output = BooleanCriteriaField.class;
        else if (dataType == Currency.class)
            output = CurrencyCriteriaField.class;
        else if (dataType == DateOnly.class)
            output = DateOnlyCriteriaField.class;
        else if (dataType == DateTime.class)
            output = DateTimeCriteriaField.class;
        else if (dataType == Double.class)
            output = DecimalCriteriaField.class;
        else if (dataType == Long.class)
            output = IntegerCriteriaField.class;
        else {
            if (log.isDebugEnabled())
                log.debug("Unsupported datatype '" + dataType.getName() + "' passed to the findCriteriaFieldClass routine.");
            output = StringCriteriaField.class;
        }
        return output;
    }

    /**
     * Invokes the query method on the input service class passing the given criteria.
     * 
     * @param criteriaClassName
     *            the name of the criteria class.
     * @param criteriaObject
     *            criteria as a JSON structure.
     * @param serviceClassName
     *            the name of the service class that should contain the method
     *            'public AGraphDataObject[] query(AGraphCriteria criteria)'.
     * @return the output from the query invocation.
     * @throws ClassNotFoundException
     *             if either the criteria or the service class are invalid.
     * @throws NoSuchMethodException
     *             if the 'public AGraphDataObject[] query(AGraphCriteria
     *             criteria)' is not found on the service class.
     * @throws InstantiationException
     *             if an error occurs in instantiating the service class.
     * @throws IllegalAccessException
     *             if the query method is not accessible.
     * @throws IllegalArgumentException
     *             if the input is invalid.
     * @throws InvocationTargetException
     *             if an error occurs during invocation of the query method.
     */
    private static Object[] invokeQueryService(String criteriaClassName, String criteriaObject, String serviceClassName, String serviceClassMethodName) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (log.isDebugEnabled())
            log.debug("Invoking query service '" + serviceClassName + "' passing an instance of '" + criteriaClassName + "' having the criteria '" + criteriaObject + '\'');

        // convert the JSON criteria into a Java object
        Object criteria = jsonToBean(criteriaObject, criteriaClassName);

        // If no method name supplied, use default method "query"
        if (serviceClassMethodName == null || serviceClassMethodName.equals(""))
            serviceClassMethodName = "query";

        // find the 'public AGraphDataObject[] query(AGraphCriteria criteria)'
        // method on the service class
        Class serviceClass = Class.forName(serviceClassName);
        Method m = serviceClass.getMethod(serviceClassMethodName, criteria.getClass());

        // invoke the query method on the service
        Object service = m.getModifiers() == Modifier.STATIC ? null : serviceClass.newInstance();
        Object[] output = null;
        if (m.getReturnType().isArray()) {
            output = (Object[]) m.invoke(service, criteria);
        } else {
            Object gqr = m.invoke(service, criteria);
            output = (Object[]) BeanHelper.getField(gqr, "graphs");
        }
        if (log.isDebugEnabled())
            log.debug("Query output: " + Arrays.toString(output));
        return output;
    }

    /**
     * Invokes the query method on the input class passing the given criteria.
     * It then generates the Excel representation of the data returned by the query.
     * 
     * @param criteriaClassName
     *            the name of the criteria class.
     * @param criteriaObject
     *            criteria as a JSON structure.
     * @param serviceClassName
     *            the name of the service class that should contain the method
     *            'public AGraphDataObject[] query(AGraphCriteria criteria)'.
     * @param columnModel
     *            JSON structure containing an array of column elements, each
     *            element having 'header' and 'mapping' for providing the
     *            column-label and data respectively.
     * @return the output from the query invocation as an Excel table.
     * @throws ClassNotFoundException
     *             if either the criteria or the service class are invalid.
     * @throws NoSuchMethodException
     *             if the 'public AGraphDataObject[] query(AGraphCriteria
     *             criteria)' is not found on the service class.
     * @throws InstantiationException
     *             if an error occurs in instantiating the service class.
     * @throws IllegalAccessException
     *             if the query method is not accessible.
     * @throws IllegalArgumentException
     *             if the input is invalid.
     * @throws InvocationTargetException
     *             if an error occurs during invocation of the query method.
     */
    public static Workbook generateExcel(QueryServiceConfig master, QueryServiceConfig child) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return generateExcel(master, child, null);
    }
    
    public static Workbook generateExcel(QueryServiceConfig master, QueryServiceConfig child, String sheetName) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        Workbook wb = null;
        String legacyExport = (String) ContextManagerFactory.instance().getProperty("jaffa.widgets.exportToExcel.legacy");
        if (legacyExport!=null && legacyExport.equals("T")){
            wb = new HSSFWorkbook();
        } else {
            wb = new SXSSFWorkbook(100);
        }
        try {
            // Creating worksheet
            Sheet sheet = null;
            if (sheetName != null) {
                if (sheetName.length() > 31)
                    sheetName = sheetName.substring(0, 31);
                char replaceChar = '_';
                sheetName = sheetName.replace('\u0003',replaceChar).replace(':',replaceChar).replace('/',replaceChar).replace("\\\\",Character.toString(replaceChar)).replace('?',replaceChar).replace('*',replaceChar).replace(']',replaceChar).replace('[',replaceChar);
                sheet = wb.createSheet(sheetName);
            } else
              sheet = wb.createSheet();

            // creating a custom palette for the workbook
            CellStyle style = wb.createCellStyle();
            style = wb.createCellStyle();

            // setting the foreground color to gray
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);

            // Setting the border for the cells
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setLeftBorderColor(IndexedColors.GREEN.getIndex());
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setTopBorderColor(IndexedColors.BLACK.getIndex());
            style.setAlignment(CellStyle.ALIGN_CENTER);

            // setting font weight
            Font titleFont = wb.createFont();
            titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(titleFont);

            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum);
            int colIndex = 0;
            for (Object o : master.getColumnModel()) {
                String columnTitle = (String) ((DynaBean) o).get("header");
                if (columnTitle == null || columnTitle.length() == 0)
                    columnTitle = (String) ((DynaBean) o).get("mapping");

                headerRow.createCell(colIndex).setCellValue(columnTitle);
                Cell cell = headerRow.getCell(colIndex);
                cell.setCellStyle(style);
                sheet.autoSizeColumn(colIndex);
                colIndex += 1;
            }

            // Generate the Excel output by creating a simple HTML table
            if (child != null) {
                for (Object o : child.getColumnModel()) {
                    String columnTitle = (String) ((DynaBean) o).get("header");
                    if (columnTitle == null || columnTitle.length() == 0)
                        columnTitle = (String) ((DynaBean) o).get("mapping");

                    headerRow.createCell(colIndex).setCellValue(columnTitle);
                    Cell cell = headerRow.getCell(colIndex);
                    cell.setCellStyle(style);
                    sheet.autoSizeColumn(colIndex);
                    colIndex += 1;

                }
            }
            
            // Invoke the query and obtain an array of Graph objects
            Object[] queryOutput = invokeQueryService(master.getCriteriaClassName(), master.getCriteriaObject(), master.getServiceClassName(), master.getServiceClassMethodName());

            // Add the data rows
            if (queryOutput != null) {
                for (Object row : queryOutput) {
                    Object[] detailQueryOutput = new Object[0];
                    if (child==null){
                        rowNum += 1;
                        Row dataRow = sheet.createRow((short) rowNum);
                         int colNum = 0;
                        // extract the columns from master object
                        for (Object o : master.getColumnModel()) {
                            String mapping = (String) ((DynaBean) o).get("mapping");
                            Object value = null;
                            try {
                                value = PropertyUtils.getProperty(row, mapping);
                            } catch (Exception e) {
                                if (log.isDebugEnabled())
                                    log.debug("Property not found: " + mapping, e);
                            }
    
                            dataRow.createCell(colNum).setCellValue(format(value, (DynaBean) o));
                            colNum += 1;
                        }
                    } else { //child is not null
                        // load the child rows
                        String detailCriteriaObject = child.getCriteriaObject();
                        for (int i = 0; i < child.getMasterKeyFieldNames().length; i++) {
                            String kfn = child.getMasterKeyFieldNames()[i];
                            try {
                                String keyValue = (String) PropertyUtils.getProperty(row, kfn);
                                detailCriteriaObject = detailCriteriaObject.replace("{" + i + "}", keyValue);
                            } catch (Exception e) {
                                if (log.isDebugEnabled())
                                    log.debug("Key property not found: " + kfn, e);
                            }
                        }
                        detailQueryOutput = invokeQueryService(child.getCriteriaClassName(), detailCriteriaObject, child.getServiceClassName(), "query");

                        // add the child columns
                        if (detailQueryOutput != null && detailQueryOutput.length > 0) {
                            for (Object detailRow : detailQueryOutput) {
                                rowNum += 1;
                                Row dataRow = sheet.createRow((short) rowNum);
            
                                int colNum = 0;
                                // extract the columns from master object
                                for (Object obj : master.getColumnModel()) {
                                    String masterMapping = (String) ((DynaBean) obj).get("mapping");
                                    Object masterValue = null;
                                    try {
                                        masterValue = PropertyUtils.getProperty(row, masterMapping);
                                    } catch (Exception e) {
                                        if (log.isDebugEnabled())
                                            log.debug("Property not found: " + masterMapping, e);
                                    }
            
                                    dataRow.createCell(colNum).setCellValue(format(masterValue, (DynaBean) obj));
                                    colNum += 1;
                                }

                                for (Object o : child.getColumnModel()) {
                                    String mapping = (String) ((DynaBean) o).get("mapping");
                                    Object value = null;
                                    try {
                                        value = PropertyUtils.getProperty(detailRow, mapping);
                                    } catch (Exception e) {
                                        if (log.isDebugEnabled())
                                            log.debug("Property not found in child result: " + mapping, e);
                                    }

                                    dataRow.createCell(colNum).setCellValue(format(value, (DynaBean) o));
                                    colNum += 1;
                                }
                            }
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
    }


        /**
         * Formats the input object based on the layout specified in the
         * ColumnModel.
         */
        private static String format(Object value, DynaBean columnModel) {
            try {
                String layout = (String) columnModel.get("layout");
                if (value != null && value instanceof Double) {
                    return Formatter.format((Double) value);
                } else {
                    if ("[hh:mm]".equals(layout)) {
                        return decimal2hmm(value);
                    }
                }
            } catch (Exception e) {
            }
            return value != null ? value.toString() : "";
        }

    /** Convert a decimal number of hours into the format h:mm. */
    private static String decimal2hmm(Object value) {
        Number n = null;
        if (value != null) {
            if (value instanceof Number)
                n = (Number) value;
            else {
                try {
                    n = Parser.parseDecimal(value.toString());
                } catch (Exception ignore) {
                }
            }
        }

        String output = null;
        if (n == null)
            output = "0:00";
        else {
            long h = (long) Math.floor(n.doubleValue());
            long m = Math.round((n.doubleValue() - h) * 60);
            output = "" + h + ':' + (m < 10 ? "0" + m : "" + m);
        }
        return output;
    }
}
