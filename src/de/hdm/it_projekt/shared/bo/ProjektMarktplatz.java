package de.hdm.it_projekt.shared.bo;

/***
 * 
 * @author Tugba Bulat
 *
 */
public class ProjektMarktplatz extends BusinessObject {

	/**
	 * Bezeichnung des Projektmarktplatzes
	 */
	private String bezeichnung;
	  /**
	   * Fremschluesselbeziehung zu Orgnaisationseinheit
	   */
	  private int adminId = 0;


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
	 * @return the adminId
	 */
	public int getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode
	 * die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
		return super.toString() + " " + this.bezeichnung;
	}
}
