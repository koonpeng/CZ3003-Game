package cz3003.pptx.game.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EffectActor extends Actor {

	private final Animation animation;
	private float stateTime = 0;

	public EffectActor(Animation animation) {
		super();
		this.animation = animation;
		TextureRegion firstFrame = animation.getKeyFrame(0);
		setSize(firstFrame.getRegionWidth(), firstFrame.getRegionHeight());
	}

	public EffectActor(TextureRegion effect) {
		this(new Animation(1, effect));
	}

	public void reset() {
		stateTime = 0;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		TextureRegion frame = animation.getKeyFrame(stateTime);
		batch.setColor(getColor());
		batch.draw(frame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(),
				getRotation());

		stateTime += Gdx.graphics.getDeltaTime();
		batch.flush();
	}
}
