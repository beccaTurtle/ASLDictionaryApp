package edu.vassar.cmpu203.asl.model;
import java.util.*;

/**
 * Represents a translation dictionary of English and ASL
 */
public class SignDictionary implements java.io.Serializable {
    Map<String,Word> words;


    /**
     * Contructor loads words into the SignDictionary
     * @param words : data to be loaded
     */
    public SignDictionary(List<Word> words) {
        this();

        for (Word w : words) {
            this.words.put(w.getEnglish(), w);
        }
    }

    /**
     * Constructor makes an empty SignDictionary
     */
    public SignDictionary(){
        this.words = new Hashtable<String,Word>();
    }

    /**
     * Gets the sign with a given label
     * @param label : english word
     * @return Word object
     */
    public Word select(String label){
        return words.get(SignDictionary.standardize(label));
    }

    /**
     *  Searches for words that match a given search term
     *
     * @param raw_text : search term
     * @return List of words that match the search term
     */
    public List<Word> search(String raw_text){

        if (raw_text.equals("")){
            return new ArrayList<Word>();
        }

        String text = SignDictionary.standardize(raw_text);

        SignDictionary results = new SignDictionary();
        Set<String> labels = words.keySet();

        for(String label : labels){
            if (label.contains(text) || text.contains(label)){
                results.add(words.get(label));
            }
        }

        return results.getDict();
    }

    /**
     * Sorts and returns all words in the SignDictionary
     *
     * @return List of words
     */
    public List<Word> getDict(){
        List toReturn = new ArrayList<Word> (words.values());
        Collections.sort(toReturn);
        return toReturn;
    }


    /**
     * Adds a word to the SignDictionary
     * @param w : Word to be added
     */
    public void add(Word w){
        words.put(w.getEnglish(), w);
    }

    /**
     * Removes word from the SignDictionary
     * @param w : Word to be removed
     */
    public void remove(Word w){
        words.remove(w.getEnglish());
    }

    /**
     * Makes a string lowercase and removes whitespace
     * @param text : input
     * @return lowercase string without whitespace
     */
    public static String standardize(String text){
        return text.toLowerCase().trim();
    }

}