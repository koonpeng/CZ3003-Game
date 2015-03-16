package cz3003.pptx.game.battle;

import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cz3003.pptx.game.PPTXGame;

public class PlayerActor extends BattleActor {

	private Random rand = new Random();
	private Sound[] attackSounds = new Sound[1];

	public PlayerActor(String name, int hp, int maxHp, int att, int def) {
		super(name, hp, maxHp, att, def);
		attackSounds[0] = PPTXGame.getAssetManager().get("sound/33245__ljudman__grenade-16bit.wav");
	}

	@Override
	public Action getAttackAction() {
		return Actions.run(new Runnable() {
			@Override
			public void run() {
				switch (rand.nextInt(1)) {
				case 0:
					attackSounds[0].play(0.75f);
					break;
				}
			}
		});
	}

	@Override
	public Action getTakeDamageAction() {
		return null;
	}

	@Override
	public Action getPostHitAction(BattleActor source, BattleActor target, CombatParameters combatParams) {
		return null;
	}

}
