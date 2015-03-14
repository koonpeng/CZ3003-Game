package com.mygdx.game;

//sprite是对texture的一个继承 他可以draw

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;

public class PPTXGame extends Game {

	public static int GAME_WIDTH = 720;
	public static int GAME_HEIGHT = 1280;

	private static AssetManager assetManager = new AssetManager();

	/* Stage definition */
	BattleStage questionstage;
	SelectionStage selectionstage;
	ResultStage resultstage;
	SpriteBatch batch;
	TextureAtlas atlas;
	Animation AlienAnimation;
	float statetime = 0;
	Sprite sprite;

	LoginScreen loginscreen;
	SelectionScreen selectionscreen;
	ResultScreen resultscreen;
	BattleScreen questionscreen;

	public void SelectStageRender() {
		/*
		 * if(Constants.StageFlag==Constants.StartStageOn){
		 * }else if(Constants.StageFlag==Constants.QuestionStageOn){
		 * Gdx.input.setInputProcessor(questionstage);
		 * questionstage.act();
		 * questionstage.draw();
		 * }else if(Constants.StageFlag==Constants.SelectionStageOn){
		 * Gdx.input.setInputProcessor(selectionstage);
		 * selectionstage.act();
		 * selectionstage.draw();
		 * }
		 * else if(Constants.StageFlag==Constants.ResultStageOn){
		 * Gdx.input.setInputProcessor(resultstage);
		 * resultstage.updatescore();
		 * resultstage.act();
		 * resultstage.draw();
		 * }
		 */
	}

	@Override
	public void create() {
		InternalFileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		loadAssets();
		assetManager.finishLoading();
		loginscreen = new LoginScreen(this);
		questionscreen = new BattleScreen(this);

		selectionscreen = new SelectionScreen(this);
		resultscreen = new ResultScreen(this);
		this.setScreen(selectionscreen);
		/*
		 * questionstage=new QuestionStage();
		 * selectionstage=new SelectionStage();
		 * resultstage=new ResultStage();
		 */
	}

	private void loadAssets() {
		assetManager.load("button.png", Texture.class);
		FreeTypeFontLoaderParameter fontParam = new FreeTypeFontLoaderParameter();
		fontParam.fontFileName = "fonts/calibri.ttf";
		fontParam.loadedCallback = new LoadedCallback() {
			@Override
			public void finishedLoading(AssetManager assetManager, String fileName, @SuppressWarnings("rawtypes") Class type) {
				BitmapFont font = assetManager.get(fileName);
				font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			}
		};
		fontParam.fontParameters.size = 36;
		assetManager.load("size36.ttf", BitmapFont.class, fontParam);
		
		FreeTypeFontLoaderParameter ansFontParams = new FreeTypeFontLoaderParameter();
		ansFontParams.fontFileName = "fonts/calibri.ttf";
		ansFontParams.loadedCallback = new LoadedCallback() {
			@Override
			public void finishedLoading(AssetManager assetManager, String fileName, @SuppressWarnings("rawtypes") Class type) {
				BitmapFont font = assetManager.get(fileName);
				font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			}
		};
		ansFontParams.fontParameters.size = 24;
		assetManager.load("size24.ttf", BitmapFont.class, ansFontParams);
	}

	public static AssetManager getAssetManager() {
		return assetManager;
	}

	/*
	 * @Override
	 * public void render () {
	 * Gdx.gl.glClearColor(1, 1, 1, 1);
	 * Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	 * batch.begin();
	 * //batch.draw(character,x,y);
	 * batch.end();
	 * //SelectStageRender();
	 * }
	 */

}
