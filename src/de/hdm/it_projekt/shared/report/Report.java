package de.hdm.it_projekt.shared.report;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Basisklasse aller Reports. Reports sind als <code>Serializable</code>
 * deklariert, damit sie von dem Server an den Client gesendet werden können.
 * Der Zugriff auf Reports erfolgt also nach deren Bereitstellung lokal auf dem
 * Client.
 * </p>
 * <p>
 * Ein Report besitzt eine Reihe von Standardelementen. Sie werden mittels
 * Attributen modelliert und dort dokumentiert.
 * </p>
 * 
 * @see Report
 * @author Thies
 */
public abstract class Report implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Ein kleines Impressum, das eine Art Briefkopf darstellt. Jedes Unternehmen
   * einige Daten wie Firmenname, Adresse, Logo, etc. auf Geschäftsdokumenten
   * ab. Dies gilt auch für die hier realisierten Reports.
   */
  private Paragraph imprint = null;

  /**
   * Kopfdaten des Berichts.
   */
  private Paragraph headerData = null;

  /**
   * Jeder Bericht kann einen individuellen Titel besitzen.
   */
  private String title = "Report";

  /**
   * Datum der Erstellung des Berichts.
   */
  private Date created = new Date();

  /**
   * Auslesen des Impressums.
   * 
   * @return Text des Impressums
   */
  public Paragraph getImprint() {
    return this.imprint;
  }

  /**
   * Setzen des Impressums.
   * 
   * @param imprint Text des Impressums
   */
  public void setImprint(Paragraph imprint) {
    this.imprint = imprint;
  }

  /**
   * Auslesen der Kopfdaten.
   * 
   * @return Text der Kopfdaten.
   */
  public Paragraph getHeaderData() {
    return this.headerData;
  }

  /**
   * Setzen der Kopfdaten.
   * 
   * @param headerData Text der Kopfdaten.
   */
  public void setHeaderData(Paragraph headerData) {
    this.headerData = headerData;
  }

  /**
   * Auslesen des Berichtstitels.
   * 
   * @return Titeltext
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Setzen des Berichtstitels.
   * 
   * @param title Titeltext
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Auslesen des Erstellungsdatums.
   * 
   * @return Datum der Erstellung des Berichts
   */
  public Date getCreated() {
    return this.created;
  }

  /**
   * Setzen des Erstellungsdatums. <b>Hinweis:</b> Der Aufruf dieser Methoden
   * ist nicht unbedingt erforderlich, da jeder Report bei seiner Erstellung
   * automatisch den aktuellen Zeitpunkt festhält.
   * 
   * @param created Zeitpunkt der Erstellung
   */
  public void setCreated(Date created) {
    this.created = created;
  }

}
