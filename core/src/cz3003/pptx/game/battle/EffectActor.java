package cz3003.pptx.game.battle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EffectActor extends Actor {

	private final Sprite sprite;

	public EffectActor(Sprite sprite) {
		super();
		this.sprite = sprite;
		setSize(sprite.getWidth(), sprite.getHeight());
	}

	@Override
	protected void positionChanged() {
		super.positionChanged();
		sprite.setPosition(getX(), getY());
	}

	@Override
	protected void sizeChanged() {
		super.sizeChanged();
		sprite.setSize(getWidth(), getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setColor(getColor());
		sprite.draw(batch);
		batch.flush();
	}

}
