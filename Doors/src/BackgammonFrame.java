/**************************************
 * MEMBERS
 * ----------------------------------
 * Dimaki Georgia 3130052
 * Kolokathi Fotini 3090088
 * Papatheodorou Dimitrios 3130162
 * ************************************
 */


import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Container;

/**
 * Builds the window frame of the game.
 */

public class BackgammonFrame extends JFrame {

	private static final long serialVersionUID = -124755586989834L;
	private Container container;
	private Board game;
	private BackgammonPanel panel;

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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 690);
		setResizable(false);
		container = getContentPane();
		container.setLayout(new BorderLayout());
		
		panel = new BackgammonPanel(game);
		container.add(panel);
		
		setVisible(true);
		repaint();
	}
	
	protected void reinitialize(Board game){
		this.game = game;
		container.remove(panel);
		panel = new BackgammonPanel(game);
		container.add(panel);
		revalidate();
		panel.setVisible(true);
		repaint();
	}

	public Board getGame() {
		return game;
	}
	
	public BackgammonPanel getGamePanel(){ return panel; }
	
}
