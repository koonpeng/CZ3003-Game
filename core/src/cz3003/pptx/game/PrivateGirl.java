package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PrivateGirl extends Actor{
	public static float x;
	public static float y;
	public static float statetime;
	
	Texture texture;
	TextureRegion currentFrame;
	/* ******background part ******** */
	TextureRegion backgroundregion;
	Image backgroundimage;
	/* ******Annimation Part****** */
	public static final int FRAME_COLS = 4;
	public static final int FRAME_ROWS = 4;
	public static final float pic_interval = 0.285f;
	Animation walkAnimation;
	Texture walkSheet;
	TextureRegion[] walkFrames;

	//touchpad part
	Texture touchpadtexture;
	TextureRegionDrawable touchBackground;
	TextureRegionDrawable touchKnob;
	TouchpadStyle touchpadStyle;
	Touchpad touchpad;
	
	Animation aniRight;
	Animation aniLeft;
	Animation aniUp;
	Animation aniDown;
	Animation aniIdleL;
	Animation aniIdleR;
	Animation aniIdleU;
	Animation aniIdleD;
	
	
	enum STATE{
		Up,Down,Left,Right,IdelL,IdelR,IdelU,IdelD
	};
	STATE state;
	STATE LastState;
	Sound sound;
	public PrivateGirl(float x,float y){
		this.x=x;
		this.y=y;
		state=STATE.IdelL;
		LastState=state;
		this.show();
	}
	
	public void show()
	{
		
		walkSheet = new Texture(ImgFile.smallwukongimg);

		TextureRegion[][] temp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);
		
		
		// down
		TextureRegion[] regionD = new TextureRegion[4];
		regionD[0] = temp[0][0];
		regionD[1] = temp[0][1];
		regionD[2] = temp[0][2];
		regionD[3] = temp[0][3];
		aniDown = new Animation(0.1f, regionD);
		// Left
		TextureRegion[] regionL = new TextureRegion[4];
		regionL[0] = temp[1][0];
		regionL[1] = temp[1][1];
		regionL[2] = temp[1][2];
		regionL[3] = temp[1][3];
		aniLeft = new Animation(0.1f, regionL);
		// Right
		TextureRegion[] regionR = new TextureRegion[4];
		regionR[0] = temp[2][0];
		regionR[1] = temp[2][1];
		regionR[2] = temp[2][2];
		regionR[3] = temp[2][3];
		aniRight = new Animation(0.1f, regionR);
		// Up
		TextureRegion[] regionU = new TextureRegion[4];
		regionU[0] = temp[3][0];
		regionU[1] = temp[3][1];
		regionU[2] = temp[3][2];
		regionU[3] = temp[3][3];
		aniUp = new Animation(0.1f, regionU);
		
		
		// idle
		TextureRegion[] regionIL = new TextureRegion[1];
		TextureRegion[] regionIR = new TextureRegion[1];
		TextureRegion[] regionIU = new TextureRegion[1];
		TextureRegion[] regionID = new TextureRegion[1];
		regionID[0] = temp[0][0];
		regionIL[0] = temp[1][0];
		regionIR[0] = temp[2][0];
		regionIU[0] = temp[3][0];
		
		aniIdleD = new Animation(0.1f, regionID);
		aniIdleL = new Animation(0.1f, regionIL);
		aniIdleR = new Animation(0.1f, regionIR);
		aniIdleU = new Animation(0.1f, regionIU);

		backgroundregion=new TextureRegion(new Texture(ImgFile.smallwukongbackground),0,0,720,1280);
		backgroundimage=new Image(backgroundregion);
		backgroundimage.setPosition(0, 0);
		backgroundimage.setSize(720, 1280);
		
		//touchpad part
		touchpadtexture=new Texture(Gdx.files.internal("touchpad.png"));
		touchBackground=new TextureRegionDrawable(new TextureRegion(touchpadtexture,0,0,128,128));
		touchKnob=new TextureRegionDrawable(new TextureRegion(touchpadtexture,128,0,128,128));
		touchpadStyle=new TouchpadStyle(touchBackground,touchKnob);
		touchpad=new Touchpad(15,touchpadStyle);
		touchpad.setBounds(0, 0, 150, 150);	
 	}
	
	public void update()
	{
		double radius=Math.atan2(touchpad.getKnobPercentY(),touchpad.getKnobPercentX());
		double degree=radius*180/Math.PI;
		if (touchpad.isTouched()) {


			if (degree>=45 && degree<=135 ) {
			
				if (y >Constants.SCREENHEIGHT-200-currentFrame.getRegionHeight())
					this.y =Constants.SCREENHEIGHT-200-currentFrame.getRegionHeight();
					
				else
					this.y += 3f;
			
				state = STATE.Up;
				LastState=STATE.IdelU;
				
			} else if (degree<45 && degree>=-45 ) {
				
				if (x >Constants.SCREENWIDTH-currentFrame.getRegionWidth()){
					
					this.x = Constants.SCREENWIDTH-currentFrame.getRegionWidth();}
				
				else
					this.x += 3f;
				state = STATE.Right;
				LastState=STATE.IdelR;
				
			} if (degree>=-135 && degree<-45 ) {
				if (y < 20)
					this.y = 20;
				else
					this.y -= 3f;
				
				state = STATE.Down;
				LastState=STATE.IdelD;
				
			} else if ((degree<-135 && degree>=-180)||(degree>135 && degree<=180)) {
				
				if (x < 20)
					this.x = 20;
				else
					this.x -= 3f;
				state = STATE.Left;
				LastState=STATE.IdelL;
			}
		}
		else
		{
			state=LastState;
		}
	}
	
	public void aniCheck()
	{
		if(state==STATE.Left){
			currentFrame=aniLeft.getKeyFrame(statetime,true);
		}else if(state==STATE.Right){
			currentFrame=aniRight.getKeyFrame(statetime,true);
		}else if(state==STATE.IdelL){
			currentFrame=aniIdleL.getKeyFrame(statetime,true);
		}else if(state==STATE.IdelR){
			currentFrame=aniIdleR.getKeyFrame(statetime,true);
		}
		else if(state==STATE.Up){
			currentFrame=aniUp.getKeyFrame(statetime,true);
		}else if(state==STATE.Down){
			currentFrame=aniDown.getKeyFrame(statetime,true);
		}else if(state==STATE.IdelU){
			currentFrame=aniIdleU.getKeyFrame(statetime,true);
		}else if(state==STATE.IdelD){
			currentFrame=aniIdleD.getKeyFrame(statetime,true);
		}
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		statetime+=Gdx.graphics.getDeltaTime();
		this.update();
		this.aniCheck();

		batch.draw(currentFrame,x,y);
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

}
