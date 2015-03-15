package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class ResultStage extends Stage {
	Label lblresult;
	BitmapFont font;
	LabelStyle style;
	/* ******background part ******** */
	TextureRegion backgroundregion;
	Image backgroundimage;
	public ResultStage() {
		super();
		
		init();
		
	
		
		

	}
	public void init(){

		backgroundregion=new TextureRegion(new Texture(Gdx.files.internal("imagebear.jpg")),0,0,512,512);
		backgroundimage=new Image(backgroundregion);
		backgroundimage.setPosition(0, 0);
		backgroundimage.setSize(512, 512);
		this.addActor(backgroundimage);
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.setScale(2);
		style = new LabelStyle(font, font.getColor());
		
		String result="Your Score is "+Test.getScore();
		lblresult = new Label(result, style);
		lblresult.setPosition(100, 300);
		lblresult.setFontScale(1);
		this.addActor(lblresult);
		

	}
	public void updatescore()
	{
		String result="Your Score is "+Test.getScore();
		lblresult.setText(result);
	}
	public void backtomain()
	{
		DelayAction delay = Actions.delay(1f);
		this.addAction(delay);
		Constants.StageFlag=Constants.SelectionStageOn;
	}

}
