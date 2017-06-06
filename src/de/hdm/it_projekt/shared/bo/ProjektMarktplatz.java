package de.hdm.it_projekt.shared.bo;

import java.util.Vector;

/***
 * 
 * @author Tugba Bulat
 *
 */
public class ProjektMarktplatz extends BusinessObject {

	private static final long serialVersionUID = 1L;

	private String bezeichnung;

	private Vector<Projekt> projekte = null;

	public ProjektMarktplatz() {
		if (this.projekte == null)
			this.projekte = new Vector<Projekt>();
	}

	/**
	 * 
	 * @return
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	/**
	 * 
	 * @param bezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	public void addProjekt(Projekt pr) throws IllegalArgumentException {
		this.projekte.add(pr);
	}
	public void updateProjekt(Projekt pr) throws IllegalArgumentException {
		this.projekte.set(this.projekte.indexOf(pr), pr);
	}
	public void deleteProjekt(Projekt pr) throws IllegalArgumentException {
		this.projekte.remove(pr);
	}

	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode
	 * die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
		return super.toString() + " " + this.bezeichnung;
	}
}
