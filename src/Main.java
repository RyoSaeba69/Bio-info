import com.sun.deploy.util.StringUtils;
import erest.BioHashMap;
import erest.EUtilClient;

import java.util.ArrayList;
import java.util.StringJoiner;


public class Main {
	// coucou
    public static void main(String[] args)
            throws Exception
    {
        EUtilClient util = new EUtilClient();

        BioHashMap<String, String> opts = new BioHashMap<String, String>();
      //  util.esearchId("eukaryota", opts);
        ArrayList<String> allIds = util.esearchAllId("eukaryota", opts);
        String seqRes = util.efetchSeqByIds(new ArrayList<String>(allIds.subList(0, 20)));
        System.out.println("SeqRes => "+seqRes);
       // String xml = util.efetchSeqById(allIds.get(0));
      //  String idcoma = StringUtils.join(allIds, ",");
        //System.out.println(idcoma);

    }


}