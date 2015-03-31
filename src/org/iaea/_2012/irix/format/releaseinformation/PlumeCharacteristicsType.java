//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.01.14 at 09:56:57 AM CET 
//


package org.iaea._2012.irix.format.releaseinformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.iaea._2012.irix.format.base.AngleType;


/**
 * <p>Java class for PlumeCharacteristicsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlumeCharacteristicsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransportSpeed" type="{http://www.iaea.org/2012/IRIX/Format/ReleaseInformation}TransportSpeedType" minOccurs="0"/>
 *         &lt;element name="TransportDirection" type="{http://www.iaea.org/2012/IRIX/Format/Base}AngleType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlumeCharacteristicsType", propOrder = {
    "transportSpeed",
    "transportDirection"
})
public class PlumeCharacteristicsType {

    @XmlElement(name = "TransportSpeed")
    protected TransportSpeedType transportSpeed;
    @XmlElement(name = "TransportDirection")
    protected AngleType transportDirection;

    /**
     * Gets the value of the transportSpeed property.
     * 
     * @return
     *     possible object is
     *     {@link TransportSpeedType }
     *     
     */
    public TransportSpeedType getTransportSpeed() {
        return transportSpeed;
    }

    /**
     * Sets the value of the transportSpeed property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportSpeedType }
     *     
     */
    public void setTransportSpeed(TransportSpeedType value) {
        this.transportSpeed = value;
    }

    /**
     * Gets the value of the transportDirection property.
     * 
     * @return
     *     possible object is
     *     {@link AngleType }
     *     
     */
    public AngleType getTransportDirection() {
        return transportDirection;
    }

    /**
     * Sets the value of the transportDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleType }
     *     
     */
    public void setTransportDirection(AngleType value) {
        this.transportDirection = value;
    }

}
