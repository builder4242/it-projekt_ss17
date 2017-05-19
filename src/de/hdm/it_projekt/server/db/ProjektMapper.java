package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.it_projekt.shared.bo.Projekt;

/**
 * Mapper-Klasse, die <code>Projekt</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 * 
 *
 * Anlehnung an @author Thies
 * @author ElifY
 */
	public class ProjektMapper {

  /**
   * Die Klasse ProjektMapper wird nur einmal instantiiert. Man spricht hierbei
   * von einem sogenannten <b>Singleton</b>.
   * <p>
   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
   * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
   * einzige Instanz dieser Klasse.
   * 
   * @see projektMapper()
   */
	private static ProjektMapper projektMapper = null;

  /**
   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
   * neue Instanzen dieser Klasse zu erzeugen.
   */
  protected ProjektMapper() {
  }

  /**
   * Diese statische Methode kann aufgrufen werden durch
   * <code>ProjektMapper.projektMapper()</code>. Sie stellt die
   * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
   * Instanz von <code>ProjektMapper</code> existiert.
   * <p>
   * 
   * <b>Fazit:</b> ProjektMapper sollte nicht mittels <code>new</code>
   * instantiiert werden, sondern stets durch Aufruf dieser statischen Methode.
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
	public Projekt insert(Projekt pr){
		
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		  
		try {
			
				// Leeres SQL-Statement (JDBC) anlegen
				Statement stmt = con.createStatement();
				
				// Momentan hoechsten Primaerschluesselwert pruefen
				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM projekte ");

				if (rs.next()) {
	
					/*
					 * pr erhaelt den bisher maximalen, nun um 1
					 * inkrementierten Primaerschluessel.
					 */	
					
					pr.setId(rs.getInt("maxid") + 1);

					stmt = con.createStatement();

			        // Jetzt erst erfolgt die tatsächliche Einfügeoperation.
			        stmt.executeUpdate("INSERT INTO projekte (name, startDatum, endDatum, beschreibung) "
			            + "VALUES (" + pr.getName() + ",'" + pr.getStartDatum() + "','" + pr.getEndDatum() + "','" + pr.getBeschreibung() + "')");
			      }
			    }
			    catch (SQLException e) {
			      e.printStackTrace();
			    }
			
				
			//Rückgabe, des evtl. korrigierten Projekts.
			    return pr;
			  }

		
		/**	
		 * Wiederholtes Schreiben eines Projekt-Objekts in die Datenbank.
		 * 
		 * @param pr
		 * 		das Objekt, das in die DB geschrieben werden soll
		 * @return das als Parameter uebergebene Objekt	
		 */

		 public Projekt update(Projekt pr){
			 
			 //DB-Verbindung holen
			 Connection con= DBConnection.connection();
			 
			 try{
				 
				 // Leeres SQL-Statement(JDBC) anlegen
				 Statement stmt = con.createStatement();
				 
				 stmt.executeUpdate("UPDATE projekte " + "SET name=\""
			     + pr.getName() + "\", " + "startDatum=\"" + pr.getStartDatum() 
			     + "\", " + "endDatum=\"" + pr.getEndDatum() 
			     + "\", " + "beschreibung=\"" + pr.getBeschreibung() + "\" "
			     + "WHERE id=" + pr.getID());
			 
		 }
		 catch (SQLException e2){
			 e2.printStackTrace();
		 }
		 
		//Rueckgabe des evtl. korrigierten Projekts
		return pr;
		
		 }
	
		 
		 /**
			 * Loeschen der Daten eines <code>Projekt</code>-Objekts aus der Datenbank.
			 * 
			 * @param projekt
			 *            das aus der DB zu loeschende Objekt
			 */
		 
		 
		 public void delete (Projekt pr){
			 
			 //DB-Verbindung herstellen
			 Connection con = DBConnection.connection();
			 
			 try{
				 
				 //Leeres SQL-Statement (JDBC) anlegen
				 Statement stmt = con.createStatement();
				 
				 stmt.executeUpdate("DELETE FROM projekte" + "WHERE id=" + pr.getId());
			 }
			 catch (SQLException e3){
				 e3.printStackTrace();
			 }
		 }
		 
		 /**
			 * Auslesen aller Projekte.
			 * 
			 * @return Ein Vektor mit Projekt-Objekten, die saemtliche
			 *         Projekte repraesentieren. Bei evtl. Exceptions wird eine
			 *         partiell gefuellter oder ggf. auch leerer Vektor zurueckgeliefert.
			 */
		 	
		 	 public Vector<Projekt> findAll(){
		 		 
		 		 //DB-Verbindung herstellen
		 		 Connection con = DBConnection.connection();
		 		 
		 		 //Ergebnisvektor vorbereiten
		 		 Vector<Projekt> result = new Vector<Projekt>();
		 		 
		 		 try{
		 			
		 			 //Leeres SQL-Statement (JDBC) anlegen
		 			 Statement stmt = con.createStatement();
		 			 
		 			 ResultSet rs = stmt.executeQuery("SELECT id, name, startDatum, endDatum, beschreibung "
		 				+ "FROM projekte " + " ORDER BY name");
		 			 
		 			 //Fuer jeden Eintrag im Suchergebnis wird nun ein
		 			 // Projekt-Objekt erstellt.
		 		 
		 			 while (rs.next()) {
		 				 Projekt pr = new Projekt();
		 				 pr.setId(rs.getInt("id"));
		 				 pr.setName(rs.getString("name"));
		 				 pr.setStartDatum(rs.getDate("startDatum"));
		 				 pr.setEndDatum(rs.getDate("endDatum"));
		 				 pr.setBeschreibung(rs.getString("beschreibung"));
		 			 
		 			// Hinzufügen des neuen Objekts zum Ergebnisvektor
		 		       result.addElement(pr);
		 		      		}
		 		 		}
		 		    catch (SQLException e4) {
		 		    e4.printStackTrace();
		 		    		}	

		 		   	// Ergebnisvektor zurückgeben
		 		   	return result;
		 	 		}
		 	 
		 	 /**
		 	  * Suchen eines Projekts mit vorgegebener ID. Da diese eindeutig ist,
		 	  * wird genau ein Objekt zurueckgegeben.
		 	  * 
		 	  * @param prId
		 	  * 	Primaerschluesselattribut in DB
		 	  * @return Projekt-Objekt, das dem uebergebenen Schluessel entspricht,
		 	  * null bei nicht vorhandenem DB-Tupel.
		 	  */
		 	 
		 	 public Vector<Projekt> findById(int prId){
		 		 
		 		 //DB-Verbindung herstellen
		 		 Connection con = DBConnection.connection();
		 		 
		 		 try{
		 			 
		 		  //Leeres SQL-Statement (JDBC) anlegen
		 		  Statement stmt = con.createStatement();
		 			 
		 		 //Statement ausfuellen und als Query an die DB schicken
		 		 ResultSet rs= stmt.executeQuery("SELECT * FROM projekte " + "WHERE pr=" 
		 		 + prId + " ORDER BY id");
		 		 
		 		 /*
		 		  * Da id der Primaerschluessel ist, kann maximal nur ein Tupel
		 		  * zurueckgegeben werden.
		 		  * Pruefung, ob ein Ergebnis vorliegt.
		 		  */
		
		 			 if (rs.next()){
		 				 //Umwandlung des Ergebnis-Tupel in ein Objekt und
		 				 //Ausgabe des Ergebnis-Objekts.
		 				 return Element(rs);
		 			 }
		 		 } 
		 		 	catch (SQLException e4){
		 		 		e4.printStackTrace();
		 		 		return null;
		 		 	}
		 		 
		 		 return null;
		 	 	}
		 	 
			private Vector<Projekt> Element(ResultSet rs) {
				return null;
			}

			/**
		 	 * Diese Methode ermoeglicht eine Ausgabe ueber Nutzer in der DB
		 	 * anhand deren Namen.
		 	 * 
		 	 * @param name
		 	 * @return result
		 	 */
		 	 
		 	 public Vector <Projekt> findByName(String name){
		 		 Connection con = DBConnection.connection(); 
		 		 Vector<Projekt> result= new Vector<Projekt>();
		 		 
		 		 try{
		 		      Statement stmt = con.createStatement();
		 		      
				 	  ResultSet rs= stmt.executeQuery("SELECT id, name" + "FROM projekte " + "WHERE name LIKE=" 
				 		 + name + " ORDER BY name");
		 		  
				 		 //Fuer jeden Eintrag im Suchergebnis wird nun ein 
				 	     //Projekt-Objekt erstellt.
				 		 while (rs.next()){
				 			 Projekt pr = new Projekt();
				 			 pr.setId(rs.getInt("id"));
				 			 pr.setName(rs.getString("name"));
				 			 
				 			//Hinzufuegen des neuen Objekts zum Ergebnisvektor
				 			 result.addElement(pr);
				 		 } 
				 	}
		 		 	catch (SQLException e5){
		 		 		e5.printStackTrace();
		 		 	}
				 			 
		 		 //Ergebnisvektor zurueckgeben
		 		 return result;
				 		 }
				 		 	 		 
	} 