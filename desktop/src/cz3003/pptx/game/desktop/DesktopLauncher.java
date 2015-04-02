package cz3003.pptx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

import cz3003.pptx.game.PPTXGame;
import cz3003.pptx.game.socialmedia.SocialMediaSharedVariable;

public class DesktopLauncher {

	public static void main(String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "";
		config.width = 504;
		config.height = 896;
		new LwjglApplication(PPTXGame.getInstance(), config);
		SocialMediaSharedVariable.instance.setDesktopApplication(true);
	}

}
