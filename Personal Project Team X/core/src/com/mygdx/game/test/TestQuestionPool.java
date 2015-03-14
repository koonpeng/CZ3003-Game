package com.mygdx.game.test;

import com.mygdx.game.Question;

public class TestQuestionPool {

	private int curQuestion = 1;

	public Question getNextQuestion() {
		String questionTitle = "Question " + curQuestion + ": This is a test question";
		String[] answers = new String[4];
		answers[0] = "A";
		answers[1] = "B";
		answers[2] = "C";
		answers[3] = "D";
		curQuestion++;
		return new Question(questionTitle, answers, 0);
	}

}
