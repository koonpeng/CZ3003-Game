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
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import cz3003.pptx.game.Constants;
import cz3003.pptx.game.PPTXGame;
import cz3003.pptx.game.SelectionScreen;
import cz3003.pptx.game.socialmedia.Profile;
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
	
	// ensure login and welcome screen is rendered only once
	private boolean isWelcomeScreenRendered = false;
	private boolean isLoginScreenRendered = false;
	
	PPTXGame game;
	public MenuScreen (PPTXGame game) {
		super(game);
		this.game=game;
	}
	
	private void rebuildStage () {
		skinAppOne = new Skin(Gdx.files.internal(Constants.SKIN_LOGINSCREEN_UI), new TextureAtlas(Constants.OBJ_LOGINSCREEN_UI));
		// build all layers
		Table layerBackground = buildBackgroundLayer();
		Table layerObjects = buildObjectsLayer();
		Table layerLoginControls = buildLoginControlsLayer();
		Table layerAndroidWelcomeControls = buildAndroidWelcomeControlsLayer();
		Table layerDesktopWelcomeControls = buildDesktopWelcomeControlsLayer();
		
		// assemble stage for menu screen
		stage.clear();
		Stack stack = new Stack();
		stack.setName("stack");
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH,
		Constants.VIEWPORT_GUI_HEIGHT);
		stack.addActor(layerBackground);
		stack.addActor(layerObjects);
		
		Gdx.app.log(TAG, "Doing check in rebuildStage()");
		Gdx.app.log(TAG, "Logged in: " + SocialMediaSharedVariable.instance.isUserLoggedIn());
		Gdx.app.log(TAG, "Desktop: " + SocialMediaSharedVariable.instance.isDesktopApplication());
		
		//if platform is android
		if (!SocialMediaSharedVariable.instance.isDesktopApplication()){
			stack.add(layerLoginControls);
			stack.add(layerAndroidWelcomeControls);
			hideWelcomeControls(true);
		//if platform is desktop
		}else{
			stack.addActor(layerDesktopWelcomeControls);
		}
		
