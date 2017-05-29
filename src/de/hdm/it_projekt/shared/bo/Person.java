package de.hdm.it_projekt.shared.bo;

/**
 * 
 * @author Daniel Miedtank
 *
 */
public class Person extends Organisationseinheit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Vorname der Person
	 */
	private String vorname;

	/**
	 * R�ckgabe des Vornamens
	 * @return
	 */
	public static String getVorname() {
		return null;
	}

	/**
	 * Setzen des Vornamens
	 * @param vorname the vorname to set
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	
	

}
