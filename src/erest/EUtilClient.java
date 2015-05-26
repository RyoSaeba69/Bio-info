package erest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import models.Genom;
import models.Genoms;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import bioutils.BioXMLUtils;

import com.sun.deploy.util.StringUtils;

import fetchclass.ESearchId;
import fetchclass.ElinkResult;


public class EUtilClient {

    private static final String EUTIL_API_ESEARCH_URL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?";
    private static final String EUTIL_API_ESUMMARY_URL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?";
	private static final String EUTIL_API_ELINK_URL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/elink.fcgi?dbfrom=genome&db=nuccore";
    private static final String EUTIL_API_EFETCH_URL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?";
    private static final String FETCH_PARAMETER = "db=nuccore&retmode=xml&rettype=gb";
    private static final String CONST_PARAMETERS = "db=genome&retmode=xml&rettype=gb";//&tool=adfeutils&email=fangx%40mskcc.org";


    public EUtilClient() {
    }



    @SuppressWarnings("unchecked")
	public List<EFetch> eFetchTitleByGi(String gi) {

        String term = "&id=" + gi; // + encode(gi);
        String parameters = CONST_PARAMETERS + term;
        String resturl = EUTIL_API_EFETCH_URL + parameters;
        System.out.println("url: " + resturl);
        Document doc = parseDOM(sendGet(resturl));


        NodeList docSumList = doc.getElementsByTagName("GBReference");
        String locusid = doc.getElementsByTagName("GBSeq_locus").item(0).getTextContent();
        String definition = doc.getElementsByTagName("GBSeq_definition").item(0).getTextContent();
        String organism = doc.getElementsByTagName("GBSeq_source").item(0).getTextContent();

        @SuppressWarnings("rawtypes")
		List efetchList = new ArrayList();


        for (int i = 0; i < docSumList.getLength(); i++) {
            //GBReference_title
            //GBReference_pubmed
            Element ele = (Element) docSumList.item(i);
            String title = ele.getElementsByTagName("GBReference_title").item(0).getTextContent();
            String pubmed = ele.getElementsByTagName("GBReference_pubmed").item(0).getTextContent();


            EFetch efetch = new EFetch(locusid, gi, title, pubmed, definition, organism);

            efetchList.add(efetch);

        }

        return efetchList;
    }


    @SuppressWarnings("unchecked")
	public List<EFetch> eFetchTitleByGi(@SuppressWarnings("rawtypes") List giList) {

        String term = "&id=";

        if (giList.size() > 0) {
            for (int i = 0; i < giList.size(); i++) {
                if (giList.size() - 1 == i) {
                    term = term + giList.get(i);
                } else {
                    term = term + giList.get(i) + ",";
                }
            }
        }

        String parameters = CONST_PARAMETERS + term;
        String resturl = EUTIL_API_EFETCH_URL + parameters;
        System.out.println("url: " + resturl);
        Document doc = parseDOM(sendGet(resturl));

        NodeList docSumList = doc.getElementsByTagName("GBReference");
        String locusid = doc.getElementsByTagName("GBSeq_locus").item(0).getTextContent();
        String definition = doc.getElementsByTagName("GBSeq_definition").item(0).getTextContent();
        String organism = doc.getElementsByTagName("GBSeq_source").item(0).getTextContent();
        String gi = doc.getElementsByTagName("GBSeqid").item(1).getTextContent();
        System.out.println("gi: " + gi);

        @SuppressWarnings("rawtypes")
		List efetchList = new ArrayList();


        for (int i = 0; i < docSumList.getLength(); i++) {
            //GBReference_title
            //GBReference_pubmed
            Element ele = (Element) docSumList.item(i);
            String title = ele.getElementsByTagName("GBReference_title").item(0).getTextContent();
            String pubmed = ele.getElementsByTagName("GBReference_pubmed").item(0).getTextContent();


            EFetch efetch = new EFetch(locusid, gi, title, pubmed, definition, organism);

            efetchList.add(efetch);

        }

        return efetchList;
    }

    public String esummaryTitleByGi(String gi) {

        List<ESummary> esummaryList = esummaryGIByLocusID(gi);
        System.out.println("esummaryList size: " + esummaryList.size());
        return esummaryList.get(0).getTitle();
    }

