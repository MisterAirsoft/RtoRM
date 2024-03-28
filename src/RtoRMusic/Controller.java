package RtoRMusic;
import com.formdev.flatlaf.FlatDarculaLaf;

public class Controller extends javax.swing.JFrame {
	
	public Controller(){
		getComponents();
	}
	
	public static void main(String[] args) {
		
		FlatDarculaLaf.setup();
		Vue vu1 = new Vue() ;
		vu1.extractAndDisplayAlbumArt("Music", 4, 50);

		;
		
	}
}
