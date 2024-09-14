import java.util.*;

/**
 * Represents a translation dictionary of English and ASL
 */
public class SignDictionary {
    Map<String,Word> words;


    /**
     * Contructor loads words into the SignDictionary
     * @param words : data to be loaded
     */
    public SignDictionary(List<String[]> words) {
        this();

        for (String[] w : words) {
            this.words.put(w[0], new Word(w[0],w[1]));
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
        return words.get(label);
    }

    /**
     *  Searches for words that match a given search term
     *
     * @param text : search term
     * @return List of words that match the search term
     */
    public List<Word> search(String text){
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
     * Returns all words in the SignDictionary
     *
     * @return List of words
     */
    public List<Word> getDict(){
        return new ArrayList<Word> (words.values());
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



}