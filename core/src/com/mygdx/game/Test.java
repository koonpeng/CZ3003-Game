package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Test {
	private int questionsetno = 0;
	private int readindex;
	private String[] lines;
	private String[] question;
	private int answerindex;
	private String[] answerlines;
	private int kind=1;
	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}
	private static  int score=0;

	public static int getScore() {
		return score;
	}

	public static void setScore(int s) {
		score = s;
	}

	public Test(int questionsetno) {
		this.questionsetno = questionsetno;
		score=0;
		readindex=0;
		answerindex=0;
		question=new String[5];
		lines=null;
		answerlines=null;
		QuestionSetreading();
		AnswerSetreading();
		nextquestion();
		

	}
	private void AnswerSetreading() {
		switch (questionsetno) {
		case 0:

			FileHandle file = Gdx.files.internal("Answers0.txt");
			String text = file.readString();
			answerlines = text.split("\\r?\\n");

			break;
		case 1:
			break;
		}
		
		
	}
	
	private void QuestionSetreading() {
		switch (questionsetno) {
		case 0:

			FileHandle file = Gdx.files.internal("Questions0.txt");
			String text = file.readString();
			lines = text.split("\\r?\\n");

			break;
		case 1:
			break;
		}
	}

	public void nextquestion() {
		
		if (readindex >=lines.length) {
			//question = null;
			readindex=0;
			answerindex=0;
		} else {
			for (int i = 0; i < 5; i++) {
				if(i==0){
					question[i] = "Question "+(readindex+5)/5+": "+lines[readindex++];
				}
				else{
				question[i] = lines[readindex++];
				}

			}
		}

	}
	public boolean verifyAnswer(String useranswer)
	{
		if(useranswer.equals(answerlines[answerindex++]))
		{
			score++;
			return true;
		}
		return false;
	}
	public String[] getQuestion() {
		return question;
	}
}
