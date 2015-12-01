


/**************************************
 * MEMBERS
 * ----------------------------------
 * Dimaki Georgia 3130052
 * Kolokathi Fotini 3090088
 * Papatheodorou Dimitrios 3130162
 * ************************************
 */

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A "static" class the implements the minimax algorithm.
 * It uses a probabilistic pruning method.
 */

public final class Minimax {
	
	private static final int MAX_LENGTH = 1; //first level 0 //1 means 2 levels
	private static final boolean PRUNE = false; //use pruning or not //if true, MAX_LENGTH may be higher but be careful
	private static final int INF = 100000;
	private static ArrayList<Dice> possibleRolls; 

	private Minimax(){}

	/**
	 * Computes the best Move to be made to be made for the current game board with the given dice and the given player
	 * Basic algorithm of the game
	 * */
	public static Move MinimaxAlgorithm(Board root, Dice d, Player player) 
	{
		possibleRolls = Dice.allPossibleRolls(); //initialize possibleRolls
		Move bestMove = maxValue(root, d, 0, player, Evaluation.Vmin, Evaluation.Vmax);

		return bestMove;
	}
	
	/**
	 * Computes the best move MIN can make on a current game
	 * @param b the board on which the computations will be made
	 * @param d the current dice of the game
	 * @param p the player (always GREEN for Min)
	 * @param treeLength the current length of the tree
	 * @param alpha a value for pruning
	 * @param beta b value for pruning
	 * @return the best Move min player can make
	 * */
	private static Move minValue(Board b, Dice d, int treeLength, Player player, int alpha, int beta){
		if(treeLength == MAX_LENGTH){
			return new Move(b.getLastPlayedMove().getMove(),  Evaluation.boardScore(b,1)-Evaluation.boardScore(b,0)); 
		}
		
		Move min = new Move(INF);
		HashSet<Board> succ = b.getChildren(d, player);
		int value;
		for(Board current : succ){
			value = chanceValue(current, d, player.getOpponent(), treeLength, alpha, beta);
			if (value < min.getScore()){
				min.setMove(current.getLastPlayedMove().getMove());
				min.setScore(value);
			}
		}
		return min;
	}
	

	/**
	 * Computes the best move MAX can make on a current game
	 * @param b the board on which the computations will be made
	 * @param d the current dice of the game
	 * @param p the player (always RED for Max)
	 * @param treeLength the current length of the tree
	 * @param alpha a value for pruning
	 * @param beta b value for pruning
	 * @return the best Move max player can make
	 * */
	private static Move maxValue(Board b, Dice d, int treeLength, Player player, int alpha, int beta){
		if(treeLength == MAX_LENGTH){
			return new Move(b.getLastPlayedMove().getMove(), Evaluation.boardScore(b,1)-Evaluation.boardScore(b,0)); 
		}
		
		Move max = new Move(-INF);
		HashSet<Board> succ = b.getChildren(d, player);
		int value;
		for(Board current : succ){
			value = chanceValue(current, d, player.getOpponent(), treeLength, alpha, beta);
			if (value > max.getScore()) {
				max.setMove(current.getLastPlayedMove().getMove());
				max.setScore(value);
			}
		}
		return max;
	} 
	
	/**
	 * Computes the expected score of all the best opponent's choices for each probable value of the dice
	 * @param b the board on which the computations will be made
	 * @param d the current dice of the game
	 * @param p the player for whom it computes the expected score
	 * @param treeLength the current length of the tree
	 * @param alpha a value for pruning
	 * @param beta b value for pruning
	 * @return the expected value of the score
	 * */
	private static int chanceValue(Board b, Dice d, Player P, int treeLength, int alpha, int beta){
		float expectedValue = 0; 
		float s = 0;
		float p = 0; 
		float currentP = 0;
		int roundedValue = 0;
		
		if(P == Player.RED){ 
			
			for(Dice roll: possibleRolls){
				currentP = (float) 1/(roll.isDouble()? 36:18);
				Move max = maxValue(b, roll, treeLength+1, P, alpha, beta);
				s += max.getScore()*currentP;
				
				if(PRUNE){
					p += currentP;
					expectedValue = s + (1-p)*Evaluation.Vmax;
					roundedValue = Math.round(expectedValue); 
					if(roundedValue < alpha) {
						return roundedValue;
					}
					if(roundedValue > alpha) alpha = roundedValue;
				}
			}
			
		}else if(P == Player.GREEN){
			
			for(Dice roll: possibleRolls){
				currentP  = (float) 1/(roll.isDouble()? 36:18);
				Move min = minValue(b, roll, treeLength+1,P, alpha, beta);
				s += min.getScore()*currentP;
				
				if(PRUNE){
					p += currentP;
					expectedValue = s + (1-p)*Evaluation.Vmin;
					roundedValue = Math.round(expectedValue);
					if(roundedValue > beta) {
						return roundedValue;
					}
					if(roundedValue < beta) beta = roundedValue;
				}
			}
			
		}
		
		if(!PRUNE) roundedValue = Math.round(s);
		
		return roundedValue; 
	}
	

}