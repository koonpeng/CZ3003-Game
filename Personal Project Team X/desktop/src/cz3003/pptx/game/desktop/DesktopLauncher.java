package cz3003.pptx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import cz3003.pptx.game.PPTXGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="";
		config.height=720;
		config.width=480;
		new LwjglApplication(new PPTXGame(), config);
	}
}
