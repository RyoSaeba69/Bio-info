import java.io.*;
import org.biojava.bio.symbol.*;
import org.biojava.bio.seq.*;
import org.biojava.bio.seq.io.*;

public class GCContent {
    public static void main(String[] args)
            throws Exception
    {
        if (args.length != 1)
            throw new Exception("usage: java GCContent filename.fa");
        String fileName = args[0];

        // Set up sequence iterator

        BufferedReader br = new BufferedReader(
                new FileReader(fileName));
        SequenceIterator stream = SeqIOTools.readFastaDNA(br);

        // Iterate over all sequences in the stream

        while (stream.hasNext()) {
            Sequence seq = stream.nextSequence();
            int gc = 0;
            for (int pos = 1; pos <= seq.length(); ++pos) {
                Symbol sym = seq.symbolAt(pos);
                if (sym == DNATools.g() || sym == DNATools.c())
                    ++gc;
            }
            System.out.println(seq.getName() + ": " +
                    ((gc * 100.0) / seq.length()) +
                    "%");
        }
    }
}