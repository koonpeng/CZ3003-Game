package cz3003.pptx.game.battle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.FloatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

import cz3003.pptx.game.PPTXGame;

public class HPBar extends Widget {

	private final Texture redTex;
	private final Texture emptyTex;
	private final Sprite redSprite;
	private final Sprite emptySprite;

	private float curPercent = 1;

	public HPBar() {
		super();
		redTex = PPTXGame.getAssetManager().get("battle/RedBar.png");
		redTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		emptyTex = PPTXGame.getAssetManager().get("battle/EmptyBar.png");
		emptyTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		redSprite = new Sprite(redTex);
		emptySprite = new Sprite(emptyTex);
		redSprite.setSize(redTex.getWidth(), redTex.getHeight());
		emptySprite.setSize(emptyTex.getWidth(), emptyTex.getHeight());

		setSize(getPrefWidth(), getPrefHeight());
	}

	public void setPercent(float newPercent) {
		ChangeHpBarAction changeHpBarAct = new ChangeHpBarAction();
		changeHpBarAct.setStart(curPercent);
		changeHpBarAct.setEnd(newPercent);
		changeHpBarAct.setDuration(0.9f);
		clearActions();
		addAction(changeHpBarAct);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		emptySprite.setPosition(getX(), getY());
		redSprite.setPosition(getX(), getY());
		emptySprite.setAlpha(parentAlpha);
		redSprite.setAlpha(parentAlpha);
		emptySprite.draw(batch);
		redSprite.draw(batch);
		batch.flush();
	}

	@Override
	protected void sizeChanged() {
		super.sizeChanged();
		redSprite.setSize(getWidth(), getHeight());
		emptySprite.setSize(getWidth(), getHeight());
	}

	private class ChangeHpBarAction extends FloatAction {
		@Override
		protected void update(float percent) {
			super.update(percent);
			curPercent = getValue();
			redSprite.setSize(emptySprite.getWidth() * curPercent, emptySprite.getHeight());
			redSprite.setU2(curPercent);
		}
	}

}
