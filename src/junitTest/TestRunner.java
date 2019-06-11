package junitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import contact.Contact;

/**
 * Cette classe test JUNIT nous permet de tester si les entrée String sont correctes
 * @author Louise Bretz, Benjamin Keller
 *
 */
class ContactTest {


	@Test
	void testExplicitToString()
	{
		
		String id = "1";
		String firstname = "Jean-Michel";
		String lastname = "Dujardin";
		String email ="jeanmi@gmail.com";
		String phoneNumber ="027395935";
		Date birthDate = null ;
		String image="null";
		
		String expected = id+ ";"+ firstname+ ";" + lastname+";" +email + ";"+phoneNumber+ ";"+birthDate+ ";"+image;
		Contact contact = new Contact();
		contact.setFirstName(firstname);
		contact.setLastName(lastname);
		contact.setEmail(email);
		contact.setPhoneNumber(phoneNumber);
		contact.setBirthDate(birthDate);
		contact.setImage(image);
		
			
		String actual = contact.toString();
		System.out.println(expected);
		System.out.println(actual);
		assertTrue(expected.equals(actual));
		
	}
				
	
}
