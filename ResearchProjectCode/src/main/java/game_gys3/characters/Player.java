package game_gys3.characters;

import game_gys3.PresidentialRescue;
import game_gys3.factories.BulletFactory;
import game_gys3.factories.EnemyFactory;
import game_gys3.factories.ForkedFactory;
import game_gys3.factories.LazerFactory;
import game_gys3.factories.CupcakeMissileFactory;
import game_gys3.factories.RailGunFactory;
import game_gys3.factories.WaveGunFactory;
import jgame.JGObject;
import jgame.JGPoint;
import jgame.impl.JGEngineInterface;
import jgame.platform.StdGame;

public class Player extends JGObject {
	public static final int DEFAULT_CID = 4;
	public PresidentialRescue e;
	public int bulletId;
	public boolean invincible = false;
	public int MOVE_SPEED = 15;
	public BulletFactory bulletFactory;
	public int actionPressTimer, numFramesFiredAgo, thisLifeCounter,
			timeToWaitAfterKilled;

	public Player(PresidentialRescue engine) {
		super("player", false, engine.pfWidth() / 2, engine.pfHeight() - 4
				* engine.tileWidth(), 4, "tank_trans_anim");
		this.e = engine;

	}

	private void createBullets() {
		if (numFramesFiredAgo >= bulletFactory.getFPR()) {
			bulletFactory.createRound(x, y, e);
			numFramesFiredAgo = 0;
		}
	}

	public int getBulletId() {
		return bulletId;
	}

	public void hit(JGObject obj) {

		if (thisLifeCounter > 100) {
			createExplosion(x + 16, y + 16, 20);
			thisLifeCounter = 0;
			timeToWaitAfterKilled = 40;
			setGraphic(null);
			remove();
		}

	}

	public void hit_bg(int tilecid) {
		snapToGrid(16, 16);
	}

	public void move() {
		if (thisLifeCounter == 100) {
			if (e.cheatsEnabled)
				setGraphic("colored_tank");
			else
				setGraphic("tank");
		}

		moveInDirectionMenu();
		chooseAGunMenu();
		activateCheatMenu();
		if (e.getKey(e.key_fire)) {
			createBullets();
		}
	}

	private void activateCheatMenu() {
		if (e.getKey(57) & actionPressTimer > 10) { // press nine to
													// activate
			// all weapons and
			// invulnerability
			toggleCheats();
			actionPressTimer = 0;
		}
		if (e.getKey(76)) { // press L
			e.gameTimer = 0;
			e.stage = 5; // jump straight to the first boss stage to test boss
			e.level = 5;
		}
	}

	private void toggleCheats() {
		e.cheatsEnabled = !e.cheatsEnabled;
		if (e.cheatsEnabled) {
			setAnim("colored_tank");
		} else {
			setGraphic("tank");
		}
	}

	private void chooseAGunMenu() {
		for (int i = PresidentialRescue.KEY_1; i <= PresidentialRescue.KEY_5; i++) {
			int newBulletId = i - 48;
			if (e.getKey(i)
					&& ((e.cheatsEnabled) || (e.stage) / 2 + 1 >= newBulletId)) {
				if (bulletId == newBulletId)
					break;
				setGun(i - 48);
			}
		}
	}

	private void moveInDirectionMenu() {
		JGPoint centerPoint = getCenterTile();
		if (e.getKey(JGEngineInterface.KeyUp)
				&& (!isYAligned(MOVE_SPEED) || (e.getTileCid(centerPoint.x,
						centerPoint.y - 1) & 7) == 0))
			y -= MOVE_SPEED;
		if (e.getKey(JGEngineInterface.KeyDown)
				&& (!isYAligned(MOVE_SPEED) || (e.getTileCid(centerPoint.x,
						centerPoint.y + 1) & 7) == 0))
			y += MOVE_SPEED;
		if (e.getKey(JGEngineInterface.KeyLeft)
				&& (!isXAligned(MOVE_SPEED) || (e.getTileCid(centerPoint.x - 1,
						centerPoint.y) & 7) == 0))
			x -= MOVE_SPEED;
		if (e.getKey(JGEngineInterface.KeyRight)
				&& (!isXAligned(MOVE_SPEED) || (e.getTileCid(centerPoint.x + 1,
						centerPoint.y) & 7) == 0))
			x += MOVE_SPEED;
		if (x < 0 || x > e.pfWidth() - 32)
			snapToGrid(16, 0);
		if (y > e.pfHeight() - 32 || y <= e.pfHeight() * 0.45)
			snapToGrid(0, 16);
	}

	private void setBulletFactory(BulletFactory newBulletFactory) {
		bulletFactory = newBulletFactory;
	}

	private void setFactory() {
		if (bulletId == 1)
			setBulletFactory(new CupcakeMissileFactory());
		if (bulletId == 2) {
			setBulletFactory(new WaveGunFactory());
		}
		if (bulletId == 3) {
			setBulletFactory(new ForkedFactory());
		}
		if (bulletId == 4) {
			setBulletFactory(new RailGunFactory());
		}
		if (bulletId == 5) {
			setBulletFactory(new LazerFactory());
		}
	}

	public void setGun(int newBulletId) {
		bulletId = newBulletId;
		for (int key = PresidentialRescue.KEY_1; key <= PresidentialRescue.KEY_5; key++) {
			int index = key - 49;
			if (bulletId == index + 1) {
				e.current_gun = PresidentialRescue.guns[index];
			}
		}
		setFactory();
	}

	public void initializeGracePeriod() {
		thisLifeCounter = 0;
	}

	public void createExplosion(double x, double y, int intensity) {
		for (int i = 0; i < intensity; i++) {
			new JGObject("explo", true, x, y, 0, "explo", e.random(-5, 5),
					e.random(-5, 5), (int) e.random(10, 10 + intensity));
		}
	}
}