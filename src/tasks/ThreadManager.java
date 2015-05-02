package tasks;

import java.util.Vector;

public class ThreadManager extends Thread {

	/**
	 * Temps en miliseconde du temps d'attente entre deux exï¿½cutions
	 */
	private long repeat;
	/**
	 * Nombre d'exï¿½cution avant l'arrï¿½t de la tï¿½che
	 */
	private int nbExec;
	
	/**
	 * Vecteur de l'ensemble des Threads de recherches
	 */
	private static Vector<GetDataThread> allTasks = new Vector<GetDataThread>();
	/**
	 * Vecteur de la liste des éléments à rechercher
	 */
	private Vector<String> allResearchs = new Vector<String>();

	/**
	 * Constructeur du ThreadManager
	 * @param allResearchs Vecteur des éléments à rechercher
	 */
	public ThreadManager(Vector<String> allResearchs) {
		this.setAllResearchs(allResearchs);
	}
	
	/**
	 * Initialisation du ThreadManager avec la liste des Threads des éléments à rechercher
	 */
	public void init() {
		for(String searchTemp : this.getAllResearchs()) {
			getAllTasks().add(new GetDataThread(1, searchTemp));
		}
	}
	
	/**
	 * Fonction de lancement des Threads
	 */
	public static void startAllTasks() {
		for(GetDataThread tTemp : getAllTasks()) {
			System.out.println("Lancement Thread n " + tTemp.getId() + " nameSearch :" + tTemp.getResearchName());
			tTemp.start();
		}
		
		System.out.println("Fin de lancement des Threads");
	}
	
	public static void getEndProgramme() {
		int nbThreadFinish = 0;
		for(GetDataThread tTemp : ThreadManager.getAllTasks()) {
			if(tTemp.isFinish()) {
				nbThreadFinish++;
			} else {
				System.out.println("Le Thread de recherche : " + tTemp.getResearchName() + " est toujours en cours d'execution.");
			}
		}
		if(nbThreadFinish == getAllTasks().size()) {
			System.out.println("Tous les Threads sont termines, fin du programme.");
		}
	}
	
	public static Vector<GetDataThread> getAllTasks() {
		return ThreadManager.allTasks;
	}

	public void setAllTasks(Vector<GetDataThread> allTasks) {
		ThreadManager.allTasks = allTasks;
	}
	
	public Vector<String> getAllResearchs() {
		return allResearchs;
	}

	public void setAllResearchs(Vector<String> allResearchs) {
		this.allResearchs = allResearchs;
	}

	public long getRepeat() {
		return repeat;
	}

	public void setRepeat(long repeat) {
		this.repeat = repeat;
	}

	public int getNbExec() {
		return nbExec;
	}

	public void setNbExec(int nbExec) {
		this.nbExec = nbExec;
	}
}
