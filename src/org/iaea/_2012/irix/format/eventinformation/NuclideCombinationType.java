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
 * <p>Java class for NuclideCombinationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NuclideCombinationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="T-Alpha"/>
 *     &lt;enumeration value="T-Beta"/>
 *     &lt;enumeration value="T-Gamma"/>
 *     &lt;enumeration value="R-Beta"/>
 *     &lt;enumeration value="T-Ca"/>
 *     &lt;enumeration value="T-K"/>
 *     &lt;enumeration value="T-Na"/>
 *     &lt;enumeration value="T-U"/>
 *     &lt;enumeration value="Sr+Rare"/>
 *     &lt;enumeration value="I-131(G)"/>
 *     &lt;enumeration value="I-131(P)"/>
 *     &lt;enumeration value="I-131(P+G)"/>
 *     &lt;enumeration value="Cs(134/137)"/>
 *     &lt;enumeration value="Sr(89/90)"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NuclideCombinationType")
@XmlEnum
public enum NuclideCombinationType {

    @XmlEnumValue("T-Alpha")
    T_ALPHA("T-Alpha"),
    @XmlEnumValue("T-Beta")
    T_BETA("T-Beta"),
    @XmlEnumValue("T-Gamma")
    T_GAMMA("T-Gamma"),
    @XmlEnumValue("R-Beta")
    R_BETA("R-Beta"),
    @XmlEnumValue("T-Ca")
    T_CA("T-Ca"),
    @XmlEnumValue("T-K")
    T_K("T-K"),
    @XmlEnumValue("T-Na")
    T_NA("T-Na"),
    @XmlEnumValue("T-U")
    T_U("T-U"),
    @XmlEnumValue("Sr+Rare")
    SR_RARE("Sr+Rare"),
    @XmlEnumValue("I-131(G)")
    I_131_G("I-131(G)"),
    @XmlEnumValue("I-131(P)")
    I_131_P("I-131(P)"),
    @XmlEnumValue("I-131(P+G)")
    I_131_P_G("I-131(P+G)"),
    @XmlEnumValue("Cs(134/137)")
    CS_134_137("Cs(134/137)"),
    @XmlEnumValue("Sr(89/90)")
    SR_89_90("Sr(89/90)");
    private final String value;

    NuclideCombinationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NuclideCombinationType fromValue(String v) {
        for (NuclideCombinationType c: NuclideCombinationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
