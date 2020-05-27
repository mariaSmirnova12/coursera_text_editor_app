package application;

import document.Document;
import spelling.*;
import textgen.MarkovTextGenerator;
import textgen.MarkovTextGeneratorLoL;
import java.util.Random;

public class LaunchClass {
	
	public String dictFile = "data/dict.txt";

	public LaunchClass() {
		super();
	}
	
	public Document getDocument(String text) {
		return new Document(text);
	}
	
	public MarkovTextGenerator getMTG() {
		return new MarkovTextGeneratorLoL(new Random());
	}
	
	public WordPath getWordPath() {
		return new WPTree();
	}
	
    public AutoComplete getAutoComplete() {
        AutoCompleteDictionaryTrie tr = new spelling.AutoCompleteDictionaryTrie();
		DictionaryLoader loader = new DictionaryLoader();
		loader.loadFromFileToDictionary(tr, dictFile);
        return tr;
    }

   	public Dictionary getDictionary() {
		Dictionary d = new DictionaryLL();

		DictionaryLoader loader = new DictionaryLoader();
		loader.loadFromFileToDictionary(d, dictFile);
		return d;

	}
    
    public SpellingSuggest getSpellingSuggest(Dictionary dic) {
    	//return new spelling.SpellingSuggestNW(new spelling.NearbyWords(dic));
    	return new NearbyWords(dic);
    
    }
}
