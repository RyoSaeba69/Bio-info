package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by antoine on 3/28/15.
 */

@XmlRootElement(name="GBSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Genoms {

    @XmlElement(name="GBSeq")
    private ArrayList<Genom> allGenoms;

    public Genoms() {
    }

    public ArrayList<Genom> getAllGenoms() {
        return allGenoms;
    }

    public void setAllGenoms(ArrayList<Genom> allGenoms) {
        this.allGenoms = allGenoms;
    }
}
