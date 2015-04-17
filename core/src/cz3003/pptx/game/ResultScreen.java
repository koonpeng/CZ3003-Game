package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import cz3003.pptx.game.battle.quiz.Quiz;

public class ResultScreen implements Screen {
	PPTXGame game;
	ResultStage resultstage;
	public float statetime;

	private Quiz quiz;
	private boolean result;

	public ResultScreen(PPTXGame game) {
		this.game = game;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	@Override
	public void show() {
		resultstage = new ResultStage(game, result, quiz);
		resultstage.setViewport(AndroidView.getview());
		Gdx.input.setInputProcessor(resultstage);
		statetime = 0;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		resultstage.act();
		resultstage.draw();
		statetime += Gdx.graphics.getDeltaTime();
		
//			if(statetime>5)
//			{
//				//game.setScreen(game.selectionscreen);
//			}

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
		resultstage.dispose();
	}

}
