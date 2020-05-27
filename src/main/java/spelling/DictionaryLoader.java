package spelling;


import java.io.*;

public class DictionaryLoader {

    /** Load the words from the dictionary file into the dictionary
     * 
     * @param d  The dictionary to load
     * @param filename The file containing the words to load.  Each word must be on a separate line.
     */

      public void loadFromFileToDictionary(Dictionary d, String filename)  {
      FileInputStream stream = null;
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File fileName = new File(classLoader.getResource(filename).getFile());
            stream = new FileInputStream(fileName);
            BufferedReader reader = null;
            String nextWord;
            reader = new BufferedReader(new InputStreamReader(stream));
            while ((nextWord = reader.readLine()) != null) {
                d.addWord(nextWord);
            }
        }
        catch (IOException e) {
            System.err.println("Problem looking for dictionary file: " + filename);
            e.printStackTrace();
        }
        finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
