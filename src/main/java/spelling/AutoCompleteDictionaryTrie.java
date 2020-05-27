package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedList;

public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
		if(word.equals("") || word == null) return false;
		String newWord = word.toLowerCase();
		if(root.getText().equals(newWord)) return false;
		TrieNode tmpNode = root;
		char symbolLast = ' ';
		for(char symbol: newWord.toCharArray()) {
			TrieNode res = tmpNode.getChild(symbol);
			if (res == null) {// no link to child
				tmpNode = tmpNode.insert(symbol);
				//Set<Character> res1 = tmpNode.getValidNextCharacters();
			}
			else {
				tmpNode = res;
				//Set<Character> res1 = tmpNode.getValidNextCharacters();
			}
			symbolLast = symbol;
		}
		TrieNode tmpNodeChild = tmpNode.getChild(symbolLast);
		//Set<Character> res1 = tmpNode.getValidNextCharacters();
		
		if(tmpNodeChild == null) {
			tmpNode.insert(symbolLast);
			tmpNode = tmpNode.getChild(symbolLast);
			tmpNode.setEndsWord(true);
			size++;
			return true;
		}
	    return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
		int wordSize = 0;
  		TrieNode tmpNode = root;
 		Queue <TrieNode> q = new LinkedList <TrieNode>();
 		q.add(tmpNode);
 		while(!q.isEmpty()) {
 			TrieNode el = q.remove();
 			if(el.endsWord()) wordSize++;
 			Set<Character> arrayChars = el.getValidNextCharacters();
 			for(char symbol: arrayChars) {
 				TrieNode tmp = el.getChild(symbol);
 				if(tmp != null) {
 					q.add(tmp);
 				}
 			}
 		}
	    return wordSize;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
		if(s.equals("") || s == null) return false;
		String newWord = s.toLowerCase();
		TrieNode tmpNode = root;
		char symbolLast = ' ';
		for(char symbol: newWord.toCharArray()) {
			
			TrieNode res = tmpNode.getChild(symbol);
			if (res == null) {// no link to child
				return false;
			}
			else {
				tmpNode = res;
			}
			symbolLast = symbol;
		}
		tmpNode = tmpNode.getChild(symbolLast);
		if(tmpNode == null || !tmpNode.endsWord()) {
			return false;
		}
			return true;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 List<String> result = new ArrayList<String>();
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 if(numCompletions <= 0) return result;
    	// if(prefix.equals("") || prefix == null || numCompletions <= 0) return result;
 		String pref = prefix.toLowerCase();
 		//if(root.getText().equals(pref)) return false;
 		TrieNode tmpNode = root;
 		char symbolLast = ' ';
 		for(char symbol: pref.toCharArray()) {
 			TrieNode res = tmpNode.getChild(symbol);
			if (res == null) { // no link to child
				return result;
			}
			else {
				tmpNode = res;
				//Set<Character> res1 = tmpNode.getValidNextCharacters();
			}
			symbolLast = symbol;
 		}
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
 		
 		Queue <TrieNode> q = new LinkedList <TrieNode>();
 		q.add(tmpNode);
 		while(!q.isEmpty() && result.size() < numCompletions) {
 			TrieNode el = q.remove();
 			if(isWord(el.getText())) {
 				result.add(el.getText());
 				System.out.println("word - "+ el.getText());
 			}
 			Set<Character> arrayChars = el.getValidNextCharacters();
 			for(char symbol: arrayChars) {
 				TrieNode tmp = el.getChild(symbol);
 				if(tmp != null) {
 					q.add(tmp);
 				}
 			}
 		}
 		
    	 // Return the list of completions
    	 
         return result;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}