package junitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import contact.Contact;

/**
 *This JUNIT test class allows us to test the different patterns used in contacts 
 ** @author Louise Bretz, Benjamin Keller
 *
 */

class PatternTest {

	
	@Test
	void testFirstname() throws Exception {
		
		try {
		String firstname1 = "";
		Contact contact1 = new Contact();
		contact1.setFirstName(firstname1);
		} catch (Exception e) 
			{
			System.out.println(e.getMessage());
			}
		
		String firstname2 = "Jean";
		Contact contact2 = new Contact();
		contact2.setFirstName(firstname2);
	}
	
	
	@Test
	void testPhoneNumber() throws Exception {
		
		
		try {
		String phone1 = "afdhasf";
		Contact contact1 = new Contact();
		contact1.setPhoneNumber(phone1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			}		
	
		String phone2 = "144";
		Contact contact2 = new Contact();
		contact2.setPhoneNumber(phone2);
		
		String phone3 = "0798606730";
		Contact contact3 = new Contact();
		contact3.setPhoneNumber(phone3);		
	}
	
	
	@Test
	void testEmail() throws Exception {
		
		try {
		String email1 = "jean";
		Contact contact1 = new Contact();
		contact1.setEmail(email1);
			} catch (Exception e) {
				System.out.println(e.getMessage());	}
	
		String email2 = "jeanmichel@gmail.com";
		Contact contact2 = new Contact();
		contact2.setEmail(email2);
		
	}
	
	@Test
	void testDate() throws Exception {
		
		try {
		String date = "20051996";
		Contact contact1 = new Contact();
		contact1.setBirthDate(date);
			} catch (Exception e) {
				System.out.println(e.getMessage());	}
		
	
		String email2 = "25.05.1996";
		Contact contact2 = new Contact();
		contact2.setBirthDate(email2);
		
		
		try {
			String date = "20.20.2010";
			Contact contact3 = new Contact();
			contact3.setBirthDate(date);
				} catch (Exception e) {
					System.out.println(e.getMessage());	}
	}
	
	
}
