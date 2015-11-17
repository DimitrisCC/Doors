import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Container;

public class BackgammonFrame extends JFrame {

	private static final long serialVersionUID = -1419452740845309834L;
	private Board game;
	private Player player;
	private StatusBar statusBar;
	private BackgammonPanel panel;

	/**
	 * Create the application.
	 */
	public BackgammonFrame(Board game) {
		super("Backgammon Doors");
		this.game = game;
		this.player = Player.NONE;
		initialize();
	}

	/**
	 * Initialize the contents of the 
	 */
	private void initialize() {
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
		
		setVisible(true);
		repaint();
		//update();
	}

	public void repaintAndUpdate(String status){
		panel.repaint();
	}

	public void winnerDialog() {
		// TODO Auto-generated method stub
		
	}

	public Board getGame() {
		return game;
	}
	
	public BackgammonPanel getGamePanel(){return panel;}
	
	public void setPlayer(Player p){
		player = p;
		panel.setPlayer(player);
	}
	
	public Player getPlayer(){
		return player;
	}
}
