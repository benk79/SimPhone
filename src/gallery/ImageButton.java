package gallery;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageButton extends JButton {

	ImageButton (String path, int size)
	{
		super(new ImageIcon(path));
		setPreferredSize(new Dimension(size, size));
	}
}
