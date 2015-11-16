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
		
		//EventQueue.invokeLater(new Runnable() { //gamouse san threading idea, alla dimiourgei provlimata synchronization
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
			if (gameFrame.getGamePanel().getMyTurn()) {
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
		if(player == Player.RED){
			System.out.println("REDZZZ");
			gameFrame.setPlayer(currentPlayer);
			int n = player.getSign();
			dice.roll();
			gameFrame.getContentPane().repaint();
			gameFrame.getGame().makeMove(Minimax.MinimaxAlgorithm(gameFrame.getGame(), dice, Player.RED), n);
			gameFrame.repaintAndUpdate("CPU played.");
			gameFrame.getGamePanel().setMyTurn(true);
		}else{
			System.out.println("GRENZNZZ");
			gameFrame.setPlayer(currentPlayer);
			new java.util.Scanner(System.in).nextLine();//?????? -->>> debugging pipa
			
		}
	}

}
