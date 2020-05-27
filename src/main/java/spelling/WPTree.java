/**
 * 
 */
package spelling;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * WPTree implements WordPath by dynamically creating a tree of words during a Breadth First
 * Search of Nearby words to create a path between two words. 
 *
 */
public class WPTree implements WordPath {

	// this is the root node of the WPTree
	private WPTreeNode root;
	// used to search for nearby Words
	private NearbyWords nw; 
	
	// This constructor is used by the Text Editor Application
	public WPTree () {
		this.root = null;
		String dictFile = "data/dict.txt";
		Dictionary d = new DictionaryHashSet();
		DictionaryLoader loader = new DictionaryLoader();
		loader.loadFromFileToDictionary(d, dictFile);
		 this.nw = new NearbyWords(d);
	}
	
	public WPTree (NearbyWords nw) {
		this.root = null;
		this.nw = nw;
	}
	
	// see method description in WordPath interface
	public List<String> findPath(String word1, String word2) 
	{
		if(!nw.dict.isWord(word2)) return null;
		if(word1 == word2) {
			System.out.println("words must be different");
			return null;
		}
		this.root = new WPTreeNode(word1, null);
		List<WPTreeNode> queue = new LinkedList<WPTreeNode>();
		queue.add(root);
		HashSet<String> visited = new HashSet<String>();
		visited.add(word1);
		List<String> retList = new LinkedList<String>();
		//WPTreeNode child = root;
		WPTreeNode parent = root;
		int countWords = 0;
		while(!queue.isEmpty() && countWords < 10) {
			parent = queue.remove(0);
			String word = parent.getWord();
			System.out.println("word: "+word);
			List<String> wordsList = nw.distanceOne(word, true);
			for(String w: wordsList) {
				if(!visited.contains(w)) {
					WPTreeNode child = parent.addChild(w);
					System.out.println(child);
					visited.add(w);
					if(w.equals(word2)) {
						return child.buildPathToRoot();
					}
					queue.add(child);
				}
			}
			//parent = parent.getChildren().get(0);
			countWords++;
			printQueue(parent.getChildren() );
		}
	    return null;
	}
	
	// Method to print a list of WPTreeNodes (useful for debugging)
	private String printQueue(List<WPTreeNode> list) {
		String ret = "[ ";
		
		for (WPTreeNode w : list) {
			ret+= w.getWord()+", ";
		}
		ret+= "]";
		return ret;
	}
	
}




