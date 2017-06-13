package de.hdm.it_projekt.client.GUI_in_dev.GUI_alt;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.it_projekt.shared.bo.Projekt;
import com.google.gwt.cell.client.AbstractCell;

public class AccountCell extends AbstractCell<Projekt>{
	/**
	 * Klasse zur Darstellung von ProjektObjekten. 
	 * 
	 */
	

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Projekt value, SafeHtmlBuilder sb) {
		// TODO Auto-generated method stub
		
		sb.appendHtmlConstant("<div>Projekt ");
		sb.appendEscaped(value.toString());
		sb.appendHtmlConstant("</div>");
	}

}
