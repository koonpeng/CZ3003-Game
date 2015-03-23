package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Dragon extends Actor {
	private static final float pic_interval = 0.2f;
	Texture dragontexture;
	Animation dragonanimation;
	TextureRegion[] dragonregion;
	TextureRegion dragoncurrentFrame;
	float dragonstatetime=0;
	private int x;
	private int y;

	public Dragon(int x,int y)
	{
		this.x=x;
		this.y=y;
		uiini();
	}
	private void uiini()
	{
	// dragon
			dragontexture = new Texture(ImgFile.dragon);
			TextureRegion[][] temp3 = TextureRegion.split(dragontexture,
					dragontexture.getWidth() / 5,
					dragontexture.getHeight() / 2);
			for(TextureRegion[] region1 : temp3)
				for(TextureRegion region2: region1)
				{
					region2.flip(true, false);
				}
			
			dragonregion = new TextureRegion[4];
			
			dragonregion[0] = temp3[0][0];
			dragonregion[1] = temp3[0][1];
			dragonregion[2] = temp3[0][2];
			dragonregion[3] = temp3[0][3];

			
			dragonanimation = new Animation(pic_interval, dragonregion);
	}
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		dragonstatetime+=Gdx.graphics.getDeltaTime();
		dragoncurrentFrame=dragonanimation.getKeyFrame(dragonstatetime,true);
		batch.draw(dragoncurrentFrame, x, y+30);
		
	}

}
