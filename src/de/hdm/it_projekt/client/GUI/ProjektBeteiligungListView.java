/**
 * 
 */
package de.hdm.it_projekt.client.GUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.hdm.it_projekt.client.GUI.Cell.BeteiligungCell;
import de.hdm.it_projekt.shared.bo.*;

public class ProjektBeteiligungListView extends Showcase {

	private BeteiligungForm beteiligungForm = null;

	private Projekt selectedProjekt = null;
	private Beteiligung selectedBeteiligung = null;
	
	private ListDataProvider<Beteiligung> beteiligungDataProvider = null;

	private CellList<Beteiligung> btCl = null;

	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject> {
		@Override
		public String getKey(BusinessObject bo) {
			if (bo == null)
				return null;
			if (bo instanceof Beteiligung)
				return "BT" + Integer.toString(bo.getId());
			else
				return null;
		}
	};

	private BusinessObjectKeyProvider boKeyProvider = null;
	private SingleSelectionModel<BusinessObject> selectionModel = null;

	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {

			BusinessObject selection = selectionModel.getSelectedObject();

			if (selection instanceof Beteiligung)
				setSelectedBeteiligung((Beteiligung) selection);
		}
	}

	public ProjektBeteiligungListView(Projekt pr) {

		selectedProjekt = pr;
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		
		btCl = new CellList<Beteiligung>(new BeteiligungCell());
		btCl.setSelectionModel(selectionModel);
		btCl.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		beteiligungDataProvider = new ListDataProvider<Beteiligung>();
		beteiligungDataProvider.addDataDisplay(btCl);

		pa.getBeteiligungenFor(selectedProjekt, new AsyncCallback<Vector<Beteiligung>>() {
			
			@Override
			public void onSuccess(Vector<Beteiligung> result) {				
				for(Beteiligung bt : result) {
					beteiligungDataProvider.getList().add(bt);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	CellList<Beteiligung> getBeteiligungenCellList() {
		return btCl;
	}

	void setBeteiligungForm(BeteiligungForm btF){
		beteiligungForm = btF;
	}
	Projekt getSelectedProjekt() {
		return selectedProjekt;
	}
	
	void setSelectedBeteiligung(Beteiligung bt) {
		selectedBeteiligung = bt;
		beteiligungForm.setSelected(bt);
	}
	
	Beteiligung getSelBeteiligung() {
		return selectedBeteiligung;
	}

	void updateBeteiligung(Beteiligung bt) {
		List<Beteiligung> beteiligungList = beteiligungDataProvider.getList();
		beteiligungList.set(beteiligungList.indexOf(bt), bt);
		selectionModel.setSelected(bt, true);
	}
	
	void removeBeteiligung(Beteiligung bt) {
		if(!beteiligungDataProvider.getList().contains(bt))
			beteiligungDataProvider.getList().remove(bt);		
	}
}
