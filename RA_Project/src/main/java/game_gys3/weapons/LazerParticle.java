package game_gys3.weapons;

import game_gys3.characters.Enemy;
import jgame.platform.StdGame;

public class LazerParticle extends Bullet {

	

	public LazerParticle(double newX, double newY, StdGame newGameEngine) {
		super("lazer", true, // name
				newX, newY, // pos
				2, // cid
				"nyan", // sprite
				0, -FAST_CALIBER, // vector speed
				newGameEngine, // engine
				FAST_CALIBER,
				MAX_FPR
				);
	}

	public void move() {
		x -= game.random(-.1, .1);
		if (y < -16)
			remove();
	}

}