<<<<<<< HEAD
package cz3003.pptx.game.battle;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Scaling;

import cz3003.pptx.game.PPTXGame;

public class EnemyActor extends BattleActor {

	private final Sprite sprite;
	private final EffectActor fireBreath;
	private final Sound attackSound;

	public EnemyActor(String name, int hp, int maxHp, int att, int def) {
		super(name, hp, maxHp, att, def);
		Texture tex = PPTXGame.getAssetManager().get("monsters/dragon.png");
		sprite = new Sprite(tex);

		tex = PPTXGame.getAssetManager().get("battle/fire.png");
		fireBreath = new EffectActor(new TextureRegion(tex, 192, 192, 192, 192));

		attackSound = PPTXGame.getAssetManager().get("sound/explosion.wav");

		setSize(sprite.getWidth(), sprite.getHeight());
	}

	@Override
	public Action getAttackAction(BattleActor target) {
		Action addActor = Actions.run(new Runnable() {
			@Override
			public void run() {
				fireBreath.reset();
				addActor(fireBreath);
			}
		});
		fireBreath.setPosition(40, 70);
		fireBreath.setColor(1, 1, 1, 1);
		Vector2 targetCoord = target.localToStageCoordinates(new Vector2(target.getWidth() / 2, target.getHeight() / 2));
		Vector2 moveCoord = stageToLocalCoordinates(new Vector2(targetCoord.x - fireBreath.getX() - 40, targetCoord.y
				- fireBreath.getY()));
		Action attackAct = Actions.moveTo(moveCoord.x, moveCoord.y, 0.7f);
		attackAct.setTarget(fireBreath);
		Action playSound = Actions.run(new Runnable() {
			@Override
			public void run() {
				attackSound.play();
			}
		});
		
		Action fadeOut = Actions.fadeOut(0.5f);
		fadeOut.setTarget(fireBreath);
		return Actions.sequence(addActor, attackAct,playSound , fadeOut, Actions.removeActor(fireBreath));
	}

	@Override
	public Action getTakeDamageAction() {
		Action showAct = Actions.visible(true);
		Action hideAct = Actions.visible(false);
		showAct.setTarget(this);
		hideAct.setTarget(this);
		Action blinkAction = Actions.repeat(3, Actions.sequence(hideAct, Actions.delay(0.2f), showAct, Actions.delay(0.2f)));
		return blinkAction;
	}

	@Override
	protected void sizeChanged() {
		super.sizeChanged();
		Vector2 scaling = Scaling.fill.apply(sprite.getRegionWidth(), sprite.getRegionHeight(), getWidth(), getHeight());
		sprite.setSize(scaling.x, scaling.y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		applyTransform(batch, computeTransform());
		sprite.setColor(getColor().tmp().mul(1, 1, 1, parentAlpha));
		sprite.draw(batch);
		drawChildren(batch, parentAlpha);
		resetTransform(batch);
		batch.flush();
	}

}
=======
package cz3003.pptx.game.battle;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Scaling;

import cz3003.pptx.game.PPTXGame;

public class EnemyActor extends BattleActor {

	private final Sprite sprite;
	private final EffectActor fireBreath;
	private final Sound attackSound;

	public EnemyActor(String name, int hp, int maxHp, int att, int def) {
		super(name, hp, maxHp, att, def);
		Texture tex = PPTXGame.getAssetManager().get("monster/dragon.png");
		sprite = new Sprite(tex);

		tex = PPTXGame.getAssetManager().get("battle/fire.png");
		fireBreath = new EffectActor(new TextureRegion(tex, 192, 192, 192, 192));

		attackSound = PPTXGame.getAssetManager().get("sound/explosion.wav");

		setSize(sprite.getWidth(), sprite.getHeight());
	}

	@Override
	public Action getAttackAction(BattleActor target) {
		Action addActor = Actions.run(new Runnable() {
			@Override
			public void run() {
				fireBreath.reset();
				addActor(fireBreath);
			}
		});
		fireBreath.setPosition(40, 70);
		fireBreath.setColor(1, 1, 1, 1);
		Vector2 targetCoord = target.localToStageCoordinates(new Vector2(target.getWidth() / 2, target.getHeight() / 2));
		Vector2 moveCoord = stageToLocalCoordinates(new Vector2(targetCoord.x - fireBreath.getX() - 40, targetCoord.y
				- fireBreath.getY()));
		Action attackAct = Actions.moveTo(moveCoord.x, moveCoord.y, 0.7f);
		attackAct.setTarget(fireBreath);
		Action playSound = Actions.run(new Runnable() {
			@Override
			public void run() {
				attackSound.play();
			}
		});
		Action fadeOut = Actions.fadeOut(0.5f);
		fadeOut.setTarget(fireBreath);
		return Actions.sequence(addActor, attackAct, playSound, fadeOut, Actions.removeActor(fireBreath));
	}

	@Override
	public Action getTakeDamageAction() {
		Action showAct = Actions.visible(true);
		Action hideAct = Actions.visible(false);
		showAct.setTarget(this);
		hideAct.setTarget(this);
		Action blinkAction = Actions.repeat(3, Actions.sequence(hideAct, Actions.delay(0.2f), showAct, Actions.delay(0.2f)));
		return blinkAction;
	}

	@Override
	protected void sizeChanged() {
		super.sizeChanged();
		Vector2 scaling = Scaling.fill.apply(sprite.getRegionWidth(), sprite.getRegionHeight(), getWidth(), getHeight());
		sprite.setSize(scaling.x, scaling.y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		applyTransform(batch, computeTransform());
		sprite.setColor(getColor().tmp().mul(1, 1, 1, parentAlpha));
		sprite.draw(batch);
		drawChildren(batch, parentAlpha);
		resetTransform(batch);
		batch.flush();
	}

}
>>>>>>> da0b266c97f477e35bd271440e35dfb60aa4dccd
