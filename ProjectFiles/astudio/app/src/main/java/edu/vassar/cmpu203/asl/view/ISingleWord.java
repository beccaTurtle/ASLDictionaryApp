package edu.vassar.cmpu203.asl.view;

import edu.vassar.cmpu203.asl.model.Word;

/**
 * Start of ISingleWord interface
 * ISingleWord displays signs
 */
public interface ISingleWord  {



    /**
     * Start of interface Listener
     * Defines methods for a listener for the outer class
     */
    interface Listener {


        /**
         * Actives if favorite button is clicked.
         * Changes the favorite status of the selected word
         *
         * @return selected Word
         */
        Word onFavorite();

        /**
         * Gets the index where the user is looking
         * though the results
         * @return int index
         */
        int getPlaceInResults();


        /**
         * Gets the size of the results list
         * @return int List size
         */
        int getResultsSize();

        /**
         * Selects the next word in the results
         * @return newly selected Word
         */
        Word onSelectNext();

        /**
         * Selects the previous word in the results
         * @return newly selected Word
         */
        Word onSelectPrevious();

        /**
         * Selects the first word in the results
         * @return Word or null if results are empty
         */
        Word onSelectStart();

        /**
         * Switches the sorting of the results
         */
        void sort();

        /**
         * Checks the order of the results
         * @return if the results are in a-z order
         */
        boolean getAbc();
    }


}
