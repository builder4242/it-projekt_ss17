package de.hdm.it_projekt.client.GUI.Cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.it_projekt.shared.bo.Bewertung;


public class BewertungCell extends AbstractCell<Bewertung>{

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Bewertung value, SafeHtmlBuilder sb) {

		if(value == null)
			return;
		
		sb.appendHtmlConstant("<div class='Partnerprofil-Cell'>");  //Test CSS Klasse
		sb.appendEscaped("Bewertung");
		sb.appendHtmlConstant("</div class='Partnerprofil-Cell'>");  //Test CSS Klasse		
	}
}
