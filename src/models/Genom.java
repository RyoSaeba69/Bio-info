package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by antoine on 2/17/15.
 */

@XmlRootElement(name="GBSeq")
@XmlAccessorType(XmlAccessType.FIELD)
public class Genom {

    @XmlElement(name="GBSeq_taxonomy")
    private String taxonomy;

    @XmlElement(name="GBSeq_sequence")
    private String sequence;

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

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public int getSequenceLength() {
        return sequenceLength;
    }

    public void setSequenceLength(int sequenceLength) {
        this.sequenceLength = sequenceLength;
    }
}
