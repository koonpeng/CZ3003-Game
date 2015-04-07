package cz3003.pptx.game.battle.quiz;

public class Score {

	private int qns = -1;
	private int correct = -1;

	public Score() {
		this.qns = 0;
		this.correct = 0;
	}

	public void addScore(Boolean result) {
		if (result) {
			this.correct++;
		}
		this.qns++;
	}

	public int getScore() {
		return (correct / qns);
	}
}
