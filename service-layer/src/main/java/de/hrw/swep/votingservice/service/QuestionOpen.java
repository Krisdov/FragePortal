package de.hrw.swep.votingservice.service;

/**
 * 
 * @author andriesc
 *
 */
public class QuestionOpen implements QuestionStatusInterface {
    private Question question;

    public QuestionOpen(Question question) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void vote(int result) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isOpenForVoting() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void openForVoting() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void closeForVoting() {
        // TODO Auto-generated method stub
        
    }

}
