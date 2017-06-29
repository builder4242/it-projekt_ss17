package de.hdm.it_projekt.client.GUI;

/** Die Klasse Marktplatzuebersicht dient der Darstellung und Auswahl der Projektmarktplätze
 * auf der Seite "Projektmarktplätze" der GUI. Die Marktplätze werden in einer CellList angezeigt,
 * die Überschrift ist als Label realisiert. Die Optik wird über das einbinden von CSS angepasst. */ 

import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.it_projekt.client.GUI.Cell.ProjektMarktplatzCell;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class Marktuebersicht extends Showcase {

	final HorizontalPanel headerInfo = new HorizontalPanel();
		
	final Label ausgabe = new Label();
	final VerticalPanel prVp= new VerticalPanel();
	
	final ProvidesKey<ProjektMarktplatz> KEY_PROVIDER = new ProvidesKey<ProjektMarktplatz>() {

		@Override
		public Integer getKey(ProjektMarktplatz item) {
			
			return item.getId();			
		}		
	};

	public Marktuebersicht() {


		Cell<ProjektMarktplatz> pmCell = new ProjektMarktplatzCell();
		
		final CellList<ProjektMarktplatz> memberPmCl = new CellList<ProjektMarktplatz>(pmCell, KEY_PROVIDER);
		
		final SingleSelectionModel<ProjektMarktplatz> pmSelectionModel = new SingleSelectionModel<ProjektMarktplatz>(KEY_PROVIDER);
		memberPmCl.setSelectionModel(pmSelectionModel);
		memberPmCl.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		pmSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {

				MyProjekt.cpm = pmSelectionModel.getSelectedObject();
								
				Label pmTextLabel = new Label("Sie befinden sich aktuell im Projektmarktplatz: ");
				pmTextLabel.setStyleName("myprojekt-infoleistelabel");
				headerInfo.add(pmTextLabel);
				
				Label pmLabel = new Label(MyProjekt.cpm.getBezeichnung());	
				pmLabel.setStyleName("myprojekt-infoleistelabelbold");
				headerInfo.add(pmLabel);
					
				RootPanel.get("pminfo").clear();
				RootPanel.get("pminfo").add(headerInfo);
				
				RootPanel.get("content").clear();
				RootPanel.get("content").add(new Projektuebersicht());
			}			
		});
		
		
		ListDataProvider<ProjektMarktplatz> pmDataProvider = new ListDataProvider<ProjektMarktplatz>();
		pmDataProvider.addDataDisplay(memberPmCl);

		final List<ProjektMarktplatz> pmL = pmDataProvider.getList();
		
		pa.getAlleProjektMarktplaetze(new AsyncCallback<Vector<ProjektMarktplatz>>() {

			@Override
			public void onFailure(Throwable caught) {

				Window.alert("Fehler beim Abruf der Projekt Marktplätze");
			}

			@Override
			public void onSuccess(Vector<ProjektMarktplatz> result) {
				
				for(ProjektMarktplatz pm : result) {
					pmL.add(pm);
				}

			}
		});
		
		
		ausgabe.setText("Bitte wählen Sie einen Marktplatz aus:");
		ausgabe.setStyleName("h2"); 

		this.add(ausgabe);
		this.add(memberPmCl);
		this.add(prVp);
		this.add(new NewProjektMarktplatzForm());

	}
}

