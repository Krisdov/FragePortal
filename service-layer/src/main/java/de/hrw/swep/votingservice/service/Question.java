package de.hrw.swep.votingservice.service;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO A7 (NUR IM ZWEIG FEATURE!): Dieses Geschäftsobjekt soll das State-Pattern zum Umgang mit
 * seinen Zuständen „offen“ (QuestionOpen) und „geschlossen“ (QuestionClosed) verwenden. Die
 * Zustände und ihre Übergänge zeigt Abbildung 3. Die Implementierung ist für das Objekt Question
 * schon beendet, für QuestionOpen und QuestionClosed allerdings erst begonnen: Die Methoden dieser
 * beiden Klassen sind alle automatisch generiert und müssen noch implementiert
 * werden.Vervollständigen Sie die Implementierung. Beachten Sie dabei:
 * 
 * - Klasse QuestionClosed, Methode vote() wirft bei jedem Aufruf eine IllegalStateException.
 * 
 * - Klasse QuestionOpen, Methode vote() wirft eine RuntimeException, wenn der Parameter vote
 * kleiner als 0 oder größer als 5 ist.
 * 
 * Tipp: Das Attribut votes von Question hat die Sichtbarkeit protected, kann also aus Klassen, die
 * Zustände kapseln und im selben Package wie Question liegen, direkt verwendet werden.
 * 
 * 
 * @author andriesc
 *
 */
public class Question {
    private int id;
    private String description;
    private QuestionStatusInterface status;

    // visibility is protected instead of private because of State pattern
    protected List<Integer> votes;

    /**
     * Initialize a new question
     * 
     * @param id
     *            the question's unique id
     * @param description
     *            the question put into words
     * @param open
     *            <tt>true</tt> if open for voting, <tt>false</tt> otherwise
     * @param votes
     *            list of any existing votes (0..5) for this question
     */
    public Question(int id, String description, boolean open, List<Integer> votes) {
        this.id = id;
        this.description = description;

        if (votes == null) {
            this.votes = new ArrayList<Integer>();
        } else {
            this.votes = new ArrayList<Integer>(votes);
        }

        if (open) {
            setCurrentState(new QuestionOpen(this));
        } else {
            setCurrentState(new QuestionClosed(this));
        }
    }

    void setCurrentState(QuestionStatusInterface newStatus) {
        this.status = newStatus;
    }

    /**
     * 
     * @return the name of the current state class
     */
    public String getCurrentState() {
        return status.getClass().getName();
    }

    /**
     * Vote for question with numbers from 0 (strong disagree) to 5 (strong agree) -- not less, not
     * more. Method should be called only from a VotingServiceInterface.
     * 
     * @param result
     *            Number between or equal to 0 and 5
     */
    void vote(int result) {
        status.vote(result);
    }

    /**
     * 
     * @return the average for all votes for this question
     */
    public float getAverageVote() {
        if (votes.size() == 0) {
            throw new IllegalStateException("No votes.");
        } else {
            int sum = 0;
            for (int i : votes) {
                sum += i;
            }
            return ((float) sum / votes.size());
        }
    }

    /**
     * 
     * @return the question's id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @return the question put into words
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @return list of any votes for this question
     */
    public List<Integer> getVotes() {
        return votes;
    }

    /**
     * 
     * @return <tt>true</tt> if this question can be voted for, <tt>false</tt> otherwise
     */
    public boolean isOpenForVoting() {
        return status.isOpenForVoting();
    }

}
