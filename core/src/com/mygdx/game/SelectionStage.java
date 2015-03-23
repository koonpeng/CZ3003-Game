package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SelectionStage extends Stage {

	
	
	
	Texture texture;
	PrivateGirl privategirl;
	int speed = 3;
	int x = 150;
	int y = 150;
	// button A B part
	ImageButton btnA;
	
	MyGdxGame game;
	//label
	LabelStyle style;
	Label lblHP;
	
	
	Image[] imageDungeon;
	private static int DUNGEONNO=5;
	public SelectionStage(MyGdxGame game) {
		super();
		this.game = game;
		
		
		init();
	
	
	}
	private int stagesensation()
	{
		for(int i=0;i<DUNGEONNO;i++)
		{
			System.out.println(imageDungeon[i].getX()+imageDungeon[i].getImageWidth());
			if ((PrivateGirl.x >= imageDungeon[i].getX() && PrivateGirl.x <= imageDungeon[i].getX()+imageDungeon[i].getImageWidth())&& (PrivateGirl.y >= imageDungeon[i].getY() && PrivateGirl.y <= imageDungeon[i].getImageHeight()+imageDungeon[i].getY())) 
			{
				return i;
			}
		}
		
		
			return -1;
		
	}
	public void init() {
		imageDungeon=new Image[DUNGEONNO];
		
		// button A part
		Texture tex = new Texture(Gdx.files.internal("button.png"));
		TextureRegion[][] tem = TextureRegion.split(tex, 120, 120);
		TextureRegion buttonup = tem[0][2];
		TextureRegion buttondown = tem[0][3];
		TextureRegionDrawable up = new TextureRegionDrawable(buttonup);
		TextureRegionDrawable down = new TextureRegionDrawable(buttondown);
		btnA = new ImageButton(up, down);
		btnA.setSize(60, 60);
		btnA.setPosition(Constants.SCREENWIDTH-100, 30);
		btnA.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				int dungeonid=stagesensation();
				if(dungeonid!=-1)
				{
					game.questionscreen.setDungonid(dungeonid);
					game.setScreen(game.questionscreen);
				}
//				System.out.print("x is :" + PrivateGirl.x + " y is :"
//						+ PrivateGirl.y);
//				if ((PrivateGirl.x >= 50 && PrivateGirl.x <= 140)
//						&& (PrivateGirl.y >= 200 && PrivateGirl.y <= 300)) {
//					//Constants.StageFlag = Constants.QuestionStageOn;
					
//				}
			return true;
			}
		});
		this.addActor(btnA);
		
		style =new LabelStyle(CusFontStyle.getNormalFont(), CusFontStyle.getNormalFont().getColor());
	
		this.addActor(TopBar.getTopbar());

		dugeonini(0,0,700);
		dugeonini(1,240,700);
		dugeonini(2,480,700);
		dugeonini(3,120,380);
		dugeonini(4,360,380);

		privategirl = new PrivateGirl(Constants.SCREENWIDTH/2, Constants.SCREENHEIGHT/2);
		//this.addActor(privategirl.backgroundimage);
		this.addActor(privategirl);
		this.addActor(privategirl.touchpad);
		
		
	}
	private void dugeonini(int index,int x, int y)
	{
		TextureRegion dungeonRegion=new TextureRegion(new Texture(ImgFile.dungeon),0,0,200,200);
		imageDungeon[index]=new Image(dungeonRegion);
		
		imageDungeon[index].setPosition(x, y);
		this.addActor(imageDungeon[index]);
		//imageDungeon[index].addListener(new InputListener(){

//			@Override
//			public boolean touchDown(InputEvent event, float x, float y,
//					int pointer, int button) {
//				
//				return true;
//			}
			
		//});
	}
	
	
}
