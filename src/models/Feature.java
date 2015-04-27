package models;

import java.util.Arrays;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

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

    @XmlElementWrapper(name="GBFeature_intervals")
    @XmlElement(name="GBInterval")
    private Vector<GBInterval> intervals;

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

    public Vector<GBInterval> getIntervals() {
        return intervals;
    }

    public void setIntervals(Vector<GBInterval> intervals) {
        this.intervals = intervals;
    }

    public boolean isCDS(){
        return this.key.equals(CDS_NAME);
    }

    public boolean isUsableCds(){

        boolean isUsable = true;

        Vector<String> strToRemove = new Vector<String>(Arrays.asList("complement", "join", "(", ")"));
        String loc = this.getLocation();
        for(String str : strToRemove){
            loc.replace(str, "");
        }
        String regexp = "^([0-9]+\\.\\.[0-9]+,)*[0-9]+\\.\\.[0-9]+$";

        if (loc.matches(regexp)) {
            for(int i =0; i < this.intervals.size() && isUsable;i++){
                GBInterval currentInter = this.intervals.get(i);
                if(currentInter.getIntervalFrom() >= currentInter.getIntervalTo()){
                    isUsable = false;
                }
            }
        } else {
            isUsable = false;
        }
        return loc.matches(regexp);
    }


}
