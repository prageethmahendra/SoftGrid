//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.24 at 04:18:37 PM SGT 
//


package it.illinois.adsc.ema.control.cid;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="History" type="{http://www.iec.ch/61850/2003/SCL}HistoryType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="toolID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="nameStructure" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderType", namespace = "http://www.iec.ch/61850/2003/SCL", propOrder = {
    "history"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class HeaderType {

    @XmlElement(name = "History", namespace = "http://www.iec.ch/61850/2003/SCL", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected HistoryType history;
    @XmlAttribute(name = "id")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String id;
    @XmlAttribute(name = "toolID")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String toolID;
    @XmlAttribute(name = "nameStructure")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String nameStructure;

    /**
     * Gets the value of the history property.
     * 
     * @return
     *     possible object is
     *     {@link HistoryType }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public HistoryType getHistory() {
        return history;
    }

    /**
     * Sets the value of the history property.
     * 
     * @param value
     *     allowed object is
     *     {@link HistoryType }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setHistory(HistoryType value) {
        this.history = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the toolID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getToolID() {
        return toolID;
    }

    /**
     * Sets the value of the toolID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setToolID(String value) {
        this.toolID = value;
    }

    /**
     * Gets the value of the nameStructure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getNameStructure() {
        return nameStructure;
    }

    /**
     * Sets the value of the nameStructure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNameStructure(String value) {
        this.nameStructure = value;
    }

}
