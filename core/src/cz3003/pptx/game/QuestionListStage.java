package cz3003.pptx.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
	Image clearquestionimage;
	ManageCustomizeQuestion managequestion;
	Table table;
	

	public QuestionListStage() {
		super();

		init(false);
		show();

	}

	public void show() {

	}

	public void init(Boolean result) {

		Image dugeonbackgroundimg = new Image(new Texture(
				ImgFile.dugeonbackground));
		dugeonbackgroundimg.setPosition(0, 0);
		this.addActor(dugeonbackgroundimg);
		style = new LabelStyle(CusFontStyle.getTopbarFont(), CusFontStyle
				.getTopbarFont().getColor());

		createquestionimage = new Image(
				new Texture(ImgFile.creatquestionbutton));
		createquestionimage.setPosition(150, 220);

		createquestionimage.addListener(new InputListener() {
			String userinput;

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				userinput = "";

				Gdx.input.getTextInput(
						new TextInputListener() {
							@Override
							public void input(String texteSaisi) {
								userinput = texteSaisi;
								Gdx.app.postRunnable(new Runnable() {
									@Override
									public void run() {
										// 产生结果
										if (tryParseInt(userinput)) {
											if (Integer.parseInt(userinput) >= 3
													&& Integer
															.parseInt(userinput) <= 30) {
												PPTXGame.toCreateQuestionScreen(
														Integer.parseInt(userinput),
														true);
												;
											}
										}
									}
								});

							}

							@Override
							public void canceled() {
								// TODO Auto-generated method stub

							}

						},
						"Please input the number of questions you want to create(min 3,max 30)",
						null, null);

				return true;
			}

		});
		this.addActor(createquestionimage);

		// question set list part

		try {
			managequestion = new ManageCustomizeQuestion(
					Profile.instance.getUsername());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (managequestion.getLength() != -1) {
			int rowcount = managequestion.getLength();
			table = new Table();
			table.setPosition(380, 700);
			Label lblindex = new Label("index", style);
			Label lblquestion = new Label("Question Title", style);
			table.add(lblindex).width(120).align(Align.center).pad(10);
			;
			table.add(lblquestion).width(500);
			for (int i = 0; i < rowcount; i++) {

				String retrivequestion[] = new String[7];
				try {
					retrivequestion = managequestion.getQnsPos(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				table.row();
				String i2 = String.valueOf(i);
				Label labelcount = new Label(i2, style);
				Label lbltitle = new Label(retrivequestion[1], style);
				lbltitle.setWrap(true);
				table.add(labelcount).width(120).align(Align.center).pad(10);
				table.add(lbltitle).width(500);

			}
			this.addActor(table);

		}
		managequestionimage = new Image(new Texture(
				ImgFile.managequestionbutton));
		managequestionimage.setPosition(150, 130);

		managequestionimage.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				if (managequestion.getLength() != -1) {

					PPTXGame.toCreateQuestionScreen(managequestion.getLength(),
							false);
					;// to edit question

				}
				return true;
			}

		});

		clearquestionimage = new Image(new Texture(ImgFile.clearquestionbutton));
		clearquestionimage.setPosition(150, 50);

		clearquestionimage.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				if (managequestion.getLength() != -1) {
					ManageCustomizeQuestion.clearQns(Profile.instance
							.getUsername());
					table.clear();

				}
				return true;
			}

		});
		this.addActor(clearquestionimage);
		this.addActor(managequestionimage);
		final Label lblquestionsets = new Label("Question Sets", style);
		lblquestionsets.setPosition(50, 1100);
		this.addActor(lblquestionsets);
		this.addActor(TopBar.getTopbar());
		
		Image uploadquestion = new Image(new Texture(ImgFile.uploadquestion));
		uploadquestion.setPosition(400, 220);

		uploadquestion.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				uploadtoserver(Profile.instance.getUsername());
				update();
				lblquestionsets.setText("Question Sets(Upload compelete)");
				return true;
			}

		});
		this.addActor(uploadquestion);
		
		

	}
   private boolean update()
   {
	   // TODO Auto-generated method stub
   	try {
           //set the download URL, a url that points to a file on the internet
           //this is the file to be downloaded
   			String Filelist[]=new String[100];
           URL url = new URL("http://"+DbConfig.URL_http+"/mydugeon/list.txt");

           //create the new connection
           HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

           //set up some things on the connection
           urlConnection.setRequestMethod("GET");
           urlConnection.setDoOutput(true);

           //and connect!
           urlConnection.connect();
           BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
           
      
           String line = null;
          
           boolean find=false;
           int i=0;
           while ((line = reader.readLine()) != null)
           {
        	  
           	if(line==Profile.instance.getUsername())//there is list.txt file in the server/mydugeon/ it lists all the members one by one
           	{
           		find=true;
           		break;
           		
           	}
            Filelist[i]=line;
            i++;
           
           }
          i=0;
           if(!find)
           {
        	   File logFile = new File("sdcard/mydugeon/" + "list.txt");
        	   try {
   	            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
   	            
   	            while(Filelist[i]!=null &&Filelist[i]!=""){
   	            buf.append(Filelist[i]+"\n");
   	            i++;
   	            
   	            }
   	         buf.append(Profile.instance.getUsername());
   	            buf.close();
   	        } catch (IOException e) {
   	            e.printStackTrace();
   	        }
        	   uploadtoserver("list");

           }
           

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
	private void uploadtoserver(String name)
	{
		HttpURLConnection connection = null;
		DataOutputStream outputStream = null;
		DataInputStream inputStream = null;
		//lish0030.ddns.net
		String pathToOurFile = "sdcard/mydugeon/"+name+".txt";
		
		String urlServer = "http://"+DbConfig.URL_http+"/handle_upload.php";
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary =  "*****";

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1*1024*1024;

		try
		{
		FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );

		URL url = new URL(urlServer);
		connection = (HttpURLConnection) url.openConnection();

		// Allow Inputs & Outputs
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);

		// Enable POST method
		connection.setRequestMethod("POST");

		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

		outputStream = new DataOutputStream( connection.getOutputStream() );
		outputStream.writeBytes(twoHyphens + boundary + lineEnd);
		outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
		
		outputStream.writeBytes(lineEnd);

		bytesAvailable = fileInputStream.available();
		bufferSize = Math.min(bytesAvailable, maxBufferSize);
		buffer = new byte[bufferSize];

		// Read file
		bytesRead = fileInputStream.read(buffer, 0, bufferSize);

		while (bytesRead > 0)
		{
		outputStream.write(buffer, 0, bufferSize);
		bytesAvailable = fileInputStream.available();
		bufferSize = Math.min(bytesAvailable, maxBufferSize);
		bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		}

		outputStream.writeBytes(lineEnd);
		outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

		// Responses from the server (code and message)
		int serverResponseCode = connection.getResponseCode();
		String serverResponseMessage = connection.getResponseMessage();
		System.out.println(serverResponseCode);
		System.out.println(serverResponseMessage);
		fileInputStream.close();
		outputStream.flush();
		outputStream.close();
		}
		catch (Exception ex)
		{
		//Exception handling
		}
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
