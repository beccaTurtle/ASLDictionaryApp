package edu.vassar.cmpu203.asl.persistence;

import android.util.Log;

import edu.vassar.cmpu203.asl.model.SignDictionary;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Stores favorites locally
 */
public class LocalStorageFacade  implements IPersistenceFacade{

    private File fullFile; // where the files are to be stored

    private final File directory;
    private static final String FULL_FNAME = "dictionary.asl";

    /**
     * Constuctor
     * @param directory where the file should go
     */
    public LocalStorageFacade(File directory){
        this.directory = directory;
        this.fullFile = new File(this.directory, FULL_FNAME);
    }

    /**
     * Saves full
     * @param full SignDictionary to be saved
     */
    @Override
    public void saveDictionary(SignDictionary full) {

        try {
            FileOutputStream fos = new FileOutputStream(this.fullFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(full); // write object to file

        } catch (IOException e) { // issues writing to underlying file
            final String emsg = String.format("I/O error writing to %s", this.fullFile);
            Log.e("asl", emsg);
            e.printStackTrace();
        }

    }



    /**
     * Retrieves full
     * @return SignDictionary full
     */
    @Override
    public SignDictionary retrieveDictionary() {
        SignDictionary favorites = null; // null to begin with for negative outcome

        if (fullFile.isFile()) { // must check that the file actually exists
            try {
                FileInputStream fileInStream = new FileInputStream(fullFile);
                ObjectInputStream objectInStream = new ObjectInputStream(fileInStream);
                favorites = (SignDictionary) objectInStream.readObject(); // read object, must downcast from Object
            }
            catch (IOException e) { // issues reading from underlying file
                final String emsg = String.format("I/O error writing to %s", this.fullFile);
                Log.e("asl", emsg);
                e.printStackTrace();
            } catch (ClassNotFoundException e) { // can happen if codebase doesn't include class of object being read
                final String emsg = String.format("Can't find class of object from %s", this.fullFile);
                Log.e("asl", emsg);
                e.printStackTrace();
            }
        }
        return favorites;
    }
}
