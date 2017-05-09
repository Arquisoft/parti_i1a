package uniovi.asw.persistence.model.types;

import uniovi.asw.io.FileManagement;

import java.util.Set;

public class NotAllowedWords {

	private static NotAllowedWords instance = null;
	String filename = "src/main/resources/words/Not-allowed-words.txt";
	Set<String> words;
	
	private NotAllowedWords(){
		initialize();
	}
	
	private void initialize() {
		FileManagement fm = new FileManagement();
		words = fm.getNotAllowedWords(filename);
	}
	
	public Set<String> getSet(){
		return words;
	}

	public static NotAllowedWords getInstance(){
		if(instance == null)
			instance = new NotAllowedWords();
		return instance;
	}
	
	
	
	
}
