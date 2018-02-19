package game_gys3.characters;

import game_gys3.PresidentialRescue;
import game_gys3.weapons.Bullet;
import game_gys3.weapons.GenericBullet;
import game_gys3.weapons.MissileRound;
import jgame.JGRectangle;
import jgame.platform.StdGame;

public class EnemyTank extends Enemy {
	private final static double DEFAULT_SPEED = 2;
	boolean descending = false;
	int dir = 1;

	public EnemyTank(double x, double y, int dir, PresidentialRescue game) {
		super("enemy_tank", true, x, y, DEFAULT_CID, "enemy_tank_r1", 0, 0, 32,
				32, 0, 0, DEFAULT_SPEED, DEFAULT_SPEED * 5, -1, game);
	}

	public void move() {
		snapToGrid();
		if (dir > 0)
			setAnim("enemy_tank_r");
		else
			setAnim("enemy_tank_l");
		if (descending) {
			y += yspeed;
			if (isYAligned()) {
				descending = false;
			}
		} else {
			x += dir * xspeed;
			if ((x < 0 && dir < 0) || (x > 640 - 32 && dir > 0)) {
				descending = true;
				dir = -dir;
			}

		}
		if (y >= game.pfHeight()) {
			y = game.pfHeight() / 2;
			snapToGrid(0, 32);
		}
		int randomnum = game.random(0, 200, 1);
		if (randomnum == 1 && game.countObjects("b", 0) < 30) {
			JGRectangle r = getCenterTiles();
			game.setTile(r.x + dir, r.y, "b");
		}
		if (randomnum == 2) {
			String missileImage = (dir > 0) ? "missile_r" : "missile_l";
			new GenericBullet("boss_lazer", x + dir * 10, y,
					Bullet.DEFAULT_ENEMY_BULLET_CID, missileImage, dir * Bullet.SLOW_CALIBER, 0, game);
		}
	}

	public void doDeath() {
		createExplosion(x, y, 5);
		remove();
		game.score += SMALL_POINTS;
		//pop-up for experimental grp2
				if(PresidentialRescue.control >= 0.5 && PresidentialRescue.control <= 0.75)
				{
				popup(game.score);
				   
				}
				// pop-up call for last experimental grp3
				else if (PresidentialRescue.control >= 0.75)
				popup(game.score);
	}

	public void hit_bg(int tilecid) {
		snapToGrid(16, yspeed / 2.0);
		if (!descending)
			dir = -dir;
		descending = true;
	}
}
