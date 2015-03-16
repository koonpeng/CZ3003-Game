package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;


public class SelectionScreen implements Screen {
	PPTXGame game;
	SelectionStage selectionstage;
	public SelectionScreen(PPTXGame game)
	{
		this.game=game;
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		selectionstage=new SelectionStage(game);
		Gdx.input.setInputProcessor(selectionstage);
		
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		selectionstage.act();
		selectionstage.draw();
		
		
		
		
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
