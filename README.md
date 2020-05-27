 Course 2 out of 5 in **Object Oriented Java Programming: Data Structures and Beyond** Specialization by University of California San Diego https://www.coursera.org/specializations/java-object-oriented

Course Certificate: 
https://www.coursera.org/account/accomplishments/records/WK4Z3LTV9UT2

Text Editor Application has follow functionality:

- *Spell Check*  (is able to highlight spelling errors and look for suggestions).
 3 ways of getting suggestions:
- to produce all substitutions of each letter in String s with any letter from the alphabet
- to insert any letter in between existing letters (or to the start and end) to create new Strings 
- to remove any existing letter to create new Strings

- *Auto-Complete*  (Trie data structure is used).
 When the user types partial words, the editor will give suggested completions to choose from in a drop down menu under the word.Breadth first search algorithm is used to searching the list of completions. 
- *Flesch Index* (can calculate how hard it is to read the text using the following formula)
```
 Flesch score = 206.835 - 1.015(words/sentences) - 84.6(syllables/words)
```
- *Edit Distance*,  (compare two words to see how far they are from one to the other). 
Trying to find a path from one word to another with the restriction that we can only make one change at a time
 (letter permutation, letter insertion, letter deletion) and that whatever change we make, has to result in a real word.
For example, a path from the word "time" to "theme": 
```
time -> tie -> tee -> thee -> theme
```
- *Generate Markov Text* (implemented as list of lists).
List of ListNode objects is created. Each ListNode contains a word and a list of words which follow that word in the source text.

![Project screenshot](https://github.com/mariaSmirnova12/coursera_text_editor_app/blob/master/screenshot_project.png)