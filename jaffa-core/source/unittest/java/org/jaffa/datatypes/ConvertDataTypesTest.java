package org.jaffa.datatypes;

import java.lang.reflect.Method;
import junit.framework.TestCase;
import org.jaffa.datatypes.DataTypeMapper;

public class ConvertDataTypesTest extends TestCase {
    
    static {
        org.jaffa.util.LoggerHelper.init();
    }
    
    public ConvertDataTypesTest(String name) {
        super(name);
    }
    
    public void testConvertStringToBoolean() {
        assertEquals(Boolean.TRUE, DataTypeMapper.instance().map("true",Boolean.class));
        assertEquals(Boolean.TRUE, DataTypeMapper.instance().map("True",Boolean.class));
        assertEquals(Boolean.TRUE, DataTypeMapper.instance().map("TRUE",Boolean.class));
        assertEquals(Boolean.TRUE, DataTypeMapper.instance().map("T",Boolean.class));
        assertEquals(Boolean.TRUE, DataTypeMapper.instance().map("1",Boolean.class));
        assertEquals(Boolean.TRUE, DataTypeMapper.instance().map("Y",Boolean.class));

        assertEquals(Boolean.FALSE, DataTypeMapper.instance().map("false",Boolean.class));
        assertEquals(Boolean.FALSE, DataTypeMapper.instance().map("False",Boolean.class));
        assertEquals(Boolean.FALSE, DataTypeMapper.instance().map("FALSE",Boolean.class));
        assertEquals(Boolean.FALSE, DataTypeMapper.instance().map("F",Boolean.class));
        assertEquals(Boolean.FALSE, DataTypeMapper.instance().map("0",Boolean.class));
        assertEquals(Boolean.FALSE, DataTypeMapper.instance().map("N",Boolean.class));

        assertEquals(Boolean.FALSE, DataTypeMapper.instance().map("xxx",Boolean.class));
    }

    public void testConvertStringToboolean() {
        assertEquals(Boolean.TRUE, DataTypeMapper.instance().map("true",Boolean.TYPE));
        assertEquals(Boolean.FALSE, DataTypeMapper.instance().map("false",Boolean.TYPE));
    }
    
    public void testConvertStringToShort() {
        assertEquals(new Short((short)100), DataTypeMapper.instance().map("100",Short.class));
    }
    
    public void testConvertStringToInteger() {
        assertEquals(new Integer(100), DataTypeMapper.instance().map("100",Integer.class));
    }
    
    public void testConvertStringToint() {
        assertEquals(new Integer(100), DataTypeMapper.instance().map("100",Integer.TYPE));
    }

    public void testConvertStringToLong() {
        assertEquals(new Long(100L), DataTypeMapper.instance().map("100",Long.class));
    }
    
    public void testConvertStringToFloat() {
        assertEquals(new Float(100.0001F), DataTypeMapper.instance().map("100.0001",Float.class));
    }

    public void testConvertStringToDouble() {
        assertEquals(new Double(100.0001), DataTypeMapper.instance().map("100.0001",Double.class));
    }
    
    public void testConvertStringToDateTime() {
        DateTime d = TEST1_DateTime;
        assertEquals(d, DataTypeMapper.instance().map(TEST1_DateTime_STRING,DateTime.class));
    }

    public void testConvertStringToDateOnly() {
        DateOnly d = TEST1_DateOnly;
        assertEquals(d, DataTypeMapper.instance().map(TEST1_DateOnly_STRING,DateOnly.class));
    }


    public void testAddConverters() throws Exception {
        String data = "Hello World";
        StringBuffer sb = new StringBuffer(data);
        
        // Convert StringBuffer to String
        DataTypeMapper.instance().addMapping(StringBuffer.class, String.class, 
                 StringBuffer.class.getMethod("toString", new Class[] {}));
        // Convert a StringBuffer to a byte[]
        DataTypeMapper.instance().addMapping(StringBuffer.class, byte[].class,
            new Object[] {
                StringBuffer.class.getMethod("toString", new Class[] {}),
                String.class.getMethod("getBytes", new Class[] {})
            });
            
        Object obj = DataTypeMapper.instance().map(sb, String.class);
        assertEquals(String.class, obj.getClass());
        assertEquals(data, obj);

        obj = DataTypeMapper.instance().map(sb, byte[].class);
        assertEquals(byte[].class, obj.getClass());
        //System.out.println("--->" + new String( (byte[])obj ));
        //System.out.println("--->" + new String( data.getBytes() ));
        //assertEquals(data.getBytes()..toString(), obj.toString());
        assertEquals(data, new String( (byte[])obj )); 
            
        // Remove Mapping            
        assertTrue( DataTypeMapper.instance().removeMapping(StringBuffer.class, String.class));
        assertTrue( DataTypeMapper.instance().removeMapping(StringBuffer.class, byte[].class));
    }
    
