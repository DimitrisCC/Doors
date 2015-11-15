import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Class GUI
 */
public final class MainGUI {

	private static Player currentPlayer = Player.GREEN;
	private static Board currentGame;
	private static Dice dice = new Dice(); //maybe should not be here
	private static BackgammonFrame gameFrame;

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
		
		//EventQueue.invokeLater(new Runnable() {
			//public void run() {
				//try {
					currentGame = new Board();
					playGame(currentGame);
				//} catch (Exception e) {
				//	e.printStackTrace();
				//}
			//}
		//});
	}
	
	private static void playGame(Board currentGame) {
		
		gameFrame = new BackgammonFrame(currentGame);
		gameFrame.setVisible(true);
		//GameGui guiGame = new GameGui();
		//bf = new BackgammonFrame(currentGame);
		while (currentGame.getWinner() == Player.NONE) {
			if (currentPlayer == Player.GREEN) {
				playTurn(Player.GREEN);
				currentPlayer = Player.RED;
			} else {
				playTurn(Player.RED);
				currentPlayer = Player.GREEN;
			}
		}

		gameFrame.winnerDialog();
	}
	
	public static void playTurn(Player player)
	{
		if(player == Player.RED){//--> ara paizei to pc //---->>>kale o kokkinos den einai o cpu?? -->>>> oxi?
			System.out.println("REDZZZ");
			gameFrame.setPlayer(currentPlayer);
			int n = player.getSign();
			dice.roll();
			gameFrame.getGame().makeMove(Minimax.MinimaxAlgorithm(gameFrame.getGame(), dice, Player.RED), n);
			gameFrame.repaintAndUpdate("CPU played.");
		}else{
			System.out.println("GRENZNZZ");
			gameFrame.setPlayer(currentPlayer);
			new java.util.Scanner(System.in).nextLine();//??????
			
		}
	}

}
