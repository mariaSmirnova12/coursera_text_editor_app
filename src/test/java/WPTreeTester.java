import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import spelling.*;

public class WPTreeTester {
		
	WPTree tree;
	
		@Before
		public void setUp() throws Exception 
		{
			Dictionary dict = new DictionaryHashSet();
			DictionaryLoader loader = new DictionaryLoader();
			loader.loadFromFileToDictionary(dict, "data/grader_dict.txt");
            tree = new WPTree(new NearbyWords(dict));
		}

		public static String printPath(List<String> path) {
	        if (path == null) {
	            return "NULL PATH";
	        }
	        String ret = "";
	        for (int i = 0; i < path.size(); i++) {
	            ret += path.get(i);
	            if (i < path.size() - 1) {
	                ret += ", ";
	            }
	        }
	        return ret;
	    }

		/** Test if the size method is working correctly.
		 */
		@Test
		public void testPath()
		{
			List<String> path = tree.findPath("pool", "spoon");
			List<String> actual = Arrays.asList("pool", "spool", "spoon");
			assertEquals("Testing size for distance", 3, path.size());
			assertEquals(actual, path);
     		// System.out.println(printPath(path));

			path = tree.findPath("stools", "moon");
			actual = Arrays.asList("stools", "tools", "fools", "fool", "pool", "spool", "spoon", "soon", "moon");
			assertEquals("Testing size for distance", 9, path.size());
			assertEquals(actual, path);

			path = tree.findPath("foal", "needless");
			assertEquals(null, path);

			path = tree.findPath("needle", "kitten");
			assertEquals(null, path);
			//System.out.println(printPath(path));
		}
}
