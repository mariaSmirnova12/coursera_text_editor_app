import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import spelling.Dictionary;
import spelling.DictionaryHashSet;
import spelling.DictionaryLoader;
import spelling.NearbyWords;

public class NearbyWordsTester {
	
    NearbyWords nw1;
    NearbyWords nw2;

	@Before
	public void setUp()
	{
        DictionaryLoader loader = new DictionaryLoader();
        Dictionary d1 = new DictionaryHashSet();
        loader.loadFromFileToDictionary(d1, "test_cases/dict.txt");
        nw1 = new NearbyWords(d1);
        Dictionary d2 = new DictionaryHashSet();
        loader.loadFromFileToDictionary(d2, "test_cases/dict2.txt");
        nw2 = new NearbyWords(d2);
	}

	@Test
	public void testDistanceOne()
	{
        List<String> d1 = nw1.distanceOne("word", true);
        assertEquals("Testing size for distance", 5, d1.size());
        String[] res = {"words", "lord", "ward", "wore", "worn"};
        int ind = 0;
        for (String i : d1) {
        	assertEquals("Testing distanceOne words",res[ind], i);
        	ind++;
        }
        d1 = nw1.distanceOne("word", false);
        assertEquals("Testing size for large dict", 230 , d1.size());
	}

	@Test
	public void testDeletion()
	{
        List<String> d1 = new ArrayList<String>();
        nw1.deletions("makers", d1, true);
        assertEquals("Testing size for large dict", 2, d1.size());
        String[] res = {"makes", "maker"};
        int ind = 0;
        for (String i : d1) {
            assertEquals("Testing deletion words for large dict",res[ind], i);
            ind++;
        }
	}

	@Test
	public void testInsertion()
	{
       List<String> d1 = new ArrayList<String>();
       nw1.insertions("or", d1, true);
       assertEquals("Testing size for large dict after insertions", 3, d1.size());
       String[] res = {"tor", "oar", "ore"};
       int ind = 0;
       for (String i : d1) {
            assertEquals("Testing insertions words returned words for large dict",res[ind], i);
            ind++;
       }
	}

	@Test
	public void testSuggestion()
	{
       List<String> d1 = nw2.suggestions("dag", 4);
       assertEquals("Testing size for suggestions", 2, d1.size());
       String[] res = {"dog", "dogs"};
       int ind = 0;
        for (String i : d1) {
            System.out.println("sug "+i);
            ind++;
        }
        d1 = nw2.suggestions("fare", 3);
        assertEquals("Testing size for large dict", 3, d1.size());
	}
}
