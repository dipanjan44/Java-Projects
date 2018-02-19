
/**
 * @author Dipanjan
 * This is the driver program for the game
 */
package game_gys3;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.util.IOUtils;
import org.apache.logging.log4j.spi.LoggerContext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

import game_gys3.characters.Enemy;
import game_gys3.characters.EnemyBoss;
import game_gys3.characters.EnemyCopter;
import game_gys3.characters.Player;
import game_gys3.factories.EnemyFactory;
import game_gys3.weapons.Bullet;
import jgame.JGColor;
import jgame.JGFont;
import jgame.JGPoint;
import jgame.JGTimer;
import jgame.platform.StdGame;
public class PresidentialRescue extends StdGame {
	private static final Logger log4j = LogManager
			.getLogger(PresidentialRescue.class.getName());
	private static final int STAGE_DURATION = 500;
	public static final int DEFAULT_GUN = 1;
	public static final int KEY_1 = 49;
	public static final int KEY_5 = 53;
	public static final JGColor status_color = JGColor.black;
	public static final JGFont font_normal = new JGFont("Helvetica", 0, 30);
	public static final JGFont font_large = new JGFont("Helvetica", 0, 80);
	public static final String[] guns = { "Cupcake Cannon", "Sparkle Gun",
			"Star Missiles", "Tennis Gun", "Nyan Lazer" };
	public static double control = 0.0;
	public static int game_counter=0;

