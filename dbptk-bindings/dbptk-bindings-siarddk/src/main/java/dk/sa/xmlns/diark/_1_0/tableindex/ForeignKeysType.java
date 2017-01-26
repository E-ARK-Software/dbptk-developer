//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.21 at 01:32:34 PM CEST 
//

package dk.sa.xmlns.diark._1_0.tableindex;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * List of foreign keys
 * 
 * <p>
 * Java class for foreignKeysType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="foreignKeysType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="foreignKey" type="{http://www.sa.dk/xmlns/diark/1.0}foreignKeyType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "foreignKeysType", propOrder = {"foreignKey"})
public class ForeignKeysType {

  @XmlElement(required = true)
  protected List<ForeignKeyType> foreignKey;

  /**
   * Gets the value of the foreignKey property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the foreignKey property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getForeignKey().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link ForeignKeyType }
   * 
   * 
   */
  public List<ForeignKeyType> getForeignKey() {
    if (foreignKey == null) {
      foreignKey = new ArrayList<ForeignKeyType>();
    }
    return this.foreignKey;
  }

}
