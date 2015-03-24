package cz3003.pptx.game.equipment;

public abstract class Equipment {

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
