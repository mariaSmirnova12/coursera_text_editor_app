package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

 /** Links a word to the next words in the list */
public class ListNode
{
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word)
    {
        this.word = word;
        nextWords = new LinkedList<String>();
    }

    public String getWord()
    {
        return word;
    }

    public void addNextWord(String nextWord)
    {
        nextWords.add(nextWord);
    }

    public String getRandomNextWord(Random generator)
    {
        int num = generator.nextInt(nextWords.size());
        return nextWords.get(num);
    }

    public String toString()
    {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}

