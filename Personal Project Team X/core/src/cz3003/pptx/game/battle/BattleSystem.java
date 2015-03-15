package cz3003.pptx.game.battle;

import java.util.Random;

public class BattleSystem {

	private static Random rand = new Random();

	public static int doAttack(BattleActor source, BattleActor target, boolean correctAns) {
		int dmg = calcDamage(source.getAtt(), target.getDef());
		System.out.println(source.getName() + " attacks!!");
		if (correctAns)
			System.out.println("Critical Hit!!");
		for (IOnAttackTrig onAttack : source.getOnAttackTrigs()) {
			dmg = onAttack.onAttack(source, target, dmg);
		}
		for (IOnHitTrig onHit : target.getOnHitTrigs()) {
			dmg = onHit.onHit(source, target, dmg);
		}
		if (correctAns) {
			dmg = (int) (dmg * 1.5);
		}
		target.setHp(target.getHp() - dmg);
		if (target.getHp() < 0) {
			target.setHp(0);
		}
		System.out.println(String.format("%s does %d to %s!!", source.getName(), dmg, target.getName()));
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
