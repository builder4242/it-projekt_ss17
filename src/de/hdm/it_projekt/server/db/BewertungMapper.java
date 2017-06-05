
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


import de.hdm.it_projekt.shared.bo.Bewertung;
import de.hdm.it_projekt.shared.bo.Person;

/**
 * Anlehnung an @author Thies
 * @author ElifY
 * 
 * Mapper-Klasse, die <code>Bewertung</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte koennen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 */
public class BewertungMapper {

	/**
	   * Die Klasse BewertungMapper wird nur einmal instantiiert. Man spricht
	   * hierbei von einem sogenannten <b>Singleton</b>.
	   * <p>
	   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
	   * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	   * einzige Instanz dieser Klasse.
	   * 
	   */
	  private static BewertungMapper bewertungMapper = null;
	  private Vector<Bewertung> result;

	  /**
	   * Geschuetzter Konstruktor - verhindert die Moeglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen. 
	   */
	  protected BewertungMapper() {
	  }

	  /**
	   * Diese statische Methode kann aufgrufen werden durch
	   * <code>BewertungMapper.bewertungMapper()</code>. Sie stellt die
	   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
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
	   * Einfuegen eines <code>Bewertung</code>-Objekts in die Datenbank. Dabei
	   * wird auch der Primaerschluessel des uebergebenen Objekts geprueft und ggf.
	   * berichtigt.
	   * 
	   * @param bt das zu speichernde Objekt
	   * @return das bereits uebergebene Objekt, jedoch mit ggf. korrigierter
	   *         <code>id</code>.
	   */ 
	  
