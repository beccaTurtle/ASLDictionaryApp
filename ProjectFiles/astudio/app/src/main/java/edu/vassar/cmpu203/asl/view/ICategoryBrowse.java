package edu.vassar.cmpu203.asl.view;

/**
 * Start of interface ICategoryBrowse
 * ICategoryBrowse groups/ displays the words in categories e.g milk and water in drinks
 */
public interface ICategoryBrowse {

    /**
     * Start of interface Listener
     * Defines methods for a listener for the outer class
     */
    interface Listener {
        /**
         * Displays all the words in selectedCategory
         */
        void onBrowse();


        /**
         * Sets the selected category
         * @param category that the user wants to view
         */
        void onSelectCategory(String category);

        /**
         * Gets all the categories for the CategoryBrowseFragment
         * @return array of Strings
         */
        String[] getCategoryLabels();
    }
}
