package de.hdm.it_projekt.shared.bo;

/**
 * 
 * @author Daniel Miedtank
 * @author Tugba Bulat
 */
public abstract class Organisationseinheit extends BusinessObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Name der Organisationseinheit
	 */
	private String name;
	
	/**
	 * E-Mail Adresse der Organisationseinheit
	 */
	private String email;
	
	/**
	 * Partnerprofil zur Organisationseinheit
	 */
	private Partnerprofil partnerprofil = null;

  /**
	 * Strasse der Organisationseinheit
	 */
	private String strasse;
	
	/**
	 * PLZ der Organisationseinheit
	 */
	private int plz;
	
	/**
	 * Ort der Organisationseinheit
	 */
	private String ort;
	
	/**
	 * Telefonnummer der Organisationseinheit
	 */
	private String tel;
	
	/**
	 * GoogleID der Organisationseinheit
	 */
	private String googleID;
	
	/**
	 * Setzen der Strasse der Organisationseinheit
	 * @param strasse
	 */
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

/**
	 * Auslesen der Strasse der Organisationseinheit
	 * @return
	 */
	public String getStrasse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Setzen der PLZ der Organisationseinheit
	 * @param plz
	 */
	public void setPlz(int plz) {
		this.plz = plz;
	}

	/**
	 * Auslesen der PLZ der Organisationseinheit
	 * @return
	 */
	public String getPlz() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Setzen des Orts der Organisationseinheit
	 * @param ort
	 */
	public void setOrt(String ort) {
		this.ort = ort;
	}

/**
	 * Auslesen des Orts der Organisationseinheit
	 * @return
	 */
	public String getOrt() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Setzen der Telefonnummer der Organisationseinheit
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * Auslesen der Telefonnummer der Organisationseinheit
	 * @return
	 */
	public String getTel() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Setzen der GoogleID der Organisationseinheit
	 * @param googleID
	 */
	public void setGoogleID(String googleID) {
		this.googleID = googleID;
	}

	/**
	 * Auslesen der GoogleID der Organisationseinheit
	 * @return
	 */
	public String getGoogleID() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * R�ckgabe des Namens der Organisationseinheit
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzen des Namens der Organisationseinheit
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * R�ckgabe der E-Mail Adresse
	 * @return the email
	 */
	public String getEmail() {
		return null;
	}

	/**
	 * Setzen der E-Mail Adresse
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * R�ckgabe des Partnerprofils
	 * @return the partnerprofil
	 */
	public Partnerprofil getPartnerprofil() {
		return partnerprofil;
	}

	/**
	 *  Setzen des Partnerprofils
	 * @param partnerprofil the partnerprofil to set
	 */
	public void setPartnerprofil(Partnerprofil partnerprofil) {
		this.partnerprofil = partnerprofil;
	}	
}
