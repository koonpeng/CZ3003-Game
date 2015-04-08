package cz3003.pptx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import cz3003.pptx.game.socialmedia.Profile;


public class TopBar {

	private static Group topbar;
	static Label lbllogin;
	static Label lblsetting;
	
	static Label lblHP;
	
	//label
	static LabelStyle style;
	static BitmapFont font;
	public static Group getTopbar()
	{
		
		style = new LabelStyle(PPTXGame.getAssetManager().get("calibri36.ttf", BitmapFont.class), Color.BLACK);
		
		if(lbllogin==null)
		{
			topbar=new Group();
			/* ******Label Control Title Part****** */
			lbllogin = new Label(
					Profile.instance.getUsername(), style);
			lbllogin.setPosition(0, 1180);
			//lbllogin.setFontScale(2);
			lbllogin.setWidth(200);
			lbllogin.setHeight(100);
			lbllogin.setWrap(true);
			lbllogin.setAlignment(Align.left);
			
			
			lblHP = new Label("HP:", style);
			lblHP.setPosition(200,1180);
			lblHP.setWidth(320);
			lblHP.setHeight(100);
		
			lblHP.setWrap(true);
			lblHP.setAlignment(Align.topLeft);
			
			
			lblsetting = new Label("Settings", style);
			lblsetting.setPosition(520, 1180);

			lblsetting.setWidth(200);
			lblsetting.setHeight(100);
			lblsetting.setWrap(true);
			lblsetting.setAlignment(Align.center);
			
			
			topbar.addActor(lbllogin);
			topbar.addActor(lblsetting);
		
			topbar.addActor(lblHP);
			
		}
	
		return topbar;
	}
}
