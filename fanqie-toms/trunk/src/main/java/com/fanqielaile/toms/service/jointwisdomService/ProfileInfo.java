package com.fanqielaile.toms.service.jointwisdomService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wangdayin on 2016/1/21.
 */
@XmlRootElement(name = "ProfileInfo", namespace = "http://www.opentravel.org/OTA/2003/05")
public class ProfileInfo {
    private UniqueID uniqueID;
    private Profile profile;

    @XmlElement(name = "Profile", namespace = "http://www.opentravel.org/OTA/2003/05")
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @XmlElement(name = "UniqueID", namespace = "http://www.opentravel.org/OTA/2003/05")
    public UniqueID getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(UniqueID uniqueID) {
        this.uniqueID = uniqueID;
    }
}
