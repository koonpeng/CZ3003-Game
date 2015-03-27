package cz3003.pptx.game.battle;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.FloatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

import cz3003.pptx.game.PPTXGame;

public class PlayerActor extends BattleActor {

	private enum State {
		IDLE, START_ATTACK, ATTACKING, END_ATTACK;
	}

	private final Array<Sprite> spriteSheet;
	private final Sprite attackSprite;
	private final Random rand = new Random();
	private final Sound[] attackSounds = new Sound[1];
	private final Animation startAttackAnimation;
	private final Animation attackingAnimation;
	private final Animation endAttackAnimation;

	private State currentState = State.IDLE;
	private float stateTime = 0;
	private float kamehamehaLength = 300;

	public PlayerActor(String name, int hp, int maxHp, int att, int def) {
		super(name, hp, maxHp, att, def);

		attackSprite = new Sprite(PPTXGame.getAssetManager().get("kamehameha.png", Texture.class));
		Texture tex = PPTXGame.getAssetManager().get("fight.png");
		TextureRegion[][] texRegion = TextureRegion.split(tex, tex.getWidth() / 5, tex.getHeight() / 5);
		spriteSheet = new Array<Sprite>(10);
		spriteSheet.add(new Sprite(texRegion[0][1]));
		spriteSheet.add(new Sprite(texRegion[0][2]));
		spriteSheet.add(new Sprite(texRegion[0][3]));
		spriteSheet.add(new Sprite(texRegion[0][4]));
		spriteSheet.add(new Sprite(texRegion[1][0]));
		spriteSheet.add(new Sprite(texRegion[1][1]));
		spriteSheet.add(new Sprite(texRegion[1][2]));
		spriteSheet.add(new Sprite(texRegion[1][3]));
		spriteSheet.add(new Sprite(texRegion[1][4]));
		spriteSheet.add(new Sprite(texRegion[2][0]));
		spriteSheet.add(new Sprite(texRegion[2][1]));

		Array<Sprite> startAttackSprites = new Array<Sprite>(4);
		startAttackSprites.add(spriteSheet.get(1));
		startAttackSprites.add(spriteSheet.get(2));
		startAttackSprites.add(spriteSheet.get(3));
		startAttackSprites.add(spriteSheet.get(4));

		Array<Sprite> attackingSprites = new Array<Sprite>(4);
		attackingSprites.add(spriteSheet.get(5));
		attackingSprites.add(spriteSheet.get(6));
		attackingSprites.add(spriteSheet.get(7));
		attackingSprites.add(spriteSheet.get(8));

		Array<Sprite> endAttackSprites = new Array<Sprite>(2);
		endAttackSprites.add(spriteSheet.get(9));
		endAttackSprites.add(spriteSheet.get(10));

		startAttackAnimation = new Animation(0.1f, startAttackSprites, PlayMode.NORMAL);
		attackingAnimation = new Animation(0.1f, attackingSprites, PlayMode.LOOP_PINGPONG);
		endAttackAnimation = new Animation(0.1f, endAttackSprites, PlayMode.NORMAL);

		attackSounds[0] = PPTXGame.getAssetManager().get("sound/explosion.wav");
	}

	@Override
	public Action getAttackAction() {
		RunnableAction attackSound = Actions.run(new Runnable() {
			@Override
			public void run() {
				switch (rand.nextInt(1)) {
				case 0:
					attackSounds[0].play();
					break;
				}
			}
		});
		RunnableAction attackAct = Actions.run(new Runnable() {
			@Override
			public void run() {
				currentState = State.START_ATTACK;
			}
		});
		RunnableAction endAttackAct = Actions.run(new Runnable() {
			@Override
			public void run() {
				stateTime = 0;
				currentState = State.END_ATTACK;
			}
		});
		attackSprite.setSize(0, attackSprite.getHeight());
		FloatAction kamehameha = new KamehamehaAction();
		kamehameha.setStart(0);
		kamehameha.setEnd(kamehamehaLength);
		kamehameha.setDuration(0.25f);
		FloatAction kamehamehaEnd = new KamehamehaEndAction();
		kamehamehaEnd.setDuration(0.25f);
		attackSprite.setAlpha(1);
		return Actions.sequence(attackAct, Actions.delay(startAttackAnimation.getAnimationDuration()), kamehameha,
				Actions.sequence(Actions.repeat(5, Actions.sequence(attackSound, Actions.delay(0.1f)))), kamehamehaEnd,
				endAttackAct);
	}

	@Override
	public Action getTakeDamageAction() {
		return null;
	}

	@Override
	protected void sizeChanged() {
		attackSprite.setSize(0, getHeight() * 0.6f);
		Vector2 scaling = Scaling.fit.apply(spriteSheet.get(0).getRegionWidth(), spriteSheet.get(0).getRegionHeight(),
				getWidth(), getHeight());
		for (Sprite s : spriteSheet)
			s.setSize(scaling.x, scaling.y);
	}

	@Override
	protected void positionChanged() {
		attackSprite.setPosition(getX() + getParent().getX() + 125, getY() + getParent().getY() + 50);
		for (Sprite s : spriteSheet)
			s.setPosition(getX() + getParent().getX(), getY() + getParent().getY());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Sprite frame;
		switch (currentState) {
		case IDLE:
			frame = spriteSheet.get(0);
			break;
		case START_ATTACK:
			stateTime += Gdx.graphics.getDeltaTime();
			frame = (Sprite) startAttackAnimation.getKeyFrame(stateTime);
			if (startAttackAnimation.isAnimationFinished(stateTime)) {
				currentState = State.ATTACKING;
				stateTime = 0;
			}
			break;
		case ATTACKING:
			stateTime += Gdx.graphics.getDeltaTime();
			frame = (Sprite) attackingAnimation.getKeyFrame(stateTime);
			break;
		case END_ATTACK:
			stateTime += Gdx.graphics.getDeltaTime();
			frame = (Sprite) endAttackAnimation.getKeyFrame(stateTime);
			if (endAttackAnimation.isAnimationFinished(stateTime)) {
				currentState = State.IDLE;
				stateTime = 0;
			}
			break;
		default:
			frame = spriteSheet.get(0);
			break;
		}

		frame.setColor(getColor().tmp().mul(1, 1, 1, parentAlpha));
		frame.draw(batch);
		if (currentState == State.ATTACKING)
			attackSprite.draw(batch);
	}

	private class KamehamehaAction extends FloatAction {
		@Override
		protected void update(float percent) {
			super.update(percent);
			attackSprite.setSize(getEnd() * percent, attackSprite.getHeight());
			attackSprite.setU2(percent);
		}
	}

	private class KamehamehaEndAction extends FloatAction {
		@Override
		protected void update(float percent) {
			super.update(percent);
			attackSprite.setAlpha(1 - percent);
		}
	}

}
