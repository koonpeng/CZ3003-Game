package pptx.battlesystem;

import java.util.Random;

public class BattleSystem {
	
	private static Random rand = new Random();
	
	public static int doAttack(BattleActor source, BattleActor target) {
		int dmg = calcDamage(source.att, target.def);
		for (IOnAttackTrig onAttack : source.getOnAttackTrigs()) {
			dmg = onAttack.onAttack(source, target, dmg);
		}
		for (IOnHitTrig onHit : target.getOnHitTrigs()) {
			dmg = onHit.onHit(source, target, dmg);
		}
		target.hp -= dmg;
		if (target.hp < 0) {
			target.hp = 0;
		}
		for (IPostAttackTrig postAttack : source.getPostAttackTrigs()) {
			postAttack.postAttack(source, target, dmg);
		}
		for (IPostHitTrig postHit : target.getPostHitTrigs()) {
			postHit.postHit(source, target, dmg);
		}
		return dmg;
	}
	
	private static int calcDamage(int att, int def) {
		int dmg = att - def / 2;
		if (dmg < att / 2) {
			dmg = att / 2;
		}
		dmg *= 1 + (rand.nextFloat() / 5 - 0.1);	// Damage variation
		return dmg;
	}
	
}
