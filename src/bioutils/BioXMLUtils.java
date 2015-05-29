package bioutils;

import java.io.StringReader;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * Created by antoine on 3/28/15.
 */
public final class BioXMLUtils {


    public static HashMap<String, JAXBContext> sinContext = new HashMap<String, JAXBContext>();

    public static JAXBContext getJaxbContext(@SuppressWarnings("rawtypes") Class classType){

        if(!sinContext.containsKey(classType.getName())){
            try {
                sinContext.put(classType.getName(), JAXBContext.newInstance(classType));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return sinContext.get(classType.getName());
    }

    public static Object XMLToClass(String xmlString, @SuppressWarnings("rawtypes") Class classType ){

        Object res = null;

        try {
        	if(xmlString!=null) {
//	            JAXBContext jaxbContext = JAXBContext.newInstance(classType);
	            JAXBContext jaxbContext = getJaxbContext(classType);

	            SAXParserFactory spf = SAXParserFactory.newInstance();
	            spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	            spf.setFeature("http://xml.org/sax/features/validation", false);
	
	            StringReader reader = new StringReader(xmlString);
	
	
	            XMLReader xmlReader = spf.newSAXParser().getXMLReader();
	            InputSource inputSource = new InputSource(reader);
	
	            SAXSource source = new SAXSource(xmlReader, inputSource);
	
	            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	
	            res = unmarshaller.unmarshal(source);
	            
	            unmarshaller = null;
	            source = null;
	            inputSource = null;
	            xmlReader = null;
	            reader.close();
	            spf = null;
	            jaxbContext = null;
	            
        	}
        } catch(UnmarshalException e) {
        	System.out.println("Probleme dans la fonction XMLToClass, xml mal formate : \n"+e);
        	res = null;
        } catch(Exception e){
        	try {
        		System.out.println("Probleme dans la fonction XMLToClass, relance dans 5 secondes : \n"+e);
				Thread.sleep(5000);
				res = XMLToClass(xmlString, classType);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
//            e.printStackTrace();
        }

        return res;
    }

}
