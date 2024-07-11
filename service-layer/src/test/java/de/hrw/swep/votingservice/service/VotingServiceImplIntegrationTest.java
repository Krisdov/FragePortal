/**
 * 
 */
package de.hrw.swep.votingservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.hsqldb.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hrw.swep.votingservice.dbloader.InitialLoad;
import de.hrw.swep.votingservice.persistance.RealDatabase;

/**
 * TODO A9a): Die Testklasse soll VotingServiceImpl als Integrationstest testen, es fehlt aber ein
 * Teil der Initialisierung: Das Attribut votingService wird bisher nicht initialisiert.
 * Identifizieren Sie die richtige Methode für diese zentrale Initialisierung und fügen Sie den
 * notwendigen Code dort so ein, dass das Attribut richtig initialisiert wird und das
 * VotingServiceImpl -Objekt enthält, dass für die Tests verwendet wird.
 * 
 * @author andriesc
 *
 */
public class VotingServiceImplIntegrationTest {
    private static final String STR_NEW_QUESTION = "new question";

    VotingServiceInterface votingService;
    DataStoreReadInterface dsri;
    DataStoreWriteInterface dswi;

    private final boolean[] questionsStates = { false, false, false, true, true };
    private final double[] questionsTotalResults = { 2.33333325, 4.66666650, 4.66666650, 3.33333325,
        4.666667 };

    // id of question in database is its position in this array
    private final String[] questionsDescriptions = { "Mögen Sie Schokoeis?",
        "Wie finden Sie Sommerwetter mit blauem Himmel und 37 Grad?",
        "Wie finden Sie Games of Thrones?", "Trinken Sie gerne Softdrinks?",
        "Mögen Sie Wassersport?" };

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        dsri = new DataStoreReadInterface();
        dswi = new DataStoreWriteInterface();
        votingService = new VotingServiceImpl(dsri, dswi);
        InitialLoad.main(null);
    }

    @AfterEach
    public void tearDown() throws Exception {
        votingService.close();
    }

    boolean checkVotes(List<Integer> votes) {
        int votesWith5 = 1;
        int votesWith3 = 1;
        int votesWith2 = 1;
        int votesWrong = 0;
        for (int i : votes) {
            if (i == 5) {
                votesWith5--;
            } else if (i == 3) {
                votesWith3--;
            } else if (i == 2) {
                votesWith2--;
            } else {
                votesWrong++;
            }
        }
        return (votesWith5 == 0 && votesWith3 == 0 && votesWith2 == 0 && votesWrong == 0);
    }

    @Test
    public void testGetAllQuestions() {
        List<Question> allQuestions = votingService.getAllQuestions();
        assertEquals(5, allQuestions.size());

        boolean found = false;
        for (Question question : allQuestions) {
            assertEquals(questionsDescriptions[question.getId() - 1], question.getDescription());
            assertEquals(questionsStates[question.getId() - 1], question.isOpenForVoting());
            if (question.getId() == 5) {
                try {
                    @SuppressWarnings("unused")
                    float f = question.getAverageVote();
                    fail();
                } catch (IllegalStateException e) {
                    assertEquals("No votes.", e.getMessage());
                }
            } else {
                assertEquals(questionsTotalResults[question.getId() - 1], question.getAverageVote(),
                    0.0000002);
                if (question.getId() == 4) {
                    found = true;
                    assertTrue(checkVotes(question.getVotes()));
                }
            }
        }

        assertTrue(found);
    }

    @Test
    public void testStoreQuestion() {
        List<Question> allQuestions = votingService.getAllQuestions();

        boolean found = false;
        Question newQuestion = null;
        for (Question question : allQuestions) {
            if (question.getId() == 4) {
                found = true;
                newQuestion = new Question(question.getId(), STR_NEW_QUESTION,
                    question.isOpenForVoting(), question.getVotes());
            }
        }
        assertTrue(found);

        votingService.storeQuestion(newQuestion);

        allQuestions = votingService.getAllQuestions();
        found = false;
        for (Question question : allQuestions) {
            if (question.getId() == 4) {
                found = true;
                assertEquals(STR_NEW_QUESTION, question.getDescription());
                assertTrue(question.isOpenForVoting());

                // check votes en detail
                assertEquals(questionsTotalResults[4 - 1], question.getAverageVote(), 0.0000002);
                List<Integer> votes = question.getVotes();
                assertEquals(3, votes.size());
                checkVotes(votes);
            }
        }

        assertTrue(found);
    }

    @Test
    public void testDeleteQuestion() {
        List<Question> allQuestions = votingService.getAllQuestions();
        Question question = allQuestions.iterator().next();
        assertTrue(allQuestions.contains(question));

        votingService.deleteQuestion(question);

        allQuestions = votingService.getAllQuestions();
        assertFalse(allQuestions.contains(question));
    }

    @Test
    public void testVoteFor() {
        List<Question> allQuestions = votingService.getAllQuestions();
        Question question = allQuestions.get(1);
        assertEquals(questionsDescriptions[1], question.getDescription());
        assertEquals(4.6667, question.getAverageVote(), 0.0001);
        try {
            votingService.voteFor(question, 1);
        } catch (IllegalStateException e) {
            assertEquals("Invalid vote.", e.getMessage());
            question = allQuestions.get(3);
            assertEquals(3.3333, question.getAverageVote(), 0.0001);
            votingService.voteFor(question, 0);
            votingService.voteFor(question, 2);
            votingService.voteFor(question, 3);
            votingService.voteFor(question, 1);
            votingService.voteFor(question, 4);
            votingService.voteFor(question, 4);
            votingService.voteFor(question, 5);
            assertEquals(2.9000, question.getAverageVote(), 0.0001);
            return;
        }
        fail();

    }

}
