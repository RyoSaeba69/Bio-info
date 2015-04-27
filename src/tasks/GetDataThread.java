package tasks;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

import models.Genom;
import controllers.DataController;
import erest.BioHashMap;
import erest.EUtilClient;

public class GetDataThread extends Thread {

	/**
	 * Temps en miliseconde du temps d'attente entre deux ex�cutions
	 */
	private long repeat;
	/**
	 * Nombre d'ex�cution avant l'arr�t de la t�che
	 */
	private int nbExec;
	
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
		
		setDataController(new DataController());
	}
	
	@Override
	public void run() {
		System.out.println("DEBUT RUN GetDataThread");
		
		RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		 
		String jvmName = runtimeBean.getName();
		System.out.println("JVM Name = " + jvmName);
		long pid = Long.valueOf(jvmName.split("@")[0]);
		System.out.println("JVM PID  = " + pid);
 
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
 
		int peakThreadCount = bean.getPeakThreadCount();
		System.out.println("Peak Thread Count = " + peakThreadCount);
		
		for(int i = 0; nbExec < 0 || i < nbExec; i++) {
			dataController.setAllIds(this.getUtilClient().esearchAllId(this.getResearchName(), this.getOpts()));
			dataController.setSeqRes(this.getUtilClient().efetchGenomsByIds(dataController.getAllIds()));
			for(Genom gTemp : dataController.getSeqRes()) {
				System.out.println("TRACE : "+ gTemp.toString());
			}
			System.out.println("Test Sequences : " + dataController.getSeqRes().toString());
		}
		System.out.println("FIN RUN GetDataThread");
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
}
