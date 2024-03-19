import java.awt.Color;
import javax.swing.*;
import com.formdev.flatlaf.*;

public class Controller extends javax.swing.JFrame {
	public Controller(){
		getComponents();
	}
	@SuppressWarnings("unchecked")
	
	public static void main(String[] args) {
		
		FlatDarculaLaf.setup();
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
			
				new Controller().setVisible(true);
				
			}
			
		});
		
	}
}
