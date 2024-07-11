package de.hrw.swep.votingservice.service;

/**
 * 
 * @author andriesc
 *
 */
public interface QuestionStatusInterface {

    /**
     * Vote for question with numbers from 0 (strong disagree) to 5 (strong agree) -- not less, not
     * more. Method should be called only from a VotingServiceInterface.
     * 
     * Method throws RuntimeException if <code>result < 0</code> or <code>result > 5</code>.
     * 
     * @param result
     *            the vote to be recorded for this question.
     */
    void vote(int result);

    /**
     * 
     * @return <tt>true</tt> if the question is open for voting, <tt>false</tt> otherwise
     */
    boolean isOpenForVoting();

    /**
     * open the question for voting
     */
    void openForVoting();

    /**
     * close the question for voting
     */
    void closeForVoting();
}
