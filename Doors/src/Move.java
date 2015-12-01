


/**
 * Represents a conceptually total move
 */

public class Move {

	private int[][] move;
	private int score;
	
	public Move()
	{
		move = new int[4][2];
		for(int i=0; i<move.length; i++)
			for(int j=0; j<move[0].length; j++) move[i][j] = -99; //invalid values when it's not used yet
		score = 0;
	}
	
	public Move(int[][] moves)
	{
		move = new int[4][2];
		setMove(moves);
		score = 0;
	}
	
	
	public Move(int value)
	{
		move = new int[4][2];
		for(int i=0; i<move.length; i++)
			for(int j=0; j <move[0].length ; j++) 
				move[i][j] = -99;
		score = value;
	} 
	
	public Move(int[][] moves, int value)
	{
		move = new int[4][2];
		setMove(moves);
		score = value;
	}
	
	public Move(Move move){
		setMove(move.getMove());
		score = 0;
	}
	
	public int[][] getMove(){ return move; } 
	
	public int getScore(){ return score; }
	
	public void setMove(int[][] moves)
	{
		for(int i=0; i<move.length; i++){
			for(int j=0;  j < move[0].length; j++)
				move[i][j] = moves[i][j];
		}
	}
	
	public void setScore(int value)
	{
		score = value;
	}
	
	public static void resetMove(int[][] theMove, int fromWhere){
		for(int i=fromWhere; i < theMove.length; ++i){
			theMove[i][0] =  -99;
			theMove[i][1] = -99;
		}
	}

}
