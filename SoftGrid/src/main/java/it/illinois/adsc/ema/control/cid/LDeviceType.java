//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.24 at 04:18:37 PM SGT 
//


package it.illinois.adsc.ema.control.cid;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LDeviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LDeviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LN0" type="{http://www.iec.ch/61850/2003/SCL}LN0Type"/>
 *         &lt;element name="LN" type="{http://www.iec.ch/61850/2003/SCL}LNType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="inst" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="desc" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LDeviceType", namespace = "http://www.iec.ch/61850/2003/SCL", propOrder = {
    "ln0",
    "ln"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class LDeviceType {

    @XmlElement(name = "LN0", namespace = "http://www.iec.ch/61850/2003/SCL", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected LN0Type ln0;
    @XmlElement(name = "LN", namespace = "http://www.iec.ch/61850/2003/SCL")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected List<LNType> ln;
    @XmlAttribute(name = "inst")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String inst;
    @XmlAttribute(name = "desc")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String desc;

    /**
     * Gets the value of the ln0 property.
     * 
     * @return
     *     possible object is
     *     {@link LN0Type }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public LN0Type getLN0() {
        return ln0;
    }

    /**
     * Sets the value of the ln0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link LN0Type }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLN0(LN0Type value) {
        this.ln0 = value;
    }

    /**
     * Gets the value of the ln property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ln property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLN().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LNType }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public List<LNType> getLN() {
        if (ln == null) {
            ln = new ArrayList<LNType>();
        }
        return this.ln;
    }

    /**
     * Gets the value of the inst property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getInst() {
        return inst;
    }

    /**
     * Sets the value of the inst property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setInst(String value) {
        this.inst = value;
    }

    /**
     * Gets the value of the desc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDesc(String value) {
        this.desc = value;
    }

}
