package erest;

import java.util.*;

/**
 * Created by antoine on 2/17/15.
 */
@SuppressWarnings("serial")
public class BioHashMap<K, V> extends HashMap<K, V> {

    public static Vector<String> initVector = new Vector<String>(Arrays.asList(
            "aaa", "aac", "aag",
            "aat", "aca", "acc",
            "acg", "act", "aga",
            "agc", "agg", "agt",
            "ata", "atc", "atg",
            "att", "caa", "cac",
            "cag", "cat", "cca",
            "ccc", "ccg", "cct",
            "cga", "cgc", "cgg",
            "cgt", "cta", "ctc",
            "ctg", "ctt", "gaa",
            "gac", "gag", "gat",
            "gca", "gcc", "gcg",
            "gct", "gga", "ggc",
            "ggg", "ggt", "gta",
            "gtc", "gtg", "gtt",
            "taa", "tac", "tag",
            "tat", "tca", "tcc",
            "tcg", "tct", "tga",
            "tgc", "tgg", "tgt",
            "tta", "ttc", "ttg",
            "ttt"));

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

    public static HashMap<String, Integer> initAnalysisHm() {

        HashMap<String, Integer> initHm = new HashMap<String, Integer>();
        for(String tri : initVector){
            initHm.put(tri, 0);
        }

        return initHm;
    }

}
