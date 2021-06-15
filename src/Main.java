import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame {
	private Point point = new Point();
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	int screen_height = (int) size.getHeight();
	int screen_width = (int) size.getWidth();
	int k_height = (int) (screen_width * 0.1);
	int k_width =  k_height;
	
	boolean is_falling = false;
	
	private ArrayList<BufferedImage> kirby_frames = new ArrayList<BufferedImage>();
	
	public Main() {
		initUI();
	}

	private void initUI() {
		setUndecorated(true);
		// setTitle("Simple example");
		setSize(k_height, k_width);
		setLocationRelativeTo(null);
		setLocation(0, screen_height - k_height);
        setBackground(new Color(0, 0, 0, 0));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		populateFrames();
		add(new JLabel(new ImageIcon(kirby_frames.get(1).getScaledInstance(k_width, k_height, Image.SCALE_FAST))));
		
		addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
		
		addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });
	}
	
	private void populateFrames() {
		try {
			for (int i = 1; i < 10; i++) {
				kirby_frames.add(ImageIO.read(new File(String.format("img/kirby0%s.png", i))));
			} 
			
			for (int i = 10; i < 18; i++) {
				kirby_frames.add(ImageIO.read(new File(String.format("img/kirby%s.png", i))));
			}
		} catch (IOException ex) {
			// bruh
		}
	}
	


	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			var ex = new Main();
			ex.setVisible(true);
		});
	}
}