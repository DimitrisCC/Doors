
public class Move {
	

	private int[] move;
	private int score;
	
	public Move()
	{
		move = new int[4];
		for(int i=0; i<move.length; i++) move[i] = -1;
		score = 0; //prepei na skeftw ena invalide noumero gia edw
	}
	
	public Move(int[] moves)
	{
		move = new int[4];
		setMove(moves);
		score = 0;
	}
	
	public Move(int value)
	{
		move = new int[4];
		for(int i=0; i<move.length; i++) move[i] = -1;
		score = value;
	}
	
	public Move(int[] moves, int value)
	{
		move = new int[4];
		setMove(moves);
		score = value;
	}
	
	public int[] getMove(){ return move; } 
	
	public int getScore(){ return score; }
	
	public void setMove(int[] moves)
	{
		for(int i=0; i<move.length; i++){
			move[i] = moves[i];
		}
	}
	
	public void setScore(int value)
	{
		score = value;
	}

}
