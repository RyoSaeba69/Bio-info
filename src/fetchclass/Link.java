package fetchclass;

import javax.xml.bind.annotation.*;
import java.util.Vector;

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
