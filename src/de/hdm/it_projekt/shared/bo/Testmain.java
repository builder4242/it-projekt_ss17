package de.hdm.it_projekt.shared.bo;

import de.hdm.it_projekt.server.db.*;  
import java.util.Date;
import java.text.SimpleDateFormat;


public class Testmain {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ProjektMarktplatz pm1 = new ProjektMarktplatz();
		pm1.setBezeichnung("Burger King");
		
		
		
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

		/*
		p1.setBezeichnung("Volvo");
		pmMapper.update(p1);
		pmMapper.delete(p1);
		*/
		
		System.out.println(tmMapper.findTeamByGoogleId("oie8tu49hgnovrhe"));
		System.out.println(unMapper.findUnternehmenByGoogleId("klsnjv98z793rhgobwv"));
		System.out.println(psMapper.findPersonByGoogleId("iurehf934fh3ovrv"));
		
		System.out.println(tmMapper.findByMail("info@goodquality.de"));
		System.out.println(tmMapper.findAll());
		
		
		/*System.out.println(readDate());*/

	}

}
