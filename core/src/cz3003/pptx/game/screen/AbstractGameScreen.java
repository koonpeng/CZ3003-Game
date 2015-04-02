package cz3003.pptx.game.screen;

import com.badlogic.gdx.Screen;
import cz3003.pptx.game.PPTXGame;

public abstract class AbstractGameScreen implements Screen{
	private static final String TAG = AbstractGameScreen.class.getName();
	
	protected PPTXGame game;
	
	public AbstractGameScreen(PPTXGame game){
		this.game = game;
	}
	
	public abstract void render (float deltaTime);
	
	public abstract void resize (int width, int height);
	public abstract void show ();
	public abstract void hide ();
	public abstract void pause ();
	public void resume () {
		//init assets
	}
	public void dispose () {
		//dispose assets
	}
	
}
