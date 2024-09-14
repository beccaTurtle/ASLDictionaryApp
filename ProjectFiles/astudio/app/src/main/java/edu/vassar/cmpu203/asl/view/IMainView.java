package edu.vassar.cmpu203.asl.view;

import android.view.View;

import androidx.fragment.app.Fragment;

/**
 * Start of IMainView interface
 * IMainView displays a nav bar with favorite, home and browse.
 */
public interface IMainView {

    /**
     * Retrieve the graphical widget (android view) at the root of the screen hierarchy/
     * @return the screen's root android view (widget)
     */
    View getRootView();

    /**
     * Replaces the contents of the screen's fragment container with the one passed in as an argument.
     *
     * @param fragment The fragment to be displayed.
     * @param reversible true if this transaction should be reversible, false otherwise
     * @param name the name this transaction can be referred by.
     */
    void displayFragment(Fragment fragment, boolean reversible, String name);

    /**
     * Method sets the status of the buttons in the navbar
     * @param homeOn boolean whether the home button is enabled
     * @param browseOn boolean whether the browse button is enabled
     * @param favoritesOn boolean whether the favorite button is enabled
     */
    void setNavButtonsStatus(boolean homeOn, boolean browseOn, boolean favoritesOn);

    /**
     * Start of interface Listener
     * Defines methods for a listener for the outer class
     */
    interface Listener {

        /**
         * Allows the user to switch to home screen
         */
        void onGoHome();

        /**
         * Allows the user to switch to favorites screen
         */
        void onViewFavorites();

        /**
         * Allows the user to switch to categories screen
         */
        void onViewCategories();

    }
}
