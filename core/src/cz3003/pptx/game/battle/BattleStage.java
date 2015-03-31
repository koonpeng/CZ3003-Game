package cz3003.pptx.game.battle;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cz3003.pptx.game.PPTXGame;
import cz3003.pptx.game.battle.quiz.Quiz;
import cz3003.pptx.game.util.LayoutUtils;

public class BattleStage extends Stage {

	private final Label questionResultLbl;
	private final Label enemyNameLbl;
	private final LabelStyle style;
	private final LabelStyle damageLblStyle;
	private final Sprite background;
	private final HPBar enemyHpBar;
	private final Table battleUI;
	private final QuestionUI questionUI;
	private final BattleActor player;
	private final EnemyActor enemy;
	private final Random rand = new Random();
	private final Quiz test;

	public BattleStage(final EnemyActor enemy) {
		super(new StretchViewport(PPTXGame.GAME_WIDTH, PPTXGame.GAME_HEIGHT));
		player = PPTXGame.player.genBattleActor();
		test = new Quiz(0);
		questionUI = new QuestionUI(test);
		questionUI.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (event.getTarget().getName() != null && event.getTarget().getName().equals("ansButton")) {
					if (test.verifyAnswer((String) event.getTarget().getUserObject())) {
						doAttack(player, enemy, true);
						questionResultLbl.setText("Correct!");
					} else {
						doAttack(player, enemy, false);
						questionResultLbl.setText("Wrong... :(");
					}
					Action showQuestionResulLbltAct = Actions.show();
					showQuestionResulLbltAct.setTarget(questionResultLbl);
					questionResultLbl.pack();
					LayoutUtils.center(questionUI, questionResultLbl);
					questionUI.addAction(Actions.sequence(Actions.moveBy(720, 0, 0.2f), showQuestionResulLbltAct));
				}
			}
		});

		battleUI = new Table();
		style = new LabelStyle(PPTXGame.getAssetManager().get("size36.ttf", BitmapFont.class), Color.RED);
		this.enemy = enemy;
		enemyNameLbl = new Label(enemy.getName(), style);
		enemyNameLbl.setPosition(0, PPTXGame.GAME_HEIGHT - enemyNameLbl.getHeight());
		damageLblStyle = style;
		questionResultLbl = new Label("", style);
		questionResultLbl.setVisible(false);
		background = new Sprite(PPTXGame.getAssetManager().get("backgrounds/environment_forest_alt1.png", Texture.class));
		enemyHpBar = new HPBar(500, 56);
		//battleUI.setBackground(new SpriteDrawable(background));
		battleUI.setWidth(PPTXGame.GAME_WIDTH);
		battleUI.setHeight(PPTXGame.GAME_HEIGHT / 2);
		battleUI.add(player).size(150).left();
		battleUI.add(enemy).size(150).right();
		battleUI.row();
		battleUI.add(enemyHpBar).padTop(50).colspan(2);
		battleUI.setPosition(0, PPTXGame.GAME_HEIGHT / 2);

		addActor(questionUI);
		addActor(questionResultLbl);
		addActor(battleUI);
		addActor(enemyNameLbl);
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

		/*
		 * Take damage
		 */
		ParallelAction takeDamageAct = Actions.parallel();
		Action targetTakeDamageAct = target.getTakeDamageAction();
		if (targetTakeDamageAct != null)
			takeDamageAct.addAction(targetTakeDamageAct);
		takeDamageAct.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				doDamage(target, combatParams.dmg);
				if (target == enemy) {
					showDamage(combatParams);
				}
			}
		}));
		takeDamageAct.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				updateEnemyHpBar();
			}
		}));
		combatAct.addAction(takeDamageAct);

		/*
		 * Post attack
		 */
		combatAct.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				if (enemy.getHp() <= 0) {
					Action deathAct = Actions.color(Color.BLACK, 0.5f);
					Action deathActDisappear = Actions.fadeOut(1);
					deathAct.setTarget(enemy);
					deathActDisappear.setTarget(enemy);
					addAction(Actions.sequence(Actions.parallel(deathAct, deathActDisappear), Actions.run(new Runnable() {
						public void run() {
							PPTXGame.toResultScreen();
						}
					})));
					return;
				}
				questionUI.nextQuestion();
				Action hideQuestionResultLblAct = Actions.hide();
				hideQuestionResultLblAct.setTarget(questionResultLbl);
				Action moveQuestionUI = Actions.sequence(hideQuestionResultLblAct, Actions.moveTo(0 - questionUI.getWidth(), 0),
						Actions.moveBy(questionUI.getWidth(), 0, 0.2f));
				moveQuestionUI.setTarget(questionUI);
				Action after = Actions.after(moveQuestionUI);
				after.setTarget(questionUI);
				questionUI.addAction(after);
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
		dmg *= 1 + (rand.nextFloat() / 5 - 0.1); // Damage variation
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
