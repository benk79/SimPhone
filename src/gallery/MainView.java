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
	//Filtre utilis� pour ne montre qu'un certain type de fichier dans la fen�tre
	FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");

	MainView (ArrayList<GalleryImage> imageList, int size, ActionListener imageListener)
	{

		thumbSize = size;
		this.imageList = imageList;
		this.imageListener = imageListener;
		//setPreferredSize(dim);
		setLayout(new BorderLayout());
		
		setBorder(new EmptyBorder(20, 5, 5, 5));
		setBackground(Color.BLACK);


		//On cr�e un JPanel contenant les images et on lui attribue un Gridlayout de 3x3
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
		
		//On attribue une action aux imagesButtons
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//On instancie un JFileChooser pour pouvoir s�lectionner un fichier
				JFileChooser fileChooser = new JFileChooser();

				//On permet � l'utilisateur de s�lectionner plusieurs fichier dans la fen�tre
				fileChooser.setMultiSelectionEnabled(true);

				//On applique un filtre sur l'extension du fichier comme d�finit en haut de cette classe
				fileChooser.setFileFilter(filter);

				//Si l'utilisateur valide la selection, on continue
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					/*
					 *On cr�e un tableau d'image avec les fichiers selectionn�s
					 *(On est oblig� d'utiliser un tableau File[] car nous permettons la selection de plusieurs fichiers plus haut dans le code)
					 *Si l'on ne permettait la selection que d'un fichier, File aurait suffit avec la m�thode getSelectedFile() au lieu de getSelectedFileS()
					 */
					File[] selectedImages = fileChooser.getSelectedFiles();

					//On parcours tous les �l�ments du tableau pr�c�demment cr��
					for(File selectedImage : selectedImages) {

						//Pour chaque fichier selectionn�, on cr�e un JButton avec pour apparence une ImageIcon qui prend en param�tre le chemin absolut du fichier sur notre disque
						//JButton imageButton = new JButton(new ImageIcon(selectedImage.getAbsolutePath()));
						ImageButton image = new ImageButton(selectedImage.getAbsolutePath(), size);
						//image.setPreferredSize(new Dimension(size, size));
						panel.add(image);

						//On ajoute le JButton au panel
						//panel.add(imageButton);
						
						panel.revalidate();
						panel.repaint();	
					}
				}
			}
		});

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
	
	
	/*
	private void copyFile(String source) throws IOException {
			  
			    File copied = new File(source);
			    
				try (
			      InputStream in = new BufferedInputStream(new FileInputStream(original));
			      OutputStream out = new BufferedOutputStream(new FileOutputStream(copied))) {
			    		byte[] buffer = new byte[1024];
			    		int lengthRead;
			    		while ((lengthRead = in.read(buffer)) > 0) {
			    			out.write(buffer, 0, lengthRead);
			    			out.flush();
			        }
			    }
			  
			}
	*/
	/*
	public class ListenerImageClick implements ActionListener {

	    public void actionPerformed(ActionEvent e) {
	    	
	    }
	} */
}
