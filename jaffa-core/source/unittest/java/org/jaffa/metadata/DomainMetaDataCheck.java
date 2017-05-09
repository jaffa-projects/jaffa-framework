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

/*
 * DomainMetaDataCheck.java
 *
 * Created on June 22, 2001, 10:34 AM
 *
 * 1- Check if a XxxMeta.class has the methods:
 *       a- public static String getName()
 *       b- public static String[] getAttributes()
 *       c- public static FieldMetaData[] getFieldMetaData()
 *       d- public static FieldMetaData getFieldMetaData(String fieldName)
 * 2- Check if method-1-a returns the correct String identifying the corresponding Domain object
 * 3- Check if the String(s) returned by method-1-b are accessible via 'public static final String constant(s)' and vice versa
 * 4- Check if the FieldMetaData(s) returned by method-1-c are accessible via 'public static final FieldMetaData constant(s)' and vice versa
 * 5- Check if the getAttributes() & getFieldMetaData() methods return values in sync
 * 6- Check if method-1-d works correctly
 * 7- Check if each FieldMetaData has a corresponding getter/setter/updater (with the correct data-type) in the corresponding Domain object
 *
 */

package org.jaffa.metadata;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;


/**
 *
 * @author  GautamJ
 * @version
 */
public class DomainMetaDataCheck extends Object {
    private Class m_metaClass = null;
    private Class m_domainClass = null;
    private List m_errors = new ArrayList();
    private static final int m_maxErrors = 20;
    private static final String PUBLIC = "public", PRIVATE = "private"
    , PROTECTED = "protected", ABSTRACT = "abstract", STATIC = "static"
    , FINAL = "final", SYNCHRONIZED = "synchronized";
    private static final String KEY1 = "key1", KEY2 = "key2";

    public DomainMetaDataCheck(String metaClassNameWithPackage)
    throws ClassNotFoundException {
        this( metaClassNameWithPackage, null);
    }


    public DomainMetaDataCheck(String metaClassNameWithPackage,
    String domainClassNameWithPackage)
    throws ClassNotFoundException {
        m_metaClass = Class.forName(metaClassNameWithPackage);

        if ( domainClassNameWithPackage == null )
            domainClassNameWithPackage = extractDomainClassName(metaClassNameWithPackage);
        m_domainClass = Class.forName(domainClassNameWithPackage);
    }


    public void performChecks() throws DomainMetaDataCheckException {
        // 1- Check if a XxxMeta.class has the required methods:
        Method getNameMethod = getMethod(m_metaClass, "getName", null,
        String.class, new int[]{Modifier.PUBLIC, Modifier.STATIC});

        Method getRefNameMethod = getMethod(m_metaClass, "getRefName", null,
        String.class, new int[]{Modifier.PUBLIC, Modifier.STATIC});

        Method getAttributesMethod = getMethod(m_metaClass, "getAttributes", null,
        String[].class, new int[]{Modifier.PUBLIC, Modifier.STATIC});

        Method getFieldMetaDataMethod = getMethod(m_metaClass, "getFieldMetaData", null,
        org.jaffa.metadata.FieldMetaData[].class
        , new int[]{Modifier.PUBLIC, Modifier.STATIC});

        Method getFieldMetaDataStringMethod = getMethod(m_metaClass, "getFieldMetaData", new Class[] {String.class},
        org.jaffa.metadata.FieldMetaData.class,
        new int[]{Modifier.PUBLIC, Modifier.STATIC});


        // 2- Check if method-1-a returns the correct String identifying
        // the corresponding Domain object
        checkGetName(getNameMethod);

        // 3- Check if method-1-b returns the correct String identifying
        // the corresponding Domain Ref
        checkGetRefName(getRefNameMethod);


        // 4- Check if the String(s) returned by method-1-c are accessible via
        // 'public static final String constant(s)' and vice versa
        String[] attributes = checkGetAttributes(getAttributesMethod);


        // 5- Check if the FieldMetaData(s) returned by method-1-d are
        // accessible via 'public static final FieldMetaData constant(s)' and vice versa
        FieldMetaData[] metaFields = checkGetFieldMetaData(getFieldMetaDataMethod);

        // 6- Check if the getAttributes() & getFieldMetaData() methods return values in sync
        checkAttributeAndField(attributes, metaFields);

        // 7- Check if method-1-e works correctly
        checkGetFieldMetaDataString(getFieldMetaDataStringMethod, attributes);


        // 8- Check if each FieldMetaData has a corresponding getter/setter/updater
        // (with the correct data-type) in the corresponding Domain object
        checkMetaAndDomainSync(metaFields);



        // Finally- throw an exception if errors exist
        checkErrors();
    }



