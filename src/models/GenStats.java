package models;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sun.tools.javac.jvm.Gen;
import erest.BioHashMap;

/**
 * Created by antoine on 4/21/15.
 */
public class GenStats implements Serializable{

    private String name;
    private String path;
    private int nbCds;
    private int nbTrinucleotide;
    private int unUsedCds;
    private int ph0Total;
    private int ph1Total;
    private int ph2Total;
    private int totalTrinucleotide;
    private int totalHmRes;
    private Vector<String> usedSequence = new Vector<String>();

    private Vector<Vector<HashMap<String, TriInfo>>> phTrinucleotide;

    private HashMap<String, TriInfo> ph0Trinucleotide;
    private HashMap<String, TriInfo> ph1Trinucleotide;
    private HashMap<String, TriInfo> ph2Trinucleotide;


    public static GenStats newEmptyGs(){
        GenStats newGs = new GenStats();
        newGs.setName("");
        newGs.setPath("");
        newGs.setNbCds(0);
        newGs.setNbTrinucleotide(0);
        newGs.setUnUsedCds(0);
        newGs.setPh0Total(0);
        newGs.setPh1Total(0);
        newGs.setPh2Total(0);
        newGs.setTotalTrinucleotide(0);
        newGs.setTotalHmRes(0);

//        Vector<HashMap<String, TriInfo>> allPhases = new Vector<HashMap<String, TriInfo>>();
//        for(int p = 0; p < 3;p++) {
//            HashMap<String, TriInfo> hmRes = Genom.genCountTri(p, "");
//
//            allPhases.add(hmRes);
//        }
//        newGs.addNewPhTrinucleotide(allPhases);

        return newGs;
    }

    public void addSeq(String usedSeq){
        this.usedSequence.add(usedSeq);
    }

    public Vector<String> getUsedSequence() {
        return usedSequence;
    }

    public void setUsedSequence(Vector<String> usedSequence) {
        this.usedSequence = usedSequence;
    }

