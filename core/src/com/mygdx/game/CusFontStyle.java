package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


public class CusFontStyle {
	private static BitmapFont normalfont;
	private static BitmapFont boldfont;
	public static BitmapFont getNormalFont() {
		if (normalfont == null) {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
					Gdx.files.internal("font.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 36;
			normalfont = generator.generateFont(parameter); // font size
																	// 12 pixels
			generator.dispose(); // don't forget to dispose to avoid memory
									// leaks!
			normalfont.setColor(Color.BLACK);
			
		}
		return normalfont;
	}
	public static BitmapFont getBoldFont() {
		if (boldfont == null) {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
					Gdx.files.internal("font_bold.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 36;
			boldfont = generator.generateFont(parameter); // font size
																	// 12 pixels
			generator.dispose(); // don't forget to dispose to avoid memory
									// leaks!
			boldfont.setColor(Color.BLACK);
			
		}
		return boldfont;
	}
}
