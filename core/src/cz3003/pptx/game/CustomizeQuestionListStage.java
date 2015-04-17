package cz3003.pptx.game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
	private static boolean refresh;
	
	public static boolean isRefresh() {
		return refresh;
	}
	public static void  setRefresh(boolean re) {
		refresh = re;
	}
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
	
		
		final Label lblquestionsets = new Label("Question Sets", style);
		lblquestionsets.setPosition(50, 1100);
		this.addActor(lblquestionsets);
		
		Image downloadquestion = new Image(new Texture(ImgFile.refreshquestion));
		downloadquestion.setPosition(250, 130);

		downloadquestion.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				
				downloadFile();
				PPTXGame.toCustomizequestionlistscreen(true);
				
				
				return true;
			}

		});
		this.addActor(downloadquestion);
		// question set list part
		
		final File[] filelist=finder("sdcard/mydugeon/");
		System.out.println(filelist.length);
		if (filelist.length >0) {
			
			int rowcount =filelist.length;
			table = new Table();
			table.setPosition(380, 700);
			table.top();
			
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
			if(refresh)
			{
				lblquestionsets.setText("Question Sets(Refresh Complete)");
			}

		}
		



		


	}
	
	 private boolean downloadFile() {//String path, String sUrl
	        // TODO Auto-generated method stub
	    	try {
	            //set the download URL, a url that points to a file on the internet
	            //this is the file to be downloaded
	    		
	            URL url = new URL("http://"+DbConfig.URL_http+"/mydugeon/wangbwhz.txt");

	            //create the new connection
	            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

	            //set up some things on the connection
	            urlConnection.setRequestMethod("GET");
	            urlConnection.setDoOutput(true);

	            //and connect!
	            urlConnection.connect();

	            //set the path where we want to save the file
	            //in this case, going to save it on the root directory of the
	            //sd card.
	    		File wallpaperDirectory = new File("sdcard/mydugeon/");
	    		wallpaperDirectory.mkdir();

	            //create a new file, specifying the path, and the filename
	            //which we want to save the file as.
	            File file = new File("sdcard/mydugeon/wangbwhz.txt");

	            //this will be used to write the downloaded data into the file we created
	            FileOutputStream fileOutput = new FileOutputStream(file);

	            //this will be used in reading the data from the internet
	            InputStream inputStream = urlConnection.getInputStream();

	            //this is the total size of the file
	            int totalSize = urlConnection.getContentLength();
	            //variable to store total downloaded bytes
	            int downloadedSize = 0;

	            //create a buffer...
	            byte[] buffer = new byte[1024];
	            int bufferLength = 0; //used to store a temporary size of the buffer

	            //now, read through the input buffer and write the contents to the file
	            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
	                    //add the data in the buffer to the file in the file output stream (the file on the sd card
	                    fileOutput.write(buffer, 0, bufferLength);


	            }
	            //close the output stream when done
	            fileOutput.close();
	            return true;
	    //catch some possible errors...
	    } catch (MalformedURLException e) {
	            e.printStackTrace();
	            return false;
	    } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	    }
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
