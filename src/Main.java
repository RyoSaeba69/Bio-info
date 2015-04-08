import java.util.Vector;

import models.Genom;
import erest.BioHashMap;
import erest.EUtilClient;


public class Main {
    public static void main(String[] args)
            throws Exception
    {
        EUtilClient util = new EUtilClient();

        BioHashMap<String, String> opts = new BioHashMap<String, String>();
        Vector<String> allIds = util.esearchAllId("eukaryota", opts);
        System.out.println("Test allIds : " + allIds.toString());
        Vector<Genom> seqRes = util.efetchGenomsByIds(allIds);

        System.out.println("Test Sequences : " + seqRes.toString());
    }
}
