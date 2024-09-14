package edu.vassar.cmpu203.asl.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.asl.R;
import edu.vassar.cmpu203.asl.model.*;
import java.util.*;
import edu.vassar.cmpu203.asl.databinding.*;


/**
 * Start of SingleWordFragment class
 * SingleWordFragment class displays signs
 */
public class SingleWordFragment extends Fragment implements ISingleWord{
    private FragmentSingleWordBinding binding;

    //Controller
    private Listener listener;

    //The word that is being shown
    Word displayedWord;


    /**
     * Constructor for SingleWordFragment
     * @param listener controller
     */
    public SingleWordFragment(Listener listener){
        this.listener = listener;
    }


    /**
     * onCreateView creates the view associate with the fragment
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return the root view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentSingleWordBinding.inflate(inflater);

        return this.binding.getRoot();
    }

    /**
     * onViewCreated sets up the functionality (dynamic part)
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        displayedWord = this.listener.onSelectStart();
        this.displayWord();

        //Set ups the functionality for the next and previous buttons in multiple search
        this.binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayedWord = SingleWordFragment.this.listener.onSelectNext();
                SingleWordFragment.this.displayWord();

            }
        });

        this.binding.previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayedWord = SingleWordFragment.this.listener.onSelectPrevious();
                SingleWordFragment.this.displayWord();

            }
        });

        //Sets the functionality for the favorite button
        this.binding.favoriteWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayedWord = SingleWordFragment.this.listener.onFavorite();
                SingleWordFragment.this.displayWord();
            }
        });

        //Sets the functionality for the sort button
        this.binding.sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleWordFragment.this.listener.sort();
                displayedWord =  SingleWordFragment.this.listener.onSelectStart();
                SingleWordFragment.this.displayWord();
            }
        });

    }

    /**
     * Updates the message on the sort button
     */
    private void setSortBtnMsg(){
        if(listener.getAbc()){
            this.binding.sortBtn.setText(R.string.ZA);
        }
        else {
            SingleWordFragment.this.binding.sortBtn.setText(R.string.AZ);
        }
    }

    /**
     * Updates the message on the favorite button
     */
    private void setFavoriteBtnMsg() {
        if (displayedWord.getFavoriteStatus()){
            this.binding.favoriteWordBtn.setText(R.string.unfav_word);

        }
        else {
            this.binding.favoriteWordBtn.setText(R.string.fav_word);

        }
    }


    /**
     * Displays the word at the index place in results
     */
    private void displayWord() {
        //Checks if the list of selected_words is empty, if not displays the result
        if (listener.getResultsSize() > 0) {
            String wordDisplay = displayedWord.getEnglish();
            this.binding.englishWord.setText(wordDisplay);
            this.binding.displayGif.setBackgroundResource(displayedWord.getASL());
            this.setFavoriteBtnMsg();
            this.setSortBtnMsg();
            String label = this.getContext().getString(R.string.result);
            this.binding.resultNum.setText(" " + label + (listener.getPlaceInResults()+1) + "/" + listener.getResultsSize());
        }
        //if it is empty displays a not found message
        else {
            this.binding.englishWord.setText(R.string.no_results);
            this.binding.favoriteWordBtn.setVisibility(getView().INVISIBLE);
            this.binding.displayGif.setVisibility(getView().INVISIBLE);
            this.binding.resultNum.setVisibility(getView().INVISIBLE);
        }
        // If there is not a multiple search the next, previous and sort buttons get hidden
        if (listener.getResultsSize() < 2) {
            this.binding.nextBtn.setVisibility(getView().INVISIBLE);
            this.binding.previousBtn.setVisibility(getView().INVISIBLE);
            this.binding.sortBtn.setVisibility(getView().INVISIBLE);
        }
    }

}