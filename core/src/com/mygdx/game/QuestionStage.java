package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.FightCharacter.STATE;

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
public class QuestionStage extends Stage {

	/* Hp part */
	Hp monsterhp;
	Hp characterhp;

	Texture backgroundtexture;
	ImageButton[] btnABCDTF;
	MyGdxGame game;
	/* ******Label Control Part****** */
	Stage stage;
	LabelStyle style;
	BitmapFont font;
	Label lblTitle;
	Label lblA;
	Label lblB;
	Label lblC;
	Label lblD;

	Label lblF;
	Label lblT;

	Test test;
	/* Character */
	FightCharacter fightcharacter;
	Dragon dragon;
	// sound part
	Sound music;

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		super.draw();
		if (fightcharacter.state == STATE.End) {
			fightcharacter.state = STATE.Idel;
			btnABCDTF[0].setTouchable(Touchable.enabled);
			btnABCDTF[1].setTouchable(Touchable.enabled);
			btnABCDTF[2].setTouchable(Touchable.enabled);
			btnABCDTF[3].setTouchable(Touchable.enabled);
			btnABCDTF[4].setTouchable(Touchable.enabled);
			btnABCDTF[5].setTouchable(Touchable.enabled);
		} else if (fightcharacter.state == STATE.Act) {
			btnABCDTF[0].setTouchable(Touchable.disabled);
			btnABCDTF[1].setTouchable(Touchable.disabled);
			btnABCDTF[2].setTouchable(Touchable.disabled);
			btnABCDTF[3].setTouchable(Touchable.disabled);
			btnABCDTF[4].setTouchable(Touchable.disabled);
			btnABCDTF[5].setTouchable(Touchable.disabled);
		}
	}

	public QuestionStage(MyGdxGame game, int dungonid) {
		super();
		this.game = game;
		test = new Test(dungonid);
		uiinit();
		displaylblbut();
		monsterhp = new Hp(420, 1000, 3);
		characterhp = new Hp(200, 1200, 3);
		this.addActor(monsterhp);
		this.addActor(characterhp);
		this.addActor(monsterhp.lblhp);

		this.addActor(characterhp.lblhp);
		dragon = new Dragon(450, 800);
		this.addActor(dragon);
		this.addActor(dragon.imgdragon);
		fightcharacter = new FightCharacter(20, 800);
		this.addActor(fightcharacter);

	}

	private void updatequestion() {

		test.nextquestion();
		String question[] = test.getQuestion();
		displaylblbut();
		lblTitle.setText(question[0]);
		lblA.setText(question[1]);
		lblB.setText(question[2]);
		lblC.setText(question[3]);
		lblD.setText(question[4]);

	}

	public void uiinit() {

		style = new LabelStyle(CusFontStyle.getBoldFont(), CusFontStyle
				.getBoldFont().getColor());
		LabelStyle style2 = new LabelStyle(CusFontStyle.getNormalFont(),
				CusFontStyle.getNormalFont().getColor());
		String question[] = test.getQuestion();

		/* ******Label Control Title Part****** */
		lblTitle = new Label(question[0], style);
		lblTitle.setPosition(50, 700);

		lblTitle.setWidth(600);
		lblTitle.setWrap(true);

		this.addActor(lblTitle);
		/* ******Label Control A Part****** */
		lblA = new Label(question[1], style2);
		lblA.setPosition(70, 330);

		this.addActor(lblA);
		/* ******Label Control B Part****** */
		lblB = new Label(question[2], style2);
		lblB.setPosition(70, 280);

		this.addActor(lblB);
		/* ******Label Control C Part****** */
		lblC = new Label(question[3], style2);
		lblC.setPosition(70, 230);

		this.addActor(lblC);
		/* ******Label Control D Part****** */
		lblD = new Label(question[4], style2);
		lblD.setPosition(70, 180);

		this.addActor(lblD);
		btnABCDTF = new ImageButton[6];
		buttoninit("A", 0, 15, 320);
		buttoninit("B", 1, 15, 270);
		buttoninit("C", 2, 15, 220);
		buttoninit("D", 3, 15, 170);

		buttoninit("T", 4, 15+45, 500);
		buttoninit("F", 5, 245+45, 500);

		/* ******Label Control T Part****** */
		lblT = new Label("T", style2);
		lblT.setPosition(70+45, 500);

		this.addActor(lblT);

		/* ******Label Control F Part****** */
		lblF = new Label("F", style2);
		lblF.setPosition(300+45, 500);

		this.addActor(lblF);

		this.addActor(TopBar.getTopbar());

		/* Fight Character */
		// music=Gdx.audio.newSound(SoundFile.guipai);

		// ScaleToAction scaleto = Actions.scaleTo(3, 1, 3);
		//
		//
		// Action endAction = Actions.run(new Runnable() {
		//
		// @Override
		// public void run() {
		// System.out.println("All action is completed");
		// }
		// });
		// SequenceAction sequenceAction = Actions.sequence(scaleto,endAction);
		//

		// sequenceAction.setActor(fightcharacter);
		// fightcharacter.addAction(sequenceAction);

	}

	private void displaylblbut() {
		if (test.getKind() == 0) {
			lblA.setVisible(true);
			lblB.setVisible(true);
			lblC.setVisible(true);
			lblD.setVisible(true);
			lblT.setVisible(false);
			lblF.setVisible(false);
			btnABCDTF[0].setVisible(true);
			btnABCDTF[1].setVisible(true);
			btnABCDTF[2].setVisible(true);
			btnABCDTF[3].setVisible(true);
			btnABCDTF[4].setVisible(false);
			btnABCDTF[5].setVisible(false);

		} else {
			lblA.setVisible(false);
			lblB.setVisible(false);
			lblC.setVisible(false);
			lblD.setVisible(false);
			lblT.setVisible(true);
			lblF.setVisible(true);
			btnABCDTF[0].setVisible(false);
			btnABCDTF[1].setVisible(false);
			btnABCDTF[2].setVisible(false);
			btnABCDTF[3].setVisible(false);
			btnABCDTF[4].setVisible(true);
			btnABCDTF[5].setVisible(true);
		}
	}

	private void buttoninit(final String answer, final int index, int x, int y) {

		Texture tex = new Texture(ImgFile.questionButton);
		TextureRegion[][] tem = TextureRegion.split(tex, 120, 120);

		TextureRegion buttonup = tem[0][0];
		buttonup.flip(true, false);
		TextureRegion buttondown = tem[0][1];
		buttondown.flip(true, false);
		TextureRegionDrawable up = new TextureRegionDrawable(buttonup);
		TextureRegionDrawable down = new TextureRegionDrawable(buttondown);
		btnABCDTF[index] = new ImageButton(up, down);

		btnABCDTF[index].setPosition(x, y);
		btnABCDTF[index].setSize(40, 40);
	
		btnABCDTF[index].addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// music.play();
				// TODO Auto-generated method stub
				if (test.verifyAnswer(answer)) {
					btnABCDTF[0].setTouchable(Touchable.disabled);
					btnABCDTF[1].setTouchable(Touchable.disabled);
					btnABCDTF[2].setTouchable(Touchable.disabled);
					btnABCDTF[3].setTouchable(Touchable.disabled);
					btnABCDTF[4].setTouchable(Touchable.disabled);
					btnABCDTF[5].setTouchable(Touchable.disabled);
					fightcharacter.state = STATE.Act;
					monsterhp.minushp();

				} else {
					fightcharacter.state = STATE.Act;
					characterhp.minushp();

				}
				if (!monsterhp.isAlive()) {
					game.setScreen(game.resultscreen);
				} else if (!characterhp.isAlive()) {
					game.setScreen(game.resultscreen);
				} else {
					updatequestion();

				}

			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		this.addActor(btnABCDTF[index]);
	}
}