    @SuppressWarnings("unchecked")
	public List<ESummary> esummaryGIByLocusID(@SuppressWarnings("rawtypes") List giList) {

        String term = "&id=";

        if (giList.size() > 0) {

            for (int i = 0; i < giList.size(); i++) {
                if (giList.size() - 1 == i) {
                    term = term + giList.get(i);
                } else {
                    term = term + giList.get(i) + ",";
                }
            }
        }

        String parameters = CONST_PARAMETERS + term;
        String resturl = EUTIL_API_ESUMMARY_URL + parameters;

        Document doc = parseDOM(sendGet(resturl));

        NodeList docSumList = doc.getElementsByTagName("DocSum");

        @SuppressWarnings("rawtypes")
		List esummaryList = new ArrayList();


        for (int i = 0; i < docSumList.getLength(); i++) {

            Element ele = (Element) docSumList.item(i);
            String id = ele.getElementsByTagName("Id").item(0).getTextContent();
            ESummary esummary = new ESummary(id);
            esummarySetElementItems(esummary, ele.getElementsByTagName("Item"));
            System.out.println(esummary);
            esummaryList.add(esummary);
        }

        return esummaryList;
    }

    @SuppressWarnings("unchecked")
	public List<ESummary> esummaryGIByLocusID(String gi) {

        String term = "&id=" + gi;
        String parameters = CONST_PARAMETERS + term;
        String resturl = EUTIL_API_ESUMMARY_URL + parameters;

        Document doc = parseDOM(sendGet(resturl));

        NodeList docSumList = doc.getElementsByTagName("DocSum");

        @SuppressWarnings("rawtypes")
		List esummaryList = new ArrayList();


        for (int i = 0; i < docSumList.getLength(); i++) {

            Element ele = (Element) docSumList.item(i);
            String id = ele.getElementsByTagName("Id").item(0).getTextContent();
            ESummary esummary = new ESummary(id);
            esummarySetElementItems(esummary, ele.getElementsByTagName("Item"));
            esummaryList.add(esummary);

        }

        return esummaryList;
    }

    public void esummarySetElementItems(ESummary esummary, NodeList e) {
        for (int i = 0; i < e.getLength(); i++) {
            Element element = (Element) e.item(i);
            String attributeName = element.getAttribute("Name");

            if (attributeName.equals("Caption")) {
                esummary.setCaption(element.getTextContent());
            } else if (attributeName.equals("Title")) {
                esummary.setTitle(element.getTextContent());
            } else if (attributeName.equals("Extra")) {
                esummary.setExtra(element.getTextContent());
            } else if (attributeName.equals("Gi")) {
                esummary.setGi(element.getTextContent());
            } else if (attributeName.equals("CreateDate")) {
                esummary.setCreateDate(element.getTextContent());
            } else if (attributeName.equals("UpdateDate")) {
                esummary.setUpdateDate(element.getTextContent());
            } else if (attributeName.equals("Flags")) {
                esummary.setFlags(element.getTextContent());
            } else if (attributeName.equals("TaxId")) {
                esummary.setTaxId(element.getTextContent());
            } else if (attributeName.equals("Length")) {
                esummary.setLength(element.getTextContent());
            } else if (attributeName.equals("Status")) {
                esummary.setStatus(element.getTextContent());
            } else if (attributeName.equals("ReplacedBy")) {
                esummary.setReplacedBy(element.getTextContent());
            } else {
                esummary.setComment(element.getTextContent());
            }
        }

    }

    public String esearchId(String locusID, BioHashMap<String, String> options) {
        String allOptions = options.toBioParameters();

        String term = "&term=" + locusID;
        String parameters = CONST_PARAMETERS + term;
        String resturl = EUTIL_API_ESEARCH_URL + parameters + allOptions;

        return sendGet(resturl);
    }

