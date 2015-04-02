package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;

public class QuestionListScreen implements Screen {
		PPTXGame game;
		QuestionListStage questionliststage;
		public float statetime;
		public QuestionListScreen(PPTXGame game)
		{
			this.game=game;
			
		}

		@Override
		public void show() {
			// TODO Auto-generated method stub
			questionliststage=new QuestionListStage();
			questionliststage.setViewport(AndroidView.getview());
			Gdx.input.setInputProcessor(questionliststage);
			statetime=0;
		
		}

		@Override
		public void render(float delta) {
			// TODO Auto-generated method stub
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			questionliststage.act();
			questionliststage.draw();
			
			
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

