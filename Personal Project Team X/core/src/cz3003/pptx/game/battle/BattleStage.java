package cz3003.pptx.game.battle;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cz3003.pptx.game.PPTXGame;
import cz3003.pptx.game.test.TestQuestionPool;

public class BattleStage extends Stage {

	private final BattleUI battleUI;
	private final QuestionUI questionUI;
	private final BattleActor player;
	private final EnemyActor enemy;

	private int score = 0;

	public BattleStage() {
		super(new StretchViewport(PPTXGame.GAME_WIDTH, PPTXGame.GAME_HEIGHT));
		player = PPTXGame.player.genBattleActor();
		questionUI = new QuestionUI(new TestQuestionPool());
		questionUI.setPosition(0, PPTXGame.GAME_HEIGHT / 2);
		questionUI.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if ((Boolean) event.getTarget().getUserObject()) {
					BattleSystem.doAttack(player, enemy, true);
					BattleSystem.doAttack(enemy, player, false);
					score++;
				} else {
					BattleSystem.doAttack(player, enemy, false);
					BattleSystem.doAttack(enemy, player, false);
				}
				battleUI.update();
			}
		});
		enemy = new EnemyActor("Progenitor", 1000, 1000, 100, 100);
		battleUI = new BattleUI(enemy);
		battleUI.setPosition(0, PPTXGame.GAME_HEIGHT / 2);

		addActor(questionUI);
		addActor(battleUI);
	}

}
