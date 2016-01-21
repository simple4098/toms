package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "Profile", namespace = "http://www.opentravel.org/OTA/2003/05")
public class Profile {
    private String profileType;
    private List<PersonName> personNames;

    @XmlElement(name = "PersonName", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElementWrapper(name = "Customer")
    public List<PersonName> getPersonNames() {
        return personNames;
    }

    public void setPersonNames(List<PersonName> personNames) {
        this.personNames = personNames;
    }

    @XmlAttribute(name = "ProfileType")
    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }
}
