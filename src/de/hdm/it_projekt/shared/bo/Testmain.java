package de.hdm.it_projekt.shared.bo;

import de.hdm.it_projekt.server.db.BewerbungMapper;
import de.hdm.it_projekt.server.db.PersonMapper;
import de.hdm.it_projekt.shared.bo.Bewerbung;
import java.util.Date;

public class Testmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Bewerbung b1 = new Bewerbung();
		
		b1.setBewerbungstext("das ist ein Test");
		
		System.out.println(b1.getBewerbungstext());
		
		BewerbungMapper.insert(b1);
		
	}

}
