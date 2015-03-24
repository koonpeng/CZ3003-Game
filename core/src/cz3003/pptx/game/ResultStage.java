package cz3003.pptx.game;

import jdk.internal.util.xml.impl.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import cz3003.pptx.game.Treasure.STATE;
import cz3003.pptx.game.battle.quiz.Quiz;

public class ResultStage extends Stage {
	// @Override
	// public void draw() {
	// // TODO Auto-generated method stub
	// statetime = Gdx.graphics.getDeltaTime();
	// batch.draw(treasureAnimation.getKeyFrame(statetime), x, y, width,
	// height);
	//
	//
	// super.draw();
	// }
	Label lblresult;
	BitmapFont font;
	LabelStyle style;
	PPTXGame game;
	/* ******background part ******** */
	Texture backgroundregion;
	Image backgroundimage;
	Treasure treasure;

	public ResultStage(PPTXGame game) {
		super();
		this.game=game;
		init();
		show();

	}

	public void show() {

		treasure = new Treasure();
		MoveToAction movetomiddle = Actions.moveTo(
				Gdx.graphics.getWidth() / 2 - 100,
				Gdx.graphics.getHeight() / 2 - 300, 0.3f);

		MoveToAction movetoleftup = Actions.moveTo(100,
				Gdx.graphics.getHeight() - 200, 0.3f);
		MoveToAction movetomiddle2 = Actions.moveTo(
				Gdx.graphics.getWidth() / 2 - 100,
				Gdx.graphics.getHeight() / 2 - 300, 0.3f);
		ScaleToAction scaleto = Actions.scaleTo(1, 1, 3);

		// AlphaAction alpha = Actions.alpha(0.6f, duration);
		Action endAction = Actions.run(new Runnable() {

			@Override
			public void run() {
				treasure.state = STATE.openbox;
			}
		});
		SequenceAction seaqction = Actions.sequence(movetomiddle, movetoleftup,
				movetomiddle2);
		// ParallelAction paaction=Actions.parallel(seaqction,scaleto);

		treasure.setPosition(Gdx.graphics.getWidth() - 100,
				Gdx.graphics.getHeight() - 200);
		treasure.addAction(Actions.sequence(seaqction, endAction));
		this.addActor(treasure);
		// sequenceAction.setActor(fightcharacter);
		// fightcharacter.addAction(sequenceAction);
	}

	public void init() {

		backgroundregion = new Texture(ImgFile.resultbackground);
		backgroundimage = new Image(backgroundregion);
		backgroundimage.setPosition(0, 0);
		backgroundimage.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				game.setScreen(game.selectionscreen);
				return true;
			}
		});
		
		this.addActor(backgroundimage);

		style = new LabelStyle(CusFontStyle.getBoldFont(), CusFontStyle
				.getBoldFont().getColor());

		String result = "Your Score is " + Quiz.getScore();
		lblresult = new Label(result, style);
		lblresult.setPosition(100, 300);
		lblresult.setFontScale(1);
		//this.addActor(lblresult);

	}

	

	public void updatescore() {
		String result = "Your Score is " + Quiz.getScore();
		lblresult.setText(result);																																								 
	} 

	public void backtomain() {
		DelayAction delay = Actions.delay(1f);
		this.addAction(delay);
		Constants.StageFlag = Constants.SelectionStageOn;
	}

}
