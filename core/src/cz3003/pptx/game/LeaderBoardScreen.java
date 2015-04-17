package cz3003.pptx.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import cz3003.pptx.game.socialmedia.Profile;

public class LeaderBoardScreen implements Screen {
	PPTXGame game;
	Stage stage;
	LabelStyle style;

	public LeaderBoardScreen(PPTXGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		stage.setViewport(AndroidView.getview());
		Gdx.input.setInputProcessor(stage);

		Image backgroundimg = new Image(new Texture(ImgFile.leaderboardback));
		backgroundimg.setPosition(0, 0);

		Image backbuttonimg = new Image(new Texture(
				ImgFile.leaderboardbackbutton));
		backbuttonimg.setPosition(40, 120);
		backbuttonimg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				game.setScreen(game.resultscreen);
				return true;
			}

		});
		int[] top3 = new int[5];
		String[] top3name = new String[5];
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
			conn = DriverManager.getConnection(DbConfig.DB_URL, DbConfig.USER,
					DbConfig.PASS);
			System.out.println("4");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM playinghistory ORDER BY score DESC";
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			top3[0] = 0;
			top3[1] = 0;
			top3[2] = 0;
			int i = 0;
			while (rs.next()) {
				// Retrieve user name
				String username = rs.getString("username");
				int dugeonid = rs.getInt("dugeonid");
				int score = rs.getInt("score");
				// Display values

				if (dugeonid == SelectionStage.getCurrentDungeon()) {
					top3[i] = score;
					top3name[i] = username;
					i++;
					if (i == 3) {
						break;
					}
				}

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

		style = new LabelStyle(CusFontStyle.getLeaderboardFont(), CusFontStyle
				.getLeaderboardFont().getColor());
		int[] highScoreArray = Profile.instance.getStageHighScoreArray();
		Label lblfirstname = new Label(top3name[0], style);
		lblfirstname.setPosition(348, 734);
		lblfirstname.setSize(180, 43);
		lblfirstname.setAlignment(Align.left);
		Label lblsecondname = new Label(top3name[1], style);
		lblsecondname.setPosition(348, 526);
		lblsecondname.setSize(180, 43);
		lblsecondname.setAlignment(Align.left);
		Label lblthirdname = new Label(top3name[2], style);
		lblthirdname.setPosition(348, 341);
		lblthirdname.setSize(180, 43);
		lblthirdname.setAlignment(Align.left);
		Label lblfirstpoint = new Label(String.valueOf(top3[0]), style);
		lblfirstpoint.setPosition(560, 734);
		lblfirstpoint.setAlignment(Align.left);
		lblfirstpoint.setSize(90, 43);
		Label lblsecondpoint = new Label(String.valueOf(top3[1]), style);
		lblsecondpoint.setPosition(560, 526);
		lblsecondpoint.setSize(90, 43);
		lblsecondpoint.setAlignment(Align.left);
		Label lblthirdpoint = new Label(String.valueOf(top3[2]), style);
		lblthirdpoint.setPosition(560, 341);
		lblthirdpoint.setSize(90, 43);
		lblthirdpoint.setAlignment(Align.left);

		stage.addActor(backgroundimg);
		stage.addActor(lblfirstname);
		stage.addActor(lblsecondname);
		stage.addActor(lblthirdname);
		stage.addActor(lblfirstpoint);
		stage.addActor(lblsecondpoint);
		stage.addActor(lblthirdpoint);

		stage.addActor(backbuttonimg);

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
