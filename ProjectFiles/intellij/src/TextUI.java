import java.util.*;

/**
 * Text based UI and main method
 */
public class TextUI {
    Scanner s = new Scanner(System.in);
    Controller nav;

    /**
     * Constructs new controller
     * @param contents : signs to load into the dictionary
     */
    public TextUI(List<String[]> contents) {
        nav = new Controller(contents);
    }

    /**
     * Collects a response from the user
     * @return String typed by the user
     */
    public String ask(String question){
        System.out.print(question);
        return s.nextLine();
    }

    /**
     * Prints a given Word
     * @param w : Word to be displayed
     * @return boolean if the input Word is not null
     */
    public boolean displayWord(Word w){
        if (w == null){
            System.out.println("We could not find your word :(");
            return false;
        }

        System.out.print(w.getEnglish());
        System.out.print(" The sign path is at " + w.getASL());

        if(w.getFavoriteStatus()){
            System.out.println(". This sign is favorited");
        }
        else {
            System.out.println(". This sign is not favorited");
        }

        return true;

    }

    /**
     * Prints welcome and asks for next action
     * @return String of what the user wants to do next
     */
    public String home(){
        System.out.println("Welcome to our ASL translation app!");
        return ask("Type search, browse category, view favorites or quit: ");

    }

    /**
     * Prompts user to choose a sign and prints that sign
     * @return String of what the user wants to do next
     */
    public String selectSign() {
        String response;
        boolean wordFound;

        response = this.ask("Select one of the above words: ");
        wordFound = this.displayWord(nav.selectSign(response));

        if (!wordFound) {
            return this.ask("Type go home or quit: ");
        }

        return this.ask("Type add favorite, delete favorite, go home or quit: ");
    }

    public String searchSign(){
        String response;
        List<Word> browseWords;

        response = this.ask("Search for a word or phrase: ");
        browseWords = nav.searchSign(response);

        if (browseWords.isEmpty()) {
            System.out.println("We could not find any matches for your search :(");
            return this.ask("Type go home or quit: ");
        }

        for (Word w : browseWords) {
            this.displayWord(w);
        }

        return this.ask("Type select word, go home or quit: ");
    }

    /**
     * Prompts user to choose a category and prints that category
     * @return String of what the user wants to do next
     */
    public String browseCategory(){
        String response;
        List<String> categories;
        List<Word> browseWords;

        categories = nav.getCategoriesList();
        for (String c : categories) {
            System.out.println(c);
        }

        response = this.ask("Type one of the above categories: ");
        browseWords = nav.viewCategory(response);
        for (Word w : browseWords) {
            this.displayWord(w);
        }

        return this.ask("Type select word, go home or quit: ");

    }

    /**
     * Prints the favorite list
     * @return String of what the user wants to do next
     */
    public String viewFavorites(){
        List<Word> browseWords;

        browseWords = nav.viewFavorites();
        if (browseWords.isEmpty()) {
            System.out.println("No favorites yet. Select a word to add it to your favorites list.");
            return this.ask("Type go home or quit: ");
        }
        for (Word w : browseWords) {
            this.displayWord(w);
        }
        return this.ask("Type select word, go home or quit: ");
    }

    /**
     * Favorites the last selected word
     * @return String of what the user wants to do next
     */
    public String addFavoriteStatus(){
        this.displayWord(nav.addFavoriteStatus());
        return this.ask("Type go home, delete favorite, or quit: ");
    }

    /**
     * Un-favorites the last selected word
     * @return String of what the user wants to do next
     */
    public String deleteFavoriteStatus(){
        this.displayWord(nav.removeFavoriteStatus());
        return this.ask("Type go home, add favorite, or quit: ");
    }

    /**
     * Runs the app
     */
    public void runApp() {
        String response;
        State choice;
        boolean noQuit = true;
        response = this.home();

        while (noQuit) {
            choice = nav.navigateApp(response);
            switch(choice){
                case HOME:
                    response = this.home();
                    break;
                case SELECT_SIGN:
                    response = this.selectSign();
                    break;
                case SEARCH_SIGN:
                    response = this.searchSign();
                    break;
                case BROWSE_CATEGORY:
                    response = this.browseCategory();
                    break;
                case VIEW_FAVORITES:
                    response = this.viewFavorites();
                    break;
                case ADD_FAVORITE:
                    response = this.addFavoriteStatus();
                    break;
                case DELETE_FAVORITE:
                    response = this.deleteFavoriteStatus();
                    break;
                case QUIT:
                    noQuit = false;
                    break;
                case ERROR:
                    response = ask("Not a valid command. Try again: ");
                    break;
            }
        }
    }

    /**
     * Main method loads a few test words and starts the text app.
     */
    public static void main(String[] args) {
        List<String[]> contents = new ArrayList<String[]>();
        contents.add(new String[]{"milk", "team-1e/intellij/img/milk.gif", "food"});
        contents.add(new String[]{"apple", "team-1e/intellij/img/...gif", "food"});
        contents.add(new String[]{"big", "team-1e/intellij/img/...gif", "adjective"});
        contents.add(new String[]{"small", "team-1e/intellij/img/...gif", "adjective"});

        TextUI ui = new TextUI(contents);
        ui.runApp();

    }

}
