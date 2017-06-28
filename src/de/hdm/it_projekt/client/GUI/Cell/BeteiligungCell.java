package de.hdm.it_projekt.client.GUI.Cell;

import com.google.gwt.cell.client.AbstractCell;
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

		sb.appendHtmlConstant("<div class='Beteiligung-Cell'>"); // Test CSS Klasse
		sb.appendEscaped("Beteiligung: " + Integer.toString(context.getIndex()+1));
		sb.appendHtmlConstant("</div class='Beteiligung-Cell'>"); // Test CSS Klasse
	}

	String getNameFor(Beteiligung value) {
		
		final StringBuffer name = new StringBuffer();

		pa.getBeteiligterFor(value, new AsyncCallback<Organisationseinheit>() {

			@Override
			public void onSuccess(Organisationseinheit result) {
				if(result instanceof Person) {
					Person p = (Person) result;
					name.append(p.getName() + ", " + p.getVorname());
				} else {
					name.append(result.getName());
				}
			}

			@Override
			public void onFailure(Throwable caught) {

			}
		});

		return name.toString();
	}
}
