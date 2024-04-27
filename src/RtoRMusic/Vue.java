package RtoRMusic;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders.SplitPaneBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vue {
    private Model m;
    private TreeMap<String, Musique> play_list;
    private JTextField searchField;
    private JPanel musicPanel;
    private int imageSize;

    
    public void extractAndDisplayAlbumArt(String folderPath, int columns, int imageSize) {
        this.imageSize = imageSize;
        Logger.getLogger("org.jaudiotagger").setLevel(Level.WARNING);
        m = new Model();
        JFrame frame = new JFrame("RTR Musique 🎶 ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        


        Font titleFont = new Font("Helvetica Neue Light", Font.ITALIC, 30);
        Font titleFont1 = new Font("Helvetica Neue Light", Font.BOLD, 30);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK); // Définir la couleur de fond du mainPanel
        // Créer un nouveau JPanel pour les boutons et utiliser GridBagLayout pour qu'ils prennent toute la hauteur de la page
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        buttonPanel.setBackground(Color.BLACK); // Définir la couleur de fond du panneau des boutons

        // Créer un objet GridBagConstraints pour configurer le positionnement et le dimensionnement des composants dans GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL; // Remplissage vertical
        gbc.gridx = 0; // Positionnement sur la colonne 0
        gbc.gridy = 0; // Positionnement sur la ligne 0
        gbc.anchor = GridBagConstraints.NORTH; // Ancrage au nord (haut)
        
        
        JButton searchButton = new JButton(" ✈ Explorer ");
        searchButton.setFont(titleFont1);
        searchButton.setBackground(Color.black);
        searchButton.setForeground(Color.white);
        searchButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                searchButton.setBackground(new Color(161, 25, 195)); // Couleur de fond légèrement plus claire
                
            }

            public void mouseExited(MouseEvent evt) {
                searchButton.setBackground(Color.black); // Retour à la couleur de fond normale
            }
        });
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Initialiser la barre de recherche
                JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                searchField = new JTextField("Que souhaitez-vous écouter ?", 20);
                searchField.setBackground(Color.white);
                searchField.setForeground(Color.gray);
                searchField.setFont(titleFont);
                searchField.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        searchField.setText("");
                        searchField.setForeground(Color.black);
                    }
                });
                searchField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        performSearch();
                    }
                });
                
                
                searchPanel.add(searchField);
                mainPanel.add(searchPanel, BorderLayout.NORTH);

                // Réinitialiser la couleur d'arrière-plan du bouton "Rechercher"
                searchButton.setBackground(Color.black);

                // Rafraîchir l'interface utilisateur
                mainPanel.revalidate();
                mainPanel.repaint();
            }
            private void performSearch() {
                String keyword = searchField.getText().trim();
                if (!keyword.isEmpty()) {
                    // Appeler la méthode de recherche de musique dans le modèle
                    m.rechercherMusique(keyword);
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un mot-clé de recherche.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        
        
     // Définir une largeur préférée pour tous les boutons
        int preferredButtonWidth = 400; // Largeur préférée en pixels
        searchButton.setPreferredSize(new Dimension(preferredButtonWidth, searchButton.getPreferredSize().height));
        buttonPanel.add(searchButton, gbc);
        buttonPanel.add(searchButton);

        JButton homeButton = new JButton(" ⛺ Accueil");
        homeButton.setFont(titleFont1);
        homeButton.setBackground(Color.black);
        homeButton.setForeground(Color.white);
        homeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                homeButton.setBackground(new Color(161, 25, 195)); // Couleur de fond légèrement plus claire
            }

            public void mouseExited(MouseEvent evt) {
                homeButton.setBackground(Color.black); // Retour à la couleur de fond normale
            }
        });
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when Home button is clicked
            	afficherMusiques(musicPanel, play_list, imageSize, "");
            }
        });
        homeButton.setPreferredSize(new Dimension(preferredButtonWidth, homeButton.getPreferredSize().height));
        buttonPanel.add(homeButton, gbc);
        buttonPanel.add(homeButton);

        JButton favoritesButton = new JButton(" ❤️ Favoris");
        favoritesButton.setFont(titleFont1);
        favoritesButton.setBackground(Color.black);
        favoritesButton.setForeground(Color.white);
        favoritesButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                favoritesButton.setBackground(new Color(161, 25, 195)); // Couleur de fond légèrement plus claire
            }

            public void mouseExited(MouseEvent evt) {
                favoritesButton.setBackground(Color.black); // Retour à la couleur de fond normale
            }
        });
        favoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when Favorites button is clicked
            	afficherMusiques(musicPanel, m.musique_aimer, imageSize, "");
            }
        });
        favoritesButton.setPreferredSize(new Dimension(preferredButtonWidth, favoritesButton.getPreferredSize().height));
        buttonPanel.add(favoritesButton, gbc);
        buttonPanel.add(favoritesButton);
        
        JButton recommendationButton = new JButton("♬ Suggestion ");
        recommendationButton.setFont(titleFont1);
        recommendationButton.setBackground(Color.black);
        recommendationButton.setForeground(Color.white);
        recommendationButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                recommendationButton.setBackground(new Color(161, 25, 195)); // Couleur de fond légèrement plus claire
            }

            public void mouseExited(MouseEvent evt) {
                recommendationButton.setBackground(Color.black); // Retour à la couleur de fond normale
            }
        });
        recommendationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	afficherMusiques(musicPanel, m.RechercheRecommendation(), imageSize, "");
               
            }
        });
        recommendationButton.setPreferredSize(new Dimension(preferredButtonWidth,  recommendationButton.getPreferredSize().height));
        buttonPanel.add( recommendationButton, gbc);
        buttonPanel.add(recommendationButton);
        
        JButton userButton = new JButton("👤 Utilisateur");
        userButton.setFont(titleFont1);
        userButton.setBackground(Color.black);
        userButton.setForeground(Color.white);
        userButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                userButton.setBackground(new Color(161, 25, 195)); // Couleur de fond légèrement plus claire
            }

            public void mouseExited(MouseEvent evt) {
                userButton.setBackground(Color.black); // Retour à la couleur de fond normale
            }
        });
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à effectuer lorsque le bouton utilisateur est cliqué
                // Par exemple, afficher une boîte de dialogue pour la gestion de l'utilisateur
                JOptionPane.showMessageDialog(frame, "Gérer l'utilisateur");
            }
        });
     



        mainPanel.add(buttonPanel, BorderLayout.WEST);

        musicPanel = new JPanel(new GridLayout(0, columns, 5, 5));

        play_list = m.construire_play_list(folderPath);
        afficherMusiques(musicPanel, play_list, imageSize, "");
        mainPanel.add(new JScrollPane(musicPanel), BorderLayout.CENTER);
        
        
        
        userButton.setPreferredSize(new Dimension(preferredButtonWidth, userButton.getPreferredSize().height));
        
        
     // Ajouter les boutons au panneau des boutons
        gbc.insets = new Insets(0, 5, 150, 5); // Appliquez des marges après le bouton de suggestion
        buttonPanel.add(userButton, gbc); // Ajoutez le bouton utilisateur
        gbc.gridy++; // Passez à la ligne suivante
        
        gbc.insets = new Insets(0, 0, 0, 0);
        buttonPanel.add(searchButton, gbc); // Ajouter le bouton de recherche
        gbc.gridy++; // Passer à la ligne suivante
        buttonPanel.add(homeButton, gbc); // Ajouter le bouton d'accueil
        gbc.gridy++; // Passer à la ligne suivante
        buttonPanel.add(favoritesButton, gbc); // Ajouter le bouton de favoris
        gbc.gridy++; // Passer à la ligne suivante
        buttonPanel.add(recommendationButton, gbc); // Ajoutez le bouton de suggestion
        

        
        mainPanel.add(buttonPanel, BorderLayout.WEST); // Ajouter le panneau des boutons au mainPanel

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFont(titleFont);
    }

    void afficherMusiques(JPanel panel, TreeMap<String, Musique> musiques, int imageSize, String searchTerm) {
        panel.removeAll();

        for (Map.Entry<String, Musique> entry : musiques.entrySet()) {
            Musique musique = entry.getValue();

            if (musique.titre.toLowerCase().contains(searchTerm.toLowerCase()) ||
                    musique.artist.toLowerCase().contains(searchTerm.toLowerCase()) ||
                    musique.album.toLowerCase().contains(searchTerm.toLowerCase()) ||
                    musique.genre.toLowerCase().contains(searchTerm.toLowerCase())) {

                ImageIcon imageIcon = new ImageIcon(musique.artwork.getBinaryData());
                Image resizedImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
                imageLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Utilisation de JPanel pour organiser les informations cote a cote 
                JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));    	

                // Labels pour les informations de la musique
                JLabel titleLabel = new JLabel("Title: " + musique.titre);
                JLabel artistLabel = new JLabel("Artist: " + musique.artist);
                JLabel albumLabel = new JLabel("Album: " + musique.album);
                
                // Bouton "like" (favori)
                JButton favButton = new JButton("❤️");
                favButton.setSize(50, 50); // Définir une taille plus petite si nécessaire

                // Mettre le bouton en rouge si la musique est en favori
                if (musique.aimer) {
                    favButton.setForeground(Color.RED);
                } else {
                    favButton.setForeground(Color.WHITE);
                }
                favButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Inverser l'état de la musique comme favori
                        musique.aimer = !musique.aimer;
                        m.modifier_list_musique_aimer(musique);

                        // Changer la couleur du bouton cœur en fonction de l'état de la musique
                        if (musique.aimer) {
                            favButton.setForeground(Color.RED); // Bouton rouge si la musique est en favori
                        } else {
                            favButton.setForeground(Color.WHITE); // Bouton blanc si la musique n'est pas en favori
                        }
                    }
                });
             // Ajout de bordures entre les labels (utilisation de EmptyBorder)
                int borderSize = 5; // Taille de la bordure
                titleLabel.setBorder(BorderFactory.createEmptyBorder(borderSize*10, borderSize, 0, borderSize)); // Bordure à droite du titre
                artistLabel.setBorder(BorderFactory.createEmptyBorder(borderSize*10, borderSize, 0, borderSize)); // Bordure à gauche et droite de l'artiste
                albumLabel.setBorder(BorderFactory.createEmptyBorder(borderSize*10, borderSize, 0, 0)); // Bordure à gauche de l'album
                // Ajouter les composants au panel d'informations
                infoPanel.add(titleLabel, BorderLayout.WEST);
                infoPanel.add(artistLabel, BorderLayout.CENTER);
                infoPanel.add(albumLabel, BorderLayout.EAST);
                
                // Utilisation d'un layout flexible pour organiser l'image et les informations
                JPanel itemPanel = new JPanel(new BorderLayout());
                itemPanel.add(imageLabel, BorderLayout.WEST);
                itemPanel.add(infoPanel, BorderLayout.CENTER);
                
                itemPanel.add(favButton, BorderLayout.EAST);

                // Ajouter le panel d'élément à votre panel principal
                panel.add(itemPanel);
            }
        }

        // Rafraîchir et redessiner le panel principal
        panel.revalidate();
        panel.repaint();
    }
    private void performSearch() {
        String searchTerm = searchField.getText();
        if (!searchTerm.isEmpty()) {
            afficherMusiques(musicPanel, play_list, imageSize, searchTerm);
        } else {
            afficherMusiques(musicPanel, play_list, imageSize, "");
        }
    }
    
}


