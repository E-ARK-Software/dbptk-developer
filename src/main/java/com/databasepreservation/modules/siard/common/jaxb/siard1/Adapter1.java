//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.24 at 06:16:58 PM WEST 
//

package com.databasepreservation.modules.siard.common.jaxb.siard1;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1 extends XmlAdapter<String, DateTime> {

        public DateTime unmarshal(String value) {
                return (com.databasepreservation.utils.JodaUtils.xs_date_parse(value));
        }

        public String marshal(DateTime value) {
                return (com.databasepreservation.utils.JodaUtils.xs_date_format(value));
        }

}