	public static void main(String[] args) throws IOException,
			NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, InterruptedException {

		control = PresidentialRescue.randomGen();
		if (control < 0.40)
			log4j.trace("This is a control-group");
		/*else if (control >= 0.25 && control <= 0.50) {
			log4j.trace("This is an exp-grp1");
		} else if (control > 0.5 && control < 0.75) {
			log4j.trace("This is an exp-grp2");
		} else if (control > 0.75 && control <= 1.00) {
			log4j.trace("This is an exp-grp3");
		}*/
		/**
		 * Modified on 01/15/2018 as per the latest requirement
		 */
		else if (control >= 0.40) {
			log4j.trace("This is an exp-grp with no distraction");
		} 

		System.out.println("Running in mode" + "  " + control);
		new PresidentialRescue(new JGPoint(768, 576));
		/**
		 * @author Dipanjan Code segment to pick up the process id of the
		 *         self-process
		 */
		java.lang.management.RuntimeMXBean runtime = java.lang.management.ManagementFactory
				.getRuntimeMXBean();
		java.lang.reflect.Field jvm = runtime.getClass()
				.getDeclaredField("jvm");
		jvm.setAccessible(true);
		sun.management.VMManagement mgmt = (sun.management.VMManagement) jvm
				.get(runtime);
		java.lang.reflect.Method pid_method = mgmt.getClass()
				.getDeclaredMethod("getProcessId");
		pid_method.setAccessible(true);

		final int pid = (Integer) pid_method.invoke(mgmt);
		// System.out.println("The current pid is " + " --> " + pid);
		/**
		 * @Dipanjan This segment is used to take the user-inputs from command
		 *           line
		 */
		final String PATH_TO_JSTACK = args[0]; // path of the jstack executable
		final String dir = args[1]; // file path of the stack trace output file
		final int interval = Integer.parseInt(args[2]);// interval for
														// stack-trace
														// collection (in
														// milliseconds)
		final String log4jPath = args[3]; // path of log4j config file

		/**
		 * @author Dipanjan This code segment is used to randonly generate and
		 *         allocate the subjectId
		 */
		Random randomLong = new Random();
		final long subjectId = randomLong.nextLong();

		// new PresidentialRescue(new JGPoint(0, 0));

		/**
		 * @author Dipanjan Get the count of processors
		 */

		int count = Runtime.getRuntime().availableProcessors();
		// System.out.println(count);
		/**
		 * @author Dipanjan Code segment to burden the cpu
		 */
		for (int i = 0; i < count - 1; i++) {
			new Thread(new Runnable() {
				public void run() {
					while (true)
						;
				}
			}).start();

			/**
			 * @author Dipanjan Code segment for the logger -log4j
			 */
			LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager
					.getContext(false);
			File file = new File(log4jPath);

			// this will force a reconfiguration
			((org.apache.logging.log4j.core.LoggerContext) context)
					.setConfigLocation(file.toURI());
		}

		/**
		 * @author Dipanjan Code-segment to call and collect the stack-trace at
		 *         specified interval
		 */
		Timer timer = new Timer();
		TimerTask collectTrace = new TimerTask() {
			@Override
			public void run() {

				try {
					PresidentialRescue.generateStackTrace(PATH_TO_JSTACK, pid,
							dir, subjectId);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		timer.schedule(collectTrace, interval, interval);

		// PresidentialRescue.excuteCommand(filePath);

	}

	/**
	 * Method to generate stack traces at regular interval
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void generateStackTrace(String PATH_TO_JSTACK, int pid,
			String dir, long subjectId) throws IOException,
			InterruptedException {
		BufferedWriter brw = null;
		FileWriter fw = null;
		BufferedReader br = null;
		try {
			String outFile = subjectId + ".txt";
			File file = new File(dir, "stacktrace_" + outFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile(), true);
			brw = new BufferedWriter(fw);
			long stackTraceStartTime = System.currentTimeMillis();
			InputStream in = Runtime.getRuntime()
					.exec(PATH_TO_JSTACK + " " + pid).getInputStream();
			long stackTraceEndTime = System.currentTimeMillis();
			br = new BufferedReader(new InputStreamReader(in));
			// capture the current time in milliseconds
			

			/**
			 * write the time-stamp at the beginning of each dump
			 */
			// brw.write(String.valueOf(stackTraceTime));
			brw.write("StartTime" + ":" + String.valueOf(stackTraceStartTime));
			brw.newLine();
			String stackTrace;
			while (((stackTrace = br.readLine()) != null)) {
				// System.out.println(stackTrace);
				brw.write(stackTrace);
				brw.newLine();
			}
			brw.write("EndTime" + ":" + String.valueOf(stackTraceEndTime));
			brw.newLine();
			

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (brw != null)
					brw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	public static double randomGen() {
		Random randno = new Random();
		double rand = randno.nextDouble();
		// int rand = (Math.random() <= 0.5) ? 1 : 2;
		return rand;
	}

	/**
	 * Commented out the method for triggering the shell script
	 */

	/*
	 * public static void excuteCommand(String filePath) throws IOException {
	 * File file = new File(filePath); if (!file.isFile()) { throw new
	 * IllegalArgumentException("The file " + filePath + " does not exist"); }
	 * if (isLinux()) { Runtime.getRuntime().exec( new String[] { "/bin/sh",
	 * "-c", filePath }, null); } else if (isWindows()) {
	 * Runtime.getRuntime().exec("cmd /c start " + filePath); } }
	 * 
	 * public static boolean isLinux() { String os =
	 * System.getProperty("os.name"); return os.toLowerCase().indexOf("linux")
	 * >= 0; }
	 * 
	 * public static boolean isWindows() { String os =
	 * System.getProperty("os.name"); return os.toLowerCase().indexOf("windows")
	 * >= 0; }
	 */

	public boolean cheatsEnabled = false;
	public Player player;
	public String current_gun = guns[0];
	public int gameTimer = 0, level = 0;
	public int stage = 0;
	public EnemyFactory enemyFactory = new EnemyFactory(this);

	private int cids_nonFlyingObjects = Bullet.DEFAULT_ENEMY_BULLET_CID
			| Bullet.DEFAULT_PLAYER_BULLET_CID | Player.DEFAULT_CID
			| Enemy.DEFAULT_CID;
	private int cids_killableByPlayerBullets = EnemyCopter.DEFAULT_CID
			| Enemy.DEFAULT_CID | EnemyBoss.DEFAULT_CID;
	private int cids_canKillPlayer = Bullet.DEFAULT_ENEMY_BULLET_CID
			| Enemy.DEFAULT_CID | EnemyBoss.DEFAULT_CID;
	private int cid_allRunOverableEnemies = Enemy.DEFAULT_CID
			| EnemyBoss.DEFAULT_CID;;
	private int cids_bullet = Bullet.DEFAULT_PLAYER_BULLET_CID;
	private int cid_player = Player.DEFAULT_CID;
	private int cid_boss = EnemyBoss.DEFAULT_CID;
	private boolean bossAlreadyCreated;

	public PresidentialRescue() {
		initEngineApplet();
	}

	public PresidentialRescue(JGPoint point) {
		initEngine(point.x, point.y);

	}

	private void createBoss() {
		enemyFactory.createBoss();
	}

	private void createFlyingEnemy() {
		enemyFactory.createEnemyCopter();
	}

	private void createLandEnemy() {
		enemyFactory.createEnemyTank();

	}

	private void createStrongerLandEnemy() {
		enemyFactory.createRobot();
	}

	private void createTurretBuilder() {
		enemyFactory.createEngineer();

	}

	public void startLevelDone() {
		gameTimer = 0;
	}

	private void defineCustomAnimations() {
		String[] frames = { "beam2", "beam3", "beam4", "beam5", "beam6",
				"beam7", "beam8", };
		defineAnimation("lazers", frames, .5);
		String[] frames2 = { "nyan2", "nyan3", "nyan4", "nyan5", "nyan6",
				"nyan7", "nyan8", };
		defineAnimation("nyan", frames2, 1);
		String[] frames3 = { "copter_l1", "copter_l2" };
		defineAnimation("copter_l", frames3, 1);
		String[] frames4 = { "copter_r1", "copter_r2" };
		defineAnimation("copter_r", frames4, 1);
		String[] frames5 = { "tank", "tank_transparent" };
		defineAnimation("tank_trans_anim", frames5, .2);
		String[] frames6 = { "fire1", "fire2", "fire3", "fire4", "fire5",
				"fire6", "fire7" };
		defineAnimation("fire_anim", frames6, .5);
	}

	private void defineRockObstacles() {
		int coordx;
		int coordy;
		for (int i = 0; i < 15 + level; i++) {
			coordx = random(0, pfTilesX(), 1);
			coordy = random(0, pfTilesY() - 1, 1);
			if (coordx != pfTilesX() / 2 && coordy != pfTilesY() - 4) {
				setTile(coordx, coordy, "m");
			}

		}
	}

	public void displayCurrentGun() {
		if (!inGameState("Title")) {
			setColor(status_color);
			/*
			 * Commented out by Dipanjan
			 */
			/*
			 * drawString(current_gun + " Equipped.", 25, pfHeight() -
			 * tileHeight() * 3, -1);
			 * 
			 * drawString("Unlocked weapons stored in keys 1-5", 25, pfHeight()
			 * - tileHeight() * 4, -1); if (cheatsEnabled) {
			 * drawString("CHEATS ENABLED", 25, pfHeight() - tileHeight() * 5,
			 * -1); }
			 */
		}

	}

	public void doFrameInGame() {

		moveAndCheckCollisons();
		incrementTimers();

		if (finishedFinalLevel()) {

			addGameState("GameWon");

			gameOver();

		} else {

			if (player.timeToWaitAfterKilled == 0) {
				lifeLost();
				log4j.trace("This is a Life-lost message.");
			}
			if (existsObject("player")) {
				if (!onBossLevel()) {
					if (gameTimer < STAGE_DURATION) {
						generateDrones();
					} else if (countObjects(null, 24) == 0)
						endLevel();
				} else {
					if (gameTimer == 1)
						createBoss();
					if (gameTimer > 1 && !existsObject("boss")) { // next level
						endLevel();
					}
				}

			}
		}

	}

	private void endLevel() {
		if (!inGameState("EndLevel")) {
			addGameState("EndLevel");
			new JGTimer(100, true, "EndLevel") {
				public void alarm() {
					setGameState("InGame");
					if (level < 10)
						level++;
					stage++;
					defineLevel();
					gameTimer = 0;
				}
			};
		}
	}

	private void generateDrones() {

		if ((gameTimer % 220 - 10 * level) == 0) {
			createFlyingEnemy();
		}
		if ((gameTimer % 380 - 15) == 20) {
			createLandEnemy();
		}
		if (stage > 4 && (gameTimer % (130 - stage)) == 0) {
			createTurretBuilder();
		}
		if (stage > 2 && (gameTimer % (160 - stage)) == 0) {
			createStrongerLandEnemy();
		}

	}

	private void incrementTimers() {
		gameTimer++;
		player.numFramesFiredAgo++;
		player.actionPressTimer++;
		player.thisLifeCounter++;
		player.timeToWaitAfterKilled--;
	}

	public void initCanvas() {
		setCanvasSettings(24, 18, 32, 32, null, null, null);
	}

	public void initGame() {
		defineMedia("gys_game.tbl");

		startgame_ingame = true;
		leveldone_ingame = true;
		lives_img = "tank";
		startgame_ticks = 0;
		gameover_ticks = 250;
		/**
		 * @added by Dipanjan for control sequence
		 */
		defineCustomAnimations();
		// Speed for control group
		if (control < 0.25) {
			setFrameRate(30, 3);
		}
		/**
		 * @author Dipanjan - commented out to stop distractions
		 */
		/*// speed for experimental grp1 w/o distraction
		else if (control >= 0.25 && control <= 0.5) {
			setFrameRate(40, 3);
		}
		// speed for slow experimental grp2 with distractions
		else if (control >= 0.5 && control <= 0.75) {
			setFrameRate(30, 3);
		}

		// fast experimental grp3 with distraction
		else if (control > 0.75 && score==0)
			setFrameRate(50, 3);*/
		
		else if (control > 0.4) {
			setFrameRate(35, 3);
		}
	}

	public void initNewLife() {
		player = new Player(this);
		player.setGun(DEFAULT_GUN);
		if (cheatsEnabled)
			player.setAnim("colored_tank");
		player.initializeGracePeriod();
		
		/**
		 * @dipanjan - commented out on 01/20/2018 
		 * 
		 */
		
		/*if (control > 0.4 && score >= 240) {
			setFrameRate(85, 3);
		}
		else if (control > 0.4 && score >= 200) {
			setFrameRate(75, 3);
		}
		else if (control > 0.4 && score >= 160) {
			setFrameRate(65, 3);
		}
		else if (control > 0.4 && score >= 120) {
			setFrameRate(55, 3);
		}
		else if (control > 0.4 && score >= 60) {
			setFrameRate(45, 3);
			
		}*/
	}

	private boolean finishedFinalLevel() {
		return (stage + 1 == 19);

	}

	public void startTitle() {
		stage = 0;
		setBGImage("empty_w");
		fillBG("");
		removeObjects(null, 64);
	}

	public void defineLevel() {
		bossAlreadyCreated = false;
		removeObjects(null, 24);
		initNewLife();
		setBGImage("empty_" + (stage % 3)); // changing background image
		fillBG("");
		defineRockObstacles();
	}

	private boolean killedBoss() {
		if (gameTimer > 1) {
			if (!existsObject("boss")) {
				return true;
			}
		}
		return false;
	}

	private void moveAndCheckCollisons() {

		moveObjects();
		checkCollision(cids_bullet, cids_killableByPlayerBullets); // enemies
																	// calling
																	// hit with
																	// player
																	// bullet
		checkCollision(cid_player, cid_allRunOverableEnemies); // ignore
																// helicopter
																// when
		// checking for vehicle to
		// vehicle

		if (!cheatsEnabled) {
			checkCollision(cids_canKillPlayer, cid_player); // enemy 1 attacks
															// me
		}
		checkBGCollision(63, cids_nonFlyingObjects);
	}

	private boolean onBossLevel() {
		return (stage + 1) % 6 == 0 && stage > 0;
	}

	public void paintFrame() {
		displayCurrentGun();
		setFont(font_normal);
		setColor(status_color);
	}

	public void paintFrameInGame() {
		displayScore();
		if (lives_img == null) {
			drawString("Lives " + lives, viewWidth() - status_r_margin, 0, 1);
		} else {
			drawCount(lives - 1, lives_img, viewWidth() - status_r_margin, 0,
					-getImageSize(lives_img).x - 2);
		}
	}

	private void displayScore() {
		drawString("Score " + score + " | Stage " + (stage + 1),
				status_l_margin, 0, -1);
	}

	public void paintFrameEndLevel() {
		displayScore();
		drawString("Stage complete!", pfWidth() / 2, pfHeight() / 3, 0);
		if (stage % 2 == 1 && stage < 10) {
			drawString(guns[(stage + 1) / 2].toUpperCase() + " Unlocked!",
					pfWidth() / 2, pfHeight() / 2, 0);
		}
	}

	public void paintFrameGameWon() {
		displayScore();
		drawString("You saved the President!!!!!!!!!!", pfWidth() / 2,
				pfHeight() * .7, 0);
		drawImage(pfWidth() / 2, pfHeight() / 2, "splash_image");
		drawImage(0, 0, "dennis");
	}

	public void paintFrameTitle() {
		setFont(font_large);
		drawImage(pfWidth() / 3 + tileWidth(), pfHeight() / 3, "splash_image");
		drawString("Save the President".toUpperCase(), pfWidth() / 2,
				pfHeight() / 3, 0);
		setFont(font_normal);
		drawString("Press " + getKeyDesc(key_startgame) + " to start",
				pfWidth() / 2, pfHeight() / 2 + tileHeight(), 0);
		drawString("Press " + getKeyDesc(key_gamesettings) + " for settings",
				pfWidth() / 2, pfHeight() / 2 + 2 * tileHeight(), 0);

	}

}
