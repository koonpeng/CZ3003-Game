package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.TextInputListener;

import com.badlogic.gdx.Input.Keys;

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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import cz3003.pptx.game.screen.MenuScreen;
import cz3003.pptx.game.socialmedia.Profile;

public class SelectionStage extends Stage {
	
	private static final String TAG = SelectionStage.class.getName();

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
	Image customizedugeonimg;
	Image[] imageDungeon;
	Label lblsummary;
	private static int DUNGEONNO=5;
	private static int currentDungeon;
	private static boolean selecttype;//true for local
	public static boolean isSelecttype() {
		return selecttype;
	}
	public static void setSelecttype(boolean selecttype) {
		SelectionStage.selecttype = selecttype;
	}
	public SelectionStage() {
		super();
		
		//super(new StretchViewport(PPTXGame.GAME_WIDTH, PPTXGame.GAME_HEIGHT));

		init();
	
	
	}
	public static int getCurrentDungeon() {
		return currentDungeon;
	}
	private int stagesensation()
	{
		for(int i=0;i<DUNGEONNO;i++)
		{
			System.out.println(imageDungeon[i].getX()+imageDungeon[i].getImageWidth());
			if ((PrivateGirl.x >= imageDungeon[i].getX() && PrivateGirl.x <= imageDungeon[i].getX()+imageDungeon[i].getImageWidth())&& (PrivateGirl.y >= imageDungeon[i].getY() && PrivateGirl.y <= imageDungeon[i].getImageHeight()+imageDungeon[i].getY())) 
			{
				selecttype=true;
				return i+1;
				
				
			}
		}
		if ((PrivateGirl.x >= 240 && PrivateGirl.x <= 480)&& (PrivateGirl.y >= 880&& PrivateGirl.y <= 880+240))
		{
			selecttype=false;
			return -9;
			
		}
		
		
			return -1;
		
	}
	public void init() {
		Image dugeonbackgroundimg=new Image(new Texture(ImgFile.dugeonbackground));
		dugeonbackgroundimg.setPosition(0, 0);
		this.addActor(dugeonbackgroundimg);
		imageDungeon=new Image[DUNGEONNO];
		
		Gdx.input.setCatchBackKey(true);
		
		// button A part
		Texture tex = new Texture(Gdx.files.internal("button.png"));
		TextureRegion[][] tem = TextureRegion.split(tex, 120, 120);
		TextureRegion buttonup = tem[0][2];
		TextureRegion buttondown = tem[0][3];
		TextureRegionDrawable up = new TextureRegionDrawable(buttonup);
		TextureRegionDrawable down = new TextureRegionDrawable(buttondown);
		btnA = new ImageButton(up, down);
		btnA.setSize(60, 60);
		btnA.setPosition(720-150, 155);
		btnA.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				int dungeonid=stagesensation();
				if(dungeonid==	-9)
				{
					PPTXGame.toCustomizequestionlistscreen(true);
				}
				else if(dungeonid!=-1)
				{
					currentDungeon=dungeonid;
					game.toBattleScreen(dungeonid, Profile.instance.getUsername());
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
		
		style = new LabelStyle(CusFontStyle.getBoldFont(), CusFontStyle
				.getTopbarFont().getColor());
	
		this.addActor(TopBar.getTopbar());
		
		dugeonatlas = new TextureAtlas(ImgFile.dungeon);
		
		
		dugeonini(0,0,680,false);
		dugeonini(1,240,680,Profile.instance.getStageLockedArray()[1]);
		dugeonini(2,480,680,Profile.instance.getStageLockedArray()[2]);
		dugeonini(3,120,380,Profile.instance.getStageLockedArray()[3]);
		dugeonini(4,360,380,Profile.instance.getStageLockedArray()[4]);
		customizedugeonimg = new Image(new Texture(ImgFile.otehrequestion));
		customizedugeonimg.setPosition(240,880);
		
		customizedugeonimg.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				PPTXGame.toCustomizequestionlistscreen(false);
				return true;
			}
			
		});
		this.addActor(customizedugeonimg);
		privategirl = new PrivateGirl(Constants.SCREENWIDTH/2, Constants.SCREENHEIGHT/2);
		//this.addActor(privategirl.backgroundimage);
		this.addActor(privategirl);
		this.addActor(privategirl.touchpad);
		
		lblchoosedungeon = new Label("Choose a dungeon: ", style);
		lblchoosedungeon.setPosition(0, 1030);

		lblchoosedungeon.setWidth(720);
		lblchoosedungeon.setHeight(100);
		lblchoosedungeon.setWrap(true);
		lblchoosedungeon.setAlignment(Align.topLeft);
		this.addActor(lblchoosedungeon);
		//another
		lblsummary = new Label("Summary", style);
		lblsummary.setPosition(260, 80);

		

		lblsummary.setWrap(true);
		lblsummary.setAlignment(Align.topLeft);

		lblsummary.addListener(new InputListener(){
			String userinput;
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				

				Gdx.input.getTextInput(
						new TextInputListener() {
							@Override
							public void input(String texteSaisi) {
								userinput = texteSaisi;
								Gdx.app.postRunnable(new Runnable() {
									@Override
									public void run() {
										// 产生结果
										
											if (Integer.parseInt(userinput) ==123456){
												PPTXGame.toSummeryscreen();
													
												
											}
										
									}
								});

							}

							@Override
							public void canceled() {
								// TODO Auto-generated method stub

							}

						},
						"Please enter the password to see the summary",
						null, null);
				//PPTXGame.toSummeryscreen();
				return true;
			}
			
		});
		this.addActor(lblsummary);
		
		

	}
	
	@Override
	public boolean keyUp(int keycode){
		if (keycode == Keys.BACK){
			if (PPTXGame.getInstance() == null){
				Gdx.app.log(TAG, "PPTXGame instance is null");
			}
			PPTXGame.getInstance().menuscreen.show();
			PPTXGame.getInstance().setScreen(PPTXGame.getInstance().menuscreen);
		}
		return false;

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
