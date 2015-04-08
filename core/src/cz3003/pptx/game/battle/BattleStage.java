<<<<<<< HEAD
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
	private final Sprite background;
	private final Sprite questionBackground;
	private final Image questionBackgroundHolder;
	private final HPBar playerHpBar;
	private final HPBar enemyHpBar;
	private final Table battleUI;
	private final QuestionUI questionUI;
	private final BattleActor player;
	private final EnemyActor enemy;
	private final Random rand = new Random();
	private final Quiz quiz;
	private final Music battleMusic;

	public BattleStage(final EnemyActor enemy, int dungeonId, String dungeonName) {
		super(new StretchViewport(PPTXGame.GAME_WIDTH, PPTXGame.GAME_HEIGHT));

		player = PPTXGame.player.genBattleActor();
		this.enemy = enemy;
		battleUI = new Table();
		quiz = new Quiz(0, 1);
		questionUI = new QuestionUI(quiz);
		style = new LabelStyle(PPTXGame.getAssetManager().get("calibri36.ttf", BitmapFont.class), Color.BLACK);
		damageLblStyle = new LabelStyle(PPTXGame.getAssetManager().get("calibri36.ttf", BitmapFont.class), Color.WHITE);
		playerLbl = new Label("Player", style);
		enemyLbl = new Label(enemy.getName(), style);
		questionResultLbl = new Label("", style);
		background = new Sprite(PPTXGame.getAssetManager().get("backgrounds/environment_forest_alt1.png", Texture.class));
		questionBackground = new Sprite(PPTXGame.getAssetManager().get("backgrounds/crumpled-paper.jpg", Texture.class));
		questionBackgroundHolder = new Image(new SpriteDrawable(questionBackground));
		playerHpBar = new HPBar();
		enemyHpBar = new HPBar();

		questionUI.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (event.getTarget().getName() != null && event.getTarget().getName().equals("ansButton")) {
					boolean correct = quiz.verifyAnswer((String) event.getTarget().getUserObject());
					if (correct) {
						doAttack(player, enemy, true);
						questionResultLbl.setText("Correct!");
					} else {
						doAttack(enemy, player, false);
						questionResultLbl.setText("Wrong... :(");
					}
					Action showQuestionResulLbltAct = Actions.show();
					showQuestionResulLbltAct.setTarget(questionResultLbl);
					questionResultLbl.pack();
					LayoutUtils.center(questionResultLbl, questionUI);
					questionUI.addAction(Actions.sequence(Actions.moveBy(PPTXGame.GAME_WIDTH, 0, 0.2f), showQuestionResulLbltAct));
				}
			}
		});

		playerLbl.pack();
		enemyLbl.pack();
		questionResultLbl.setVisible(false);
		questionBackgroundHolder.setSize(questionUI.getWidth(), questionUI.getHeight());

		battleUI.setBackground(new SpriteDrawable(background));
		battleUI.setWidth(PPTXGame.GAME_WIDTH);
		battleUI.setHeight(PPTXGame.GAME_HEIGHT / 2);
		battleUI.setPosition(0, PPTXGame.GAME_HEIGHT / 2);
		battleUI.left();
		battleUI.add(player).size(150).left().bottom().padLeft(20);
		battleUI.add().width(300);
		battleUI.add(enemy).size(250).right();
		battleUI.row();
		battleUI.add(playerLbl).padTop(50);
		battleUI.add(playerHpBar).size(500, 56).left().padTop(60).colspan(2);
		battleUI.row();
		battleUI.add(enemyLbl);
		battleUI.add(enemyHpBar).size(500, 56).left().padTop(10).colspan(2);

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
		float percent = (float) enemy.getHp() / enemy.getMaxHp();
		if (enemy.getHp() <= 0) {
			System.out.println("WIN!!");
		}
		enemyHpBar.setPercent(percent);
	}

	private void updatePlayerHpBar() {
		float percent = (float) player.getHp() / player.getMaxHp();
		if (player.getHp() <= 0) {
			System.out.println("LOSE...");
		}
		playerHpBar.setPercent(percent);
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
		 * Preparation
		 */
		SequenceAction combatAct = Actions.sequence();
		Action swap = Actions.run(new Runnable() {
			@Override
			public void run() {
				if (source.getZIndex() < target.getZIndex())
					battleUI.swapActor(source, target);
			}
		});
		combatAct.addAction(swap);

		/*
		 * Attack
		 */
		Action sourceAttackAct = source.getAttackAction(target);
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
				showDamage(combatParams, target);
			}
		}));
		takeDamageAct.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				updateEnemyHpBar();
				updatePlayerHpBar();
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
							PPTXGame.toResultScreen(quiz, true);
						}
					})));
					return;
				} else if (player.getHp() <= 0) {
					Action deathAct = Actions.color(Color.BLACK, 0.5f);
					Action deathActDisappear = Actions.fadeOut(1);
					deathAct.setTarget(player);
					deathActDisappear.setTarget(player);
					addAction(Actions.sequence(Actions.parallel(deathAct, deathActDisappear), Actions.run(new Runnable() {
						public void run() {
						battleMusic.stop();
							PPTXGame.toResultScreen(quiz, false);
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
				updatePlayerHpBar();
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

	private void showDamage(CombatParameters combatParams, BattleActor actor) {
		Label damageLbl = new Label(Integer.toString(combatParams.dmg), damageLblStyle);
		damageLbl.setText(Integer.toString(combatParams.dmg));
		damageLbl.pack();
		LayoutUtils.center(damageLbl, actor);
		damageLbl.moveBy(0, 100);
		damageLbl.setVisible(true);
		damageLbl.addAction(Actions.parallel(Actions.moveBy(0, 150, 1.5f), Actions.fadeOut(1)));
		addActor(damageLbl);
	}

}
=======
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
	private final Sprite background;
	private final Sprite questionBackground;
	private final Image questionBackgroundHolder;
	private final HPBar playerHpBar;
	private final HPBar enemyHpBar;
	private final Table battleUI;
	private final QuestionUI questionUI;
	private final BattleActor player;
	private final EnemyActor enemy;
	private final Random rand = new Random();
	private final Quiz quiz;
	private final Music battleMusic;

	public BattleStage(final EnemyActor enemy, int dungeonId, String dungeonName) {
		super(new StretchViewport(PPTXGame.GAME_WIDTH, PPTXGame.GAME_HEIGHT));

		player = PPTXGame.player.genBattleActor();
		this.enemy = enemy;
		battleUI = new Table();
		quiz = new Quiz(0, 1);
		questionUI = new QuestionUI(quiz);
		style = new LabelStyle(PPTXGame.getAssetManager().get("calibri36.ttf", BitmapFont.class), Color.BLACK);
		damageLblStyle = new LabelStyle(PPTXGame.getAssetManager().get("calibri36.ttf", BitmapFont.class), Color.WHITE);
		playerLbl = new Label("Player", style);
		enemyLbl = new Label(enemy.getName(), style);
		questionResultLbl = new Label("", style);
		background = new Sprite(PPTXGame.getAssetManager().get("background/environment_forest_alt1.png", Texture.class));
		questionBackground = new Sprite(PPTXGame.getAssetManager().get("background/crumpled-paper.jpg", Texture.class));
		questionBackgroundHolder = new Image(new SpriteDrawable(questionBackground));
		playerHpBar = new HPBar();
		enemyHpBar = new HPBar();

		questionUI.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (event.getTarget().getName() != null && event.getTarget().getName().equals("ansButton")) {
					boolean correct = quiz.verifyAnswer((String) event.getTarget().getUserObject());
					if (correct) {
						doAttack(player, enemy, true);
						questionResultLbl.setText("Correct!");
					} else {
						doAttack(enemy, player, false);
						questionResultLbl.setText("Wrong... :(");
					}
					Action showQuestionResulLbltAct = Actions.show();
					showQuestionResulLbltAct.setTarget(questionResultLbl);
					questionResultLbl.pack();
					LayoutUtils.center(questionResultLbl, questionUI);
					questionUI.addAction(Actions.sequence(Actions.moveBy(PPTXGame.GAME_WIDTH, 0, 0.2f), showQuestionResulLbltAct));
				}
			}
		});

		playerLbl.pack();
		enemyLbl.pack();
		questionResultLbl.setVisible(false);
		questionBackgroundHolder.setSize(questionUI.getWidth(), questionUI.getHeight());

		battleUI.setBackground(new SpriteDrawable(background));
		battleUI.setWidth(PPTXGame.GAME_WIDTH);
		battleUI.setHeight(PPTXGame.GAME_HEIGHT / 2);
		battleUI.setPosition(0, PPTXGame.GAME_HEIGHT / 2);
		battleUI.left();
		battleUI.add(player).size(150).left().bottom().padLeft(20);
		battleUI.add().width(300);
		battleUI.add(enemy).size(250).right();
		battleUI.row();
		battleUI.add(playerLbl).padTop(50);
		battleUI.add(playerHpBar).size(500, 56).left().padTop(60).colspan(2);
		battleUI.row();
		battleUI.add(enemyLbl);
		battleUI.add(enemyHpBar).size(500, 56).left().padTop(10).colspan(2);

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
		float percent = (float) enemy.getHp() / enemy.getMaxHp();
		if (enemy.getHp() <= 0) {
			System.out.println("WIN!!");
		}
		enemyHpBar.setPercent(percent);
	}

	private void updatePlayerHpBar() {
		float percent = (float) player.getHp() / player.getMaxHp();
		if (player.getHp() <= 0) {
			System.out.println("LOSE...");
		}
		playerHpBar.setPercent(percent);
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
		 * Preparation
		 */
		SequenceAction combatAct = Actions.sequence();
		Action swap = Actions.run(new Runnable() {
			@Override
			public void run() {
				if (source.getZIndex() < target.getZIndex())
					battleUI.swapActor(source, target);
			}
		});
		combatAct.addAction(swap);

		/*
		 * Attack
		 */
		Action sourceAttackAct = source.getAttackAction(target);
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
				showDamage(combatParams, target);
			}
		}));
		takeDamageAct.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				updateEnemyHpBar();
				updatePlayerHpBar();
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
							PPTXGame.toResultScreen(quiz, true);
						}
					})));
					return;
				} else if (player.getHp() <= 0) {
					Action deathAct = Actions.color(Color.BLACK, 0.5f);
					Action deathActDisappear = Actions.fadeOut(1);
					deathAct.setTarget(player);
					deathActDisappear.setTarget(player);
					addAction(Actions.sequence(Actions.parallel(deathAct, deathActDisappear), Actions.run(new Runnable() {
						public void run() {
							battleMusic.stop();
							PPTXGame.toResultScreen(quiz, false);
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
				updatePlayerHpBar();
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

	private void showDamage(CombatParameters combatParams, BattleActor actor) {
		Label damageLbl = new Label(Integer.toString(combatParams.dmg), damageLblStyle);
		damageLbl.setText(Integer.toString(combatParams.dmg));
		damageLbl.pack();
		LayoutUtils.center(damageLbl, actor);
		damageLbl.moveBy(0, 100);
		damageLbl.setVisible(true);
		damageLbl.addAction(Actions.parallel(Actions.moveBy(0, 150, 1.5f), Actions.fadeOut(1)));
		addActor(damageLbl);
	}

}
>>>>>>> da0b266c97f477e35bd271440e35dfb60aa4dccd
