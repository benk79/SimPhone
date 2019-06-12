package junitTest;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.security.auth.login.Configuration;

import org.junit.jupiter.api.Test;

import contact.Contact;
import gallery.GalleryApp;
import gallery.GalleryImage;

public class FichierTest extends GalleryApp {
	

	@Test
	void test() throws Exception {
		
		
		String dir = "ressourcesContenu/Images";

		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();
		
		System.out.println("Tableau : " + listOfFiles);
		int expected = listOfFiles.length;
		System.out.println("Attendu " + expected);
		
		GalleryImage image = new GalleryImage("C:/Users/Louise/git/SimPhone/ressourcesContenu/Images/hokkaido2-bw.jpg");
		File file = new File(image.getPath());
		file.delete();
		

		int actual = listOfFiles.length;
	
		System.out.println("Réel : " + actual);
		
		boolean test ;
		if (expected==actual) 
			{
				test = true ;
			}
			else 
				test = false ;
		
		assertTrue(test);


	
	}

}