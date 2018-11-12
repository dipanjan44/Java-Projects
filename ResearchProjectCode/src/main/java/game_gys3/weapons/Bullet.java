package game_gys3.weapons;

import java.util.Random;

import jgame.JGObject;
import jgame.platform.StdGame;

public abstract class Bullet extends JGObject {
	public static final int DEFAULT_PLAYER_BULLET_CID = 2;
	public static final int DEFAULT_ENEMY_BULLET_CID = 1;
	public final static double SLOW_CALIBER = 5;
	public final static double MEDIUM_CALIBER = 20;
	public final static double FAST_CALIBER = 40;
	public final static int SLOW_FPR = 100;
	public final static int MEDIUM_FPR = 18;
	public final static int FAST_FPR = 8;
	public final static int MAX_FPR = 1;


	class Velocity {
		double xVelocity = 0;
		double yVelocity = 0;

		Velocity(double x, double y) {
			xVelocity = x;
			yVelocity = y;
		}
	}

	protected static int frames_per_round;
	protected static double caliber;

	public static int getFPR() {
		return frames_per_round;
	}

	protected StdGame game;

	public Bullet(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, double xspeed, double yspeed,
			StdGame newGame, double newMagnitude, int newFPR) {
		super(name, unique_id, x, y, collisionid, gfxname, xspeed, yspeed,
				JGObject.expire_off_pf);
		setAttributes(newGame,newMagnitude,newFPR);
	}

	protected Velocity createDiagonalVector(double magnitude,
			boolean xPositive, boolean yPositive) {
		double xVelo, yVelo;
		double rootMag = Math.pow(Math.pow(magnitude, 2) / 2, .5);
		xVelo = (!xPositive) ? rootMag : -rootMag;
		yVelo = (!yPositive) ? rootMag : -rootMag;
		return new Velocity(xVelo, yVelo);
	}

	protected Velocity createRandomVector(double magnitude) {
		double xVelo = game.random(-magnitude, magnitude);
		double yVelo = (new Random().nextInt(2) * 2 - 1)
				* Math.pow(Math.pow(magnitude, 2) - Math.pow(xVelo, 2), 0.5);
		return new Velocity(xVelo, yVelo);
	}

	public void hit(JGObject obj) {
		remove();
	}

	public void hit_bg(int tilecid) {
		remove();
	}

	public void hit_bg(int tilecid, int tx, int ty, int txsize, int tysize) {
		for (int x = 0; x < txsize; x++) {
			for (int y = 0; y < tysize; y++) {
				int thiscid = game.getTileCid(tx + x, ty + y);
				game.setTile(tx + x, ty + y, "");
				if (thiscid >= 1 && thiscid < 7) {
					game.setTile(tx + x, ty + y, "m" + (thiscid + 1));
				}
				if (thiscid >= 10 && thiscid < 15) {
					game.setTile(tx + x, ty + y, "b" + (thiscid + 1));
				}
			}
		}
	}

	protected void setAttributes(StdGame newGame, double newMagnitude,
			int newFPR) {

		setGame(newGame);
		setCaliber(newMagnitude);
		setFPR(newFPR);
	}

	
	private void setFPR(int newFPR) {
		frames_per_round = newFPR;
	}

	public void setCaliber(double newCaliber) {
		caliber = newCaliber;
	}

	protected void setGame(StdGame game) {
		this.game = game;
	}
}
