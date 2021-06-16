import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	int screen_height = (int) size.getHeight();
	int screen_width = (int) size.getWidth();
	int k_height = (int) (screen_width * 0.1);
	int k_width =  k_height;
	
	public MainFrame() {
		initUI();
	}
	
	public void initUI() {
		setUndecorated(true);
		add(new SpriteHandler(this));
		setResizable(false);
		pack();
		
		
		setLocation(0, screen_height - k_height);
		
		setLocationRelativeTo(null);
		setBackground(new Color(0, 0, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			MainFrame m = new MainFrame();
			m.setVisible(true);
		});
	}

}
