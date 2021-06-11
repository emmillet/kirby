import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UndecoratedFrame {
    private static Point point = new Point();
    

    public static void main(String[] args) {
        final JFrame frame = new JFrame();
        //Graphics g = new Graphics();

        // Disables or enables decorations for this frame. By setting undecorated
        // to true will remove the frame's title bar including the maximize,
        // minimize and the close icon.
        frame.setUndecorated(true);

        // As the the frame's title bar removed we need to close out frame for
        // instance using our own button.
        JButton button = new JButton("Close Me");
        button.addActionListener(e -> System.exit(0));

        // The mouse listener and mouse motion listener we add here is to simply
        // make our frame draggable.
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = frame.getLocation();
                frame.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
            }
        });

        frame.setSize(300, 300);
        frame.setLocation(200, 200);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setLayout(new BorderLayout());
        

        frame.getContentPane().add(button, BorderLayout.NORTH);
        frame.getContentPane().add(new JLabel("Drag Me", JLabel.CENTER), 
            BorderLayout.CENTER);
        frame.setVisible(true);
    }
}