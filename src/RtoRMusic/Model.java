package RtoRMusic;
import java.awt.GridLayout;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Model {
    TreeMap<String, Musique> play_list = new TreeMap<>();





    public static Musique creerMusique(String nomFichier) {
        return new Musique(nomFichier);
    }
    private static boolean isAudioFile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        return extension.equals("mp3") || extension.equals("wav") || extension.equals("flac") || extension.equals("ogg");
    }
    
    public TreeMap<String, Musique> construire_play_list(String folderPath) {
    	play_list = new TreeMap<String , Musique>();
    	File folder = new File(folderPath);
        File[] files = folder.listFiles();
    	if (files != null) {
            for (File file : files) {
                if (file.isFile() && isAudioFile(file.getName())) {
                    try {
                    	Musique musique =  new Musique(file.getName());
                    	play_list.put(musique.titre, musique);
                    	
                    	
                       
                       
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    	
    	return play_list;
    	}
    
    public TreeMap<String, Musique> Recherche(String mot_clef) {
    	//recherche les musique en fonciton des différent mot clef envoyer
    	//mot clef string, les espaces sépare les différents mot clef
    	TreeMap<String, Musique >new_play_list = new TreeMap<String , Musique>();
    	mot_clef= mot_clef.toUpperCase();
    	for  (Map.Entry<String, Musique> entry : play_list.entrySet()) {
    		Musique musique = entry.getValue();
    		if ((musique.titre.toUpperCase().contains(mot_clef)) || 
    		    (musique.album.toUpperCase().contains(mot_clef)) || 
    		    (musique.annee.toUpperCase().contains(mot_clef)) ||
    		    (musique.artist.toUpperCase().contains(mot_clef))) {
    			new_play_list.put(musique.titre, musique);
    			
    		}
    	       
    		
    	}
    	return new_play_list;
    }
    private void afficherResultatsRecherche(TreeMap<String,Musique> searchResults) {
        JPanel musicPanel = new JPanel(new GridLayout(0, 4, 10, 10)); // Utilisez le même layout que votre affichage principal
        Vue vue = new Vue();
        vue.afficherMusiques(musicPanel, searchResults, 50,""); // Utilisez la méthode d'affichage de la classe Vue
        JOptionPane.showMessageDialog(null, new JScrollPane(musicPanel), "Résultats de la recherche", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void rechercherMusique(String mot_clef) {
    	TreeMap<String,Musique> searchResults = Recherche(mot_clef);
        if (!searchResults.isEmpty()) {
            afficherResultatsRecherche(searchResults);
        } else {
            JOptionPane.showMessageDialog(null, "Nous sommes désolés, nous n'avons pas trouvé ce que vous cherchez 😭 ", "Résultat", JOptionPane.PLAIN_MESSAGE);
        }
    }
}

