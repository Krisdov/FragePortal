package de.hrw.swep.votingservice.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataStoreReadInterfaceTest {

    private RealDatabase db;

    private final int[] questionIds = { 1, 2, 3, 4, 5 };

    // id of question in database is its position in this array
    private final String[] questionsDescriptions = { "", "Mögen Sie Schokoeis?",
        "Wie finden Sie Sommerwetter mit blauem Himmel und 37 Grad?",
        "Wie finden Sie Games of Thrones?", "Trinken Sie gerne Softdrinks?",
        "Mögen Sie Wassersport?" };

    private final int[] votesForID2 = { 5, 4, 5 };

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        db = new RealDatabase();
    }

    @Test
    public void testGetAllQuestions() {
        List<Integer> allQuestions = db.getAllQuestions();
        assertEquals(5, allQuestions.size());
        for (int i = 0; i <= 4; i++) {
            assertEquals(questionIds[i], allQuestions.get(i).intValue());
        }
    }

    @Test
    public void testGetTextOfQuestion() {
        List<Integer> allQuestions = db.getAllQuestions();
        assertEquals(5, allQuestions.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(questionsDescriptions[allQuestions.get(i)],
                db.getTextOfQuestion(allQuestions.get(i)));
        }
    }

    @Test
    public void testGetVotesForGericht() {
        List<Integer> votes = db.getVotesForQuestion(2);
        assertNotNull(votes);
        assertEquals(3, votes.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(votesForID2[i], votes.get(i).intValue());
        }
    }

    @Test
    public void testGetStatusOfQuestion() {
        assertTrue(db.getStatusOfQuestion(4));
        assertTrue(db.getStatusOfQuestion(5));
        assertFalse(db.getStatusOfQuestion(1));
        assertFalse(db.getStatusOfQuestion(2));
        assertFalse(db.getStatusOfQuestion(3));
    }
}
