import erest.EUtilClient;


public class Main {

    public static void main(String[] args)
            throws Exception
    {
        EUtilClient util = new EUtilClient();

        util.esearchGIByLocusID("Eukaryota");

        System.out.println(util.toString());
    }


}