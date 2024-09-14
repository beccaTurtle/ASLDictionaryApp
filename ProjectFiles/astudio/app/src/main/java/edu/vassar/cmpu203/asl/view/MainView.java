package edu.vassar.cmpu203.asl.view;

import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.vassar.cmpu203.asl.R;
import edu.vassar.cmpu203.asl.databinding.MainBinding;
import edu.vassar.cmpu203.asl.persistence.LocalStorageFacade;

/**
 * Start of MainView class
 * MainView class displays a nav bar with favorite, home and browse.
 */
public class MainView implements IMainView{

    FragmentManager fmanager;
    MainBinding binding;

    // this stores the controller
    Listener listener;

    /**
     * Constructor method.
     * @param activity The android activity the screen is associated with.
     * @param listener The controller that listens for onClick events
     */
    public MainView(FragmentActivity activity, Listener listener){
        this.fmanager = activity.getSupportFragmentManager();
        this.binding = MainBinding.inflate(activity.getLayoutInflater());
        this.listener = listener;


        // Setting up functionality for navbar
        this.setNavButtonsStatus(true, true, true);

        this.binding.homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainView.this.listener.onGoHome();
                MainView.this.setNavButtonsStatus(false, true, true);
            }
        });

        this.binding.favoritebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainView.this.listener.onViewFavorites();
                MainView.this.setNavButtonsStatus(true, true, false);
            }
        });

        this.binding.browsebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainView.this.listener.onViewCategories();
                MainView.this.setNavButtonsStatus(true, false, true);
            }
        });

    }

    /**
     * Method sets the status of the buttons in the navbar
     * @param homeOn boolean whether the home button is enabled
     * @param browseOn boolean whether the browse button is enabled
     * @param favoritesOn boolean whether the favorite button is enabled
     */
    public void setNavButtonsStatus(boolean homeOn, boolean browseOn, boolean favoritesOn){
        this.binding.homebtn.setEnabled(homeOn);
        this.binding.browsebtn.setEnabled(browseOn);
        this.binding.favoritebtn.setEnabled(favoritesOn);

    }

    /**
     * Retrieve the graphical widget (android view) at the root of the screen hierarchy/
     * @return the screen's root android view (widget)
     */
    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    /**
     * Replaces the contents of the screen's fragment container with the one passed in as an argument.
     *
     * @param fragment The fragment to be displayed.
     * @param addToStack true if this transaction should be reversible, false otherwise
     * @param name the name this transaction can be referred by.
     */
    @Override
    public void displayFragment(Fragment fragment, boolean addToStack, String name) {
        FragmentTransaction ft = fmanager.beginTransaction();
        ft.replace(this.binding.fragmentContainer.getId(), fragment);
        if (addToStack)  ft.addToBackStack(name);
        ft.commit();
    }
}