    public void testAddClassConverters() throws Exception {
        String data = "Hello World";
        StringBuffer sb = new StringBuffer(data);

        // Add converters based on a class, 1 public one should be added
        assertEquals(1, DataTypeMapper.instance().addMappings(Converter.class, true) ); 

        // Add converters based on a class, 1 public, 1 private one should be added
        assertEquals(2, DataTypeMapper.instance().addMappings(Converter.class, false) ); 
        
        Object obj = DataTypeMapper.instance().map(sb, String.class);
        assertEquals(String.class, obj.getClass());
        assertEquals(data, obj);

        obj = DataTypeMapper.instance().map(sb, byte[].class);
        assertEquals(byte[].class, obj.getClass());
        assertEquals(data, new String( (byte[])obj )); 
            
        // Remove Mapping            
        assertTrue( DataTypeMapper.instance().removeMapping(StringBuffer.class, String.class));
        assertTrue( DataTypeMapper.instance().removeMapping(StringBuffer.class, byte[].class));
    }
                    
    // Convert Strings to Java Primatives
    public void testPrimativeConversion() throws Exception {
        // Input parameters are of mixed types
        Object[] params = {"true","127","1234567890.1234","1234.4567","1234567","123456789","1234"};
        // Find method with specific parameter types
        Method m = this.getClass().getDeclaredMethod("localTest1", new Class[]
            {Boolean.TYPE, Byte.TYPE,Double.TYPE,Float.TYPE,Integer.TYPE,Long.TYPE,Short.TYPE});
        // Convert input parameters
        for(int i=0; i< m.getParameterTypes().length; i++) {
            //System.out.println("Convert " + params[i].getClass() + " to " + m.getParameterTypes()[i]);
            params[i] = DataTypeMapper.instance().map( params[i], m.getParameterTypes()[i] );
        }
        m.invoke(this, params);
    }

    // Convert Strings to Java Primatives Wrappers
    public void testPrimativeWrapperConversion() throws Exception {
        // Input parameters are of mixed types
        Object[] params = {"true","127","1234567890.1234","1234.4567","1234567","123456789","1234"};
        // Find method with specific parameter types
        Method m = this.getClass().getDeclaredMethod("localTest1", new Class[]
            {Boolean.class, Byte.class,Double.class,Float.class,Integer.class,Long.class,Short.class});
        // Convert input parameters
        for(int i=0; i< m.getParameterTypes().length; i++) {
            //System.out.println("Convert " + params[i].getClass() + " to " + m.getParameterTypes()[i]);
            params[i] = DataTypeMapper.instance().map( params[i], m.getParameterTypes()[i] );
        }
        m.invoke(this, params);
    }

    // Convert Strings to Additional Jaffa Data Types
    public void testJaffaTypesConversion() throws Exception {
        // Input parameters are of mixed types
        Object[] params = {TEST1_DateOnly_STRING, TEST1_DateTime_STRING, TEST1_Double.toString()};
        // Find method with specific parameter types
        Method m = this.getClass().getDeclaredMethod("localTest1", new Class[]
            {DateOnly.class, DateTime.class, Currency.class});
        // Convert input parameters
        for(int i=0; i< m.getParameterTypes().length; i++) {
            //System.out.println("Convert " + params[i].getClass() + " to " + m.getParameterTypes()[i]);
            params[i] = DataTypeMapper.instance().map( params[i], m.getParameterTypes()[i] );
        }
        m.invoke(this, params);
    }

    // Convert Strings to Additional Jaffa Data Types with special formatting
    public void testJaffaParserConversion() throws Exception {
        // Input parameters are of mixed types
        Object[] params = {"t-10", "n+1", "1,234,567,890.1234"};
        // Find method with specific parameter types
        Method m = this.getClass().getDeclaredMethod("localTest2", new Class[]
            {DateOnly.class, DateTime.class, Currency.class});
        // Convert input parameters
        for(int i=0; i< m.getParameterTypes().length; i++) {
            //System.out.println("Convert " + params[i].getClass() + " to " + m.getParameterTypes()[i]);
            params[i] = DataTypeMapper.instance().map( params[i], m.getParameterTypes()[i] );
        }
        m.invoke(this, params);
    }

