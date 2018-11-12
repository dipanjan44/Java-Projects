package game_gys3.characters;

import game_gys3.PresidentialRescue;
import game_gys3.weapons.Bullet;
import game_gys3.weapons.GenericBullet;
import game_gys3.weapons.MissileRound;
import jgame.JGObject;
import jgame.JGRectangle;
import jgame.JGTimer;
import jgame.platform.StdGame;

public class EnemyCopter extends Enemy {
	
	public static final int DEFAULT_CID = 8;
	
	//copter has cid of 8 to make sure it can be run through.
	public EnemyCopter(double newX, double newY, double xVelo, double yVelo,
			PresidentialRescue newEngine) {
		super("copter", true, newX, newY, DEFAULT_CID, "copter", 0, 0, 64, 64, xVelo,
				yVelo, newEngine);

		new JGTimer(game.random(40, 80, 1) - game.stage * 2, // timer alarms
																// after x
																// frames
				false, // false = restart after alarm, true = one-shot
				this // Parent. The timer will remove itself
						// automatically when the parent is removed.
		) {
			public void alarm() {
				new GenericBullet("copter_bullet", x + 32, y + 32, Bullet.DEFAULT_ENEMY_BULLET_CID, "bullet",
						xdir * game.random(2,4), game.random(3,5), game);
				new GenericBullet("copter_bullet", x + 32, y + 32, Bullet.DEFAULT_ENEMY_BULLET_CID, "bullet",
						-xdir * game.random(2,4), game.random(3,5), game);
			}
		};
	}

	public void move() {
		if (x < game.pfWidth() * 2 / 3 && x > game.pfWidth() / 3)
			setGraphic("copter");
		else if (x >= game.pfWidth() * 2 / 3) {
			setAnim("copter_l");
		} else
			setAnim("copter_r");

		if (x < 0 && xdir < 0) {
			xdir = -xdir;
		}

		if (y < game.pfHeight() * .1 && ydir < 0) {
			ydir = -ydir;
		}
		if (x > game.pfWidth() - 32 && xdir > 0) {
			xdir = -xdir;
		}
		if (y > game.pfHeight() * 0.6 && ydir > 0) {
			ydir = -ydir;
		}

	}

	public void doDeath() {
		createExplosion(x, y, 6);
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