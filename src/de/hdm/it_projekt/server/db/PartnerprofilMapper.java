/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	
}
