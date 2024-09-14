package edu.vassar.cmpu203.asl.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.vassar.cmpu203.asl.R;
import edu.vassar.cmpu203.asl.controller.MainActivity;
import edu.vassar.cmpu203.asl.model.SignDictionary;
import edu.vassar.cmpu203.asl.model.Word;
import edu.vassar.cmpu203.asl.persistence.IPersistenceFacade;
import edu.vassar.cmpu203.asl.persistence.LocalStorageFacade;

/**
 * Class DictionaryBuilder is responsible for building all of the dictionaries of the app
 */
public class DictionaryBuilder {
    private SignDictionary full;
    private SignDictionary favorites;

    Map<String, SignDictionary> categories;

    //Stores class that deals with data persistence
    IPersistenceFacade persFacade;

    /**
     * Constructor loads in the data for the full, favorites and category dictionaries
     */
    public DictionaryBuilder(File directory){

        // initialize persistence facade
        this.persFacade = new LocalStorageFacade(directory);
        List<Word> contents = new ArrayList<Word>();

        // Uploads or creates a new full dictionary
        this.full= this.persFacade.retrieveDictionary();
        if (this.full == null) {

            // Populates the dictionary with gifs associated to words
            contents.add(new Word("milk", R.drawable.milk, "drink"));
            contents.add(new Word("apple", R.drawable.apple, "food"));
            contents.add(new Word("big", R.drawable.big, "adjective"));
            contents.add(new Word("small", R.drawable.small, "adjective"));
            contents.add(new Word("water", R.drawable.water, "drink"));
            contents.add(new Word("no", R.drawable.no, "expression"));
            contents.add(new Word("yes", R.drawable.yes, "expression"));
            contents.add(new Word("brother", R.drawable.brother, "family"));
            contents.add(new Word("cat", R.drawable.cat, "animal"));
            contents.add(new Word("dance", R.drawable.dance, "verb"));
            contents.add(new Word("dog", R.drawable.dog, "animal"));
            contents.add(new Word("eat", R.drawable.eat, "verb"));
            contents.add(new Word("father", R.drawable.father, "family"));
            contents.add(new Word("mother", R.drawable.mother, "family"));
            contents.add(new Word("parent", R.drawable.parent, "family"));
            contents.add(new Word("penguin", R.drawable.penguin, "animal"));
            contents.add(new Word("run", R.drawable.run, "verb"));
            contents.add(new Word("share", R.drawable.share, "verb"));
            contents.add(new Word("sibling", R.drawable.sibling, "family"));
            contents.add(new Word("sister", R.drawable.sister, "family"));
            contents.add(new Word("thank you", R.drawable.thankyou, "expression"));
            contents.add(new Word("walk", R.drawable.walk, "verb"));
            contents.add(new Word("want", R.drawable.want, "verb"));



            this.full = new SignDictionary(contents);
        }

        contents = full.getDict();

        // Populates the categories dictionaries
        this.categories = new Hashtable<String, SignDictionary>();

        Set<String> category_labels = new HashSet<String>();

        for (Word word : contents){
            category_labels.add(word.getCategory());
        }

        for (String label : category_labels){
            this.categories.put(label, DictionaryBuilder.filterByCategory(label, contents));
        }

        // Populates the favorites dictionary
        this.favorites = new SignDictionary();
        for (Word word : contents){
            if (word.getFavoriteStatus()){
                this.favorites.add(word);
            }
        }
    }

    /** Helper method for Constructor.
     * @param label : choosen category
     * @param words : list that is filtered
     * @return SignDictionary with only signs in the chosen category
     */
    public static SignDictionary filterByCategory(String label, List<Word> words){
        List<Word> filtered = new ArrayList<Word>();

        for (Word word : words){
            if (label.equals(word.getCategory())){
                filtered.add(word);
            }
        }

        return new SignDictionary(filtered);
    }

    /**
     * Gets the full dictionary
     * @return populated SignDictionary
     */
    public SignDictionary getFull(){
        return full;

    }

    /**
     * Gets the favorites dictionary
     * @return SignDictionary of favorites
     */
    public SignDictionary getFavorites(){
        return favorites;
    }

    /**
     * Gets the category dictionaries
     * @return Map of SignDictionaries
     */
    public Map<String, SignDictionary> getCategories(){
        return categories;
    }

    /**
     * Saves the given dictionary
     * @param updated SignDictionary to be saved
     */
    public void save(SignDictionary updated){
        this.persFacade.saveDictionary(updated);
    }


}
