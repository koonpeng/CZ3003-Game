package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.game.test.TestQuestionPool;

public class QuestionUI extends Table {

	public static final int WIDTH = PPTXGame.GAME_WIDTH;
	public static final int HEIGHT = PPTXGame.GAME_HEIGHT / 2; // Half the screen

	private BitmapFont font;
	private BitmapFont ansFont;
	private LabelStyle style;
	private LabelStyle ansStyle;
	private Label lblTitle;
	private ArrayList<Button> ansBtns;
	private ArrayList<Label> ansLbls;
	private ArrayList<HorizontalGroup> ansGroups;
	private TestQuestionPool questionPool;

	public QuestionUI(TestQuestionPool questionPool) {
		super();
		this.questionPool = questionPool;
		Question question = questionPool.getNextQuestion();
		ansBtns = new ArrayList<Button>(question.getChoices().length);
		ansLbls = new ArrayList<Label>(question.getChoices().length);
		ansGroups = new ArrayList<HorizontalGroup>(question.getChoices().length);

		font = PPTXGame.getAssetManager().get("size36.ttf");
		font.setColor(Color.RED);
		ansFont = PPTXGame.getAssetManager().get("size24.ttf");
		ansFont.setColor(Color.RED);

		style = new LabelStyle(font, font.getColor());
		ansStyle = new LabelStyle(ansFont, ansFont.getColor());
		lblTitle = new Label(question.getQuestion(), style);
		lblTitle.setWrap(true);

		padLeft(50);
		padRight(10);

		add(lblTitle).width(WIDTH - getPadLeft() - getPadRight()).padBottom(25).align(Align.topLeft);
		row();

		align(Align.topLeft);
		setQuestion(question);
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
		tmp.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if ((Boolean) event.getTarget().getUserObject()) {
					System.out.println("CORRECT!!");
				} else {
					System.out.println("WRONG!!");
				}
				tmp.fire(new ChangeListener.ChangeEvent());
				setQuestion(questionPool.getNextQuestion());
			}
		});
		return tmp;
	}

	private void setQuestion(Question question) {
		lblTitle.setText(question.getQuestion());

		float ansLabelWidth = WIDTH - 72 - getPadLeft() - getPadRight();
		for (int i = 0; i < question.getChoices().length; i++) {
			Label lbl;
			Button btn;
			if (ansLbls.size() > i) {
				lbl = ansLbls.get(i);
				lbl.setText(question.getChoices()[i]);
				btn = ansBtns.get(i);
			} else {
				lbl = new Label(question.getChoices()[i], ansStyle);
				lbl.setWrap(true);
				ansLbls.add(lbl);
				btn = createAnsButtons();
				ansBtns.add(btn);
			}

			if (i == question.getAnswer()) {
				btn.setUserObject(new Boolean(true));
			} else {
				btn.setUserObject(new Boolean(false));
			}

			HorizontalGroup hGrp;
			if (ansGroups.size() > i) {
				hGrp = ansGroups.get(i);
			} else {
				hGrp = new HorizontalGroup();
				hGrp.addActor(btn);
				hGrp.addActor(new Table().add(lbl).width(ansLabelWidth).padLeft(10).getTable());
				ansGroups.add(hGrp);
				add(hGrp).align(Align.topLeft);
				row();
			}
		}

		for (int i = question.getChoices().length; i < ansGroups.size(); i++) {
			removeActor(ansGroups.get(i));
		}
	}

}
