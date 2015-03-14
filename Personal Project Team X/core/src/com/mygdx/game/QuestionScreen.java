package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class QuestionScreen implements Screen {
		MyGdxGame game;
		QuestionStage questionstage;
		public QuestionScreen(MyGdxGame game)
		{
			this.game=game;
			
		}

		@Override
		public void show() {
			// TODO Auto-generated method stub
			questionstage=new QuestionStage(game);
			Gdx.input.setInputProcessor(questionstage);
		}

		@Override
		public void render(float delta) {
			// TODO Auto-generated method stub
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			questionstage.act();
			questionstage.draw();
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