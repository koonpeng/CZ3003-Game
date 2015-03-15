package cz3003.pptx.game.battle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import cz3003.pptx.game.PPTXGame;

public class EnemyActor extends BattleActor {

	private final Texture tex;

	public EnemyActor(String name, int hp, int maxHp, int att, int def) {
		super(name, hp, maxHp, att, def);
		tex = PPTXGame.getAssetManager().get("monsters/Progenitor.png");
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
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(tex, getX() - getOriginX(), getY());
	}

}
