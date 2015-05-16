package controllers;

import java.util.Vector;

import models.Genom;

public class DataController {
	
	/**
	 * La liste des IDs r�cup�r�s lors de la recherche
	 */
	private Vector<String> allIds;
	/**
	 * La liste des G�noms correspondant aux IDs
	 * @see #allIds
	 */
	private Vector<Genom> seqRes;

	/**
	 * La liste des ids links
	 */
	private Vector<String> allLinkIds;
	
	/**
	 * Constructeur du DataController
	 */
	public DataController(){
		this.setSeqRes(new Vector<Genom>());
		this.setAllIds(new Vector<String>());
	}

	public Vector<String> getAllIds() {
		return allIds;
	}

	public void setAllIds(Vector<String> allIds) {
		this.allIds = allIds;
	}

	public Vector<Genom> getSeqRes() {
		return seqRes;
	}

	public void setSeqRes(Vector<Genom> seqRes) {
		this.seqRes = seqRes;
	}

	public Vector<String> getAllLinkIds() {
		return allLinkIds;
	}

	public void setAllLinkIds(Vector<String> allLinkIds) {
		this.allLinkIds = allLinkIds;
	}
}