    private String extractDomainClassName(String metaClassName) {
        String domainClassName = null;
        int i = metaClassName.lastIndexOf("Meta");
        if ( i > 0 )
            domainClassName = metaClassName.substring(0, i);
        return domainClassName;
    }

    private String extractRefName(String domainClassName) {
        String refName = null;
        if (domainClassName != null) {
            StringTokenizer st = new StringTokenizer(domainClassName, ".");
            while ( st.hasMoreTokens() )
                refName = st.nextToken();
        }
        return refName == null ? "Ref" : refName + "Ref";
    }

    private void log(String msg) throws DomainMetaDataCheckException {
        if ( m_errors.size() >= m_maxErrors ) {
            // Too many errors. Throw an exception
            checkErrors();
        } else {
            m_errors.add(msg);
        }
    }

    private void checkErrors() throws DomainMetaDataCheckException {
        if ( !m_errors.isEmpty() ) {
            StringBuffer buf = new StringBuffer();
            if ( m_errors.size() >= m_maxErrors )
                buf.append( "Too many errors(" + m_maxErrors + "+) !" );
            else
                buf.append( m_errors.size() + " error(s) found");

            for ( Iterator itr = m_errors.iterator(); itr.hasNext(); ) {
                buf.append( '\n' );
                buf.append( '\n' );
                buf.append( itr.next() );
            }
            m_errors.clear();
            throw new DomainMetaDataCheckException( buf.toString() );
        }
    }


    private Method getMethod(Class clazz, String methodName, Class[] parameterTypes,
    Class returnType, int[] modifiers)
    throws DomainMetaDataCheckException {
        // local variables
        Method m = null;

        StringBuffer parametersBuf = null, modifierBuf = null;

        boolean isAbstract = false, isFinal = false, isPrivate = false,
        isProtected = false, isPublic = false, isStatic = false,
        isSynchronized = false;

        String returnTypeString = returnType.isArray() ?
        returnType.getComponentType().getName() + "[]" : returnType.getName();

        String space = " ";

        // create the parameters buffer
        parametersBuf = new StringBuffer();
        if ( parameterTypes != null ) {
            for (int i = 0; i < parameterTypes.length; i++) {
                if ( i > 0 ) parametersBuf.append(", ");
                Class parameterClass = parameterTypes[i];
                String parameter = parameterClass.isArray() ?
                parameterClass.getComponentType().getName() + "[]" :
                    parameterClass.getName();
                    parametersBuf.append(parameter);
            }
        }

        // create the modifiers buffer & also set the booleans
        if (modifiers != null) {
            Arrays.sort(modifiers);
            modifierBuf = new StringBuffer();
            if ( Arrays.binarySearch( modifiers, Modifier.PUBLIC ) >= 0 ) {
                isPublic = true;
                modifierBuf.append(PUBLIC);
                modifierBuf.append(space);
            }
            if ( Arrays.binarySearch( modifiers, Modifier.PRIVATE ) >= 0 ) {
                isPrivate = true;
                modifierBuf.append(PRIVATE);
                modifierBuf.append(space);
            }
            if ( Arrays.binarySearch( modifiers, Modifier.PROTECTED ) >= 0 ) {
                isProtected = true;
                modifierBuf.append(PROTECTED);
                modifierBuf.append(space);
            }
            if ( Arrays.binarySearch( modifiers, Modifier.ABSTRACT ) >= 0 ) {
                isAbstract = true;
                modifierBuf.append(ABSTRACT);
                modifierBuf.append(space);
            }
            if ( Arrays.binarySearch( modifiers, Modifier.STATIC ) >= 0 ) {
                isStatic = true;
                modifierBuf.append(STATIC);
                modifierBuf.append(space);
            }
            if ( Arrays.binarySearch( modifiers, Modifier.FINAL ) >= 0 ) {
                isFinal = true;
                modifierBuf.append(FINAL);
                modifierBuf.append(space);
            }
            if ( Arrays.binarySearch( modifiers, Modifier.SYNCHRONIZED ) >= 0 ) {
                isSynchronized = true;
                modifierBuf.append(SYNCHRONIZED);
                modifierBuf.append(space);
            }
        }

        try {
            // check if method with the required signature exists
            m = clazz.getDeclaredMethod(methodName, parameterTypes);

            // check the return-type
            if ( !m.getReturnType().getName().equals( returnType.getName() ) )
                throw new NoSuchMethodException();

            // check the modifier
            // NOTE: This isnt an exact match !!!
            // for eg. the method could very well be 'final', so long as it matches the input criteria...
            // should work.. unless someone complains.
            int mod = m.getModifiers();
            if ( isPublic && !Modifier.isPublic(mod) )
                throw new NoSuchMethodException();
            if ( isPrivate && !Modifier.isPrivate(mod) )
                throw new NoSuchMethodException();
            if ( isProtected && !Modifier.isProtected(mod) )
                throw new NoSuchMethodException();
            if ( isAbstract && !Modifier.isAbstract(mod) )
                throw new NoSuchMethodException();
            if ( isStatic && !Modifier.isStatic(mod) )
                throw new NoSuchMethodException();
            if ( isFinal && !Modifier.isFinal(mod) )
                throw new NoSuchMethodException();
            if ( isSynchronized && !Modifier.isSynchronized(mod) )
                throw new NoSuchMethodException();
        } catch(NoSuchMethodException e) {
            log( "Method " + clazz.getName() + ": "
            + modifierBuf + returnTypeString + " "
            + methodName + "(" + parametersBuf + ") not found");
        }
        return m;
    }


