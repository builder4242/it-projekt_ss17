/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Beteiligung;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Projekt;

/**
 * Mapper-Klasse, die <code>Beteiligung</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte
 * koennen in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * 
 * @author Elif Yavuz
 */
public class BeteiligungMapper {

	/**
	 * Die Klasse BeteiligungMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static BeteiligungMapper beteiligungMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected BeteiligungMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>BeteiligungMapper.beteiligungMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>BeteiligungMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> BeteiligungMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>BeteiligungMapper</code>-Objekt.
	 */
	public static BeteiligungMapper beteiligungMapper() {
		if (beteiligungMapper == null) {
			beteiligungMapper = new BeteiligungMapper();
		}

		return beteiligungMapper;
	}

	/**
	 * Diese Methode ermoeglicht es eine Beteiligung in der Datenbank anzulegen.
	 * 
	 * @param bet
	 * @return
	 */
	public Beteiligung insert(Beteiligung bet) {

		String startDatum = "";

		if (bet.getStartdatum() == null)
			startDatum = "NULL";
		else
			startDatum = "'" + DBConnection.convertToSQLDateString(bet.getStartdatum()) + "'";

		String endDatum = "";

		if (bet.getEnddatum() == null)
			endDatum = "NULL";
		else
			endDatum = "'" + DBConnection.convertToSQLDateString(bet.getEnddatum()) + "'";

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Momentan hoechsten Primaerschluesselwert pruefen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM beteiligung ");

			// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {

				/*
				 * bet erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel.
				 */
				bet.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
				stmt.executeUpdate(
						"INSERT INTO beteiligung (ID, Personentage, Startdatum, Enddatum, Projekt_ID, Organisationseinheit_ID) "
								+ "VALUES ('" + bet.getId() + "','" + bet.getPersonentage() + "',"
								+ startDatum + ","
								+ endDatum + ",'" + bet.getProjektId()
								+ "','" + bet.getOrganisationseinheitId() + "')");

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// Rueckgabe, der evtl. korrigierten Beteiligung.
		return bet;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param bet
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter uebergebene Objekt
	 */

	public Beteiligung update(Beteiligung bet) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation.
			stmt.executeUpdate("UPDATE beteiligung " + "SET Personentage=\"" + bet.getPersonentage() + "\", "
					+ "Startdatum=\"" + DBConnection.convertToSQLDateString(bet.getStartdatum()) + "\", "
					+ "Enddatum=\"" + DBConnection.convertToSQLDateString(bet.getEnddatum()) + "\", " + "Projekt_ID=\""
					+ bet.getProjektId() + "\", " + "Organisationseinheit_ID=\"" + bet.getOrganisationseinheitId()
					+ "\" " + "WHERE ID=" + bet.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um Analogie zu insert(Bewertung bet) zu wahren, geben wir bt zurueck
		return bet;
	}

	/**
	 * Loeschen der Daten eines <code>Beteiligung</code>-Objekts aus der DB
	 * 
	 * @param bet
	 *            das aus der DB zu loeschende Objekt
	 * @return
	 */

	public void delete(Beteiligung bet) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM beteiligung " + "WHERE ID=" + bet.getId());
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
	}

	/**
	 * Auslesen aller Beteiligungen
	 * 
	 * @return Ein Vektor mit Beteiligung-Objekten, die saemtliche Beteiligungen
	 *         repraesentieren. Bei evtl. Exceptions wird eine partiell
	 *         gefuellter oder ggf. auch leerer Vektor zurueckgeliefert
	 */

	public Vector<Beteiligung> findAll() {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Beteiligung> result = new Vector<Beteiligung>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Personentage, Startdatum, Enddatum, Projekt_ID, Organisationseinheit_ID FROM beteiligung "
							+ " ORDER BY ID");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Beteiligung-Objekt erstellt.

