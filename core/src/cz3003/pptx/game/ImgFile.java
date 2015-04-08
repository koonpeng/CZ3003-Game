package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class ImgFile {
	
	
	//data selection part
	public static FileHandle smallwukongimg=Gdx.files.internal("privategirl.png");
	public static FileHandle smallwukongbackground=Gdx.files.internal("loading.jpg");
	public static FileHandle dugeonbackground=Gdx.files.internal("dugeon/background.png");
	
	public static FileHandle dungeon=Gdx.files.internal("dugeon/dugeon.pack");
	//fighting part
	public static FileHandle skill1_1=Gdx.files.internal("wave.gif");
	public static FileHandle skill1_2=Gdx.files.internal("skill2.gif");
	public static FileHandle fightimg=Gdx.files.internal("fight.png");
	public static FileHandle dragon=Gdx.files.internal("dragon.png");
	public static FileHandle attack=Gdx.files.internal("attack.png");

	//question part
	public static FileHandle questionButton=Gdx.files.internal("button.png");
	public static FileHandle nextquestion=Gdx.files.internal("createquestionpage/nextquestion.png");
	public static FileHandle backquestion=Gdx.files.internal("createquestionpage/backquestion.png");
	public static FileHandle submitquestion=Gdx.files.internal("createquestionpage/submitquestion.png");
	public static FileHandle creatquestionbutton=Gdx.files.internal("createquestionpage/createquestionbutton.png");
	public static FileHandle managequestionbutton=Gdx.files.internal("createquestionpage/managequestionbutton.png");
	//result part
	public static FileHandle resultbackground_gameover=Gdx.files.internal("resultpage/gameover.jpg");
	public static FileHandle resultbackground_youwinr=Gdx.files.internal("resultpage/youwin.jpg");
	public static FileHandle backbutton=Gdx.files.internal("resultpage/back.png");
	public static FileHandle checkleaderboarbutton=Gdx.files.internal("resultpage/checkleaderboar.png");
	public static FileHandle scorebackground=Gdx.files.internal("resultpage/score.png");
	public static FileHandle playagainbutton=Gdx.files.internal("resultpage/playagain.png");
	//loading part
	public static FileHandle loadingbackground=Gdx.files.internal("loading.jpg");
	//treasure
	//loading part
	public static FileHandle treasureaction=Gdx.files.internal("action.png");
	//result page
	public static FileHandle leaderboardbackbutton=Gdx.files.internal("leaderboard/backbutton.png");
	public static FileHandle leaderboardback=Gdx.files.internal("leaderboard/leaderoard.png");
	
	
}
