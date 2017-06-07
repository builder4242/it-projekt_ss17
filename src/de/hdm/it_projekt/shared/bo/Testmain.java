package de.hdm.it_projekt.shared.bo;

import de.hdm.it_projekt.server.db.ProjektMarktplatzMapper;
import de.hdm.it_projekt.server.db.PersonMapper;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import java.util.Date;

public class Testmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ProjektMarktplatz p1 = new ProjektMarktplatz();
		

	
		p1.setBezeichnung("ATI");
		
		System.out.println(p1.getId() + " " + p1.getBezeichnung());
		
		ProjektMarktplatzMapper.insert(p1);
		
		/*
		p1.setBezeichnung("Volvo");
		ProjektMarktplatzMapper.update(p1);
		ProjektMarktplatzMapper.delete(p1);
		*/
		
		System.out.println(ProjektMarktplatzMapper.findAll());
		System.out.println(ProjektMarktplatzMapper.findById(8));
		System.out.println(ProjektMarktplatzMapper.findByBezeichnung("Daimler"));
	}

}
