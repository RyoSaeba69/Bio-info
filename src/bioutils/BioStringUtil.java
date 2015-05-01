package bioutils;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by antoine on 4/7/15.
 */
public class BioStringUtil {

    private HashMap<String, Integer> initHmAnalysis;

    public static Vector<String> groupByString(String str, int groupLength){

        Vector<String> res = new Vector<String>();

        for(int i = 0; (i+groupLength) <= str.length();i += groupLength){
            res.add(str.substring(i, i+groupLength));
        }

        return res;
    }

    public static int countTrinucleotide(Vector<String> vSeq, String tri){

        int nbTri = 0;

        for(String currentTri : vSeq){
            if(currentTri.equals(tri.toLowerCase())){
                nbTri++;
            }
        }

        return nbTri;
    }

    public static String join (Vector<HashMap<String, Integer>> intervals, String sSeq){

        String joinedSeq = "";

        for(HashMap<String, Integer> interval : intervals){
            joinedSeq += sSeq.substring((interval.get("from") - 1), interval.get("to"));
        }

        return joinedSeq;
    }

    public static String complement (String seq){
        return seq;
    }

    public static String complement(Vector<HashMap<String, Integer>> intervals, String sSeq){
        return complement(join(intervals, sSeq));
    }

    public static HashMap<String, Integer> processTriAnalysis(String seq){

        HashMap<String, Integer> hmRes = new HashMap<String, Integer>();




        return hmRes;
    }

	public HashMap<String, Integer> getInitHmAnalysis() {
		return initHmAnalysis;
	}

	public void setInitHmAnalysis(HashMap<String, Integer> initHmAnalysis) {
		this.initHmAnalysis = initHmAnalysis;
	}

}
