package game_gys3.factories;

import game_gys3.weapons.StarShot;
import jgame.platform.StdGame;

public class ForkedFactory extends BulletFactory {

	@Override
	public int getFPR() {
		return StarShot.getFPR();
	}

	@Override
	public void createRound(double x, double y, StdGame engine) {
		new StarShot(x, y, engine);
	}

}
