package gallery;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.ButtonIcon;

public class ImageView extends JPanel {
	private GalleryImage image;

	private JPanel panel_image;
	private BlackAndWhiteListener blackAndWhiteListener ;
	private ButtonIcon btnModify ;
	private GalleryApp galleryApp;

	ImageView (GalleryApp galleryApp, ActionListener cancelListener)
	{
		this.galleryApp = galleryApp;

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 5, 5, 5));
		setBackground(Color.BLACK);
		
		//on cr�e le panel contenant l'image pleine �cran
		panel_image = new JPanel();
		panel_image.setBackground(Color.BLACK);
		add(panel_image, BorderLayout.CENTER);
		
			
		//on cr�e un JPanel contenant les boutons de l'application
		JPanel panel_button = new JPanel();
		add(panel_button, BorderLayout.SOUTH);
		panel_button.setBackground(Color.BLACK);

		//On ajoute du bouton annuler
		ButtonIcon btnCancel = new ButtonIcon("return.png", "Retour");
		panel_button.add(btnCancel);
		btnCancel.addActionListener(cancelListener);

		//On ajoute du bouton supprimer et le bouton modifier
		ButtonIcon btnDelete = new ButtonIcon("delete.png", "Supprimer");
		panel_button.add(btnDelete);
		
		btnModify = new ButtonIcon("blackAndWhite.png", "Modifier");
		panel_button.add(btnModify);

		blackAndWhiteListener = new BlackAndWhiteListener();
		btnModify.addActionListener(blackAndWhiteListener);

		
	}
	
	
	
	/* public BlackAndWhiteListener getBlackAndWhiteListener() {
		return blackAndWhiteListener;
	}



	public void setBlackAndWhiteListener(BlackAndWhiteListener blackAndWhiteListener) {
		this.blackAndWhiteListener = blackAndWhiteListener;
	} */



	public void setImage(GalleryImage image) {
		this.image = image;
		updateImage();
		
	}

	public void updateImage ()
	{
		panel_image.removeAll();

		System.out.println(image.getPath());
		JLabel chemin = new JLabel(image.getPath());
		chemin.setBackground(Color.BLACK);
		chemin.setForeground(Color.WHITE);
		panel_image.add(chemin);
		
		ImageIcon icon = new ImageIcon(image.getPath());
		Image img = icon.getImage();
		int height = icon.getIconHeight();
		int width = icon.getIconWidth();
		
		Image newimg;
		if (height > width) {
			int newWidth = 400;
			int newHeight = newWidth * height / width;
			newimg = img.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
		} else {
			int newHeight = 300;
			int newWidth = newHeight * width / height;
			newimg = img.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
		}

		icon = new ImageIcon(newimg);

		JLabel imagelabel = new JLabel(icon);
		imagelabel.setBackground(Color.BLACK);
		panel_image.setBackground(Color.BLACK);
		panel_image.setForeground(Color.WHITE);
		panel_image.add(imagelabel, BorderLayout.CENTER);
		

		validate();
		repaint();
	}
	
	/**
	 * Listener for black/white button in edit form
	*/

	class BlackAndWhiteListener implements ActionListener
	{
		/*
		GalleryImage image ; 
		ImageView imageview ;
		
		public BlackAndWhiteListener (GalleryImage image, ImageView imageview) {
			this.image = image ; 
			this.imageview = imageview ;
			
		}	
		*/
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			try {


				System.out.println(image.getPath());

				File input = new File(image.getPath());

				BufferedImage bufferedImage = ImageIO.read(input);

	            BufferedImage result = new BufferedImage(
			    bufferedImage.getWidth(),
			    bufferedImage.getHeight(),
	                    BufferedImage.TYPE_BYTE_BINARY);

	            Graphics2D graphic = result.createGraphics();
				graphic.drawImage(bufferedImage, 0, 0, Color.WHITE, null);
	            graphic.dispose();

				String newpath = image.getPath().substring(0, image.getPath().indexOf(".")).concat("-bw.jpg");

	            File output = new File(newpath);
	            ImageIO.write(result, "jpg", output);
	            
	          
	            
	           GalleryImage modifyImage = new GalleryImage(newpath);
				galleryApp.addImage(modifyImage);
				// GalleryImage  galleryImage = new GalleryImage(newImagePath);
				//imageList.add(galleryImage);
				setImage(modifyImage);

	        }  catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        }
		}
}
