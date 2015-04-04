package cz3003.pptx.game.battle;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import cz3003.pptx.game.PPTXGame;
import cz3003.pptx.game.battle.quiz.Quiz;

public class QuestionUI extends Table {

	public static final int WIDTH = PPTXGame.GAME_WIDTH;
	public static final int HEIGHT = PPTXGame.GAME_HEIGHT / 2;

	private final BitmapFont font;
	private final BitmapFont ansFont;
	private final LabelStyle style;
	private final LabelStyle ansStyle;
	private final Label lblTitle;
	private final ArrayList<Button> ansBtns;
	private final ArrayList<Label> ansLbls;
	private final ArrayList<HorizontalGroup> ansGroups;
	private final Quiz questionPool;

	public QuestionUI(Quiz quiz) {
		super();
		this.questionPool = quiz;
		String[] question = quiz.getQuestion();
		ansBtns = new ArrayList<Button>(4);
		ansLbls = new ArrayList<Label>(4);
		ansGroups = new ArrayList<HorizontalGroup>(4);

		font = PPTXGame.getAssetManager().get("calibri36.ttf");
		font.setColor(Color.BLACK);
		ansFont = PPTXGame.getAssetManager().get("calibri24.ttf");
		ansFont.setColor(Color.BLACK);

		style = new LabelStyle(font, font.getColor());
		ansStyle = new LabelStyle(ansFont, ansFont.getColor());
		lblTitle = new Label(question[1], style);
		lblTitle.setWrap(true);

		padLeft(50);
		padRight(10);

		add(lblTitle).width(WIDTH - getPadLeft() - getPadRight()).padBottom(25).align(Align.topLeft).padTop(50);
		row();

		setWidth(PPTXGame.GAME_WIDTH);
		setHeight(PPTXGame.GAME_HEIGHT / 2);
		align(Align.topLeft);
		setQuestion(question);
	}

	public void nextQuestion() {
		setQuestion(questionPool.getQuestion());
	}

	private Button createAnsButtons() {
		Texture tex = PPTXGame.getAssetManager().get("button.png");
		TextureRegion[][] tem = TextureRegion.split(tex, 120, 120);
		Sprite upSprite = new Sprite(tem[0][0]);
		upSprite.flip(true, false);
		upSprite.setSize(72, 72);
		Sprite downSprite = new Sprite(tem[0][1]);
		downSprite.flip(true, false);
		downSprite.setSize(72, 72);
		SpriteDrawable up = new SpriteDrawable(upSprite);
		SpriteDrawable down = new SpriteDrawable(downSprite);

		final Button tmp = new Button(up, down);
		tmp.setName("ansButton");
		return tmp;
	}

	private void setQuestion(String[] question) {
		lblTitle.setText(question[1]);
		String[] choices = null;

		if (question[0].equals("A")) {
			choices = new String[2];
			choices[0] = "True";
			choices[1] = "False";
		} else if (question[0].equals(("B"))) {
			choices = new String[4];
			choices[0] = question[2];
			choices[1] = question[3];
			choices[2] = question[4];
			choices[3] = question[5];
		}

		float ansLabelWidth = WIDTH - 72 - getPadLeft() - getPadRight();
		for (int i = 0; i < choices.length; i++) {
			Label lbl;
			Button btn;
			if (ansLbls.size() > i) {
				lbl = ansLbls.get(i);
				btn = ansBtns.get(i);
			} else {
				lbl = new Label(choices[i], ansStyle);
				lbl.setWrap(true);
				ansLbls.add(lbl);
				btn = createAnsButtons();
				ansBtns.add(btn);
			}
			lbl.setText(choices[i]);
			if (choices[i].equals("True"))
				btn.setUserObject(String.valueOf('T'));
			else if (choices[i].equals("False"))
				btn.setUserObject(String.valueOf('F'));
			else
				btn.setUserObject(String.valueOf((char) ('A' + i))); // Convert ASCII codepoint to String

			HorizontalGroup hGrp;
			if (ansGroups.size() > i) {
				hGrp = ansGroups.get(i);
				hGrp.setVisible(true);
			} else {
				hGrp = new HorizontalGroup();
				hGrp.addActor(btn);
				hGrp.addActor(new Table().add(lbl).width(ansLabelWidth).padLeft(10).getTable());
				ansGroups.add(hGrp);
				add(hGrp).align(Align.topLeft);
				row();
			}
		}

		for (int i = choices.length; i < ansGroups.size(); i++) {
			ansGroups.get(i).setVisible(false);
		}
	}
}
