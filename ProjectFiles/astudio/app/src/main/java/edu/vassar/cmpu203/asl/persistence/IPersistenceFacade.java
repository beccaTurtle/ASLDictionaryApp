package edu.vassar.cmpu203.asl.persistence;
import edu.vassar.cmpu203.asl.model.*;

/**
 * Interface that specifies a contract that all persistence solutions must fulfill.
 */
public interface IPersistenceFacade {

    /**
     * Saves full
     * @param full SignDictionary to be saved
     */
    void saveDictionary(SignDictionary full);

    /**
     * Retrieves full
     * @return SignDictionary full
     */
    SignDictionary retrieveDictionary();

}
