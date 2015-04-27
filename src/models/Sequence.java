package models;

import bioutils.BioStringUtil;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;


/**
 * Created by antoine on 4/7/15.
 */

public class Sequence {

    private String sequence;

    private static Vector<String> initCodon =
            new Vector<String>(Arrays.asList("atg", "ctg", "ttg", "gtg", "ata", "atc", "atc", "att", "tta"));

    private static Vector<String> stopCodon =
            new Vector<String>(Arrays.asList("taa", "tag", "tga"));

    public Sequence(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    private static boolean isStopCodon(String codon){
        return stopCodon.contains(codon);
    }

    private static boolean isInitCodon(String codon){
        return initCodon.contains(codon);
    }

    public static boolean isValid(String sSeq)
    {
        String regexp = "[^acgt]";
        boolean isOnlyACGT = !(sSeq.matches(regexp));

        boolean isModThree = ((sSeq.length() % 3 ) == 0);

        boolean isFirstStart = false;
        boolean isLastStop = false;

        if(sSeq.length() > 6) {
            String firstTri = sSeq.substring(0, 3);
            isFirstStart = isInitCodon(firstTri);

            String lastTri = sSeq.substring((sSeq.length() - 3), sSeq.length());
            isLastStop = isStopCodon(lastTri);
        }

        return isModThree && isOnlyACGT && isFirstStart && isLastStop;

    }

    public String join (Vector<HashMap<String, Integer>> intervals){
        return BioStringUtil.join(intervals, this.getSequence());
    }

    public String complement (String seq){
        return BioStringUtil.complement(seq);
    }

    public String complement(Vector<HashMap<String, Integer>> intervals){
        return this.complement(join(intervals));
    }

    public String applyFeatures(Feature f){
        Vector<String> featureNames = f.findFeatureName();

        String resSeq = BioStringUtil.join(f.findIntervals(), this.getSequence());

        for(String fn : featureNames){
            if(fn.equals("complement")){
                resSeq = BioStringUtil.complement(resSeq);
            }
        }
        return resSeq;
    }

    public static Vector<String> findVseqByPhase(int phase, String sSeq){

        String resString = sSeq.toLowerCase();
        for(int i = 0; i < phase;i++){
            String lastChar = resString.substring(resString.length() - 1);
            resString = lastChar + resString.substring(0, (resString.length() - 1));
        }

        return BioStringUtil.groupByString(resString, 3);
    }

}
