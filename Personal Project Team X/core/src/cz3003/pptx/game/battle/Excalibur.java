package cz3003.pptx.game.battle;


public class Excalibur extends Equipment {

	public Excalibur() {
		super("Excalibur", 100, 100, 0);
	}

	@Override
	public int onAttack(BattleActor source, BattleActor target, int dmg) {
		// TODO Auto-generated method stub
		return dmg;
	}

	@Override
	public void postAttack(BattleActor source, BattleActor target, int dmg) {
		// TODO Auto-generated method stub

	}

	@Override
	public int onHit(BattleActor source, BattleActor target, int dmg) {
		// TODO Auto-generated method stub
		return dmg;
	}

	@Override
	public void postHit(BattleActor source, BattleActor target, int dmg) {
		// TODO Auto-generated method stub

	}

}
