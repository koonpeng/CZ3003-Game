package cz3003.pptx.game;

import java.io.IOException;

import org.json.JSONException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @author wbw
 *
 */
/**
 * @author wbw
 *
 */
/**
 * @author wbw
 *
 */
public class CreateQuestion extends Stage {
	Skin skin;
	private static String[] ABCD = { "Question", "A", "B", "C", "D",
			"Answer(A/B/C/D)" };

	Texture backgroundtexture;
	ImageButton[] btnABCDTF;
	PPTXGame game;
	/* ******Label Control Part****** */

	LabelStyle style;
	BitmapFont font;
	Label lblQuestionNoLabel;
	Label[] lblABCD;
	Label lblF;
	Label lblT;
	// image
	Image nextimage;
	Image backimage;
	Image submitimage;

	// Test test;
	String[] question;
	int currentQuestionIndex;

	MyDungeonQuestion mydungeonquestion;
	int numberofquestions;

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.draw();
	}

	public CreateQuestion(int questionnumber)  {
		super();
		numberofquestions = questionnumber;
		currentQuestionIndex = 0;
		uiinit();
		try {
			mydungeonquestion=new MyDungeonQuestion("username",questionnumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// displaylblbut();

	}

	private void checkQuestion() {

	}

	private void saveQuestion() {
		String cusquestion[] = new String[7];
		cusquestion[0] = "B";
		cusquestion[1] = lblABCD[0].getText().toString();
		cusquestion[2] = lblABCD[1].getText().toString();
		cusquestion[3] = lblABCD[2].getText().toString();
		cusquestion[4] = lblABCD[3].getText().toString();
		cusquestion[5] = lblABCD[4].getText().toString();
		try {
			mydungeonquestion.addQns(cusquestion, currentQuestionIndex);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void backQuestion() {
		saveQuestion();
		currentQuestionIndex--;
	}

	private void nextQuestion() {
		saveQuestion();
		currentQuestionIndex++;
		try {
			String returnquestion[] = new String[7];
			returnquestion = mydungeonquestion.getQnsPos(currentQuestionIndex);
			if (returnquestion.length!=1) {
				if (currentQuestionIndex < numberofquestions - 1) {
					questionUIReset();
				} else if (currentQuestionIndex == numberofquestions - 1) {
					lastquestionUI();
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}

	}

	private void questionlabelReset() {
		lblABCD[0].setText("Question: Please Click to type in");
		lblABCD[1].setText("A: Please Click to type in");
		lblABCD[2].setText("B: Please Click to type in");
		lblABCD[3].setText("C: Please Click to type in");
		lblABCD[4].setText("D: Please Click to type in");
		lblABCD[5].setText("Answer(A/B/C/D): Please Click to type in");
	}

	private void lastquestionUI() {
		questionlabelReset();
		nextimage.setVisible(false);
		submitimage.setVisible(true);

	}

	private void questionUIReset() {
		questionlabelReset();
		submitimage.setVisible(false);
		nextimage.setVisible(true);
	}

	public void uiinit() {
		lblABCD = new Label[6];
		question = new String[6];
		style = new LabelStyle(CusFontStyle.getBoldFont(), CusFontStyle
				.getBoldFont().getColor());
		LabelStyle style2 = new LabelStyle(CusFontStyle.getNormalFont(),
				CusFontStyle.getNormalFont().getColor());

		lblini("Question: Please Click to type in", 0, 50, 800);
		/* ******Label Control A Part****** */
		lblini("A: Please Click to type in", 1, 70, 550);
		/* ******Label Control B Part****** */

		lblini("B: Please Click to type in", 2, 70, 500);

		/* ******Label Control C Part****** */

		lblini("C: Please Click to type in", 3, 70, 450);

		/* ******Label Control D Part****** */

		lblini("D: Please Click to type in", 4, 70, 400);
		lblini("Answer(A/B/C/D): Please Click to type in", 5, 70, 350);

		Texture texture = new Texture(ImgFile.nextquestion);

		nextimage = new Image(texture);
		nextimage.setPosition(458, 170);
		nextimage.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				nextQuestion();

				return true;
			}

		});
		Texture texture2 = new Texture(ImgFile.backquestion);
		backimage = new Image(texture2);
		backimage.setPosition(192, 170);
		backimage.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				backQuestion();

				return true;
			}

		});
		Texture texture3 = new Texture(ImgFile.submitquestion);
		submitimage = new Image(texture3);
		submitimage.setPosition(258, 170);
		submitimage.addListener(new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				return true;
			}

		});

		this.addActor(TopBar.getTopbar());
		this.addActor(nextimage);
		this.addActor(backimage);
		this.addActor(submitimage);
		nextimage.setVisible(true);
		submitimage.setVisible(false);
		backimage.setVisible(false);

	}

	// private void displaylblbut() {
	// if (test.getKind() == 0) {
	// lblA.setVisible(true);
	// lblB.setVisible(true);
	// lblC.setVisible(true);
	// lblD.setVisible(true);
	// lblT.setVisible(false);
	// lblF.setVisible(false);
	// btnABCDTF[0].setVisible(true);
	// btnABCDTF[1].setVisible(true);
	// btnABCDTF[2].setVisible(true);
	// btnABCDTF[3].setVisible(true);
	// btnABCDTF[4].setVisible(false);
	// btnABCDTF[5].setVisible(false);
	//
	// } else {
	// lblA.setVisible(false);
	// lblB.setVisible(false);
	// lblC.setVisible(false);
	// lblD.setVisible(false);
	// lblT.setVisible(true);
	// lblF.setVisible(true);
	// btnABCDTF[0].setVisible(false);
	// btnABCDTF[1].setVisible(false);
	// btnABCDTF[2].setVisible(false);
	// btnABCDTF[3].setVisible(false);
	// btnABCDTF[4].setVisible(true);
	// btnABCDTF[5].setVisible(true);
	// }
	// }
	private void lblini(final String answer, final int index, int x, int y) {
		lblABCD[index] = new Label(answer, style);
		lblABCD[index].setPosition(x, y);
		lblABCD[index].setWidth(600);
		lblABCD[index].setWrap(true);
		lblABCD[index].addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				Gdx.input.getTextInput(new TextInputListener() {
					@Override
					public void input(String texteSaisi) {
						question[index] = texteSaisi;
						lblABCD[index].setText(ABCD[index] + ":" + texteSaisi);

					}

					@Override
					public void canceled() {
						// TODO Auto-generated method stub

					}

				}, "Please input the " + ABCD[index], null, null);

				return true;
			}

		});
		this.addActor(lblABCD[index]);
	}
}

