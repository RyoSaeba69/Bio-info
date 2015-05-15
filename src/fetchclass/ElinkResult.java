package fetchclass;

import models.Feature;

import javax.xml.bind.annotation.*;
import java.util.Vector;

/**
 * Created by antoine on 5/12/15.
 */

@XmlRootElement(name="eLinkResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElinkResult {

    @XmlElementWrapper(name="LinkSet")
    @XmlElement(name="LinkSetDb")
    private Vector<LinkSetDb> linkSetDbs;

    public ElinkResult() {
    }

    public Vector<LinkSetDb> getLinkSetDbs() {
        return linkSetDbs;
    }

    public void setLinkSetDbs(Vector<LinkSetDb> linkSetDbs) {
        this.linkSetDbs = linkSetDbs;
    }

    public Vector<String> getAllLinkIds(){
        Vector<String> allIds = new Vector<String>();

        for(LinkSetDb lsd : this.linkSetDbs){
            for(Link link : lsd.getLinks()){
                allIds.add(link.getId());
            }
        }
            return allIds;
    }
}
