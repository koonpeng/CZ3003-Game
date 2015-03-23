package com.mygdx.game.screen;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Assets;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public abstract class AbstractGameScreen implements Screen{
	private static final String TAG = AbstractGameScreen.class.getName();
	
	protected MyGdxGame game;
	
	public AbstractGameScreen(MyGdxGame game){
		this.game = game;
	}
	
	public abstract void render (float deltaTime);
	
	public abstract void resize (int width, int height);
	public abstract void show ();
	public abstract void hide ();
	public abstract void pause ();
	public void resume () {
	Assets.instance.init(new AssetManager());
	}
	public void dispose () {
	Assets.instance.dispose();
	}
	
}
