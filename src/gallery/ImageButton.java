package gallery;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Image;

public class ImageButton extends JButton {

	private GalleryImage image;

	private int thumbsize;

	public ImageButton (GalleryImage image, int thumbsize)
	{
		this.thumbsize = thumbsize;
		//super(new ImageIcon(path));
		//int size = 180;
		setPreferredSize(new Dimension(thumbsize, thumbsize));

		// createEmptyBorder(20, 20, 20, 20);
		// setMargin(new Insets(10, 10, 10, 10));
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);


		setImage(image);

	}

	public void setImage (GalleryImage image)
	{
		this.image = image;

		ImageIcon icon = new ImageIcon(image.getPath());
		Image img = icon.getImage();
		int height = icon.getIconHeight();
		int width = icon.getIconWidth();

		Image newimg;
		//int thumbsize = size;

		if (height > width) {
			int newWidth = thumbsize;
			int newHeight = newWidth * height / width;
			newimg = img.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
		} else {
			int newHeight = thumbsize;
			int newWidth = newHeight * width / height;
			newimg = img.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
		}

		icon = new ImageIcon(newimg);

		setIcon(icon);
	}



	public GalleryImage getImage ()
	{
		return image;
	}
}
