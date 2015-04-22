package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by antoine on 4/22/15.
 */
@XmlRootElement(name="GBInterval")
@XmlAccessorType(XmlAccessType.FIELD)
public class GBInterval {

    @XmlElement(name="GBInterval_from")
    private int intervalFrom;

    @XmlElement(name="GBInterval_to")
    private int intervalTo;

    public GBInterval() {
    }

    public int getIntervalFrom() {
        return intervalFrom;
    }

    public void setIntervalFrom(int intervalFrom) {
        this.intervalFrom = intervalFrom;
    }

    public int getIntervalTo() {
        return intervalTo;
    }

    public void setIntervalTo(int intervalTo) {
        this.intervalTo = intervalTo;
    }
}
