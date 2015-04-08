<<<<<<< HEAD
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
=======
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
>>>>>>> da0b266c97f477e35bd271440e35dfb60aa4dccd
