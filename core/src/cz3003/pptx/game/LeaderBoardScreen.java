package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import cz3003.pptx.game.socialmedia.Profile;

public class LeaderBoardScreen  implements Screen {
	PPTXGame game;
	Stage stage;
	LabelStyle style;
	public LeaderBoardScreen(PPTXGame game) {
		this.game = game;
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage =new Stage();
		stage.setViewport(AndroidView.getview());
		Gdx.input.setInputProcessor(stage);

		Image backgroundimg=new Image(new Texture(ImgFile.leaderboardback));
		backgroundimg.setPosition(0, 0);
		
		Image backbuttonimg=new Image(new Texture(ImgFile.leaderboardbackbutton));
		backbuttonimg.setPosition(40, 120);
		backbuttonimg.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				game.setScreen(game.resultscreen);
				return true;
			}
			
		});

		style = new LabelStyle(CusFontStyle.getLeaderboardFont(), CusFontStyle.getLeaderboardFont().getColor());
		int[] highScoreArray = Profile.instance.getStageHighScoreArray();
		Label lblfirstname= new Label("Wang Bowen", style);
		lblfirstname.setPosition(348, 734);
		lblfirstname.setSize(180, 43);
		lblfirstname.setAlignment(Align.left);
		Label lblsecondname= new Label("Tang Chen", style);
		lblsecondname.setPosition(348, 526);
		lblsecondname.setSize(180, 43);
		lblsecondname.setAlignment(Align.left);
		Label lblthirdname= new Label("Yanng Xu", style);
		lblthirdname.setPosition(348, 341);
		lblthirdname.setSize(180, 43);
		lblthirdname.setAlignment(Align.left);
		Label lblfirstpoint= new Label("512 PTS", style);
		lblfirstpoint.setPosition(550,734 );
		lblfirstpoint.setAlignment(Align.left);
		lblfirstpoint.setSize(90, 43);
		Label lblsecondpoint= new Label("410 PTS", style);
		lblsecondpoint.setPosition(550, 526);
		lblsecondpoint.setSize(90, 43);
		lblsecondpoint.setAlignment(Align.left);
		Label lblthirdpoint= new Label("300 PTS", style);
		lblthirdpoint.setPosition(550, 341);
		lblthirdpoint.setSize(90, 43);
		lblthirdpoint.setAlignment(Align.left);
		
		
		stage.addActor(backgroundimg);
		stage.addActor(lblfirstname);
		stage.addActor(lblsecondname);
		stage.addActor(lblthirdname);
		stage.addActor(lblfirstpoint);
		stage.addActor(lblsecondpoint);
		stage.addActor(lblthirdpoint);
		
		stage.addActor(backbuttonimg);
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		AndroidView.getview().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

}
