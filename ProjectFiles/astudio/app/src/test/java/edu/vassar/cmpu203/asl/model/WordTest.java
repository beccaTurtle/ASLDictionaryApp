package edu.vassar.cmpu203.asl.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.vassar.cmpu203.asl.R;

public class WordTest {
    /**
     * Tet for switchFavoriteStatus method
     */
    @Test
    public void testSwitchFavoriteStatus() {
        Word word = new Word("apple", R.drawable.apple, "Food");

        //In the start the word is not favorited
        assertFalse(word.getFavoriteStatus());

        //Change the status to favorite
        word.switchFavoriteStatus(true);
        assertTrue(word.getFavoriteStatus());

        //Change the status to unfavorite if the user doesn't want to be in favorite list anymore
        word.switchFavoriteStatus(false);
        assertFalse(word.getFavoriteStatus());
    }

    /**
     * Test for the getEnglish method
     */
    @Test
    public void testGetEnglish() {
        Word word = new Word("apple", R.drawable.apple, "Food");

        //Check if the correct word is returned
        assertEquals("apple", word.getEnglish());

        //Change the word and double check
        word = new Word("big", R.drawable.big, "Adjective");
        assertEquals("big", word.getEnglish());
    }

    /**
     * Test for the getASL method
     */
    @Test
    public void testGetASL() {
        Word word = new Word("Small", R.drawable.small, "Adjective");

        //Check if it gets the correct path
        assertEquals(R.drawable.small, word.getASL());

        //Change the gif path and check again
        word = new Word("Water", R.drawable.water, "Food");
        assertEquals(R.drawable.water, word.getASL());
    }

    /**
     * Test for getCategory method
     */
    @Test
    public void testGetCategory() {
        Word word = new Word("Yes", R.drawable.yes, "Expression");

        //Check if the right category returns
        assertEquals("Expression", word.getCategory());

        //Change category and check again
        word = new Word("Apple", R.drawable.apple, "Food");
        assertEquals("Food", word.getCategory());
    }
}
