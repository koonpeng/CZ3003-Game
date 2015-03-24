package cz3003.pptx.game.battle;

import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

public class CombatAction extends RunnableAction {

	private BattleActor source;
	private BattleActor target;
	private CombatParameters combatParams;

	public CombatAction() {
		super();
	}

	public BattleActor getSource() {
		return source;
	}

	public void setSource(BattleActor source) {
		this.source = source;
	}

	public BattleActor getTarget() {
		return target;
	}

	public void setTarget(BattleActor target) {
		this.target = target;
	}

	public CombatParameters getCombatParams() {
		return combatParams;
	}

	public void setCombatParams(CombatParameters combatParams) {
		this.combatParams = combatParams;
	}

	public abstract class CombatActionRunnable implements Runnable {

		@Override
		public void run() {
			run(source, target, combatParams);
		}

		public abstract void run(BattleActor source, BattleActor target, CombatParameters combatParams);
	}

}
