package cz3003.pptx.game;

import java.io.IOException;

import org.json.JSONException;

import jdk.internal.util.xml.impl.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import cz3003.pptx.game.Treasure.STATE;
import cz3003.pptx.game.battle.quiz.Quiz;

public class QuestionListStage extends Stage {
	// @Override
	// public void draw() {
	// // TODO Auto-generated method stub
	// statetime = Gdx.graphics.getDeltaTime();
	// batch.draw(treasureAnimation.getKeyFrame(statetime), x, y, width,
	// height);
	//
	//
	// super.draw();
	// }
	Label lblresult;
	BitmapFont font;
	LabelStyle style;
	LabelStyle style2;

	/* ******background part ******** */
	Texture backgroundregion;
	Image backgroundimage;
	Image createquestionimage;
	Image managequestionimage;
	
	ManageQuestion managequestion;
	public QuestionListStage() {
		super();
	
		init(false);
		show();

	}

	public void show() {

	}

	public void init(Boolean result) {
		
		
	
		style = new LabelStyle(CusFontStyle.getBoldFont(), CusFontStyle
						.getBoldFont().getColor());
		style = new LabelStyle(CusFontStyle.getNormalFont(), CusFontStyle
				.getNormalFont().getColor());
		
		createquestionimage = new Image(new Texture(ImgFile.creatquestionbutton));
		createquestionimage.setPosition(130, 220);
		
		createquestionimage.addListener(new InputListener() {
			String userinput;
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				userinput="";
				
					Gdx.input.getTextInput(new TextInputListener() {
						@Override
						public void input(String texteSaisi) {
							userinput=texteSaisi;
							Gdx.app.postRunnable(new Runnable() {
								 @Override
								 public void run() {
								 // 产生结果
									 PPTXGame.toCreateQuestionScreen(Integer.parseInt(userinput),true);;
								 }
								 });

						
						}
						@Override
						public void canceled() {
							// TODO Auto-generated method stub
							
						}

					}, "Please input the number of questions you want to create" , null, null);
					
					
					
				return true;
			}

		});
		this.addActor(createquestionimage);
		
		
		
		//question set list part
		
		try {
			managequestion = new ManageQuestion("username");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(managequestion.getLength()!=-1)
		{
			int rowcount=managequestion.getLength();
			Table table=new Table();
			table.setPosition(380,700);
			Label lblindex=new Label("index",style);
			Label lblquestion=new Label("Question Title",style);
			table.add(lblindex).width(120).align(Align.center).pad(10);;
			table.add(lblquestion).width(500);
			for(int i=0;i<rowcount;i++)
			{
				
				String retrivequestion[]=new String[7];
				try {
					retrivequestion=managequestion.getQnsPos(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				table.row();
				String i2=String.valueOf(i);
				Label labelcount=new Label(i2,style);
				Label lbltitle=new Label(retrivequestion[1],style);
				lbltitle.setWrap(true);
				table.add(labelcount).width(120).align(Align.center).pad(10);
				table.add(lbltitle).width(500);
				
				
			}
			this.addActor(table);
			
		}
		managequestionimage = new Image(new Texture(ImgFile.managequestionbutton));
		managequestionimage.setPosition(130, 130);
		
		managequestionimage.addListener(new InputListener() {
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				
				if(managequestion.getLength()!=-1)
				{
				
				PPTXGame.toCreateQuestionScreen(managequestion.getLength(),false);;//to edit question
				
				}
				return true;
			}

		});
		this.addActor(managequestionimage);
		Label lblquestionsets=new Label("Question Sets",style);
		lblquestionsets.setPosition(50, 1100);
		this.addActor(lblquestionsets);
		
		
		
		
		
		
		
		}
	private boolean tryParseInt(String value)  
	{  
	     try  
	     {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch(NumberFormatException nfe)  
	      {  
	          return false;  
	      }  
	}
	
}
