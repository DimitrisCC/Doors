
import java.util.ArrayList;
import java.util.Stack;

/**
 * Backgammon board class.
 *
 * **HashMap-like implementation for speed.
 * **Could be done more easily by having a "position" field in each piece but would hurt the speed.
 * ------> oxi den 8a itan pio eukolo giati 8a diskoleue i anazitisi tou "posa poulia einai se mia 8esi"
 * ------> outws i allws ta poulia dn exoun "noumero" opote dn se noiazei i 8esi enos sigkekrimenou pouliou
 * ------> alla ta poulia se ka8e 8esi
 * For more extensibility we could create a class for the data structure and not just use ArrayList<Stack<Piece>>.
 * It looks kinda ugly.
 */
public class Board {

    private static ArrayList<Stack<Piece>> table; //a list of stacks of Pieces
    // list->positions of board, stack->pieces in each position

    public Board(){
        table = new ArrayList<Stack<Piece>>();

        int i;

        for(i = 0; i < 24; ++i) {
            table.add((new Stack<Piece>()));
        }

        //initialization of a doors game
        //RED: 0->23
        //GREEN: 23->0

        for(i = 0; i < 2; ++i){
            table.get(0).push(new Piece(PieceEnum.RED));
            table.get(23).push(new Piece(PieceEnum.GREEN));
        }

        for(i = 0; i < 5; ++i){
            table.get(11).push(new Piece(PieceEnum.RED));
            table.get(16).push(new Piece(PieceEnum.RED));
            table.get(12).push(new Piece(PieceEnum.GREEN));
            table.get(7).push(new Piece(PieceEnum.GREEN));
        }

        for(i = 0; i < 3; ++i){
            table.get(18).push(new Piece(PieceEnum.RED));
            table.get(12).push(new Piece(PieceEnum.GREEN));
        }
    }

    /**
     * Checks if there is a red door in that position.
     */
    public boolean isRedDoor(short position){
        return table.get(position).size() > 1 && table.get(position).peek().getColor().equals(PieceEnum.RED);
    }

    /**
     * Checks if there is a green door in that position.
     */
    public boolean isGreenDoor(short position){
        return table.get(position).size() > 1 && table.get(position).peek().getColor().equals(PieceEnum.GREEN);
    }

    /**
     * @return true if Red has reached the final destination on the board
     */
    public boolean hasRedReachedDestination(){
    	 short reds = 0;
         Stack<Piece> s;
         for(int i = 0; i < 6; ++i){
         	s = table.get(i);
         	if(!s.empty())
         		if(s.peek().getColor().equals(PieceEnum.RED)) //if first is red, anything in the stack is red
         			reds += table.get(i).size();
         }
         return reds == 15;
    }

    /**
     * @return true if Green has reached the final destination on the board
     */
    public boolean hasGreenReachedDestination(){
        short greens = 0;
        Stack<Piece> s;
        for(int i = 0; i < 6; ++i){
        	s = table.get(i);
        	if(!s.empty())
        		if(s.peek().getColor().equals(PieceEnum.GREEN)) //if first is green, anything in the stack is green
        			greens += table.get(i).size();
        }
        return greens == 15;
    }

   // public movePieceTo

}
