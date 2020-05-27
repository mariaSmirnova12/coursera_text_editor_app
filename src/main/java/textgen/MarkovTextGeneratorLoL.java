package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		String[] words = sourceText.split(" +");
		String prevWord;

		if(words.length == 1) {
			starter = words[0];
			ListNode node = new ListNode(words[0]);
			node.addNextWord(words[0]);
			wordList.add(node);
		}
		if(words.length > 1) {
			starter = words[0];
			//prevWord = starter;
			String addWord = words[1];
			
			for(int i = 1; i < words.length+1; i++) {
				//System.out.println("word: "+words[i]);
				prevWord = words[i-1];
				if(i < words.length) {
					addWord = words[i];
				}
				else {
					addWord = starter;
				}
				boolean isExist = false;
				for(ListNode word: wordList) {
					if(word.getWord().equals(prevWord)){//node already exist
						word.addNextWord(addWord);
						isExist = true;
						break;
					}
				}
				if(!isExist) {
					//not yet such node
					ListNode node = new ListNode(prevWord);
					node.addNextWord(addWord);
					wordList.add(node);
				}
			}
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		String currword = starter;
		StringBuilder output = new StringBuilder();
		//ArrayList<String> output = new ArrayList<>();
		if(numWords <= 0) return "";
		//output.add(currword+" ");
		output.append(currword);
		//output.add(" ");
		for(int i = 1; i < numWords; i++) {
			for(ListNode word: wordList) {
				if(word.getWord().equals(currword)){
					currword = word.getRandomNextWord(rnGenerator);
					output.append(" "+currword);
					//output.add(currword+" ");
					//output.add(" ");
					break;
				}
			}
		}
		//String res = output.toString();
		//res = res.substring(0, res.length()-1);
		//System.out.println("result string: "+res);
		return output.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		wordList.clear();
		starter = "";
 	    train(sourceText);
	}

	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		//String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		//System.out.println(textString);
		//gen.train("");
		String textString = "1 2 3";
		gen.train(textString);
		//gen.toString();
		System.out.println(gen);
		System.out.println("end");
		String text = gen.generateText(3);
		System.out.println(text);
		gen.retrain(text);
		System.out.println(gen);
		System.out.println(gen.generateText(4));
		//System.out.println(gen.generateText(20));
		//System.out.println(gen);
		//System.out.println(gen.generateText(20));
		/*String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));*/
	}

}



