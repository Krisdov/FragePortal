package de.hrw.swep.votingservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hrw.swep.votingservice.persistence.DataStoreReadInterface;
import de.hrw.swep.votingservice.persistence.DataStoreWriteInterface;
import org.mockito.Mockito;

public class VotingServiceImplMockTest {
    private static final String STR_TEXT_OF_QUESTION_3 = "Wie finden Sie Games of Thrones?";
    VotingServiceInterface votingService;
    DataStoreReadInterface dbReadMock;
    DataStoreWriteInterface dbWriteMock;
    private final List<Integer> votes = new ArrayList<Integer>(Arrays.asList(5, 4, 5));

    @BeforeEach
    public void setUp() throws Exception {
        dbReadMock = Mockito.mock(DataStoreReadInterface.class);
        dbWriteMock = Mockito.mock(DataStoreWriteInterface.class);
        votingService = new VotingServiceImpl(dbReadMock, dbWriteMock);

        List<Integer> allQuestionsIds = new ArrayList<Integer>();
        allQuestionsIds.add(1);
        allQuestionsIds.add(2);
        allQuestionsIds.add(3);
        List<Question> questionList= new ArrayList<>();
        questionList.add(new Question(1, "Mögen Sie Schokoeis?", true, votes));
        questionList.add(new Question(2, "Wie finden Sie Sommerwetter mit blauem Himmel und 37 Grad?", true, votes));
        questionList.add(new Question(3, STR_TEXT_OF_QUESTION_3, true, votes));

        when(votingService.getAllQuestions()).thenReturn(questionList);
    }

    @Test
    public void testGetAllQuestions() {
        List<Question> questions = votingService.getAllQuestions();
        assertEquals(3, questions.size());

        boolean found = false;
        for (Question question : questions) {
            if (question.getId() == 3) {
                // control sample: Check question with id 3 in detail

                assertEquals(STR_TEXT_OF_QUESTION_3, question.getDescription());
                assertTrue(question.isOpenForVoting());

                List<Integer> votesForQuestion3 = question.getVotes();
                assertEquals(3, votesForQuestion3.size());

                // check votes in detail
                int votesWithAnswer5 = 0;
                int votesWithAnswer4 = 0;
                int votesNeither5Nor4 = 0;
                for (int i : votesForQuestion3) {
                    if (i == 5) {
                        votesWithAnswer5++;
                    } else if (i == 4) {
                        votesWithAnswer4++;
                    } else {
                        votesNeither5Nor4++;
                    }
                }
                assertEquals(2, votesWithAnswer5);
                assertEquals(1, votesWithAnswer4);
                assertEquals(0, votesNeither5Nor4);

                found = true;
            }
        }

        assertTrue(found);
    }

}
