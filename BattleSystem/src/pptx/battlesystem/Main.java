package pptx.battlesystem;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import pptx.equipment.EquipmentFactory;

public class Main {

	private JFrame frame;
	private JLabel lbl_player;
	private Player player;
	private BattleActor playerBattleActor;
	private BattleActor enemy;
	private JProgressBar pb_playerHp;
	private JProgressBar pb_enemyHp;
	private JLabel lbl_enemy;
	private JLabel lbl_playerHp;

	private JLabel lbl_enemyHp;
	private JButton btn_attack;
	private JTextArea ta_battlelog;
	private JScrollPane sp_battlelog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 541, 353);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		player = new Player("Player", 1000, 200, 200);
		player.equip(EquipmentFactory.getEquipment("Excalibur"));
		playerBattleActor = player.genBattleActor();
		enemy = new BattleActor("CZ3003", 1000, 1000, 200, 200);

		lbl_player = new JLabel(playerBattleActor.getName());
		lbl_player.setBounds(28, 36, 46, 14);
		frame.getContentPane().add(lbl_player);

		lbl_enemy = new JLabel(enemy.getName());
		lbl_enemy.setBounds(469, 36, 46, 14);
		frame.getContentPane().add(lbl_enemy);

		pb_playerHp = new JProgressBar();
		pb_playerHp.setMaximum(player.maxHp);
		pb_playerHp.setValue(player.hp);
		pb_playerHp.setBounds(84, 36, 146, 14);
		frame.getContentPane().add(pb_playerHp);

		pb_enemyHp = new JProgressBar();
		pb_enemyHp.setMaximum(enemy.maxHp);
		pb_enemyHp.setValue(enemy.hp);
		pb_enemyHp.setBounds(297, 36, 146, 14);
		frame.getContentPane().add(pb_enemyHp);

		lbl_playerHp = new JLabel("0");
		lbl_playerHp.setText(Integer.toString(player.hp));
		lbl_playerHp.setBounds(84, 61, 46, 14);
		frame.getContentPane().add(lbl_playerHp);

		lbl_enemyHp = new JLabel("0");
		lbl_enemyHp.setText(Integer.toString(enemy.hp));
		lbl_enemyHp.setBounds(397, 61, 46, 14);
		frame.getContentPane().add(lbl_enemyHp);

		btn_attack = new JButton("Attack");
		btn_attack.addActionListener(e -> attack());
		btn_attack.setBounds(218, 93, 89, 23);
		frame.getContentPane().add(btn_attack);

		sp_battlelog = new JScrollPane();
		sp_battlelog.setBounds(10, 136, 505, 168);
		frame.getContentPane().add(sp_battlelog);

		ta_battlelog = new JTextArea();
		sp_battlelog.setViewportView(ta_battlelog);
		ta_battlelog.setEditable(false);
	}

	private void attack() {
		if (player.hp > 0 && enemy.hp > 0) {
			int dmg = BattleSystem.doAttack(playerBattleActor, enemy);
			updateLog(playerBattleActor, enemy, dmg);
			update(dmg);
		}
		if (player.hp > 0 && enemy.hp > 0) {
			int dmg = BattleSystem.doAttack(enemy, playerBattleActor);
			updateLog(enemy, playerBattleActor, dmg);
			update(dmg);
		}
	}

	private void update(int dmgdone) {
		lbl_playerHp.setText(Integer.toString(playerBattleActor.hp));
		pb_playerHp.setValue(playerBattleActor.hp);
		lbl_enemyHp.setText(Integer.toString(enemy.hp));
		pb_enemyHp.setValue(enemy.hp);
	}

	private void updateLog(BattleActor source, BattleActor target, int dmgdone) {
		String log = source.getName() + " attacks!\n" + target.getName() + " takes " + dmgdone + " damage!!\n";
		if (target.hp <= 0) {
			log += "\n\t" + source.getName() + " wins!!\n";
		}
		ta_battlelog.setText(ta_battlelog.getText() + log);
	}

}
