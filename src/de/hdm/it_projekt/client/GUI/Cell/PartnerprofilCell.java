package de.hdm.it_projekt.client.GUI.Cell;

/** */ 

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.it_projekt.shared.bo.Partnerprofil;


public class PartnerprofilCell extends AbstractCell<Partnerprofil>{

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Partnerprofil value, SafeHtmlBuilder sb) {

		if(value == null)
			return;
		
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yyyy");		
		
		sb.appendHtmlConstant("<div class='Partnerprofil-Cell'>");  //Test CSS Klasse
		sb.appendEscaped("Partnerprofil");
		sb.appendHtmlConstant("</div class='Partnerprofil-Cell'>");  //Test CSS Klasse
		
	}

}
