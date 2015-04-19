package models;

import java.lang.reflect.Array;
import java.util.Arrays;
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

    private boolean isStopCodon(String codon){
        return stopCodon.contains(codon);
    }

    private boolean isInitCodon(String codon){
        return initCodon.contains(codon);
    }

    public boolean isValid(){
        return true;
    }
}
