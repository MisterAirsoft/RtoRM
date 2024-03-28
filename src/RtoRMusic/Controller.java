package RtoRMusic;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.formdev.flatlaf.FlatDarculaLaf;

public class Controller extends javax.swing.JFrame {
	public Controller(){
		getComponents();
	}
	@SuppressWarnings("unchecked")
	
	public static void main(String[] args) {
		Logger.getLogger("org.jaudiotagger").setLevel(Level.WARNING);
		FlatDarculaLaf.setup();
		Vue vu1 = new Vue() ;
		vu1.extractAndDisplayAlbumArt("Music", 4, 50);
	
			

	
		;
		
	}
}
