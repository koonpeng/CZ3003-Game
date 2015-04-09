package cz3003.pptx.game;

import java.util.ArrayList;

import cz3003.pptx.game.battle.BattleActor;
import cz3003.pptx.game.battle.PlayerActor;
import cz3003.pptx.game.equipment.Equipment;

public class Player {

	public int lvl;
	public int hp;
	public int maxHp;
	public int att;
	public int def;
	public String name;

	private ArrayList<Equipment> equips = new ArrayList<Equipment>();

	public Player(String name, int maxHp, int att, int def) {
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.att = att;
		this.def = def;
		this.name = name;
	}

	public void equip(Equipment equipment) {
		equips.add(equipment);
	}

	public BattleActor genBattleActor() {
		int att = this.att;
		int def = this.def;
		int hp = this.hp;
		int maxHp = this.maxHp;

		BattleActor actor = new PlayerActor(name, hp, maxHp, att, def);
		for (Equipment equip : equips) {
			att += equip.att;
			def += equip.def;
			maxHp += equip.maxHp;
			hp += equip.maxHp;
		}
		actor.setAtt(att);
		actor.setDef(def);
		actor.setHp(hp);
		actor.setMaxHp(maxHp);
		return actor;
	}

}
