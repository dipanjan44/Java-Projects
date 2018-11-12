package characters;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import game_gys3.PresidentialRescue;
import game_gys3.weapons.Bullet;
import jgame.JGObject;

public abstract class Enemy extends JGObject {
	public static final int DEFAULT_CID = 16;
	public static final int SMALL_POINTS = 5;
	public static final int MED_POINTS = 20;
	public static final int LARGE_POINTS = 50;

	public void setEngine(PresidentialRescue game) {
		this.game = game;
	}
	

	protected PresidentialRescue game;

	public void hit(JGObject obj) {
		if (obj.colid != Player.DEFAULT_CID || !game.cheatsEnabled) {
			obj.hit(this);
		}
		doDeath();
	}

	public abstract void doDeath();

	public Enemy(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, double xspeed, double yspeed,
			int expiry, PresidentialRescue game) {
		super(name, unique_id, x, y, collisionid, gfxname, xspeed, yspeed,
				expiry);

		setEngine(game);
	}

	public Enemy(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, double xspeed, double yspeed,
			PresidentialRescue game) {
		super(name, unique_id, x, y, collisionid, gfxname, xspeed, yspeed);

		setEngine(game);
	}

	public Enemy(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, int xdir, int ydir, double xspeed,
			double yspeed, int expiry, PresidentialRescue game) {
		super(name, unique_id, x, y, collisionid, gfxname, xdir, ydir, xspeed,
				yspeed, expiry);

		setEngine(game);
	}

	public Enemy(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, int tilebbox_x, int tilebbox_y,
			int tilebbox_width, int tilebbox_height, double xspeed,
			double yspeed, int expiry, PresidentialRescue game) {
		super(name, unique_id, x, y, collisionid, gfxname, tilebbox_x,
				tilebbox_y, tilebbox_width, tilebbox_height, xspeed, yspeed,
				expiry);

		setEngine(game);
	}

	public Enemy(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, int tilebbox_x, int tilebbox_y,
			int tilebbox_width, int tilebbox_height, double xspeed,
			double yspeed, PresidentialRescue game) {
		super(name, unique_id, x, y, collisionid, gfxname, tilebbox_x,
				tilebbox_y, tilebbox_width, tilebbox_height, xspeed, yspeed);

		setEngine(game);
	}

	public Enemy(String name, boolean unique_id,
			double x,
			double y, // scorpion
			int collisionid, String gfxname, int tilebbox_x, int tilebbox_y,
			int tilebbox_width, int tilebbox_height, int xdir, int ydir,
			double xspeed, double yspeed, int expiry, PresidentialRescue game) {
		super(name, unique_id, x, y, collisionid, gfxname, tilebbox_x,
				tilebbox_y, tilebbox_width, tilebbox_height, xdir, ydir,
				xspeed, yspeed, expiry);

		setEngine(game);
	}

	public Enemy(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, int tilebbox_x, int tilebbox_y,
			int tilebbox_width, int tilebbox_height, int expiry, PresidentialRescue game) {
		super(name, unique_id, x, y, collisionid, gfxname, tilebbox_x,
				tilebbox_y, tilebbox_width, tilebbox_height, expiry);

		setEngine(game);
	}

	public Enemy(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, int tilebbox_x, int tilebbox_y,
			int tilebbox_width, int tilebbox_height, PresidentialRescue game) {
		super(name, unique_id, x, y, collisionid, gfxname, tilebbox_x,
				tilebbox_y, tilebbox_width, tilebbox_height);

		setEngine(game);
	}

	public Enemy(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, int expiry, PresidentialRescue game) {
		super(name, unique_id, x, y, collisionid, gfxname, expiry);

		setEngine(game);
	}

	public Enemy(String name, boolean unique_id, double x, double y,
			int collisionid, String gfxname, PresidentialRescue game) {
		super(name, unique_id, x, y, collisionid, gfxname);

		setEngine(game);
	}

	public void createExplosion(double x, double y, int intensity) {
		for (int i = 0; i < intensity; i++) {
			new JGObject("explo", true, x, y, 0, "explo", game.random(-5, 5),
					game.random(-5, 5), (int) game.random(10, 10 + intensity));
		}
	}
	
	
	
	public void popup(int score)
	{
		
				if(score==30||score==60||(score>80 && (score%10==0))){
				String message = "You got a new notification message. Isn't it awesome to have such a notification message.";
				
				String currentRoom;
				String[] rooms = {"You got a new notification message. Isn't it awesome to have such a notification message.",
						 "More exiting news! your getting distracted!", "Lets keep going!", "Lets concentrate on the game!"};
				Random rand = new Random();
				currentRoom = rooms [rand.nextInt( rooms.length)];

				
				String header = "This is header of notification message";
				
				final JFrame frame = new JFrame();
				JButton cloesButton = new JButton(new AbstractAction("X") {
					
				       public void actionPerformed(final ActionEvent e) {
			
			               frame.dispose();
			            
			
			        }
			
				});
				

				frame.setSize(300,125);
				frame.setUndecorated(true);
				
				frame.setLayout(new GridBagLayout());
				frame.setLocationRelativeTo(null) ;
				
				GridBagConstraints constraints = new GridBagConstraints();
				
				constraints.gridx = 0;
				
				constraints.gridy = 0;
				
				constraints.weightx = 1.0f;
				
				constraints.weighty = 1.0f;
			
				constraints.insets = new Insets(5, 5, 5, 5);
				
				constraints.fill = GridBagConstraints.BOTH;
				
				JLabel headingLabel = new JLabel(header);
				
								
				headingLabel.setOpaque(false);
				
				frame.add(headingLabel, constraints);
				
				constraints.gridx++;
				
				constraints.weightx = 0f;
				
				constraints.weighty = 0f;
				
				constraints.fill = GridBagConstraints.NONE;
				
				constraints.anchor = GridBagConstraints.NORTH;
				
				//JButton cloesButton = new JButton("X");
				
				cloesButton.setMargin(new Insets(1, 4, 1, 4));
				
				cloesButton.setFocusable(false);
				
				frame.add(cloesButton, constraints);
				
				constraints.gridx = 0;
				
				constraints.gridy++;
				
				constraints.weightx = 1.0f;
				
				constraints.weighty = 1.0f;
				
				constraints.insets = new Insets(5, 5, 5, 5);
				
				constraints.fill = GridBagConstraints.BOTH;
				
				JLabel messageLabel = new JLabel("<html>"+currentRoom+"</html>");
			
				frame.add(messageLabel, constraints);
				
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
				frame.setVisible(true);
				
				
				}
				
				
				
				
				
	}


}