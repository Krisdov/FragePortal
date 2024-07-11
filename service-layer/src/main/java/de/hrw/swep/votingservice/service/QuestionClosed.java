package de.hrw.swep.votingservice.service;

/**
 * TODO A12a) (NUR IM ZWEIG NEWFEATURE!): Entfernen Sie alle Kommentare „// T O D O  Auto-generated
 * method stub“ und ersetzen Sie diese jeweils durch den Kommentar „// implementiert in anderem
 * Zweig“.
 * 
 * @author andriesc
 *
 */
public class QuestionClosed implements QuestionStatusInterface {
    private Question question;
    private String ALLREADY_CLOSED = "Questions has already been closed.";

    public QuestionClosed(Question question) {
        question = this.question;
    }

    @Override
    public void vote(int result) {
        throw new IllegalStateException(ALLREADY_CLOSED);
    }

    @Override
    public boolean isOpenForVoting() {
        return false;
    }

    @Override
    public void openForVoting() {
        question.setCurrentState(new QuestionOpen(question));
    }

    @Override
    public void closeForVoting() {
        throw new IllegalStateException(ALLREADY_CLOSED);
    }

}
