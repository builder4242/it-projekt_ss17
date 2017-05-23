/**
 * 
 */
package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Beteiligung;
import de.hdm.it_projekt.shared.bo.Projekt;

/**
 * Mapper-Klasse, die <code>Beteiligung</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfuegung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * geloescht werden koennen. Das Mapping ist bidirektional. D.h., Objekte koennen
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * <p>
 * 
 * Anlehnung an @author Thies
 * @author ElifY
 */
public class BeteiligungMapper {

  /**
   * Die Klasse BeteiligungMapper wird nur einmal instantiiert. Man spricht hierbei
   * von einem sogenannten <b>Singleton</b>.
   * <p>
   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fuer
   * saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
   * einzige Instanz dieser Klasse.
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
   * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine einzige
   * Instanz von <code>BeteiligungMapper</code> existiert.
   * <p>
   * 
   * <b>Fazit:</b> BeteiligungMapper sollte nicht mittels <code>new</code>
   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
   * 
   * @return DAS <code>BeteiligungMapper</code>-Objekt.
   */
  public static BeteiligungMapper beteiligungMapper() {
    if (beteiligungMapper == null) {
      beteiligungMapper = new BeteiligungMapper();
    }

    return beteiligungMapper;
  }

public Beteiligung insert(Beteiligung bet){
	
	//DB-Verbindung herstellen
	Connection con = DBConnection.connection();

	try {
		    //Leeres SQL-Statement (JDBC) anlegen
	    	Statement stmt = con.createStatement();

	       //Momentan hoechsten Primaerschluesselwert pruefen
	       ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid "
	          + "FROM transactions ");

	      // Wenn wir etwas zurueckerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	        
	    	/*
	         * bet erhaelt den bisher maximalen, nun um 1 inkrementierten
	         * Primaerschluessel.
	         */
	        bet.setId(rs.getInt("maxid") + 1);

	        stmt = con.createStatement();

	        // Jetzt erst erfolgt die tatsaechliche Einfuegeoperation
	        stmt.executeUpdate("INSERT INTO beteiligungs (id, personentage, enddatum, startdatum) "
	            + "VALUES (" + Beteiligung.getId() + ","
	            + bet.getPersonentage() + ","
	            + bet.getEnddatum() + "," + bet.getStartdatum() + ")");
	      }
	    }
	    catch (SQLException e1) {
	      e1.printStackTrace();
	    }
	
	    //Rueckgabe, der evtl. korrigierten Beteiligung.
	    return bet;
	  }

