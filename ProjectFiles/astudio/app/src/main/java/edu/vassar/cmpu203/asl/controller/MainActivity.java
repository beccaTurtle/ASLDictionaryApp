package edu.vassar.cmpu203.asl.controller;

import edu.vassar.cmpu203.asl.R;

import java.io.Serializable;
import java.util.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.widget.EditText;

import edu.vassar.cmpu203.asl.model.*;
import edu.vassar.cmpu203.asl.persistence.DictionaryBuilder;
import edu.vassar.cmpu203.asl.view.AslFragFactory;
import edu.vassar.cmpu203.asl.view.CategoryBrowseFragment;
import edu.vassar.cmpu203.asl.view.HomeScreenFragment;
import edu.vassar.cmpu203.asl.view.ICategoryBrowse;
import edu.vassar.cmpu203.asl.view.IHomeScreenView;
import edu.vassar.cmpu203.asl.view.IMainView;
import edu.vassar.cmpu203.asl.view.ISingleWord;
import edu.vassar.cmpu203.asl.view.MainView;
import edu.vassar.cmpu203.asl.view.SingleWordFragment;

/**
 * Start of MainActivity class
 * MainActivity class is the bridge between the UI and model
 */
public class MainActivity extends AppCompatActivity
        implements IHomeScreenView.Listener, ISingleWord.Listener, IMainView.Listener, ICategoryBrowse.Listener {

    public static final String CUR_STATE = "curState";
    public static final String SELECTED = "selected";
    public static final String RESULTS = "results";
    public static final String PLACE_IN_RESULTS = "placeInResults";
    public static final String ABC = "abc";
    //State keeps track of use case/fragment
    State state = State.HOME;

    //Word the user is looking at
    Word selected;

    //Category the user has selected
    String selectedCategory;

    //Stores dictionary of all words
    SignDictionary full;

    //Stores words the user has favorited
    SignDictionary favorites;

    //Stores the list of words in different categories
    Map<String, SignDictionary> categories;

    //List of the categories
    String[] categoryLabels;

    //Temporarily stores the list of results
    List<Word> results;

    //The index of the word that the user is currently viewing
    int placeInResults = 0;

    //If the results are in a-z order
    boolean abc = true;

    //Stores MainView
    IMainView mainView;

    //Builds the dictionaries and deals with data persistence
    DictionaryBuilder dictionaryBuilder;


    /**
     * Starts the program
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this. getSupportFragmentManager().
                setFragmentFactory(new AslFragFactory(this));

        super.onCreate(savedInstanceState);

        this.dictionaryBuilder = new DictionaryBuilder(this.getFilesDir());
        this.full = dictionaryBuilder.getFull();
        this.favorites = dictionaryBuilder.getFavorites();
        this.categories = dictionaryBuilder.getCategories();
        this.categoryLabels = categories.keySet().toArray(new String[0]);


        // Creates and displays the starting view
        this.mainView = new MainView(this, this);

        if (savedInstanceState == null) { //first time calling this method
            HomeScreenFragment homeScreenFragment = new HomeScreenFragment(this);
            this.mainView.displayFragment(homeScreenFragment, false, "home");
        }
        else { //activity if being re-created after its resources where allocated elsewhere
            this.state = savedInstanceState.getSerializable(CUR_STATE, State.class);

            //extra information must be loaded results were being shown
            if (this.state == State.FAVORITE_RESULTS ||
                    this.state == State.BROWSE_RESULTS ||
                    this.state == State.SEARCH_RESULTS) {
                this.selected = savedInstanceState.getSerializable(SELECTED, Word.class);
                this.results = (List<Word>) savedInstanceState.getSerializable(RESULTS, Serializable.class);
                this.placeInResults = savedInstanceState.getInt(PLACE_IN_RESULTS);
                this.abc = savedInstanceState.getBoolean(ABC);
            }
        }

        setContentView(this.mainView.getRootView());
    }


    /**
     * Gets the searched words from the dictionary and displays the result as a gif
     * @param word the search term
     */
    @Override
    public void onSearch(String word) {
        this.placeInResults = 0;
        this.abc = true;
        this.results = full.search(word);
        SingleWordFragment wfrag = new SingleWordFragment(this);
        this.mainView.setNavButtonsStatus(true, true, true);
        this.mainView.displayFragment(wfrag, true, "multiple words");
        this.state = State.SEARCH_RESULTS;

    }

    public void onSpeechRecognitionResults(String spokenWord){
        if(spokenWord != null){
            EditText inputEditText = findViewById(R.id.input);
            inputEditText.setText(spokenWord);
        }

    }

    /**
     * Actives if favorite button is clicked.
     * Changes the favorite status of the selected word
     *
     * @return selected Word
     */
    @Override
    public Word onFavorite() {
            if (this.selected.getFavoriteStatus()) {
                favorites.remove(selected);
                selected.switchFavoriteStatus(false);
            } else {
                favorites.add(selected);
                selected.switchFavoriteStatus(true);
            }
            this.dictionaryBuilder.save(this.full);

            // updates the results if the favorites are being shown
            if (this.state == State.FAVORITE_RESULTS){
                results.remove(selected);

                if (placeInResults >= results.size()) {
                    placeInResults = 0;
                    return this.onSelectStart();
                }

                selected = results.get(placeInResults);
            }
        return selected;
    }

    /**
     * Gets the index where the user is looking
     * though the results
     * @return int index
     */
    @Override
    public int getPlaceInResults() {
        return this.placeInResults;
    }

    @Override
    public int getResultsSize() {
        return results.size();
    }

    /**
     * Selects the next word in the results
     * @return newly selected Word
     */
    @Override
    public Word onSelectNext() {
        placeInResults += 1;
        if (placeInResults >= results.size()) {placeInResults = 0;}
        selected = results.get(placeInResults);
        return selected;
    }

    /**
     * Selects the previous word in the results
     * @return newly selected Word
     */
    @Override
    public Word onSelectPrevious() {
        placeInResults -= 1;
        if (placeInResults < 0) {placeInResults = (results.size()-1);}
        selected = results.get(placeInResults);
        return selected;

    }

    /**
     * Selects the first word in the results
     * @return selected Word or null if results are empty
     */
    @Override
    public Word onSelectStart() {
        if (results.size() > placeInResults){
            selected = results.get(placeInResults);
        }
        else {
            selected = null;
        }
        return selected;
    }

    /**
     * Switches the sorting of the results
     */
    @Override
    public void sort() {
        if (abc){
            Collections.sort(results, Collections.reverseOrder());
        }
        else{
            Collections.sort(results);
        }

        abc = !abc;
        placeInResults = 0;
    }

    /**
     * Checks the order of the results
     * @return if the results are in a-z order
     */
    @Override
    public boolean getAbc() {
        return this.abc;
    }

    /**
     * Allows the user to switch to home screen
     */
    @Override
    public void onGoHome() {
        HomeScreenFragment homeScreenFragment = new HomeScreenFragment(this);
        this.mainView.displayFragment(homeScreenFragment, true, "go home");
        this.state = State.HOME;
    }

    /**
     * Allows the user to switch to favorites screen
     */
    @Override
    public void onViewFavorites() {
        this.placeInResults = 0;
        this.abc = true;
        this.results = favorites.getDict();
        SingleWordFragment wfrag = new SingleWordFragment(this);
        this.mainView.displayFragment(wfrag, true, "multiple words");
        this.state = State.FAVORITE_RESULTS;

    }

    /**
     * Allows the user to switch to categories screen
     */
    @Override
    public void onViewCategories() {
        CategoryBrowseFragment bfrag = new CategoryBrowseFragment(this);
        this.mainView.displayFragment(bfrag, true, "categories");
        this.state = State.BROWSE_CATEGORY;

    }

    /**
     * Displays all the words in selectedCategory
     */
    @Override
    public void onBrowse() {
        this.placeInResults = 0;
        this.abc = true;
        this.results = categories.get(selectedCategory).getDict();
        SingleWordFragment wfrag = new SingleWordFragment(this);
        this.mainView.setNavButtonsStatus(true, true, true);
        this.mainView.displayFragment(wfrag, true, "multiple words");
        this.state = State.BROWSE_RESULTS;

    }

    /**
     * Sets the selected category
     * @param category that the user wants to view
     */
    @Override
    public void onSelectCategory(String category) {
        this.selectedCategory = category;

    }

    /**
     * Gets the names of the categories
     * @return String array categoryLabels
     */
    @Override
    public String[] getCategoryLabels() {
        return categoryLabels;
    }

    /**
     * Saves the data needed to recreate current fragment
     * @param outState Bundle in which to place your saved state.
     *
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(CUR_STATE, this.state);

        //saves extra information if results are being viewed
        if (this.state == State.FAVORITE_RESULTS ||
        this.state == State.BROWSE_RESULTS ||
        this.state == State.SEARCH_RESULTS) {
            outState.putSerializable(SELECTED, this.selected);
            outState.putSerializable(RESULTS, (Serializable) this.results);
            outState.putInt(PLACE_IN_RESULTS, this.placeInResults);
            outState.putBoolean(ABC, this.abc);
        }

        super.onSaveInstanceState(outState);

    }
}