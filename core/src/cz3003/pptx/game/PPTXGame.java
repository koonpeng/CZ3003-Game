package cz3003.pptx.game;

import com.badlogic.gdx.Game;
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
import cz3003.pptx.game.battle.quiz.Quiz;
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
	private static CreateQuestionScreen creatquestionscreen;
	static QuestionListScreen questionlistscreen;

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

	public static void toResultScreen(Quiz quiz, boolean result) {
		resultscreen.setQuiz(quiz);
		resultscreen.setResult(result);
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

	public static void toCreateQuestionScreen(int questionnumber) {
		creatquestionscreen = new CreateQuestionScreen(questionnumber);
		pptxGame.setScreen(creatquestionscreen);
	}

	public static void toQuestionListScreen() {
		questionlistscreen.dispose();
		questionlistscreen = new QuestionListScreen(pptxGame);
		pptxGame.setScreen(questionlistscreen);
	}

	private void loadAssets() {
		assetManager.load("empty.png", Texture.class);
		assetManager.load("button.png", Texture.class);
		assetManager.load("battle/player.png", Texture.class);
		assetManager.load("battle/kamehameha.png", Texture.class);
		assetManager.load("monsters/dragon.png", Texture.class);
		assetManager.load("battle/RedBar.png", Texture.class);
		assetManager.load("battle/EmptyBar.png", Texture.class);
		assetManager.load("battle/fire.png", Texture.class);
		assetManager.load("backgrounds/environment_forest_alt1.png", Texture.class);
		assetManager.load("backgrounds/crumpled-paper.jpg", Texture.class);
		assetManager.load("music/(10) Force Your Way.mp3", Music.class);
		assetManager.load("music/(05) The Winner.mp3", Music.class);
		assetManager.load("sound/explosion.wav", Sound.class);

		FreeTypeFontLoaderParameter calibri36 = new FreeTypeFontLoaderParameter();
		calibri36.fontFileName = "font/calibri.ttf";
		calibri36.fontParameters.size = 36;
		calibri36.fontParameters.minFilter = TextureFilter.Linear;
		calibri36.fontParameters.magFilter = TextureFilter.Linear;
		assetManager.load("calibri36.ttf", BitmapFont.class, calibri36);

		FreeTypeFontLoaderParameter calibri24 = new FreeTypeFontLoaderParameter();
		calibri24.fontFileName = "font/calibri.ttf";
		calibri24.fontParameters.size = 24;
		calibri24.fontParameters.minFilter = TextureFilter.Linear;
		calibri24.fontParameters.magFilter = TextureFilter.Linear;
		assetManager.load("calibri24.ttf", BitmapFont.class, calibri24);
	}

	@Override
	public void create() {
		InternalFileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		loadAssets();
		player = new Player("Player", 10000, 5000, 5000);
		player.equip(EquipmentFactory.getEquipment("Excalibur"));
		assetManager.finishLoading();

		loginscreen = new LoginScreen(this);
		battleScreen = new BattleScreen();
		menuscreen = new MenuScreen(this);
		questionlistscreen = new QuestionListScreen(this);

		resultscreen = new ResultScreen(this);
		loadingscreen = new LoadingScreen(this);
		// toCreateQuestionScreen(5);
		this.setScreen(menuscreen);
	}

}
