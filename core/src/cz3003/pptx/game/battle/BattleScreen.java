package cz3003.pptx.game.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class BattleScreen implements Screen {

	private BattleStage battleStage;
	private int dungeonId;
	private String dungeonName;

	public BattleScreen() {
	}

	@Override
	public void show() {
		battleStage = new BattleStage(new EnemyActor("Dragon", 20000, 20000, 5000, 5000), dungeonId, dungeonName);
		battleStage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
		Gdx.input.setInputProcessor(battleStage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		battleStage.act();
		battleStage.draw();
	}

	public void setDungeonId(int id) {
		dungeonId = id;
	}

	public void setDungeonName(String name) {
		dungeonName = name;
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
		battleStage.dispose();
	}

}
