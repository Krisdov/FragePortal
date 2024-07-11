package de.hrw.swep.votingservice.persistence;

import java.sql.SQLException;
import java.util.List;

/**
 * <code>DataStoreReadInterface</code> provides services to get voting data from the database.
 * 
 * @author andriesc
 *
 */
public interface DataStoreReadInterface {

    /**
     * 
     * Returns the IDs of all questions stored in the database.
     * 
     * @return <code>List</code> of all IDs of all questions stored in the database as
     *         <code>Integer</code>
     */
    List<Integer> getAllQuestions();

    /**
     * 
     * Returns from the database the description of the question given by its ID.
     * 
     * @param id
     *            the question's <code>id</code>
     * @return description of the question having ID <code>id</code>
     */
    String getTextOfQuestion(int id);

    /**
     * 
     * Returns from the database all votes for the question given by its ID.
     * 
     * @param id
     *            the question's <code>id</code>
     * @return all votes for question having ID <code>id</code>
     */
    List<Integer> getVotesForQuestion(int id);

    /**
     * 
     * Returns from the database the status (open for voting or closed) of the question given by its
     * ID.
     * 
     * @param id
     *            the question's <code>id</code>
     * @return <code>true</code>, if question is open for votes and <code>false</code>, if not
     */
    boolean getStatusOfQuestion(int id);

    /**
     * Must be called to release any resources after database is not being used anymore.
     * 
     * @throws SQLException
     *             in case of any errors during release of database resources
     */
    void close() throws SQLException;
}
