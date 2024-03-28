package RtoRMusic;
import java.awt.GridLayout;
import java.io.File;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Model {
	TreeSet<Musique> play_list;

    public static void main(String[] args) {
        // Désactiver les messages INFO de la bibliothèque jAudiotagger
       
        String cheminDossier = "Music";
        NoeudMusique listeMusiques = construireListeMusiques(cheminDossier);
        afficherListeMusiques(listeMusiques);
    }

    public static NoeudMusique construireListeMusiques(String cheminDossier) {
        File dossier = new File(cheminDossier);
        File[] fichiers = dossier.listFiles();
        NoeudMusique tete = null;
        NoeudMusique fin = null;

        if (dossier.exists() && fichiers != null) {
            // Trier les noms de fichiers par ordre alphabétique
            Arrays.sort(fichiers);

            tete = new NoeudMusique(null);
            fin = tete;

            for (File fichier : fichiers) {
                if (fichier.isFile()) {
                    Musique musique = creerMusique(fichier.getName());
                    fin.suivant = new NoeudMusique(musique);
                    fin = fin.suivant;
                }
            }
        } else {
            System.out.println("Le dossier spécifié n'existe pas ou ne contient aucun fichier.");
        }
        return tete;
    }
    public void rechercherMusique(String mot_clef) {
        TreeSet<Musique> searchResults = Recherche(mot_clef);
        if (!searchResults.isEmpty()) {
            afficherResultatsRecherche(searchResults);
        } else {
            JOptionPane.showMessageDialog(null, "Nous sommes désolés, nous n'avons pas trouvé ce que vous cherchez 😭 ", "Résultat", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void afficherListeMusiques(NoeudMusique tete) {
        System.out.println("Liste des musiques (ordre alphabétique) :");
        NoeudMusique courant = tete.suivant; // Ignorer le premier nœud sentinelle
        while (courant != null) {
            System.out.println(courant.musique.toString());
            courant = courant.suivant;
        }
    }

    public static Musique creerMusique(String nomFichier) {
        return new Musique(nomFichier);
    }
    private static boolean isAudioFile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        return extension.equals("mp3") || extension.equals("wav") || extension.equals("flac") || extension.equals("ogg");
    }
    
    public TreeSet<Musique> construire_play_list(String folderPath) {
    	play_list = new TreeSet<Musique>();
    	File folder = new File(folderPath);
        File[] files = folder.listFiles();
    	if (files != null) {
            for (File file : files) {
                if (file.isFile() && isAudioFile(file.getName())) {
                    try {
                    	play_list.add(new Musique(file.getName()));
                    	
                       
                       
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    	
    	return play_list;
    	}
    
    public TreeSet<Musique> Recherche(String mot_clef) {
    	TreeSet<Musique >new_play_list = new TreeSet<Musique>();
    	mot_clef= mot_clef.toUpperCase();
    	for (Musique music : play_list) {
    		if (music.titre.toUpperCase().contains(mot_clef)) {
    			new_play_list.add(music);
    			
    		}
    		else {
    			if (music.album.toUpperCase().contains(mot_clef)) {
        			new_play_list.add(music);
        			
        		}
    			else {
        			if (music.annee.toUpperCase().contains(mot_clef)) {
            			new_play_list.add(music);
            			
            		}
        			{
            			if (music.artist.toUpperCase().contains(mot_clef)) {
                			new_play_list.add(music);
                			
                		}
            		}
        		}
    		}
    	}
    	return new_play_list;
    }
    private void afficherResultatsRecherche(TreeSet<Musique> searchResults) {
        JPanel musicPanel = new JPanel(new GridLayout(0, 4, 10, 10)); // Utilisez le même layout que votre affichage principal
        Vue vue = new Vue();
        vue.afficherMusiques(musicPanel, searchResults, 50); // Utilisez la méthode d'affichage de la classe Vue
        JOptionPane.showMessageDialog(null, new JScrollPane(musicPanel), "Résultats de la recherche", JOptionPane.PLAIN_MESSAGE);
    }
}

