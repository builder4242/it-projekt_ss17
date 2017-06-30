package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

/**
 * Mapper-Klasse, die <code>Projekt</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte
 * koennen in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 *
 * Anlehnung an @author Thies
 * 
 * @author ElifY
 */
public class ProjektMapper {

	/**
	 * Die Klasse ProjektMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see projektMapper()
	 */
	private static ProjektMapper projektMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected ProjektMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>ProjektMapper.projektMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>ProjektMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> ProjektMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>ProjektMapper</code>-Objekt.
	 * @see projektMapper
	 */
	public static ProjektMapper projektMapper() {
		if (projektMapper == null) {
			projektMapper = new ProjektMapper();
		}

		return projektMapper;
	}

	/**
	 * Diese Methode ermoeglicht es ein Projekt in der Datenbank anzulegen.
	 * 
	 * @param pr
	 * @return
	 */

	public Projekt insert(Projekt pr) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Momentan hoechsten Primaerschluesselwert pruefen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM projekt ");

			if (rs.next()) {

				/*
				 * pr erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel.
				 */

				pr.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation.
				stmt.executeUpdate(
						"INSERT INTO projekt (ID, Name, Startdatum, Enddatum, Beschreibung, Projektmarktplatz_ID, Projektleiter_ID) "
								+ "VALUES ('" + pr.getId() + "','" + pr.getName() + "','"
								+ DBConnection.convertToSQLDateString(pr.getStartdatum()) + "','"
								+ DBConnection.convertToSQLDateString(pr.getEnddatum()) + "','" + pr.getBeschreibung()
								+ "','" + pr.getProjektMarktplatzId() + "','"
								+ pr.getProjektleiterId() + "')");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Rueckgabe, des evtl. korrigierten Projekts.
		return pr;
	}

	/**
	 * Wiederholtes Schreiben eines Projekt-Objekts in die Datenbank.
	 * 
	 * @param pr
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter uebergebene Objekt
	 */

	public Projekt update(Projekt pr) {

		// DB-Verbindung holen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement(JDBC) anlegen
			Statement stmt = con.createStatement();

			// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation.
			stmt.executeUpdate("UPDATE projekt " + "SET Name=\"" + pr.getName() + "\", " + "Startdatum=\""

					+ DBConnection.convertToSQLDateString(pr.getStartdatum()) + "\", " + "Enddatum=\""
					+ DBConnection.convertToSQLDateString(pr.getEnddatum()) + "\", " + "Beschreibung=\""

					+ pr.getBeschreibung() + "\", " + "Projektmarktplatz_ID=\"" + pr.getProjektMarktplatzId() + "\", "
					+ "Projektleiter_ID=\""	+ pr.getProjektleiterId() + "\" " + "WHERE ID=" + pr.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Rueckgabe des evtl. korrigierten Projekts
		return pr;

	}

	/**
	 * Loeschen der Daten eines <code>Projekt</code>-Objekts aus der Datenbank.
	 * 
	 * @param projekt
	 *            das aus der DB zu loeschende Objekt
	 */

	public void delete(Projekt pr) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation.
			stmt.executeUpdate("DELETE FROM projekt " + "WHERE ID=" + pr.getId());
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
	}

