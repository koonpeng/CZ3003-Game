package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CreateQuestionScreen implements Screen{
	MyGdxGame game;
	public float statetime;
	SpriteBatch batch;

	Stage stage;

	CreateQuestion createquestion;
	public CreateQuestionScreen(MyGdxGame game)
	{
		this.game=game;
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	
		createquestion=new CreateQuestion(game);
		Gdx.input.setInputProcessor(createquestion);
		createquestion.setViewport(AndroidView.getview());
	
	}


	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		createquestion.act();
		createquestion.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		AndroidView.getview().update(width, height, true);
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
		stage.dispose();
	}
}
