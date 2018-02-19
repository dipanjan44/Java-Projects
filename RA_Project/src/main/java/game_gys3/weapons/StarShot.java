package game_gys3.weapons;

import jgame.platform.StdGame;

public class StarShot extends Bullet {

	private final static String SPRITE = "star";

	public StarShot(double newX, double newY, StdGame newGameEngine) {
		super("forked_round", true, // name
				newX, newY, // pos
				2, // cid
				SPRITE, // sprite
				0, -SLOW_CALIBER, // vector speed
				newGameEngine, // engine
				SLOW_CALIBER, FAST_FPR);

		createForkedPistolRounds();

	}

	public void move() {
		if (y < -16)
			remove();
	}

	private void createForkedPistolRounds() {
		boolean[][] directions = { { false, true }, { true, true },
				{ true, false }, { false, false } };
		Velocity velo;
		for (boolean[] direction : directions) {
			velo = createDiagonalVector(SLOW_CALIBER, direction[0],
					direction[1]);
			new GenericBullet("fork_bullet", x, y, 2, SPRITE, velo.xVelocity,
					velo.yVelocity, game);
		}

		new GenericBullet("fork_bullet", x, y, 2, SPRITE, 0, SLOW_CALIBER,
				game);

	}
}