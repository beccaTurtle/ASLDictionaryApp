package edu.vassar.cmpu203.asl.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import edu.vassar.cmpu203.asl.R;
import edu.vassar.cmpu203.asl.model.SignDictionary;
import edu.vassar.cmpu203.asl.model.Word;

/**
 * Start of SignDictionaryTest
 *
 * Purpose of this test is to test the functionality of the SignDictionary class
 */
public class SignDictionaryTest {

    /**
     * Test the constructor method
     */
    @Test
    public void testConstructorWithWords(){
        List<Word> wordList = new ArrayList<>();

        //the word searched
        wordList.add(new Word("big", R.drawable.big, "adjective"));
        wordList.add(new Word("small", R.drawable.small, "adjective"));

        SignDictionary signDictionary = new SignDictionary(wordList);

        //verify if the the category matches the word searched
        assertEquals("adjective", signDictionary.select("big").getCategory());
        assertEquals ("adjective", signDictionary.select("small").getCategory());
    }

    /**
     * Testing the constructor when the user does not search a word
     */
    @Test
    public void testEmptyConstructor(){

        //The object SignDictionary is empty does not take any parameter
        SignDictionary signDictionary = new SignDictionary();

        //checks that the constructor is empty
        assertNotNull(signDictionary.getDict());
        assertTrue(signDictionary.getDict().isEmpty());
    }

    /**
     * Test for the select method
     */
    @Test
    public void testSelect() {
        SignDictionary signDictionary = new SignDictionary();
        Word word = new Word("apple", R.drawable.apple, "food");

        //The word its added
        signDictionary.add(word);

        //Checks if the right word is selected
        assertEquals(word.getEnglish(), signDictionary.select("apple").getEnglish());
    }

    /**
     * Test for the search method
     */
    @Test
    public void testSearch() {
        SignDictionary signDictionary = new SignDictionary();

        Word word1 = new Word("milk", R.drawable.milk, "drink");
        Word word2 = new Word("apple", R.drawable.apple, "food");
        signDictionary.add(word1);
        signDictionary.add(word2);

        List<Word> searchResults = signDictionary.search("Mi");

        //Checks if the word we wanted to search displays
        assertEquals(1, searchResults.size());
        assertTrue(searchResults.contains(word1));
        assertFalse(searchResults.contains(word2));
    }

    /**
     * Test for the GetDict method
     */
    @Test
    public void testGetDict() {
        SignDictionary signDictionary = new SignDictionary();
        Word word1 = new Word("Yes", R.drawable.yes, "Expression");
        Word word2 = new Word("No", R.drawable.no, "Expression");
        signDictionary.add(word1);
        signDictionary.add(word2);

        List<Word> dictionary = signDictionary.getDict();

        //Checks if is the right word is in the right category in the dictionary
        assertEquals(2, dictionary.size());
        assertTrue(dictionary.contains(word1));
        assertTrue(dictionary.contains(word2));
    }

    /**
     * Test for add method
     */
    @Test
    public void testAdd() {
        SignDictionary signDictionary = new SignDictionary();
        Word word = new Word("water", R.drawable.water, "drink");
        signDictionary.add(word);

        //Checks if the right word is added
        assertEquals(word, signDictionary.select("Water"));
    }

    /**
     * Test for remove method
     */
    @Test
    public void testRemove() {
        SignDictionary signDictionary = new SignDictionary();
        Word word = new Word("water", R.drawable.water, "drink");
        signDictionary.add(word);

        assertNotNull(signDictionary.select("Water"));
        signDictionary.remove(word);
        assertNull(signDictionary.select("Water"));
    }

    /**
     * Test for standardize method
     */
    @Test
    public void testStandardize() {
        assertEquals("water", SignDictionary.standardize("Water"));
        assertEquals("no", SignDictionary.standardize("No"));
        assertEquals("yes", SignDictionary.standardize("Yes"));
        assertEquals("big", SignDictionary.standardize("Big"));
        assertEquals("small", SignDictionary.standardize("Small"));
        assertEquals("milk", SignDictionary.standardize("Milk"));
    }
}