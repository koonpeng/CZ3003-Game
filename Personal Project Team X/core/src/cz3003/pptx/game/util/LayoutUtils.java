package cz3003.pptx.game.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LayoutUtils {

	public static void center(Actor base, Actor target) {
		Vector2 basePos = base.localToStageCoordinates(new Vector2());
		target.setX(base.getWidth() / 2 + basePos.x - target.getWidth() / 2);
		target.setY(base.getHeight() / 2 + basePos.y - target.getHeight() / 2);
	}

}
