import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Class GUI
 */
public final class MainGUI {

	private static Player currentPlayer = Player.GREEN;
	private static Board currentGame;

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
					BackgammonFrame window = new BackgammonFrame(currentGame);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void playGame(Board currentGame) {

		//GameGui guiGame = new GameGui();
		//bf = new BackgammonFrame(currentGame);
		/*while (currentGame.getWinner() == Player.NONE) {
			if (curPl == Player.GREEN) {
				guiGame.playTurn(currentGame, currentGame.getPlayer1(), bf);
				curPl = Player.RED;
			} else {
				guiGame.playTurn(currentGame, currentGame.getPlayer2(), bf);
				curPl = Player.GREEN;
			}
		}

		bf.winnerDialog();*/
	}

}
