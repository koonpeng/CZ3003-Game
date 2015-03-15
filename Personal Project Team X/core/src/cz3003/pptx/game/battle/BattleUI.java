package cz3003.pptx.game.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import cz3003.pptx.game.PPTXGame;

public class BattleUI extends Table {

	public static final int WIDTH = PPTXGame.GAME_WIDTH;
	public static final int HEIGHT = PPTXGame.GAME_HEIGHT / 2; // Half the screen

	private final Sprite background;
	private final Label enemyNameLbl;
	private final EnemyActor enemy;
	private final HPBar hpBar;

	public BattleUI(EnemyActor enemy) {
		super();
		LabelStyle style = new LabelStyle(PPTXGame.getAssetManager().get("size36.ttf", BitmapFont.class), new Color(1, 0, 1, 1));
		this.enemyNameLbl = new Label(enemy.getName(), style);
		this.enemy = enemy;

		background = new Sprite(PPTXGame.getAssetManager().get("backgrounds/environment_forest_alt1.png", Texture.class));
		hpBar = new HPBar();

		setBackground(new SpriteDrawable(background));
		setWidth(WIDTH);
		setHeight(HEIGHT);
		add(enemyNameLbl).padBottom(100);
		row();
		add(enemy);
		row();
		add(hpBar).width(500).height(56).padTop(50);
	}

	public void update() {
		float enemyHpPercent = (float) enemy.getHp() / enemy.getMaxHp();
		if (enemy.getHp() <= 0) {
			System.out.println("WIN!!");
		}
		hpBar.setPercent(enemyHpPercent);
	}

}
