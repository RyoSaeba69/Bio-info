package fetchclass;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by antoine on 3/24/15.
 */

@XmlRootElement(name="eSearchResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class ESearchId {

    @XmlElement(name="Count")
    private int count;

    @XmlElement(name="RetMax")
    private int retmax;

    @XmlElement(name="RetStart")
    private int retstart;

    @XmlElementWrapper(name="IdList")
    @XmlElement(name="Id")
    private ArrayList<String> ids;

    public ESearchId() {

    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRetmax() {
        return retmax;
    }

    public void setRetmax(int retmax) {
        this.retmax = retmax;
    }

    public int getRetstart() {
        return retstart;
    }

    public void setRetstart(int retstart) {
        this.retstart = retstart;
    }
}
