package game_gys3.weapons;

import game_gys3.characters.Enemy;
import jgame.platform.StdGame;

public class MissileRound extends Bullet {
	public static final String SPRITE = "cupcake";
	public MissileRound(double newX, double newY, StdGame newEngine) {
		super("missile", true, newX, newY, DEFAULT_PLAYER_BULLET_CID, SPRITE,
				0, -MEDIUM_CALIBER,
				newEngine, SLOW_CALIBER, SLOW_FPR);
	}

	public MissileRound(double newX, double newY, double xVelo, double yVelo,
			StdGame newEngine) {
		super("missile", true, newX, newY, DEFAULT_PLAYER_BULLET_CID, "bullet",
				xVelo, yVelo, newEngine, MEDIUM_CALIBER, SLOW_FPR);
	}

	// called when need to make custom cid
	public MissileRound(double newX, double newY, double xVelo, double yVelo,
			int cid, StdGame newEngine) {
		super("missile", true, newX, newY, cid, "bullet", xVelo, yVelo,
				newEngine, MEDIUM_CALIBER, SLOW_FPR);
	}

	public void move() {
		if (y < -16)
			remove();
	}

}