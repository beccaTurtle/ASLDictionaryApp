package edu.vassar.cmpu203.asl.view;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import edu.vassar.cmpu203.asl.databinding.FragmentHomeScreenBinding;


/**
 * Start of HomeScreenFragment class
 * HomeScreenFragment class displays the search for signs functionality
 */

public class HomeScreenFragment extends Fragment implements IHomeScreenView{

    FragmentHomeScreenBinding binding;

    //Stores the controller
    Listener listener;


    /**
     * Constructor for HomeScreenFragment
     * @param listener controller
     */
    public HomeScreenFragment(@NonNull Listener listener){
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
        this.binding = FragmentHomeScreenBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    /**
     * onViewCreated sets up the functionality (dynamic part)
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set up the functionality of the search button
        this.binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable searchTermEditable = HomeScreenFragment.this.binding.input.getText();
                String searchTerm = searchTermEditable.toString();
                searchTermEditable.clear();

                HomeScreenFragment.this.listener.onSearch(searchTerm);
            }
        });
    }
}
