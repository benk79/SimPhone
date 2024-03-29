package contact;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Serializer;


/**
 * The Contact class is used to manage contacts in ContactApp
 *
 * @author Benjamin Keller
 * @version 1.0.0
 */
public class Contact implements Serializable
{
	/**
	 * Serial number for serialization
	 */
	private static final Long serialVersionUID = 1L;


	/**
	 * Unique id
	 */
	private Integer id = -1;


	/**
	 * First name
	 */
	private String firstName;


	/**
	 * Last name
	 */
	private String lastName;


	/**
	 * Email address
	 */
	private String email;


	/**
	 * Phone number
	 */
	private String phoneNumber;


	/**
	 * Birth date
	 */
	private Date birthDate;


	/**
	 * Birth date
	 */
	private String image;


	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


	/**
	 * Get unique id of contact
	 *
	 * @return Unique id
	 */
	public Integer getId ()
	{
		return id;
	}


	/**
	 * Set unique id for a new contact to save
	 *
	 * @return Unique id
	 */
	public Integer insertId ()
	{
		if (id != -1)
			return id;

		Integer contactIdIncrement = 0;

		String incrementIdFile = ContactApp.getDataPath() + "contactIdIncrement.ser";

		try {
			contactIdIncrement = (Integer) Serializer.get(incrementIdFile);
		} catch (Exception e) {
			System.out.println("oops");
		}

		id = contactIdIncrement;
		// contactIdIncrement++;

		try {
			Serializer.set(incrementIdFile, contactIdIncrement + 1);
		} catch (Exception e) {
			System.out.println("oops");
			e.printStackTrace();
		}

		return id;
	}


	/**
	 * Get first name of contact
	 *
	 * @return First name
	 */
	public String getFirstName ()
	{
		return firstName;
	}


	/**
	 * Set first name of contact
	 *
	 * @param firstName First name
	 */
	public void setFirstName (String firstName) throws Exception
	{
		if (firstName.isEmpty())
			throw new Exception("First name is required");

		this.firstName = firstName;
	}


	/**
	 * Get last name of contact
	 *
	 * @return Last name
	 */
	public String getLastName ()
	{
		return lastName;
	}


	/**
	 * Set last name of contact
	 *
	 * @param lastName Last name
	 */
	public void setLastName (String lastName)
	{
		this.lastName = lastName;
	}


	/**
	 * @return Email address
	 */
	public String getEmail ()
	{
		return email;
	}


	/**
	 * @param email Email address
	 */
	public void setEmail (String email) throws Exception
	{
		
		Pattern pattern = Pattern.compile (
				"([a-zA-Z0-9_\\-\\.]+)@((\\[a-z]{1,3}\\.[a-z]"
				 + "{1,3}\\.[a-z]{1,3}\\.)|(([a-zA-Z\\-]+\\.)+))"
				 + "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)",
				Pattern.MULTILINE);
		
		Matcher m=pattern.matcher(email);
		boolean b = true ;
		b=m.matches();
		
		if (email.isEmpty()){
			
		}
		else 
			if(b==false) {
			throw new Exception("Invalid mail");
		}
				
		this.email = email;
	}


	/**
	 * @return Phone number
	 */
	public String getPhoneNumber ()
	{
		return phoneNumber;
	}


	/**
	 * @param phoneNumber Phone number
	 * @throws Exception 
	 */
	public void setPhoneNumber (String phoneNumber) throws Exception
	{
		Pattern pattern = Pattern.compile (
				"([0-9]{3,10})",
				Pattern.MULTILINE);
		
		Matcher m=pattern.matcher(phoneNumber);
		boolean b=m.matches();
		
		if (phoneNumber.isEmpty()){
			
		}
		else {
			if (b==false) 
			{
			throw new Exception("Invalid phone number, 10 numbers without -");
			}
		}
		
		
		this.phoneNumber = phoneNumber;
	}


	public String getImage ()
	{
		return image;
	}

	public void setImage (String image)
	{
		this.image = image;
	}

	/**
	 * @return String representing the contact
	 */
	@Override
	public String toString ()
	{
		return "First name: " + firstName
			+ "\nLast name: " + lastName
			+ "\nPhone number: " + phoneNumber
			+ "\nEmail: " + email;
	}

	public Date getBirthDate ()
	{
		return birthDate;
	}
	
	public String getStringBirthDate ()
	{
		String dateString;
		if (birthDate != null) {
			dateFormat.setLenient(false);
			dateString = dateFormat.format(birthDate);

		} else {
			dateString = "";
		}
		return dateString;
	}

	public void setBirthDate (Date birthDate) throws Exception
	{
		this.birthDate = birthDate;
	}


	public void setBirthDate (String birthDate) throws Exception
	{
		if (birthDate.equals("")) {
			this.birthDate = null;
			return;
		}

		dateFormat.setLenient(false);
		try {
			Date date = dateFormat.parse(birthDate);
			if (!dateFormat.format(date).equals(birthDate)) {
				throw new Exception("Invalid format for date, should be jj.mm.aaaa");
			}

			this.birthDate = date;
		} catch (ParseException ex) {
			throw new Exception("Invalid format for date, should be jj.mm.aaaa");
		}

	}
}
