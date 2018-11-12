package game_gys3.factories;

import game_gys3.weapons.MissileRound;
import jgame.platform.StdGame;

public class CupcakeMissileFactory extends BulletFactory {

	@Override
	public int getFPR() {
		return MissileRound.getFPR();
	}

	@Override
	public void createRound(double x, double y, StdGame engine) {
		new MissileRound(x,y,engine);
	}

}
