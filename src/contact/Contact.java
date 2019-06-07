package contact;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

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
		contactIdIncrement++;

		try {
			Serializer.set(incrementIdFile, contactIdIncrement);
		} catch (Exception e) {
			System.out.println("oops");
			e.printStackTrace();
		}

		return id;
	}


	/**
	 * @return First name
	 */
	public String getFirstName ()
	{
		return firstName;
	}


	/**
	 * @param firstName First name
	 */
	public void setFirstName (String firstName)
	{
		this.firstName = firstName;
	}


	/**
	 * @return Last name
	 */
	public String getLastName ()
	{
		return lastName;
	}


	/**
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
	public void setEmail (String email)
	{
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
	 */
	public void setPhoneNumber (String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
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
}
