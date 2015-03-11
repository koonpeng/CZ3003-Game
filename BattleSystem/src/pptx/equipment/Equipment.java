package pptx.equipment;

import pptx.battlesystem.IOnAttackTrig;
import pptx.battlesystem.IOnHitTrig;
import pptx.battlesystem.IPostAttackTrig;
import pptx.battlesystem.IPostHitTrig;

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
