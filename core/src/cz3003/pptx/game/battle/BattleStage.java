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
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
	private final Label playerLbl;
	private final Label enemyLbl;
	private final LabelStyle style;
	private final LabelStyle damageLblStyle;
	private final Sprite battleBackground;
	private final Sprite questionBackground;
	private final Image questionBackgroundHolder;
	private final HPBar playerHpBar;
	private final HPBar enemyHpBar;
	private final Table battleUI;
	private final QuestionUI questionUI;
	private final BattleActor player;
	private final EnemyActor enemy;
	private final Random rand = new Random();
	private final Quiz test;
	private final Music battleMusic;

	public BattleStage(final EnemyActor enemy, int dungeonId, String dungeonName) {
		super(new StretchViewport(PPTXGame.GAME_WIDTH, PPTXGame.GAME_HEIGHT));
		player = PPTXGame.player.genBattleActor();
		test = new Quiz(0);
		questionUI = new QuestionUI(test);
		questionUI.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (event.getTarget().getName() != null && event.getTarget().getName().equals("ansButton")) {
//					if (test.verifyAnswer((String) event.getTarget().getUserObject())) {
//						doAttack(player, enemy, true);
//						questionResultLbl.setText("Correct!");
//					} else {
//						doAttack(player, enemy, false);
//						questionResultLbl.setText("Wrong... :(");
//					}
					doAttack(player, enemy, true);
					questionResultLbl.setText("Wrong... :(");
					Action showQuestionResulLbltAct = Actions.show();
					showQuestionResulLbltAct.setTarget(questionResultLbl);
					questionResultLbl.pack();
					LayoutUtils.center(questionUI, questionResultLbl);
					questionUI.addAction(Actions.sequence(Actions.moveBy(PPTXGame.GAME_WIDTH, 0, 0.2f), showQuestionResulLbltAct));
				}
			}
		});

		battleUI = new Table();
		style = new LabelStyle(PPTXGame.getAssetManager().get("size36.ttf", BitmapFont.class), Color.BLACK);
		this.enemy = enemy;
		playerLbl = new Label("Player", style);
		playerLbl.pack();
		enemyLbl = new Label(enemy.getName(), style);
		enemyLbl.pack();
		damageLblStyle = new LabelStyle(PPTXGame.getAssetManager().get("size36.ttf", BitmapFont.class), Color.WHITE);
		questionResultLbl = new Label("", style);
		questionResultLbl.setVisible(false);
		battleBackground = new Sprite(PPTXGame.getAssetManager().get("backgrounds/environment_forest_alt1.png", Texture.class));
		questionBackground = new Sprite(PPTXGame.getAssetManager().get("backgrounds/crumpled-paper.jpg", Texture.class));
		questionBackgroundHolder = new Image(new SpriteDrawable(questionBackground));
		questionBackgroundHolder.setSize(questionUI.getWidth(), questionUI.getHeight());
		playerHpBar = new HPBar();
		enemyHpBar = new HPBar();
		battleUI.setBackground(new SpriteDrawable(battleBackground));
		battleUI.setWidth(PPTXGame.GAME_WIDTH);
		battleUI.setHeight(PPTXGame.GAME_HEIGHT / 2);
		battleUI.add(player).size(150).left().padTop(100);
		battleUI.add(enemy).size(250).right().padTop(100);
		player.setZIndex(2);
		battleUI.row();
		battleUI.add(playerLbl).top();
		battleUI.add(playerHpBar).size(500, 56);
		battleUI.row();
		battleUI.add(enemyLbl).top();
		battleUI.add(enemyHpBar).colspan(2).size(500, 56);
		battleUI.setPosition(0, PPTXGame.GAME_HEIGHT / 2);

		addActor(questionBackgroundHolder);
		addActor(questionUI);
		addActor(questionResultLbl);
		addActor(battleUI);

		battleMusic = PPTXGame.getAssetManager().get("music/(10) Force Your Way.mp3");
		battleMusic.setLooping(true);
		battleMusic.setVolume(0.75f);
		battleMusic.play();
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
							battleMusic.stop();
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
		damageLbl.moveBy(0, 100);
		damageLbl.setVisible(true);
		damageLbl.addAction(Actions.parallel(Actions.moveBy(0, 150, 1), Actions.fadeOut(1)));
		addActor(damageLbl);
	}

}
