import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

enum State {
	FALLING,
	WALKING,
	SLEEPING,
	SWINGING
}

public class SpriteHandler extends JPanel implements ActionListener {
	private Point point = new Point();
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	int screen_height = (int) size.getHeight();
	int screen_width = (int) size.getWidth();
	int k_height = (int) (screen_width * 0.1);
	int k_width =  k_height;
	State state = State.FALLING;
	
	int sleep_state = 0;
	int walk_state = 0;
	int swing_state = 0;
	
	Random rand = new Random();
	
	private final int DELAY = 50; 
	private Timer timer;
	
	boolean is_falling = false;
	
	private ArrayList<BufferedImage> kirby_frames = new ArrayList<BufferedImage>();
	private JLabel current_frame;
	private JFrame parent;
	
	public SpriteHandler(final JFrame parent) {
		this.parent = parent;
		initUI();
		// loop();
	}

	private void initUI() {
		setSize(k_height, k_width);
		populateFrames();
		current_frame = new JLabel(new ImageIcon(kirby_frames.get(1).getScaledInstance(k_width, k_height, Image.SCALE_FAST)));
		add(current_frame);
		setBackground(new Color(0, 0, 0, 0));
		
		parent.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	state = State.SWINGING;
                point.x = e.getX();
                point.y = e.getY();
            }
            
            public void mouseReleased(MouseEvent e) {
            	state = State.FALLING;
            }
        });
		
		parent.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = parent.getLocation();
                parent.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
                state = State.SWINGING;
            }
        });
		
		
		timer = new Timer(DELAY, this);
		timer.start();
		
		// paint();
	}
	
	private void populateFrames() {
		try {
			for (int i = 1; i < 10; i++) {
				kirby_frames.add(ImageIO.read(new File(String.format("img/kirby0%s.png", i))));
			} 
			
			for (int i = 10; i < 17; i++) {
				kirby_frames.add(ImageIO.read(new File(String.format("img/kirby%s.png", i))));
			}
		} catch (IOException ex) {
			System.out.println("Files did not load");
			ex.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//decideState();
		switch(state) {
		case FALLING:
			int new_y_pos = parent.getY() + 15;
			/*
			if (new_y_pos > screen_height - k_height) {
				new_y_pos = screen_height - k_height;
				state = State.WALKING_1;
			}
			*/
			parent.setLocation(parent.getX(), new_y_pos);
			break;
		case WALKING:
			int walk_amt = rand.nextInt(15);
			int walk_times = rand.nextInt(5) + 1;
			int walk_direction = rand.nextInt(2);
			walk_state = walk_amt;
			if (walk_direction == 0)
				walk_amt *= -1;
			for (int i = 0; i < walk_times; i++) {
				walk(walk_amt);
			}
			break;
		case SLEEPING:
			// sleep_state = rand.nextInt(10) + 5;
			if (sleep_state % 2 == 0) {
				setIconOfSprite(8);
			} else {
				setIconOfSprite(9);
			}
			break;
		case SWINGING:
			switch(swing_state) {
			case 0: 
			case 1:
				setIconOfSprite(5);
				swing_state++;
				break;
			case 2: 
			case 3:
				setIconOfSprite(6);
				swing_state++;
				break;
			case 4:
			case 5:
				setIconOfSprite(7);
				swing_state--;
				break;
			}
		}
		
		repaint();
		decideState();
	}
	
	private void wait(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			System.out.println("Thread interrupted");
		}
	}
	private void walk(int amt) {
		if (amt == 0) {
			return;
		} else if (amt > 0) {
			if (parent.getX() + amt > screen_width - k_width) {
				amt = 0;
			}
			
			if (walk_state % 2 == 0) {
				setIconOfSprite(2);
				repaint();
				parent.setLocation(parent.getX() + amt,  parent.getY());
				setIconOfSprite(1);
				repaint();
			} else {
				setIconOfSprite(1);
				repaint();
				parent.setLocation(parent.getX() + amt,  parent.getY());
				setIconOfSprite(2);
				repaint();
			}
		} else {
			if (parent.getX() + amt < 0) {
				amt = 0;
			}
			if (walk_state % 2 == 0) {
				setIconOfSprite(13);
				repaint();
				parent.setLocation(parent.getX() + amt,  parent.getY());
				setIconOfSprite(12);
				repaint();
			} else {
				setIconOfSprite(12);
				repaint();
				parent.setLocation(parent.getX() + amt,  parent.getY());
				setIconOfSprite(13);
				repaint();
			}
		}
		walk_state--;
	}
	
	private void decideState() {
		int x_pos = (int) getLocationOnScreen().getX();
		int y_pos = (int) getLocationOnScreen().getY();
		
		if (state == State.SWINGING) {
			return;
		}
		
		if (y_pos < screen_height - k_height) {
			state = State.FALLING;
			current_frame.setIcon(new ImageIcon(kirby_frames.get(4).getScaledInstance(k_width, k_height, Image.SCALE_FAST)));
			return;
		}
		
		if (y_pos >= screen_height - k_height && state == State.FALLING) {
			state = State.WALKING;
			current_frame.setIcon(new ImageIcon(kirby_frames.get(1).getScaledInstance(k_width, k_height, Image.SCALE_FAST)));
			return;
		}
		
		if (state == State.WALKING) {
			int sleep = rand.nextInt(21);
			if (sleep > 17) {
				state = State.SLEEPING;
				sleep_state = rand.nextInt(30) + 10;
				return;
			} else {
				state = State.WALKING;
				setIconOfSprite(1);
				return;
			}
		}
		
		if (state == State.SLEEPING) {
			if (sleep_state > 0) {
				state = State.SLEEPING;
				sleep_state--;
				return;
			}
			state = State.WALKING;
			setIconOfSprite(1);
			return;
		}
		
	}
	
	private void setIconOfSprite(int sprite_num) {
		current_frame.setIcon(new ImageIcon(kirby_frames.get(sprite_num).getScaledInstance(k_width, k_height, Image.SCALE_FAST)));
	}
	
}