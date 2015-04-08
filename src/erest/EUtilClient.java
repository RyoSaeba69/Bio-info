package erest;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;

import java.util.*;

import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.ParserConfigurationException;

import bioutils.BioXMLUtils;
import com.sun.deploy.util.StringUtils;
import fetchclass.ESearchId;
import models.Genom;
import models.Genoms;
import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class EUtilClient {

    private static final String EUTIL_API_ESEARCH_URL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?";
    private static final String EUTIL_API_ESUMMARY_URL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?";
    private static final String EUTIL_API_ELINK_URL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/elink.fcgi?";
    private static final String EUTIL_API_EFETCH_URL = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?";
    private static final String FETCH_PARAMETER = "db=nuccore&retmode=xml&rettype=gb";
    private static final String CONST_PARAMETERS = "db=genome&retmode=xml";//&tool=adfeutils&email=fangx%40mskcc.org";


    public EUtilClient() {

    }

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


    public List<EFetch> eFetchTitleByGi(List giList) {

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

    public List<ESummary> esummaryGIByLocusID(List giList) {

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

    public List<ESummary> esummaryGIByLocusID(String gi) {

        String term = "&id=" + gi;
        String parameters = CONST_PARAMETERS + term;
        String resturl = EUTIL_API_ESUMMARY_URL + parameters;

        Document doc = parseDOM(sendGet(resturl));

        NodeList docSumList = doc.getElementsByTagName("DocSum");

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
        return sendGet(resturl);
    }

    public String efetchSeqByIds(Vector<String> ids){
        BioHashMap<String, String> options = new BioHashMap<String, String>();
        options.put("id", StringUtils.join(ids, ","));
        String parameters = FETCH_PARAMETER;
        String resturl = EUTIL_API_EFETCH_URL + parameters + options.toBioParameters();

        return sendGet(resturl);
    }

    public Vector<Genom> efetchGenomsByIds(Vector<String> ids){
        String xmlResult = this.efetchSeqByIds(ids);
        Genoms genoms = (Genoms) BioXMLUtils.XMLToClass(xmlResult, Genoms.class);
        return genoms.getAllGenoms();
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
            System.out.println("MalformedURLException");
            e.printStackTrace();
        }
        StringBuffer repsonse = new StringBuffer();
        try {

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            InputStream xmlresponse = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader((xmlresponse)));
            String output;
            while ((output = br.readLine()) != null) {
                repsonse.append(output);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        System.out.println("repsonse.toString(): " + repsonse.toString());
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
