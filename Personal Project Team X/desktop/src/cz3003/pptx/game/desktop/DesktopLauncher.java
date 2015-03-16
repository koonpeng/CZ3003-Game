package cz3003.pptx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import cz3003.pptx.game.PPTXGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Personal Project Team X";
		config.width = 360;
		config.height = 640;
		new LwjglApplication(new PPTXGame(), config);
	}
}
