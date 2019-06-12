package junitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import contact.Contact;

/**
 * This JUNIT test class allows us to test if the String inputs are correct
 * @author Louise Bretz, Benjamin Keller
 *
 */
class ContactTest {


	@Test
	void testExplicitToString() throws Exception
	{
		
		String firstname = "Jean";
		String lastname = "Dujardin";
		String email = "jeanmi@gmail.com";
		String phoneNumber = "027395935";
		String image="null";
		
		String expected = firstname + ";" + lastname + ";" + email + ";" + phoneNumber  + ";" + image;
		
		
		Contact contact = new Contact();
		contact.setFirstName(firstname);
		contact.setLastName(lastname);
		contact.setEmail(email);
		contact.setPhoneNumber(phoneNumber);
		contact.setImage(image);
		
			
		String actual = contact.getFirstName() + ";" + contact.getLastName() + 
				";" + contact.getEmail() + ";" + contact.getPhoneNumber()  + ";" + contact.getImage();
		System.out.println(expected);
		System.out.println(actual);
		assertTrue(expected.equals(actual));
	
		
	}
				
	
}
