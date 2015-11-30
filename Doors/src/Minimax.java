import java.util.ArrayList;
import java.util.HashSet;

/**
 * A "static" class the implements the minimax algorithm.
 * It uses a probabilistic pruning method.
 */

public final class Minimax {
	
	private static final int MAX_LENGTH = 1; //first level 0
	private static final int INF = 100000;
	private static ArrayList<Dice> possibleRolls; 
	private static int LastLevelChildren = 0; //DEBUG
	
	private Minimax(){}
	
	public static Move MinimaxAlgorithm(Board root, Dice d, Player player) 
	{
		possibleRolls = Dice.allPossibleRolls(); //initialize possibleRolls
		Move bestMove = maxValue(root, d, 0, player, Evaluation.Vmin, Evaluation.Vmax);

		for(int i=0; i<4; i++){
			System.out.println("from "+bestMove.getMove()[i][0]+" to "+bestMove.getMove()[i][1]); //DEBUG
		}
		System.out.println("******* MINIMAX CHILDREN: " + LastLevelChildren); //DEBUG
		LastLevelChildren = 0; //DEBUG
		return bestMove;
	}
	
	private static Move minValue(Board b, Dice d, int treeLength, Player player, int alpha, int beta){
		if(treeLength == MAX_LENGTH){
			LastLevelChildren++; //DEBUG
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
	
	private static Move maxValue(Board b, Dice d, int treeLength, Player player, int alpha, int beta){
		if(treeLength == MAX_LENGTH){
			LastLevelChildren++; //DEBUG
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
	
	private static int chanceValue(Board b, Dice d, Player P, int treeLength, int alpha, int beta){
		float expectedValue = 0; 
		float s = 0;
		float p = 0; 
		float currentP = 0;
		int roundedValue = 0;
		
		if(P == Player.RED){ 
			
			for(Dice roll: possibleRolls){
				currentP  = 1/(roll.isDouble()? 36:18);
				Move max = maxValue(b, roll, treeLength+1, P, alpha, beta);
				s += max.getScore()*currentP;
				p += currentP;
				expectedValue = s + (1-currentP)*Evaluation.Vmax;
				roundedValue = Math.round(expectedValue); 
				if(roundedValue < alpha) return roundedValue; 
				if(roundedValue > alpha) alpha = roundedValue;
			}
			
		}else if(P == Player.GREEN){
			
			for(Dice roll: possibleRolls){
				currentP  = 1/(roll.isDouble()? 36:18);
				Move min = minValue(b, roll, treeLength+1,P, alpha, beta);
				s += min.getScore()*currentP;
				p += currentP;
				expectedValue = s + (1-currentP)*Evaluation.Vmin;
				roundedValue = Math.round(expectedValue);
				if(roundedValue > beta) return roundedValue;
				if(roundedValue < beta) beta = roundedValue;
			}
		}
		
		return roundedValue; 
	}
	

}