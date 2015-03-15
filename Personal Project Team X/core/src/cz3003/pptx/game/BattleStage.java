package cz3003.pptx.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cz3003.pptx.game.test.TestQuestionPool;

public class BattleStage extends Stage {

	private int score = 0;

	public BattleStage() {
		super(new StretchViewport(PPTXGame.GAME_WIDTH, PPTXGame.GAME_HEIGHT));
		uiinit();
	}

	public void uiinit() {
		QuestionUI questionUI = new QuestionUI(new TestQuestionPool());
		questionUI.setPosition(0, PPTXGame.GAME_HEIGHT / 2);
		questionUI.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if ((Boolean) event.getTarget().getUserObject()) {
					score++;
				}
				System.out.println("Score: " + score + "\n");
			}
		});
		addActor(questionUI);
	}

}
