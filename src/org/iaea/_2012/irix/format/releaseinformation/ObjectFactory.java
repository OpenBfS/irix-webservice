//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.01.16 at 10:55:05 AM CET 
//


package org.iaea._2012.irix.format.releaseinformation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.iaea._2012.irix.format.releaseinformation package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NuclideInfo_QNAME = new QName("http://www.iaea.org/2012/IRIX/Format/ReleaseInformation", "NuclideInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.iaea._2012.irix.format.releaseinformation
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.iaea.org/2012/IRIX/Format/ReleaseInformation", name = "NuclideInfo")
    public JAXBElement<Object> createNuclideInfo(Object value) {
        return new JAXBElement<Object>(_NuclideInfo_QNAME, Object.class, null, value);
    }

}
