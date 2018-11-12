package characters;

import game_gys3.PresidentialRescue;
import jgame.JGObject;
import jgame.JGRectangle;
import jgame.platform.StdGame;

public class Engineer extends Enemy {
	int buildingBunker = -1;

	public Engineer(double x, double y, double xspeed, double yspeed,
			PresidentialRescue newEngine) {
		super("engineer", true, x, y, DEFAULT_CID, null, xspeed, yspeed, newEngine);
	}

	public void move() {
		if (xdir < 0)
			setGraphic("engineer_l");
		if (xdir > 0)
			setGraphic("engineer_r");
		if (x < 0 && xdir < 0)
			xdir = -xdir;
		if (y < 0 && ydir < 0)
			ydir = -ydir;
		if (x > game.pfWidth() - 16 && xdir > 0)
			xdir = -xdir;
		if (y > game.pfHeight() - 16 && ydir > 0)
			ydir = -ydir;
		if (buildingBunker == 0) {
			if (game.countObjects("engineer", 0) < 15) {
				if (game.random(0, 100, 1) == 1)
					new Engineer(x - 8, y - 8, -xspeed, yspeed - 0.05, game);

			}
		} else
			buildingBunker--;
	}


	public void hit_bg(int tilecid) {
		if (buildingBunker < 0) {
			buildingBunker = (int) game.random(100, 140);
			new Turret(x, y, game);
			//setGraphic("turret");
			setDir(0, 0);
			remove();
		}
	}

	public void hit_bg(int tilecid, int tx, int ty) {
		game.setTile(tx, ty, "");
		setPos(tx * game.tileWidth(), ty * game.tileHeight());
	}

	@Override
	public void doDeath() {
		createExplosion(x,y,2);
		remove();
		game.score += SMALL_POINTS;
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