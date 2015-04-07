package bioutils;

import java.util.ArrayList;

/**
 * Created by antoine on 4/7/15.
 */
public class BioStringUtil {
    public static ArrayList<String> groupByString(String str, int groupLength){

        ArrayList<String> res = new ArrayList<String>();

        for(int i = 0; (i+groupLength) <= str.length();i += groupLength){
            res.add(str.substring(i, i+groupLength));
        }

        return res;
    }
}
