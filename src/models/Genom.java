package models;

import bioadapters.SequenceAdapter;
import bioutils.BioStringUtil;
import erest.BioHashMap;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.File;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by antoine on 2/17/15.
 */

@XmlRootElement(name="GBSeq")
@XmlAccessorType(XmlAccessType.FIELD)
public class Genom {

	@XmlElement(name="GBSeq_taxonomy")
    private String taxonomy;

//    @XmlElement(name="GBSeq_sequence")
//    private String sequence;

    @XmlElement(name="GBSeq_organism")
    private String organism;

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    @XmlJavaTypeAdapter(SequenceAdapter.class)
    @XmlElement(name="GBSeq_sequence")
    private Sequence sequence;

    @XmlElement(name="GBSeq_length")
    private int sequenceLength;

    @XmlElementWrapper(name="GBSeq_feature-table")
    @XmlElement(name="GBFeature")
    private Vector<Feature> featureTable;

    public Genom() {}

    public Vector<Feature> getFeatureTable() {
        return featureTable;
    }

    public void setFeatureTable(Vector<Feature> featureTable) {
        this.featureTable = featureTable;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public int getSequenceLength() {
        return sequenceLength;
    }

    public void setSequenceLength(int sequenceLength) {
        this.sequenceLength = sequenceLength;
    }
    
    @Override
   	public String toString() {
   		return "Genom [taxonomy=" + taxonomy + ", sequence=" + sequence
   				+ ", sequenceLength=" + sequenceLength + "]";
   	}

    public void createFiles(){
        String taxonomySeparator = "; ";
        String basePath = this.taxonomy.replaceAll(taxonomySeparator, File.pathSeparator);
        File newDirectories = new File(basePath);
        if(newDirectories.mkdirs()){
            System.out.println("Directory successfully created : "+basePath);
        } else {
            System.out.println("Failed to create directory : "+basePath);
        }
    }

    public Vector<Feature> findCDSFeatures(){

        Vector<Feature> vCdsFeature = new Vector<Feature>();

        for(Feature f : this.getFeatureTable()){
            if(f.isCDS()){
                vCdsFeature.add(f);
            }
        }
        return vCdsFeature;
    }

    public HashMap<String, TriInfo> genCountTri(int phase, String sSeq){

        Vector<String> phasedVSeq = Sequence.findVseqByPhase(phase, sSeq.toLowerCase());

        int totalTri = 0;
        HashMap<String, Integer> hmCount = BioHashMap.initAnalysisHm();

        for (Map.Entry<String, Integer> pair : hmCount.entrySet()) {
            String key = pair.getKey();
            int nbTri = BioStringUtil.countTrinucleotide(phasedVSeq, key.toLowerCase());
            totalTri += nbTri;
            hmCount.put(key, nbTri);
        }

        HashMap<String, TriInfo> hmRes = new HashMap<String, TriInfo>();

        for (Map.Entry<String, Integer> pair : hmCount.entrySet()) {
            String key = pair.getKey();
            int count = pair.getValue();

            TriInfo newTriInfo = new TriInfo();

            newTriInfo.setPhCount(count);

            if (totalTri > 0) {
                newTriInfo.setPbCount((count * 100) / totalTri);
            } else {
                newTriInfo.setPbCount(0);
            }

            hmRes.put(key, newTriInfo);
        }

        return hmRes;
    }

    public GenStats generateStats (){
        GenStats resStats = new GenStats();

        resStats.setName(this.organism);

        Sequence seq = this.getSequence();

        int unUsedCds = 0;

        Vector<Feature> allCds = this.findCDSFeatures();
        resStats.setNbCds(allCds.size());

        for(Feature cds : allCds){

            if(!cds.isUsableCds(seq.getSequence().length())){
                unUsedCds++;
            } else {
                String usedSeq = seq.applyFeatures(cds);

                if(Sequence.isValid(usedSeq)) {
                    // there is 3 phases
                    Vector<HashMap<String, TriInfo>> allPhases = new Vector<HashMap<String, TriInfo>>();
                    for(int p = 0; p < 3;p++) {
                        HashMap<String, TriInfo> hmRes = this.genCountTri(p, usedSeq);

                        switch (p){
                            case 0:
                                resStats.setPh0Trinucleotide(hmRes);
                                resStats.setPh0Total((usedSeq.length() / 3));
                                break;
                            case 1:
                                resStats.setPh1Trinucleotide(hmRes);
                                resStats.setPh1Total((usedSeq.length() / 3));
                                break;
                            case 2:
                                resStats.setPh2Trinucleotide(hmRes);
                                resStats.setPh2Total((usedSeq.length() / 3));
                                break;
                        }

                        allPhases.add(hmRes);
                    }
                    resStats.addNewPhTrinucleotide(allPhases);

                } else {
                    unUsedCds++;
                }
            }
        }

        resStats.setUnUsedCds(unUsedCds);

        return resStats;
    }
}
