import java.util.ArrayList;
import java.util.Random;

/**
 * 6-side random dice class.
 * Tosses twice.
 */
public class Dice {

    private Random random;
    private int[] values; //store them as a member variable for further usage

    public Dice() {
        random = new Random();
        values = new int[2];
    }

    public Dice(int[] v) {
        random = new Random();
        values = new int[2];
        setValues(v);
    }
    /**
     * Generate random 1-6 values.
     */
    public int[] roll() {
        this.values[0] = (int) (random.nextInt(6) + 1);
        this.values[1] = (int) (random.nextInt(6) + 1);
        return values;
    }
    
    public void setValues(int[] v){
    	try {
		  System.arraycopy(v, 0, values, 0, v.length);
		}
		catch(Exception ex) {
			System.err.println(ex.getMessage());
		}
    }

    public int[] getValues(){
    	int[] val = new int[values.length];
    	System.arraycopy(values, 0, val, 0, values.length );
        return val;
    }
    
    public static ArrayList<Dice> allPossibleRolls(){
    	
    	ArrayList<Dice> possibleRolls = new ArrayList<Dice>();
    	int[] values = new int[2];
    	
    	for(int i=1; i<=6; i++){
    		for(int j=i; j<=6; j++){ // j=i to not re-use a roll
    			values[0] = i;
    			values[1] = j;
    			possibleRolls.add(new Dice(values));
    		}
    	}
    	return possibleRolls;
    	
    }
    
    public boolean isDouble(){ return values[0] == values[1];}
    
    public int[] getDiceMoves(){
    	
    	int[] moves = new int[4];
    	moves[0] = getValues()[0];
    	moves[1] = getValues()[1];
    	
    	if(isDouble()){
    		moves[2] = moves[0];
    		moves[3] = moves[0];
    	}
    	
    	return moves;
    }
    
    public ArrayList<Integer> getDiceMoveset(){
    	int[] ms = getDiceMoves();
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
