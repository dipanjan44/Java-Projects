package game_gys3.characters;

import game_gys3.PresidentialRescue;
import game_gys3.weapons.Bullet;
import game_gys3.weapons.GenericBullet;
import game_gys3.weapons.LazerParticle;
import game_gys3.weapons.MissileRound;
import jgame.JGObject;
import jgame.JGRectangle;
import jgame.JGTimer;
import jgame.platform.StdGame;

public class EnemyBoss extends Enemy {
	int health = 0;
	Player player;
	public static final int DEFAULT_CID = 32;

	public EnemyBoss(double newX, double newY, double xVelo, double yVelo,
			PresidentialRescue newEngine, int newHealth, Player newPlayer) {
		super("boss", false, newX, newY, DEFAULT_CID, "plane", 0, 0, 128, 64, xVelo,
				yVelo, newEngine);

		health = newHealth;
		player = newPlayer;
		new JGTimer(30 - (game.stage), // timer alarms after x frames
				false, // false = restart after alarm, true = one-shot
				this // Parent. The timer will remove itself
						// automatically when the parent is removed.
		) {

			public void alarm() {
				double xmag = (player.x < game.pfWidth() / 2) ? -1 : 1;
				double ymag = (player.y < game.pfHeight() / 2) ? -1 : 1;

				new GenericBullet("boss_bullet", x + 64, y + 32, Bullet.DEFAULT_ENEMY_BULLET_CID, "fire_anim",
						xmag * game.random(0, 0.5)
								* GenericBullet.DEFAULT_CALIBER, ymag
								* game.random(0.5, 1)
								* GenericBullet.DEFAULT_CALIBER, game);
			}
		};
		
		
	}

	public void move() {

		if (x < 0 && xdir < 0) {
			xdir = -xdir;
		}

		if (y > game.pfHeight() * 0.5 && ydir > 0) {
			ydir = -ydir;
		}
		if (x > game.pfWidth() - 64 && xdir > 0) {
			xdir = -xdir;
		}

		if (y < 0 && ydir < 0) {
			ydir = -ydir;
		}
		
		if (game.gametime % 100 > 1 && game.gametime % 100 <20) {
			new GenericBullet("boss_lazer", x + 64, y + 32, Bullet.DEFAULT_ENEMY_BULLET_CID, "lazers",
					game.random(0, 0.5), GenericBullet.DEFAULT_CALIBER,
					game);
		}

	}

	public void hit(JGObject obj) {
		if (obj.colid == Player.DEFAULT_CID && game.cheatsEnabled) {
			doDeath();
			return;
		}
		obj.hit(this);
		createExplosion(x + 64, y + 32, 20);
		health--;
		if (health == 0) {
			doDeath();
		}
	}

	public void doDeath() {
		createExplosion(x + 64, y + 32, 50);
		remove();
		game.addGameState("EndLevel");
		game.score += LARGE_POINTS * game.stage;
		//pop-up for experimental grp2
		if(PresidentialRescue.control >= 0.5 && PresidentialRescue.control <= 0.75)
		{
		popup(game.score);
		   
		}
		// pop-up call for last experimental grp3
		else if (PresidentialRescue.control >= 0.75)
		popup(game.score);

	}

}