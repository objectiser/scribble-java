//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-146 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.01.30 at 04:43:27 PM GMT 
//


package org.scribble.protocol.monitor.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Scope complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Scope">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.scribble.org/monitor}Node">
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="innerIndex" type="{http://www.w3.org/2001/XMLSchema}int" default="-1" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Scope")
@XmlSeeAlso({
    TryNode.class
})
public class Scope
    extends Node
{

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "innerIndex")
    protected Integer innerIndex;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the innerIndex property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getInnerIndex() {
        if (innerIndex == null) {
            return -1;
        } else {
            return innerIndex;
        }
    }

    /**
     * Sets the value of the innerIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInnerIndex(Integer value) {
        this.innerIndex = value;
    }

}