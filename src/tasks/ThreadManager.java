package tasks;

import java.util.Vector;

public class ThreadManager {

	/**
	 * Vecteur de l'ensemble des Threads de recherches
	 */
	private Vector<GetDataThread> allTasks = new Vector<GetDataThread>();
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
			this.getAllTasks().add(new GetDataThread(1, searchTemp));
		}
	}
	
	/**
	 * Fonction de lancement des Threads
	 */
	public void startAllTasks() {
		for(GetDataThread tTemp : this.getAllTasks()) {
			tTemp.start();
		}
	}
	
	public Vector<GetDataThread> getAllTasks() {
		return allTasks;
	}

	public void setAllTasks(Vector<GetDataThread> allTasks) {
		this.allTasks = allTasks;
	}
	
	public Vector<String> getAllResearchs() {
		return allResearchs;
	}

	public void setAllResearchs(Vector<String> allResearchs) {
		this.allResearchs = allResearchs;
	}
}
