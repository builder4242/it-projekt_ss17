package de.hdm.it_projekt.client.GUI;

import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.it_projekt.client.GUI.Cell.ProjektMarktplatzCell;
import de.hdm.it_projekt.shared.bo.Projekt;
import de.hdm.it_projekt.shared.bo.ProjektMarktplatz;

public class Marktuebersicht extends Showcase {

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

					pa.getAlleProjekteFor(pmSelectionModel.getSelectedObject(), new AsyncCallback<Vector<Projekt>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Vector<Projekt> result) {

						for(Projekt pr : result) {
							prVp.add(new Label(pr.toString()));
						}						
					}
				});				
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

		this.add(ausgabe);
		this.add(memberPmCl);
		this.add(prVp);

	}
}

