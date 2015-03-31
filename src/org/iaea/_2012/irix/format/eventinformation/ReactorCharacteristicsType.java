//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.01.14 at 09:56:57 AM CET 
//


package org.iaea._2012.irix.format.eventinformation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.iaea._2012.irix.format.base.FreeTextType;


/**
 * <p>Java class for ReactorCharacteristicsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReactorCharacteristicsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="TypeOfReactor" type="{http://www.iaea.org/2012/IRIX/Format/EventInformation}TypeOfReactorType"/>
 *           &lt;element name="TypeOfReactorDescription" type="{http://www.iaea.org/2012/IRIX/Format/Base}ShortStringType"/>
 *         &lt;/choice>
 *         &lt;element name="ThermalPower" type="{http://www.iaea.org/2012/IRIX/Format/EventInformation}PowerUnitType" minOccurs="0"/>
 *         &lt;element name="ElectricalPower" type="{http://www.iaea.org/2012/IRIX/Format/EventInformation}PowerUnitType" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.iaea.org/2012/IRIX/Format/Base}FreeTextType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReactorCharacteristicsType", propOrder = {
    "typeOfReactor",
    "typeOfReactorDescription",
    "thermalPower",
    "electricalPower",
    "description"
})
public class ReactorCharacteristicsType {

    @XmlElement(name = "TypeOfReactor")
    protected TypeOfReactorType typeOfReactor;
    @XmlElement(name = "TypeOfReactorDescription")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String typeOfReactorDescription;
    @XmlElement(name = "ThermalPower")
    protected PowerUnitType thermalPower;
    @XmlElement(name = "ElectricalPower")
    protected PowerUnitType electricalPower;
    @XmlElement(name = "Description")
    protected FreeTextType description;

    /**
     * Gets the value of the typeOfReactor property.
     * 
     * @return
     *     possible object is
     *     {@link TypeOfReactorType }
     *     
     */
    public TypeOfReactorType getTypeOfReactor() {
        return typeOfReactor;
    }

    /**
     * Sets the value of the typeOfReactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOfReactorType }
     *     
     */
    public void setTypeOfReactor(TypeOfReactorType value) {
        this.typeOfReactor = value;
    }

    /**
     * Gets the value of the typeOfReactorDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeOfReactorDescription() {
        return typeOfReactorDescription;
    }

    /**
     * Sets the value of the typeOfReactorDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeOfReactorDescription(String value) {
        this.typeOfReactorDescription = value;
    }

    /**
     * Gets the value of the thermalPower property.
     * 
     * @return
     *     possible object is
     *     {@link PowerUnitType }
     *     
     */
    public PowerUnitType getThermalPower() {
        return thermalPower;
    }

    /**
     * Sets the value of the thermalPower property.
     * 
     * @param value
     *     allowed object is
     *     {@link PowerUnitType }
     *     
     */
    public void setThermalPower(PowerUnitType value) {
        this.thermalPower = value;
    }

    /**
     * Gets the value of the electricalPower property.
     * 
     * @return
     *     possible object is
     *     {@link PowerUnitType }
     *     
     */
    public PowerUnitType getElectricalPower() {
        return electricalPower;
    }

    /**
     * Sets the value of the electricalPower property.
     * 
     * @param value
     *     allowed object is
     *     {@link PowerUnitType }
     *     
     */
    public void setElectricalPower(PowerUnitType value) {
        this.electricalPower = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link FreeTextType }
     *     
     */
    public FreeTextType getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link FreeTextType }
     *     
     */
    public void setDescription(FreeTextType value) {
        this.description = value;
    }

}
