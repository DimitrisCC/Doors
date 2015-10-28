
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

    /**
     * Generate random 1-6 values.
     */
    public byte[] roll() {
        this.values[0] = (byte) (random.nextInt(6) + 1);
        this.values[1] = (byte) (random.nextInt(6) + 1);
        return values;
    }

    public byte[] getValues(){
        return values;
    }

}
