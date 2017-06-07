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
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode
	 * die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
		return super.toString() + " " + this.bezeichnung;
	}
}
