package game_gys3.factories;

import game_gys3.weapons.LazerParticle;
import jgame.platform.StdGame;

public class LazerFactory extends BulletFactory {

	@Override
	public int getFPR() {
		return LazerParticle.getFPR();
	}

	@Override
	public void createRound(double x, double y, StdGame engine) {
		
		new LazerParticle(x, y, engine);
	}

}
