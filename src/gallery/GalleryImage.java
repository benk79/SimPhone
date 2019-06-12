package gallery;

import contact.Contact;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class GalleryImage implements Serializable
{
	private Integer id = -1;

	private String path;

	private ArrayList<Integer> peopleIds;

	public GalleryImage (String path) throws Exception
	{
		File imageFile = new File(path);
		if (!imageFile.isFile())
			throw new Exception("File not found");
		this.path = path;

		peopleIds = new ArrayList<Integer>();
	}

	public Integer getId ()
	{
		return id;
	}

	public void setId (Integer id)
	{
		if (this.id != -1)
			return;

		this.id = id;
	}

	public String getPath ()
	{
		return path;
	}


	/*
	public void setPeople (ArrayList<Contact> people)
	{
		this.people = people;
	} */

	public void removePeople (Integer id)
	{
		peopleIds.remove(id);
	}

	public void removePeople (Contact contact)
	{
		peopleIds.remove(contact.getId());
	}

	public void addPeople (Contact contact)
	{
		peopleIds.add(contact.getId());
	}


	public ArrayList<Integer> getPeopleIds ()
	{
		return peopleIds;
	}


	public BufferedImage getThumbnail (int size)
	{
		File input = new File(path);
		BufferedImage thumbImage = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(input);
			thumbImage = bufferedImage.getSubimage(0, 0, size, size);
		} catch (IOException e) {
			//e.printStackTrace();
			return null;
		}
		return thumbImage;

	}
}
