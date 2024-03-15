import javax.swing.JFrame;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.*;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class Controller {
	public static void main(String[] args) {
		
		FlatLightLaf.setup();
		UIManager.put( "TextComponent.arc", 5 );
		UIManager.put( "TabbedPane.selectedBackground", Color.BLACK );
		UIManager.put( "Component.arc", 0 );
		UIManager.put( "ProgressBar.arc", 0 );
		
		// Création d'une fenêtre Swing
        JFrame frame = new JFrame("Ma fenêtre");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //ajout barre de texte
        JTextField textField = new JTextField("Barre de texte");
        frame.add(textField);
        
        // Afficher la fenêtre
        frame.setVisible(true);
	}
	
}
