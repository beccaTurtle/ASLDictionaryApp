package edu.vassar.cmpu203.asl.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import edu.vassar.cmpu203.asl.databinding.FragmentCategoryBrowseBinding;
import java.util.*;

/**
 * Start of CategoryBrowseFragment class
 * CategoryBrowseFragment class groups/ displays the words in categories e.g milk and water in drinks
 */
public class CategoryBrowseFragment extends Fragment implements ICategoryBrowse{

    FragmentCategoryBrowseBinding binding;

    //Controller for ICategoryBrowse
    ICategoryBrowse.Listener listener;


    /**
     * Constructor for CategoryBrowseFragment
     * @param listener controller
     */
    public CategoryBrowseFragment(@NonNull ICategoryBrowse.Listener listener){
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
        this.binding = FragmentCategoryBrowseBinding.inflate(inflater);
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

        //Setting up the functionality of the dropdown menu in browse category
        ArrayAdapter<String> categoryAdpt =
                new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_dropdown_item_1line, this.listener.getCategoryLabels());

        this.binding.categorySelect.setAdapter(categoryAdpt);
        this.binding.categorySelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryBrowseFragment.this.listener.onSelectCategory(CategoryBrowseFragment.this.listener.getCategoryLabels()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Setting up the functionality of the select button
        this.binding.selectCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryBrowseFragment.this.listener.onBrowse();
            }
        });

    }
}
