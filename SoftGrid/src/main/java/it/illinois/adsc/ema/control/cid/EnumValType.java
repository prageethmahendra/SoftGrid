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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for EnumValType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EnumValType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="ord" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnumValType", namespace = "http://www.iec.ch/61850/2003/SCL", propOrder = {
    "value"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class EnumValType {

    @XmlValue
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String value;
    @XmlAttribute(name = "ord")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected Byte ord;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the ord property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public Byte getOrd() {
        return ord;
    }

    /**
     * Sets the value of the ord property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2016-10-24T04:18:37+08:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setOrd(Byte value) {
        this.ord = value;
    }

}
