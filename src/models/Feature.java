package models;

import java.util.Arrays;
import java.util.HashMap;
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

    public boolean isUsableCds(int seqLength){

        boolean isUsable = true;

        Vector<String> strToRemove = new Vector<String>(Arrays.asList("complement", "join", "(", ")"));
        String loc = this.getLocation();
        for(String str : strToRemove){
            loc = loc.replace(str, "");
        }
        String regexp = "^([0-9]+\\.\\.[0-9]+,)*[0-9]+\\.\\.[0-9]+$";

        if (loc.matches(regexp)) {

            Vector<HashMap<String, Integer>> allIntervals = this.findIntervals();

            for(int i =0; i < allIntervals.size() && isUsable;i++){
                HashMap<String, Integer> currentInter = allIntervals.get(i);
                if(currentInter.get("from") >= currentInter.get("to")){
                    isUsable = false;
                }
            }
        } else {
            isUsable = false;
        }
        return loc.matches(regexp);
    }

    public Vector<HashMap<String, Integer>> findIntervals(){



        Vector<HashMap<String, Integer>> res = new Vector<HashMap<String, Integer>>();

        if(!this.isCDS()){
            return res;
        }

        Vector<String> strToRemove = new Vector<String>(Arrays.asList("complement", "join", "(", ")"));
        String loc = this.getLocation();
        for(String str : strToRemove){
            loc = loc.replace(str, "");
        }

//        String regexp = ", | 'join' | 'complement' | \\( | \\) | ' '";

        //Vector<String> intervals = new Vector<String>(Arrays.asList(this.getLocation().split(regexp)));

        String numberRegexp = "\\.\\.";

        Vector<String> intervals = new Vector<String> (Arrays.asList(loc.split(",")));

        for(String singleLoc : intervals){
            String[] bases = singleLoc.split(numberRegexp);
            if(bases.length == 2){
                HashMap<String, Integer> newInterval = new HashMap<String, Integer>();

                newInterval.put("from", Integer.valueOf(bases[0]));
                newInterval.put("to", Integer.valueOf(bases[1]));

                res.add(newInterval);
            }
        }
        return res;
    }

    public Vector<String> findFeatureName(){

        Vector<String> allFeatureNames = new Vector<String>();
        String loc = this.getLocation();

        int joinIndex = loc.indexOf("join");
        int compIndex = loc.indexOf("complement");

        if(joinIndex == -1 && compIndex != -1){
            allFeatureNames.add("complement");
        } else if (joinIndex != -1 && compIndex == -1){
            allFeatureNames.add("join");
        } else if(joinIndex != -1 && compIndex != -1) {
            if(joinIndex < compIndex){
                allFeatureNames.add("join");
                allFeatureNames.add("complement");
            } else {
                allFeatureNames.add("complement");
                allFeatureNames.add("join");
            }

        }
        return allFeatureNames;
    }

}
