package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.FightCharacter.STATE;

public class Treasure extends Actor {

	// trasure
	Texture treasureTexture;
	private static final int FRAME_COLS = 5;
	private static final int FRAME_ROWS = 2;
	private static final float pic_interval = 0.285f;
	TextureRegion[] treasureFrames;
	Animation treasureAnimation;
	TextureRegion currentFrame;
	float statetime;
	TextureRegion[] treasureRegion;
	enum STATE {
		openbox,movebox;
	};

	STATE state;
	public Treasure()
	{
		uiini();
	}
	private void uiini() {
		state=STATE.movebox;
		treasureTexture = new Texture(ImgFile.treasureaction);

		TextureRegion[][] temp = TextureRegion.split(treasureTexture,
				treasureTexture.getWidth() / FRAME_COLS,
				treasureTexture.getHeight() / FRAME_ROWS);

		treasureRegion = new TextureRegion[3];
		treasureRegion[0] = temp[0][0];
		treasureRegion[1] = temp[0][1];
		treasureRegion[2] = temp[0][2];
		treasureAnimation = new Animation(pic_interval, treasureRegion);
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		if(state==STATE.movebox)
		{
			batch.draw(treasureRegion[0], getX(), getY(),
					treasureRegion[0].getRegionWidth() / 2,
					treasureRegion[0].getRegionHeight() / 2,
	                treasureRegion[0].getRegionWidth(),
	                treasureRegion[0].getRegionHeight(), getScaleX(), getScaleY(),
	                getRotation());

			 statetime=0;
			 
			
		}
		else
		{	
			
			if(!treasureAnimation.isAnimationFinished(statetime))
				
			{
				statetime+=Gdx.graphics.getDeltaTime();
				currentFrame=treasureAnimation.getKeyFrame(statetime, false);
				batch.draw(currentFrame,  Gdx.graphics.getWidth()/2-100, Gdx.graphics.getHeight()/2-300);
			}
		}
		
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

}
