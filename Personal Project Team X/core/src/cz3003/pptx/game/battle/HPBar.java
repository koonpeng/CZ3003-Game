package cz3003.pptx.game.battle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import cz3003.pptx.game.PPTXGame;

public class HPBar extends Actor {

	private final Texture redTex;
	private final Texture emptyTex;
	private final Sprite redSprite;
	private final Sprite emptySprite;

	private float percent = 1;

	public HPBar() {
		redTex = PPTXGame.getAssetManager().get("RedBar.png");
		redTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		emptyTex = PPTXGame.getAssetManager().get("EmptyBar.png");
		emptyTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		redSprite = new Sprite(redTex);
		emptySprite = new Sprite(emptyTex);

		setWidth(emptySprite.getWidth());
		setHeight(emptySprite.getHeight());
	}

	public void setPercent(float percent) {
		this.percent = percent;
		redSprite.setSize(emptySprite.getWidth() * percent, redSprite.getHeight());
		redSprite.setRegionWidth((int) (emptySprite.getRegionWidth() * percent));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		emptySprite.draw(batch);
		redSprite.draw(batch);
	}

	@Override
	protected void sizeChanged() {
		redSprite.setSize(getWidth() * percent, getHeight());
		emptySprite.setSize(getWidth(), getHeight());
	}

	@Override
	protected void positionChanged() {
		Group parent = getParent();
		redSprite.setX(getX() + parent.getX());
		redSprite.setY(getY() + parent.getY());
		emptySprite.setX(getX() + parent.getX());
		emptySprite.setY(getY() + parent.getY());
	}

}