	  public Bewertung insert(Bewertung bt){
		   
		   //DB-Verbindung herstellen
		   Connection con = DBConnection.connection();
		   
		   try {
			   //Leeres SQL-Statement (JDBC) anlegen
			   Statement stmt = con.createStatement();
			   
			   //Momentan hoechsten Primaerschluessel pruefen
			   ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM bewertung");
		   
			   if (rs.next()){
				   
				   /*
				    * bt erhaelt den bisher maximalen, nun um 1
				    * inkrementierten Primaerschluessel
				    */
				   
				   bt.setId(rs.getInt("maxid") + 1);
				   
				   stmt = con.createStatement();
				   
				   // Jetzt erst erfolgt die tatsaechliche Einfuegeoperation.
				   stmt.executeUpdate("INSERT INTO bewertung (Id, Wert, Stellungnahme, Erstelldatum)"
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
				 * @return das als Parameter uebergebene Objekt
				 */

	  			 public Bewertung update(Bewertung bt){
	  				 
	  				 //DB-Verbindung herstellen
	  				 Connection con = DBConnection.connection();
	  				 
	  				 try{
	  					 //Leeres SQL-Statement (JDBC) anlegen
	  					 Statement stmt = con.createStatement();
	  					 
	  				     // Jetzt erst erfolgt die tatsächliche Einfügeoperation.
	  					 stmt.executeUpdate("UPDATE bewertung " + "SET Wert=\""
	  				     + bt.getWert() + "\"," + "Stellungnahme=\"" + bt.getStellungnahme()
	  				     + "\", " + "Erstelldatum=\"" + bt.getErstelldatum() + "\" "
	  				     + "WHERE id=" + bt.getId());
	  				 
	  				 }
	  				 catch (SQLException e2){
	  					 e2.printStackTrace();
	  				 }
	  				 
	  				 // Um Analogie zu insert(Bewertung bt) zu wahren, geben wir bt zurueck
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
	 				 
	 				 stmt.executeUpdate("DELETE FROM bewertung" + "WHERE Id=" + bt.getId());
	 			 }
	 			 catch (SQLException e3){
	 				 e3.printStackTrace();
	 			 }
	 		 }
	 		
	 		 	/**
				 * Auslesen aller Bewertungen.
				 * 
				 * @return Ein Vektor mit Bewertung-Objekten, die saemtliche
				 *         Bewertungen repraesentieren. Bei evtl. Exceptions wird eine
				 *         partiell gefuellter oder ggf. auch leerer Vektor zurueckgeliefert.
				 */
			 	
			 	 public Vector<Bewertung> findAll(){
			 		 
			 		 //DB-Verbindung herstellen
			 		 Connection con = DBConnection.connection();
			 		 
			 		 //Ergebnisvektor vorbereiten
			 		 Vector<Bewertung> result = new Vector<Bewertung>();
			 		 
			 		 try{
			 			
			 			 //Leeres SQL-Statement (JDBC) anlegen
			 			 Statement stmt = con.createStatement();
			 			 
			 			 ResultSet rs = stmt.executeQuery("SELECT Id, Wert, Stellungnahme, Erstelldatum FROM bewertung "
			 				+ "ORDER BY Wert");
			 				
			 			 
			 			 //Fuer jeden Eintrag im Suchergebnis wird nun ein
			 			// Bewertung-Objekt erstellt.
			 		 
			 			 while (rs.next()) {
			 				 Bewertung bt = new Bewertung();
			 				 bt.setId(rs.getInt("Id"));
			 				 bt.setWert(rs.getFloat("Wert"));
			 				 bt.setStellungnahme(rs.getString("Stellungnahme"));
			 				 bt.setErstelldatum(rs.getDate("Erstelldatum"));
			 				
			 				 
			 			 
			 			// Hinzufuegen des neuen Objekts zum Ergebnisvektor
			 		       result.addElement(bt);
			 		      		}
			 		 		}
			 		    catch (SQLException e4) {
			 		    e4.printStackTrace();
			 		    		}	

			 		   	// Ergebnisvektor zurueckgeben
			 		   	return result;
			 	 		}
			 	 
	 		 
			 	
			 	/** Suchen einer Bewertung mit vorgegebener ID. Da diese eindeutig ist,
			 	  * wird genau ein Objekt zurueckgegeben.
			 	  * 
			 	  * @param id
			 	  * 	Primaerschluesselattribut in DB
			 	  * @return Bewertung-Objekt, das dem uebergebenen Schluessel entspricht,
			 	  * null bei nicht vorhandenem DB-Tupel.
			 	  */
			 	 
			 	 public Bewertung findById(int id){
			 		 
			 		 //DB-Verbindung herstellen
			 		 Connection con = DBConnection.connection();
			 		 
			 		 try{
			 			 
			 		  //Leeres SQL-Statement (JDBC) anlegen
			 		  Statement stmt = con.createStatement();
			 			 
			 		 //Statement ausfuellen und als Query an die DB schicken
			 		 ResultSet rs= stmt.executeQuery("SELECT Id, Wert, Stellungnahme, Ertselldatum FROM bewertung "
			 				 + "WHERE Id=" + id + "ORDER BY Id");
			 		 		
			 		 /*
			 		  * Da id der Primaerschluessel ist, kann maximal nur ein Tupel
			 		  * zurueckgegeben werden.
			 		  * Pruefung, ob ein Ergebnis vorliegt.
			 		  */
			
			 			 if (rs.next()){
			 				 
			 				 //Umwandlung des Ergebnis-Tupel in ein Objekt und
			 				 //Ausgabe des Ergebnis-Objekts.
			 				 
			 				 Bewertung bt = new Bewertung();
			 				 bt.setId(rs.getInt("Id"));
			 				 bt.setWert(rs.getFloat("Wert"));
			 				 bt.setStellungnahme(rs.getString("Stellungnahme"));
			 				 bt.setErstelldatum(rs.getDate("Erstelldatum"));
			 				 
			 				 return bt;
			 			 }
			 		 } 
			 		 	catch (SQLException e5){
			 		 		e5.printStackTrace();
			 		 		return null;
			 		 	}
			 		 
			 		 return null;
			 	 	}
			 	 
			 	 /**
			 	  * Suchen einer Bewertung anhand einer Stellungnahme.
			 	  * @param stellungnahme
			 	  * @return result
			 	  */
				 	
			 	 
				public Vector<Bewertung> findByStellungnahme (String stellungnahme){
					
			 		 //DB-Verbindung herstellen
			 		 Connection con = DBConnection.connection();
			 		
			 		 //Ergebnisvektor vorbereiten
			 		 Vector<Bewertung> result = new Vector<Bewertung>();
			 		
			 		 
					try{
			 			 //Leeres SQL-Statement (JDBC) anlegen
			 			 Statement stmt = con.createStatement();
			 			 
			 			 //Statement ausfuellen und als Query an die DB schicken
			 			 ResultSet rs = stmt.executeQuery("SELECT Id, Wert, Stellungnahme, Erstelldatum FROM bewertung "
			 					 + "WHERE Stellungnahme='" + stellungnahme + "' ORDER BY Stellungnahme");
			 			 
			 			 
			 			  //Fuer jeden Eintrag im Suchergebnis wird nun ein Bewertung-Objekt
			 			  //erstellt
			 			  while (rs.next()){
			 				  Bewertung bt= new Bewertung();
			 				  bt.setId(rs.getInt("Id"));
			 				  bt.setWert(rs.getFloat("Wert"));
			 				  bt.setStellungnahme(rs.getString("Stellungnahme"));
			 				  bt.setErstelldatum(rs.getDate("Erstelldatum"));
			 				  
			 				  //Hinzufuegen des neuen Objekts zum Ergebnisvektor
			 				  result.addElement(bt);
			 			  }
			 		   }
			 		 catch (SQLException e6){
			 			 e6.printStackTrace();
			 		 }
			 		 
			 		 //Ergebnisvektor zurueckgeben
			 		 return result;
			 				  
			 			  }
			 			
				
				/**
				 * Suchen einer Bewertung anhand eines Wertes.
				 * @param wert
				 * @return result
				 */
				public Vector<Bewertung> findByWert(float wert){
					
					//DB-Verbindung herstellen
					Connection con = DBConnection.connection();
					
					try {
						//Leeres SQL-Statement (JDBC) anlegen
						Statement stmt = con.createStatement();
						
						//Statement ausfuellen und als Query an die DB schicken
						ResultSet rs = stmt.executeQuery("SELECT Id, Wert, Stellungnahme, Erstelldatum FROM bewertung "
								+ "WHERE Wert=" + wert + " ORDER BY Wert");
					
						
						//Fuer jeden Eintrag im Suchergebnis wird nun ein Bewertung-Objekt
			 			//erstellt
			 			  while (rs.next()){
			 				  Bewertung bt= new Bewertung();
			 				  bt.setId(rs.getInt("Id"));
			 				  bt.setWert(rs.getFloat("Wert"));
			 				  bt.setStellungnahme(rs.getString("Stellungnahme"));
			 				  bt.setErstelldatum(rs.getDate("Erstelldatum"));
			 				  
			 				  //Hinzufuegen des neuen Objekts zum Ergebnisvektor
			 				  result.addElement(bt);
			 			  }
			 		   }
			 		 catch (SQLException e7){
			 			 e7.printStackTrace();
			 		 }
			 		 
			 		 //Ergebnisvektor zurueckgeben
			 		 return result;
				}	  
					
				/**
				 * Das Erhalten der Bewertung von einer Person.
				 * @param p
				 * @return
				 */
				
	public Vector<Person> getByPerson(Person p){
		 return null; 
					
					
					}
					
					
					
					
				}