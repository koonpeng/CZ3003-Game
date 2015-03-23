package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements Screen{
	MyGdxGame game;
	public float statetime;
	SpriteBatch batch;
	Texture loadTexture;
	public LoadingScreen(MyGdxGame game)
	{
		this.game=game;
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		loadTexture=new Texture(ImgFile.loadingbackground);
		batch=new SpriteBatch();
		
	
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		statetime+=Gdx.graphics.getDeltaTime();
		if(statetime>3)
		{
			game.setScreen(game.selectionscreen);
		}
		batch.begin();
		batch.draw(loadTexture,0,0);
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
