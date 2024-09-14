package edu.vassar.cmpu203.asl;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


import edu.vassar.cmpu203.asl.controller.MainActivity;


/**
 * Instrumented test, which will execute on an Android device.
 * Note, the favorites should be empty before running the tests
 * and the date persistent tests should be run in the order
 * they are written.
 * Also, make sure all of the graphics in the documents folder
 * have had time to load.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AslAppInstrumentedTest {

    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * If nothing is sent into the search,
     * then a screen with "no results" should be displayed
     */
    @Test
    public void testSearchEmpty() {
        ViewInteraction viSearchBtn =
                Espresso.onView(ViewMatchers.withId(R.id.searchBtn));
        viSearchBtn.perform(ViewActions.click());

        ViewInteraction viResults =
                Espresso.onView(ViewMatchers.withId(R.id.englishWord));

        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText(R.string.no_results)));
    }

    /**
     * Tests searching "milk"
     */
    @Test
    public void testSearch() {
        ViewInteraction viInput =
                Espresso.onView(ViewMatchers.withId(R.id.input));
        viInput.perform(ViewActions.typeText("milk"));

        Espresso.closeSoftKeyboard();

        ViewInteraction viSearchBtn =
                Espresso.onView(ViewMatchers.withId(R.id.searchBtn));
        viSearchBtn.perform(ViewActions.click());

        ViewInteraction viResults =
                Espresso.onView(ViewMatchers.withId(R.id.englishWord));

        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText("milk")));
    }

    /**
     * Tests searching "big apples"
     * The results are displayed in alphabetical order
     */
    @Test
    public void testSearchMultiple() {
        ViewInteraction viInput =
                Espresso.onView(ViewMatchers.withId(R.id.input));
        viInput.perform(ViewActions.typeText("big apples"));

        Espresso.closeSoftKeyboard();

        ViewInteraction viSearchBtn =
                Espresso.onView(ViewMatchers.withId(R.id.searchBtn));
        viSearchBtn.perform(ViewActions.click());

        ViewInteraction viResults =
                Espresso.onView(ViewMatchers.withId(R.id.englishWord));

        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText("apple")));

        ViewInteraction viNextBtn =
                Espresso.onView(ViewMatchers.withId(R.id.nextBtn));
        viNextBtn.perform(ViewActions.click());

        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText("big")));

    }

    

    /**
     * Tests that there is no words in the favorites list
     */
    @Test
    public void testFavoriteEmpty(){
        ViewInteraction viFavoriteBtn =
                Espresso.onView(ViewMatchers.withId(R.id.favoritebtn));
        viFavoriteBtn.perform(ViewActions.click());

        ViewInteraction viResults =
                Espresso.onView(ViewMatchers.withId(R.id.englishWord));

        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText(R.string.no_results)));
    }

    /**
     * Tests adding and removing a word from the favorites list
     */
    @Test
    public void testFavorite(){

        // checks that favorite word button changes when clicked
        testSearch();

        ViewInteraction viFavoriteWBtn =
                Espresso.onView(ViewMatchers.withId(R.id.favoriteWordBtn));

        viFavoriteWBtn.check(ViewAssertions.matches(
                ViewMatchers.withText(R.string.fav_word)));
        viFavoriteWBtn.perform(ViewActions.click());
        viFavoriteWBtn.check(ViewAssertions.matches(
                ViewMatchers.withText(R.string.unfav_word)));

        // checks that new favorite appears in the list
        ViewInteraction viFavoriteBtn =
                Espresso.onView(ViewMatchers.withId(R.id.favoritebtn));
        viFavoriteBtn.perform(ViewActions.click());

        ViewInteraction viResults =
                Espresso.onView(ViewMatchers.withId(R.id.englishWord));

        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText("milk")));

        // tests that favorite can be removed from the list
        ViewInteraction viHomeBtn =
                Espresso.onView(ViewMatchers.withId(R.id.homebtn));
        viHomeBtn.perform(ViewActions.click());

        testSearch();
        viFavoriteWBtn.perform(ViewActions.click());
        testFavoriteEmpty();

    }

    /**
     * Tests that "apple" is the first word in the food category
     */
    @Test
    public void testBrowse(){

        ViewInteraction viBrowseBtn =
                Espresso.onView(ViewMatchers.withId(R.id.browsebtn));
        viBrowseBtn.perform(ViewActions.click());

        ViewInteraction viDropdown =
                Espresso.onView(ViewMatchers.withId(R.id.categorySelect));
        viDropdown.perform(ViewActions.click());

        ViewInteraction viFood =
                Espresso.onView(ViewMatchers.withText("food"));
        viFood.perform(ViewActions.click());

        ViewInteraction viSelectBtn =
                Espresso.onView(ViewMatchers.withId(R.id.selectCatBtn));
        viSelectBtn.perform(ViewActions.click());

        ViewInteraction viResults =
                Espresso.onView(ViewMatchers.withId(R.id.englishWord));

        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText("apple")));

    }

    /**
     * Tests that favorites can be removed spontaneous
     * from within the favorites section
     */
    @Test
    public void testFavoritesRemove(){

        // words big and apple are favorited
        testSearchMultiple();

        ViewInteraction viFavoriteWBtn =
                Espresso.onView(ViewMatchers.withId(R.id.favoriteWordBtn));
        viFavoriteWBtn.perform(ViewActions.click());

        ViewInteraction viNextBtn =
                Espresso.onView(ViewMatchers.withId(R.id.nextBtn));
        viNextBtn.perform(ViewActions.click());

        viFavoriteWBtn.perform(ViewActions.click());

        //checks that words apple and big are removed as they are unfavorited
        ViewInteraction viFavoriteBtn =
                Espresso.onView(ViewMatchers.withId(R.id.favoritebtn));
        viFavoriteBtn.perform(ViewActions.click());

        ViewInteraction viResults =
                Espresso.onView(ViewMatchers.withId(R.id.englishWord));
        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText("apple")));

        viFavoriteWBtn.perform(ViewActions.click());

        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText("big")));

        viFavoriteWBtn.perform(ViewActions.click());

        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText(R.string.no_results)));
    }

    /**
     * Tests that sort button sorts the
     * signs in Z-A and then A-Z order
     */
    @Test
    public void testSort(){
        // searches "big" and "apple"
        testSearchMultiple();

        ViewInteraction viSortBtn =
                Espresso.onView(ViewMatchers.withId(R.id.sortBtn));
        viSortBtn.perform(ViewActions.click());

        ViewInteraction viResults =
                Espresso.onView(ViewMatchers.withId(R.id.englishWord));
        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText("big")));

        viSortBtn.perform(ViewActions.click());
        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText("apple")));

    }

    /**
     * First method to test data persistence adds "milk" to list of favorites
     * All data persistence tests should be
     * run in order
     */
    @Test
    public void testDataPersistence1(){

        // adds milk to list of favorites
        testSearch();

        ViewInteraction viFavoriteWBtn =
                Espresso.onView(ViewMatchers.withId(R.id.favoriteWordBtn));

        viFavoriteWBtn.perform(ViewActions.click());

    }

    /**
     * Second method to test data persistence checks that "milk" has been added
     * and removes it
     * All data persistence tests should be
     * run in order
     */
    @Test
    public void testDataPersistence2(){

        // checks that new favorite appears in the list
        ViewInteraction viFavoriteBtn =
                Espresso.onView(ViewMatchers.withId(R.id.favoritebtn));
        viFavoriteBtn.perform(ViewActions.click());

        ViewInteraction viResults =
                Espresso.onView(ViewMatchers.withId(R.id.englishWord));

        viResults.check(ViewAssertions.matches(
                ViewMatchers.withText("milk")));

        // tests that favorite can be removed from the list
        ViewInteraction viFavoriteWBtn =
                Espresso.onView(ViewMatchers.withId(R.id.favoriteWordBtn));
        viFavoriteWBtn.perform(ViewActions.click());

    }

    /**
     * Third method to test data persistence checks that "milk" has been removed
     * All data persistence tests should be
     * run in order
     */
    @Test
    public void testDataPersistence3(){
        testFavoriteEmpty();
    }





}