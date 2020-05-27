/**
 * 
 */
package spelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class NearbyWords implements SpellingSuggest {
	// THRESHOLD to determine how many words to look through when looking
	// for spelling suggestions (stops prohibitively long searching)
	// For use in the Optional Optimization in Part 2.
	private static final int THRESHOLD = 100000; 

	Dictionary dict;

	public NearbyWords (Dictionary dict) 
	{
		this.dict = dict;
	}

	/** Return the list of Strings that are one modification away
	 * from the input string.  
	 * @param s The original String
	 * @param wordsOnly controls whether to return only words or any String
	 * @return list of Strings which are nearby the original string
	 */
	public List<String> distanceOne(String s, boolean wordsOnly )  {
		//System.out.println("distance 1:");
		   List<String> retList = new ArrayList<String>();
		  // System.out.println("insert:");
		   insertions(s, retList, wordsOnly);
		  // System.out.println("subst:");
		   substitution(s, retList, wordsOnly);
		  // System.out.println("deletion:");
		   deletions(s, retList, wordsOnly);
		   return retList;
	}

	
	/** Add to the currentList Strings that are one character mutation away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void substitution(String s, List<String> currentList, boolean wordsOnly) {
		// for each letter in the s and for all possible replacement characters
		for(int index = 0; index < s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				// use StringBuffer for an easy interface to permuting the 
				// letters in the String
				StringBuffer sb = new StringBuffer(s);
				sb.setCharAt(index, (char)charCode);

				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					//System.out.println("subst  "+sb.toString());
					currentList.add(sb.toString());
				}
			}
		}
		
		//System.out.println(currentList);
	}
	
	/** Add to the currentList Strings that are one character insertion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void insertions(String s, List<String> currentList, boolean wordsOnly ) {
		int len = s.length();
		for(int i = 0; i < len+1; i++) {
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				StringBuffer sb = new StringBuffer(s);
				if(i == len) {
					sb.append((char)charCode);
				}
				else {
					sb.insert(i, (char)charCode);
				}
				//System.out.println("ins ind = "+i+ " "+sb.toString());
				//sb.setCharAt(index, (char)charCode);

				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					//System.out.println("ins ind = "+i+ " "+sb.toString());
					currentList.add(sb.toString());
				}
			}
		}
		//System.out.println(currentList);
	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void deletions(String s, List<String> currentList, boolean wordsOnly ) {
		int len = s.length();
		for(int i = 0; i < len; i++) {
			StringBuffer sb = new StringBuffer(s);
			sb.deleteCharAt(i);
			//System.out.println("del ind = "+i+ " "+sb.toString());
			// if the item isn't in the list, isn't the original string, and
			// (if wordsOnly is true) is a real word, add to the list
			if(!currentList.contains(sb.toString()) && 
					(!wordsOnly||dict.isWord(sb.toString())) &&
					!s.equals(sb.toString())) {
				//System.out.println("del ind = "+i+ " "+sb.toString());
				currentList.add(sb.toString());
			}
		}
		//System.out.println(currentList);
	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param word The misspelled word
	 * @param numSuggestions is the maximum number of suggestions to return 
	 * @return the list of spelling suggestions
	 */
	@Override
	public List<String> suggestions(String word, int numSuggestions) {

		// initial variables
		List<String> queue = new LinkedList<String>();     // String to explore
		HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same  
														   // string multiple times
		List<String> retList = new LinkedList<String>();   // words to return
		//List<String> retListAll = new LinkedList<String>(); 
		
		// insert first node
		queue.add(word);
		visited.add(word);
		int countWords = 0;
		while(!queue.isEmpty() && (retList.size() < numSuggestions) && countWords < THRESHOLD) {
			String newWord = queue.remove(0);
			//System.out.println("remove from queue: "+newWord);
			List<String> retListNew = distanceOne(newWord, false );
			//System.out.println("all:");
			//System.out.println(retListNew);
			countWords += retListNew.size();
			//System.out.println("countWords:"+ countWords);
			for(String w: retListNew) {
				if(!visited.contains(w)) {
					visited.add(w);
					queue.add(w);
					
					if(dict.isWord(w)) {
						retList.add(w);
						//System.out.println("added, words:");
						//System.out.println(retList);
						if(retList.size() >= numSuggestions) break;
					}
				}
				
			}
		}
		//System.out.println("result words: "+ retList);
		return retList;

	}	

   public static void main(String[] args) {

   }

}
