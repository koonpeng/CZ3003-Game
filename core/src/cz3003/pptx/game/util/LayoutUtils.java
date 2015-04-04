package cz3003.pptx.game.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LayoutUtils {

	public static void center(Actor a, Actor to) {
		Vector2 pos = to.localToStageCoordinates(new Vector2());
		Vector2 move = a.stageToLocalCoordinates(new Vector2(pos.x + to.getWidth() / 2 - a.getWidth() / 2, pos.y + to.getHeight()
				/ 2 - a.getHeight() / 2));
		a.setPosition(a.getX() + move.x, a.getY() + move.y);
	}

}
