<<<<<<< HEAD
package cz3003.pptx.game.battle;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private final Sprite[] kamehamehaSprite;
	private final Random rand = new Random();
	private final Sound[] attackSounds = new Sound[1];
	private final Animation startAttackAnimation;
	private final Animation attackingAnimation;
	private final Animation endAttackAnimation;

	private State currentState = State.IDLE;
	private float stateTime = 0;
	private float kamehamehaLength = 400;

	public PlayerActor(String name, int hp, int maxHp, int att, int def) {
		super(name, hp, maxHp, att, def);

		Texture tex = PPTXGame.getAssetManager().get("battle/player.png");
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
		startAttackSprites.add(spriteSheet.get(3));
		startAttackSprites.add(spriteSheet.get(4));
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

		Texture kamehamehaTex = PPTXGame.getAssetManager().get("battle/kamehameha.png");
		kamehamehaSprite = new Sprite[3];
		kamehamehaSprite[0] = new Sprite(kamehamehaTex, 0, 0, 65, kamehamehaTex.getHeight());
		kamehamehaSprite[1] = new Sprite(kamehamehaTex, 65, 0, 80, kamehamehaTex.getHeight());
		kamehamehaSprite[2] = new Sprite(kamehamehaTex, 145, 0, 65, kamehamehaTex.getHeight());
		kamehamehaSprite[0].setPosition(130, 60);
		kamehamehaSprite[1].setPosition(kamehamehaSprite[0].getX() + kamehamehaSprite[0].getWidth(), kamehamehaSprite[0].getY());
		kamehamehaSprite[2].setPosition(kamehamehaSprite[1].getX() + kamehamehaSprite[1].getWidth(), kamehamehaSprite[0].getY());
		for (Sprite s : kamehamehaSprite)
			s.setSize(0, s.getHeight() * 1.2f);

		setSize(spriteSheet.get(0).getWidth(), spriteSheet.get(0).getHeight());

		attackSounds[0] = PPTXGame.getAssetManager().get("sound/explosion.wav");
	}

	@Override
	public Action getAttackAction(BattleActor target) {
		kamehamehaLength = target.getX() - getX();
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
		FloatAction kamehameha = new KamehamehaAction();
		kamehameha.setStart(0);
		kamehameha.setEnd(kamehamehaLength);
		kamehameha.setDuration(0.25f);
		FloatAction kamehamehaEnd = new KamehamehaEndAction();
		kamehamehaEnd.setDuration(0.25f);
		for (Sprite s : kamehamehaSprite) {
			s.setAlpha(1);
			s.setSize(0, s.getHeight());
		}
		return Actions.sequence(attackAct, Actions.delay(startAttackAnimation.getAnimationDuration()), kamehameha,
				Actions.sequence(Actions.repeat(5, Actions.sequence(attackSound, Actions.delay(0.15f)))), kamehamehaEnd,
				endAttackAct);
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
		Vector2 scaling = Scaling.fit.apply(spriteSheet.get(0).getRegionWidth(), spriteSheet.get(0).getRegionHeight(),
				getWidth(), getHeight());
		for (Sprite s : spriteSheet)
			s.setSize(scaling.x, scaling.y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		applyTransform(batch, computeTransform());
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
			for (Sprite s : kamehamehaSprite)
				s.draw(batch);
		resetTransform(batch);
		batch.flush();
	}

	private class KamehamehaAction extends FloatAction {
		@Override
		protected void update(float percent) {
			super.update(percent);
			float curLength = percent * kamehamehaLength;
			float length0 = curLength;
			float length1 = 0;
			float length2 = 0;
			if (length0 > 65) {
				length0 = 65;
				length1 = curLength - length0;
				if (kamehamehaLength - curLength < 65) {
					length1 = kamehamehaLength - 65 - 65;
					length2 = curLength - length0 - length1;
				}
			}
			kamehamehaSprite[0].setSize(length0 * 1.2f, kamehamehaSprite[0].getHeight());
			kamehamehaSprite[0].setRegionWidth((int) length0);
			kamehamehaSprite[1].setSize(length1, kamehamehaSprite[1].getHeight());
			kamehamehaSprite[2].setSize(length2 * 1.2f, kamehamehaSprite[2].getHeight());
			kamehamehaSprite[2].setRegionWidth((int) length2);
			kamehamehaSprite[1].setPosition(kamehamehaSprite[0].getX() + kamehamehaSprite[0].getWidth(),
					kamehamehaSprite[0].getY());
			kamehamehaSprite[2].setPosition(kamehamehaSprite[1].getX() + kamehamehaSprite[1].getWidth(),
					kamehamehaSprite[0].getY());
		}
	}

	private class KamehamehaEndAction extends FloatAction {
		@Override
		protected void update(float percent) {
			super.update(percent);
			for (Sprite s : kamehamehaSprite) {
				s.setAlpha(1 - percent);
			}
		}
	}

}
=======
package cz3003.pptx.game.battle;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private final Sprite[] kamehamehaSprite;
	private final Random rand = new Random();
	private final Sound[] attackSounds = new Sound[1];
	private final Animation startAttackAnimation;
	private final Animation attackingAnimation;
	private final Animation endAttackAnimation;

	private State currentState = State.IDLE;
	private float stateTime = 0;
	private float kamehamehaLength = 400;

	public PlayerActor(String name, int hp, int maxHp, int att, int def) {
		super(name, hp, maxHp, att, def);

		Texture tex = PPTXGame.getAssetManager().get("battle/player.png");
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
		startAttackSprites.add(spriteSheet.get(3));
		startAttackSprites.add(spriteSheet.get(4));
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

		Texture kamehamehaTex = PPTXGame.getAssetManager().get("battle/kamehameha.png");
		kamehamehaSprite = new Sprite[3];
		kamehamehaSprite[0] = new Sprite(kamehamehaTex, 0, 0, 65, kamehamehaTex.getHeight());
		kamehamehaSprite[1] = new Sprite(kamehamehaTex, 65, 0, 80, kamehamehaTex.getHeight());
		kamehamehaSprite[2] = new Sprite(kamehamehaTex, 145, 0, 65, kamehamehaTex.getHeight());
		kamehamehaSprite[0].setPosition(130, 60);
		kamehamehaSprite[1].setPosition(kamehamehaSprite[0].getX() + kamehamehaSprite[0].getWidth(), kamehamehaSprite[0].getY());
		kamehamehaSprite[2].setPosition(kamehamehaSprite[1].getX() + kamehamehaSprite[1].getWidth(), kamehamehaSprite[0].getY());
		for (Sprite s : kamehamehaSprite)
			s.setSize(0, s.getHeight() * 1.2f);

		setSize(spriteSheet.get(0).getWidth(), spriteSheet.get(0).getHeight());

		attackSounds[0] = PPTXGame.getAssetManager().get("sound/explosion.wav");
	}

	@Override
	public Action getAttackAction(BattleActor target) {
		kamehamehaLength = target.getX() - getX();
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
		FloatAction kamehameha = new KamehamehaAction();
		kamehameha.setStart(0);
		kamehameha.setEnd(kamehamehaLength);
		kamehameha.setDuration(0.25f);
		FloatAction kamehamehaEnd = new KamehamehaEndAction();
		kamehamehaEnd.setDuration(0.25f);
		for (Sprite s : kamehamehaSprite) {
			s.setAlpha(1);
			s.setSize(0, s.getHeight());
		}
		return Actions.sequence(attackAct, Actions.delay(startAttackAnimation.getAnimationDuration()), kamehameha,
				Actions.sequence(Actions.repeat(5, Actions.sequence(attackSound, Actions.delay(0.15f)))), kamehamehaEnd,
				endAttackAct);
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
		Vector2 scaling = Scaling.fit.apply(spriteSheet.get(0).getRegionWidth(), spriteSheet.get(0).getRegionHeight(),
				getWidth(), getHeight());
		for (Sprite s : spriteSheet)
			s.setSize(scaling.x, scaling.y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		applyTransform(batch, computeTransform());
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
			for (Sprite s : kamehamehaSprite)
				s.draw(batch);
		resetTransform(batch);
		batch.flush();
	}

	private class KamehamehaAction extends FloatAction {
		@Override
		protected void update(float percent) {
			super.update(percent);
			float curLength = percent * kamehamehaLength;
			float length0 = curLength;
			float length1 = 0;
			float length2 = 0;
			if (length0 > 65) {
				length0 = 65;
				length1 = curLength - length0;
				if (kamehamehaLength - curLength < 65) {
					length1 = kamehamehaLength - 65 - 65;
					length2 = curLength - length0 - length1;
				}
			}
			kamehamehaSprite[0].setSize(length0 * 1.2f, kamehamehaSprite[0].getHeight());
			kamehamehaSprite[0].setRegionWidth((int) length0);
			kamehamehaSprite[1].setSize(length1, kamehamehaSprite[1].getHeight());
			kamehamehaSprite[2].setSize(length2 * 1.2f, kamehamehaSprite[2].getHeight());
			kamehamehaSprite[2].setRegionWidth((int) length2);
			kamehamehaSprite[1].setPosition(kamehamehaSprite[0].getX() + kamehamehaSprite[0].getWidth(),
					kamehamehaSprite[0].getY());
			kamehamehaSprite[2].setPosition(kamehamehaSprite[1].getX() + kamehamehaSprite[1].getWidth(),
					kamehamehaSprite[0].getY());
		}
	}

	private class KamehamehaEndAction extends FloatAction {
		@Override
		protected void update(float percent) {
			super.update(percent);
			for (Sprite s : kamehamehaSprite) {
				s.setAlpha(1 - percent);
			}
		}
	}

}
>>>>>>> da0b266c97f477e35bd271440e35dfb60aa4dccd
