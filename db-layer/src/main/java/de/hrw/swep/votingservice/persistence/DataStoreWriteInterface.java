package de.hrw.swep.votingservice.persistence;

import java.util.List;

/**
 * 
 * <code>DataStoreWriteInterface</code> provides services to store or modify voting data in the
 * database.
 * 
 * @author andriesc
 *
 */
public interface DataStoreWriteInterface {

    /**
     * 
     * Adds a vote for question <code>id</code>. Raises PersistenceException, if question does not
     * exist.
     * 
     * @param id
     *            question being voted vor
     * @param vote
     *            vote (0-5)
     */
    void addVote(int id, int vote);

    /**
     * Deletes all votes for question <code>id</code>.
     * 
     * @param id
     *            question the votes belong to
     * 
     */
    void deleteVotes(int id);

    /**
     * 
     * If question having ID <code>id</code> does exist in the database, it will be updated with the
     * new data given ("UPdate"). Otherwise, it will inserted ("inSERT").
     * 
     * @param id
     *            ID of question to be stored -- ID of -1 will cause an Insert operation
     * @param description
     *            Description of question
     * @param status
     *            The question's state des Gerichts -- can it be voted for ( <code>true</code>) or
     *            is it closed for voting (<code>false</code>)?
     * @param votes
     *            All votes to store for the questions. Will overwrite all existing votes for this
     *            question in the database.
     */
    void upsertQuestion(int id, String description, boolean status, List<Integer> votes);

    /**
     * 
     * Deletes question having ID <code>id</code> in the database.
     * 
     * @param id
     *            Question to be deleted
     */
    void deleteQuestion(int id);
}
