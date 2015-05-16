package tasks;

import fetchclass.ElinkResult;
import ihm.ProgressBarPanel;
import models.Genom;
import controllers.DataController;
import erest.BioHashMap;
import erest.EUtilClient;
import models.GlobalGs;

public class GetDataThread extends Thread {

	/**
	 * Temps en miliseconde du temps d'attente entre deux ex�cutions
	 */
	private long repeat;
	/**
	 * Nombre d'ex�cution avant l'arr�t de la t�che
	 */
	private int nbExec;
	
	private boolean finish;
	
	/**
	 * Nom de l'�l�ment � rechercher
	 */
	private String researchName;
	/**
	 * Liste des options de la recherche
	 */
	private BioHashMap<String, String> opts;
	/**
	 * EUtilClient de la recherche
	 */
	private EUtilClient utilClient;
	
	/**
	 * DataController du Thread
	 */
	private static DataController dataController;
	
	/**
	 * Constructeur du GetDataThread
	 * @param repeat Nombre en miliseconde du temps d'attente entre deux ex�cutions du Thread
	 * @param nbExec Nombre d'�x�cution du Thread
	 * @param researchName Nom de l'entit� � rechercher
	 */
	public GetDataThread(long repeat, int nbExec, String researchName) {
		this.setRepeat(repeat);
		this.setNbExec(nbExec);
		this.setResearchName(researchName);
		this.setOpts(new BioHashMap<String,String>());
		this.setUtilClient(new EUtilClient());
		this.setFinish(false);
		
		setDataController(new DataController());
	}
	
	/**
	 * Constructeur du GetDataThread
	 * @param repeat Nombre en miliseconde du temps d'attente entre deux ex�cutions du Thread
	 * @param nbExec Nombre d'�x�cution du Thread
	 * @param researchName Nom de l'entit� � rechercher
	 * @param opts Liste des options de la recherche
	 */
	public GetDataThread(long repeat, int nbExec, String researchName, BioHashMap<String, String> opts) {
		this.setRepeat(repeat);
		this.setNbExec(nbExec);
		this.setResearchName(researchName);
		this.setOpts(opts);
		this.setUtilClient(new EUtilClient());
		this.setFinish(false);
		
		setDataController(new DataController());
	}
	
	/**
	 * Constructeur du GetDataThread
	 * @param nbExec Nombre d'�x�cution du Thread
	 * @param researchName Nom de l'entit� � rechercher
	 */
	public GetDataThread(int nbExec, String researchName) {
		this.setNbExec(nbExec);
		this.setResearchName(researchName);
		this.setOpts(new BioHashMap<String,String>());
		this.setUtilClient(new EUtilClient());
		this.setFinish(false);
		
		setDataController(new DataController());
	}
	
	/**
	 * Constructeur du GetDataThread
	 * @param nbExec Nombre d'�x�cution du Thread
	 * @param researchName Nom de l'entit� � rechercher
	 * @param opts Liste des options de la recherche
	 */
	public GetDataThread(int nbExec, String researchName, BioHashMap<String, String> opts) {
		this.setNbExec(nbExec);
		this.setResearchName(researchName);
		this.setOpts(opts);
		this.setUtilClient(new EUtilClient());
		this.setFinish(false);
		
		setDataController(new DataController());
	}
	
	@Override
	public void run() {
		try {
			System.out.println("DEBUT RUN GetDataThread s'occupant de la recherche : " + this.getResearchName());
			//Test
			ProgressBarPanel.setProgressBarValue(1);
			
			for(int i = 0; nbExec < 0 || i < nbExec; i++) {
				System.out.println("Recuperation des donnees "+this.getResearchName()+", veuillez patienter...");
				dataController.setAllIds(this.getUtilClient().esearchAllId(this.getResearchName(), this.getOpts()));
				//dataController.setSeqRes(this.getUtilClient().efetchGenomsByIds(dataController.getAllIds()));
				ElinkResult resLink = this.getUtilClient().elinkLinkBySearchIds(dataController.getAllIds());
				dataController.setAllLinkIds(resLink.getAllLinkIds());

				dataController.setSeqRes(this.getUtilClient().efetchAllGenomsByIds(dataController.getAllLinkIds()));


				System.out.println("Fin de la recuperation des donnees " + this.getResearchName());
				System.out.println("Debut traitement des donnees " + this.getResearchName());
				ProgressBarPanel.setMaximumProgressBar(dataController.getSeqRes().size());
				int nbr = 1;
				for(Genom gTemp : dataController.getSeqRes()) {
					ProgressBarPanel.setProgressBarValue(nbr);nbr++;
					//System.out.println("TRACE : "+ gTemp.toString());
					gTemp.createStatsAndFiles();
				}

				GlobalGs.getCurrentGlobalGs().genGLobalExcels();

				//System.out.println("Test Sequences : " + dataController.getSeqRes().toString());
			}
			System.out.println("FIN RUN GetDataThread s'occupant de la recherche : " + this.getResearchName());
			this.setFinish(true);
			System.gc();
			ThreadManager.getEndProgramme();
		} catch(Exception e) {
			System.out.println("Le Thread s'occupant de la recherche des " + this.getResearchName() + " a rencontre une erreur : " + e);
			this.setFinish(true);
		}
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

	public String getResearchName() {
		return researchName;
	}

	public void setResearchName(String researchName) {
		this.researchName = researchName;
	}

	public BioHashMap<String, String> getOpts() {
		return opts;
	}

	public void setOpts(BioHashMap<String, String> opts) {
		this.opts = opts;
	}

	public EUtilClient getUtilClient() {
		return utilClient;
	}

	public void setUtilClient(EUtilClient utilClient) {
		this.utilClient = utilClient;
	}

	public static DataController getDataController() {
		return dataController;
	}

	public static void setDataController(DataController dataController) {
		GetDataThread.dataController = dataController;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}
}
