//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.02 at 09:05:28 AM PDT 
//


package org.jaffa.presentation.portlet.component.componentdomain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paramTypesEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="paramTypesEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="string"/>
 *     &lt;enumeration value="int"/>
 *     &lt;enumeration value="boolean"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "paramTypesEnum")
@XmlEnum
public enum ParamTypesEnum {

    @XmlEnumValue("string")
    STRING("string"),
    @XmlEnumValue("int")
    INT("int"),
    @XmlEnumValue("boolean")
    BOOLEAN("boolean");
    private final String value;

    ParamTypesEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ParamTypesEnum fromValue(String v) {
        for (ParamTypesEnum c: ParamTypesEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
