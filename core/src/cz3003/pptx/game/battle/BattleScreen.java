package cz3003.pptx.game.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cz3003.pptx.game.PPTXGame;

public class BattleScreen implements Screen {

	private final Music battleMusic;
	private BattleStage battleStage;

	public BattleScreen() {
		battleMusic = PPTXGame.getAssetManager().get("music/1-15 Unrest - Hoist the Sword with Pride in the Heart.mp3");
		battleMusic.setLooping(true);
		battleMusic.setVolume(0.75f);
	}

	@Override
	public void show() {
		battleMusic.setPosition(0);
		battleMusic.play();
		battleStage = new BattleStage(new EnemyActor("Progenitor", 1000, 1000, 100, 100));
		battleStage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
		Gdx.input.setInputProcessor(battleStage);
	}

	@Override
	public void render(float delta) {
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
		battleMusic.stop();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
