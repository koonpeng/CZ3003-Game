package cz3003.pptx.game.battle;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class BattleActor extends Actor {

	private int hp;
	private int maxHp;
	private int att;
	private int def;
	private String name;

	public BattleActor(String name, int hp, int maxHp, int att, int def) {
		this.hp = hp;
		this.maxHp = maxHp;
		this.att = att;
		this.def = def;
		this.name = name;
	}

	public abstract Action getAttackAction();

	public abstract Action getTakeDamageAction();

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

}
