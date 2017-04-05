
package auk.corp.crmunsubscribe.utilities.alz.aemsoapinterface;

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
 *         &lt;element name="pId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "pId",
    "mId",
    "flag"
})
@XmlRootElement(name = "alzUnsub")
public class AlzUnsub {

    @XmlElement(required = true)
    protected String sessiontoken;
    @XmlElement(required = true)
    protected String pId;
    protected int mId;
    @XmlElement(required = true)
    protected String flag;

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
     * Gets the value of the pId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPId() {
        return pId;
    }

    /**
     * Sets the value of the pId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPId(String value) {
        this.pId = value;
    }

    /**
     * Gets the value of the mId property.
     * 
     */
    public int getMId() {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     */
    public void setMId(int value) {
        this.mId = value;
    }

    /**
     * Gets the value of the flag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag(String value) {
        this.flag = value;
    }

}
