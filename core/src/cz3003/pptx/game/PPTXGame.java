package cz3003.pptx.game;

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
import cz3003.pptx.game.equipment.EquipmentFactory;
import cz3003.pptx.game.screen.MenuScreen;

public class PPTXGame extends Game {

	/* Stage definition */
	SelectionStage selectionstage;
	ResultStage resultstage;
	SpriteBatch batch;
	TextureAtlas atlas;
	Animation AlienAnimation;
	float statetime = 0;
	Sprite sprite;

	LoginScreen loginscreen;
	public SelectionScreen selectionscreen;
	public static ResultScreen resultscreen;
	private static BattleScreen battleScreen;
	LoadingScreen loadingscreen;
	MenuScreen menuscreen;

	public static final int GAME_WIDTH = 720;
	public static final int GAME_HEIGHT = 1280;

	public static Player player;

	private static PPTXGame pptxGame = new PPTXGame();
	private static AssetManager assetManager = new AssetManager();

	private PPTXGame() {

	}

	public static PPTXGame getInstance() {
		return pptxGame;
	}

	public static AssetManager getAssetManager() {
		return assetManager;
	}

	public static void toResultScreen() {
		pptxGame.setScreen(resultscreen);
	}
	
	public static void toBattleScreen(int id) {
		battleScreen.setDungeonId(id);
		pptxGame.setScreen(battleScreen);
	}
	
	public static void toBattleScreen(int id, String name) {
		battleScreen.setDungeonId(id);
		pptxGame.setScreen(battleScreen);
	}

	private void loadAssets() {
		assetManager.load("empty.png", Texture.class);
		assetManager.load("button.png", Texture.class);
		assetManager.load("player/battle.png", Texture.class);
		assetManager.load("player/kamehameha.png", Texture.class);
		assetManager.load("monsters/dragon.png", Texture.class);
		assetManager.load("RedBar.png", Texture.class);
		assetManager.load("EmptyBar.png", Texture.class);
		assetManager.load("backgrounds/environment_forest_alt1.png", Texture.class);
		assetManager.load("backgrounds/crumpled-paper.jpg", Texture.class);
		assetManager.load("music/(10) Force Your Way.mp3", Music.class);
		assetManager.load("music/(05) The Winner.mp3", Music.class);
		assetManager.load("sound/explosion.wav", Sound.class);
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

	@Override
	public void create() {
		InternalFileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		loadAssets();
		player = new Player("Player", 1000, 5000, 5000);
		player.equip(EquipmentFactory.getEquipment("Excalibur"));
		assetManager.finishLoading();

		loginscreen = new LoginScreen(this);
		battleScreen = new BattleScreen();
		menuscreen = new MenuScreen(this);

		
		resultscreen = new ResultScreen(this);
		loadingscreen = new LoadingScreen(this);
		this.setScreen(menuscreen);
	}

}
