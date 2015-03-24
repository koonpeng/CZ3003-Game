package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class LoginScreen implements Screen {
	MyGdxGame game;
	/* ******Label Control Part****** */
	Stage stage;
	LabelStyle style;
	BitmapFont font;
	Label lbluser;
	Label lblpassword;
	/* ****Submit Control Part ***** */
	Image submitimage;
	
	/* ****TextField  Control Part ***** */
	TextField accountTextField;
	TextField passwordTextField;
	public LoginScreen(MyGdxGame myGdxGame) {
		// TODO Auto-generated constructor stub
		this.game = game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		font = new BitmapFont();
		font.setColor(Color.RED);
		style = new LabelStyle(font, font.getColor());
		/* ******Label Control User Part****** */
		lbluser = new Label("User", style);
		lbluser.setPosition(50, 400);
		lbluser.setFontScale(1);
		stage.addActor(lbluser);
		/* ******Label Control Password Part****** */
		lbluser = new Label("Password", style);
		lbluser.setPosition(50, 200);
		lbluser.setFontScale(1);
		stage.addActor(lbluser);
		
		
		Texture texture = new Texture(Gdx.files.internal("shop.png"));

		TextureRegion SubmitRegion = new TextureRegion(texture, 512, 256, 512, 128);
		submitimage = new Image(SubmitRegion);
		submitimage.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				
				return true;
			}

		});

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

	}

}
