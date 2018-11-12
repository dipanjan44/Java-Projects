package game_gys3.weapons;

import jgame.JGTimer;
import jgame.platform.StdGame;

public class SparkleShell extends Bullet {

	private final static String SPRITE = "explo";

	public SparkleShell(double newX, double newY, StdGame newGameEngine) {
		super("wave_shell", true, // name
				newX, newY - 16, // pos
				2, // cid
				SPRITE, // sprite
				0, -SLOW_CALIBER, // vector speed
				newGameEngine, // engine
				SLOW_CALIBER, MEDIUM_FPR);

		new JGTimer(game.random(15, 100, 2), // timer alarms after x frames
				false, // false = restart after alarm, true = one-shot
				this // Parent. The timer will remove itself
						// automatically when the parent is removed.
		) {
			public void alarm() {
				new SparkleShell(x, y, game);
			}
		};
	}

	public void move() {
		x -= game.random(-10, 10);
		if (y < -16)
			remove();
	}

}