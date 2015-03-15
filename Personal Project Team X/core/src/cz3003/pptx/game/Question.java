package cz3003.pptx.game;

public class Question {

	private final String question;
	private final String[] choices;
	private final int correctAns;

	public Question(String question, String[] choices, int correctAns) {
		super();
		this.question = question;
		this.choices = choices;
		this.correctAns = correctAns;
	}

	public String getQuestion() {
		return question;
	}

	public String[] getChoices() {
		return choices;
	}

	public int getAnswer() {
		return correctAns;
	}

}
