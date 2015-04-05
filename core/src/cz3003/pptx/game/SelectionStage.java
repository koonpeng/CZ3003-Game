package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

import cz3003.pptx.game.socialmedia.Profile;

public class SelectionStage extends Stage {

	
	
	
	Texture texture;
	TextureAtlas dugeonatlas;
	PrivateGirl privategirl;
	int speed = 3;
	int x = 150;
	int y = 150;
	// button A B part
	ImageButton btnA;
	
	PPTXGame game;
	//label
	LabelStyle style;
	Label lblHP;
	static Label lblchoosedungeon;
	
	Image[] imageDungeon;
	private static int DUNGEONNO=5;
	public SelectionStage(PPTXGame game) {
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
					//game.toBattleScreen(dungeonid, name);
					// game.setScreen(game.questionscreen);
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
		
		dugeonatlas = new TextureAtlas(ImgFile.dungeon);
		
		
		dugeonini(0,0,700,false);
		dugeonini(1,240,700,Profile.instance.getStageLockedArray()[1]);
		dugeonini(2,480,700,Profile.instance.getStageLockedArray()[2]);
		dugeonini(3,120,380,Profile.instance.getStageLockedArray()[3]);
		dugeonini(4,360,380,Profile.instance.getStageLockedArray()[4]);
		
		privategirl = new PrivateGirl(Constants.SCREENWIDTH/2, Constants.SCREENHEIGHT/2);
		//this.addActor(privategirl.backgroundimage);
		this.addActor(privategirl);
		this.addActor(privategirl.touchpad);
		Hp monsterhp = new Hp(200, 1200, 3);
		this.addActor(monsterhp);
		lblchoosedungeon = new Label("Choose a dungeon", style);
		lblchoosedungeon.setPosition(0, 1080);

		lblchoosedungeon.setWidth(720);
		lblchoosedungeon.setHeight(100);
		lblchoosedungeon.setWrap(true);
		lblchoosedungeon.setAlignment(Align.topLeft);
		this.addActor(lblchoosedungeon);
		
	}
	private void dugeonini(int index,int x, int y,Boolean lock)
	{
		//TextureRegion dungeonRegion=new TextureRegion(new Texture(ImgFile.dungeon),0,0,240,240);
		String dugegonSname=Integer.toString(index+1);
		if(lock)
		{
			dugegonSname+="_lock";
		}
		imageDungeon[index]=new Image(dugeonatlas.findRegion(dugegonSname));
		
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
