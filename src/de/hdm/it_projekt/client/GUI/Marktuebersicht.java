/** Die Klasse Marktplatzuebersicht dient der Darstellung und Auswahl der Projektmarktplätze
 * auf der Seite "Projektmarktplätze" der GUI. Die Marktplätze werden in einer CellList angezeigt,
 * die Überschrift ist als Label realisiert. Die Optik wird über das einbinden von CSS angepasst. */ 
package de.hdm.it_projekt.client.GUI;



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
import com.google.gwt.user.client.ui.FormPanel;
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
	
	HorizontalPanel hP = new HorizontalPanel();
	VerticalPanel vP = new VerticalPanel();
	VerticalPanel formPanel = new VerticalPanel();
	
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
								
				if(MyProjekt.cpm.getAdminId() == MyProjekt.loginInfo.getCurrentUser().getId()) {
					
					Button deleteButton = new Button("Löschen");
					deleteButton.addStyleName("myprojekt-formbutton");
					deleteButton.addClickHandler(new DeleteClickHandler(MyProjekt.cpm));
					headerInfo.add(deleteButton);
					
					Button updateButton = new Button("Ändern");
					updateButton.addStyleName("myprojekt-formbutton");
					updateButton.addClickHandler(new UpdateClickHandler(MyProjekt.cpm));
					headerInfo.add(updateButton);

				}
					
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
		
		hP.add(vP);
		hP.add(formPanel);
		
		Button newPm = new Button("Marktplatz anlegen");
		newPm.setStyleName("myprojekt-formbutton");
		newPm.addClickHandler(new NewPmClickHandler());
		vP.add(memberPmCl);
		vP.add(newPm);
		ausgabe.setText("Bitte wählen Sie einen Marktplatz aus:");
		ausgabe.setStyleName("h2"); 
		this.add(ausgabe);

		this.add(hP);		

	}
	
	public void showMarktplatzForm(ProjektMarktplatz pm) {
		ProjektMarktplatzForm pmf = new ProjektMarktplatzForm();
		pmf.setSelected(pm);
		formPanel.clear();
		formPanel.add(pmf);
	}
	
	private class NewPmClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			ProjektMarktplatzForm pmf = new ProjektMarktplatzForm();
			pmf.setSelected(null);
			formPanel.clear();
			formPanel.add(pmf);			
		}		
	}
	
	private class DeleteClickHandler implements ClickHandler {

		ProjektMarktplatz pm = null;
		public DeleteClickHandler(ProjektMarktplatz pm) {
			this.pm = pm;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(pm != null) {
				pa.delete(pm, new DeleteCallback());
			}
		}		
	}
	
	private class DeleteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(Void result) {

			RootPanel.get("pminfo").clear();
			RootPanel.get("content").clear();
			RootPanel.get("content").add(new Marktuebersicht());
			
		}		
	}
	
	private class UpdateClickHandler implements ClickHandler {

		ProjektMarktplatz pm = null;
		
		public UpdateClickHandler(ProjektMarktplatz pm) {
			this.pm = pm;
		}
		@Override
		public void onClick(ClickEvent event) {
			Marktuebersicht mu = new Marktuebersicht();
			mu.showMarktplatzForm(pm);
			RootPanel.get("content").clear();
			RootPanel.get("content").add(mu);
		}
		
	}
}

