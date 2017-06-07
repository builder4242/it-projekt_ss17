package de.hdm.it_projekt.shared.bo;

import de.hdm.it_projekt.server.db.ProjektMarktplatzMapper;  
import java.util.Date;

public class Testmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ProjektMarktplatz p1 = new ProjektMarktplatz();
		
		p1.setBezeichnung("Burger King");
		
		ProjektMarktplatzMapper pmMapper = ProjektMarktplatzMapper.projektMarktplatzMapper();
		
		System.out.println(p1.getId() + " " + p1.getBezeichnung());
		
		
		pmMapper.insert(p1);
		
		/*
		p1.setBezeichnung("Volvo");
		pmMapper.update(p1);
		pmMapper.delete(p1);
		*/
		
		System.out.println(pmMapper.findAll());
		System.out.println(pmMapper.findById(8));
		System.out.println(pmMapper.findByBezeichnung("Daimler"));
	}

}
