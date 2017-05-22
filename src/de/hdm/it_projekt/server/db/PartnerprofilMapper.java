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

import de.hdm.it_projekt.client.ProjektMarktplatz;
import de.hdm.it_projekt.shared.bo.Partnerprofil;

/**
 * Mapper-Klasse, die <code>Partnerprofil</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte koennen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * @author Tugba Bulat
 *
 */
public class PartnerprofilMapper {

	
	
	/**
	 * Die Klasse PartnerprofilMapper wird nur einmal instantiiert.
	 * Man spricht hierbei von einem sogenannten <b>Singleton</b>.
	 * <p>
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	 * saemtliche eventuellen Instanzen dieser Klasse vorhanden.
	 * Sie speichert die einzige Instanz dieser Klasse.
	 * 
	 */
	private static PartnerprofilMapper partnerprofilMapper = null;
	
	
	/***
	 * Geschuetzer Konstruktor - verhindert die Moeglichkeit, mit new neue
	 * Instanzen dieser Klasse zu erzeugen.
	 */
	protected PartnerprofilMapper(){
		
	}
	
	
	
	/**
	 * Diese statische Methode kann aufgrufen werden durch
	 * <code>PartnerprofilMapper.partnerprofilMapper()</code>.
	 * Sie stellt die Singleton-Eigenschaft sicher,
	 * indem Sie dafuer sorgt, dass nur eine einzige
	 * Instanz von <code>PartnerprofilMapper</code> existiert.
	 * <p>
	 * 
	 * <b>Fazit:</b> PartnerprofilMapper sollte nicht mittels <code>new</code>
	 * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.

	 * @return DAS <code>PartnerprofilMapper</code>-Objekt

	 */
	public static PartnerprofilMapper partnerprofilMapper() {
	    if (partnerprofilMapper == null) {
	    	partnerprofilMapper = new PartnerprofilMapper();
	    }

	    return partnerprofilMapper;
	  }

	
	
	/**
	 * Diese Methode ermoeglicht es ein Partnerprofil in der Datenbank anzulegen.
	 * 
	 * @param pp
	 * @return
	 * @throws Exception
	 */
	public Partnerprofil insert(Partnerprofil pp)
			throws Exception {
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		PreparedStatement preStmt = null;

		try {
			String sql = "INSERT INTO `Partnerprofil`(`ID`) VALUES (NULL)";
			
			preStmt = con.prepareStatement(sql);
			preStmt.executeUpdate();
			preStmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Datenbank fehler!" + e.toString());
		}
		return pp;
	}
	
	
	
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param pp - das Objekt, das in die DB geschrieben werden soll
	 * @return das als Parameter übergebene Objekt
	 */
	public Partnerprofil update(Partnerprofil pp){
	    
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

	    try {
	      Statement stmt = con.createStatement();

	      stmt.executeUpdate("UPDATE Projektmarktplatz " + "SET erstelldatum=\""
	          + Partnerprofil.getErstelldatum() + "\" " + "SET aenderungsdatum=\"" + Partnerprofil.getAenderungsdatum()
	          + "\" "+ "WHERE ID=" + Partnerprofil.getID());
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    // Um Analogie zu insert(Partnerprofil pp) zu wahren, geben wir pm zurueck
	    return pp;
	}

	
	
	/**
	 * Loeschen der Daten eines <code>Partnerprofil</code>-Objekts aus der Datenbank.

	 * @param pp
	 */
	public void delete(Partnerprofil pp){ 
		
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		Statement stmt = null;
		
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM Partnerprofil " + "WHERE ID=" + Partnerprofil.getID());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	/***
	 * Auslesen aller Partnerprofile.
	 * 
	 * @return Ein Vektor mit Partnerprofil-Objekten, die saemtliche
	 *         Partnerprofile repraesentieren. Bei evtl. Exceptions wird eine
	 *         partiell gefuellter oder ggf. auch leerer Vektor zurueckgeliefert.
	 */
	
	public Vector<Partnerprofil> findAll() {

		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();

		// Ergebnisvektor vorbereiten
		Vector<Partnerprofil> result = new Vector<Partnerprofil>();

		try {

			// Leeres SQL-Statement (JDBC) anlegen
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT ID, erstelldatum, aenderungsdatum " + "FROM Partnerprofil" + " ORDER BY ID");

			// Fuer jeden Eintrag im Suchergebnis wird nun ein
			// Partnerprofil-Objekt erstellt.

			while (rs.next()) {
				Partnerprofil pp = new Partnerprofil();
				pp.setID(rs.getInt("ID"));
				pp.setErstelldatum(rs.getDate("erstelldatum"));
				pp.setAenderungsdatum(rs.getDate("aenderungsdatum"));

				// Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(pp);
			}
		}
		catch (SQLException e4) {
			e4.printStackTrace();
		}

		// Ergebnisvektor zurueckgeben
		return result;
	}

}
