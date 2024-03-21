package RtoRMusic;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
    public static void main(String[] args) {
        // Désactiver les messages INFO de la bibliothèque jAudiotagger
        Logger.getLogger("org.jaudiotagger").setLevel(Level.WARNING);
        String cheminDossier = "Music";
        afficherInformationsMusicales(cheminDossier);
    }

    public static void afficherInformationsMusicales(String cheminDossier) {
        File dossier = new File(cheminDossier);
        File[] fichiers = dossier.listFiles();

        if (dossier.exists() && fichiers != null) {
            Arbre_recherche arbre = new Arbre_recherche();
            for (File fichier : fichiers) {
                if (fichier.isFile()) {
                    Musique musique = creerMusique(fichier.getName());
                    arbre.insertion(musique);
                }
            }
            System.out.println("Liste des musiques (ordre alphabétique) :");
            afficherMusiques(arbre.racine);
        } else {
            System.out.println("Le dossier spécifié n'existe pas ou ne contient aucun fichier.");
        }
    }
    
    public static void afficherMusiques(Noeud racine) {
        if (racine != null) {
            afficherMusiques(racine.gauche);
            System.out.println(racine.musique.toString());
            afficherMusiques(racine.droit);
        }
    }
    
    public static Musique creerMusique(String nomFichier) {
        return new Musique(nomFichier);
    }
}


