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

import de.hdm.it_projekt.shared.bo.Partnerprofil;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
import de.hdm.it_projekt.shared.bo.Team;
import de.hdm.it_projekt.shared.bo.Unternehmen;

/**
 * Mapper-Klasse, die <code>Unternehmen</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte
 * koennen in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * 
 * @author Tugba Bulat
 */
public class UnternehmenMapper {

	/**
	 * Die Klasse UnternehmenMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static UnternehmenMapper unternehmenMapper = null;

	/**
	 * Zuordnung der Klasse in der Organisationseinheit-Tabelle
	 */
	private final String SQLTYP = "U";

	/***
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen
	 */
	protected UnternehmenMapper() {

	}

	/**
	 * Diese statische Methode kann aufgerufen werden durch
	 * <code>UnternehmenMapper.unternehmenMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>UnternehmenMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> UnternehmenMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>UnternehmenMapper</code>-Objekt.
	 */
	public static UnternehmenMapper unternehmenMapper() {
		if (unternehmenMapper == null) {
			unternehmenMapper = new UnternehmenMapper();
		}

		return unternehmenMapper;
	}

	/**
	 * Diese Methode ermoeglicht es ein Unternehmen in der Datenbank anzulegen.
	 * 
	 * @param u
	 * @return
	 */
	public Unternehmen insert(Unternehmen u) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunaechst schauen wir nach, welches der momentan hoechste
			 * Primaerschluesselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM organisationseinheit ");

			// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {

				/*
				 * u erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel.
				 */
				u.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
				stmt.executeUpdate(
						"INSERT INTO organisationseinheit (ID, Name, Email, Strasse, PLZ, Ort, Tel, Partnerprofil_ID, Typ) "
								+ "VALUES ('" + u.getId() + "','" + u.getName() + "','" + u.getEmail() + "','"
								+ u.getStrasse() + "','" + u.getPlz() + "','" + u.getOrt() + "','" + u.getTel()
								+ "',NULL,'" + SQLTYP + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param u
	 *            - das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter uebergebene Objekt
	 */
	public Unternehmen update(Unternehmen u) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			/*
			 * Statement stmt = con.createStatement(); stmt.executeUpdate(
			 * "UPDATE organisationseinheit SET Name = '" + u.getName() +
			 * ", SET Email = " + u.getEmail() + ", " + "SET Strasse = " +
			 * u.getStrasse() + ", " + "SET PLZ = " + u.getPlz() + ", " +
			 * "SET Ort = " + u.getOrt() + ", " + "SET Tel = " + u.getTel() +
			 * ", " + "SET GoogleID=\"" + u.getGoogleID() + ", " +
			 * "SET Partnerprofil_ID=\"" + u.getPartnerprofilId() +
			 * ", ' WHERE ID=" + u.getId());
			 */

			PreparedStatement pstmt = con.prepareStatement(
					"UPDATE organisationseinheit SET Typ = ?, Name = ?, Email = ?, Strasse = ?, Plz = ?, Ort = ?, Tel = ?, Partnerprofil_ID = ? WHERE ID = ?");
			pstmt.setString(1, SQLTYP);
			pstmt.setString(2, u.getName());
			pstmt.setString(3, u.getEmail());
			pstmt.setString(4, u.getStrasse());
			pstmt.setInt(5, u.getPlz());
			pstmt.setString(6, u.getOrt());
			pstmt.setString(7, u.getTel());
			if(u.getPartnerprofilId() == 0)
				pstmt.setObject(9, null);
			else
				pstmt.setObject(9, u.getPartnerprofilId());
			pstmt.setInt(9, u.getId());

			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um die Analogie zu insert(Unternehmen u) zu wahren,
		// geben wir u zurueck.
		return u;
	}

