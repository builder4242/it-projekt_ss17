package de.hdm.it_projekt.server.db;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Mapper-Klasse, die <code>Projekt</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur VerfÃ¼gung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelÃ¶scht werden kÃ¶nnen. Das Mapping ist bidirektional. D.h., Objekte kÃ¶nnen
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
   * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal fÃ¼r
   * sÃ¤mtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
   * einzige Instanz dieser Klasse.
   * 
   * @see projektMapper()
   */
	private static ProjektMapper projektMapper = null;

  /**
   * GeschÃ¼tzter Konstruktor - verhindert die MÃ¶glichkeit, mit <code>new</code>
   * neue Instanzen dieser Klasse zu erzeugen.
   */
  protected ProjektMapper() {
  }

  /**
   * Diese statische Methode kann aufgrufen werden durch
   * <code>ProjektMapper.projektMapper()</code>. Sie stellt die
   * Singleton-Eigenschaft sicher, indem Sie dafÃ¼r sorgt, dass nur eine einzige
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
	 * Diese Methode ermÃ¶glicht es ein Projekt in der Datenbank anzulegen.
	 * 
	 * @param Projekt
	 * @return
	 */
	public Projekt insert(Projekt pr){
		
		// DB-Verbindung herstellen
		Connection con = DBConnection.connection();
		  
		try {
			
				// Leeres SQL-Statement (JDBC) anlegen
				Statement stmt = con.createStatement();
				
				// Momentan höchsten Primärschlüsselwert prüfen
				ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM projekte ");

				if (rs.next()) {
	
					/*
					 * pr erhält den bisher maximalen, nun um 1
					 * inkrementierten Primärschlüssel.
					 */	
					
					pr.setId(rs.getInt("maxid") + 1);

					stmt = con.createStatement();

			        // Jetzt erst erfolgt die tatsÃ¤chliche EinfÃ¼geoperation.
			        stmt.executeUpdate("INSERT INTO projekte (name, startDatum, endDatum, beschreibung) "
			            + "VALUES (" + pr.getName() + ",'" + pr.getstartDatum() + "','" + pr.getendDatum() + "','" + pr.beschreibung() + "')");
			      }
			    }
			    catch (SQLException e) {
			      e.printStackTrace();
			    }
			
				
			//RÃ¼ckgabe, des evtl. korrigierten Projekts.
			    return pr;
			  }

		
		/**	
		 * Wiederholtes Schreiben eines Projekt-Objekts in die Datenbank.
		 * 
		 * @param pr
		 * 		das Objekt, das in die DB geschrieben werden soll
		 * @return das als Parameter uebergebene Objekt	
		 */

		 public Vector<Projekt> update(Projekt pr){
			 
			 //DB-Verbindung holen
			 Connection con= DBConnection.connection();
			 
			 try{
				 
				 // Leeres SQL-Statement(JDBC) anlegen
				 Statement stmt = con.createStatement();
				 
				 stmt.executeUpdate("UPDATE projekte " + "SET name=\""
			     + pr.getname() + "\", " + "startDatum=\"" + pr.getstartDatum() 
			     + "\", " + "endDatum=\"" + pr.getendDatum() 
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
			 * Löschen der Daten eines <code>Projekt</code>-Objekts aus der Datenbank.
			 * 
			 * @param projekt
			 *            das aus der DB zu löschende Objekt
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
		}
