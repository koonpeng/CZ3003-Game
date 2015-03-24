package cz3003.pptx.game.equipment;

import cz3003.pptx.game.battle.IOnAttackTrig;
import cz3003.pptx.game.battle.IOnHitTrig;
import cz3003.pptx.game.battle.IPostAttackTrig;
import cz3003.pptx.game.battle.IPostHitTrig;


public abstract class Equipment implements IOnAttackTrig, IPostAttackTrig, IOnHitTrig, IPostHitTrig {

	public final String name;
	public final int att;
	public final int def;
	public final int maxHp;

	public Equipment(String name, int att, int def, int hp) {
		this.name = name;
		this.att = att;
		this.def = def;
		this.maxHp = hp;
	}

}
