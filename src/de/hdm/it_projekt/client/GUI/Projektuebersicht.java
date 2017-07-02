/** Die Klasse Projektuebersicht dient dem Ausgeben eines Infotextes, nachdem ein Marktplatz
 * ausgewählt wurde. Der Name des Markplatzes wird über die Methode getBezeichnung aufgerufen 
 * und in den Text der als HTML realisiert ist mit eingefügt. Im HTML Element kann der Text 
 * direkt über HTML Tags angepasst werden. Zuätzlich wird eine CSS Klasse eingebunden um die 
 * Optik anzupassen. */ 
package de.hdm.it_projekt.client.GUI;

import com.google.gwt.user.client.ui.HTML;


public class Projektuebersicht extends Showcase {

	public Projektuebersicht() {
		
		HTML pmHTML = new HTML(MyProjekt.cpm.getBezeichnung());	
		pmHTML.setStyleName("myprojekt-infoleistelabel");
		
		HTML einfuehrungHTML = new HTML("</br><p align='justify'>Herzlich willkommen im Projektmarktplatz <b>" + pmHTML +
				 "</b> </br> Wenn Sie ein neues Projekt oder eine Ausschreibung anlegen wollen, "
				 + "gehen Sie zu <b>Meine Ausschreibungen</b>.</br> Wenn Sie sich auf eine Ausschreibung"
				 + " bewerben wollen, gehen Sie bitte zu <b>Meine Bewerbungen</b>."

				 + "</br>Möchten Sie Ihr Partnerprofil verwalten, gehen Sie einfach zu <b>Mein Profil "
				 + "verwalten</b>. </br>Um Reports zu generieren, gehen Sie zu <b>Report Generator</b>"
				 + "</br> </br> Wir freuen uns, dass Sie sich für getLinked entschieden haben.</p>"); 

		einfuehrungHTML.setStyleName("myprojekt-infoleistelabel");
		

		
		this.add(einfuehrungHTML);
		
	}
	
}
