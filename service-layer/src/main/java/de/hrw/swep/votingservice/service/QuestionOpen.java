package de.hrw.swep.votingservice.service;

/**
 * 
 * @author andriesc
 *
 */
public class QuestionOpen implements QuestionStatusInterface {
    private Question question;
    private final String ALLREADY_OPENED = "Questions has already been opened.";

    public QuestionOpen(Question question) {
        question = this.question;
    }

    @Override
    public void vote(int result) {
        question.votes.add(result);
    }

    @Override
    public boolean isOpenForVoting() {
        return true;
    }

    @Override
    public void openForVoting() {
        throw new IllegalStateException(ALLREADY_OPENED);
    }

    @Override
    public void closeForVoting() {
        question.setCurrentState(new QuestionClosed(question));
    }
}
