package com.mygdx.game.test;

import com.mygdx.game.PPTXGame;
import com.mygdx.game.BattleScreen;

public class TestBattleGame extends PPTXGame {

	@Override
	public void create() {
		super.create();
		BattleScreen screen = new BattleScreen(this);
		setScreen(screen);
	}

}
