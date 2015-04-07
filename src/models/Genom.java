package models;

import bioadapters.SequenceAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

    public Genom() {

    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

//    public String getSequence() {
//        return sequence;
//    }
//
//    public void setSequence(String sequence) {
//        this.sequence = sequence;
//    }


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
}
