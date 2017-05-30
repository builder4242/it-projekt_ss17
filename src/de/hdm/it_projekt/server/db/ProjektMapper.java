package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Person;
import de.hdm.it_projekt.shared.bo.Projekt;

/**
 * Mapper-Klasse, die <code>Projekt</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte
 * können in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
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
	 * für sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 * @see projektMapper()
	 */
	private static ProjektMapper projektMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected ProjektMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>ProjektMapper.projektMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine
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
	 * Diese Methode ermöglicht es ein Projekt in der Datenbank anzulegen.
	 * 
	 * @param Projekt
	 * @return pr
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
				stmt.executeUpdate("INSERT INTO projekt (Id, Name, Startdatum, Enddatum, Beschreibung) " + "VALUES ("
						+ pr.getId() + "," + pr.getName() + "," + pr.getStartdatum() + "," + pr.getEnddatum() + ","
						+ pr.getBeschreibung() + ")");
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
			stmt.executeUpdate("UPDATE projekt " + "SET Name=\"" + pr.getName() + "\"," + "Startdatum=\""
					+ pr.getStartdatum() + "\", " + "Enddatum=\"" + pr.getEnddatum() + "\" " + "\", "
					+ "Beschreibung=\"" + pr.getBeschreibung() + "\" " + "WHERE Id=" + pr.getId());

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
			stmt.executeUpdate("DELETE FROM projekt" + "WHERE Id=" + pr.getId());
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
					"SELECT Id, Name, Startdatum, Enddatum, Beschreibung " + "FROM projekt " + " ORDER BY Name");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Projekt-Objekt erstellt.
			while (rs.next()) {
				Projekt pr = new Projekt();
				pr.setId(rs.getInt("Id"));
				pr.setName(rs.getString("Name"));
				pr.setStartdatum(rs.getDate("Startdatum"));
				pr.setEnddatum(rs.getDate("Enddatum"));
				pr.setBeschreibung(rs.getString("Beschreibung"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(pr);
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
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
			ResultSet rs = stmt.executeQuery("SELECT Id, Name, Startdatum, Enddatum, Beschreibung FROM projekt "
					+ "WHERE Id=" + id + " ORDER BY Id");

			/*
			 * Da id der Primaerschluessel ist, kann maximal nur ein Tupel
			 * zurueckgegeben werden. Pruefung, ob ein Ergebnis vorliegt.
			 */

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Projekt pr = new Projekt();
				pr.setId(rs.getInt("Id"));
				pr.setName(rs.getString("Name"));
				pr.setStartdatum(rs.getDate("Startdatum"));
				pr.setEnddatum(rs.getDate("Enddatum"));
				pr.setBeschreibung(rs.getString("Beschreibung"));

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

			ResultSet rs = stmt.executeQuery("SELECT Id, Name, Startdatum, Enddatum, Beschreibung" + "FROM projekt "
					+ "WHERE Name LIKE=" + name + " ORDER BY Name");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Projekt-Objekt erstellt.
			while (rs.next()) {
				Projekt pr = new Projekt();
				pr.setId(rs.getInt("Id"));
				pr.setName(rs.getString("Name"));
				pr.setStartdatum(rs.getDate("Startdatum"));
				pr.setEnddatum(rs.getDate("Enddatum"));
				pr.setBeschreibung(rs.getString("Beschreibung"));

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
	 * Diese Methode ermoeglicht eine Ausgabe ueber Ausschreibung in der DB
	 * anhand deren Beschreibung.
	 *
	 * @param beschreibung
	 * @return pr
	 */
	public Vector<Projekt> findByBeschreibung(String beschreibung) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		Vector<Projekt> result = null;

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT Id, Name, Startdatum, Enddatum, Beschreibung" + "FROM projekt "
					+ "WHERE Beschreibung =" + beschreibung + " ORDER BY Beschreibung");

			result = new Vector<Projekt>();
			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Projekt-Objekt erstellt.
			while (rs.next()) {
				Projekt pr = new Projekt();
				pr.setId(rs.getInt("Id"));
				pr.setName(rs.getString("Name"));
				pr.setStartdatum(rs.getDate("Startdatum"));
				pr.setEnddatum(rs.getDate("Enddatum"));
				pr.setBeschreibung(rs.getString("Beschreibung"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(pr);
			}
		} catch (SQLException e6) {
			e6.printStackTrace();
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

	public Vector<Projekt> getByProjektmarktplatz(Projektmarktplatz pm) {
		return null;
	}

	/**
	 * Das Erhalten des Projektes von einem Projektleiter, der eine Person ist
	 * 
	 * @param p
	 * @return
	 */
	public Vector<Projekt> getByProjektleiter(Person p) {
		return null;

	}
}
