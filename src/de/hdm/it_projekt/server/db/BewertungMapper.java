
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.it_projekt.shared.bo.Bewertung;

/**
 * Anlehnung an @author Thies
 * @author ElifY
 * 
 * Mapper-Klasse, die <code>Bewertung</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 */
public class BewertungMapper {

	/**
	   * Die Klasse BewertungMapper wird nur einmal instantiiert. Man spricht
	   * hierbei von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   */
	  private static BewertungMapper bewertungMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen. 
	   */
	  protected BewertungMapper() {
	  }

	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>BewertungMapper.bewertungMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
	   * Instanz von <code>BewertungMapper</code> existiert.
	   * <p>
	   * 
	   * <b>Fazit:</b> BewertungMapper sollte nicht mittels <code>new</code>
	   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
	   * 
	   * @return DAS <code>BewertungMapper</code>-Objekt.
	   */
	  public static BewertungMapper bewertungMapper() {
	    if (bewertungMapper == null) {
	      bewertungMapper = new BewertungMapper();
	    }

	    return bewertungMapper;
	  }

	  /**
	   * Einfügen eines <code>Bewertung</code>-Objekts in die Datenbank. Dabei
	   * wird auch der Primärschlüssel des übergebenen Objekts geprüft und ggf.
	   * berichtigt.
	   * 
	   * @param bt das zu speichernde Objekt
	   * @return das bereits übergebene Objekt, jedoch mit ggf. korrigierter
	   *         <code>id</code>.
	   */ 
	  
	  public Bewertung insert(Bewertung bt){
		   
		   //DB-Verbindung herstellen
		   Connection con = DBConnection.connection();
		   
		   try {
			   //Leeres SQL-Statement (JDBC) anlegen
			   Statement stmt = con.createStatement();
			   
			   //Momentan hoechsten Primaerschluessel pruefen
			   ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM bewertungs");
		   
			   if (rs.next()){
				   
				   /*
				    * bt erhaelt den bisher maximalen, nun um 1
				    * inkrementierten Primaerschluessel
				    */
				   
				   bt.setId(rs.getInt("maxid") + 1);
				   
				   stmt = con.createStatement();
				   
				   // Jetzt erst erfolgt die tatsächliche Einfügeoperation.
				   stmt.executeUpdate("INSERT INTO bewertungs (id, wert, stellungnahme, erstelldatum)"
				   + "VALUES (" + bt.getId() + "," + bt.getWert() + ","
				   + bt.getStellungnahme() + "," + bt.getErstelldatum() + ")");
				   
			   	 	}
		   		 	} catch (SQLException e) {
		   			 e.printStackTrace();
		   		 	}
		   
			     // Rueckgabe, der evtl. korrigierten Bewertung.
			     return bt;
		}
	  
	  			/**
				 * Wiederholtes Schreiben eines Objekts in die Datenbank.
				 * 
				 * @param bt das Objekt, das in die DB geschrieben werden soll
				 * @return das als Parameter übergebene Objekt
				 */

	  			 public Bewertung update(Bewertung bt){
	  				 
	  				 //DB-Verbindung herstellen
	  				 Connection con = DBConnection.connection();
	  				 
	  				 try{
	  					 //Leeres SQL-Statement (JDBC) anlegen
	  					 Statement stmt = con.createStatement();
	  					 
	  				     // Jetzt erst erfolgt die tatsächliche Einfügeoperation.
	  					 stmt.executeUpdate("UPDATE bewertungs " + "SET wert=\""
	  				     + bt.getWert() + "\"," + "stellungnahme=\"" + bt.getStellungnahme()
	  				     + "\", " + "erstelldatum=\"" + bt.getErstelldatum() + "\" "
	  				     + "WHERE id=" + bt.getId());
	  				 
	  				 }
	  				 catch (SQLException e2){
	  					 e2.printStackTrace();
	  				 }
	  				 
	  				 // Um Analogie zu insert(Bewertung bt) zu wahren, geben wir bt zurück
	  			     return bt;
	  			  }
	  			 
	  			 
	  			 /**
	 			 * Loeschen der Daten eines <code>Bewertung</code>-Objekts aus der Datenbank.
	 			 * 
	 			 * @param bt
	 			 *            das aus der DB zu loeschende Objekt
	 			 */
	 		 
	 		 
	 		 public void delete (Bewertung bt){
	 			 
	 			 //DB-Verbindung herstellen
	 			 Connection con = DBConnection.connection();
	 			 
	 			 try{
	 				 
	 				 //Leeres SQL-Statement (JDBC) anlegen
	 				 Statement stmt = con.createStatement();
	 				 
	 				 stmt.executeUpdate("DELETE FROM bewertungs" + "WHERE id=" + bt.getId());
	 			 }
	 			 catch (SQLException e3){
	 				 e3.printStackTrace();
	 			 }
	 		 }
	 		 
}