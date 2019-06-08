package gallery;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainView extends JPanel {

	private ActionListener imageListener;

	private int thumbSize;

	private ArrayList<GalleryImage> imageList;

	private JPanel panel;

	MainView (ArrayList<GalleryImage> imageList, int size, ActionListener imageListener, ActionListener addListener)
	{

		thumbSize = size;
		this.imageList = imageList;
		this.imageListener = imageListener;

		setLayout(new BorderLayout());
		
		setBorder(new EmptyBorder(20, 5, 5, 5));
		setBackground(Color.BLACK);


		panel = new JPanel();


		add(new JScrollPane(panel), BorderLayout.CENTER);
		panel.setBackground(Color.BLACK);
		
		//on cr�e un JPanel contenant les boutons de l'application
		JPanel panel_button = new JPanel();
		add(panel_button, BorderLayout.SOUTH);
		panel_button.setBackground(Color.BLACK);
		
		
		/*
		 * On parcours la liste d'images et on cr�e un JButton ainsi qu'une ImageIcon qu'on passe en 
		 * param�tre du JButton pour chaque images pr�sentes dans la liste
		 */

		//On ajoute du bouton add
		JButton btnAdd = new JButton("Ajouter");
		panel_button.add(btnAdd);
		btnAdd.addActionListener(addListener);
		updateView();
	}

	public void updateView ()
	{

		panel.removeAll();


		ArrayList<ImageButton> buttons = new ArrayList<ImageButton>();
		for (GalleryImage galleryImage : imageList) {
			ImageButton image = new ImageButton(galleryImage.getPath(), thumbSize);
			image.addActionListener(imageListener);
			buttons.add(image);

		}


		int count = buttons.size();
		int cols = 2;
		int rows = count / cols;


		if (count % cols != 0) {
			rows++;
		}

		panel.setLayout(new GridLayout(rows, cols));


		for (ImageButton imgbtn : buttons) {
			JPanel btnContainer = new JPanel();
			imgbtn.setMargin(new Insets(10, 10, 10, 10));
			btnContainer.add(imgbtn);
			btnContainer.setBackground(Color.black);
			panel.add(btnContainer);
		}

		int emptyCells = rows * cols - count;

		for (int i = 0; i < emptyCells; i++) {
			panel.add(new JLabel(""));
		}

		panel.validate();
		panel.repaint();
	}
	
	
}
