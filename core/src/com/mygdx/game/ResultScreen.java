package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;

public class ResultScreen implements Screen {
		MyGdxGame game;
		ResultStage resultstage;
		public float statetime;
		public ResultScreen(MyGdxGame game)
		{
			this.game=game;
			
		}

		@Override
		public void show() {
			// TODO Auto-generated method stub
			resultstage=new ResultStage(game);
			resultstage.setViewport(AndroidView.getview());
			Gdx.input.setInputProcessor(resultstage);
			statetime=0;
		
		}

		@Override
		public void render(float delta) {
			// TODO Auto-generated method stub
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			resultstage.act();
			resultstage.draw();
			statetime+=Gdx.graphics.getDeltaTime();
			if(Constants.StageFlag== Constants.SelectionStageOn)
			{
			
			}
//			if(statetime>5)
//			{
//				//game.setScreen(game.selectionscreen);
//			}
			
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
			
		}

	}

