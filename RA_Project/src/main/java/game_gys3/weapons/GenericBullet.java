package game_gys3.weapons;

import jgame.platform.StdGame;

public class GenericBullet extends Bullet {

	public final static double DEFAULT_CALIBER = 30;
	public final static int DEFAULT_FPR = 20;

	public GenericBullet(String name, double newX,
			double newY, int cid, String sprite, double xVelo, double yVelo,
			StdGame newEngine) {
		super(name, true, newX, newY, cid, sprite, xVelo, yVelo,
				newEngine, caliber, DEFAULT_FPR);

	}

	public void move() {
		if (y < -16)
			remove();
	}

}