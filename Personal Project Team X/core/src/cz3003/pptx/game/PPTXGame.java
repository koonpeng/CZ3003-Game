package cz3003.pptx.game;

//sprite�Ƕ�texture��һ���̳� ������draw

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

import cz3003.pptx.game.battle.BattleScreen;
import cz3003.pptx.game.battle.BattleStage;
import cz3003.pptx.game.equipment.EquipmentFactory;

public class PPTXGame extends Game {

	public static final int GAME_WIDTH = 720;
	public static final int GAME_HEIGHT = 1280;

	public static Player player;

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
		player = new Player("Player", 1000, 100, 100);
		player.equip(EquipmentFactory.getEquipment("Excalibur"));
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
		assetManager.load("empty.png", Texture.class);
		assetManager.load("button.png", Texture.class);
		assetManager.load("monsters/Progenitor.png", Texture.class);
		assetManager.load("RedBar.png", Texture.class);
		assetManager.load("EmptyBar.png", Texture.class);
		assetManager.load("backgrounds/environment_forest_alt1.png", Texture.class);
		assetManager.load("music/1-15 Unrest - Hoist the Sword with Pride in the Heart.mp3", Music.class);
		assetManager.load("sound/33245__ljudman__grenade-16bit.wav", Sound.class);
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
