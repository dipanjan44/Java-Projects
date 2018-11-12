package game_gys3.factories;

import game_gys3.weapons.RailGunRound;
import jgame.platform.StdGame;

public class RailGunFactory extends BulletFactory {

	@Override
	public int getFPR() {
		return RailGunRound.getFPR();
	}

	@Override
	public void createRound(double x, double y, StdGame engine) {
		String[] frames = {"tennis2","tennis3","tennis4"};
		engine.defineAnimation("tennis", frames, .5);
		new RailGunRound(x, y, engine);
	}

}
