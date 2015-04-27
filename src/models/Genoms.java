package models;

import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by antoine on 3/28/15.
 */

@XmlRootElement(name="GBSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Genoms {

	@XmlElement(name="GBSeq")
    private Vector<Genom> allGenoms;

    public Genoms() {
    }

    public Vector<Genom> getAllGenoms() {
        return allGenoms;
    }

    public void setAllGenoms(Vector<Genom> allGenoms) {
        this.allGenoms = allGenoms;
    }
    
    @Override
	public String toString() {
		return "Genoms [allGenoms=" + allGenoms + "]";
	}
}
