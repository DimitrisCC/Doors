
public class Game {
	
	private Board gameBoard;
	private Dice dice;
	
	private Player greenP;
	private Player redP;
	
	private int[] eaten;
	

	
	 public byte[] rollDice(){
	    	return dice.roll();
	    }
	 

	    public byte[] getDice(){
	    	return dice.getValues();
	    }
	    

	    
	    public byte[] getDiceMoveset(){
	    	Dice dice = new Dice();
	    	dice.roll();
	    	return getDiceMoveset(dice);
	    }

	    
	    
	    public byte[] getDiceMoveset(Dice dice){
	    	
	    	byte[] moves = new byte[4];
	    	moves[0] = dice.getValues()[0];
	    	moves[1] = dice.getValues()[1];
	    	
	    	if(dice.isDouble()){
	    		moves[2] = moves[0];
	    		moves[3] = moves[0];
	    	}
	    	
	    	return moves;
	    }
}
