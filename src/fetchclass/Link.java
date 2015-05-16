package fetchclass;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by antoine on 5/12/15.
 */
@XmlRootElement(name="Link")
@XmlAccessorType(XmlAccessType.FIELD)
public class Link {

    @XmlElement(name="Id")
    private String id;

    public Link() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
