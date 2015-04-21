package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by antoine on 4/21/15.
 */
@XmlRootElement(name="GBFeature")
@XmlAccessorType(XmlAccessType.FIELD)
public class Feature {

    public static String CDS_NAME = "CDS";

    @XmlElement(name="GBFeature_key")
    private String key;

    @XmlElement(name="GBFeature_location")
    private String location;

    public Feature() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isCDS(){
        return this.key.equals(CDS_NAME);
    }

    // TODO Implement
    // return tab
    public Vector<HashMap<String, String>> findIntervals(Sequence seq){

        if(!this.key.equals(CDS_NAME)){
            // Don't handle other features
            return null;
        }


        String strSeq = seq.getSequence();

        return null;


    }

}
