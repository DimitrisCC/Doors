
/** 
 * Enums are cool and gamedev-friendly.
 */

public enum Player {

    RED, GREEN, CHANCE, NONE;
	
	private byte sign;
	
	static {
		Player.RED.sign = -1;
		Player.GREEN.sign = 1;
		Player.NONE.sign = 0;
		Player.CHANCE.sign = 0;
	}
	
	public byte getSign(){
		return sign;
	}
	
}
