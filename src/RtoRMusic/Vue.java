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
import java.awt.BorderLayout;

public class Vue{

    public static void main(String[] args) {
        // Chemin vers le dossier contenant les fichiers audio
        String folderPath = "Music";
        // Nombre de colonnes dans la grille
        int columns = 1;
        // Taille des images des pochettes d'albums
        int imageSize = 90;

        // Extraire les pochettes d'albums et les afficher dans une grille
        extractAndDisplayAlbumArt(folderPath, columns, imageSize);
    }

    private static void extractAndDisplayAlbumArt(String folderPath, int columns, int imageSize) {
        JFrame frame = new JFrame("Album Art Gallery");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Créer un panneau pour afficher les pochettes d'albums dans une grille
        JPanel panel = new JPanel(new GridLayout(0, columns, 10, 10)); // Ajouter des marges de 10 pixels

        // Charger les fichiers audio du dossier spécifié
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isAudioFile(file.getName())) {
                    try {
                        // Charger les métadonnées du fichier audio
                        AudioFile audioFile = AudioFileIO.read(file);
                        Tag tag = audioFile.getTag();

                        // Extraire la pochette de l'album
                        byte[] imageData = tag.getFirstArtwork().getBinaryData();
                        ImageIcon imageIcon = new ImageIcon(imageData);
                        Image image = imageIcon.getImage();

                        // Redimensionner l'image
                        Image resizedImage = image.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);

                        // Créer une étiquette pour afficher l'image redimensionnée
                        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));

                        // Ajouter une marge autour de l'étiquette
                        imageLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Ajouter des marges de 10 pixels

                        // Créer une étiquette pour afficher le titre de la musique
                        JLabel titleLabel = new JLabel("Title: " + tag.getFirst(FieldKey.TITLE));

                        // Créer une étiquette pour afficher l'artiste de la musique
                        JLabel artistLabel = new JLabel("Artist: " + tag.getFirst(FieldKey.ARTIST));

                        // Créer une étiquette pour afficher le genre de la musique
                        JLabel genreLabel = new JLabel("Album: " + tag.getFirst(FieldKey.ALBUM));

                        // Créer un panneau pour contenir l'image et les informations texte
                        JPanel itemPanel = new JPanel(new BorderLayout());

                        // Ajouter l'image à gauche
                        itemPanel.add(imageLabel, BorderLayout.WEST);

                        // Créer un panneau pour contenir les informations texte à droite
                        JPanel textPanel = new JPanel(new GridLayout(3, 1));

                        // Ajouter les étiquettes pour le titre, l'artiste et le genre dans le panneau de texte
                        textPanel.add(titleLabel);
                        textPanel.add(artistLabel);
                        textPanel.add(genreLabel);

                        // Ajouter le panneau de texte à droite
                        itemPanel.add(textPanel, BorderLayout.CENTER);

                        // Ajouter l'ensemble au panneau principal
                        panel.add(itemPanel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
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