// private void buttoninit(final String answer, final int index, int x, int y) {
//
// Texture tex = new Texture(ImgFile.questionButton);
// TextureRegion[][] tem = TextureRegion.split(tex, 120, 120);
//
// TextureRegion buttonup = tem[0][0];
// buttonup.flip(true, false);
// TextureRegion buttondown = tem[0][1];
// buttondown.flip(true, false);
// TextureRegionDrawable up = new TextureRegionDrawable(buttonup);
// TextureRegionDrawable down = new TextureRegionDrawable(buttondown);
// btnABCDTF[index] = new ImageButton(up, down);
//
// btnABCDTF[index].setPosition(x, y);
// btnABCDTF[index].setSize(40, 40);
//
// btnABCDTF[index].addListener(new InputListener() {
// @Override
// public void touchUp(InputEvent event, float x, float y,
// int pointer, int button) {
// // music.play();
// // TODO Auto-generated method stub
// if (test.verifyAnswer(answer)) {
// btnABCDTF[0].setTouchable(Touchable.disabled);
// btnABCDTF[1].setTouchable(Touchable.disabled);
// btnABCDTF[2].setTouchable(Touchable.disabled);
// btnABCDTF[3].setTouchable(Touchable.disabled);
// btnABCDTF[4].setTouchable(Touchable.disabled);
// btnABCDTF[5].setTouchable(Touchable.disabled);
// fightcharacter.state = STATE.Act;
// monsterhp.minushp();
//
// } else {
// fightcharacter.state = STATE.Act;
// characterhp.minushp();
//
// }
// if (!monsterhp.isAlive()) {
// game.setScreen(game.resultscreen);
// } else if (!characterhp.isAlive()) {
// game.setScreen(game.resultscreen);
// } else {
// // updatequestion();
//
// }
//
// }
//
// @Override
// public boolean touchDown(InputEvent event, float x, float y,
// int pointer, int button) {
// return true;
// }
// });
// this.addActor(btnABCDTF[index]);
// }
// }
