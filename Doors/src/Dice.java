import java.util.ArrayList;
import java.util.Random;

/**
 * 6-side random dice class.
 * Tosses twice.
 */
public class Dice {

    private Random random;
    private byte[] values; //store them as a member variable for further usage

    public Dice() {
        random = new Random();
        values = new byte[2];
    }

    public Dice(byte[] v) {
        random = new Random();
        values = new byte[2];
        setValues(v);
    }
    /**
     * Generate random 1-6 values.
     */
    public byte[] roll() {
        this.values[0] = (byte) (random.nextInt(6) + 1);
        this.values[1] = (byte) (random.nextInt(6) + 1);
        return values;
    }
    
    public void setValues(byte[] v){
    	try {
		  System.arraycopy(v, 0, values, 0, v.length);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
    }

    public byte[] getValues(){
        return values;
    }
    
    public static ArrayList<Dice> allPossibleRolls(){
    	
    	ArrayList<Dice> possibleRolls = new ArrayList<Dice>();
    	byte[] values = new byte[2];
    	
    	for(byte i=1; i<=6; i++){
    		for(byte j=i; j<=6; j++){ // j=i to not re-use a roll
    			values[0] = i;
    			values[1] = j;
    			possibleRolls.add(new Dice(values));
    		}
    	}
    	return possibleRolls;
    	
    }
    
    public boolean isDouble(){ return values[0] == values[1];}
    
    public byte[] getDiceMoves(){
    	
    	byte[] moves = new byte[4];
    	moves[0] = getValues()[0];
    	moves[1] = getValues()[1];
    	
    	if(isDouble()){
    		moves[2] = moves[0];
    		moves[3] = moves[0];
    	}
    	
    	return moves;
    }
    
    public ArrayList<Integer> getDiceMoveset(){
    	byte[] ms = getDiceMoves();
    	ArrayList<Integer> arms = new ArrayList<Integer>();
    	
    	if(isDouble()){
    		arms.add((int) ms[0]);
        	arms.add((int) ms[0]*2);
        	arms.add((int) ms[0]*3);
        	arms.add((int) ms[0]*4);
    	} else {
    		arms.add((int) ms[0]);
        	arms.add((int) ms[1]);
        	arms.add((int) ms[0]+ms[1]);
    	}
    	return arms;
    }
    
    public int getTotalJumpsFromDice(){
    	int total = getValues()[0]+getValues()[1];
    	
    	if(isDouble()){
    		total += getValues()[0] * 2;
    	}
    	
    	return total;
    }
}
