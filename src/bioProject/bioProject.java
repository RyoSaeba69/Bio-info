package bioProject;

import ihm.MainFenetre;

import java.util.Vector;

import tasks.ThreadManager;

public class bioProject {

	public static String appName;
	public static String appDescription;
	public static String appVersion;
	public static String appDevs;
	
	public static ThreadManager ThreadManager;
	
	public static void main(String[] args){
		init();
		MainFenetre ihm = new MainFenetre();
		initData();
	}
	
	private static void init() {
		appName = "BioProject M1";
		appDescription = "Projet de Bio-Informatique de recherche et de statistique sur les gênes.";
		appVersion = "0.1";
		appDevs = "Quentin CLAUDON, Antoine MULLER, Jean-François MULLER, Jérémy SAIDANI";
	}
	
	private static void initData() {
		Vector<String> genes = new Vector<String>();
		genes.add("eukaryota");
		
		ThreadManager = new ThreadManager(genes);
		
		ThreadManager.init();
		ThreadManager.startAllTasks();
	}
}
