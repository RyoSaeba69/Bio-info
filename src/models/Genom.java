package models;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import bioadapters.SequenceAdapter;
import bioutils.BioStringUtil;
import erest.BioHashMap;

/**
 * Created by antoine on 2/17/15.
 */

@XmlRootElement(name="GBSeq")
@XmlAccessorType(XmlAccessType.FIELD)
public class Genom {

	@XmlElement(name="GBSeq_taxonomy")
    private String taxonomy;

//    @XmlElement(name="GBSeq_sequence")
//    private String sequence;

    @XmlElement(name="GBSeq_organism")
    private String organism;

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    @XmlJavaTypeAdapter(SequenceAdapter.class)
    @XmlElement(name="GBSeq_sequence")
    private Sequence sequence;

    @XmlElement(name="GBSeq_length")
    private int sequenceLength;

    @XmlElementWrapper(name="GBSeq_feature-table")
    @XmlElement(name="GBFeature")
    private Vector<Feature> featureTable;

    public Genom() {}

    public Vector<Feature> getFeatureTable() {
        return featureTable;
    }

    public void setFeatureTable(Vector<Feature> featureTable) {
        this.featureTable = featureTable;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    public int getSequenceLength() {
        return sequenceLength;
    }

    public void setSequenceLength(int sequenceLength) {
        this.sequenceLength = sequenceLength;
    }
    
    @Override
   	public String toString() {
   		return "Genom [taxonomy=" + taxonomy + ", sequence=" + sequence
   				+ ", sequenceLength=" + sequenceLength + "]";
   	}

    public void createStatsAndFiles(){
        GenStats gs = this.generateStats();
        this.createFiles(gs);
    }

    public void createFiles(GenStats gs){

        if(gs.isUsable()) {

            String fileSeparator = "/";

            String excelExt = ".xls";
            String taxonomySeparator = "; ";
            String statsDirectory = "." + fileSeparator + "biostats" + fileSeparator;
            String basePath = statsDirectory + this.taxonomy.replaceAll(taxonomySeparator, fileSeparator);
            File newDirectories = new File(basePath);
//            if (newDirectories.mkdirs()) {
//                System.out.println("Directory successfully created : " + basePath);
//            } else {
//                System.out.println("Failed to create directory : " + basePath);
//            }
            newDirectories.mkdirs();

            String filePath = basePath + fileSeparator + this.organism + excelExt;
            File newExcelFile =  new File(filePath);
            if(newExcelFile.exists()){
                filePath = newExcelFile.getParent() + fileSeparator + this.organism + "("+new File(newExcelFile.getParent()).listFiles().length+")"+excelExt;
            }

            gs.setPath(basePath);

            try {
                FileOutputStream newStatFile = new FileOutputStream(filePath);
                genExcelFile(gs, newStatFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Vector<Feature> findCDSFeatures(){

        Vector<Feature> vCdsFeature = new Vector<Feature>();

        for(Feature f : this.getFeatureTable()){
            if(f.isCDS()){
                vCdsFeature.add(f);
            }
        }
        return vCdsFeature;
    }

    public HashMap<String, TriInfo> genCountTri(int phase, String sSeq){

        Vector<String> phasedVSeq = Sequence.findVseqByPhase(phase, sSeq.toLowerCase());

        int totalTri = 0;
        HashMap<String, Integer> hmCount = BioHashMap.initAnalysisHm();

        for (Map.Entry<String, Integer> pair : hmCount.entrySet()) {
            String key = pair.getKey();
            int nbTri = BioStringUtil.countTrinucleotide(phasedVSeq, key.toLowerCase());
            totalTri += nbTri;
            hmCount.put(key, nbTri);
        }

        HashMap<String, TriInfo> hmRes = new HashMap<String, TriInfo>();

        for (Map.Entry<String, Integer> pair : hmCount.entrySet()) {
            String key = pair.getKey();
            int count = pair.getValue();

            TriInfo newTriInfo = new TriInfo();

            newTriInfo.setPhCount(count);

            if (totalTri > 0) {
                newTriInfo.setPbCount((count * 100) / (double) totalTri);
            } else {
                newTriInfo.setPbCount(0);
            }

            hmRes.put(key, newTriInfo);
        }

        return hmRes;
    }

    public GenStats generateStats (){
        GenStats resStats = new GenStats();

        resStats.setName(this.organism);

        Sequence seq = this.getSequence();

        int unUsedCds = 0;

        Vector<Feature> allCds = this.findCDSFeatures();
        resStats.setNbCds(allCds.size());

        for(Feature cds : allCds){

            if(!cds.isUsableCds(seq.getSequence().length())){
                unUsedCds++;
            } else {
                String usedSeq = seq.applyFeatures(cds);

                if(Sequence.isValid(usedSeq)) {
                    // there is 3 phases
                    Vector<HashMap<String, TriInfo>> allPhases = new Vector<HashMap<String, TriInfo>>();
                    for(int p = 0; p < 3;p++) {
                        HashMap<String, TriInfo> hmRes = this.genCountTri(p, usedSeq);

                        switch (p){
                            case 0:
                                resStats.setPh0Trinucleotide(hmRes);
                                resStats.setPh0Total((usedSeq.length() / 3));
                                break;
                            case 1:
                                resStats.setPh1Trinucleotide(hmRes);
                                resStats.setPh1Total((usedSeq.length() / 3));
                                break;
                            case 2:
                                resStats.setPh2Trinucleotide(hmRes);
                                resStats.setPh2Total((usedSeq.length() / 3));
                                break;
                        }

                        allPhases.add(hmRes);
                    }
                    resStats.addNewPhTrinucleotide(allPhases);

                } else {
                    unUsedCds++;
                }
            }
        }

        resStats.setUnUsedCds(unUsedCds);

        return resStats;
    }

    @SuppressWarnings("resource")
	public static void genExcelFile(GenStats gs, FileOutputStream excelFile) throws Exception {

        Vector<HashMap<String, TriInfo>> allResHm = gs.computeAllTrinucleotide();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Bio stats");

        CellStyle decimalCellStyle = workbook.createCellStyle();
        decimalCellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.0"));

        // Name
        Row nameRow = sheet.createRow(0);
        nameRow.createCell(0).setCellValue("Name");
        nameRow.createCell(1).setCellValue(gs.getName());

        // Path
        Row pathRow = sheet.createRow(1);
        pathRow.createCell(0).setCellValue("Path");
        pathRow.createCell(1).setCellValue(gs.getPath());

        // Nb CDS
        Row nbCDSRow = sheet.createRow(2);
        nbCDSRow.createCell(0).setCellValue("Nb Cds");
        nbCDSRow.createCell(1).setCellValue(gs.getNbCds());

        // Nb trinucleotide
        Row nbTriRow = sheet.createRow(3);
        nbTriRow.createCell(0).setCellValue("Nb trinucleotide");
        nbTriRow.createCell(1).setCellValue(gs.getTotalHmRes());

        // Nb unused CDS
        Row nbUnusedRow = sheet.createRow(4);
        nbUnusedRow.createCell(0).setCellValue("Nb unused CDS");
        nbUnusedRow.createCell(1).setCellValue(gs.getUnUsedCds());

        // Stats header
        int statsHeaderNumRow = 5;
        Row statsHeaderRow = sheet.createRow(statsHeaderNumRow);
        statsHeaderRow.createCell(0).setCellValue("Trinucleotides");

        statsHeaderRow.createCell(1).setCellValue("Nb Ph0");
        statsHeaderRow.createCell(2).setCellValue("Pb Ph0");

        HashMap<String, TriInfo> hmPh0 = allResHm.get(0);

        int currentLine = statsHeaderNumRow + 1;
        for (Map.Entry<String, TriInfo> entry : hmPh0.entrySet()) {
            Row currentRow = sheet.createRow(currentLine);

            currentRow.createCell(0).setCellValue(entry.getKey().toUpperCase());
            currentRow.createCell(1).setCellValue(entry.getValue().getPhCount());
            currentRow.createCell(2).setCellValue(entry.getValue().getPbCount());
            currentRow.getCell(2).setCellStyle(decimalCellStyle);


            currentLine++;
        }

        currentLine = statsHeaderNumRow + 1;
        statsHeaderRow.createCell(3).setCellValue("Nb Ph1");
        statsHeaderRow.createCell(4).setCellValue("Pb Ph1");
        HashMap<String, TriInfo> hmPh1 = allResHm.get(1);

        for (Map.Entry<String, TriInfo> entry : hmPh1.entrySet()) {
            Row currentRow = sheet.getRow(currentLine);

            currentRow.createCell(3).setCellValue(entry.getValue().getPhCount());
            currentRow.createCell(4).setCellValue(entry.getValue().getPbCount());
            currentRow.getCell(4).setCellStyle(decimalCellStyle);

            currentLine++;
        }

        statsHeaderRow.createCell(5).setCellValue("Nb Ph2");
        statsHeaderRow.createCell(6).setCellValue("Pb Ph2");

        currentLine = statsHeaderNumRow + 1;
        HashMap<String, TriInfo> hmPh2 = allResHm.get(2);

        for (Map.Entry<String, TriInfo> entry : hmPh2.entrySet()) {
            Row currentRow = sheet.getRow(currentLine);

            currentRow.createCell(5).setCellValue(entry.getValue().getPhCount());
            currentRow.createCell(6).setCellValue(entry.getValue().getPbCount());
            currentRow.getCell(6).setCellStyle(decimalCellStyle);


            currentLine++;
        }


        Row totalRow = sheet.createRow(currentLine);
        totalRow.createCell(0).setCellValue("TOTAL");
        totalRow.createCell(1).setCellValue(gs.getTotalHmRes());
        totalRow.createCell(2).setCellValue(100);
        totalRow.createCell(3).setCellValue(gs.getTotalHmRes());
        totalRow.createCell(4).setCellValue(100);
        totalRow.createCell(5).setCellValue(gs.getTotalHmRes());
        totalRow.createCell(6).setCellValue(100);




        workbook.write(excelFile);
        excelFile.close();
    }
}
