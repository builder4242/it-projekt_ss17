/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;
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

	/***
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen
	 */
	protected UnternehmenMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
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
						"INSERT INTO organisationseinheit (ID, Name, Email, Strasse, PLZ, Ort, Tel, GoogleID) "
								+ "VALUES (" + u.getId() + "," + u.getName() + "," + u.getEmail() + "," + u.getStrasse()
								+ "," + u.getPlz() + "," + u.getOrt() + "," + u.getTel() + "," + u.getGoogleID() + ")");
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
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE organisationseinheit " + "SET Name=\"" + u.getName() + "\" " + "SET Email=\""
					+ u.getEmail() + "\" " + "SET Strasse=\"" + u.getStrasse() + "\" " + "SET Plz=\"" + u.getPlz()
					+ "\" " + "SET Ort=\"" + u.getOrt() + "\" " + "SET Tel=\"" + u.getTel() + "\" " + "SET GoogleID=\""
					+ u.getGoogleID() + "\" " + "WHERE ID=" + u.getId());
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

			ResultSet rs = stmt.executeQuery("SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, GoogleID "
					+ "FROM organisationseinheit" + " ORDER BY ID");

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
				u.setGoogleID(rs.getString("GoogleID"));

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

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, GoogleID FROM organisationseinheit"
							+ "WHERE ID=" + id + " ORDER BY ID");

			/*
			 * Da ID der Primaerschluessel ist, kann maximal nur ein Tupel
			 * zurueckgegeben werden. Pruefung, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {
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
				u.setGoogleID(rs.getString("GoogleID"));

				return u;
			}
		} catch (SQLException e5) {
			e5.printStackTrace();
			return null;
		}
		return null;
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
					"SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, GoogleID FROM organisationseinheit WHERE Name='"
							+ name + "' AND Typ='U' ORDER BY Name");

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
				u.setGoogleID(rs.getString("GoogleID"));

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
					"SELECT ID, Name, Email, Strasse, PLZ, Ort, Tel, GoogleID FROM organisationseinheit WHERE Email='"
							+ email + "' AND Typ='U' ORDER BY Email");

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
				u.setGoogleID(rs.getString("GoogleID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(u);
			}
		} catch (SQLException e7) {
			e7.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Erhalten des Unternehmens anhand eines Projekts.
	 * 
	 * @param pr
	 * @return
	 */
	public Vector<Unternehmen> getByProjekt(Projekt pr) {
		return null;
	}

	/**
	 * Erhalten des Unternehmens anhand eines ProjektMarktplatzes.
	 * 
	 * @param pm
	 * @return
	 */
	public Vector<Unternehmen> getByProjektMarktplatz(ProjektMarktplatz pm) {
		return null;
	}

}
