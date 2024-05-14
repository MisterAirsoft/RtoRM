package RtoRMusic;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.formdev.flatlaf.FlatDarculaLaf;
public class Controller extends javax.swing.JFrame {
	
	public Controller(){
		getComponents();
	}
	
	public static void main(String[] args) throws IOException {
		 Logger jAudioTaggerLogger = Logger.getLogger("org.jaudiotagger");
	     jAudioTaggerLogger.setLevel(Level.WARNING); 
		
		FlatDarculaLaf.setup();
		Vue vu1 = new Vue() ;
		vu1.extractAndDisplayAlbumArt("Music", 1, 50);

		;
		
	}
}
