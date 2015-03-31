package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class ImgFile {
	
	//data selection part
	public static FileHandle smallwukongimg=Gdx.files.internal("privategirl.png");
	public static FileHandle smallwukongbackground=Gdx.files.internal("loading.jpg");
	
	public static FileHandle dungeon=Gdx.files.internal("dugeon/dugeon.pack");
	//fighting part
	public static FileHandle skill1_1=Gdx.files.internal("wave.gif");
	public static FileHandle skill1_2=Gdx.files.internal("skill2.gif");
	public static FileHandle fightimg=Gdx.files.internal("fight.png");
	public static FileHandle dragon=Gdx.files.internal("dragon.png");
	public static FileHandle attack=Gdx.files.internal("attack.png");

	//question part
	public static FileHandle questionButton=Gdx.files.internal("button.png");
	public static FileHandle nextquestion=Gdx.files.internal("nextquestion.png");
	
	//result part
	public static FileHandle resultbackground=Gdx.files.internal("result.jpg");
	//loading part
	public static FileHandle loadingbackground=Gdx.files.internal("loading.jpg");
	//treasure
	//loading part
	public static FileHandle treasureaction=Gdx.files.internal("action.png");
	
	
}
