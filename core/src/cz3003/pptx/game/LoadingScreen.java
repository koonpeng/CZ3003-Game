package cz3003.pptx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LoadingScreen implements Screen {
	PPTXGame game;
	public float statetime;
	SpriteBatch batch;
	Texture loadTexture;
	Stage stage;
	Image image;
	private float loadTime = 0;

	public LoadingScreen(PPTXGame game) {
		this.game = game;

	}

	@Override
	public void show() {
		PPTXGame.getAssetManager().clear();
		loadAssets(PPTXGame.getAssetManager());
		loadTexture = new Texture(ImgFile.loadingbackground);
		Image image = new Image(loadTexture);

		stage = new Stage();
		stage.addActor(image);
		stage.setViewport(AndroidView.getview());
	}

	private static void loadAssets(AssetManager assetManager) {
		assetManager.load("button.png", Texture.class);
		assetManager.load("battle/player.png", Texture.class);
		assetManager.load("battle/kamehameha.png", Texture.class);
		assetManager.load("monster/dragon.png", Texture.class);
		assetManager.load("battle/RedBar.png", Texture.class);
		assetManager.load("battle/EmptyBar.png", Texture.class);
		assetManager.load("battle/fire.png", Texture.class);
		assetManager.load("background/environment_forest_alt1.png", Texture.class);
		assetManager.load("background/crumpled-paper.jpg", Texture.class);
		assetManager.load("music/(10) Force Your Way.mp3", Music.class);
		assetManager.load("music/(05) The Winner.mp3", Music.class);
		assetManager.load("sound/explosion.wav", Sound.class);

		FreeTypeFontLoaderParameter calibri36 = new FreeTypeFontLoaderParameter();
		calibri36.fontFileName = "font/calibri.ttf";
		calibri36.fontParameters.size = 36;
		calibri36.fontParameters.minFilter = TextureFilter.Linear;
		calibri36.fontParameters.magFilter = TextureFilter.Linear;
		assetManager.load("calibri36.ttf", BitmapFont.class, calibri36);

		FreeTypeFontLoaderParameter calibri24 = new FreeTypeFontLoaderParameter();
		calibri24.fontFileName = "font/calibri.ttf";
		calibri24.fontParameters.size = 24;
		calibri24.fontParameters.minFilter = TextureFilter.Linear;
		calibri24.fontParameters.magFilter = TextureFilter.Linear;
		assetManager.load("calibri24.ttf", BitmapFont.class, calibri24);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		statetime += Gdx.graphics.getDeltaTime();
		if (PPTXGame.getAssetManager().update()) {
			System.out.println("Loaded in " + loadTime + " seconds");
//			PPTXGame.toBattleScreen(0);
			game.setScreen(game.menuscreen);
		}
		loadTime += delta;

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
		stage.dispose();
	}
}