    public Vector<String> esearchAllId(String locusID, BioHashMap<String, String> options) {
        Vector<String> allIds = new Vector<String>();

        if (!options.containsKey("retmax")) {
            options.put("retmax", "10000");
        }

        int retmax = Integer.parseInt(options.get("retmax"));
        int retstart = 0;
        int count = 0;

        options.put("retstart", Integer.toString(retstart));
        do {
            String xmlResult = this.esearchId(locusID, options);
            ESearchId esId = (ESearchId) BioXMLUtils.XMLToClass(xmlResult, ESearchId.class);
            allIds.addAll(esId.getIds());
            count = esId.getCount();

            retstart++;
            options.put("retstart", Integer.toString(retstart));

        } while (((retstart) * retmax) < count);


        return allIds;
    }

    public String efetchSequence(BioHashMap<String, String> options) {
        String allOptions = options.toBioParameters();
        String parameters = FETCH_PARAMETER;
        String resturl = EUTIL_API_EFETCH_URL + parameters + allOptions;

        return sendGet(resturl);
    }

    public String efetchSeqById(String id) {
        BioHashMap<String, String> options = new BioHashMap<String, String>();
        options.put("id", id);
        String parameters = FETCH_PARAMETER;
        String resturl = EUTIL_API_EFETCH_URL + parameters + options.toBioParameters();
        options.clear();
        return sendGet(resturl);
    }

    public Vector<String> elinkBySearchIds(Vector<String> ids){
        Vector<String> allXml = new Vector<String>();
        BioHashMap<String, String> options = new BioHashMap<String, String>();
//        options.put("id", StringUtils.join(ids.subList(0, 900), ","));
//        options.put("id", StringUtils.join(ids, ","));
       // String parameters = FETCH_PARAMETER;
        int linkSearchSize = 500;
        for(int i = 0; i < ids.size();i += linkSearchSize){

            int realSearchSize = linkSearchSize;
            if(i + linkSearchSize >= ids.size()){
                realSearchSize = ids.size() - i;
            }

            options.put("id", StringUtils.join(ids.subList(i, i+realSearchSize), ","));

            String resturl = EUTIL_API_ELINK_URL /*+ parameters*/ + options.toBioParameters();
            allXml.add(sendGet(resturl));

        }

        options.clear();
        return allXml;
    }

    public Vector<String> elinkLinkBySearchIds(Vector<String> ids){
        Vector<String> linkIds = new Vector<String>();
//        ElinkResult elr = null;
        try {
            Vector<String> xmlResults = this.elinkBySearchIds(ids);

            for(String xml : xmlResults) {
                ElinkResult elr = (ElinkResult) BioXMLUtils.XMLToClass(xml, ElinkResult.class);
                linkIds.addAll(elr.getAllLinkIds());
            }
            
            xmlResults.clear();
        } catch(Exception e) {
            System.out.println("error : " + e);
            e.printStackTrace();
        }
        return linkIds;
    }

    public String efetchSeqByIds(Vector<String> ids){
        BioHashMap<String, String> options = new BioHashMap<String, String>();
//        options.put("id", StringUtils.join(ids.subList(0, 1000), ","));
        options.put("id", StringUtils.join(ids, ","));
        String parameters = FETCH_PARAMETER;
        String resturl = EUTIL_API_EFETCH_URL + parameters + options.toBioParameters();

        options.clear();
        return sendGet(resturl);
    }



    public Vector<Genom> efetchGenomsByIds(Vector<String> ids){
    	Genoms genoms = new Genoms();
    	try {
    		String xmlResult = this.efetchSeqByIds(ids);
    		genoms = (Genoms) BioXMLUtils.XMLToClass(xmlResult, Genoms.class);
    		xmlResult = "";
    	} catch(Exception e) {
    		System.out.println("error : " + e);
    		e.printStackTrace();
    	}
        return genoms.getAllGenoms();
    }

