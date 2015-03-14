package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class QuestionStage extends Stage {

	
	Texture backgroundtexture;
	ImageButton[] btnABCD;
	MyGdxGame game;
	/* ******Label Control Part****** */
	Stage stage;
	LabelStyle style;
	BitmapFont font;
	Label lblTitle;
	Label lblA;
	Label lblB;
	Label lblC;
	Label lblD;
	
	Test test;
	
	public QuestionStage(MyGdxGame game)
	{
		super();
		this.game=game;
		test=new Test(0);
		uiinit();
		
	}
	private void updatequestion()
	{
		test.nextquestion();
		String question[]=test.getQuestion();
		
		if(question!=null)
		{
			lblTitle.setText(question[0]);
			lblA.setText(question[1]);
			lblB.setText(question[2]);
			lblC.setText(question[3]);
			lblD.setText(question[4]);
			
		}
		else
		{
			//Constants.StageFlag=Constants.ResultStageOn;
			game.setScreen(game.resultscreen);
			
		//quiz end	
		}
	}
	private void buttoninit( final String answer,int index,int x, int y)
	{
		
		Texture tex = new Texture(Gdx.files.internal("button.png"));
		TextureRegion[][] tem = TextureRegion.split(tex, 120, 120);
		
		TextureRegion buttonup = tem[0][0];
		buttonup.flip(true,false);
		TextureRegion buttondown = tem[0][1];
		buttondown.flip(true,false);
		TextureRegionDrawable up = new TextureRegionDrawable(buttonup);
		TextureRegionDrawable down = new TextureRegionDrawable(buttondown);
		btnABCD[index] = new ImageButton(up, down);
		
		btnABCD[index].setPosition(x, y);
		btnABCD[index].setSize(40, 40);
		btnABCD[index].addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				test.verifyAnswer(answer);
				updatequestion();
				System.out.println(Test.getScore());
				return true;
			}
		});
		this.addActor(btnABCD[index]);
	}
	public void uiinit() {
		
		font = new BitmapFont();
		font.setColor(Color.RED);
		style= new LabelStyle(font,font.getColor());
		String question[]=test.getQuestion();
		/* ******Label Control Title Part****** */
		lblTitle=new Label(question[0],style);
		lblTitle.setPosition(50,400);
		lblTitle.setFontScale(1);
		this.addActor(lblTitle);
		/* ******Label Control A Part****** */
		lblA=new Label(question[1],style);
		lblA.setPosition(70,330);
		lblA.setFontScale(1);
		this.addActor(lblA);
		/* ******Label Control B Part****** */
		lblB=new Label(question[2],style);
		lblB.setPosition(70,280);
		lblB.setFontScale(1);
		this.addActor(lblB);
		/* ******Label Control C Part****** */
		lblC=new Label(question[3],style);
		lblC.setPosition(70,230);
		lblC.setFontScale(1);
		this.addActor(lblC);
		/* ******Label Control D Part****** */
		lblD=new Label(question[4],style);
		lblD.setPosition(70,180);
		lblD.setFontScale(1);
		this.addActor(lblD);
		btnABCD=new ImageButton[4];
		buttoninit( "A",0,15, 320);
		buttoninit( "B",1,15, 270);
		buttoninit( "C",2,15, 220);
		buttoninit( "D",3,15, 170);
		
		
		
		
	}
	
}
