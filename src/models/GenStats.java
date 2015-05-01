package models;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by antoine on 4/21/15.
 */
public class GenStats {

    private String name;
    private String path;
    private int nbCds;
    private int nbTrinucleotide;
    private int unUsedCds;
    private int ph0Total;
    private int ph1Total;
    private int ph2Total;
    private int totalTrinucleotide;

    private Vector<Vector<HashMap<String, TriInfo>>> phTrinucleotide;

    private HashMap<String, TriInfo> ph0Trinucleotide;
    private HashMap<String, TriInfo> ph1Trinucleotide;
    private HashMap<String, TriInfo> ph2Trinucleotide;

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

}
