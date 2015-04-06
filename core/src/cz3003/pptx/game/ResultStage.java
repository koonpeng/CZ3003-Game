package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import cz3003.pptx.game.Treasure.STATE;
import cz3003.pptx.game.battle.quiz.Quiz;
import cz3003.pptx.game.socialmedia.Profile;

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

	private Quiz quiz;

	public ResultStage(PPTXGame game, boolean result, Quiz quiz) {
		super();
		this.game = game;
		show();
		this.quiz = quiz;
		init(result);
	}

	public void show() {

		treasure = new Treasure();
		MoveToAction movetomiddle = Actions.moveTo(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 300, 0.3f);

		MoveToAction movetoleftup = Actions.moveTo(100, Gdx.graphics.getHeight() - 200, 0.3f);
		MoveToAction movetomiddle2 = Actions.moveTo(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 300, 0.3f);
		ScaleToAction scaleto = Actions.scaleTo(1, 1, 3);

		// AlphaAction alpha = Actions.alpha(0.6f, duration);
		Action endAction = Actions.run(new Runnable() {

			@Override
			public void run() {
				treasure.state = STATE.openbox;
			}
		});
		SequenceAction seaqction = Actions.sequence(movetomiddle, movetoleftup, movetomiddle2);
		// ParallelAction paaction=Actions.parallel(seaqction,scaleto);

		treasure.setPosition(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 200);
		treasure.addAction(Actions.sequence(seaqction, endAction));
		this.addActor(treasure);
		// sequenceAction.setActor(fightcharacter);
		// fightcharacter.addAction(sequenceAction);
	}

	public void init(Boolean result) {

		if (!result) {
			backgroundregion = new Texture(ImgFile.resultbackground_gameover);
			backgroundimage = new Image(backgroundregion);
			backgroundimage.setPosition(0, 0);

			Texture backregion = new Texture(ImgFile.backbutton);
			Image backimage = new Image(backregion);
			backimage.setPosition(405, 177);
			backimage.setSize(240, 76);
			backimage.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					game.setScreen(game.selectionscreen);
					return true;
				}
			});

			Texture playagainregion = new Texture(ImgFile.playagainbutton);
			Image playagainimage = new Image(playagainregion);
			playagainimage.setPosition(93, 177);
			playagainimage.setSize(240, 76);
			playagainimage.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					game.setScreen(game.selectionscreen);
					return true;
				}
			});

			Texture checkleaderboardregion = new Texture(ImgFile.checkleaderboarbutton);
			Image checkleaderboardimage = new Image(checkleaderboardregion);
			checkleaderboardimage.setPosition(92, 64);
			checkleaderboardimage.setSize(551, 76);
			checkleaderboardimage.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					game.setScreen(game.selectionscreen);
					return true;
				}
			});

			Texture scoreregion = new Texture(ImgFile.scorebackground);
			Image scoreimage = new Image(scoreregion);
			scoreimage.setPosition(166, 798);
			scoreimage.setSize(361, 92);

			style = new LabelStyle(CusFontStyle.getBoldFont(), CusFontStyle.getBoldFont().getColor());

			String result1 = "Score is " + quiz.getScore();
			lblresult = new Label(result1, style);
			lblresult.setPosition(166, 798);
			lblresult.setAlignment(Align.center);
			lblresult.setSize(361, 92);
			this.addActor(backgroundimage);
			this.addActor(scoreimage);
			this.addActor(playagainimage);
			this.addActor(checkleaderboardimage);
			this.addActor(backimage);
			this.addActor(lblresult);

		} else {
			backgroundregion = new Texture(ImgFile.resultbackground_youwinr);
			backgroundimage = new Image(backgroundregion);
			backgroundimage.setPosition(0, 0);

			Texture backregion = new Texture(ImgFile.backbutton);
			Image backimage = new Image(backregion);
			backimage.setPosition(397, 146);
			backimage.setSize(240, 76);
			backimage.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					game.setScreen(game.selectionscreen);
					return true;
				}
			});

			Texture playagainregion = new Texture(ImgFile.playagainbutton);
			Image playagainimage = new Image(playagainregion);
			playagainimage.setPosition(86, 146);
			playagainimage.setSize(240, 76);
			playagainimage.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					game.setScreen(game.selectionscreen);
					return true;
				}
			});

			Texture checkleaderboardregion = new Texture(ImgFile.checkleaderboarbutton);
			Image checkleaderboardimage = new Image(checkleaderboardregion);
			checkleaderboardimage.setPosition(84, 33);
			checkleaderboardimage.setSize(551, 76);
			checkleaderboardimage.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					game.setScreen(game.selectionscreen);
					return true;
				}
			});

			Texture scoreregion = new Texture(ImgFile.scorebackground);
			Image scoreimage = new Image(scoreregion);
			scoreimage.setPosition(172, 639);
			scoreimage.setSize(361, 92);

			style = new LabelStyle(CusFontStyle.getBoldFont(), CusFontStyle.getBoldFont().getColor());

			String result1 = "Score is " + quiz.getScore();
			lblresult = new Label(result1, style);
			lblresult.setPosition(172, 639);
			lblresult.setAlignment(Align.center);
			lblresult.setSize(361, 92);
			this.addActor(backgroundimage);
			this.addActor(scoreimage);
			this.addActor(playagainimage);
			this.addActor(checkleaderboardimage);
			this.addActor(backimage);
			this.addActor(lblresult);

			Music music = PPTXGame.getAssetManager().get("music/(05) The Winner.mp3");
			music.setLooping(true);
			music.setVolume(0.75f);
			music.play();
		}

	}

	public void updatescore() {
		String result = "Your Score is " + quiz.getScore();
		lblresult.setText(result);
		
		int[] highScoreArray = Profile.instance.getStageHighScoreArray();
		if(highScoreArray[Profile.instance.getAccessdugeonid()] <quiz.getScore() )
		{
		highScoreArray[Profile.instance.getAccessdugeonid()] =quiz.getScore() ;
		Profile.instance.setStageHighScoreArray(highScoreArray);
		}
		
		
		
	}

	public void backtomain() {
		DelayAction delay = Actions.delay(1f);
		this.addAction(delay);
		Constants.StageFlag = Constants.SelectionStageOn;
	}

}
