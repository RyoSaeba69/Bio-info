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
		new MainFenetre();
		initData();
	}
	
	private static void init() {
		appName = "BioProject M1";
		appDescription = "Projet de Bio-Informatique de recherche et de statistique sur les g�nes.";
		appVersion = "0.1";
		appDevs = "Quentin CLAUDON, Antoine MULLER, Jean-Fran�ois MULLER, J�r�my SAIDANI";
	}
	
	private static void initData() {
		Vector<String> genes = new Vector<String>();
		genes.add("eukaryota");
		genes.add("viruses");
		genes.add("virus");
		genes.add("plasmid");
		genes.add("plasmids");

		genes.add("archaea");
		genes.add("chloroplast");
		genes.add("chloroplasts");
		genes.add("mitochondria");

		genes.add("prokaryota");



		ThreadManager = new ThreadManager(genes);
		
		ThreadManager.init();
		ThreadManager.startAllTasks();
	}
}
