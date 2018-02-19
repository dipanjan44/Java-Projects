package game_gys3.factories;

import jgame.platform.StdGame;

public abstract class BulletFactory {
	
	public abstract int getFPR();

	public abstract void createRound(double x, double y, StdGame engine);
	
}
