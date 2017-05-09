package de.hdm.it_projekt.shared.report;

import java.io.Serializable;

/**
 * Reports benötigen die MÖglichkeit, Text strukturiert abspeichern zu können.
 * Dieser Text kann später durch <code>ReportWriter</code> in verschiedene
 * Zielformate konvertiert werden. Die Verwendung der Klasse <code>String</code>
 * reicht hier nicht aus, da allein das Hinzufügen eines Zeilenumbruchs zur
 * Markierung eines Absatzendes Kenntnisse über das Zielformat voraussetzt. Im
 * Falle einer rein textuellen Darstellung würde hierzu ein doppeltes "
 * <code>\n</code>" genügen. Bei dem Zielformat HTML müsste jedoch der gesamte
 * Absatz in entsprechendes Markup eingefügt werden.
 * <p>
 * 
 * <code>Paragraph</code> ist <code>Serializable</code>, so das Objekte dieser
 * Klasse durch das Netzwerk übertragbar sind.
 * 
 * @see Report
 * @author Thies
 */
public abstract class Paragraph implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

}
