package RtoRMusic;

public class NoeudMusique {
    public Musique musique;
    public NoeudMusique suivant;

    public NoeudMusique(Musique musique) {
        this.musique = musique;
        this.suivant = null;
    }
}
