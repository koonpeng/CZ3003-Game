package cz3003.pptx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import cz3003.pptx.game.Constants;
import cz3003.pptx.game.PPTXGame;
import cz3003.pptx.game.SelectionScreen;
import cz3003.pptx.game.socialmedia.SocialMediaSharedVariable;
import cz3003.pptx.game.socialmedia.TwitterResource;

public class MenuScreen extends AbstractGameScreen{
	
	private static final String TAG = MenuScreen.class.getName();
	
	private Stage stage;
	private Skin skinAppOne;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	// menu
	private Image imgBackground;
	private Image imgTeamName;
	private Image imgTitle;
	private Image imgTransparent;
	private Button btnGooglePlay;
	private Button btnTwitter;
	private Button btnPlayNow;
	private Button btnLogout;
	
	// debug
	private final float DEBUG_REBUILD_INTERVAL = 5.0f;
	private boolean debugEnabled = false;
	private float debugRebuildStage;
	
	// ensure login and splash screen is rendered only once
	private boolean isloginScreenRendered = false;
	private boolean isSplashScreenRendered = false;
	
	PPTXGame game;
	public MenuScreen (PPTXGame game) {
		super(game);
		this.game=game;
	}
	
	private void rebuildStage () {
		skinAppOne = new Skin(
		Gdx.files.internal(Constants.SKIN_APPONE_UI),
		new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		// build all layers
		Table layerBackground = buildBackgroundLayer();
		Table layerObjects = buildObjectsLayer();
		Table layerLogos = buildLogosLayer();
		Table layerControls = buildControlsLayer();
		// assemble stage for menu screen
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH,
		Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(layerBackground);
		stack.add(layerObjects);
		stack.add(layerLogos);
		stack.add(layerControls);
	}
	
	private Table buildBackgroundLayer () {
		Table layer = new Table();
		// + Background
		imgBackground = new Image(skinAppOne, "background");
		layer.add(imgBackground);
		return layer;
		}
	
	private Table buildObjectsLayer () {
		Table layer = new Table();
		layer.center();
		// + Game Name
		imgTitle = new Image(skinAppOne, "title");
		layer.add(imgTitle);
		return layer;
	}
	
	private Table buildLogosLayer () {
		Table layer = new Table();
		layer.left().top();
		// + Team Name
		imgTeamName = new Image(skinAppOne, "teamName");
		layer.add(imgTeamName);
		return layer;
	}
	
	private Table buildControlsLayer () {
		Table layer = new Table();
		layer.right().top();
		
		//hide logout button if user is not logged in
		if (SocialMediaSharedVariable.instance.isUserLoggedIn() && 
				!SocialMediaSharedVariable.instance.isDesktopApplication()){
			// + logout Button
			btnLogout = new Button(skinAppOne, "logout");
			layer.add(btnLogout);
			btnLogout.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeEvent event, Actor actor) {
					onLogoutClicked();
				}
			});
		}
		
		layer.row().expandY();
		
		// + Transparent Image
		imgTransparent = new Image(skinAppOne, "transparent");
		layer.add(imgTransparent);
		
		layer.row();
		
		//hide login button if user is logged in
		if (!SocialMediaSharedVariable.instance.isUserLoggedIn() && 
				!SocialMediaSharedVariable.instance.isDesktopApplication()){
		
			// + Google Play Button
			btnGooglePlay = new Button(skinAppOne, "google_play");
			layer.add(btnGooglePlay);
			btnGooglePlay.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeEvent event, Actor actor) {
					onGooglePlayClicked();
				}
			});
			
			layer.row();
			
			// + Twitter Button
			btnTwitter = new Button(skinAppOne, "twitter");
			layer.add(btnTwitter);
			btnTwitter.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeEvent event, Actor actor) {
					onTwitterClicked();
				}
			});
			
			layer.row();
		
		}
		
		//hide play now button if user is not logged in
		if (SocialMediaSharedVariable.instance.isUserLoggedIn() || 
				SocialMediaSharedVariable.instance.isDesktopApplication()){
		// + Play Now Button
			btnPlayNow = new Button(skinAppOne, "play_now");
			layer.add(btnPlayNow);
			btnPlayNow.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeEvent event, Actor actor) {
					onPlayNowClicked();
				}
			});
		}
		
		if (debugEnabled) layer.debug();
		return layer;
	}
	
	private void onGooglePlayClicked(){
		
		Gdx.app.log(TAG, "google play clicked");
		
		SocialMediaSharedVariable.instance.setGoogleBtnClicked(true);
		if (!SocialMediaSharedVariable.instance.isUserLoggedIn()){
			SocialMediaSharedVariable.instance.getSocialMediaActivityInterface().startGooglePlusLoginActivity();
		}
	}
		
	private void onTwitterClicked () {
		
		SocialMediaSharedVariable.instance.setTwitterBtnClicked(true);
		if (!SocialMediaSharedVariable.instance.isUserLoggedIn()){
			SocialMediaSharedVariable.instance.getTwitterInterface().loginToSocialMedia();
		}
	}
	
	private void onPlayNowClicked(){
		if (SocialMediaSharedVariable.instance.isUserLoggedIn() ||
			SocialMediaSharedVariable.instance.isDesktopApplication()){
			game.selectionscreen = new SelectionScreen(game);
			game.setScreen(game.selectionscreen);
			//game.getTwitterInterface().publishMaterialToSocialMedia(null);
		}else{
			Gdx.app.log(TAG, "unable to go to game screen, user is not logged in");
		}
	}
	
	private void onLogoutClicked(){
		
		SocialMediaSharedVariable.instance.setlogOutBtnClicked(true);
		
		SocialMediaSharedVariable.instance.getSocialMediaActivityInterface().startGooglePlusRevokeAccessActivity();

		TwitterResource.instance.clear();
		SocialMediaSharedVariable.instance.clear();
		
		isloginScreenRendered = false;
		isSplashScreenRendered = false;
	}
	
	@Override
	public void render (float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (debugEnabled) {
			debugRebuildStage -= deltaTime;
			if (debugRebuildStage <= 0) {
			debugRebuildStage = DEBUG_REBUILD_INTERVAL;
			rebuildStage();
			}
		}
		
		if (SocialMediaSharedVariable.instance.isUserLoggedIn() &&
			!isloginScreenRendered){
			rebuildStage();
			isSplashScreenRendered = false;
			isloginScreenRendered = true;
		}else if (!SocialMediaSharedVariable.instance.isUserLoggedIn() &&
				!isSplashScreenRendered){
			rebuildStage();
			isloginScreenRendered = false;
			isSplashScreenRendered = true;
			
		}

		stage.act(deltaTime);
		stage.draw();
		stage.setDebugAll(debugEnabled);
	}
	@Override public void resize (int width, int height) {
		 stage.getViewport().update(width, height, false);
	}
	@Override public void show () { 
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		viewport = new FitViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT,camera);
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}
	@Override public void hide () { 
		stage.dispose();
		skinAppOne.dispose();
	}
	@Override public void pause () { }
}
