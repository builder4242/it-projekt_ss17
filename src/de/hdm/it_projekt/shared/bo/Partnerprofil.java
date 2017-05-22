package de.hdm.it_projekt.shared.bo;

import java.util.Date;
import java.util.Vector;

/**
 * 
 * @author Daniel Miedtank
 *
 */
public class Partnerprofil extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Datum an dem das Projekt erstellt wurde.
	 */
	private Date erstelldatum;
	
	/**
	 *  Letzte �nderung am Projekt
	 */
	private Date aenderungsdatum;
	
	/**
	 * 
	 */
	private Vector<Eigenschaft> eigenschaften = null;
	
	public Partnerprofil () {
		
		/**
		 * Pr�fen ob der Vector Eigenschaften
		 */
		if(eigenschaften == null) {
			this.eigenschaften = new Vector<Eigenschaft>();
		}
	}

	/**
	 * R�ckgabe des Erstelldatums
	 * @return the erstelldatum
	 */
	public static Date getErstelldatum() {
		return null;
	}

	/**
	 * Setzen des Erstelldatums
	 * @param erstelldatum the erstelldatum to set
	 */
	public void setErstelldatum(Date erstelldatum) {
		this.erstelldatum = erstelldatum;
	}

	/**
	 * R�ckgabe des �nderungsdatums
	 * @return the aenderungsdatum
	 */
	public static Date getAenderungsdatum() {
		return null;
	}

	/**
	 * Setzen des �nderungsdatums
	 * @param aenderungsdatum the aenderungsdatum to set
	 */
	public void setAenderungsdatum(Date aenderungsdatum) {
		this.aenderungsdatum = aenderungsdatum;
	}

	/**
	 * R�ckgabe des Vectors Eigenschaften
	 * @return the eigenschaften
	 */
	public Vector<Eigenschaft> getEigenschaften() {
		return eigenschaften;
	}

	/**
	 * Eien weitere Eigenschaft hinzuf�gen
	 * @param e
	 */
	public void addEigenschaft(Eigenschaft e) {
		this.eigenschaften.add(e);
	}
	
	/**
	 * Eine Eigenschaft l�schen
	 * @param Eigenschaft e
	 * @return true wenn das Objekt erfolgreich gel�scht wurde.
	 */
	public boolean deleteEigenschaft(Eigenschaft e) {
		return this.eigenschaften.remove(e);
	}

	public static String getID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
