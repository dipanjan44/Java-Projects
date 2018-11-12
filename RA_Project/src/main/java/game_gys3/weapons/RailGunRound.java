package game_gys3.weapons;

import game_gys3.characters.Enemy;
import jgame.JGTimer;
import jgame.platform.StdGame;

public class RailGunRound extends Bullet {

	private final static String SPRITE = "tennis";

	public RailGunRound(double newX, double newY, StdGame newGameEngine) {
		super("railgun", true, // name
				newX, newY, // pos
				2, // cid
				SPRITE, // sprite
				0, -FAST_CALIBER, // vector speed
				newGameEngine, // engine
				FAST_CALIBER,
				FAST_FPR
				);
	}

	public void move() {
		x -= game.random(-5, 5);
		if (y < -16)
			remove();
	}

}