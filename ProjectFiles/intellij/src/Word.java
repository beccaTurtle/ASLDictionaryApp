import java.util.*;

/**
 * Representation of an entry in an online translation dictionary between English and ASL
 */
public class Word {
    private String english;
    private String asl;
    private boolean isFavorite = false;

    /**
     * Constructor creates a word
     * @param english : text for the word
     * @param path : location of a gif of its sign
     */
    public Word(String english, String path){
        this.english = english;
        this.asl = path;
    }

    /**
     * Changes if the word is favorited or not
     * @param newStatus : updated value for isFavorite
     */
    public void switchFavoriteStatus(boolean newStatus){
        isFavorite = newStatus;
    }

    /**
     * Gets the english text of the word
     * @return String english word
     */
    public String getEnglish(){
        return english;
    }

    /**
     * Gets the path to the asl gif
     * @return String path
     */
    public String getASL(){
        return asl;
    }

    /**
     * Asks if the sign is a favorite
     * @return boolean isFavorite
     */
    public boolean getFavoriteStatus(){
        return isFavorite;
    }

}