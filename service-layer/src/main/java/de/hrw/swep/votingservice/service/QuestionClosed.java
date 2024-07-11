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

    public QuestionClosed(Question question) {
        // implementiert in anderem Zweig
    }

    @Override
    public void vote(int result) {
        // implementiert in anderem Zweig
    }

    @Override
    public boolean isOpenForVoting() {
        // implementiert in anderem Zweig
        return false;
    }

    @Override
    public void openForVoting() {
        // implementiert in anderem Zweig
    }

    @Override
    public void closeForVoting() {
        // implementiert in anderem Zweig
    }

}
