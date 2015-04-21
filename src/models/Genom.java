package models;

import bioadapters.SequenceAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Vector;

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



}
