package cz3003.pptx.game.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import cz3003.pptx.game.PPTXGame;

public class BattleScreen implements Screen {
	PPTXGame game;
	BattleStage battleStage;

	public BattleScreen(PPTXGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		battleStage = new BattleStage();
		Gdx.input.setInputProcessor(battleStage);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		battleStage.act();
		battleStage.draw();
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
