package com.mygdx.game;
//sprite是对texture的一个继承 他可以draw

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.screen.MenuScreen;
public class MyGdxGame  extends  Game  {

/* Stage definition */
	QuestionStage questionstage;
	SelectionStage selectionstage;
	ResultStage resultstage;
	SpriteBatch batch;
	TextureAtlas atlas;
	Animation AlienAnimation;
	float statetime=0;
	Sprite sprite;
	
	LoginScreen loginscreen;
	public SelectionScreen selectionscreen;
	ResultScreen resultscreen;
	QuestionScreen questionscreen;
	LoadingScreen loadingscreen;
	MenuScreen menuscreen;
	

	
	
	public void SelectStageRender()
	{
		/*if(Constants.StageFlag==Constants.StartStageOn){
		}else if(Constants.StageFlag==Constants.QuestionStageOn){
			Gdx.input.setInputProcessor(questionstage);
			
			questionstage.act();
			questionstage.draw();
		}else if(Constants.StageFlag==Constants.SelectionStageOn){
			Gdx.input.setInputProcessor(selectionstage);
			
			selectionstage.act();
			selectionstage.draw();
		}
		else if(Constants.StageFlag==Constants.ResultStageOn){
			Gdx.input.setInputProcessor(resultstage);
			resultstage.updatescore();
			resultstage.act();
			resultstage.draw();
		}*/
	}
	@Override
	public void create() {
		loginscreen=new LoginScreen(this);
		questionscreen=new QuestionScreen(this);
		menuscreen = new MenuScreen(this);
		
		selectionscreen=new SelectionScreen(this);
		resultscreen=new ResultScreen(this);
		loadingscreen=new LoadingScreen(this);
		this.setScreen(loadingscreen);
		/*questionstage=new QuestionStage();
		selectionstage=new SelectionStage();
		resultstage=new ResultStage();*/
	}

	/*@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();
		//batch.draw(character,x,y);
		batch.end();
		
		//SelectStageRender();
		
	}*/
	
	
}