    public Vector<String> efetchAllSeqByIds(Vector<String> ids){
    	Vector<String> allXml = new Vector<String>();
    	try {
	        System.out.println("Fetching "+ids.size()+" Genom files  ");
	
	        BioHashMap<String, String> options = new BioHashMap<String, String>();
	
	        int i = 0, fetchSize = 50;
	        List<String> subIds;
	
	        while(i <= ids.size()) {
	            //realFetchSize = fetchSize;
	            if(i + fetchSize > ids.size()){
	                subIds = ids.subList(i, ids.size());
	
	            } else {
	               subIds = ids.subList(i, i + fetchSize);
	            }

                if(subIds.size() > 0) {
                    //System.out.println("Fetching subIds "+subIds.size()+" Genom files  ");
                    options.put("id", StringUtils.join(subIds, ","));
                    //System.out.println(options.toString());

                    String parameters = FETCH_PARAMETER;
                    String resturl = EUTIL_API_EFETCH_URL + parameters + options.toBioParameters();
                    //System.out.println("En attente d'une reponse serveur de la requete :\n " + resturl);
                    allXml.add(sendGet(resturl));
                }
	            i += fetchSize;
	        }
	        options = null;
	        System.gc();
	        //System.out.println("Fin FetchSeqById");
    	} catch(Exception e) {
    		try {
    			System.out.println("Erreur, relance de la fonction : " + e);
				Thread.sleep(5000);
				allXml = efetchAllSeqByIds(ids);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
        return allXml;
    }

    public Vector<Genom> efetchAllGenomsByIds(Vector<String> ids){
        Vector<Genom> allGenoms = new Vector<Genom>();
        try {
//            int computeGenomSize = 300;
//            for(int i = 0; i < ids.size();i += computeGenomSize){
//
//                int realGenomSize = computeGenomSize;
//                if(i + computeGenomSize >= ids.size()){
//                    realGenomSize = ids.size() - i;
//                }

                Genoms genoms = null;
//                Vector<String> subVectors = new Vector(ids.subList(i, i+realGenomSize));
                Vector<String> allXml = this.efetchAllSeqByIds(ids);
//                subVectors = null;
                for(String xmlResult : allXml) {
                    genoms = (Genoms) BioXMLUtils.XMLToClass(xmlResult, Genoms.class);
                    if(genoms != null){
                        allGenoms.addAll(genoms.getAllGenoms());
                        genoms.clearGenoms();
                        genoms = null;
                    }
                    xmlResult = "";
                }

//                allGenoms.clear();
                allXml.clear();
                allXml = null;


                System.gc();
//            }
        } catch(Exception e) {
            System.out.println("error : " + e);
            e.printStackTrace();
        }
        return allGenoms;
    }

    public Document parseDOM(String xmlStr) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            return dBuilder.parse(new InputSource(new ByteArrayInputStream(xmlStr.getBytes("utf-8"))));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendGet(String uri) {
        oneSecondDelay();
        System.out.println("getting: " + uri);
        HttpURLConnection connection = null;
        URL url = null;
        try {

            url = new URL(uri);

        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException:"+e);
            e.printStackTrace();
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            sendGet(uri);
        }
        StringBuilder repsonse = new StringBuilder();
        //StringBuffer repsonse = new StringBuffer();
        try {

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            InputStream xmlresponse = connection.getInputStream();
            
            InputStreamReader isr = new InputStreamReader(xmlresponse);
            BufferedReader br = new BufferedReader(isr);
            
            String output = "";
            while ((output = br.readLine()) != null) {
            	try {
            		repsonse.append(output);
            		output = "";
            	} catch (OutOfMemoryError  e) {
            		System.err.println("Erreur dans sendGet ! \n" + e);
            		e.printStackTrace();
            		try {
                    	System.out.println("Veuillez patienter, relance dans 5 secondes...");
        				Thread.sleep(5000);
        			} catch (InterruptedException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
            	}
            }
            br.close();
            isr.close();
            xmlresponse.close();
            br = null;
            isr = null;
            xmlresponse = null;
        } catch (Exception e) {
            System.out.println("Exception:" + e);
            e.printStackTrace();
            try {
            	System.out.println("Veuillez patienter, relance dans 5 secondes...");
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            sendGet(uri);
        } finally {
            connection.disconnect();
        }
        connection = null;
        System.gc();
        //System.out.println("repsonse.toString(): " + repsonse.toString());
        return repsonse.toString();
    }

    public static void oneSecondDelay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    public String encode(String s) {

        try {
            return java.net.URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }*/ //glassfish bug, encode throws error
}