/**
 * Wiederholtes Schreiben eines Objekts in die Datenbank.
 * 
 * @param bet das Objekt, das in die DB geschrieben werden soll
 * @return das als Parameter uebergebene Objekt
 */

	 public Beteiligung update(Beteiligung bet){
		 
		 //DB-Verbindung herstellen
		 Connection con = DBConnection.connection();
		 
		 try{
			 //Leeres SQL-Statement (JDBC) anlegen
			 Statement stmt = con.createStatement();
			 
		     // Jetzt erst erfolgt die tatsaechliche Einfuegeoperation.
			 stmt.executeUpdate("UPDATE beteiligungs " + "SET peronentage=\""
		     + bet.getPersonentage() + "\"," + "enddatum=\"" + bet.getEnddatum()
		     + "\", " + "startdatum=\"" + bet.getStartdatum() + "\" "
		     + "WHERE id=" + Beteiligung.getId());
		 
		 }
		 catch (SQLException e2){
			 e2.printStackTrace();
		 }
		 
		 // Um Analogie zu insert(Bewertung bet) zu wahren, geben wir bt zurueck
	     return bet;
	  }
	 
	 
	 /**
	  * Loeschen der Daten eines <code>Beteiligung</code>-Objekts aus der DB
	  * @param bet
	  * 		das aus der DB zu loeschende Objekt
	 * @return 
	  */
	 
	 public void delete (Beteiligung bet){
			 
			 //DB-Verbindung herstellen
			 Connection con = DBConnection.connection();
			 
			 try{
				 
				 //Leeres SQL-Statement (JDBC) anlegen
				 Statement stmt = con.createStatement();
				 
				 stmt.executeUpdate("DELETE FROM beteiligungs" + "WHERE id=" + Beteiligung.getId());
			 }
			 catch (SQLException e3){
				 e3.printStackTrace();
			 }
		 }
		 
		 
	 /**
	  * Auslesen aller Beteiligungen
	  * @return Ein Vektor mit Beteiligung-Objekten, die saemtliche
	  *         Beteiligungen repraesentieren. Bei evtl. Exceptions wird eine
	  *         partiell gefuellter oder ggf. auch leerer Vektor zurueckgeliefert
	  */
		 
	 public Vector<Beteiligung> findAll(){
			 
			//DB-Verbindung herstellen
	 		 Connection con = DBConnection.connection();
	 		 
	 		 //Ergebnisvektor vorbereiten
	 		 Vector<Beteiligung> result = new Vector<Beteiligung>();
	 		 
	 		 try{
	 			
	 			 //Leeres SQL-Statement (JDBC) anlegen
	 			 Statement stmt = con.createStatement();
	 			 
	 			 ResultSet rs = stmt.executeQuery("SELECT id, personentage, enddatum, startdatum "
	 				+ "FROM beteiligungs " + " ORDER BY id");
	 			 
	 			 
	 			 //Fuer jeden Eintrag im Suchergebnis wird nun ein
	 			 //Beteiligung-Objekt erstellt.
	 		 
	 			 while (rs.next()) {
	 				 Beteiligung bet = new Beteiligung();
	 				 bet.setId(rs.getInt("id"));
	 				 bet.setPersonentage(rs.getInt("personentage"));
	 				 bet.setEnddatum(rs.getDate("enddatum"));
	 				 bet.setEnddatum(rs.getDate("startdatum"));
	 				 
	 			 
	 			 //Hinzufuegen des neuen Objekts zum Ergebnisvektor
	 		       result.addElement(bet);
	 		      		}
	 		 		}
	 		    catch (SQLException e4) {
	 		    e4.printStackTrace();
	 		    		}	

	 		   	// Ergebnisvektor zurueckgeben
	 		   	return result;
	 	 		}
	 
	 /**
	   * Suchen einer Beteiligung mit vorgegebener Nummer. Da diese eindeutig ist,
	   * wird genau ein Objekt zurueckgegeben.
	   * 
	   * @param id Primaerschluesselattribut (->DB)
	   * @return Bewerber-Objekt, das dem uebergebenen Schluessel entspricht, null bei
	   *         nicht vorhandenem DB-Tupel.
	   */
	 
	 public Vector<Beteiligung> findByID (int id){
	 
		 //DB-Verbindung herstellen
		 Connection con = DBConnection.connection();
		 
		 try {
		      // Leeres SQL-Statement (JDBC) anlegen
		      Statement stmt = con.createStatement();

		      //Statement ausfuellen und als Query an die DB schicken
		      ResultSet rs = stmt.executeQuery("SELECT id, personentage, enddatum, startdatum FROM beteiligungs "
		      + "WHERE id=" + id + "ORDER BY personentage");
	 
		      /*
		       * Da id Primäerschluessel ist, kann max. nur ein Tupel zurueckgegeben
		       * werden. Pruefe, ob ein Ergebnis vorliegt
		       */
		      
		      if (rs.next()){
		    	
		    	  // Ergebnis-Tupel in Objekt umwandeln
		    	  Beteiligung bet = new Beteiligung();
		    	  bet.setId(rs.getInt("id"));
		          bet.setPersonentage(rs.getInt("personentage"));
		          bet.setEnddatum(rs.getDate("enddatum"));
		          bet.setStartdatum(rs.getDate("startdatum"));
			 
		          return bet;
		      }
		    }
		    catch (SQLException e) {
		      e.printStackTrace();
		      return null;
		    }

		    return null;
		  
	 }
		      
	 	
	 /**
	  * Suchen einer Beteiligung anhand der Personentage.
	  * @param personentage
	  * @return result
	  */
	 	
	 	public Vector <Beteiligung> findByPersonentage(int personentage){
	 		//DB-Verbindung herstellen
	 		Connection con = DBConnection.connection();
		
	 		//Ergebnisvektor vorbereiten
	 		Vector<Beteiligung> result = new Vector<Beteiligung>();
		
	 		try{
	 			//Leeres SQL-Statement (JDBC) anlegen
	 			Statement stmt = con.createStatement();
			
	 			//Statement ausfuellen und als Query an die Datenbank schicken
	 			ResultSet rs = stmt.executeQuery("SELECT id, personentage, enddatum, startdatum FROM beteiligungs "
					+ "WHERE personentage=" + personentage + " ORDER BY personentage"); 
				
			
	 			//Fuer jeden Eintrag im Suchergebnis wird nun ein Beteiligung-Objekt erstellt
	 			while (rs.next()){
				Beteiligung bet = new Beteiligung();
				bet.setId(rs.getInt("id"));
				bet.setPersonentage(rs.getInt("personentage"));
				bet.setEnddatum(rs.getDate("enddatum"));
				bet.setStartdatum(rs.getDate("startdatum"));
				
				//Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(bet);
	 			}
	 		}
	 			catch (SQLException e5) {
	 				e5.printStackTrace();
	 			}
		
	 		//Ergebnisvektor zurueckgeben
	 		return result;
	 	}
	 

	 	/**
	 	 * Suchen einer Beteiligung anhand des Startdatums.
	 	 * @param <date>
	 	 * @param startdatum
	 	 * @return result
	 	 */
	 		
	 	public <date> Vector <Beteiligung> findByStartdatum (date startdatum){
	 		
	 		//DB-Verbindung herstellen
	 		Connection con = DBConnection.connection();
		
	 		//Ergebnisvektor vorbereiten
	 		Vector<Beteiligung> result = new Vector<Beteiligung>();
		
	 		try{
	 			//Leeres SQL-Statement (JDBC) anlegen
	 			Statement stmt = con.createStatement();
			
	 			//Statement ausfuellen und als Query an die Datenbank schicken
	 			ResultSet rs = stmt.executeQuery("SELECT id, personentage, enddatum, startdatum FROM beteiligungs "
					+ "WHERE startdatum=" + startdatum + " ORDER BY startdatum"); 
				
			
	 			//Fuer jeden Eintrag im Suchergebnis wird nun ein Beteiligung-Objekt erstellt
	 			while (rs.next()){
				Beteiligung bet = new Beteiligung();
				bet.setId(rs.getInt("id"));
				bet.setPersonentage(rs.getInt("personentage"));
				bet.setEnddatum(rs.getDate("enddatum"));
				bet.setStartdatum(rs.getDate("startdatum"));
				
				//Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(bet);
	 			}
	 		}
	 			catch (SQLException e6) {
	 				e6.printStackTrace();
	 			}
		
	 		//Ergebnisvektor zurueckgeben
	 		return result;
	 	}
	 		
	 	/**
	 	 * Suchen einer Beteiligung anhand des Startdatums.
	 	 * @param <date>
	 	 * @param enddatum
	 	 * @return result
	 	 */
	 		
	 	public <date> Vector <Beteiligung> findByEnddatum (date enddatum){
	 		
	 		//DB-Verbindung herstellen
	 		Connection con = DBConnection.connection();
		
	 		//Ergebnisvektor vorbereiten
	 		Vector<Beteiligung> result = new Vector<Beteiligung>();
		
	 		try{
	 			//Leeres SQL-Statement (JDBC) anlegen
	 			Statement stmt = con.createStatement();
			
	 			//Statement ausfuellen und als Query an die Datenbank schicken
	 			ResultSet rs = stmt.executeQuery("SELECT id, personentage, enddatum, startdatum FROM beteiligungs "
					+ "WHERE enddatum=" + enddatum + " ORDER BY enddatum"); 
				
			
	 			//Fuer jeden Eintrag im Suchergebnis wird nun ein Beteiligung-Objekt erstellt
	 			while (rs.next()){
				Beteiligung bet = new Beteiligung();
				bet.setId(rs.getInt("id"));
				bet.setPersonentage(rs.getInt("personentage"));
				bet.setEnddatum(rs.getDate("enddatum"));
				bet.setStartdatum(rs.getDate("startdatum"));
				
				//Hinzufuegen des neuen Objekts zum Ergebnisvektor
				result.addElement(bet);
	 			}
	 		}
	 			catch (SQLException e7) {
	 				e7.printStackTrace();
	 			}
		
	 		//Ergebnisvektor zurueckgeben
	 		return result;
	 	}
	 	
	 	/**
	 	 * Auslesen des zugehoerigen <code>Projekt</code>-Objekts zu einem gegebenen
	 	 * Projektmarktplatzes.
	 	 * 
	 	 * @param pm
	 	 * @return
	 	 */

	 	public Vector<Projekt> getByProjektmarktplatz(Projektmarktplatz pm){
	 		
	 	}

	 	
	 	/**
	 	 * Das Erhalten des Projektes von einem Projektleiter, der eine Person ist
	 	 * @param p
	 	 * @return
	 	 */
	 	public Vector<Projekt> getByProjektleiter(Person p){
	 		
	 	}

}