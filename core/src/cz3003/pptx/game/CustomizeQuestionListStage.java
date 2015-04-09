package cz3003.pptx.game;

import java.io.File;
import java.io.FilenameFilter;
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
import cz3003.pptx.game.socialmedia.Profile;

public class CustomizeQuestionListStage extends Stage {
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
	Image clearquestionimage;
	ManageCustomizeQuestion managequestion;
	Table table;
	Label[] lblarray;

	
	public CustomizeQuestionListStage()
	{
		super();
		init();
	}
	public void init() {

		Image dugeonbackgroundimg = new Image(new Texture(
				ImgFile.dugeonbackground));

		dugeonbackgroundimg.setPosition(0, 0);
		this.addActor(dugeonbackgroundimg);

		this.addActor(TopBar.getTopbar());

		style = new LabelStyle(CusFontStyle.getBoldFont(), CusFontStyle
				.getTopbarFont().getColor());
	
		

		// question set list part

		final File[] filelist=finder("sdcard/mydugeon/");
		
		if (filelist.length >0) {
			int rowcount =filelist.length;
			table = new Table();
			table.setPosition(380, 700);
			
			Label lblusernametitle = new Label("Username", style);
			Label lblaccess = new Label("Access", style);
			table.add(lblusernametitle).width(300);
			table.add(lblaccess).width(200).align(Align.center).pad(10);
			
			String namearray[]=new String[rowcount];
			lblarray=new Label[rowcount];
			for ( int i = 0; i < rowcount; i++) {
			
				namearray[i]=filelist[i].getName().replace(".txt", "");
			}
			for ( int i = 0; i < rowcount; i++) {
				
			

				table.row();
				Label lblusername = new Label(namearray[i], style);
				lblini(i,namearray[i]);
				table.add(lblusername).width(300);
				table.add(lblarray[i]).width(200).align(Align.center).pad(10);
				

			}
			this.addActor(table);

		}
		



		Label lblquestionsets = new Label("Question Sets", style);
		lblquestionsets.setPosition(50, 1100);
		this.addActor(lblquestionsets);

	}
	private void lblini(int index,final String username)
	{
		
		
		lblarray[index] = new Label( "access", style);
		String i2 = String.valueOf("access");
		
		lblarray[index].addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x,
					float y, int pointer, int button) {
				// TODO Auto-generated method stub
				PPTXGame.toBattleScreen(-1, username );
				
				return true;
			}
			
		});
		lblarray[index].setWrap(true);
		
	}
	
	
	 public File[] finder( String dirName){
	    	File dir = new File(dirName);

	    	return dir.listFiles(new FilenameFilter() { 
	    	         public boolean accept(File dir, String filename)
	    	              { return filename.endsWith(".txt"); }
	    	} );

	    }

	private boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

}
