
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
		
		playGame(false);
	}
	
	/**
	 * Let's play the f*cking game!!!!
	 */
	protected static void playGame(boolean replay) {
		if(replay){
			currentGame = new Board();
			gameFrame.reinitialize(currentGame);
		} else {
			currentPlayer = Player.GREEN;
			currentGame = new Board();
			gameFrame = new BackgammonFrame(currentGame);
		}
	
		BackgammonPanel panel = gameFrame.getGamePanel();
		
		while(currentGame.getWinner() == Player.NONE){
					
			if(currentPlayer == Player.GREEN){
				panel.setRoll(true);
				while(panel.isMyTurn()) System.out.print("");
				currentPlayer = Player.RED;
			}else{
				panel.setRoll(false);
				int n = currentPlayer.getSign();
				currentGame.getDice().roll();
				gameFrame.getContentPane().repaint();
				gameFrame.getGame()
					.makeTotalMove(Minimax.MinimaxAlgorithm(gameFrame.getGame(), currentGame.getDice(), Player.RED), n);
				gameFrame.getContentPane().repaint();
				panel.setMyTurn(true);
				panel.setPlayerRolled(false);
				currentPlayer = Player.GREEN;
				panel.getStatusBar().setStatus("Your turn, man. Opponent's done.");
				panel.getStatusBar().clearDice();
			}
			
			if(gameFrame.getGame().isTerminal()){ //set as if(true) for replay debug
				panel.getStatusBar().setStatus("END OF GAME!!! "+gameFrame.getGame().getWinner()+" is the WINNER!!!");
				panel.getStatusBar().clearDice();
				
				panel.enableReplayButton();
				while(!panel.plsReplayMe()) System.out.print("");
				playGame(true);
			}
			
		}
		
		
	}
	
}
