package RtoRMusic;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.BorderLayout;

public class Vue{
	Model m;
	TreeSet<Musique> play_list ;

   
    public void extractAndDisplayAlbumArt(String folderPath, int columns, int imageSize) {
    	m = new Model();
        JFrame frame = new JFrame("Album Art Gallery");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Créer un panneau pour afficher les pochettes d'albums dans une grille
        JPanel panel = new JPanel(new GridLayout(0, columns, 10, 10)); // Ajouter des marges de 10 pixels
        
        play_list = m.construire_play_list(folderPath);
        // Charger les fichiers audio du dossier spécifié
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        for (Musique musique : play_list) {
			ImageIcon imageIcon = new ImageIcon(musique.artwork.getBinaryData());
			Image resizedImage = imageIcon.getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
			JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
			imageLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			JLabel titleLabel = new JLabel("Title: " + musique.titre);
			JLabel artistLabel = new JLabel("Artist: " + musique.artist);
			JLabel genreLabel = new JLabel("Album: " + musique.genre);
			JLabel albumLabel = new JLabel("Album: " + musique.album);
			
			JPanel itemPanel = new JPanel(new BorderLayout());
			
			itemPanel.add(imageLabel, BorderLayout.WEST);

			// Créer un panneau pour contenir les informations texte à droite
			JPanel textPanel = new JPanel(new GridLayout(3, 1));

			// Ajouter les étiquettes pour le titre, l'artiste et le genre dans le panneau
			// de texte
			textPanel.add(titleLabel);
			textPanel.add(artistLabel);
			textPanel.add(albumLabel);

			// Ajouter le panneau de texte à droite
			itemPanel.add(textPanel, BorderLayout.CENTER);

			// Ajouter l'ensemble au panneau principal
			panel.add(itemPanel);
				
			
		}
		

        // Ajouter le panneau de grille à un JScrollPane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // Ajouter le JScrollPane à la fenêtre
        frame.getContentPane().add(scrollPane);

        // Ajuster la taille de la fenêtre au contenu
        frame.pack();

        // Centrer la fenêtre sur l'écran
        frame.setLocationRelativeTo(null);

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    private static boolean isAudioFile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        return extension.equals("mp3") || extension.equals("wav") || extension.equals("flac") || extension.equals("ogg");
    }
}
