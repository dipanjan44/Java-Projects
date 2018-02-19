package game_gys3.characters;

import game_gys3.PresidentialRescue;
import game_gys3.weapons.Bullet;
import game_gys3.weapons.MissileRound;
import game_gys3.weapons.SparkleShell;
import jgame.JGObject;
import jgame.JGRectangle;
import jgame.JGTimer;
import jgame.platform.StdGame;

public class Turret extends Enemy {
	int frames_collided = 0;

	public Turret(double newX, double newY, PresidentialRescue newEngine) {
		super("turret", true, newX, newY, DEFAULT_CID, "turret", 0, 0, 32, 32, newEngine);

		new JGTimer(40, // timer alarms after x frames
				false, // false = restart after alarm, true = one-shot
				this // Parent. The timer will remove itself
						// automatically when the parent is removed.
		) {
			public void alarm() {
				// if (game.countObjects("engineer", 0) < 15) {
				//if (game.random(0, 1, 1) == 1) {
					new MissileRound(x+8,y+32,0,5, Bullet.DEFAULT_ENEMY_BULLET_CID, game);
			}
		};
	}

	public void doDeath() {
		createExplosion(x,y,7);
		remove();
		game.score += MED_POINTS;
		//pop-up for experimental grp2
				/*if(PresidentialRescue.control >= 0.5 && PresidentialRescue.control <= 0.75)
				{
				popup(game.score);
				   
				}
				// pop-up call for last experimental grp3
				else if (PresidentialRescue.control >= 0.75)
				popup(game.score);*/
	}

}