    public GenStats() {
        this.phTrinucleotide = new Vector<Vector<HashMap<String, TriInfo>>>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getNbCds() {
        return nbCds;
    }

    public void setNbCds(int nbCds) {
        this.nbCds = nbCds;
    }

    public int getNbTrinucleotide() {
        return nbTrinucleotide;
    }

    public void setNbTrinucleotide(int nbTrinucleotide) {
        this.nbTrinucleotide = nbTrinucleotide;
    }

    public int getUnUsedCds() {
        return unUsedCds;
    }

    public void setUnUsedCds(int unUsedCds) {
        this.unUsedCds = unUsedCds;
    }

    public int getPh0Total() {
        return ph0Total;
    }

    public void setPh0Total(int ph0Total) {
        this.ph0Total = ph0Total;
    }

    public int getPh1Total() {
        return ph1Total;
    }

    public void setPh1Total(int ph1Total) {
        this.ph1Total = ph1Total;
    }

    public int getPh2Total() {
        return ph2Total;
    }

    public void setPh2Total(int ph2Total) {
        this.ph2Total = ph2Total;
    }

    public int getTotalHmRes() {
        return totalHmRes;
    }

    public void setTotalHmRes(int totalHmRes) {
        this.totalHmRes = totalHmRes;
    }

    public int getTotalTrinucleotide() {
        return totalTrinucleotide;
    }

    public void setTotalTrinucleotide(int totalTrinucleotide) {
        this.totalTrinucleotide = totalTrinucleotide;
    }

    public HashMap<String, TriInfo> getPh0Trinucleotide() {
        return ph0Trinucleotide;
    }

    public void setPh0Trinucleotide(HashMap<String, TriInfo> ph0Trinucleotide) {
        this.ph0Trinucleotide = ph0Trinucleotide;
    }

    public HashMap<String, TriInfo> getPh1Trinucleotide() {
        return ph1Trinucleotide;
    }

    public void setPh1Trinucleotide(HashMap<String, TriInfo> ph1Trinucleotide) {
        this.ph1Trinucleotide = ph1Trinucleotide;
    }

    public HashMap<String, TriInfo> getPh2Trinucleotide() {
        return ph2Trinucleotide;
    }

    public void setPh2Trinucleotide(HashMap<String, TriInfo> ph2Trinucleotide) {
        this.ph2Trinucleotide = ph2Trinucleotide;
    }

    public Vector<Vector<HashMap<String, TriInfo>>> getPhTrinucleotide() {
        return phTrinucleotide;
    }

    public void setPhTrinucleotide(Vector<Vector<HashMap<String, TriInfo>>> phTrinucleotide) {
        this.phTrinucleotide = phTrinucleotide;
    }

    public void addNewPhTrinucleotide(Vector<HashMap<String, TriInfo>> newPhTri){
        this.phTrinucleotide.add(newPhTri);
    }


    public boolean isUsable(){
        return this.phTrinucleotide != null && this.phTrinucleotide.size() > 0;
    }

    public Vector<HashMap<String, TriInfo>> computeAllTrinucleotide(){

        Vector<HashMap<String, TriInfo>> hmRes = new Vector<HashMap<String, TriInfo>>();

        for(int i =0; i < 3;i++){
            HashMap<String, TriInfo> newHm = new HashMap<String, TriInfo>();
            for(String triKey : BioHashMap.initVector){
                newHm.put(triKey, new TriInfo());
            }
            hmRes.add(newHm);
        }



        for(int i = 0; i < 3; i++){
            for(Vector<HashMap<String, TriInfo>> allHm : this.phTrinucleotide){
                HashMap<String, TriInfo> currentHm = allHm.get(i);
                for(Map.Entry<String, TriInfo> mValue : currentHm.entrySet()){
                    int newCountTri = mValue.getValue().getPhCount() + hmRes.get(i).get(mValue.getKey()).getPhCount();
                    hmRes.get(i).get(mValue.getKey()).setPhCount(newCountTri);
                }
            }
        }

        Vector<TriInfo> allValues = new Vector<TriInfo>();
        allValues.addAll(hmRes.get(0).values());

        double total = 0.0;
        for(TriInfo t : allValues){
            total += t.getPhCount();
        }

        this.totalHmRes = (int) total;

        for(HashMap<String, TriInfo> curHm : hmRes){
            for(Map.Entry<String, TriInfo> meValue : curHm.entrySet()) {
                double percentage = (meValue.getValue().getPhCount() / total) * 100.0;
                meValue.getValue().setPbCount(percentage);
            }
        }

        return hmRes;
    }

    public void mergeWith(GenStats gsToMerge){
//        this.name = gsToMerge.getName();
//        this.path = gsToMerge.getPath();
        this.nbCds += gsToMerge.getNbCds();
        this.nbTrinucleotide += gsToMerge.getNbTrinucleotide();
        this.unUsedCds += gsToMerge.getUnUsedCds();
        this.ph0Total += gsToMerge.getPh0Total();
        this.ph1Total += gsToMerge.getPh1Total();
        this.ph2Total += gsToMerge.getPh2Total();
        this.totalTrinucleotide += gsToMerge.getTotalTrinucleotide();
        this.totalHmRes += gsToMerge.getTotalHmRes();

            for(Vector<HashMap<String, TriInfo>> allHm : gsToMerge.phTrinucleotide){
                this.addNewPhTrinucleotide(allHm);
            }
    }

    public void serializeGs(){
        try{

            FileOutputStream fout = new FileOutputStream(GlobalGs.getFilePath(this.path));
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
            oos.close();
            System.out.println("Done " + GlobalGs.getFilePath(this.path));

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static GenStats deserializeGs(String filePath){

        GenStats gs;

        try{

            FileInputStream fin = new FileInputStream(filePath);
            ObjectInputStream ois = new ObjectInputStream(fin);
            gs = (GenStats) ois.readObject();
            ois.close();

            return gs;

        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
