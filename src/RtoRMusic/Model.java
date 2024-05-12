package RtoRMusic;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.KeyStore.Entry;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

public class Model {
	TreeMap<String, Musique> play_list = new TreeMap<>();
	TreeMap<String, TreeSet<String>> listeAlbumParArtiste = new TreeMap<>();
	TreeMap<String, TreeSet<Musique>> listeMusiqueParAlbum = new TreeMap<>();
	TreeMap<String, Musique> musique_aimer = new TreeMap<>(); // contient l'ensemble des musiques aimer par
																// l'utilisateur
	String utilisateur = "Invite"; // utilisateur par defaut
	String mot_de_passe = "Invite";// mot de passe de l'utilisateur

	public static Musique creerMusique(String nomFichier) {
		return new Musique(nomFichier);
	}

	private static boolean isAudioFile(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
		return extension.equals("mp3") || extension.equals("wav") || extension.equals("flac")
				|| extension.equals("ogg");
	}

	public TreeMap<String, Musique> construire_play_list(String folderPath) {

		play_list = new TreeMap<String, Musique>(); // créer une play_list vide
		listeAlbumParArtiste = new TreeMap<String, TreeSet<String>>();
		listeMusiqueParAlbum = new TreeMap<String, TreeSet<Musique>>();

		File folder = new File(folderPath);// récupère les fichiers
		File[] files = folder.listFiles();

		if (files != null) {
			for (File file : files) {// parcour l'enssemble des fichier du dossier
				if (file.isFile() && isAudioFile(file.getName())) {
					try {
						Musique musique = new Musique(file.getName());
						play_list.put(normalisation_text(musique.titre + musique.artist.split("/")[0]), musique);
						System.out.println(file);
						if (file.getName().compareTo(
								normalisation_text(musique.titre + musique.artist.split("/")[0]) + ".mp3") != 0) {

							file.renameTo(new File("Music/"
									+ normalisation_text(musique.titre + musique.artist.split("/")[0]) + ".mp3"));
						}

						for (String artist : musique.artist.split("/")) {

							if (listeAlbumParArtiste.containsKey(artist)) {
								if (!(listeAlbumParArtiste.get(artist).contains(musique.album))) {
									listeAlbumParArtiste.get(artist).add(musique.album);
								}

							} else {

								TreeSet<String> listeAlbum = new TreeSet<>();
								listeAlbum.add(musique.album);
								listeAlbumParArtiste.put(artist, listeAlbum);

							}

						}

						if (listeMusiqueParAlbum.containsKey(musique.album)
								&& (listeMusiqueParAlbum.get(musique.album).last() != musique)) {
							listeMusiqueParAlbum.get(musique.album).add(musique);
						} else if ((listeMusiqueParAlbum.containsValue(musique)) == false) {

							TreeSet<Musique> listeMusique = new TreeSet<>();
							listeMusique.add(musique);
							listeMusiqueParAlbum.put(musique.album, listeMusique);

						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			try {
				if (utilisateur.compareTo("Invite") != 0) {
					/* Permet de récupéré la liste des musique aimes par l'utilisateur */
					// Création d'un fileReader pour lire le fichier

					FileReader fileReader = new FileReader("Utilisateur/" + utilisateur);

					// Création d'un bufferedReader qui utilise le fileReader
					BufferedReader reader = new BufferedReader(fileReader);

					// une fonction à essayer pouvant générer une erreur
					mot_de_passe = reader.readLine();// la première ligne contient le mot de passe
					String line = reader.readLine();

					while ((line != null) && (line.length() >= 1)) {
						System.out.println(line);

						play_list.get(line).aimer = true;
						musique_aimer.put(line, play_list.get(line));
						// affichage de la ligne
						// lecture de la prochaine ligne
						line = reader.readLine();
					}
					reader.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return play_list;
	}

	

	

	public TreeMap<String, Musique> Recherche(String mot_clef) {
		// recherche les musique en fonciton des différent mot clef envoyer
		// mot clef string, les espaces sépare les différents mot clef
		TreeMap<String, Musique> new_play_list = new TreeMap<String, Musique>();
		mot_clef = mot_clef.toUpperCase();
		for (Map.Entry<String, Musique> entry : play_list.entrySet()) {
			Musique musique = entry.getValue();
			if ((musique.titre.toUpperCase().contains(mot_clef)) || (musique.album.toUpperCase().contains(mot_clef))
					|| (musique.annee.toUpperCase().contains(mot_clef))
					|| (musique.artist.toUpperCase().contains(mot_clef))) {
				new_play_list.put(musique.titre, musique);

			}

		}
		return new_play_list;
	}

	public TreeMap<String, Musique> RechercheRecommendation() {

		// recherche les musique en fonciton des différent mot clef envoyer
		// mot clef string, les espaces sépare les différents mot clef
		TreeMap<String, Musique> new_play_list = new TreeMap<String, Musique>();
		String[] mot_clef = recommander().split(",");
		for (java.util.Map.Entry<String, Musique> musique : play_list.entrySet()) {
			if (!(musique_aimer.containsKey(musique.getKey()))) {
				for (String clef : mot_clef) {
					for (String artiste : musique.getValue().artist.split("/"))
						if (artiste.compareTo(clef) == 0) {

							new_play_list.put(musique.getKey(), musique.getValue());

						}
				}
			}

		}

		return new_play_list;
	}

	private void afficherResultatsRecherche(TreeMap<String, Musique> searchResults) {
		JPanel musicPanel = new JPanel(new GridLayout(0, 4, 10, 10)); // Utilisez le même layout que votre affichage
																		// principal
		Vue vue = new Vue();
		vue.afficherMusiques(musicPanel, searchResults, 0, ""); // Utilisez la méthode d'affichage de la classe Vue
		JOptionPane.showMessageDialog(null, new JScrollPane(musicPanel), "Résultats de la recherche",
				JOptionPane.PLAIN_MESSAGE);
	}

	public void rechercherMusique(String mot_clef) {
		TreeMap<String, Musique> searchResults = Recherche(mot_clef);
		if (!searchResults.isEmpty()) {
			afficherResultatsRecherche(searchResults);
		} else {
			JOptionPane.showMessageDialog(null, "Nous sommes désolés, nous n'avons pas trouvé ce que vous cherchez 😭 ",
					"Résultat", JOptionPane.PLAIN_MESSAGE);
		}
	}

	public String recommander() {
		if (utilisateur.compareTo("Invite") != 0) {

			/*
			 * Permet de récupéré les 5 artistes préféré d'un utilisateur en fonction de ses
			 * titre aimer
			 */

			String recomandation = ""; // variable de fin qui indiquera les préférences de l'utilisateur
			TreeMap<String, Integer> tags_aimer = new TreeMap<>();// contiendra les différente catégorie aimer

			for (String musique_id : musique_aimer.keySet()) {// récupère tout les titres aimer et les comptes

				Musique musique = play_list.get(musique_id);
				for (String artist : musique.artist.split("/")) {
					if (tags_aimer.containsKey(artist)) {
						tags_aimer.replace(artist, tags_aimer.get(artist) + 1);

					} else {
						tags_aimer.put(artist, 1);
					}
				}

			}

			TreeSet<couple_trie_aimer> list = new TreeSet<couple_trie_aimer>();// trie chaqu'une des valeur pour trouver
																				// les
																				// meilleurs
			for (java.util.Map.Entry<String, Integer> values : tags_aimer.entrySet()) {

				list.add(new couple_trie_aimer(values.getKey(), values.getValue()));

			}

			int i = 0;
			while ((i < 5) && (0 < list.size())) {

				recomandation += list.pollFirst() + ",";
				i += 1;
			}

			return recomandation;
		}
		return "";
	}

	public void modifier_list_musique_aimer(Musique musique) {
		/*
		 * Permet de modifier la list : musique_aimer en fonction de la musique envoyer
		 */
		if (musique.aimer) {
			musique_aimer.put(normalisation_text(musique.titre + musique.artist.split("/")[0]), musique);
		} else {
			musique_aimer.remove(normalisation_text(musique.titre + musique.artist.split("/")[0]));
		}
		try {
			/* modifie le fichier de l'utilisateur */
			PrintWriter writer = new PrintWriter("Utilisateur/" + utilisateur, "UTF-8");
			writer.println(mot_de_passe);
			for (String clef : musique_aimer.keySet()) {
				writer.println(clef);
			}

			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String normalisation_text(String mot) {
		/*
		 * Permet de transformer un titre de munique avec des caractère spétiaux en
		 * titre de musique normal
		 */

		String nmot = "";
		for (char letre : mot.toCharArray()) {
			nmot += convert(letre);

		}
		return nmot;

	}

	public static char convert(char c) {
		/* Permet de convertir un caractère spétiaux en caractère normal */
		String s = c + "";

		if (s.compareTo("’") == 0) {
			return ' ';
		}

		if (c >= 97 && c <= 122) {
			return c;
		} else if (c >= 65 && c <= 90) {
			return c;
		} else if (c >= 43 && c <= 57) {
			return c;
		} else if (c == 32 || c == 39 || c == 40 || c == 41 || c == 58 || c == 63) {
			return c;
		} else {
			switch (c) {
			case 'À':
			case 'Á':
			case 'Â':
				return 'A';
			case 'Ç':
				return 'C';
			case 'È':
			case 'É':
			case 'Ê':
			case 'Ë':
				return 'E';
			case 'â':
			case 'à':
			case 'ä':
				return 'a';
			case 'ç':
				return 'c';
			case 'ê':
			case 'è':
			case 'é':
			case 'ë':
				return 'e';
			case 'ï':
			case 'î':
				return 'i';
			case 'ò':
			case 'ó':
			case 'ô':
			case 'ö':
				return 'o';
			case 'û':
			case 'ü':
			case 'ù':
				return 'u';
			case '’':
				return ' ';
			default:
				return '?';
			}
		}
	}

	public void racourcir_les_musiques(String folderPath) {

		File folder = new File(folderPath);// récupère les fichiers
		File[] files = folder.listFiles();

		if (files != null) {
			for (File file : files) {// parcour l'enssemble des fichier du dossier
				if (file.isFile()) {
					try {

						File targetFile = new File("Neutre.mp3");

						try {
							System.out.println(file);
							// Chargement des tags du fichier source
							AudioFile sourceAudioFile = AudioFileIO.read(file);
							org.jaudiotagger.tag.Tag sourceTag = sourceAudioFile.getTag();

							// Création d'un nouveau fichier cible avec les mêmes données audio que le
							// fichier source
							AudioFile targetAudioFile = AudioFileIO.read(targetFile);
							targetAudioFile.setTag((org.jaudiotagger.tag.Tag) sourceTag);

							// Sauvegarde des modifications sur le fichier cible
							targetAudioFile.commit();

							File destinationDirectory = new File("Music/");
							Path destinationPath = destinationDirectory.toPath().resolve(file.getName());

							Files.copy(targetFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

							System.out.println("Transfert des tags terminé avec succès !");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// Chemin des fichiers source et cible

		}
	}

	public void changement_d_utilisateur() throws IOException {
		for (String musique : play_list.keySet()) {
			play_list.get(musique).aimer = false;
		}

		if (utilisateur.compareTo("Invite") != 0) {
			/* Permet de récupéré la liste des musique aimes par l'utilisateur */
			// Création d'un fileReader pour lire le fichier

			FileReader fileReader;
		
			fileReader = new FileReader("Utilisateur/" + utilisateur);
			// Création d'un bufferedReader qui utilise le fileReader
			BufferedReader reader = new BufferedReader(fileReader);

			// une fonction à essayer pouvant générer une erreur
			mot_de_passe = reader.readLine();// la première ligne contient le mot de passe
			String line = reader.readLine();
			musique_aimer = new TreeMap<>();
			while ((line != null) && (line.length() >= 1)) {
				System.out.println(line);

				play_list.get(line).aimer = true;
				musique_aimer.put(line, play_list.get(line));
				// affichage de la ligne
				// lecture de la prochaine ligne
				line = reader.readLine();
			}
			reader.close();
		}
	}

}
