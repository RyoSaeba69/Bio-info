package models;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import controllers.FileController;

/**
 * Created by antoine on 5/15/15.
 */
public class GlobalGs {

    private static GlobalGs instance = null;

    public HashMap<String, GenStats> allGs;

    public static GlobalGs getCurrentGlobalGs(){
        if(instance == null){
            instance = new GlobalGs();
        }

        return instance;
    }

    public GlobalGs() {
        allGs = new HashMap<String, GenStats>();
    }

    public GenStats getGs(String path){
        return this.allGs.get(path);
    }

    public void addStats(String path, GenStats gs){
        if(!this.allGs.containsKey(path)){
             this.allGs.put(path, GenStats.newEmptyGs());
        }

        this.allGs.get(path).mergeWith(gs);

    }

    public void genGLobalExcels (){
            for(Map.Entry<String, GenStats> values : allGs.entrySet()) {

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
                    Genom.genExcelFile(values.getValue(), new FileOutputStream(globalPath));
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
    }

}
