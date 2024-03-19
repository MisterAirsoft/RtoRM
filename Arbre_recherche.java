
class Noeud {
	public Musique musique;
    public  Noeud gauche, droit;

    public Noeud(Musique musique) {
        this.musique = musique;
  
    }
    
    public String toString() {
    	String textgauche="";
    	String textdroit="";
    	if (gauche !=null) {
    		textgauche=gauche.toString()+",";
    	}
    	if (droit !=null) {
    		textdroit=","+droit.toString();
    	}
    	return textgauche+musique.titre+textdroit;
    	
    }
}

class Arbre_recherche {
    public Noeud racine;
    
    public void insertion(Musique musique) {
    	if (racine==null) {
    		racine= new Noeud(musique);
    	}
    	else {
    		insertion(racine, musique);
    	}
    }
    public void  insertion(Noeud noeud, Musique new_musique) {
    	if (noeud.musique.titre.compareTo(new_musique.titre)>0) {
    		if (noeud.gauche==null) {
    			noeud.gauche= new Noeud(new_musique);
    		}
    		else {
    			insertion(noeud.gauche, new_musique);
    		}
    		
    	}
    	else {
       		if (noeud.droit==null) {
    			noeud.droit= new Noeud(new_musique);
    		}
    		else {
    			insertion(noeud.droit, new_musique);
    		}
    		
    	}
 
    }

    int hauteur(Noeud noeud) {
        if (noeud == null)
            return 0;
        return max(hauteur(noeud.gauche),hauteur(noeud.droit))+1;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

       
    public String toString() {
    	return racine.toString();
    	
    }

    
}