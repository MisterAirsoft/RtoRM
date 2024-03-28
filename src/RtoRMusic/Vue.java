package RtoRMusic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vue {
    private Model m;
    private TreeSet<Musique> play_list;
    private JTextField searchField;

    
    
    public void extractAndDisplayAlbumArt(String folderPath, int columns, int imageSize) {
    	 Logger.getLogger("org.jaudiotagger").setLevel(Level.WARNING);
        m = new Model();
        JFrame frame = new JFrame("RTR Musique 🎶 " );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Font titleFont = new Font("Helvetica Neue Light", Font.ITALIC, 30); // Choisir la police, le style et la taille
        Font titleFont1 = new Font("Helvetica Neue Light", Font.ITALIC, 30);
      

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Barre de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchField = new JTextField("Que souhaitez vouz écoutez ?",20);
        searchField.setBackground(Color.white);
        searchField.setFont(titleFont);
        
        JButton searchButton = new JButton("Rechercher");
        // Ajout d'un écouteur pour changer la couleur du bouton lorsque survolé
        searchButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                searchButton.setBackground(new Color(156, 208, 241)); // Couleur de fond légèrement plus claire
            }

            public void mouseExited(MouseEvent evt) {
                searchButton.setBackground(Color.WHITE); // Retour à la couleur de fond normale
            }
        });

        // Définition des marges pour le bouton
        searchButton.setMargin(new Insets(5, 10, 5, 10)); 

        searchButton.setBackground(Color.white);
        searchButton.setFont(titleFont1);
        Color titleColor1 = Color.black;
        searchButton.setForeground(titleColor1);
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                if (!searchTerm.isEmpty()) {
                    m.rechercherMusique(searchTerm);
                } else {
                    
                }
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Affichage de la liste de musiques
        JPanel musicPanel = new JPanel(new GridLayout(0, columns, 10, 10));
        play_list = m.construire_play_list(folderPath);
        afficherMusiques(musicPanel, play_list, imageSize);
        mainPanel.add(new JScrollPane(musicPanel), BorderLayout.CENTER);

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFont(titleFont);
    }

    void afficherMusiques(JPanel panel, TreeSet<Musique> musiques, int imageSize) {
        panel.removeAll(); // Nettoyer le panneau
        for (Musique musique : musiques) {
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

            JPanel textPanel = new JPanel(new GridLayout(3, 1));
            textPanel.add(titleLabel);
            textPanel.add(artistLabel);
            textPanel.add(albumLabel);
            itemPanel.add(textPanel, BorderLayout.CENTER);

            panel.add(itemPanel);
        }
        panel.revalidate(); // Mettre à jour le panneau
        panel.repaint(); // Redessiner le panneau
    }
}
