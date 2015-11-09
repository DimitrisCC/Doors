/**
 * Class GUI
 */
public final class MainGUI {

	private static Player currentPlayer = Player.GREEN;
	private static Board currentGame;

	private MainGUI() { }

	public static void main(String[] args) {
		currentGame = new Board();
		playGame(currentGame);
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
