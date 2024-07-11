/**
 * 
 */
package de.hrw.swep.votingservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author andriesc
 *
 */
public class QuestionTest {
	private Question question;
	private List<Integer> votes = Arrays.asList(5, 5, 5, 5, 3, 2, 1);
	private String description = "Mögen Sie Möhrenbrei?";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		question = new Question(0, description, true, votes);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	public void tearDown() throws Exception {
		question = null;
	}

	@Test
	public void testCreateQuestion() {
		assertEquals(0, question.getId());
		assertEquals(description, question.getDescription());
		assertEquals(votes, question.getVotes());
		assertEquals(3.71, question.getAverageVote(), 0.01);
	}

	@Test
	public void testChangeQuestionStatus() {
		assertEquals(QuestionOpen.class.getName(), question.getCurrentState());
		question.setCurrentState(new QuestionOpen(question));
		assertEquals(QuestionOpen.class.getName(), question.getCurrentState());

		question.setCurrentState(new QuestionClosed(question));
		assertEquals(QuestionClosed.class.getName(), question.getCurrentState());
		assertEquals(0, question.getId());
		assertEquals(description, question.getDescription());
		assertEquals(votes, question.getVotes());
		assertEquals(3.71, question.getAverageVote(), 0.01);

		question.setCurrentState(new QuestionOpen(question));
		assertEquals(QuestionOpen.class.getName(), question.getCurrentState());

		assertEquals(0, question.getId());
		assertEquals(description, question.getDescription());
		assertEquals(votes, question.getVotes());
		assertEquals(3.71, question.getAverageVote(), 0.01);
	}

	@Test
	public void testVoteForOpenQuestion() {
		question.setCurrentState(new QuestionOpen(question));
		assertEquals(QuestionOpen.class.getName(), question.getCurrentState());
		assertEquals(votes, question.getVotes());
		assertEquals(3.71, question.getAverageVote(), 0.01);
		question.vote(5);
		question.vote(4);
		question.vote(1);
		assertEquals(3.59, question.getAverageVote(), 0.01);
	}

	@Test
	public void testVoteForClosedQuestion() {
		question.setCurrentState(new QuestionClosed(question));
		assertEquals(QuestionClosed.class.getName(), question.getCurrentState());
		assertEquals(votes, question.getVotes());
		assertEquals(3.71, question.getAverageVote(), 0.01);
		try {
			question.vote(5);
		} catch (IllegalStateException e) {
			assertEquals("Invalid vote.", e.getMessage());
			assertEquals(votes, question.getVotes());
			assertEquals(3.71, question.getAverageVote(), 0.01);
			return;
		}
		fail("Vote was accepted, this should not have happened.");
	}

	@Test
	public void testOpenQuestionGetVotes() {
		question.setCurrentState(new QuestionOpen(question));
		assertEquals(QuestionOpen.class.getName(), question.getCurrentState());
		assertEquals(votes, question.getVotes());
	}
	
	@Test
	public void testGetAverageVote() {
		question.setCurrentState(new QuestionOpen(question));
		assertEquals(3.71, question.getAverageVote(), 0.01);		
		question.setCurrentState(new QuestionClosed(question));
		assertEquals(3.71, question.getAverageVote(), 0.01);		
	}

	@Test
	public void testClosedQuestionGetVotes() {
		question.setCurrentState(new QuestionClosed(question));
		assertEquals(QuestionClosed.class.getName(), question.getCurrentState());
		assertEquals(votes, question.getVotes());
	}
}
