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
	
	
	static Image imgbackground;
	static Image imghome;
	static Image imgdugeonlist;
	static Image imgcreatequestion;
	//label
	static LabelStyle style;
	static BitmapFont font;
	
	public static Group getTopbar()
	{
		
		style = new LabelStyle(CusFontStyle.getBoldFont(), CusFontStyle
				.getTopbarFont().getColor());
		
		if(lbllogin==null)
		{
			topbar=new Group();
			/* ******Label Control Title Part****** */
			lbllogin = new Label(
					Profile.instance.getUsername(), style);
			lbllogin.setPosition(280, 1180);
			//lbllogin.setFontScale(2);
			lbllogin.setWidth(200);
			lbllogin.setHeight(100);
			lbllogin.setWrap(true);
			lbllogin.setAlignment(Align.left);

			
			imghome = new Image(new Texture(ImgFile.barhome));
			imghome.setPosition(5,1180);
			imghome.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// TODO Auto-generated method stub
					PPTXGame.toSelectionScreen();
					return true;
				}
				
			});

			imgcreatequestion = new Image(new Texture(ImgFile.barquestion));
			imgcreatequestion.setPosition(460,1180);
			
			imgcreatequestion.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					// TODO Auto-generated method stub
					PPTXGame.toQuestionListScreen();
					return true;
				}
				
			});
			
			topbar.addActor(lbllogin);
			topbar.addActor(imgcreatequestion);
		
		
			topbar.addActor(imghome);
			
		}
	
		return topbar;
	}
}
