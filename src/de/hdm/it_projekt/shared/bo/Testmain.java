package de.hdm.it_projekt.shared.bo;

import de.hdm.it_projekt.server.db.*;  
import java.util.Date;
import java.text.SimpleDateFormat;


public class Testmain {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		
		
		
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

		//System.out.println(pm1.getId() + " " + pm1.getBezeichnung());

		/*
		p1.setBezeichnung("Volvo");
		pmMapper.update(p1);
		pmMapper.delete(p1);
		*/
		
		System.out.println(tmMapper.findByMail("info@goodquality.de"));
		System.out.println(tmMapper.findAll());
		
		
		ProjektMarktplatz pm1 = new ProjektMarktplatz();
		pm1 = pmMapper.findById(4);
		System.out.println(psMapper.getByProjektMarktplatz(pm1));
		
		
		Person p1 = new Person();
		p1.setId(4);
		p1.setName("Bulat");
		p1.setVorname("Tugba");
		p1.setEmail("tugba@bulat.de");
		p1.setStrasse("Weg3");
		p1.setPlz(10100);
		p1.setOrt("Hier");
		p1.setTel("0711000000");
		p1.setPartnerprofilId(2);
		
		psMapper.update(p1);

		
		
		/*System.out.println(readDate());*/

	}

}
