//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.01.14 at 09:56:57 AM CET 
//


package org.iaea._2012.irix.format.eventinformation;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TypeOfReactorType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TypeOfReactorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="PWR"/>
 *     &lt;enumeration value="BWR"/>
 *     &lt;enumeration value="AGR"/>
 *     &lt;enumeration value="VVER"/>
 *     &lt;enumeration value="RBMK"/>
 *     &lt;enumeration value="Candu"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TypeOfReactorType")
@XmlEnum
public enum TypeOfReactorType {

    PWR("PWR"),
    BWR("BWR"),
    AGR("AGR"),
    VVER("VVER"),
    RBMK("RBMK"),
    @XmlEnumValue("Candu")
    CANDU("Candu");
    private final String value;

    TypeOfReactorType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TypeOfReactorType fromValue(String v) {
        for (TypeOfReactorType c: TypeOfReactorType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
