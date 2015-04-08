package bioutils;

import fetchclass.ESearchId;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;

/**
 * Created by antoine on 3/28/15.
 */
public final class BioXMLUtils {

    public static Object XMLToClass(String xmlString, Class classType ){

        Object res = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(classType);

            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            spf.setFeature("http://xml.org/sax/features/validation", false);

            StringReader reader = new StringReader(xmlString);


            XMLReader xmlReader = spf.newSAXParser().getXMLReader();
            InputSource inputSource = new InputSource(reader);

            SAXSource source = new SAXSource(xmlReader, inputSource);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            res = unmarshaller.unmarshal(source);
        } catch(Exception e){
            e.printStackTrace();
        }

        return res;
    }

}
