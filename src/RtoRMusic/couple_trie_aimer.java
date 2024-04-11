package RtoRMusic;

public class couple_trie_aimer implements Comparable<couple_trie_aimer> {
	String tag;
	Integer quantité;
	public couple_trie_aimer(String tag, int q) {
		
		this.tag = tag;
		this.quantité =q;
	}
	
	

	@Override
	public int compareTo(couple_trie_aimer o) {
		// TODO Auto-generated method stub
		return (o.quantité-this.quantité)*10+this.tag.compareTo(o.tag);
	}
	public String toString() {
		return tag;
	}
	

}
