package cz3003.pptx.game.test;

import cz3003.pptx.game.BattleScreen;
import cz3003.pptx.game.PPTXGame;

public class TestBattleGame extends PPTXGame {

	@Override
	public void create() {
		super.create();
		BattleScreen screen = new BattleScreen(this);
		setScreen(screen);
	}

}
