package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SelectionStage extends Stage {

	Texture texture;
	PrivateGirl privategirl;
	int speed = 3;
	int x = 150;
	int y = 150;
	// button A B part
	ImageButton btnA;
	PPTXGame game;

	public SelectionStage(PPTXGame game) {
		super();
		this.game = game;
		init();
	}

	public void init() {
		privategirl = new PrivateGirl(x, y);
		this.addActor(privategirl.backgroundimage);
		this.addActor(privategirl);
		this.addActor(privategirl.touchpad);
		// button A part
		Texture tex = new Texture(Gdx.files.internal("button.png"));
		TextureRegion[][] tem = TextureRegion.split(tex, 120, 120);
		TextureRegion buttonup = tem[0][2];
		TextureRegion buttondown = tem[0][3];
		TextureRegionDrawable up = new TextureRegionDrawable(buttonup);
		TextureRegionDrawable down = new TextureRegionDrawable(buttondown);
		btnA = new ImageButton(up, down);
		btnA.setSize(60, 60);
		btnA.setPosition(Gdx.graphics.getWidth() - 100, 30);
		btnA.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.print("x is :" + PrivateGirl.x + " y is :"
						+ PrivateGirl.y);
				if ((PrivateGirl.x >= 50 && PrivateGirl.x <= 140)
						&& (PrivateGirl.y >= 200 && PrivateGirl.y <= 300)) {
					//Constants.StageFlag = Constants.QuestionStageOn;
					game.setScreen(game.questionscreen);
				}
				return true;
			}
		});
		this.addActor(btnA);

	}

}
