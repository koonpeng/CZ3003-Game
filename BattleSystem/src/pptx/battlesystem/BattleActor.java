package pptx.battlesystem;

import java.util.ArrayList;

public class BattleActor {

	public int hp;
	public int maxHp;
	public int att;
	public int def;
	private String name;
	
	private ArrayList<IOnAttackTrig> onAttackTrigs = new ArrayList<IOnAttackTrig>();
	private ArrayList<IPostAttackTrig> postAttackTrigs = new ArrayList<IPostAttackTrig>();
	private ArrayList<IOnHitTrig> onHitTrigs = new ArrayList<IOnHitTrig>();
	private ArrayList<IPostHitTrig> postHitTrigs = new ArrayList<IPostHitTrig>();

	public BattleActor(String name, int hp, int maxHp, int att, int def) {
		this.hp = hp;
		this.maxHp = maxHp;
		this.att = att;
		this.def = def;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ArrayList<IOnAttackTrig> getOnAttackTrigs() {
		return onAttackTrigs;
	}

	public ArrayList<IPostAttackTrig> getPostAttackTrigs() {
		return postAttackTrigs;
	}

	public ArrayList<IOnHitTrig> getOnHitTrigs() {
		return onHitTrigs;
	}

	public ArrayList<IPostHitTrig> getPostHitTrigs() {
		return postHitTrigs;
	}

	public void registerOnAttackTrig(IOnAttackTrig trig) {
		onAttackTrigs.add(trig);
	}

	public void registerPostAttackTrig(IPostAttackTrig trig) {
		postAttackTrigs.add(trig);
	}

	public void registerOnHitTrig(IOnHitTrig trig) {
		onHitTrigs.add(trig);
	}

	public void registerPostHitTrig(IPostHitTrig trig) {
		postHitTrigs.add(trig);
	}

}
