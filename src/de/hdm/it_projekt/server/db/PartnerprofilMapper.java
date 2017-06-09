/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Partnerprofil;

/**
 * Mapper-Klasse, die <code>Partnerprofil</code>-Objekte auf eine relationale
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
public class PartnerprofilMapper {

	/**
	 * Die Klasse PartnerprofilMapper wird nur einmal instantiiert. Man spricht
	 * hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
	 * fuer saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
	 * speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static PartnerprofilMapper partnerprofilMapper = null;

	/***
	 * Geschuetzer Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected PartnerprofilMapper() {

	}

	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>PartnerprofilMapper.partnerprofilMapper()</code>. Sie stellt die
	 * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
	 * einzige Instanz von <code>PartnerprofilMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> PartnerprofilMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen
	 * Methode.
	 * 
	 * @return DAS <code>PartnerprofilMapper</code>-Objekt
	 */
	public static PartnerprofilMapper partnerprofilMapper() {
		if (partnerprofilMapper == null) {
			partnerprofilMapper = new PartnerprofilMapper();
		}

		return partnerprofilMapper;
	}

	/**
	 * Diese Methode ermoeglicht es ein Partnerprofil in der Datenbank
	 * anzulegen.
	 * 
	 * @param pp
	 * @return
	 */
	public Partnerprofil insert(Partnerprofil pp) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			/*
			 * Zunaechst schauen wir nach, welches der momentan hoechste
			 * Primaerschluesselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM partnerprofil ");

			// Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
			if (rs.next()) {

				/*
				 * pp erhaelt den bisher maximalen, nun um 1 inkrementierten
				 * Primaerschluessel.
				 */
				pp.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				// Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
				stmt.executeUpdate("INSERT INTO partnerprofil (ID, Erstelldatum, Aenderungsdatum) " + "VALUES ('"
						+ pp.getId() + "','" + DBConnection.convertToSQLDateString(pp.getErstelldatum()) + "','"
						+ DBConnection.convertToSQLDateString(pp.getAenderungsdatum()) + "')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Rueckgabe, des evtl. korrigierten Partnerprofils.
		return pp;
	}

	/**
	 * Wiederholtes Schreiben eines Partnerprofil-Objekts in die Datenbank.
	 * 
	 * @param pp
	 *            - das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter uebergebene Objekt
	 */
	public Partnerprofil update(Partnerprofil pp) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE partnerprofil SET Erstelldatum=\"" + pp.getErstelldatum() + "\", "
					+ "Aenderungsdatum=\"" + pp.getAenderungsdatum() + "\" WHERE ID= " + pp.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Um die Analogie zu insert(Partnerprofil pp) zu wahren,
		// geben wir pp zurueck
		return pp;
	}

	/**
	 * Loeschen der Daten eines <code>Partnerprofil</code>-Objekts aus der
	 * Datenbank.
	 * 
	 * @param pp
	 */
	public void delete(Partnerprofil pp) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Statement stmt = null;

		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM partnerprofil WHERE ID=" + pp.getId());
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
	}

	/**
	 * Auslesen aller Partnerprofile.
	 * 
	 * @return Ein Vektor mit Partnerprofil-Objekten, die saemtliche
	 *         Partnerprofile repraesentieren. Bei evtl. Exceptions wird eine
	 *         partiell gefuellter oder ggf. auch leerer Vektor
	 *         zurueckgeliefert.
	 */
	public static Vector<Partnerprofil> findAll() {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Partnerprofil> result = new Vector<Partnerprofil>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ID, Erstelldatum, Aenderungsdatum FROM partnerprofil ORDER BY ID");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Partnerprofil-Objekt erstellt.

			while (rs.next()) {
				Partnerprofil pp = new Partnerprofil();
				pp.setId(rs.getInt("ID"));
				pp.setErstelldatum(rs.getDate("Erstelldatum"));
				pp.setAenderungsdatum(rs.getDate("Aenderungsdatum"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(pp);
			}
		} catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

	/**
	 * Suchen eines Partnerprofils mit vorgegebener ID. Da diese eindeutig ist,
	 * wird genau ein Objekt zurueckgegeben.
	 * 
	 * @param ID
	 *            - Primaerschluesselattribut in DB
	 * @return Partnerprofil-Objekt, das dem uebergebenen Schluessel entspricht,
	 *         null bei nicht vorhandenem DB-Tupel.
	 */
	public Partnerprofil findById(int id) {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			// Statement ausfuellen und als Query an die DB schicken
			ResultSet rs = stmt.executeQuery(
					"SELECT ID, Erstelldatum, Aenderungsdatum FROM partnerprofil WHERE ID= " + id + " ORDER BY ID");

			/*
			 * Da ID der Primaerschluessel ist, kann maximal nur ein Tupel
			 * zurueckgegeben werden. Pruefung, ob ein Ergebnis vorliegt.
			 */
			if (rs.next()) {

				// Umwandlung des Ergebnis-Tupel in ein Objekt und Ausgabe des
				// Ergebnis-Objekts
				Partnerprofil pp = new Partnerprofil();

				pp.setId(rs.getInt("ID"));
				pp.setErstelldatum(rs.getDate("erstelldatum"));
				pp.setAenderungsdatum(rs.getDate("aenderungsdatum"));

				return pp;
			}
		} catch (SQLException e5) {
			e5.printStackTrace();
			return null;
		}
		return null;
	}

}
