package RtoRMusic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Fenetre_Utilisateur extends JFrame {
	public TreeMap<String,Musique> play_list 	;
	public JPanel musicPanel ;
	public int imageSize ;
	public JLabel imageLabel;
	public  Model m;
	public  Vue v;
	
	public Fenetre_Utilisateur(TreeMap<String, Musique> play_list, JPanel musicPanel, int imageSize, Model m, Vue v)
			throws HeadlessException {
		
		this.play_list = play_list;
		this.musicPanel = musicPanel;
		this.imageSize = imageSize;
		this.m = m;
		this.v = v;
		
		ImageIcon imageIcon = new ImageIcon("profil.png");
		Image resizedImage = imageIcon.getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH);
		imageLabel = new JLabel(new ImageIcon(resizedImage));
	}






	public void DeConnectionUtilisateur_Fenetre() {
		
		Font titleFont = new Font("Helvetica Neue Light", Font.ITALIC, 10);
		Font titleFont1 = new Font("Helvetica Neue Light", Font.BOLD, 20);
		
		JFrame connection_utilisateur = new JFrame("Utilisateur ");
		

		// Ajouter un gestionnaire d'événements WindowListener pour détecter la
		// fermeture de la fenêtre
		connection_utilisateur.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Fermer la JFrame actuelle
				connection_utilisateur.dispose();
			}
		});
		
		
		

		// Créer les composants
		JLabel text1 = new JLabel("Nombre de musique aimée : "+ m.musique_aimer.size() );
		text1.setForeground(Color.white);
		text1.setFont(titleFont1);
		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setBackground(Color.DARK_GRAY);
		
		
		String[] artist_aimer = m.recommander().split(",");
		
		JLabel text_artiste = new JLabel("Artiste préféré :" );
		text_artiste.setForeground(Color.white);
		text_artiste.setFont(titleFont1);
		
		JLabel text_artiste_n1 = new JLabel(" ");
		text_artiste_n1.setForeground(Color.white);
		JLabel text_artiste_n2 = new JLabel(" " );
		text_artiste_n2.setForeground(Color.white);
		JLabel text_artiste_n3 = new JLabel(" " );
		text_artiste_n3.setForeground(Color.white);
		
		JLabel text_artiste_n4 = new JLabel(" " );
		text_artiste_n4.setForeground(Color.white);
		JLabel text_artiste_n5 = new JLabel(" " );
		text_artiste_n5.setForeground(Color.white);
		
		try { 
			text_artiste_n1.setText(artist_aimer[0]);
		}
		catch (Exception e) {
			
		}
		try { 
			text_artiste_n2.setText(artist_aimer[1]);
		}
		catch (Exception e) {
			
		}
		try { 
			text_artiste_n3.setText(artist_aimer[2]);
		}
		catch (Exception e) {
			
		}
		try { 
			text_artiste_n4.setText(artist_aimer[3]);
		}
		catch (Exception e) {
			
		}
		try { 
			text_artiste_n5.setText(artist_aimer[4]);
		}
		catch (Exception e) {
			
		}
		
	
		
		
		JPanel sous_panel = new JPanel(new GridLayout(0, 1));
		sous_panel.add(text1, BorderLayout.NORTH);
		
		sous_panel.setBackground(Color.DARK_GRAY);
		sous_panel.add(text_artiste);
		sous_panel.add(new JLabel(" "));
		sous_panel.add(text_artiste_n1);
		sous_panel.add(text_artiste_n2);
		sous_panel.add(text_artiste_n3);
		sous_panel.add(text_artiste_n4);
		sous_panel.add(text_artiste_n5);
		
		
	
		
		
		panel.add(text1, BorderLayout.NORTH);
		
		panel.add(sous_panel, BorderLayout.SOUTH);
		
		
		
		JLabel text = new JLabel(m.utilisateur);
		text.setForeground(Color.white);
		text.setFont(titleFont1);
		JPanel panel2 = new JPanel(new GridLayout(1, 2));
		panel2.add(imageLabel, BorderLayout.EAST);
		panel2.add(text, BorderLayout.WEST);
		
		panel2.setBackground(getForeground());
		
	
		panel2.setFont(titleFont1);
		// Créer un bouton de connexion
		JButton loginButton = new JButton("Se deconnecter");
		loginButton.setBackground(getForeground());
		loginButton.setFont(titleFont1);
		loginButton.setForeground(Color.white);
		loginButton.addActionListener(e -> {
			m.utilisateur = "Invite";
			try {
				m.changement_d_utilisateur();
				v.userButton.setText("👤 "+m.utilisateur);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			v.afficherMusiques(musicPanel, play_list, imageSize, "");
			connection_utilisateur.dispose();
		});

		// Ajouter les composants à la fenêtre
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.darkGray); // Définir la couleur de fond du mainPanel
		
		mainPanel.add(panel2, BorderLayout.NORTH);
		mainPanel.add(panel, BorderLayout.CENTER);
		
		mainPanel.add(loginButton, BorderLayout.SOUTH);
		
		connection_utilisateur.getContentPane().add(mainPanel);
		connection_utilisateur.pack();
		connection_utilisateur.setSize(450,500);
		connection_utilisateur.setLocationRelativeTo(null);
		
		connection_utilisateur.setVisible(true);

	}

   

  



	
	public void ConnectionUtilisateur_Fenetre() {
		JFrame connection_utilisateur = new JFrame("Utilisateur ");

		// Ajouter un gestionnaire d'événements WindowListener pour détecter la
		// fermeture de la fenêtre
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.BLACK); // Définir la couleur de fond du mainPanel

		// Créer les composants
		JTextField usernameField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField(20);
		JLabel text = new JLabel("Conectez vous ou creez un compte");

		// Créer un panneau pour les composants
		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.add(new JLabel("Nom d'utilisateur:"));
		panel.add(usernameField);
		panel.add(new JLabel("Mot de passe:"));
		panel.add(passwordField);
		JPanel panel2 = new JPanel(new GridLayout(1, 1));
		panel2.add(text, BorderLayout.CENTER);

		// Créer un bouton de connexion
		JButton loginButton = new JButton("Se connecter");
		loginButton.addActionListener(e -> {
			// Récupérer le nom d'utilisateur et le mot de passe
			String username = usernameField.getText();
			String password = new String(passwordField.getPassword());

			File fichier = new File("Utilisateur", username);

			// Vérifier si le fichier existe dans le dossier
			if (fichier.exists()) {
				FileReader fileReader;
				try {
					fileReader = new FileReader("Utilisateur/" + username);

					// Création d'un bufferedReader qui utilise le fileReader
					BufferedReader reader = new BufferedReader(fileReader);

					String mot_de_passe = reader.readLine();// la première ligne contient le mot de passe
					if (mot_de_passe.compareTo(password) == 0) {
						m.utilisateur = username;
						m.mot_de_passe = mot_de_passe;
						v.userButton.setText("👤 "+m.utilisateur);

						m.changement_d_utilisateur();
						SwingUtilities.invokeLater(() -> {
							text.setText("Connection Réussie");
							// Forcer la répétition du rendu pour actualiser l'affichage
							connection_utilisateur.revalidate();
						});

						v.afficherMusiques(musicPanel, play_list, imageSize, "");

						connection_utilisateur.dispose();

					} else {
						text.setText("Mauvais de mot de passe");

					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				text.setText("Ce compte n'existe pas");
				


			} });
			JButton creer_compte= new JButton("Creer un compte");
			creer_compte.addActionListener(e -> {
				// Récupérer le nom d'utilisateur et le mot de passe
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());

				File fichier = new File("Utilisateur", username);

				// Vérifier si le fichier existe dans le dossier
				if (fichier.exists()) {
					text.setText("Ce nom d'utilisateur existe déja");
					
				} else {
					m.utilisateur = username;
					m.mot_de_passe = password;
					v.userButton.setText("👤 "+m.utilisateur);

					/* modifie le fichier de l'utilisateur */
					PrintWriter writer;
					try {
						writer = new PrintWriter("Utilisateur/" + username, "UTF-8");
						writer.println(password);
						writer.close();
						m.changement_d_utilisateur();
						text.setText("Nouvelle Utilisateur creer");
						connection_utilisateur.repaint();

						v.afficherMusiques(musicPanel, play_list, imageSize, "");

						connection_utilisateur.dispose();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			// Ici, vous pouvez mettre la logique de vérification du nom d'utilisateur et du
			// mot de passe
			// Par exemple, vérification dans une base de données

			// Pour l'exemple, affichons simplement les informations saisies
			System.out.println("Nom d'utilisateur: " + username);
			System.out.println("Mot de passe: " + password);
		});

			
		JPanel  les_boutons = new JPanel(new GridLayout(2, 1));
		les_boutons.add(creer_compte, BorderLayout.EAST);
		les_boutons.add(loginButton, BorderLayout.EAST);
		// Ajouter les composants à la fenêtre
		connection_utilisateur.getContentPane().add(panel, BorderLayout.CENTER);
		connection_utilisateur.getContentPane().add(panel2, BorderLayout.NORTH);
		connection_utilisateur.getContentPane().add(les_boutons, BorderLayout.SOUTH);

		connection_utilisateur.pack();
		
		connection_utilisateur.setLocationRelativeTo(null);
		connection_utilisateur.setVisible(true);

	}


}

