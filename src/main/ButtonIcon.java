import java.awt.Dimension;

import javax.swing.*;

public class ButtonIcon extends JButton {
	
	  
	
	public ButtonIcon(String way) {
		
		
		
        // bouton transparent
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        
     // icone ajoutée
        setIcon(new ImageIcon(way));
        
    

	}
	
	
	
	
}

