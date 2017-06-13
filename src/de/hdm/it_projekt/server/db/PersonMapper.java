/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

/**
 * Mapper-Klasse, die <code>Person</code>-Objekte auf eine relationale Datenbank
 * abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung gestellt, mit
 * deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und geloescht werden
 * koennen. Das Mapping ist bidirektional. D.h., Objekte koennen in
 * DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * 
 * @author Tugba Bulat
 */
public class PersonMapper {

	/**
	 * Die Klasse PersonMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static PersonMapper personMapper = null;

	/**
	 * Zuordnung der Klasse in der Organisationseinheit-Tabelle
	 */
	private final String SQLTYP = "P";

	/***
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen
	 */
	protected PersonMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>PersonMapper.personMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>PersonMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> PersonMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>PersonMapper</code>-Objekt.
	 */
	public static PersonMapper personMapper() {
		if (personMapper == null) {
			personMapper = new PersonMapper();
		}

		return personMapper;
	}

	/**
	 * Diese Methode ermoeglicht es eine Person in der Datenbank anzulegen.
	 * 
	 * @param p
	 * @return
	 */
	public Person insert(Person p) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunaechst schauen wir nach, welches der momentan hoechste
			 * Primaerschluesselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM organisationseinheit");

			// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				/*
				 * p erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel.
				 */
				p.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
				stmt.executeUpdate(
						"INSERT INTO organisationseinheit (ID, Name, Vorname, Email, Strasse, PLZ, Ort, Tel, GoogleID, Partnerprofil_ID, Typ) "
								+ "VALUES ('" + p.getId() + "','" + p.getName() + "','" + p.getVorname() + "','"
								+ p.getEmail() + "','" + p.getStrasse() + "'," + p.getPlz() + ",'" + p.getOrt() + "','"
								+ p.getTel() + "','" + p.getGoogleID() + "',NULL,'" + SQLTYP + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param p
	 *            - das Objekt, das in die Datenbank geschrieben werden soll
	 * @return das als Parameter uebergebene Objekt
	 */
	public Person update(Person p) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			/* Statement stmt = con.createStatement(); */

			/*
			 * stmt.executeUpdate("UPDATE organisationseinheit SET Name=\"" +
			 * p.getName() + "\", SET Vorname=\"" + p.getVorname() + "\", " +
			 * "SET Email=\"" + p.getEmail() + "\", " + "SET Strasse=\"" +
			 * p.getStrasse() + "\", " + "SET PLZ=\"" + p.getPlz() + "\", " +
			 * "SET Ort=\"" + p.getOrt() + "\", " + "SET Tel=\"" + p.getTel() +
			 * "\", " + "SET GoogleID=\"" + p.getGoogleID() + "\", " +
			 * "SET Partnerprofil_ID=\"" + p.getPartnerprofilId() +
			 * "\", ' WHERE ID=\"" + p.getId());
			 */

			PreparedStatement pstmt = con.prepareStatement(
					"UPDATE organisationseinheit SET Typ = ?, Name = ?, Vorname = ?, Email = ?, Strasse = ?, Plz = ?, Ort = ?, Tel = ?, GoogleID = ?, Partnerprofil_ID = ? WHERE ID = ?");
			pstmt.setString(1, SQLTYP);
			pstmt.setString(2, p.getName());
			pstmt.setString(3, p.getVorname());
			pstmt.setString(4, p.getEmail());
			pstmt.setString(5, p.getStrasse());
			pstmt.setInt(6, p.getPlz());
			pstmt.setString(7, p.getOrt());
			pstmt.setString(8, p.getTel());
			pstmt.setString(9, p.getGoogleID());
			pstmt.setInt(10, p.getPartnerprofilId());
			pstmt.setInt(11, p.getId());

			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um Analogie zu insert(Person p) zu wahren,
		// geben wir p zurueck.
		return p;
	}