    private void checkGetName(Method m) throws DomainMetaDataCheckException {
        String methodName = "String getName()";
        String nameFromMethod = null;
        String expectedName = null;

        if ( m != null ) {
            try {
                nameFromMethod = (String) m.invoke(null,null);
                expectedName = m_domainClass.getName();
                if (nameFromMethod == null) {
                    log( "Method  - " + methodName + " - does not return any value" );
                }else if ( !nameFromMethod.equals(expectedName) ) {
                    log( "Method  - " + methodName + " - returns an invalid value '" + nameFromMethod
                    + "', instead of the expected value '" + expectedName + "'");
                }
            } catch (Exception e) {
                log( "Error in invoking the method  - " + methodName );
            }
        } else {
            log( "Method  - " + methodName + " - has not been defined, hence cannot be tested");
        }
    }

    private void checkGetRefName(Method m) throws DomainMetaDataCheckException {
        String methodName = "String getRefName()";
        String nameFromMethod = null;
        String expectedName = null;

        if ( m != null ) {
            try {
                nameFromMethod = (String) m.invoke(null,null);
                expectedName = extractRefName( m_domainClass.getName() );
                if (nameFromMethod == null) {
                    log( "Method  - " + methodName + " - does not return any value" );
                }else if ( !nameFromMethod.equals(expectedName) ) {
                    log( "Method  - " + methodName + " - returns an invalid value '" + nameFromMethod
                    + "', instead of the expected value '" + expectedName + "'");
                }
            } catch (Exception e) {
                log( "Error in invoking the method  - " + methodName );
            }
        } else {
            log( "Method  - " + methodName + " - has not been defined, hence cannot be tested");
        }
    }

    private String[] checkGetAttributes(Method m) throws DomainMetaDataCheckException {
        String methodName = "String[] getAttributes()";
        String[] attributesFromMethod = null;
        Field[] publicStaticFields = null;
        Object[] attributesFromFields = null;

        if ( m != null ) {
            try {
                // get the attributes from the method
                attributesFromMethod = (String[]) m.invoke(null,null);
                if (attributesFromMethod != null ) Arrays.sort( attributesFromMethod );

                // get the array of 'public static String' fields
                publicStaticFields = getPublicStaticFields( m_metaClass, String.class );

                // get the array of objects from the 'public static String' fields
                if (publicStaticFields != null) attributesFromFields = getPublicStaticFieldValues(publicStaticFields);
                if (attributesFromFields != null) Arrays.sort( attributesFromFields );

                if (publicStaticFields != null && attributesFromFields == null) {
                    log( "Error in reading the values for the 'public static String' fields" );
                } else if ( attributesFromMethod != null || attributesFromFields != null ) {
                    if ( !Arrays.equals(attributesFromMethod, attributesFromFields) ) {
                        StringBuffer msg = new StringBuffer();
                        msg.append("There is a mismatch between the values returned by the method '");
                        msg.append(methodName);
                        msg.append("' and the 'public static String' fields" );
                        Map map = compareArrays(attributesFromMethod, attributesFromFields);
                        if ( map != null ) {
                            Object obj1 = map.get(KEY1);
                            Object obj2 = map.get(KEY2);
                            if ( obj1 != null ) {
                                msg.append("\nMethod returns the following extra attributes:\n");
                                msg.append(obj1);
                            }
                            if ( obj2 != null ) {
                                msg.append("\nThe following extra attributes are available from the fields:\n");
                                msg.append(obj2);
                            }
                        }
                        log( msg.toString() );
                    }
                }
            } catch (Exception e) {
                log( "Error in invoking the method  - " + methodName );
            }
        } else {
            log( "Method  - " + methodName + " - has not been defined, hence cannot be tested");
        }
        return attributesFromMethod;
    }

