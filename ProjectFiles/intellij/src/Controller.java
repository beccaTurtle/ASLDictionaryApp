import java.util.*;

/**
 * Coordinates UI with other app classes
 */
public class Controller {
    Word selected;
    SignDictionary full;
    SignDictionary favorites;
    Map<String, SignDictionary> categories;

    State state = State.HOME;

    /**
     * Constructor loads signs into the full and category SignDictionaries and creates an empty favorites SignDictionary
     * @param contents : signs to load into the dictionary
     */
    public Controller(List<String[]> contents) {
        this.full = new SignDictionary(contents);
        this.favorites = new SignDictionary();
        this.categories = new Hashtable<String, SignDictionary>();

        Set<String> category_labels = new HashSet<String>();
        for (String[] word : contents){
            category_labels.add(word[2]);
        }

        for (String label : category_labels){
            this.categories.put(label, Controller.filterByCategory(label, contents));
        }

    }

    /**
     * Helper method for Constructor.
     * @param label : choosen category
     * @param words : list that is filtered
     * @return SignDictionary with only signs in the chosen category
     */
    public static SignDictionary filterByCategory(String label, List<String[]> words){
        List<String[]> filtered = new ArrayList<String[]>();

        for (String[] word : words){
            if (label.equals(word[2])){
                filtered.add(word);
            }
        }

        return new SignDictionary(filtered);
    }

    /**
     * Finds a sign
     * @param text : searched english text
     * @return Word searched sign
     */
    public Word selectSign(String text){
        selected = full.select(Controller.standardize(text));
        return selected;
    }

    public List<Word> searchSign(String text){
        return full.search(Controller.standardize(text));
    }

    /**
     * Gets all the available categories
     * @return List of categories
     */
    public List<String> getCategoriesList() {
        return new ArrayList<String>(categories.keySet());
    }

    /**
     * Returns the dictionary for a given category
     * @param category : chosen category
     * @return List of words
     */
    public List<Word> viewCategory(String category){
        return categories.get(category).getDict();
    }

    /**
     * Returns the dictionary for the favorites list
     * @return List of words
     */
    public List<Word> viewFavorites(){
        return favorites.getDict();
    }

    /**
     * Makes the last selected sign a favorite
     * @return updated Word
     */
    public Word addFavoriteStatus(){
        selected.switchFavoriteStatus(true);
        favorites.add(selected);
        return selected;
    }

    /**
     * Makes the last selected sign not a favorite
     * @return updated Word
     */
    public Word removeFavoriteStatus(){
        selected.switchFavoriteStatus(false);
        favorites.remove(selected);
        return selected;
    }

    /**
     * Controls the flow of app features
     * @param raw_choice : Text of what the user wants to do next
     * @return State : the updated app state
     */
    public State navigateApp(String raw_choice){
        String choice = Controller.standardize(raw_choice);

        if (choice.equals("quit")){
            state = State.QUIT;
        }
        else if (choice.equals("go home") && (state != State.HOME)){
            state = State.HOME;
        }
        else if (choice.equals("search") && (state == State.HOME)){
            state = State.SEARCH_SIGN;
        }
        else if (choice.equals("select word") &&
                ((state == State.BROWSE_CATEGORY) ||
                        (state == State.VIEW_FAVORITES) || (state == State.SEARCH_SIGN))){
            state = State.SELECT_SIGN;
        }
        else if (choice.equals("browse category") && (state == State.HOME)){
            state = State.BROWSE_CATEGORY;
        }
        else if (choice.equals("view favorites") && (state == State.HOME)){
           state = State.VIEW_FAVORITES;
        }
        else if (choice.equals("add favorite") && ((state == State.SELECT_SIGN) || (state == State.DELETE_FAVORITE))){
            state = State.ADD_FAVORITE;
        }
        else if (choice.equals("delete favorite") && ((state == State.SELECT_SIGN) || (state == State.ADD_FAVORITE))){
            state = State.DELETE_FAVORITE;
        }
        else {
            return State.ERROR;
        }
        return this.state;
    }

    public static String standardize(String text){
        return text.toLowerCase().trim();
    }

}
