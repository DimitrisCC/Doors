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
 * ------> nomizw enas pinakas einai arketos kai grigoros gia tis leitourgies pou 8eloume...xwro den pianei poli...
 * ------> parola auta auto voleuei gia to grafiko kommati.....as skeftoume kai an ginetai alliws gia na diaxwrisoume
 * ------> ilopoiisi me grafiki diepafi
 */

////------------->>> THA PARATIRISETE POS XRISIMOPOIW PANTOU ENA n (-1 'h 1) // AFTO GINETAI GIA NA DIEFKOLINTHOUN OI PRAKSEIS AFKSOMIOSIS
//////////////////// STO TABLE
////////////----> H DIEFKOLINSI THA ITAN NA FTIAXNAME KLASI Player KAI NA HTAN Player[] table OSTE NA MIN KATHOMASTE NA SKEFTOMASTE OLI TIN ORA AYTO
///////// DEN KSERO VEVAIA AN THA VOLEYE PANTOU

public class Board {
	
	private static final int POS_NUM_0 = 0;
	private static final int POS_NUM_5 = 5;
	private static final int POS_NUM_7 = 7;
	private static final int POS_NUM_11 = 11;
	private static final int POS_NUM_16 = 16;
	private static final int POS_NUM_12 = 12;
	private static final int POS_NUM_18 = 18;
	private static final int POS_NUM_23 = 23;
	
	private static final int RED_START_POS = 0;
	private static final int GREEN_START_POS = 23;
	
	private static final int PIECE_TOTAL_NUM = 15;
	
    private static int[] table; //a list of stacks of Pieces
    // list->positions of board, stack->pieces in each position
    
    private int greenPieceNum;
    private int redPieceNum;

    public Board(){
        table = new int[24];
        initBoard();
        greenPieceNum = PIECE_TOTAL_NUM;
        redPieceNum = PIECE_TOTAL_NUM;
    }
	
	public Board(int[] t){
		table = new int[24];
		setTable(t);
		greenPieceNum = PIECE_TOTAL_NUM;
        redPieceNum = PIECE_TOTAL_NUM;
	}
	
	public void setTable(int[] t){ 
		//for(int i = 0; i < table.length; ++i)
		//	table[i] = t[i];
		try {
		  System.arraycopy(t, 0, table, 0, t.length);
		}
		catch(Exception ex)
		{
			ex.getMessage();
		}
	}
	
	public int[] getTable() { return table; }
	
	//if returned list is empty, no valid moves can be done
	public ArrayList<Board> getChildren(Dice dice, Player player){
		//--->DEN EXEI AKOMA GIA DIPLES GIATI GAMIOUNTAI!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		ArrayList<Board> children = new ArrayList<Board>();
		byte move[] = dice.getValues();
		int pN = (player == Player.RED)? RED_START_POS : GREEN_START_POS;
		
		_getChildren(move, children, pN, player);
		return children;
		
	}
	
	//pN-> 0 when reds play, 23 when greens play
	// ---> isws fainetai pio mperdemeno, alla toulaxiston den yparxei idios kwdikas, whatever
	private void _getChildren(byte[] move, ArrayList<Board> children, int pN, Player player){
		
		Board child;
		int n = player.getSign();
		int fMove = pN + n; //fMove -> first move
		int sMove = pN + n; //sMove -> first move
		
		//first move
		for(int i = 0; i < 24 - move[0]; ++i){ //not taking into account the children states where moves lead out of board
											//at this moment
			fMove -= n; //increasing when reds, decreasing when greens
			
			if(!isValidMove(fMove, move[0], player)) continue;
			
			//second move
			for(int j = 0; j < 24 - move[0]; ++j){ //not taking into account the children states where moves lead out of board
												//at this moment
				sMove -= n; //increasing when reds, decreasing when greens
				if(!isValidMove(sMove, move[1], player)) continue;
			
				if(sMove != fMove){
					child = new Board(table); //clone this state
					child.move(fMove, move[0], n); 
					child.move(sMove, move[1], n);
					children.add(child);
				} else { //same piece
					if(isValidMove(fMove, move[0]+move[1], player)){
						child = new Board(table);
						child.move(fMove, move[0]+move[1], n);
						children.add(child);
					}
				}
			}

		}
	}
	
