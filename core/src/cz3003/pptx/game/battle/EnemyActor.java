package cz3003.pptx.game.battle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cz3003.pptx.game.PPTXGame;

public class EnemyActor extends BattleActor {

	private final Sprite sprite;
	private Action blinkAction;

	public EnemyActor(String name, int hp, int maxHp, int att, int def) {
		super(name, hp, maxHp, att, def);
		Texture tex = PPTXGame.getAssetManager().get("monsters/Progenitor.png");
		sprite = new Sprite(tex);
		registerPostHitTrig(new IPostHitTrig() {
			@Override
			public void postHit(BattleActor source, BattleActor target, int dmg) {
				if (getHp() > 0) {
					setHp(getHp() + 50);
					System.out.println(getName() + "'s regeneration recovers 50 hp!!");
				}
			}
		});

		setWidth(tex.getWidth());
		setHeight(tex.getHeight());
	}

	@Override
	public Action getAttackAction() {
		return null;
	}

	@Override
	public Action getTakeDamageAction() {
		Action showAct = Actions.visible(true);
		Action hideAct = Actions.visible(false);
		showAct.setTarget(this);
		hideAct.setTarget(this);
		blinkAction = Actions.repeat(3, Actions.sequence(hideAct, Actions.delay(0.2f), showAct, Actions.delay(0.2f)));
		return blinkAction;
	}

	public Action getPostHitAction(BattleActor source, BattleActor target, CombatParameters combatParams) {
		return null;
	}

	@Override
	protected void positionChanged() {
		sprite.setPosition(getX() + getParent().getX(), getY() + getParent().getY());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setColor(getColor().tmp().mul(1, 1, 1, parentAlpha));
		sprite.draw(batch);
	}
}
