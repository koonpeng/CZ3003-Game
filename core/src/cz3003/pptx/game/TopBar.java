package cz3003.pptx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import cz3003.pptx.game.socialmedia.Profile;

public class TopBar {

	private static Group topbar;
	static Label lbllogin;
	static Label lblsetting;

	// static Label lblHP;
	static Image imgsetting;
	static Image dugeonbackgroundimg;
	static Image topbarquestionimg;
	static Image topbarhomeimg;
	// label

	static BitmapFont font;

	public static Group getTopbar() {
		final LabelStyle style;
		// style = new
		// LabelStyle(PPTXGame.getAssetManager().get("calibri36.ttf",
		// BitmapFont.class), Color.BLACK);
		style = new LabelStyle(CusFontStyle.getTopbarFont(), CusFontStyle
				.getTopbarFont().getColor());
		if (lbllogin == null) {
			topbar = new Group();
			/* ******Label Control Title Part****** */
			lbllogin = new Label(Profile.instance.getUsername(), style);
			lbllogin.setPosition(10, 1180);
			// lbllogin.setFontScale(2);
			lbllogin.setWidth(200);
			lbllogin.setHeight(100);
			lbllogin.setWrap(true);
			lbllogin.setAlignment(Align.left);

			// lblHP = new Label("HP:", style);
			// lblHP.setPosition(200,1180);
			// lblHP.setWidth(320);
			// lblHP.setHeight(100);
			//
			// lblHP.setWrap(true);
			// lblHP.setAlignment(Align.topLeft);
			//

			// lblsetting = new Label("Settings", style);
			// lblsetting.setPosition(520, 1180);
			//
			// lblsetting.setWidth(200);
			// lblsetting.setHeight(100);
			// lblsetting.setWrap(true);
			// lblsetting.setAlignment(Align.center);
			imgsetting = new Image(new Texture(ImgFile.topsetting));
			imgsetting.setPosition(460, 1100);
			topbarquestionimg = new Image(new Texture(ImgFile.topbarquestion));

			topbarquestionimg.setPosition(200, 1190);
			topbarquestionimg.addListener(new InputListener() {

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// TODO Auto-generated method stub
					PPTXGame.toQuestionListScreen();
					return true;
				}

			});
			dugeonbackgroundimg = new Image(new Texture(
					ImgFile.dugeonbackground));

			dugeonbackgroundimg.setPosition(0, 1096);
			topbarhomeimg = new Image(new Texture(ImgFile.topbarhome));

			topbarhomeimg.setPosition(450, 1190);
			topbarhomeimg.addListener(new InputListener() {

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// TODO Auto-generated method stub
					PPTXGame.toSelectionScreen();
					return true;
				}

			});
			
			topbar.addActor(dugeonbackgroundimg);
			//
			topbar.addActor(lbllogin);
			topbar.addActor(imgsetting);
			topbar.addActor(topbarhomeimg);
			// topbar.addActor(lblHP);
			topbar.addActor(topbarquestionimg);

		}

		return topbar;
	}
}
