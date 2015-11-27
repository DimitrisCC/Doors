
/** 
 * Enums are cool and gamedev-friendly.
 */

public enum Player {

     GREEN, RED, CHANCE, NONE;
	
	private byte sign;
	private Player opponent;
	private int start;
	
	static {
		Player.RED.sign = -1;
		Player.GREEN.sign = 1;
		Player.NONE.sign = 0;
		Player.CHANCE.sign = 0;
		
		Player.RED.opponent = Player.GREEN;
		Player.GREEN.opponent = Player.RED;
		Player.NONE.opponent = Player.NONE;
		Player.CHANCE.opponent = Player.CHANCE;
		
		Player.RED.start = 23;
		Player.GREEN.start = 0;
		Player.NONE.start = -99;
		Player.CHANCE.start = -99;
	}
	
	public byte getSign(){
		return sign;
	}
	
	public Player getOpponent(){
		return opponent;
	}
	
	public int getStart(){
		return start;
	}
}
