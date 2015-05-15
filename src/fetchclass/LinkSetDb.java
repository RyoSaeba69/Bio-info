package fetchclass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Vector;

/**
 * Created by antoine on 5/15/15.
 */
@XmlRootElement(name="LinkSetDb")
@XmlAccessorType(XmlAccessType.FIELD)
public class LinkSetDb {

    @XmlElement(name="Link")
    public Vector<Link> links;

    public LinkSetDb() {
    }

    public Vector<Link> getLinks() {
        return links;
    }

    public void setLinks(Vector<Link> links) {
        this.links = links;
    }


}
