package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ResultScreen implements Screen {

	PPTXGame game;
	ResultStage resultstage;
	public float statetime;
	private final Music winMusic;

	public ResultScreen(PPTXGame game) {
		this.game = game;
		winMusic = PPTXGame.getAssetManager().get("music/(05) The Winner.mp3");
		winMusic.setVolume(0.75f);
		winMusic.setLooping(true);
	}

	@Override
	public void show() {
		winMusic.setPosition(0);
		winMusic.play();
		resultstage = new ResultStage();
		resultstage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
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
		if (statetime > 5) {

			game.setScreen(game.selectionscreen);
		}

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
		winMusic.stop();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
