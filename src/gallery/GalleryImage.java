package gallery;

import contact.Contact;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GalleryImage
{
	private Integer id = -1;

	private String path;

	private ArrayList<Contact> people;

	public GalleryImage (String path)
	{
		this.path = path;
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

	public ArrayList<Contact> getPeople ()
	{
		return people;
	}

	public void setPeople (ArrayList<Contact> people)
	{
		this.people = people;
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
