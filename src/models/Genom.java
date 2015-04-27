package models;

import java.io.File;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import bioadapters.SequenceAdapter;

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

    public Genom() {}

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
