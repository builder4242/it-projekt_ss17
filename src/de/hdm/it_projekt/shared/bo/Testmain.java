package de.hdm.it_projekt.shared.bo;

import de.hdm.it_projekt.server.db.ProjektMarktplatzMapper;
import de.hdm.it_projekt.server.db.PersonMapper;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import java.util.Date;

public class Testmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ProjektMarktplatz p1 = new ProjektMarktplatz();
		
		p1.setBezeichnung("Daimler");
		p1.setId(7);
		
		System.out.println(p1.getId() + " " + p1.getBezeichnung());
		
		ProjektMarktplatzMapper.insert(p1);
		
	}

}
