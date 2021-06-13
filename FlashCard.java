package ProjectFlashcards;

public class FlashCard {
	
	private String word;
	private String definition;
	
	public FlashCard(String w, String d) {
		word = w;
		definition = d;
		
	}
	
	//Getters and Setters
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
}
