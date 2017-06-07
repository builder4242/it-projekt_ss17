package de.hdm.it_projekt.shared.bo;

import de.hdm.it_projekt.server.db.*;  
import java.util.Date;

public class Testmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ProjektMarktplatz p1 = new ProjektMarktplatz();
		
		p1.setBezeichnung("Burger King");
		
		/*Mapper Definitionen*/
		AusschreibungMapper asMapper = AusschreibungMapper.ausschreibungMapper();
		BeteiligungMapper btMapper = BeteiligungMapper.beteiligungMapper();
		BewerbungMapper bwMapper = BewerbungMapper.bewerbungMapper();
		BewertungMapper bwtMapper = BewertungMapper.bewertungMapper();
		EigenschaftMapper egMapper = EigenschaftMapper.eigenschaftMapper();
		PartnerprofilMapper pfMapper = PartnerprofilMapper.partnerprofilMapper();
		PersonMapper psMapper = PersonMapper.personMapper();
		ProjektMapper pjMapper = ProjektMapper.projektMapper();
		ProjektMarktplatzMapper pmMapper = ProjektMarktplatzMapper.projektMarktplatzMapper();
		TeamMapper tmMapper = TeamMapper.teamMapper();
		UnternehmenMapper unMapper = UnternehmenMapper.unternehmenMapper();
		/*Ende Mapper Definitionen*/
		
		
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