			while (rs.next()) {
				Beteiligung bet = new Beteiligung();
				bet.setId(rs.getInt("ID"));
				bet.setPersonentage(rs.getInt("Personentage"));
				bet.setEnddatum(rs.getDate("Enddatum"));
				bet.setEnddatum(rs.getDate("Startdatum"));
				bet.setProjektId(rs.getInt("ID"));
				bet.setOrganisationseinheitId(rs.getInt("ID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(bet);
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Suchen einer Beteiligung mit vorgegebener Nummer. Da diese eindeutig ist,
	 * wird genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 *            Primaerschluesselattribut (->DB)
	 * @return Beteiligung-Objekt, das dem uebergebenen Schluessel entspricht,
	 *         null bei nicht vorhandenem DB-Tupel.
	 */

	public Beteiligung findByID(int id) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Personentage, Startdatum, Enddatum, Projekt_ID, Organisationseinheit_ID FROM beteiligung WHERE ID="
							+ id);

			/*
			 * Da id Prim�erschluessel ist, kann max. nur ein Tupel
			 * zurueckgegeben werden. Pruefe, ob ein Ergebnis vorliegt
			 */

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Beteiligung bet = new Beteiligung();
				bet.setId(rs.getInt("ID"));
				bet.setPersonentage(rs.getInt("Personentage"));
				bet.setEnddatum(rs.getDate("Enddatum"));
				bet.setStartdatum(rs.getDate("Startdatum"));
				bet.setProjektId(rs.getInt("Projekt_ID"));
				bet.setOrganisationseinheitId(rs.getInt("Organisationseinheit_ID"));

				return bet;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	/**
	 * Suchen einer Beteiligung anhand der Personentage.
	 * 
	 * @param personentage
	 * @return result
	 */

	public Vector<Beteiligung> findByPersonentage(int personentage) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Beteiligung> result = new Vector<Beteiligung>();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die Datenbank schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Personentage, Startdatum, Enddatum, Projekt_ID, Organisationseinheit_ID FROM beteiligung "
							+ "WHERE Personentage=" + personentage + " ORDER BY Personentage");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Beteiligung-Objekt erstellt
			while (rs.next()) {
				Beteiligung bet = new Beteiligung();
				bet.setId(rs.getInt("ID"));
				bet.setPersonentage(rs.getInt("Personentage"));
				bet.setEnddatum(rs.getDate("Enddatum"));
				bet.setStartdatum(rs.getDate("Startdatum"));
				bet.setProjektId(rs.getInt("ID"));
				bet.setOrganisationseinheitId(rs.getInt("ID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(bet);
			}
		} catch (SQLException e5) {
			e5.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}



	/**
	 * Auslesen der Beteiligung von einem Projekt
	 * 
	 * @param pr
	 * @return
	 */

	public Vector<Beteiligung> getByProjekt(Projekt pr) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Beteiligung> result = new Vector<Beteiligung>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery("SELECT ID FROM beteiligung WHERE Projekt_ID=" + pr.getId());

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Beteiligung-Objekt erstellt.
			while (rs.next()) {

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(findByID(rs.getInt("ID")));
			}
		} catch (SQLException e8) {
			e8.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Auslesen der Beteiligung von einer Organisationseinheit, die eine Person,
	 * ein Team oder Unternehmen sein kann.
	 * 
	 * @param o
	 * @return
	 */

	public Vector<Beteiligung> getByOrganisationseinheit(Organisationseinheit o) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Beteiligung> result = new Vector<Beteiligung>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT ID FROM organisationseinheit WHERE organisationseinheit.ID=" + o.getId());

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Beteiligung-Objekt erstellt.
			while (rs.next()) {

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(findByID(rs.getInt("ID")));
			}
		} catch (SQLException e9) {
			e9.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	public Beteiligung getByBewerbung(Bewerbung bw) {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Beteiligung bt = null;

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT bt.ID FROM beteiligung as bt "
					+ "inner join ausschreibung on ausschreibung.Projekt_ID=bt.Projekt_ID" + " WHERE ausschreibung.ID="
					+ bw.getAusschreibungId() + " AND bt.Organisationseinheit_ID=" + bw.getOrganisationseinheitId());

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Beteiligung-Objekt erstellt.
			if (rs.next()) {

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				bt = findByID(rs.getInt("ID"));
			}
		} catch (SQLException e9) {
			e9.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return bt;
	}
}