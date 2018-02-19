package game_gys3.characters;

import game_gys3.PresidentialRescue;
import jgame.JGObject;
import jgame.platform.StdGame;

public class Robot extends Enemy {
	private final static double DEFAULT_SPEED = 1;

	public Robot(double x, double y, int dir, PresidentialRescue newGame) {
		super("robot", true, x, y, DEFAULT_CID, null, newGame);
		setSpeedAbs(dir * .2, 0);
	}

	public void move() {
		setRobotAnimation();
		if (y < game.pfHeight()*(2/3)) {
			y += DEFAULT_SPEED;
		} else {
			y += (2.0 - (y - (game.pfHeight() - 200)) / 200.0 )/6;
		}
		
		if (x < 0)
			xdir = 1;
		if (x > game.pfWidth() - 64)
			xdir = -1;
	}

	private void setRobotAnimation() {
		if (xdir < 0) {
			setAnim("robot_l");
		} else {
			setAnim("robot_r");
		}
	}

	public void doDeath() {
		createExplosion(x,y,8);
		remove();
		game.score += LARGE_POINTS;
		//pop-up for experimental grp2
				if(PresidentialRescue.control >= 0.5 && PresidentialRescue.control <= 0.75)
				{
				popup(game.score);
				   
				}
				// pop-up call for last experimental grp3
				else if (PresidentialRescue.control >= 0.75)
				popup(game.score);
	}

}