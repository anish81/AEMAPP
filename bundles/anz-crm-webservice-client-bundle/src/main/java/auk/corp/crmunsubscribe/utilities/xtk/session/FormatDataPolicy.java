
package auk.corp.crmunsubscribe.xtk.session;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessiontoken" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="strDataPolicy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="strValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sessiontoken",
    "strDataPolicy",
    "strValue"
})
@XmlRootElement(name = "FormatDataPolicy")
public class FormatDataPolicy {

    @XmlElement(required = true)
    protected String sessiontoken;
    @XmlElement(required = true)
    protected String strDataPolicy;
    @XmlElement(required = true)
    protected String strValue;

    /**
     * Gets the value of the sessiontoken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessiontoken() {
        return sessiontoken;
    }

    /**
     * Sets the value of the sessiontoken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessiontoken(String value) {
        this.sessiontoken = value;
    }

    /**
     * Gets the value of the strDataPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrDataPolicy() {
        return strDataPolicy;
    }

    /**
     * Sets the value of the strDataPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrDataPolicy(String value) {
        this.strDataPolicy = value;
    }

    /**
     * Gets the value of the strValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrValue() {
        return strValue;
    }

    /**
     * Sets the value of the strValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrValue(String value) {
        this.strValue = value;
    }

}
