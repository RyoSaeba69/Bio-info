package erest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by antoine on 2/17/15.
 */
public class BioHashMap<K, V> extends HashMap<K, V> {

    public String toBioParameters() {

        String returnParameters = "";
//        Iterator it = this.entrySet().iterator();
        HashMap<K, V> cloneHM = (HashMap<K, V>) this.clone();
        Iterator it = cloneHM.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            returnParameters += "&" + pairs.getKey() + "=" + pairs.getValue();
            it.remove();
        }

        return returnParameters;
    }

}
