package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "PersonName", namespace = "http://www.opentravel.org/OTA/2003/05")
public class PersonName {
    private String namePrefix;
    private String givenName;
    private String middleName;
    private String surname;

    @XmlElement(name = "NamePrefix", namespace = "http://www.opentravel.org/OTA/2003/05")
    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @XmlElement(name = "GivenName", namespace = "http://www.opentravel.org/OTA/2003/05")
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    @XmlElement(name = "MiddleName", namespace = "http://www.opentravel.org/OTA/2003/05")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @XmlElement(name = "Surname", namespace = "http://www.opentravel.org/OTA/2003/05")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
