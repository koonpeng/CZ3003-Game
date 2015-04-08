package cz3003.pptx.game.battle.quiz;

public class Score {

	private double qns = -1;
	private double correct = -1;

	public Score() {
		qns = 0;
		correct = 0;
	}

	public void addScore(Boolean result) {
		if (result) {
			correct++;
		}
		qns++;
	}

	public int getScore() {
		double result=(correct / qns)*100f;
		return ((int)result);
	}
}
