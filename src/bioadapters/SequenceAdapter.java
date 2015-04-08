package bioadapters;

import models.Sequence;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by antoine on 4/7/15.
 */
public class SequenceAdapter extends XmlAdapter<String, Sequence>{

    @Override
    public Sequence unmarshal(String s) throws Exception {
        return new Sequence(s);
    }

    @Override
    public String marshal(Sequence sequence) throws Exception {
        return sequence.toString();
    }
}
