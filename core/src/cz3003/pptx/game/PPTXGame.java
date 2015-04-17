package cz3003.pptx.game;

import java.sql.Date;
import java.util.Calendar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

import cz3003.pptx.game.battle.BattleScreen;
import cz3003.pptx.game.battle.quiz.Quiz;
import cz3003.pptx.game.equipment.EquipmentFactory;
import cz3003.pptx.game.screen.MenuScreen;
import cz3003.pptx.game.socialmedia.Profile;

public class PPTXGame extends Game {

	/* Stage definition */
	SelectionStage selectionstage;
	ResultStage resultstage;
	SpriteBatch batch;
	TextureAtlas atlas;
	Animation AlienAnimation;
	float statetime = 0;
	Sprite sprite;

	private static SelectionScreen selectionscreen;
	public static ResultScreen resultscreen;
	private static BattleScreen battleScreen;
	LoadingScreen loadingscreen;
	MenuScreen menuscreen;
	private static CreateQuestionScreen creatquestionscreen;
	LeaderBoardScreen leaderboardscreen;
	static QuestionListScreen questionlistscreen;
	private static CustomizeQuestionListScreen customizequestionlistscreen;
	public static final int GAME_WIDTH = 720;
	public static final int GAME_HEIGHT = 1280;
	private static SummeryScreen summeryscreen;
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

	public static void toCustomizequestionlistscreen(boolean refresh) {

		customizequestionlistscreen = new CustomizeQuestionListScreen();
		CustomizeQuestionListStage.setRefresh(refresh);
		pptxGame.setScreen(customizequestionlistscreen);

		
		
	}
	public static void toSummeryscreen() {
		summeryscreen = new SummeryScreen();
		pptxGame.setScreen(summeryscreen);
	}

	public static void toResultScreen(Quiz quiz, boolean result) {
		 Calendar currenttime = Calendar.getInstance();
		 Date sqldate = new Date((currenttime.getTime()).getTime());
		 if(SelectionStage.isSelecttype())
		 {
		DbConfig db=new DbConfig("wangbwhz","123456","playinghistory");
		String inserthistory="'"+Profile.instance.getUsername()+"','"+SelectionStage.getCurrentDungeon()+"','"+quiz.getScore()+"','"+sqldate+"'";
		db.insert(inserthistory);
		 }
		
		resultscreen.setQuiz(quiz);
		resultscreen.setResult(result);
		pptxGame.setScreen(resultscreen);
	}

	public static void toBattleScreen(int id) {
		battleScreen = new BattleScreen();
		battleScreen.setDungeonId(id);
		pptxGame.setScreen(battleScreen);
	}

	public static void toBattleScreen(int id, String name) {
		battleScreen = new BattleScreen();
		battleScreen.setDungeonId(id);
		battleScreen.setDungeonName(name);
		pptxGame.setScreen(battleScreen);
	}

	public static void toSelectionScreen() {
		selectionscreen = new SelectionScreen();
		pptxGame.setScreen(selectionscreen);
	}

	public static void toCreateQuestionScreen(int questionnumber, boolean b) {

		creatquestionscreen = new CreateQuestionScreen(questionnumber, b);

		pptxGame.setScreen(creatquestionscreen);
	}

	public static void toQuestionListScreen() {
		questionlistscreen.dispose();
		questionlistscreen = new QuestionListScreen(pptxGame);
		pptxGame.setScreen(questionlistscreen);
	}

	@Override
	public void create() {
		InternalFileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		player = new Player("Player", 10000, 5000, 5000);
		player.equip(EquipmentFactory.getEquipment("Excalibur"));

		menuscreen = new MenuScreen(this);
		questionlistscreen = new QuestionListScreen(this);

		resultscreen = new ResultScreen(this);
		loadingscreen = new LoadingScreen(this);
		// toCreateQuestionScreen(5);
		leaderboardscreen = new LeaderBoardScreen(this);
		//toSummeryscreen();
		this.setScreen(loadingscreen);
	}

}
