import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BackgammonFrame extends JFrame implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = -1419452740845309834L;
	private Board game;
	private StatusBar statusBar;

	/**
	 * Create the application.
	 */
	public BackgammonFrame(Board game) {
		super("Backgammon Doors");
		this.game = game;
		initialize();
	}

	/**
	 * Initialize the contents of the 
	 */
	private void initialize() {
		BackgammonPanel panel;
		Container container;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 690);
		setResizable(false);
		container = getContentPane();
		container.setLayout(new BorderLayout());
		
		panel = new BackgammonPanel(game);
		container.add(panel);
		statusBar = new StatusBar();
		container.add(statusBar, BorderLayout.SOUTH);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setVisible(true);
		repaint();
		update();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		update();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void update(){
		statusBar.setText("yolo");
		repaint();
	}
}
