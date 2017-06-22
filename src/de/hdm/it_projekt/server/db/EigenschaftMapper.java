package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Eigenschaft;
import de.hdm.it_projekt.shared.bo.Partnerprofil;

/**
 * Mapper-Klasse, die <code>Eigenschaft</code>-Objekte auf eine relationale
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

public class EigenschaftMapper {

	/**
	 * Die Klasse EigenschaftMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static EigenschaftMapper eigenschaftMapper = null;

	/**
	 * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected EigenschaftMapper() {
	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>EigenschaftMapper.eigenschaftMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>EigenschaftMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> EigenschaftMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>EigenschaftMapper</code>-Objekt.
	 */
	public static EigenschaftMapper eigenschaftMapper() {
		if (eigenschaftMapper == null) {
			eigenschaftMapper = new EigenschaftMapper();
		}

		return eigenschaftMapper;
	}

	/**
	 * Einfuegen eines <code>Eigenschaft</code>-Objekts in die Datenbank. Dabei
	 * wird auch der Primaerschluessel des uebergebenen Objekts geprueft und
	 * ggf. berichtigt.
	 * 
	 * @param e
	 *            das zu speichernde Objekt
	 * @return das bereits uebergeben Objekt, jedoch mit ggf. korrigierter
	 *         <code>id</id>
	 */
	public Eigenschaft insert(Eigenschaft e) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Momentan hoechsten Primaerschluesselwert pruefen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM eigenschaft ");

			// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {

				/*
				 * e erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel.
				 */

				e.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
				stmt.executeUpdate(
						"INSERT INTO eigenschaft (ID, Name, Wert, Partnerprofil_ID) " + "VALUES ('" + e.getId() + "','"
								+ e.getName() + "','" + e.getWert() + "','" + e.getPartnerprofilId() + "')");

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Rueckgabe, der evtl. korrigierten Eigenschaft.
		return e;
	}

	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param e
	 *            das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter uebergebene Objekt
	 */

	public Eigenschaft update(Eigenschaft e) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
			stmt.executeUpdate("UPDATE eigenschaft SET Name='" + e.getName() + "', Wert='" + e.getWert() + "' WHERE ID=" + e.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um Analogie zu insert(Eigenschaft e) zu wahren, geben wir e zurueck
		return e;
	}

	/**
	 * Loeschen der Daten eines <code>Eigenschaft</code>-Objekts aus der DB
	 * 
	 * @param e
	 *            das aus der DB zu loeschende Objekt
	 * @return
	 */

	public void delete(Eigenschaft e) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM eigenschaft " + "WHERE ID=" + e.getId());
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
	}

	/**
	 * Auslesen aller Eigenschaften
	 * 
	 * @return Ein Vektor mit Eigenschaft-Objekten, die saemtliche Eigenschaften
	 *         repraesentieren. Bei evtl. Exceptions wird eine partiell
	 *         gefuellter oder ggf. auch leerer Vektor zurueckgeliefert
	 */

	public Vector<Eigenschaft> findAll() {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Eigenschaft> result = new Vector<Eigenschaft>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT ID, Name, Wert, Partnerprofil_ID FROM eigenschaft " + " ORDER BY ID");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Eigenschaft-Objekt erstellt.
			while (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setId(rs.getInt("ID"));
				e.setName(rs.getString("Name"));
				e.setWert(rs.getString("Wert"));
				e.setPartnerprofilId(rs.getInt("Partnerprofil_ID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(e);
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Suchen einer Eigenschaft mit vorgegebener ID. Da diese eindeutig ist,
	 * wird genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 *            Primaerschluesselattribut in DB
	 * @return Eigenschaft-Objekt, das dem uebergebenen Schluessel entspricht,
	 *         null bei nicht vorhandenem DB-Tupel.
	 */

	public Eigenschaft findById(int id) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Wert, Partnerprofil_ID FROM eigenschaft WHERE ID= " + id);

			/*
			 * Da id der Primaerschluessel ist, kann maximal nur ein Tupel
			 * zurueckgegeben werden. Pruefung, ob ein Ergebnis vorliegt.
			 */

			if (rs.next()) {

				// Ergebnis-Tupel in Objekt umwandeln
				Eigenschaft e = new Eigenschaft();
				e.setId(rs.getInt("ID"));
				e.setName(rs.getString("Name"));
				e.setWert(rs.getString("Wert"));
				e.setPartnerprofilId(rs.getInt("Partnerprofil_ID"));

				return e;
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Auslesen aller Eigenschaft-Objekte mit gegebenem Namen
	 * 
	 * @param name
	 * @return Ein Vektor mit Eigenschaft-Objekten, die saemtliche Eigenschaften
	 *         mit dem gesuchten Namen repraesentieren. Bei evtl. Exceptions
	 *         wird ein partiell gefuellter oder ggf. auch leerer Vetor
	 *         zurueckgeliefert.
	 */

	public Vector<Eigenschaft> findByName(String name) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Eigenschaft> result = new Vector<Eigenschaft>();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Wert, Partnerprofil_ID FROM eigenschaft WHERE name='" + name + "' ORDER BY name");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Eigenschaft-Objekt
			// erstellt.
			while (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setId(rs.getInt("ID"));
				e.setName(rs.getString("Name"));
				e.setWert(rs.getString("Wert"));
				e.setPartnerprofilId(rs.getInt("ID"));

				// Hinzufuegen des neuen Eigenschafts zum Ergebnisvektor
				result.addElement(e);
			}
		} catch (SQLException e5) {
			e5.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Auslesen aller Eigenschaften mit gegebenem Wert
	 * 
	 * @param wert
	 * @return Ein Vektor mit Eigenschaft-Objekten, die saemtliche Eigenschaften
	 *         mit dem gesuchten Wert repraesentieren. Bei evtl. Exceptions wird
	 *         ein partiell gefuellter oder ggf. auch leerer Vetor
	 *         zurueckgeliefert.
	 */

	public Vector<Eigenschaft> findByWert(String wert) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Eigenschaft> result = new Vector<Eigenschaft>();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken

			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Name, Wert, Partnerprofil_ID FROM eigenschaft WHERE Wert='" + wert + "' ORDER BY Wert");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Eigenschhaft-Objekt
			// erstellt.
			while (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setId(rs.getInt("ID"));
				e.setName(rs.getString("Name"));
				e.setWert(rs.getString("Wert"));
				e.setPartnerprofilId(rs.getInt("ID"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(e);
			}
		} catch (SQLException e6) {
			e6.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	public Vector<Eigenschaft> getByPartnerprofil(Partnerprofil pp) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Vector<Eigenschaft> result = new Vector<Eigenschaft>();

		try {
			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfüllen und als Query an die DB schicken
			ResultSet rs = stmt
					.executeQuery("SELECT ID FROM eigenschaft WHERE Partnerprofil_ID=" + pp.getId());

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Eigenschhaft-Objekt
			// erstellt.
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

}
