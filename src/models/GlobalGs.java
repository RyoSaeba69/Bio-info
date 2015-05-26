package models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import controllers.FileController;

/**
 * Created by antoine on 5/15/15.
 */
@SuppressWarnings("serial")
public class GlobalGs implements Serializable {

    public static String globalName = "global_stats.xls";
    private static GlobalGs instance = null;

    public HashMap<String, GenStats> allGs;

    public static GlobalGs getCurrentGlobalGs() {
        if (instance == null) {
            instance = new GlobalGs();
        }

        return instance;
    }

    public static String getFilePath(String path) {
        String statsDirectory = FileController.getFichier().getAbsolutePath() + "/";
        String globalName = "global_stats.data";
        return statsDirectory + path + globalName;
    }

    public GlobalGs() {
        allGs = new HashMap<String, GenStats>();
    }

    public GenStats getGs(String path) {
        return this.allGs.get(path);
    }

    public void addStats(String path, GenStats gs) {
        GenStats storedGs = null;
        if (!this.allGs.containsKey(path)) {
            this.allGs.put(path, GenStats.newEmptyGs());
            this.allGs.get(path).setPath(path);
        } else if (this.allGs.get(path) == null) {
            storedGs = GenStats.deserializeGs(getFilePath(path));
            this.allGs.put(path, storedGs);
        }


        this.allGs.get(path).mergeWith(gs);

        // test Serialize
//        this.allGs.get(path).serializeGs();
//        this.allGs.put(path, null);
//        storedGs = null;

//        System.gc();

    }

    public void serializedAll(){
        for(Map.Entry<String, GenStats> mValue : this.allGs.entrySet()) {
            GenStats currentGs = this.allGs.get(mValue.getKey());
            if(mValue.getValue() != null){
                currentGs.serializeGs();
            }
            this.allGs.put(mValue.getKey(), null);
            currentGs = null;
        }
        System.gc();
    }


    public void genGLobalExcels() {
        for (Map.Entry<String, GenStats> values : allGs.entrySet()) {

            String statsDirectory = FileController.getFichier().getAbsolutePath() + "/";
            String globalName = "global_stats.xls";
            String globalPath = statsDirectory + values.getKey() + "/" + globalName;

//                File globalFile = new File(globalPath);
//                if(!globalFile.exists()){
//                    try {
//                        globalFile.getParentFile().mkdirs();
//                        globalFile.createNewFile();
//                    } catch(Exception e){
//                        e.printStackTrace();
//                    }
//                }

            try {
                GenStats gs = GenStats.deserializeGs(GlobalGs.getFilePath(values.getKey()));
//                if(gs != null) {
                    Genom.genExcelFile(gs, new FileOutputStream(globalPath));
                    gs = null;
                    File fileToDel = new File(GlobalGs.getFilePath(values.getKey()));
                    fileToDel.delete();
                    fileToDel = null;

                    System.gc();
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public void serializeGs(){
//        try{
//
//            FileOutputStream fout = new FileOutputStream(getFilePath(this.));
//            ObjectOutputStream oos = new ObjectOutputStream(fout);
//            oos.writeObject(address);
//            oos.close();
//            System.out.println("Done");
//
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//    }

}
