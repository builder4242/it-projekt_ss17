package de.hdm.it_projekt.client.GUI.Cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.it_projekt.shared.bo.Partnerprofil;


public class PartnerprofilCell extends AbstractCell<Partnerprofil>{

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Partnerprofil value, SafeHtmlBuilder sb) {

		if(value == null)
			return;
		
		
		sb.appendHtmlConstant("<div class='xx-Cell'>Partnerprofil vom: ");  //Test CSS Klasse
		sb.appendEscaped(value.getErstelldatum().toString());
		sb.appendHtmlConstant("</div class='xx-Cell'>");  //Test CSS Klasse
		
	}

}
