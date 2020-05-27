package document;

/**
 * A class that represents a text document
 * It does one pass through the document to count the number of syllables, words,
 * and sentences and then stores those values.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Document {

	private String text;
	private int numWords;  // The number of words in the document
	private int numSentences;  // The number of sentences in the document
	private int numSyllables;  // The number of syllables in the document
	
	/** Create a new document from the given text.
	 * @param text The text of the document.
	 */
	public Document(String text)
	{
		this.text = text;
		processText();
	}

	/** Returns the tokens that match the regex pattern from the document 
	 * text string.
	 * @param pattern A regular expression string specifying the 
	 *   token pattern desired
	 * @return A List of tokens from the document text that match the regex 
	 *   pattern
	 */
	protected List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}

	/**
	 * Take a string that either contains only alphabetic characters,
	 * or only sentence-ending punctuation.  Return true if the string
	 * contains only alphabetic characters, and false if it contains
	 * end of sentence punctuation.
	 *
	 * @param tok The string to check
	 * @return true if tok is a word, false if it is punctuation.
	 */
	private boolean isWord(String tok)
	{
		// Note: This is a fast way of checking whether a string is a word
		// You probably don't want to change it.
		return !(tok.indexOf("!") >=0 || tok.indexOf(".") >=0 || tok.indexOf("?")>=0);
	}


	/** Passes through the text one time to count the number of words, syllables
	 * and sentences, and set the member variables appropriately.
	 * Words, sentences and syllables are defined as described below.
	 */
	private void processText()
	{
		// Call getTokens on the text to preserve separate strings that are
		// either words or sentence-ending punctuation.  Ignore everything
		// That is not a word or a sentence-ending puctuation.
		numWords = 0;
		numSentences = 0;
		numSyllables = 0;
		List<String> tokens = getTokens("[!?.]+|[a-zA-Z]+");
		for( String word: tokens) {
			//System.out.println("count of Syllables - "+ a);
			if(isWord(word)) {
				numWords++;
				numSyllables += countSyllables(word);
			}
			else {
				numSentences++;
			}
		}
		if(tokens.size()>0) {
			//System.out.println("token last - "+ tokens.get(tokens.size()-1));
			if(isWord(tokens.get(tokens.size()-1))){
				numSentences++;
			}
		}
	}

	// This is a helper function that returns the number of syllables
	// in a word.  You should write this and use it in your 
	// BasicDocument class.
	protected static int countSyllables(String word)
	{
	    //System.out.print("Counting syllables in " + word + "...");
		int numSyllables = 0;
		boolean newSyllable = true;
		String vowels = "aeiouy";
		char[] cArray = word.toCharArray();
		for (int i = 0; i < cArray.length; i++)
		{
		    if (i == cArray.length-1 && Character.toLowerCase(cArray[i]) == 'e' 
		    		&& newSyllable && numSyllables > 0) {
                numSyllables--;
            }
		    if (newSyllable && vowels.indexOf(Character.toLowerCase(cArray[i])) >= 0) {
				newSyllable = false;
				numSyllables++;
			}
			else if (vowels.indexOf(Character.toLowerCase(cArray[i])) < 0) {
				newSyllable = true;
			}
		}
		//System.out.println( "found " + numSyllables);
		return numSyllables;
	}
	
	/** A method for testing
	 * 
	 * @param doc The Document object to test
	 * @param syllables The expected number of syllables
	 * @param words The expected number of words
	 * @param sentences The expected number of sentences
	 * @return true if the test case passed.  False otherwise.
	 */
	public static boolean testCase(Document doc, int syllables, int words, int sentences)
	{
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound 
					+ ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound 
					+ ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound 
					+ ", expected " + sentences);
			passed = false;
		}
		
		if (passed) {
			System.out.println("passed.\n");
		}
		else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}
	
	
	/** Return the number of words in this document */
	public int getNumWords() {
		return numWords;
	}
	
	/** Return the number of sentences in this document */
	public int getNumSentences() {
		return numSentences;
	}
	
	/** Return the number of syllables in this document */
	public int getNumSyllables() {
		return numSyllables;
	}
	
	/** Return the entire text of this document */
	public String getText()
	{
		return this.text;
	}
	
	/** return the Flesch readability score of this document */
	public double getFleschScore()
	{
		double wordCount = (double)getNumWords();
		return 206.835 - (1.015 * ((wordCount)/getNumSentences())) 
				- (84.6 * (((double)getNumSyllables())/wordCount));
	
	}
	
	
	
}
