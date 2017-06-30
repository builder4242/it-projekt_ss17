package de.hdm.it_projekt.client.GUI_Report;

import com.google.gwt.user.client.ui.*;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.shared.ReportGeneratorAsync;

/**
 * Diese Klasse ist die Basisklasse aller Showcases. Jeder Showcase ist ein
 * VerticalPanel und lässt sich somit unter GWT entsprechend anordnen.
 * 
 * @author thies
 * @version 1.0
 * 
 */
public abstract class Showcase extends VerticalPanel {

	ReportGeneratorAsync rp = ClientsideSettings.getReportGenerator();
	
	/**
	 * Jedes GWT Widget muss diese Methode implementieren. Sie gibt an, sas
	 * geschehen soll, wenn eine Widget-Instanz zur Anzeige gebracht wird.
	 */
	@Override
	public void onLoad() {
		/*
		 * Bevor wir unsere eigene Formatierung veranslassen, überlassen wir es
		 * der Superklasse eine Initialisierung vorzunehmen.
		 */
		super.onLoad();

		/*
		 * Wenn alles vorbereitet ist, stoßen wir die run()-Methode an. Auch
		 * run() ist als abstrakte Methode bzw. als Einschubmethode realisiert.
		 * Auch hier ist es Aufgabe der Subklassen, für deren Implementierung zu
		 * sorgen.
		 */
		this.run();
	}


	/**
	 * Mit Hilfe dieser Methode erstellen wir aus einem Strinng ein mittels CSS
	 * formatierbares HTML-Element, das an das Ende der bisherigen Ausgabe
	 * dieses Showcase angehängt wird. Unter CSS lässt sich das Ergebnis über
	 * <code>.bankproject-simpletext</code> referenzieren bzw. formatieren.
	 * 
	 * @param text
	 *            der String, den wir als HTML an die bisherige Showcase-Ausgabe
	 *            anhängen wollen.
	 */
	protected void append(String text) {
		HTML content = new HTML(text);
		this.add(content);
	}

	/**
	 * Abstrakte Einschubmethode, die in den Subklassen zu realisieren ist.
	 */
	protected abstract void run();
}