	/**
	 * Loeschen der Daten eines <code>Unternehmen</code>-Objekts aus der
	 * Datenbank.
	 * 
	 * @param u
	 *            - das Objekt, das aus der DB geloescht werden soll
	 */
	public void delete(Unternehmen u) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM organisationseinheit " + "WHERE ID=" + u.getId());
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
	}

	/**
	 * Auslesen aller Unternehmen.
	 * 
	 * @return
	 */
	public Vector<Unternehmen> findAll() {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Unternehmen> result = new Vector<Unternehmen>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, Partnerprofil_ID, Typ "
					+ "FROM organisationseinheit WHERE Typ='" + SQLTYP + "' ORDER BY ID");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Unternehmen-Objekt erstellt.

			while (rs.next()) {
				Unternehmen u = new Unternehmen();
				u.setId(rs.getInt("ID"));
				u.setName(rs.getString("Name"));
				u.setEmail(rs.getString("Email"));
				u.setStrasse(rs.getString("Strasse"));
				u.setPlz(rs.getInt("PLZ"));
				u.setOrt(rs.getString("Ort"));
				u.setTel(rs.getString("Tel"));

				if(rs.getObject("Partnerprofil_ID") != null)
					u.setPartnerprofilId(rs.getInt("Partnerprofil_ID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(u);
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Suchen eines Unternehmens mit vorgegebener ID. Da diese eindeutig ist,
	 * wird genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 *            - Primaerschluesselattribut in DB
	 * @return
	 */
	public Unternehmen findById(int id) {

		// DB-Verbindung holen
		Connection con = DBConnection.connection();
		Unternehmen u = null;

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, Partnerprofil_ID, Typ FROM organisationseinheit WHERE ID="
							+ id);

			/*
			 * Da ID der Primaerschluessel ist, kann maximal nur ein Tupel
			 * zurueckgegeben werden. Pruefung, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts

				u = new Unternehmen();
				u.setId(rs.getInt("ID"));
				u.setName(rs.getString("Name"));
				u.setEmail(rs.getString("Email"));
				u.setStrasse(rs.getString("Strasse"));
				u.setPlz(rs.getInt("PLZ"));
				u.setOrt(rs.getString("Ort"));
				u.setTel(rs.getString("Tel"));

				if(rs.getObject("Partnerprofil_ID") != null)
					u.setPartnerprofilId(rs.getInt("Partnerprofil_ID"));

			}
		} catch (SQLException e5) {
			e5.printStackTrace();
			return null;
		}
		return u;
	}

	/**
	 * Auslesen eines Unternehmens mit einem bestimmten Namen.
	 * 
	 * @param name
	 * @return
	 */
	public Vector<Unternehmen> findByName(String name) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Unternehmen> result = new Vector<Unternehmen>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, Partnerprofil_ID, Typ FROM organisationseinheit WHERE name='"
							+ name + "' AND Typ='" + SQLTYP + "' ORDER BY Name");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Unternehmen-Objekt erstellt.
			while (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts
				Unternehmen u = new Unternehmen();
				u.setId(rs.getInt("ID"));
				u.setName(rs.getString("Name"));
				u.setEmail(rs.getString("Email"));
				u.setStrasse(rs.getString("Strasse"));
				u.setPlz(rs.getInt("PLZ"));
				u.setOrt(rs.getString("Ort"));
				u.setTel(rs.getString("Tel"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(u);
			}
		} catch (SQLException e6) {
			e6.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Auslesen eines Unternehmens mit einer bestimmten Email.
	 * 
	 * @param email
	 * @return
	 */
	public Vector<Unternehmen> findByMail(String email) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Unternehmen> result = new Vector<Unternehmen>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, Partnerprofil_ID, Typ FROM organisationseinheit WHERE email='"
							+ email + "' AND Typ='" + SQLTYP + "' ORDER BY Email");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Unternehmen-Objekt erstellt.
			while (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts
				Unternehmen u = new Unternehmen();
				u.setId(rs.getInt("ID"));
				u.setName(rs.getString("Name"));
				u.setEmail(rs.getString("Email"));
				u.setStrasse(rs.getString("Strasse"));
				u.setPlz(rs.getInt("PLZ"));
				u.setOrt(rs.getString("Ort"));
				u.setTel(rs.getString("Tel"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(u);
			}
		} catch (SQLException e7) {
			e7.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/***
	 * Auslesen eines Unternehmens anhand einer bestimmten GoogleID.
	 * 
	 * @param googleID
	 * @return
	 */
	public Unternehmen findByGoogleId(String googleID) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Unternehmen u = null;

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, Partnerprofil_ID, Typ FROM organisationseinheit WHERE Email='"
							+ googleID + "' AND Typ='" + SQLTYP + "'");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Unternehmen-Objekt erstellt.
			if (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts
				u = new Unternehmen();
				u.setId(rs.getInt("ID"));
				u.setName(rs.getString("Name"));
				u.setEmail(rs.getString("Email"));
				u.setStrasse(rs.getString("Strasse"));
				u.setPlz(rs.getInt("PLZ"));
				u.setOrt(rs.getString("Ort"));
				u.setTel(rs.getString("Tel"));

				if(rs.getObject("Partnerprofil_ID") != null)
					u.setPartnerprofilId(rs.getInt("Partnerprofil_ID"));

			}
		} catch (SQLException e8) {
			e8.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return u;

	}
	
	public Unternehmen getByPartnerprofil(Partnerprofil pp) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Unternehmen p = null;

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("select ID from organisationseinheit where Typ='" + SQLTYP + "'  AND  Partnerprofil_ID=" + pp.getId());

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Team-Objekt erstellt.
			if (rs.next()) {

				p = findById(rs.getInt("ID"));

			}
		} catch (SQLException e9) {
			e9.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return p;

	}

}
