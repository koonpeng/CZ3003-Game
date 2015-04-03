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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
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
	LabelStyle style2;
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

	public CreateQuestion(int questionnumber) {
		super();
		numberofquestions = questionnumber;
		currentQuestionIndex = 0;
		uiinit();
		try {
			mydungeonquestion = new MyDungeonQuestion("username",
					questionnumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// displaylblbut();

	}

	private void saveQuestion() {
		String cusquestion[] = new String[7];
		cusquestion[0] = "B";
		cusquestion[1] = lblABCD[0].getText().toString();
		cusquestion[2] = lblABCD[1].getText().toString();
		cusquestion[3] = lblABCD[2].getText().toString();
		cusquestion[4] = lblABCD[3].getText().toString();
		cusquestion[5] = lblABCD[4].getText().toString();
		cusquestion[6] = lblABCD[5].getText().toString();
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
		if (currentQuestionIndex == 0) {
			nextimage.setVisible(true);
			submitimage.setVisible(false);
			backimage.setVisible(false);
		} else {
			nextimage.setVisible(true);
			submitimage.setVisible(false);
			backimage.setVisible(true);
		}

		try {
			String returnquestion[] = new String[7];
			returnquestion = mydungeonquestion.getQnsPos(currentQuestionIndex);
			for (int i = 0; i < 6; i++) {
				lblABCD[i].setText(returnquestion[i + 1]);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void nextQuestion() {
		saveQuestion();
		currentQuestionIndex++;
		String returnquestion[] = new String[7];
		if (currentQuestionIndex == numberofquestions - 1) {

			nextimage.setVisible(false);
			submitimage.setVisible(true);
			backimage.setVisible(true);
		} else if (currentQuestionIndex < numberofquestions - 1) {
			submitimage.setVisible(false);
			nextimage.setVisible(true);
			backimage.setVisible(true);
		} else {
			nextimage.setVisible(true);
			submitimage.setVisible(false);
			backimage.setVisible(true);
		}

		try {

			returnquestion = mydungeonquestion.getQnsPos(currentQuestionIndex);
			if (returnquestion == null) {
				if (currentQuestionIndex < numberofquestions - 1) {

					questionlabelReset();

				}
				if (currentQuestionIndex == numberofquestions - 1) {

					questionlabelReset();

				}

			} else {
				for (int i = 0; i < 6; i++) {
					lblABCD[i].setText(returnquestion[i + 1]);

				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	private void questionlabelReset() {
		lblABCD[0].setText("Please Click to type in");
		lblABCD[1].setText("Please Click to type in");
		lblABCD[2].setText("Please Click to type in");
		lblABCD[3].setText("Please Click to type in");
		lblABCD[4].setText("Please Click to type in");
		lblABCD[5].setText("Please Click to type in");
	}

	/**
	 * 
	 */
	public void uiinit() {
		lblABCD = new Label[6];
		question = new String[6];
		style = new LabelStyle(CusFontStyle.getBoldFont(), CusFontStyle
				.getBoldFont().getColor());
		style2 = new LabelStyle(CusFontStyle.getNormalFont(),
				CusFontStyle.getNormalFont().getColor());

		lblini("Please Click to type in", 0, 50, 900);
		/* ******Label Control A Part****** */
		lblini("Please Click to type in", 1, 70, 600);
		/* ******Label Control B Part****** */

		lblini("Please Click to type in", 2, 70, 520);

		/* ******Label Control C Part****** */

		lblini("Please Click to type in", 3, 70, 440);

		/* ******Label Control D Part****** */

		lblini("Please Click to type in", 4, 70, 360);
		lblini("Please Click to type in", 5, 70, 280);

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
				mydungeonquestion.commitQns();
				PPTXGame.toQuestionListScreen();
				return true;
			}

		});
		
		String strindex[]={"Question: ","A: ","B: ","C: ","D: ","Answer: "};
		Label lblABCDindex[] = new Label[6];
		for (int i = 0; i < 6; i++) {
			lblABCDindex[i] = new Label(strindex[i], style);
			lblABCDindex[i].setWidth(200);
			
			//lblABCDindex[i].setWrap(true);
		}
		
		
		Group group=new Group();
		

		//this.add(attributeTable).expand().fill().padRight(5f);

		
		Table table = new Table();
		table.setPosition(380,700);
		table.add(lblABCDindex[0]).width(190) ;
		table.add(lblABCD[0]).width(500);
		Label lblheight=new Label(": ",style);
		lblheight.setHeight(200);
		table.add(lblheight).height(200);
		table.row();
		Label lbloption=new Label("Options: ",style);
		table.add(lbloption).align(Align.left);
		for (int i = 1; i < 6; i++) {
			table.row();
			table.add(lblABCDindex[i]).width(190);
			
			
			table.add(lblABCD[i]).width(500);
		}
		
		
		this.addActor(table);
		
	
	


		this.addActor(TopBar.getTopbar());
		this.addActor(nextimage);
		this.addActor(backimage);
		this.addActor(submitimage);
		//logicaltable.addActor(table);
	
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
			lblABCD[index] = new Label(answer, style2);
			//lblABCD[index].setPosition(x, y);
			lblABCD[index].setWidth(500);
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
						lblABCD[index].setText(texteSaisi);
						
					}

					@Override
					public void canceled() {
						// TODO Auto-generated method stub

					}

				}, "Please input the " + ABCD[index], null, null);

				return true;
			}

		});
		// this.addActor(lblABCD[index]);
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
