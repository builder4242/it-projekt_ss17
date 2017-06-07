package de.hdm.it_projekt.shared.bo;

import de.hdm.it_projekt.server.db.*; 

import java.util.Date;


public class Testmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ProjektMarktplatz pm1 = new ProjektMarktplatz();
		pm1.setBezeichnung("Burger King");
		
		Person p1 = new Person();
		p1.setName("Fleps");
		p1.setVorname("Guenther");
		p1.setEmail("mail@fleps.de");
		p1.setStrasse("Rohrerweg");
		p1.setPlz(70565);
		p1.setOrt("Stugart");
		p1.setTel("00000");
		p1.setGoogleID("kfnsveior8945ztghg");
		
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
		
		
		System.out.println(pm1.getId() + " " + pm1.getBezeichnung());
		
		
		pmMapper.insert(pm1);
		psMapper.insert(p1);
		
		/*
		p1.setBezeichnung("Volvo");
		pmMapper.update(p1);
		pmMapper.delete(p1);
		*/
		
		System.out.println(pmMapper.findAll());
		System.out.println(pmMapper.findById(8));
		System.out.println(pmMapper.findByBezeichnung("Daimler"));
		
		Date dat = new Date();
		System.out.println( "Datum  :  "+dat.getDay()+"."+dat.getMonth()+"."+dat.getYear() );
	}

}
