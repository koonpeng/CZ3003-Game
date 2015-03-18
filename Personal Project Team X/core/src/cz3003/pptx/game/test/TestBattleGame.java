package cz3003.pptx.game.test;

import cz3003.pptx.game.PPTXGame;
import cz3003.pptx.game.battle.BattleScreen;

public class TestBattleGame extends PPTXGame {

	@Override
	public void create() {
		super.create();
		BattleScreen screen = new BattleScreen();
		setScreen(screen);
	}

}