//		//if user has yet to log into an android device
//		if (!SocialMediaSharedVariable.instance.isUserLoggedIn() && 
//				!SocialMediaSharedVariable.instance.isDesktopApplication()){
//			
//			stack.add(layerLoginControls);
//		
//		//if user has logged into an android device
//		}else if (SocialMediaSharedVariable.instance.isUserLoggedIn() && 
//			!SocialMediaSharedVariable.instance.isDesktopApplication()){
//			
//			stack.add(layerAndroidWelcomeControls);
//		
//		//if user is using an desktop
//		}else if (SocialMediaSharedVariable.instance.isUserLoggedIn() || 
//			SocialMediaSharedVariable.instance.isDesktopApplication()){
//			
//			stack.add(layerDesktopWelcomeControls);
//		}
		
	}
	
	private void displayAllActorName(){
		for (Actor actor : stage.getActors()){
			Gdx.app.log(TAG, actor.getName());
			Stack stack = (Stack) actor;
			stack.findActor("imgBackground").setVisible(false);
		}
	}
	
	private void hideLoginControls(boolean bool){
		//do nothing if platform is desktop
		if (SocialMediaSharedVariable.instance.isDesktopApplication()){
			return;
		}

		for (Actor actor : stage.getActors()){
			if (actor.getName() == "stack"){
				Stack stack = (Stack) actor;
				if (bool == true){
					stack.findActor("btnGooglePlay").setVisible(false);
					stack.findActor("btnTwitter").setVisible(false);
				}else{
					stack.findActor("btnGooglePlay").setVisible(true);
					stack.findActor("btnTwitter").setVisible(true);
				}
			}
		}//end of for
	}
	
	private void hideWelcomeControls(boolean bool){
		//do nothing if platform is desktop
		if (SocialMediaSharedVariable.instance.isDesktopApplication()){
			return;
		}
		
		for (Actor actor : stage.getActors()){
			if (actor.getName() == "stack"){
				Stack stack = (Stack) actor;
				if (bool == true){
					stack.findActor("btnPlayNow").setVisible(false);
					stack.findActor("btnLogout").setVisible(false);
				}else{
					stack.findActor("btnPlayNow").setVisible(true);
					stack.findActor("btnLogout").setVisible(true);
				}
			}
		}//end of for
	}
	
	private Table buildBackgroundLayer () {
		
		Gdx.app.log(TAG, "Building background layer");
		
		Table layer = new Table();
		// + Background
		imgBackground = new Image(skinAppOne, "login_ui_background");
		imgBackground.setName("imgBackground");
		imgBackground.setScaling(Scaling.fit);
		layer.add(imgBackground).expand().fill();
		return layer;
		}
	
	private Table buildObjectsLayer () {
		
		Gdx.app.log(TAG, "Building object layer");
		
		Table layer = new Table();
		layer.center();
		// + Game Name
		imgTitle = new Image(skinAppOne, "login_ui_title");
		imgTitle.setName("imgTitle");
		layer.add(imgTitle);
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
			btnLogout.setName("btnLogout");
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
		imgTransparent.setName("imgTransparent");
		layer.add(imgTransparent);
		
		layer.row();
		
		//hide login button if user is logged in
		if (!SocialMediaSharedVariable.instance.isUserLoggedIn() && 
				!SocialMediaSharedVariable.instance.isDesktopApplication()){
		
			// + Google Play Button
			btnGooglePlay = new Button(skinAppOne, "google_play");
			btnGooglePlay.setName("btnGooglePlay");
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
			btnTwitter.setName("btnTwitter");
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
			btnPlayNow.setName("btnPlayNow");
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
	
	private Table buildLoginControlsLayer(){
		//Condition: platform is desktop and user is not logged in
		
		Gdx.app.log(TAG, "Building login control layer");
		
		Table layer = new Table();
		layer.right().top();
		
		layer.row().expandY();
		
		//Transparent Image is used as a spacing buffer
		imgTransparent = new Image(skinAppOne, "transparent");
		imgTransparent.setName("imgTransparent");
		layer.add(imgTransparent);
		
		layer.row();
		
		//Google Play button is displayed at the bottom right
		btnGooglePlay = new Button(skinAppOne, "google_play");
		btnGooglePlay.setName("btnGooglePlay");
		layer.add(btnGooglePlay);
		btnGooglePlay.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onGooglePlayClicked();
			}
		});
		
		layer.row();
		
		//Twitter button is displayed at the bottom right after
		//Google Plus button
		
		btnTwitter = new Button(skinAppOne, "twitter");
		btnTwitter.setName("btnTwitter");
		layer.add(btnTwitter);
		btnTwitter.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onTwitterClicked();
			}
		});
		
		return layer;

	}
	
	private Table buildAndroidWelcomeControlsLayer(){
		//Condition: platform is android and user is logged in 
		
		Gdx.app.log(TAG, "Building android welcome control layer");
		
		Table layer = new Table();
		layer.right().top();
		
		//Logout button is displayed on the top right corner
		btnLogout = new Button(skinAppOne, "logout");
		btnLogout.setName("btnLogout");
		layer.add(btnLogout);
		btnLogout.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onLogoutClicked();
			}
		});
		
		layer.row().expandY();
		
		//Transparent Image is used as a spacing buffer
		imgTransparent = new Image(skinAppOne, "transparent");
		imgTransparent.setName("imgTransparent");
		layer.add(imgTransparent);
		
		layer.row();
		
		//PlayNow button is displayed on the bottom right corner
		btnPlayNow = new Button(skinAppOne, "play_now");
		btnPlayNow.setName("btnPlayNow");
		btnPlayNow.setName("btnPlayNow");
		layer.add(btnPlayNow);
		btnPlayNow.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onPlayNowClicked();
			}
		});
		
		return layer;
	}
	
	private Table buildDesktopWelcomeControlsLayer(){
		//Condition: platform is desktop, does not matter 
		//if user is logged in or not		
		
		Gdx.app.log(TAG, "Building desktop welcome control layer");
		
		Table layer = new Table();
		layer.right().top();
		
		layer.row().expandY();
		
		//Transparent Image is used as a spacing buffer
		imgTransparent = new Image(skinAppOne, "transparent");
		layer.add(imgTransparent);
		
		layer.row();
		
		//PlayNow button is displayed on the bottom right corner
		btnPlayNow = new Button(skinAppOne, "play_now");
		btnPlayNow.setName("btnPlayNow");
		layer.add(btnPlayNow);
		btnPlayNow.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				onPlayNowClicked();
			}
		});
		
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
			PPTXGame.toSelectionScreen();
			isWelcomeScreenRendered = false;
			isLoginScreenRendered = false;
			//game.getTwitterInterface().publishMaterialToSocialMedia(null);
		}else{
			Gdx.app.log(TAG, "unable to go to game screen, user is not logged in");
		}
	}
	
	private void onLogoutClicked(){
		
		SocialMediaSharedVariable.instance.setlogOutBtnClicked(true);
		//check if user is logged in using google+ first
		SocialMediaSharedVariable.instance.getSocialMediaActivityInterface().startGooglePlusRevokeAccessActivity();
		TwitterResource.instance.clear();
		SocialMediaSharedVariable.instance.clear();
		isWelcomeScreenRendered = false;
		isLoginScreenRendered = false;
	}
	
	@Override
	public void render (float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Splash screen is also called welcome screen.
		if (SocialMediaSharedVariable.instance.isUserLoggedIn() &&
			!isWelcomeScreenRendered){
			
			hideWelcomeControls(false);
			hideLoginControls(true);
			
			isLoginScreenRendered = false;
			isWelcomeScreenRendered = true;
			
		}else if (!SocialMediaSharedVariable.instance.isUserLoggedIn() &&
				!isLoginScreenRendered){
			
			hideWelcomeControls(true);
			hideLoginControls(false);
			
			isWelcomeScreenRendered = false;
			isLoginScreenRendered = true;
			
		}

		stage.act(deltaTime);
		stage.draw();
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
		//stage.dispose();
		//skinAppOne.dispose();
	}
	@Override public void pause () { }
}