    private Field[] getPublicStaticFields( Class clazz, Class dataType ) {
        // retrieve public fields & add the 'static String' to the list
        Field[] fields = clazz.getFields();
        List list = new ArrayList();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            int mod = field.getModifiers();
            if ( Modifier.isStatic(mod) && field.getType().getName().equals( dataType.getName() ) )
                list.add(field);
        }

        // create an array from the list
        // tried the list.toArray().. but no success. maybe directly create an array instead of the list.
        Field[] out = null;
        if ( !list.isEmpty() ) {
            out = new Field[list.size()];
            int i = 0;
            for (Iterator itr = list.iterator(); itr.hasNext(); i++)
                out[i] = (Field) itr.next();
        }
        return out;
    }

    public Object[] getPublicStaticFieldValues(Field[] fields) {
        Object[] out = null;
        if ( fields != null ) {
            out = new Object[fields.length];
            try {
                for (int i = 0; i < fields.length; i++)
                    out[i] = fields[i].get(null);
            } catch (IllegalAccessException e) {
                // this should never happen since we are accessing public fields !!
                out = null;
            }
        }
        return out;
    }

    private Map compareArrays(Object[] array1, Object[] array2) {
        Map map = new HashMap();
        StringBuffer buf1 = new StringBuffer();
        StringBuffer buf2 = new StringBuffer();
        if (array1 != null) {
            for (int i = 0; i < array1.length; i++) {
                if ( array2 == null || Arrays.binarySearch(array2, array1[i]) < 0 ) {
                    if (buf1.length() > 0) buf1.append('\n');
                    buf1.append( array1[i].toString() );
                }
            }
        }

        if (array2 != null) {
            for (int i = 0; i < array2.length; i++) {
                if ( array1 == null || Arrays.binarySearch(array1, array2[i]) < 0 ) {
                    if (buf2.length() > 0) buf2.append('\n');
                    buf2.append( array2[i].toString() );
                }
            }
        }

        if (buf1.length() > 0) map.put( KEY1, buf1.toString() );
        if (buf2.length() > 0) map.put( KEY2, buf2.toString() );
        return map;
    }

    private FieldMetaData[] checkGetFieldMetaData(Method m) throws DomainMetaDataCheckException {
        String methodName = "FieldMetaData[] getFieldMetaData()";
        FieldMetaData[] attributesFromMethod = null;
        Field[] publicStaticFields = null;
        Object[] attributesFromFields = null;

        if ( m != null ) {
            try {
                // get the attributes from the method
                attributesFromMethod = (FieldMetaData[]) m.invoke(null,null);
                if (attributesFromMethod != null ) Arrays.sort( attributesFromMethod );

                // get the array of 'public static String' fields
                publicStaticFields = getPublicStaticFields( m_metaClass, FieldMetaData.class );

                // get the array of objects from the 'public static String' fields
                if (publicStaticFields != null) attributesFromFields = getPublicStaticFieldValues(publicStaticFields);
                if (attributesFromFields != null) Arrays.sort( attributesFromFields );

                if (publicStaticFields != null && attributesFromFields == null) {
                    log( "Error in reading the values for the 'public static FieldMetaData' fields" );
                } else if ( attributesFromMethod != null || attributesFromFields != null ) {
                    if ( !Arrays.equals(attributesFromMethod, attributesFromFields) ) {
                        StringBuffer msg = new StringBuffer();
                        msg.append("There is a mismatch between the values returned by the method '");
                        msg.append(methodName);
                        msg.append("' and the 'public static FieldMetaData' fields" );
                        Map map = compareArrays(attributesFromMethod, attributesFromFields);
                        if ( map != null ) {
                            Object obj1 = map.get(KEY1);
                            Object obj2 = map.get(KEY2);
                            if ( obj1 != null ) {
                                msg.append("\nMethod returns the following extra attributes:\n");
                                msg.append(obj1);
                            }
                            if ( obj2 != null ) {
                                msg.append("\nThe following extra attributes are available from the fields:\n");
                                msg.append(obj2);
                            }
                        }
                        log( msg.toString() );
                    }
                }
            } catch (Exception e) {
                log( "Error in invoking the method  - " + methodName );
            }
        } else {
            log( "Method  - " + methodName + " - has not been defined, hence cannot be tested");
        }
        return attributesFromMethod;
    }

    private void checkAttributeAndField(String[] attributes, FieldMetaData[] metaFields)
    throws DomainMetaDataCheckException {
        if (attributes == null && metaFields == null) return;

        // create a String-array from the FieldMetaData-array
        String[] metaFieldAttributes = null;
        if ( metaFields != null ) {
            metaFieldAttributes = new String[metaFields.length];
            for (int i = 0; i < metaFields.length; i++)
                metaFieldAttributes[i] = metaFields[i].getName();
            Arrays.sort( metaFieldAttributes );
        }

        if ( !Arrays.equals(attributes, metaFieldAttributes) ) {
            StringBuffer msg = new StringBuffer();
            msg.append("There is a mismatch between the values returned by the method 'getAttributes()' and those returned by the method 'getFieldMetaData()'" );
            Map map = compareArrays(attributes, metaFieldAttributes);
            if ( map != null ) {
                Object obj1 = map.get(KEY1);
                Object obj2 = map.get(KEY2);
                if ( obj1 != null ) {
                    msg.append("\nMethod 'getAttributes()' returns the following extra attributes:\n");
                    msg.append(obj1);
                }
                if ( obj2 != null ) {
                    msg.append("\nMethod 'getFieldMetaData()' returns the following extra attributes:\n");
                    msg.append(obj2);
                }
            }
            log( msg.toString() );
        }
    }

    // check if the method returns a value for each value in the string
    private void checkGetFieldMetaDataString(Method m, String[] attributes)
    throws DomainMetaDataCheckException {
        String methodName = "FieldMetaData getFieldMetaData(String)";

        if ( m != null ) {
            try {
                if ( attributes != null ) {
                    StringBuffer buf = new StringBuffer();
                    for (int i = 0; i < attributes.length; i++) {
                        Object obj = m.invoke( null, new Object[]{attributes[i]} );
                        if ( obj == null ) buf.append( attributes[i] );
                    }
                    if ( buf.length() > 0 ) {
                        buf.insert(0, "Method  - " + methodName + " - does not return values for the following attributes:\n" );
                        log( buf.toString() );
                    }
                }
            } catch (Exception e) {
                log( "Error in invoking the method  - " + methodName );
            }
        } else {
            log( "Method  - " + methodName + " - has not been defined, hence cannot be tested");
        }
    }

    private void checkMetaAndDomainSync(FieldMetaData[] metaFields) throws DomainMetaDataCheckException {
        if ( metaFields != null ) {
            for (int i = 0; i < metaFields.length; i++) {
                FieldMetaData metaField = metaFields[i];
                String name = toFirstCharUpperCase( metaField.getName() );
                String dataType = metaField.getDataType();
                Class dataTypeClass = org.jaffa.datatypes.Defaults.getClass(dataType);

                // check the getter
                getMethod(m_domainClass, "get" + name, null, dataTypeClass, new int[]{Modifier.PUBLIC});

                // check the setter
                getMethod(m_domainClass, "set" + name, new Class[] {dataTypeClass}, void.class, new int[]{Modifier.PUBLIC});

                // check the updater
                getMethod(m_domainClass, "update" + name, new Class[] {dataTypeClass}, void.class, new int[]{Modifier.PUBLIC});
            }
        }
    }

    private String toFirstCharUpperCase(String in) {
        String out = null;
        if ( in != null ) {
            out = "" + Character.toUpperCase( in.charAt(0) )
            + ( (in.length() > 1) ? in.substring(1) : "" );
        }
        return out;
    }

    // example use
    public static void main(String[] args) {
        try {
            DomainMetaDataCheck chk = null;
            if (args.length == 1)
                chk = new DomainMetaDataCheck(args[0]);
            else if (args.length == 2)
                chk = new DomainMetaDataCheck(args[0], args[1]);
            else
                throw new Error("Usage: <metaClassNameWithPackage> OR <metaClassNameWithPackage> <domainClassNameWithPackage>");

            System.out.println("Checking meta class : " + chk.m_metaClass);
            System.out.println("and domain class    : " + chk.m_domainClass);
            chk.performChecks();
            System.out.println("Finished Checks");
        } catch (DomainMetaDataCheckException e) {
            System.out.println( e.getMessage() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
