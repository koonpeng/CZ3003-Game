package pptx.equipment;

public class EquipmentFactory {

	public static Equipment getEquipment(String name) {
		switch (name) {
		case "Excalibur":
			return new Excalibur();
		}
		return null;
	}

}
