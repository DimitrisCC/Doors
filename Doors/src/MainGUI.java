import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Class GUI
 */
public final class MainGUI {

	private static Player currentPlayer = Player.GREEN;
	private static Board currentGame;
	private static Dice dice;

	private MainGUI() { }

	public static void main(String[] args) {
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					currentGame = new Board();
					playGame(currentGame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void playGame(Board currentGame) {
		

		BackgammonFrame window = new BackgammonFrame(currentGame);
		window.setVisible(true);
		//GameGui guiGame = new GameGui();
		//bf = new BackgammonFrame(currentGame);
		while (currentGame.getWinner() == Player.NONE) {
			if (currentPlayer == Player.GREEN) {
				playTurn(window, Player.GREEN);
				currentPlayer = Player.RED;
			} else {
				playTurn(window, Player.RED);
				currentPlayer = Player.GREEN;
			}
		}

		window.winnerDialog();
	}
	
	private static void playTurn(BackgammonFrame gameFrame, Player player)
	{
		if(player == Player.GREEN){//--> ara paizei to pc
			int n = player.getSign();
			dice.roll();
			gameFrame.getGame().makeMove(Minimax.MinimaxAlgorithm(gameFrame.getGame(), dice, Player.GREEN), n);
			gameFrame.Repaint();
		}else{
			
		}
	}

}
