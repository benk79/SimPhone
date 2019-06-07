package gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainView extends JPanel {
	
	//Filtre utilisé pour ne montre qu'un certain type de fichier dans la fenêtre
	FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
	
	MainView (ArrayList<String> imageList, int size){

		//setPreferredSize(dim);
		
		
		setBorder(new EmptyBorder(20, 5, 5, 5));
		setBackground(Color.BLACK);


		//On crée un JPanel contenant les images et on lui attribue un Gridlayout de 3x3
		JPanel panel = new JPanel(new GridLayout(3,3));
		
		panel.setLayout (new GridLayout(3, 3));
		add(new JScrollPane(panel), BorderLayout.CENTER);
		panel.setBackground(Color.BLACK);
		
		//on crée un JPanel contenant les boutons de l'application
		JPanel panel_button = new JPanel();
		add(panel_button, BorderLayout.SOUTH);
		panel_button.setBackground(Color.BLACK);
		
		
		/*
		 * On parcours la liste d'images et on crée un JButton ainsi qu'une ImageIcon qu'on passe en 
		 * paramètre du JButton pour chaque images présentes dans la liste
		 */

		for (String imgpath : imageList)
		{
			ImageButton image = new ImageButton(imgpath, size);
			//image.setPreferredSize(new Dimension(size, size));
			panel.add(image);
		}

		//On ajoute du bouton add
		JButton btnAdd = new JButton("Ajouter");
		panel_button.add(btnAdd);
		
		//On attribue une action aux imagesButtons
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//On instancie un JFileChooser pour pouvoir sélectionner un fichier
				JFileChooser fileChooser = new JFileChooser();

				//On permet à l'utilisateur de sélectionner plusieurs fichier dans la fenêtre
				fileChooser.setMultiSelectionEnabled(true);

				//On applique un filtre sur l'extension du fichier comme définit en haut de cette classe
				fileChooser.setFileFilter(filter);

				//Si l'utilisateur valide la selection, on continue
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					/*
					 *On crée un tableau d'image avec les fichiers selectionnés
					 *(On est obligé d'utiliser un tableau File[] car nous permettons la selection de plusieurs fichiers plus haut dans le code)
					 *Si l'on ne permettait la selection que d'un fichier, File aurait suffit avec la méthode getSelectedFile() au lieu de getSelectedFileS()
					 */
					File[] selectedImages = fileChooser.getSelectedFiles();

					//On parcours tous les éléments du tableau précédemment créé
					for(File selectedImage : selectedImages) {

						//Pour chaque fichier selectionné, on crée un JButton avec pour apparence une ImageIcon qui prend en paramètre le chemin absolut du fichier sur notre disque
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
