package de.hdm.it_projekt.client.GUI.Cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.it_projekt.shared.bo.Ausschreibung;


public class AusschreibungCell extends AbstractCell<Ausschreibung>{

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Ausschreibung value, SafeHtmlBuilder sb) {

		if(value == null)
			return;
		
		
		sb.appendHtmlConstant("<div class='xx-Cell'>");  //Test CSS Klasse
		sb.appendEscaped("Ausschreibung: " + value.getBezeichnung());
		sb.appendHtmlConstant("</div class='xx-Cell'>");  //Test CSS Klasse
		
	}

}