	/**
	 * Auslesen aller Projekte.
	 * 
	 * @return Ein Vektor mit Projekt-Objekten, die saemtliche Projekte
	 *         repraesentieren. Bei evtl. Exceptions wird eine partiell
	 *         gefuellter oder ggf. auch leerer Vektor zurueckgeliefert.
	 */
	public Vector<Projekt> findAll() {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Projekt> result = new Vector<Projekt>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Startdatum, Enddatum, Beschreibung, Projektmarktplatz_ID, Projektleiter_ID FROM projekt "
							+ "ORDER BY Name ");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Projekt-Objekt erstellt.
			while (rs.next()) {
				Projekt pr = new Projekt();
				pr.setId(rs.getInt("ID"));
				pr.setName(rs.getString("Name"));
				pr.setStartdatum(rs.getDate("Startdatum"));
				pr.setEnddatum(rs.getDate("Enddatum"));
				pr.setBeschreibung(rs.getString("Beschreibung"));
				pr.setProjektMarktplatzId(rs.getInt("Projektmarktplatz_ID"));
				pr.setProjektleiterId(rs.getInt("Projektleiter_ID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(pr);
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Suchen eines Projekts mit vorgegebener ID. Da diese eindeutig ist, wird
	 * genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 *            Primaerschluesselattribut in DB
	 * @return Projekt-Objekt, das dem uebergebenen Schluessel entspricht, null
	 *         bei nicht vorhandenem DB-Tupel.
	 */
	public Projekt findById(int id) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Startdatum, Enddatum, Beschreibung, Projektmarktplatz_ID, Projektleiter_ID FROM projekt WHERE ID= "
							+ id + " ORDER BY ID");

			/*
			 * Da id der Primaerschluessel ist, kann maximal nur ein Tupel
			 * zurueckgegeben werden. Pruefung, ob ein Ergebnis vorliegt.
			 */

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Projekt pr = new Projekt();
				pr.setId(rs.getInt("ID"));
				pr.setName(rs.getString("Name"));
				pr.setStartdatum(rs.getDate("Startdatum"));
				pr.setEnddatum(rs.getDate("Enddatum"));
				pr.setBeschreibung(rs.getString("Beschreibung"));
				pr.setProjektMarktplatzId(rs.getInt("Projektmarktplatz_ID"));
				pr.setProjektleiterId(rs.getInt("Projektleiter_ID"));

				return pr;
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Diese Methode ermoeglicht eine Ausgabe ueber Nutzer in der DB anhand
	 * deren Namen.
	 * 
	 * @param name
	 * @return result
	 */

	public Vector<Projekt> findByName(String name) {
		Connection con = DBConnection.connection();
		Vector<Projekt> result = new Vector<Projekt>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Startdatum, Enddatum, Beschreibung, Projektmarktplatz_ID, Projektleiter_ID FROM projekt WHERE name='"
							+ name + "' ORDER BY name");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Projekt-Objekt erstellt.
			while (rs.next()) {
				Projekt pr = new Projekt();
				pr.setId(rs.getInt("ID"));
				pr.setName(rs.getString("Name"));
				pr.setStartdatum(rs.getDate("Startdatum"));
				pr.setEnddatum(rs.getDate("Enddatum"));
				pr.setBeschreibung(rs.getString("Beschreibung"));
				pr.setProjektMarktplatzId(rs.getInt("Projektmarktplatz_ID"));
				pr.setProjektleiterId(rs.getInt("Projektleiter_ID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(pr);
			}
		} catch (SQLException e5) {
			e5.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}


	/**
	 * Auslesen des zugehoerigen <code>Projekt</code>-Objekts zu einem gegebenen
	 * Projektmarktplatzes.
	 * 
	 * @param pm
	 * @return
	 */

	public Vector<Projekt> getByProjektmarktplatz(ProjektMarktplatz pm) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Projekt> result = new Vector<Projekt>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT ID FROM projekt WHERE Projektmarktplatz_ID=" + pm.getId());

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Projekt-Objekt erstellt.
			while (rs.next()) {

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(findById(rs.getInt("ID")));
			}
		} catch (SQLException e7) {
			e7.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Das Erhalten des Projektes von einem Projektleiter, der eine Person ist
	 * 
	 * @param p
	 * @return
	 */
	public Vector<Projekt> getByProjektleiter(Person p, ProjektMarktplatz pm) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Projekt> result = new Vector<Projekt>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT ID FROM projekt WHERE Projektleiter_ID=" + p.getId() + " AND Projektmarktplatz_ID=" + pm.getId());
			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Projekt-Objekt erstellt.
			while (rs.next()) {

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(findById(rs.getInt("ID")));
			}
		} catch (SQLException e8) {
			e8.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}


}
