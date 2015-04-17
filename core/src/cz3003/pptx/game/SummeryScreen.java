package cz3003.pptx.game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import cz3003.pptx.game.socialmedia.Profile;

public class SummeryScreen implements Screen {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://10.27.14.48:3306/leaderboard";
	// static final String DB_URL = "jdbc:mysql://10.0.2.2/leaderboard";
	// static final String DB_URL = "jdbc:mysql://localhost/leaderboard";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";

	Stage stage;
	LabelStyle style;
	Table table;

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		stage.setViewport(AndroidView.getview());
		Gdx.input.setInputProcessor(stage);
		style = new LabelStyle(CusFontStyle.getLeaderboardFont(), CusFontStyle
				.getLeaderboardFont().getColor());
		Image backgroundimg = new Image(new Texture(ImgFile.dugeonbackground));
		backgroundimg.setPosition(0, 0);

		stage.addActor(backgroundimg);

		Label lblsummery = new Label("Summary: ", style);
		lblsummery.setPosition(0, 1030);

		lblsummery.setWrap(true);
		lblsummery.setAlignment(Align.topLeft);
		stage.addActor(lblsummery);

		Table table = new Table();
	
		//table.debug();
	    table.top();
		
		Label lblusername = new Label("Username", style);
		Label lbldugeonid = new Label("Dugeonid", style);
		Label lblsocre = new Label("Score", style);
		Label lbltime = new Label("Date", style);
		table.add(lblusername).width(200);
		table.add(lbldugeonid).width(150);
		table.add(lblsocre).width(120);
		table.add(lbltime).width(150);
		Connection conn = null;
		Statement stmt = null;
		boolean status = false;

		try {
			System.out.println("access!");
			Class.forName("com.mysql.jdbc.Driver").newInstance();

		} catch (InstantiationException e) {
			System.out.println("1");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("2");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("3");
			e.printStackTrace();
		}
		try {
			System.out.println("5");
			conn = DriverManager.getConnection(DbConfig.DB_URL, "wangbwhz", "123456");
			System.out.println("4");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM playinghistory";
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// Retrieve user name
				String username = rs.getString("username");
				int dugeonid = rs.getInt("dugeonid");
				int score = rs.getInt("score");
				Date date = rs.getDate("playingdate");
				// Display values
				System.out.print("ID: " + username);
				System.out.print(", Age: " + dugeonid);
				System.out.print(", First: " + score);

				table.row();
				Label labelcount = new Label(username, style);
				Label lbldugeonid2 = new Label(String.valueOf(dugeonid), style);
				Label lblscore2 = new Label(String.valueOf(score), style);
				Label lbldate2 = new Label(String.valueOf(date), style);

				table.add(labelcount).width(200);
				table.add(lbldugeonid2).width(120);
				table.add(lblscore2).width(150);
				
				table.add(lbldate2).width(150);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		ScrollPane scroll = new ScrollPane(table);
		scroll.setPosition(30, 200);
		scroll.setScrollingDisabled(true, false);
		scroll.setSize(680, 800);
		stage.addActor(scroll);
		stage.addActor(TopBar.getTopbar());
		
		
		Image imgtwitter = new Image(new Texture(ImgFile.twitter));
		imgtwitter.setPosition(200, 80);

		


		imgtwitter.addListener(new InputListener(){
			String userinput;
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				Gdx.input.getTextInput(
						new TextInputListener() {
							@Override
							public void input(String texteSaisi) {
								userinput = texteSaisi;
								if(userinput!="")
								{
								 try {
									 
								        Gdx.net.openURI("http://twitter.com/share?text=Assignment comes out set by teacher: "+Profile.instance.getUsername()+" Content: "+userinput);
								    } catch(Exception e) {
								       
								    }
								}
							}

							@Override
							public void canceled() {
								// TODO Auto-generated method stub

							}

						},
						"Please enter the content for the assignment",
						null, null);
			
				    return true;

			

						
				//PPTXGame.toSummeryscreen();
			}
			
		});
		stage.addActor(imgtwitter);
		
		
		Image imgfacebook = new Image(new Texture(ImgFile.facebook));
		imgfacebook.setPosition(400, 80);

		imgfacebook.addListener(new InputListener(){
			String userinput;
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				//downloadFile();
				    return true;

			

						
				//PPTXGame.toSummeryscreen();
			}
			
		});
		stage.addActor(imgfacebook);
		
		
		
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
