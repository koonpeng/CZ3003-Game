package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.socialmedia.SocialMediaSharedVariable;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		
//			Settings settings = new Settings();
//			settings.maxWidth = 2048;
//			settings.maxHeight = 2048;
//			settings.debug = false;
//			TexturePacker2.process(settings, "assets-raw/images",
//			"../android/assets/images",
//			"image1.pack");
//			TexturePacker2.process(settings, "assets-raw/images-ui",
//			"../android/assets/images",
//			"appone-ui.pack");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="";
		config.height=800;
		config.width=480;
		new LwjglApplication(new MyGdxGame(), config);
		//SocialMediaSharedVariable.instance.setTwitterInterface(new DesktopTwitterInterface());
				SocialMediaSharedVariable.instance.setDesktopApplication(true);
	}
}
