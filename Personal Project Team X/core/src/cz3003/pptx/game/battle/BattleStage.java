package cz3003.pptx.game.battle;

import java.util.Random;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cz3003.pptx.game.PPTXGame;
import cz3003.pptx.game.test.TestQuestionPool;
import cz3003.pptx.game.util.LayoutUtils;

public class BattleStage extends Stage {

	private final Label questionResultLbl;
	private final Label enemyNameLbl;
	private final Sprite background;
	private final HPBar enemyHpBar;
	private final Table battleUI;
	private final QuestionUI questionUI;
	private final BattleActor player;
	private final EnemyActor enemy;
	private final LabelStyle damageLblStyle;

	private Random rand = new Random();
	private int score = 0;

	public BattleStage() {
		super(new StretchViewport(PPTXGame.GAME_WIDTH, PPTXGame.GAME_HEIGHT));
		player = PPTXGame.player.genBattleActor();
		questionUI = new QuestionUI(new TestQuestionPool());
		questionUI.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (event.getTarget().getName() != null && event.getTarget().getName().equals("ansButton")) {
					if ((Boolean) event.getTarget().getUserObject()) {
						doAttack(player, enemy, true);
						questionResultLbl.setText("Correct!");
						score++;
					} else {
						doAttack(player, enemy, false);
						questionResultLbl.setText("Wrong... :(");
					}
					Action showQuestionResulLbltAct = Actions.show();
					showQuestionResulLbltAct.setTarget(questionResultLbl);
					questionResultLbl.pack();
					LayoutUtils.center(questionUI, questionResultLbl);
					questionUI.addAction(Actions.sequence(Actions.moveBy(questionUI.getWidth(), 0, 0.2f),
							showQuestionResulLbltAct));
				}
			}
		});

		enemy = new EnemyActor("Progenitor", 5000, 5000, 100, 100);

		battleUI = new Table();
		LabelStyle style = new LabelStyle(PPTXGame.getAssetManager().get("size36.ttf", BitmapFont.class), Color.RED);
		damageLblStyle = style;
		questionResultLbl = new Label("", style);
		questionResultLbl.setVisible(false);
		enemyNameLbl = new Label(enemy.getName(), style);
		enemyNameLbl.setPosition(0, PPTXGame.GAME_HEIGHT - enemyNameLbl.getHeight());
		background = new Sprite(PPTXGame.getAssetManager().get("backgrounds/environment_forest_alt1.png", Texture.class));
		enemyHpBar = new HPBar(500, 56);
		battleUI.setBackground(new SpriteDrawable(background));
		battleUI.setWidth(PPTXGame.GAME_WIDTH);
		battleUI.setHeight(PPTXGame.GAME_HEIGHT / 2);
		battleUI.add(enemy);
		battleUI.row();
		battleUI.add(enemyHpBar).padTop(50);
		battleUI.setPosition(0, PPTXGame.GAME_HEIGHT / 2);

		addActor(questionUI);
		addActor(questionResultLbl);
		addActor(battleUI);
		addActor(enemyNameLbl);

		Music battleMusic = PPTXGame.getAssetManager().get("music/1-15 Unrest - Hoist the Sword with Pride in the Heart.mp3");
		battleMusic.setLooping(true);
		battleMusic.setVolume(0.75f);
		battleMusic.play();

		addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
	}

	private void updateEnemyHpBar() {
		float enemyHpPercent = (float) enemy.getHp() / enemy.getMaxHp();
		if (enemy.getHp() <= 0) {
			System.out.println("WIN!!");
		}
		enemyHpBar.setPercent(enemyHpPercent);
	}

	private void doDamage(BattleActor target, int dmg) {
		target.setHp(target.getHp() - dmg);
		if (target.getHp() < 0) {
			target.setHp(0);
		}
	}

	private void doAttack(final BattleActor source, final BattleActor target, boolean correctAns) {
		System.out.println(source.getName() + " attacks!!");

		/*
		 * Calculate damage
		 */
		int dmg = calcDamage(source.getAtt(), target.getDef());
		if (correctAns) {
			System.out.println("Critical Hit!!");
			dmg = (int) (dmg * 1.5);
		}
		final CombatParameters combatParams = new CombatParameters();
		combatParams.dmg = dmg;

		/*
		 * Attack
		 */
		SequenceAction combatAct = Actions.sequence();
		Action sourceAttackAct = source.getAttackAction();
		if (sourceAttackAct != null) {
			combatAct.addAction(sourceAttackAct);
		}
		combatAct.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				doDamage(target, combatParams.dmg);
				if (target == enemy) {
					showDamage(combatParams);
				}
				updateEnemyHpBar();
			}
		}));

		/*
		 * Take damage
		 */
		Action targetTakeDamageAct = target.getTakeDamageAction();
		if (targetTakeDamageAct != null)
			combatAct.addAction(targetTakeDamageAct);
		combatAct.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				System.out.println(String.format("%s does %d damage to %s!!", source.getName(), combatParams.dmg,
						target.getName()));
			}
		}));

		/*
		 * Post attack
		 */
		Action targetPostHitAct = target.getPostHitAction(source, target, combatParams);
		if (targetPostHitAct != null)
			combatAct.addAction(targetPostHitAct);
		combatAct.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				if (source == player) {
					doAttack(enemy, player, false);
				} else {
					questionUI.setX(0 - questionUI.getWidth());
					questionResultLbl.setVisible(false);
					questionUI.nextQuestion();
					Action hideQuestionResultLblAct = Actions.hide();
					hideQuestionResultLblAct.setTarget(questionResultLbl);
					questionUI.addAction(Actions.sequence(Actions.moveBy(questionUI.getWidth(), 0, 0.2f),
							hideQuestionResultLblAct));
				}
				updateEnemyHpBar();
			}
		}));
		addAction(combatAct);
	}

	private int calcDamage(int att, int def) {
		int dmg = att - def / 2;
		if (dmg < att / 2) {
			dmg = att / 2;
		}
		dmg *= 1 + (rand.nextFloat() / 5 - 0.1);	// Damage variation
		return dmg;
	}

	private void showDamage(final CombatParameters combatParams) {
		Label damageLbl = new Label(Integer.toString(combatParams.dmg), damageLblStyle);
		damageLbl.setText(Integer.toString(combatParams.dmg));
		damageLbl.pack();
		LayoutUtils.center(enemy, damageLbl);
		damageLbl.moveBy(0, 50);
		damageLbl.setVisible(true);
		damageLbl.addAction(Actions.sequence(Actions.moveBy(0, 100, 1), Actions.hide()));
		addActor(damageLbl);
	}

}
