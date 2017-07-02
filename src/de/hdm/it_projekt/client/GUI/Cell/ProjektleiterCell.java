/** 
 * 
 * */ 
package de.hdm.it_projekt.client.GUI.Cell;



import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.hdm.it_projekt.shared.bo.Person;


public class ProjektleiterCell extends AbstractCell<Person>{

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Person value, SafeHtmlBuilder sb) {

		if(value == null)
			return;
		
		sb.appendHtmlConstant("<link type='text/css' rel='stylesheet' href='style.css'>");
		sb.appendHtmlConstant("<div class='Projektleiter-Cell'>");  //Einbinden der CSS Klasse
		sb.appendEscaped("Projektleiter: " + value.getName() + ", " + value.getVorname());
		sb.appendHtmlConstant("</div class='Projektleiter-Cell'>");  //Einbinden der CSS Klasse
		
	}

}