	/**
	 * Initializes the board for a Doors game
	 */
	protected void initBoard(){
		//initialization of a doors game
        //RED: 0->23 -integer
        //GREEN: 23->0 +integer
		table[POS_NUM_0] = -2;
		table[POS_NUM_5] = 5;
		table[POS_NUM_7] = 3;
		table[POS_NUM_11] = -5;
		table[POS_NUM_12] = 5;
		table[POS_NUM_16] = -3;
		table[POS_NUM_18] = -5;
		table[POS_NUM_23] = 2;
	}
	
	/** Checks if the move is legal
	 * @param pos current position of the piece to move
	 * @param move step the piece has to move
	 * @param n n = -1 for RED, n = 1 for GREEN
	 * @return true is move is legal
	 */
	protected boolean isValidMove(int pos, int move, Player player){
		
		byte n = player.getSign();
		int signp = (int) Math.signum(table[pos]);
		
		if((!isValidPick(pos, player)) || signp != 0) return false;
		//this position does not contain any of the player's pieces
		
		if(!checkDirection(pos, move, player)) return false;
		
		int signm = (int) Math.signum(table[pos+move]);
		if(signm == n || table[pos+move]+n == 0 || table[pos+move] == 0)
			return true; //this position either contains player's pieces, either is empty, or it contains just one of opponent's pieces
		return false;
	}
	/**
	 * Checks if you picked a correct piece
	 */
	public boolean isValidPick(int pos, Player player){
		if(Math.signum(table[pos]) == player.getSign()){
			//setStatus("Got your piece, mate!");
			return true;
		} else {
			//setStatus("Wrong color, bro!");
			return false;
		}
	}
	
	/** Performs the move
	 * @param pos current position of the piece to move
	 * @param move step the piece has to move
	 * @param n n = -1 for RED, n = 1 for GREEN
	 */
	protected void move(int pos, int move, int n){
		//validity of the move is already checked
		table[pos] -= n; //decrease the absolute value
		
		int prev = table[pos+move];
		table[pos+move] += n;
		//move done
		//izzy pizzy
		if(Math.signum(prev) < Math.signum(table[pos+move])){
			if (prev < 0) redPieceNum--;
			else if (prev > 0) greenPieceNum--;
		}
	}
	
	/**
	 * Check if the moving direction is correct
	 */
	protected boolean checkDirection(int pos, int move, Player player) {
		if ((player == Player.RED && move <= pos) || (player == Player.GREEN && move >= pos)) {
			//setStatus("You're going in the wrong direction!");
			return false;
		}
		return true;
	}


    /**
     * Checks if there is a red door in that position.
     */
    public boolean isRedDoor(short position){ //--> xreiazetai elegxo an einai entos oriwn t pinaka
    										//--------> apla ti tha petaei tote? false? kalitera na mas vgazei outofbounds
        return table[position] < -1; 
    }
    
    public byte[] getDiceMoveset(Dice dice){
    	
    	if(dice.getValues()[0] == 0) dice.roll(); //not rolled yet
    	
    	byte[] moves = new byte[4];
    	moves[0] = dice.getValues()[0];
    	moves[1] = dice.getValues()[1];
    	
    	if(dice.isDouble()){
    		moves[2] = moves[0];
    		moves[3] = moves[0];
    	}
    	
    	return moves;
    }

    /**
     * Checks if there is a green door in that position.
     */
    public boolean isGreenDoor(short position){
        return table[position] > 1;
    }

	
    /**
     * @return true if Red has reached the final destination on the board
     */
    public boolean hasRedReachedDestination(){
    	short reds = 0;
		for(int i = 0; i < 6; ++i){
			if(table[i] < 0)
				reds += table[i];
		}
		return reds == -15;
    }

    /**
     * @return true if Green has reached the final destination on the board
     */
    public boolean hasGreenReachedDestination(){
        short greens = 0;
        for(int i = 18; i < 24; ++i){
        	if(table[i] > 0)
				greens += table[i];
        }
        return greens == 15;
    }
    
    public int getGreenPiecesNumber(){
    	return this.greenPieceNum;
    }
    
    public int getRedPiecesNumber(){
    	return this.redPieceNum;
    }
    
    public int redsEaten(){
    	return PIECE_TOTAL_NUM - redPieceNum;
    }
    
    public int greensEaten(){
    	return PIECE_TOTAL_NUM - greenPieceNum;
    }

   // public movePieceTo

}
