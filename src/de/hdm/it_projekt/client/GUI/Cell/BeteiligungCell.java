/** Die Klasse Beteiligung Cell dient der Erstellung einer CellList 
 * in der Beteiligungen angeziegt werden. 
 */
package de.hdm.it_projekt.client.GUI.Cell;



import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.dom.builder.shared.SpanBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Beteiligung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;

public class BeteiligungCell extends AbstractCell<Beteiligung> {

	ProjektAdministrationAsync pa = ClientsideSettings.getProjektAdministration();

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Beteiligung value, SafeHtmlBuilder sb) {

		if (value == null)
			return;


		sb.appendHtmlConstant("<div class='Eigenschaft-Cell'>"); // Test CSS Klasse
		sb.appendEscaped(Integer.toString(context.getIndex()+1) + ". Beteiligung");
		sb.appendHtmlConstant("</div>"); // Test CSS Klasse
	}


}
