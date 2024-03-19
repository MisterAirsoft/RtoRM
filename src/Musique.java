import java.io.File;
import java.io.FileOutputStream;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.ID3v23Tag;

public class Musique {
	public String source;
	public String titre;
	public String artist;
	public String album;
	public String genre;
	public String annee; 
	public Artwork artwork; //artwork.getBinaryData() pour récupérer l'image
	public String autre;
	public String langue;
	public String commentaire;
	public int nb_d_Ecoute;
	public boolean aimer=false;

	public Musique(String fichier) {
		File audioFile = new File("Music/"+fichier);

		try {
			AudioFile file = AudioFileIO.read(audioFile);
			ID3v23Tag tag = (ID3v23Tag) file.getTag();

			source = fichier;
			titre = tag.getFirst(FieldKey.TITLE);
			artist = tag.getFirst(FieldKey.ARTIST);
			album = tag.getFirst(FieldKey.ALBUM);
			genre = tag.getFirst(FieldKey.GENRE);
			annee = tag.getFirst(FieldKey.YEAR);
			artwork = (Artwork) tag.getFirstArtwork();
			langue = annee = tag.getFirst(FieldKey.LANGUAGE);
			commentaire = annee = tag.getFirst(FieldKey.COMMENT);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		String affichage ="----------------------------------- \n";
		affichage +="Titre : " + titre +"\n";
		affichage +="Artiste : " + artist+"\n";
		affichage +="Album : " + album+"\n";
		affichage +="Genre : " + genre+"\n";
		affichage +="Année : " + annee+"\n";
		affichage +="image : " +artwork +"\n";
		affichage +="langue : " + langue+"\n";
		affichage +="commentaire : " + commentaire+"\n";
		
		return  affichage;
	}

}
