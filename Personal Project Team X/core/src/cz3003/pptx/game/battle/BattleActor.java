package cz3003.pptx.game.battle;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class BattleActor extends Actor {

	private int hp;
	private int maxHp;
	private int att;
	private int def;
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

	public abstract Action getAttackAction();
	
	public abstract Action getTakeDamageAction();

	public abstract Action getPostHitAction(BattleActor source, BattleActor target, CombatParameters combatParams);

	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		if (hp > maxHp) {
			hp = maxHp;
		}
		this.hp = hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getAtt() {
		return att;
	}

	public void setAtt(int att) {
		this.att = att;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
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