	/**
	 * Loeschen der Daten eines <code>Person</code>-Objekts aus der Datenbank.
	 * 
	 * @param p
	 *            - das Objekt, das aus der Datenbank geloescht werden soll
	 */
	public void delete(Person p) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM organisationseinheit WHERE ID=" + p.getId());
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
	}

	/**
	 * Auslesen aller Personen.
	 * 
	 * @return
	 */
	public Vector<Person> findAll() {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Person> result = new Vector<Person>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Vorname, Email, Strasse, PLZ, Ort, Tel, GoogleID, Partnerprofil_ID, Typ "
							+ "FROM organisationseinheit WHERE Typ='" + SQLTYP + "' ORDER BY ID");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Person-Objekt erstellt.

			while (rs.next()) {
				Person p = new Person();
				p.setId(rs.getInt("ID"));
				p.setName(rs.getString("Name"));
				p.setVorname(rs.getString("Vorname"));
				p.setEmail(rs.getString("Email"));
				p.setStrasse(rs.getString("Strasse"));
				p.setPlz(rs.getInt("PLZ"));
				p.setOrt(rs.getString("Ort"));
				p.setTel(rs.getString("Tel"));
				p.setGoogleID(rs.getString("GoogleID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(p);
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Suchen einer Person mit vorgegebener ID. Da diese eindeutig ist, wird
	 * genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 *            - Primaerschluesselattribut in DB
	 * @return
	 */
	public Person findById(int id) {

		// DB-Verbindung holen
		Connection con = DBConnection.connection();
		Person p = null;

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Vorname, Email, Strasse, PLZ, Ort, Tel, GoogleID, Partnerprofil_ID, Typ FROM organisationseinheit WHERE ID= '"
							+ id + "' AND Typ='" + SQLTYP + "' ORDER BY email");

			/*
			 * Da ID der Primaerschluessel ist, kann maximal nur ein Tupel
			 * zurueckgegeben werden. Pruefung, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts

				p = new Person();
				p.setId(rs.getInt("ID"));
				p.setName(rs.getString("Name"));
				p.setVorname(rs.getString("Vorname"));
				p.setEmail(rs.getString("Email"));
				p.setStrasse(rs.getString("Strasse"));
				p.setPlz(rs.getInt("PLZ"));
				p.setOrt(rs.getString("Ort"));
				p.setTel(rs.getString("Tel"));
				p.setGoogleID(rs.getString("GoogleID"));

			}
		} catch (SQLException e5) {
			e5.printStackTrace();
		}
		return p;
	}

	/**
	 * Auslesen einer Person anhand eines bestimmten Namens.
	 * 
	 * @param name
	 * @return
	 */
	public Vector<Person> findByLastName(String name) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Person> result = new Vector<Person>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Vorname, Email, Strasse, PLZ, Ort, Tel, GoogleID, Partnerprofil_ID, Typ FROM organisationseinheit WHERE name='"
							+ name + "' AND Typ='" + SQLTYP + "' ORDER BY Name");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Person-Objekt erstellt.
			while (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts
				Person p = new Person();
				p.setId(rs.getInt("ID"));
				p.setName(rs.getString("Name"));
				p.setEmail(rs.getString("Email"));
				p.setStrasse(rs.getString("Strasse"));
				p.setPlz(rs.getInt("PLZ"));
				p.setOrt(rs.getString("Ort"));
				p.setTel(rs.getString("Tel"));
				p.setGoogleID(rs.getString("GoogleID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(p);
			}
		} catch (SQLException e6) {
			e6.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;

	}

	/**
	 * Auslesen einer Person anhand eines bestimmten Vornamens.
	 * 
	 * @param vorname
	 * @return
	 */
	public Vector<Person> findBySurName(String vorname) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Person> result = new Vector<Person>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Vorname, Email, Strasse, PLZ, Ort, Tel, GoogleID, Partnerprofil_ID, Typ FROM organisationseinheit WHERE name='"
							+ vorname + "' AND Typ='" + SQLTYP + "' ORDER BY Vorname");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Person-Objekt erstellt.
			while (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts
				Person p = new Person();
				p.setId(rs.getInt("ID"));
				p.setName(rs.getString("Name"));
				p.setVorname(rs.getString("Vorname"));
				p.setEmail(rs.getString("Email"));
				p.setStrasse(rs.getString("Strasse"));
				p.setPlz(rs.getInt("PLZ"));
				p.setOrt(rs.getString("Ort"));
				p.setTel(rs.getString("Tel"));
				p.setGoogleID(rs.getString("GoogleID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(p);
			}
		} catch (SQLException e7) {
			e7.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;

	}

	/**
	 * Auslesen einer Person anhand einer bestimmten Email-Adresse.
	 * 
	 * @param email
	 * @return
	 */
	public Vector<Person> findByMail(String email) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Person> result = new Vector<Person>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Vorname, Email, Strasse, PLZ, Ort, Tel, GoogleID, Partnerprofil_ID, Typ FROM organisationseinheit WHERE email='"
							+ email + "' AND Typ='" + SQLTYP + "' ORDER BY email");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Person-Objekt erstellt.
			while (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts
				Person p = new Person();
				p.setId(rs.getInt("ID"));
				p.setName(rs.getString("Name"));
				p.setEmail(rs.getString("Email"));
				p.setStrasse(rs.getString("Strasse"));
				p.setPlz(rs.getInt("PLZ"));
				p.setOrt(rs.getString("Ort"));
				p.setTel(rs.getString("Tel"));
				p.setGoogleID(rs.getString("GoogleID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(p);
			}
		} catch (SQLException e8) {
			e8.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;

	}

	public Person findByGoogleId(String googleID) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Person p = null;

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, GoogleID, Partnerprofil_ID, Typ FROM organisationseinheit WHERE GoogleID='"
							+ googleID + "' AND Typ='" + SQLTYP + "'");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Team-Objekt erstellt.
			if (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts
				p = new Person();
				p.setId(rs.getInt("ID"));
				p.setName(rs.getString("Name"));
				p.setEmail(rs.getString("Email"));
				p.setStrasse(rs.getString("Strasse"));
				p.setPlz(rs.getInt("PLZ"));
				p.setOrt(rs.getString("Ort"));
				p.setTel(rs.getString("Tel"));
				p.setGoogleID(rs.getString("GoogleID"));
				p.setPartnerprofilId(rs.getInt("Partnerprofil_ID"));

			}
		} catch (SQLException e9) {
			e9.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return p;

	}

	/**
	 * Auslesen des zugehoerigen <code>Person</code>-Objekts zu einem gegebenen
	 * Projektmarktplatz.
	 * 
	 * @param pm
	 * @return
	 */
	public Vector<Person> getByProjektMarktplatz(ProjektMarktplatz pm) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Person> result = new Vector<Person>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT o.ID AS ID FROM organisationseinheit AS o "
					+ "INNER JOIN projektmarktplatz_has_organisationseinheit ON organisationseinheit_ID=projektmarktplatz_has_organisationseinheit.Organisationseinheit_ID "
					+ "WHERE projektmarktplatz_ID= " + pm.getId());

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Person-Objekt erstellt.
			while (rs.next()) {

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(findById(rs.getInt("ID")));
			}
		} catch (SQLException e10) {
			e10.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

}
