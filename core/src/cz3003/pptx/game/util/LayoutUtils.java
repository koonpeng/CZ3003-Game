package cz3003.pptx.game.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LayoutUtils {

	public static void center(Actor a, Actor to) {
		Vector2 pos = to.localToStageCoordinates(new Vector2());
		a.setPosition(to.getWidth() / 2 + pos.x - a.getWidth() / 2, to.getHeight() / 2 + pos.y - a.getHeight() / 2);
	}

	public static void alignY(Actor a, Actor to, float offset) {
		Vector2 pos = to.localToStageCoordinates(new Vector2());
		Vector2 after = a.stageToLocalCoordinates(new Vector2(pos.x, to.getHeight() / 2 + pos.y - a.getHeight() / 2 + offset));
		a.setY(after.y);
	}

	public static void centerOrigin(Actor a) {
		float x = a.getWidth() / 2;
		float y = a.getHeight() / 2;
		a.setOrigin(x, y);
	}

}
