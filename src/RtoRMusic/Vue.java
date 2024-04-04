package RtoRMusic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vue {
    private Model m;
    private TreeMap<String, Musique> play_list;
    private JTextField searchField;
    private JPanel musicPanel; // Déclaration de musicPanel
    private int imageSize; // Déclaration de imageSize

    public void extractAndDisplayAlbumArt(String folderPath, int columns, int imageSize) {
        this.imageSize = imageSize; // Assignation de la taille d'image à la variable de classe imageSize
        Logger.getLogger("org.jaudiotagger").setLevel(Level.WARNING);
        m = new Model();
        JFrame frame = new JFrame("RTR Musique 🎶 ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font titleFont = new Font("Helvetica Neue Light", Font.ITALIC, 30); // Choisir la police, le style et la taille
        Font titleFont1 = new Font("Liberation Sans", Font.BOLD, 30);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Barre de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchField = new JTextField("Que souhaitez-vous écouter ?", 20);
        searchField.setBackground(Color.white);
        searchField.setFont(titleFont);
        searchField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                searchField.setText("");
                searchField.setForeground(Color.black);
            }
        });

        JButton searchButton = new JButton("Rechercher");

        // Ajout d'un écouteur pour changer la couleur du bouton lorsque survolé
        searchButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                searchButton.setBackground(new Color(161, 25, 195)); // Couleur de fond légèrement plus claire
            }

            public void mouseExited(MouseEvent evt) {
                searchButton.setBackground(Color.white); // Retour à la couleur de fond normale
            }
        });

        // Définition des marges pour le bouton
        searchButton.setBackground(Color.white);
        searchButton.setFont(titleFont1);
        Color titleColor1 = Color.black;
        searchButton.setForeground(titleColor1);

        JButton homeButton = new JButton("Accueil");
        homeButton.setFont(titleFont1);
        homeButton.setBackground(Color.white);
        homeButton.setForeground(titleColor1);

        homeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                homeButton.setBackground(new Color(161, 25, 195)); // Couleur de fond légèrement plus claire
            }

            public void mouseExited(MouseEvent evt) {
                homeButton.setBackground(Color.white); // Retour à la couleur de fond normale
            }
        });

        JButton favoritesButton = new JButton("Favoris");
        favoritesButton.setFont(titleFont1);
        favoritesButton.setBackground(Color.white);
        favoritesButton.setForeground(titleColor1);

        favoritesButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                favoritesButton.setBackground(new Color(161, 25, 195)); // Couleur de fond légèrement plus claire
            }

            public void mouseExited(MouseEvent evt) {
                favoritesButton.setBackground(Color.white); // Retour à la couleur de fond normale
            }
        });

        musicPanel = new JPanel(new GridLayout(0, columns, 5, 5)); // Initialisation de musicPanel

        // Ajouter un ActionListener pour le bouton de recherche
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        // Ajouter un KeyListener pour le champ de recherche pour déclencher la recherche lors de l'appui sur "Entrée"
        searchField.addKeyListener(new SearchKeyListener());

        // Ajouter des actions aux boutons
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à exécuter lors du clic sur le bouton Accueil
                // Vous pouvez implémenter cette action ici
            }
        });

        favoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à exécuter lors du clic sur le bouton Favoris
                // Vous pouvez implémenter cette action ici
            }
        });

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(homeButton); // Ajouter le bouton Accueil
        searchPanel.add(favoritesButton); // Ajouter le bouton Favoris
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Affichage de la liste de musiques
        play_list = m.construire_play_list(folderPath);
        afficherMusiques(musicPanel, play_list, imageSize, "");
        mainPanel.add(new JScrollPane(musicPanel), BorderLayout.CENTER);

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFont(titleFont);
    }

    void afficherMusiques(JPanel panel, TreeMap<String, Musique> musiques, int imageSize, String searchTerm) {
        panel.removeAll(); // Nettoyer le panneau

        for (Map.Entry<String, Musique> entry : musiques.entrySet()) {
            Musique musique = entry.getValue();
            // Vérifier si la musique correspond au terme de recherche
            if (musique.titre.toLowerCase().contains(searchTerm.toLowerCase()) ||
                    musique.artist.toLowerCase().contains(searchTerm.toLowerCase()) ||
                    musique.album.toLowerCase().contains(searchTerm.toLowerCase()) ||
                    musique.genre.toLowerCase().contains(searchTerm.toLowerCase())) {
                ImageIcon imageIcon = new ImageIcon(musique.artwork.getBinaryData());
                Image resizedImage = imageIcon.getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
                imageLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel titleLabel = new JLabel("Title: " + musique.titre);
                JLabel artistLabel = new JLabel("Artist: " + musique.artist);
                JLabel genreLabel = new JLabel("Album: " + musique.genre);
                JLabel albumLabel = new JLabel("Album: " + musique.album);
                /**titleLabel.
                 artistLabel.
                 genreLabel.
                 albumLabel.**/

                JPanel itemPanel = new JPanel(new BorderLayout());

                itemPanel.add(imageLabel, BorderLayout.WEST);

                JPanel textPanel = new JPanel(new GridLayout(3, 1));
                textPanel.add(titleLabel);
                textPanel.add(artistLabel);
                textPanel.add(albumLabel);
                itemPanel.add(textPanel, BorderLayout.CENTER);

                panel.add(itemPanel);
            }
        }
        panel.revalidate(); // Mettre à jour le panneau
        panel.repaint(); // Redessiner le panneau
    }

    // Méthode pour effectuer la recherche
    private void performSearch() {
        String searchTerm = searchField.getText();
        if (!searchTerm.isEmpty()) {
            afficherMusiques(musicPanel, play_list, imageSize, searchTerm);
        } else {
            afficherMusiques(musicPanel, play_list, imageSize, ""); // Afficher toutes les musiques si la recherche est vide
        }
    }

    // Classe interne pour gérer les événements clavier dans le champ de recherche
    private class SearchKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                performSearch();
            }
        }
    }
}