    // Convert Strings to Java Primatives via Parse
    public void testPrimativeParserConversion() throws Exception {
        // Input parameters are of mixed types
        Object[] params = {"T","127","1,234,567,890.1234","1,234.4567","1,234,567","123,456,789","1,234"};
        // Find method with specific parameter types
        Method m = this.getClass().getDeclaredMethod("localTest1", new Class[]
            {Boolean.TYPE, Byte.TYPE,Double.TYPE,Float.TYPE,Integer.TYPE,Long.TYPE,Short.TYPE});
        // Convert input parameters
        for(int i=0; i< m.getParameterTypes().length; i++) {
            //System.out.println("Convert " + params[i].getClass() + " to " + m.getParameterTypes()[i]);
            params[i] = DataTypeMapper.instance().map( params[i], m.getParameterTypes()[i] );
        }
        m.invoke(this, params);
    }
    
    // Convert Strings to Java Primatives via Parse
    public void testPrimativeWrapperParserConversion() throws Exception {
        // Input parameters are of mixed types
        Object[] params = {"T","127","1,234,567,890.1234","1,234.4567","1,234,567","123,456,789","1,234"};
        // Find method with specific parameter types
        Method m = this.getClass().getDeclaredMethod("localTest1", new Class[]
            {Boolean.class, Byte.class,Double.class,Float.class,Integer.class,Long.class,Short.class});
        // Convert input parameters
        for(int i=0; i< m.getParameterTypes().length; i++) {
            //System.out.println("Convert " + params[i].getClass() + " to " + m.getParameterTypes()[i]);
            params[i] = DataTypeMapper.instance().map( params[i], m.getParameterTypes()[i] );
        }
        m.invoke(this, params);
    }

    private static boolean TEST1_boolean = true;
    private static byte TEST1_byte = 127;
    private static double TEST1_double = 1234567890.1234D;
    private static float TEST1_float = 1234.4567F;
    private static int TEST1_int = 1234567;
    private static long TEST1_long = 123456789L;
    private static short TEST1_short = 1234;
    private static Boolean TEST1_Boolean = Boolean.TRUE;
    private static Byte TEST1_Byte = new Byte(TEST1_byte);
    private static DateOnly TEST1_DateOnly = null;
    private static DateTime TEST1_DateTime = null;
    private static Double TEST1_Double = new Double(TEST1_double);
    private static Float TEST1_Float = new Float(TEST1_float);
    private static Integer TEST1_Integer = new Integer(TEST1_int);
    private static Long TEST1_Long = new Long(TEST1_long);
    private static Short TEST1_Short = new Short(TEST1_short);
    private static String TEST1_String = "Abcde";
    private static String TEST1_DateTime_STRING = "10/11/2004 12:13:14";
    private static String TEST1_DateOnly_STRING = "12/31/1999 23:59:59";
    private static Currency TEST1_Currency = new Currency(TEST1_double);

    static {
        try {
            TEST1_DateTime = Parser.parseDateTime(TEST1_DateTime_STRING);
            TEST1_DateOnly = Parser.parseDateOnly(TEST1_DateOnly_STRING);
        } catch (Exception e) {throw new RuntimeException(e.getLocalizedMessage(),e); }
    }

    void localTest1(boolean b,byte by,double d,float f,int i,long l,short s)
    {
        assertEquals(TEST1_boolean, b);
        assertEquals(TEST1_byte, by);
        //assertEquals(TEST1_double, d);
        //assertEquals(TEST1_float, f);
        assertEquals(TEST1_int, i);
        assertEquals(TEST1_long, l);
        assertEquals(TEST1_short, s);
    }
    
    void localTest1(Boolean b,Byte by,Double d,Float f,Integer i,Long l,Short s)
    {
        
        assertEquals(TEST1_Boolean, b);
        assertEquals(TEST1_Byte, by);
        assertEquals(TEST1_Double, d);
        assertEquals(TEST1_Float, f);
        assertEquals(TEST1_Integer, i);
        assertEquals(TEST1_Long, l);
        assertEquals(TEST1_Short, s);
    }
    
    void localTest1(DateOnly d, DateTime dt, Currency c)
    {
        assertEquals(TEST1_DateOnly, d);
        assertEquals(TEST1_DateTime, dt);
        assertEquals(TEST1_Currency, c);
    }

    void localTest2(DateOnly d, DateTime dt, Currency c)
    {
        //System.out.println("Date Only = " + d);
        assertEquals(DateOnly.addDay(new DateOnly(),-10).dayOfYear(), d.dayOfYear());
        //System.out.println("Date Time = " + dt);
        assertEquals(DateTime.addDay(new DateTime(),1).dayOfYear(), dt.dayOfYear());
        //System.out.println("Currency = " + c);
        assertEquals(TEST1_Currency, c);
    }


    /** Runs the test suite.
     * @param args The input args are not used.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(ConvertDataTypesTest.class);
    }
}


