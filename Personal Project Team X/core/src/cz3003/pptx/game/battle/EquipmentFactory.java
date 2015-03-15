package cz3003.pptx.game.battle;

public class EquipmentFactory {

	public static Equipment getEquipment(String name) {
		if (name.equals("Excalibur")) {
			return new Excalibur();
		}
		return null;
	}

}
