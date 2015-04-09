
package cz3003.pptx.game.battle.quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Quiz {

	private static String[] qnaList = new String[20];
	private int count = 0;
	private JSONObject qns_current = new JSONObject();
	private Score current;

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Create class
	public Quiz(int difficulty, int dungeon) {
		try {
			current = new Score();
			int file = dungeon * 10 + difficulty;
			String fileName = "quiz/" + file + ".txt";
			FileHandle f = Gdx.files.internal(fileName);
			qnaList = readFile(f);
			randomQns();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Quiz(int difficulty, int dungeon, String id) {
		try {
			current = new Score();
			String file = id;
			String fileName = "quiz/" + file + "student.txt";
			FileHandle f = Gdx.files.internal(fileName);
			qnaList = readFile(f);
			randomQns();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functions
	private static void randomQns() {
		Collections.shuffle(Arrays.asList(qnaList));
	}

	public String[] getQuestion() {
		try {
			String[] qns = null;
			if (qnaList[count] != (null)) {
				JSONObject qna = new JSONObject(qnaList[count++]);
				qns_current = qna;
				if (qna.getString("Type").equals("A")) {
					qns = new String[2];
					qns[0] = "A";
					qns[1] = qna.getString("Question");
				} else if (qna.getString("Type").equals("B")) {
					qns = new String[6];
					qns[0] = "B";
					qns[1] = qna.getString("Question");
					qns[2] = qna.getString("A");
					qns[3] = qna.getString("B");
					qns[4] = qna.getString("C");
					qns[5] = qna.getString("D");
				}
			} else {
				reset();
				qns = getQuestion();
			}
			return qns;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getScore() {
		return current.getScore();
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Utilities
	private void reset() {
		this.count = 0;
		randomQns();
	}

	private static String[] readFile(FileHandle f) throws IOException {
		BufferedReader br = new BufferedReader(f.reader());
		String[] qna = new String[20];
		String line = null;
		int i = 0;
		while ((line = br.readLine()) != null) {
			qna[i] = line;
			
			i++;
		}

		br.close();
		return qna;
	}

	public boolean verifyAnswer(String answer) {
		try {
			String check_answer = qns_current.getString("Answer");
			if (check_answer.equalsIgnoreCase(answer)) {
				current.addScore(true);
				return true;
			} else {
				current.addScore(false);
				return false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

}
