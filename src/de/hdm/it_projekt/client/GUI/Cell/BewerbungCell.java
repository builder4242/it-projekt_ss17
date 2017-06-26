package de.hdm.it_projekt.client.GUI.Cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;

import de.hdm.it_projekt.client.ClientsideSettings;
import de.hdm.it_projekt.client.GUI.ProjektTreeViewModel;
import de.hdm.it_projekt.shared.ProjektAdministrationAsync;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import de.hdm.it_projekt.shared.bo.Organisationseinheit;
import de.hdm.it_projekt.shared.bo.Person;

public class BewerbungCell extends AbstractCell<Bewerbung> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Bewerbung value, SafeHtmlBuilder sb) {

		if (value == null)
			return;


		sb.appendHtmlConstant("<div class='Partnerprofil-Cell'>"); // Test
		// CSS
		// Klasse
		sb.appendEscaped("Bewerber: " + value.getOrganisationseinheitId());

		sb.appendHtmlConstant("</div class='Partnerprofil-Cell'>"); // Test
		// CSS
		// Klasse

	}

}
