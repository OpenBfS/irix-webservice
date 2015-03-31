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
import org.iaea._2012.irix.format.base.InformationBlock;
import org.iaea._2012.irix.format.locations.LocationOrLocationRefType;


/**
 * <p>Java class for EventInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EventInformationType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.iaea.org/2012/IRIX/Format/Base}InformationBlock">
 *       &lt;sequence>
 *         &lt;element name="TypeOfEvent" type="{http://www.iaea.org/2012/IRIX/Format/EventInformation}TypeOfEventType"/>
 *         &lt;element name="TypeOfEventDescription" type="{http://www.iaea.org/2012/IRIX/Format/Base}ShortStringType" minOccurs="0"/>
 *         &lt;element name="DateAndTimeOfEvent" type="{http://www.iaea.org/2012/IRIX/Format/EventInformation}DateAndTimeOfEventType"/>
 *         &lt;element ref="{http://www.iaea.org/2012/IRIX/Format/Locations}Location"/>
 *         &lt;element name="ObjectInvolved" type="{http://www.iaea.org/2012/IRIX/Format/EventInformation}ObjectInvolvedType" minOccurs="0"/>
 *         &lt;element name="EmergencyClassification" type="{http://www.iaea.org/2012/IRIX/Format/EventInformation}EmergencyClassificationType" minOccurs="0"/>
 *         &lt;element name="PlantStatus" type="{http://www.iaea.org/2012/IRIX/Format/EventInformation}PlantStatusType" minOccurs="0"/>
 *         &lt;element name="INESClassification" type="{http://www.iaea.org/2012/IRIX/Format/EventInformation}INESClassificationType" minOccurs="0"/>
 *         &lt;element name="EventDescription" type="{http://www.iaea.org/2012/IRIX/Format/Base}FreeTextType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventInformationType", propOrder = {
    "typeOfEvent",
    "typeOfEventDescription",
    "dateAndTimeOfEvent",
    "location",
    "objectInvolved",
    "emergencyClassification",
    "plantStatus",
    "inesClassification",
    "eventDescription"
})
public class EventInformationType
    extends InformationBlock
{

    @XmlElement(name = "TypeOfEvent", required = true)
    protected TypeOfEventType typeOfEvent;
    @XmlElement(name = "TypeOfEventDescription")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String typeOfEventDescription;
    @XmlElement(name = "DateAndTimeOfEvent", required = true)
    protected DateAndTimeOfEventType dateAndTimeOfEvent;
    @XmlElement(name = "Location", namespace = "http://www.iaea.org/2012/IRIX/Format/Locations", required = true)
    protected LocationOrLocationRefType location;
    @XmlElement(name = "ObjectInvolved")
    protected ObjectInvolvedType objectInvolved;
    @XmlElement(name = "EmergencyClassification")
    protected EmergencyClassificationType emergencyClassification;
    @XmlElement(name = "PlantStatus")
    protected PlantStatusType plantStatus;
    @XmlElement(name = "INESClassification")
    protected INESClassificationType inesClassification;
    @XmlElement(name = "EventDescription")
    protected FreeTextType eventDescription;

    /**
     * Gets the value of the typeOfEvent property.
     * 
     * @return
     *     possible object is
     *     {@link TypeOfEventType }
     *     
     */
    public TypeOfEventType getTypeOfEvent() {
        return typeOfEvent;
    }

    /**
     * Sets the value of the typeOfEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOfEventType }
     *     
     */
    public void setTypeOfEvent(TypeOfEventType value) {
        this.typeOfEvent = value;
    }

    /**
     * Gets the value of the typeOfEventDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeOfEventDescription() {
        return typeOfEventDescription;
    }

    /**
     * Sets the value of the typeOfEventDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeOfEventDescription(String value) {
        this.typeOfEventDescription = value;
    }

    /**
     * Gets the value of the dateAndTimeOfEvent property.
     * 
     * @return
     *     possible object is
     *     {@link DateAndTimeOfEventType }
     *     
     */
    public DateAndTimeOfEventType getDateAndTimeOfEvent() {
        return dateAndTimeOfEvent;
    }

    /**
     * Sets the value of the dateAndTimeOfEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndTimeOfEventType }
     *     
     */
    public void setDateAndTimeOfEvent(DateAndTimeOfEventType value) {
        this.dateAndTimeOfEvent = value;
    }

    /**
     * 
     * 								
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;html:p xmlns:html="http://www.w3.org/1999/xhtml" xmlns:base="http://www.iaea.org/2012/IRIX/Format/Base" xmlns:event="http://www.iaea.org/2012/IRIX/Format/EventInformation" xmlns:loc="http://www.iaea.org/2012/IRIX/Format/Locations" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
     * 									Contains information on the location of the event. 
     * 									For events involving a fixed object (e.g. an installation) 
     * 									the location can also be provided under ObjectInvolved.
     * 								&lt;/html:p&gt;
     * </pre>
     * 
     * 								
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;html:p xmlns:html="http://www.w3.org/1999/xhtml" xmlns:base="http://www.iaea.org/2012/IRIX/Format/Base" xmlns:event="http://www.iaea.org/2012/IRIX/Format/EventInformation" xmlns:loc="http://www.iaea.org/2012/IRIX/Format/Locations" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="note"&gt;
     * 									For the structure of this element, see Locations section.
     * 								&lt;/html:p&gt;
     * </pre>
     * 
     * 							
     * 
     * @return
     *     possible object is
     *     {@link LocationOrLocationRefType }
     *     
     */
    public LocationOrLocationRefType getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationOrLocationRefType }
     *     
     */
    public void setLocation(LocationOrLocationRefType value) {
        this.location = value;
    }

    /**
     * Gets the value of the objectInvolved property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectInvolvedType }
     *     
     */
    public ObjectInvolvedType getObjectInvolved() {
        return objectInvolved;
    }

    /**
     * Sets the value of the objectInvolved property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectInvolvedType }
     *     
     */
    public void setObjectInvolved(ObjectInvolvedType value) {
        this.objectInvolved = value;
    }

    /**
     * Gets the value of the emergencyClassification property.
     * 
     * @return
     *     possible object is
     *     {@link EmergencyClassificationType }
     *     
     */
    public EmergencyClassificationType getEmergencyClassification() {
        return emergencyClassification;
    }

    /**
     * Sets the value of the emergencyClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmergencyClassificationType }
     *     
     */
    public void setEmergencyClassification(EmergencyClassificationType value) {
        this.emergencyClassification = value;
    }

    /**
     * Gets the value of the plantStatus property.
     * 
     * @return
     *     possible object is
     *     {@link PlantStatusType }
     *     
     */
    public PlantStatusType getPlantStatus() {
        return plantStatus;
    }

    /**
     * Sets the value of the plantStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlantStatusType }
     *     
     */
    public void setPlantStatus(PlantStatusType value) {
        this.plantStatus = value;
    }

    /**
     * Gets the value of the inesClassification property.
     * 
     * @return
     *     possible object is
     *     {@link INESClassificationType }
     *     
     */
    public INESClassificationType getINESClassification() {
        return inesClassification;
    }

    /**
     * Sets the value of the inesClassification property.
     * 
     * @param value
     *     allowed object is
     *     {@link INESClassificationType }
     *     
     */
    public void setINESClassification(INESClassificationType value) {
        this.inesClassification = value;
    }

    /**
     * Gets the value of the eventDescription property.
     * 
     * @return
     *     possible object is
     *     {@link FreeTextType }
     *     
     */
    public FreeTextType getEventDescription() {
        return eventDescription;
    }

    /**
     * Sets the value of the eventDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link FreeTextType }
     *     
     */
    public void setEventDescription(FreeTextType value) {
        this.eventDescription = value;
    }

}
