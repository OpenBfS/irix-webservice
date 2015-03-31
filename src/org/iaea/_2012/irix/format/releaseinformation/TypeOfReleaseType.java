//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.01.14 at 09:56:57 AM CET 
//


package org.iaea._2012.irix.format.releaseinformation;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TypeOfReleaseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TypeOfReleaseType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="Gaseous"/>
 *     &lt;enumeration value="Particulate"/>
 *     &lt;enumeration value="Gaseous and Particulate"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TypeOfReleaseType")
@XmlEnum
public enum TypeOfReleaseType {

    @XmlEnumValue("Gaseous")
    GASEOUS("Gaseous"),
    @XmlEnumValue("Particulate")
    PARTICULATE("Particulate"),
    @XmlEnumValue("Gaseous and Particulate")
    GASEOUS_AND_PARTICULATE("Gaseous and Particulate");
    private final String value;

    TypeOfReleaseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TypeOfReleaseType fromValue(String v) {
        for (TypeOfReleaseType c: TypeOfReleaseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
