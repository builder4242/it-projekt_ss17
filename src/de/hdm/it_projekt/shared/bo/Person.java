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
	 * Rueckgabe des Vornamens
	 * @return
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * Setzen des Vornamens
	 * @param vorname the vorname to set
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String toString() {
	return super.toString() + " " + this.vorname;
	  }
}
