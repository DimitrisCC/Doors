
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Class GUI
 */
public final class MainGUI {

	private static Player currentPlayer;
	private static Board currentGame;
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
	
		currentPlayer = Player.GREEN;
		currentGame = new Board();
		gameFrame = new BackgammonFrame(currentGame);
		gameFrame.setVisible(true);
		playGame(currentGame);
			
	}
	
	private static void playGame(Board currentGame) {
		
		
		while(currentGame.getWinner() == Player.NONE){
			if(currentPlayer == Player.GREEN){
				gameFrame.getGamePanel().setRoll(true);
				gameFrame.setPlayer(currentPlayer);
				//if(gameFrame.getGamePanel().getGameboard().getEaten()[0]){
					//gameFrame.getGamePanel().
				//}
				while(gameFrame.getGamePanel().isMyTurn()) System.out.print("");
				currentPlayer = Player.RED;
			}else{
				gameFrame.getGamePanel().setRoll(false);
				gameFrame.setPlayer(currentPlayer);
				int n = currentPlayer.getSign();
				currentGame.getDice().roll();
				gameFrame.getContentPane().repaint();
				System.out.println("HEARE");
				gameFrame.getGame().makeTotalMove(Minimax.MinimaxAlgorithm(gameFrame.getGame(), currentGame.getDice(), Player.RED), n);
				gameFrame.getContentPane().repaint();
				gameFrame.getGamePanel().setMyTurn(true);
				gameFrame.getGamePanel().setPlayerRolled(false);
				currentPlayer = Player.GREEN;
				gameFrame.getGamePanel().getStatusBar().setStatus("Your turn, man. Opponent's done.");
				gameFrame.getGamePanel().getStatusBar().clearDice();
			}
			
			if(gameFrame.getGame().isTerminal()){
				gameFrame.getGamePanel().getStatusBar().setStatus("END OF GAME!!! "+gameFrame.getGame().getWinner()+" is the WINNER!!!");
				gameFrame.getGamePanel().getStatusBar().clearDice();
			}
		}
		
	}
	
}
