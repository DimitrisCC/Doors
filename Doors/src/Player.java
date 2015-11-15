
/** 
 * Enums are cool and gamedev-friendly.
 */

public enum Player {

    RED, GREEN, CHANCE, NONE;
	
	private byte sign;
	private Player opponent;
	
	static {
		Player.RED.sign = -1;
		Player.GREEN.sign = 1;
		Player.NONE.sign = 0;
		Player.CHANCE.sign = 0;
		
		Player.RED.opponent = Player.GREEN;
		Player.GREEN.opponent = Player.RED;
		Player.NONE.opponent = Player.NONE;
		Player.CHANCE.opponent = Player.CHANCE;
	}
	
	public byte getSign(){
		return sign;
	}
	
	public Player getOpponent(){
		return opponent;
	}
	
}
