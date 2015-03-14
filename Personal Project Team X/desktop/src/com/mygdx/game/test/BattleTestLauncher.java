package com.mygdx.game.test;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class BattleTestLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Question Test";
		config.width = 576;
		config.height = 1024;
		new LwjglApplication(new TestBattleGame(), config);
	}
}
