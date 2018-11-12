package game_gys3.factories;

import game_gys3.PresidentialRescue;
import game_gys3.characters.EnemyBoss;
import game_gys3.characters.EnemyCopter;
import game_gys3.characters.EnemyTank;
import game_gys3.characters.Engineer;
import game_gys3.characters.Robot;

public class EnemyFactory {
	
	private PresidentialRescue game;
	
	public EnemyFactory(PresidentialRescue newGame) {
		game = newGame;
	}
	
	public void createBoss() {
		new EnemyBoss(game.pfWidth() / 2, game.pfHeight() / 4, 4, 1, game,
				(game.stage + 1) * 6, game.player);
	}

	public void createEnemyCopter() {
		int xpos = (game.getObject("player").x < game.pfWidth() / 2) ? game.pfWidth()
				: -game.tileWidth();
		new EnemyCopter(xpos,
				game.random(game.pfHeight() / 2, game.pfHeight() - game.tileHeight()), game.random(-1,
						1, 2) * game.random(2, game.stage / 2 + 2), game.random(-1, 1, 2),
				game);
	}

	public void createEnemyTank() {
		int xdir = game.random(-1, 1, 2);
		double xpos = (xdir < 0) ? game.random(32 * 10, game.pfWidth() - 32) : game.random(0,
				game.pfWidth() - 32 * 11);
		for (int i = 0; i < 2; i++) {
			new EnemyTank(xpos, 0, 1, game);
			xpos += 32 * xdir * game.random(2, 4);
		}

	}

	public void createEngineer() {
		int xdir = (game.getObject("player").x < game.pfWidth() / 2) ? -1 : 1;
		int xpos = xdir < 0 ? game.pfWidth() : -game.tileWidth();
		new Engineer(xpos, game.random(0, game.pfHeight() - 3 * game.tileHeight()), game.random(1,
				2) * xdir, game.random(0.05, 0.2), game);

	}

	public void createRobot() {
		int xdir = (game.getObject("player").x < game.pfWidth() / 2) ? -1 : 1;
		int xpos = xdir < 0 ? game.pfWidth() : -game.tileWidth();
		new Robot(xpos, game.random(0, game.pfHeight() / 3), xdir * 4, game);
	}
}
