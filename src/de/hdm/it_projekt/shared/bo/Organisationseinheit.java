package de.hdm.it_projekt.shared.bo;

/**
 * 
 * @author Daniel Miedtank
 * @author Tugba Bulat
 */
public abstract class Organisationseinheit extends BusinessObject {

	private static final long serialVersionUID = 1L;
	
	/*Beginn Fremdschluesseldefinitionen*/
	/**
	 * Fremdschluesselbeziehung zu Partnerprofil
	 */
	private int partnerprofilId;
	/*Ende Fremdschluesseldefinitionen*/

	/**
	 * Name der Organisationseinheit
	 */
	private String name;
	
	/**
	 * E-Mail Adresse der Organisationseinheit
	 */
	private String email;

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
	 * Default Konstruktor
	 */
	public Organisationseinheit () {
		
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the strasse
	 */
	public String getStrasse() {
		return strasse;
	}

	/**
	 * @param strasse the strasse to set
	 */
	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	/**
	 * @return the plz
	 */
	public int getPlz() {
		return plz;
	}

	/**
	 * @param plz the plz to set
	 */
	public void setPlz(int plz) {
		this.plz = plz;
	}

	/**
	 * @return the ort
	 */
	public String getOrt() {
		return ort;
	}

	/**
	 * @param ort the ort to set
	 */
	public void setOrt(String ort) {
		this.ort = ort;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the googleID
	 */
	public String getGoogleID() {
		return googleID;
	}

	/**
	 * @param googleID the googleID to set
	 */
	public void setGoogleID(String googleID) {
		this.googleID = googleID;
	}
	
	/*Beginn Fremdschluessel Getter und Setter*/
	/**
	 * @return the partnerprofilId
	 */
	public int getPartnerprofilId() {
		return partnerprofilId;
	}

	/**
	 * @param partnerprofilId the partnerprofilId to set
	 */
	public void setPartnerprofilId(int partnerprofilId) {
		this.partnerprofilId = partnerprofilId;
	}
	/*Ende Fremdschluessel Getter und Setter*/
	
	/**
	 * Gibt zusaetzlich zu der in BusinessObject definierten toString Methode die spezifischen Attribute dieser Klasse aus
	 */
	public String toString() {
	return super.toString() + " " + this.name + " " + this.email + " " + this.ort + " " + this.plz + " " + this.strasse + " " + this.tel + " " + this.googleID;
	  }
}
