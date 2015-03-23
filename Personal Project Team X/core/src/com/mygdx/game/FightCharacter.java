package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.PrivateGirl.STATE;

public class FightCharacter extends Actor {

	public static float x;
	public static float y;
	public static float statetime;
	TextureRegion currentFrame;

	/* ******Annimation Part****** */
	public static final int FRAME_COLS = 5;
	public static final int FRAME_ROWS = 5;
	public static final float pic_interval = 0.2f;
	Animation characterFightAnimation;

	Texture texturecharacter;
	Texture textureskill;

	TextureRegion skill;
	TextureRegion[] fightFrames;
	Animation attackAnimation;
	TextureRegion[] FightRegion;

	Texture texturecharacter2;
	Animation characterFightAnimation2;
	TextureRegion[] FightRegion2;
	TextureRegion currentFrame2;

	

	private int frameNumber;
	private int skillno = 1;
	private int skillwidth;
	enum STATE {
		Act, Idel, End
	};

	STATE state;

	public FightCharacter(int x, int y) {
		this.x = x;
		this.y = y;

		uiini();
		state = STATE.Idel;
	}

	public void uiini() {
		texturecharacter = new Texture(ImgFile.fightimg);

		TextureRegion[][] temp = TextureRegion.split(texturecharacter,
				texturecharacter.getWidth() / FRAME_COLS,
				texturecharacter.getHeight() / FRAME_ROWS);

		FightRegion = new TextureRegion[11];
		FightRegion[0] = temp[0][1];
		FightRegion[1] = temp[0][2];
		FightRegion[2] = temp[0][3];
		FightRegion[3] = temp[0][4];

		FightRegion[4] = temp[1][0];
		FightRegion[5] = temp[1][1];
		FightRegion[6] = temp[1][2];
		FightRegion[7] = temp[1][3];
		FightRegion[8] = temp[1][4];

		FightRegion[9] = temp[2][0];
		FightRegion[10] = temp[2][1];
		characterFightAnimation = new Animation(pic_interval, FightRegion);

		// attack
		texturecharacter2 = new Texture(ImgFile.attack);
		TextureRegion[][] temp2 = TextureRegion.split(texturecharacter2,
				texturecharacter2.getWidth() / FRAME_COLS,
				texturecharacter2.getHeight() / 2);

		FightRegion2 = new TextureRegion[8];
		FightRegion2[0] = temp2[0][0];
		FightRegion2[1] = temp2[0][1];
		FightRegion2[2] = temp2[0][2];
		FightRegion2[3] = temp2[0][3];
		FightRegion2[4] = temp2[0][4];
		FightRegion2[5] = temp2[1][0];
		FightRegion2[6] = temp2[1][1];
		FightRegion2[7] = temp2[1][2];
		//FightRegion2[7] = temp2[1][2];
		//FightRegion2[8] = temp2[1][3];
		//FightRegion2[9] = temp2[1][4];
		characterFightAnimation2 = new Animation(pic_interval, FightRegion2);

		
		textureskill = new Texture(ImgFile.skill1_1);
		

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
	
		if (state == STATE.Idel) {

			batch.draw(FightRegion[0], x, y);
		} else if (state == STATE.Act) {

			statetime += Gdx.graphics.getDeltaTime();
			switch (skillno) {
			case 1:
				
				frameNumber = (int) (statetime / pic_interval);
				currentFrame = characterFightAnimation.getKeyFrame(statetime,
						false);
				batch.draw(currentFrame, x, y);
				
				// skill part
				 if (frameNumber >= 5 && FightRegion.length - 1 > frameNumber) {
					skillwidth = (int) (textureskill.getWidth() * ((statetime * 1000 - 800) / 1200));
					

					skill= new TextureRegion(textureskill, 0, 0,
							skillwidth, textureskill.getHeight());
					
					batch.draw(skill, x + FightRegion[0].getRegionWidth() - 10, y + 80);

				}
				if (characterFightAnimation.isAnimationFinished(statetime)) {
					skillno = 2;
					// state = STATE.End;
					statetime = 0;

				}
				break;
			case 2:
				statetime += Gdx.graphics.getDeltaTime();
				currentFrame2 = characterFightAnimation2.getKeyFrame(statetime,
						false);
				batch.draw(FightRegion[0], x, y);
				batch.draw(currentFrame2, x + 500, y);
				if (characterFightAnimation2.isAnimationFinished(statetime)) {
					state = STATE.End;
					statetime = 0;
					skillno = 1;
				}
				break;

			}

		}
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

	public boolean isAnimationFinished(float stateTime) {

		int frameNumber = (int) (stateTime / pic_interval);

		return FightRegion.length - 1 > frameNumber;
	}

}
