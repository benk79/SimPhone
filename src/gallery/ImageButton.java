package gallery;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageButton extends JButton {

	private String path;
	ImageButton (String path, int size)
	{
		super(new ImageIcon(path));

		this.path = path;
		setPreferredSize(new Dimension(size, size));
	}

	public String getPath ()
	{
		return path;
	}
}
