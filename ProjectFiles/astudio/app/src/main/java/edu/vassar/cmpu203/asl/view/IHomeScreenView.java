package edu.vassar.cmpu203.asl.view;

import java.util.List;

import edu.vassar.cmpu203.asl.model.Word;

/**
 * Start of interface IHomeScreenView
 * IHomeScreenView displays the search for signs functionality
 */
public interface IHomeScreenView {

    /**
     * Start of interface Listener
     * Defines methods for a listener for the outer class
     */
    interface Listener {

        /**
         * Gets the searched words from the dictionary and displays the result as a gif
         * @param word the search term
         */
        void onSearch(String word);
        void onSpeechRecognitionResults(String spokenWord);
    }

}
