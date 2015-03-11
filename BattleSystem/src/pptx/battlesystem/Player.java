package pptx.battlesystem;

import java.util.ArrayList;

import pptx.equipment.Equipment;

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
		
		BattleActor actor = new BattleActor(name, hp, maxHp, att, def);
		for (Equipment equip : equips) {
			actor.att += equip.att;
			actor.def += equip.def;
			actor.maxHp += equip.maxHp;
			actor.registerOnAttackTrig(equip);
			actor.registerOnHitTrig(equip);
			actor.registerPostAttackTrig(equip);
			actor.registerPostHitTrig(equip);
		}
		return actor;
	}
	
}
