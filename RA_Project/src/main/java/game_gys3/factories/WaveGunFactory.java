package game_gys3.factories;

import game_gys3.weapons.SparkleShell;
import jgame.platform.StdGame;

public class WaveGunFactory extends BulletFactory {

	@Override
	public int getFPR() {
		return SparkleShell.getFPR();
	}

	@Override
	public void createRound(double x, double y, StdGame engine) {
		new SparkleShell(x, y, engine);
	}

}
