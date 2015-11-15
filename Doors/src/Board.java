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
	
    private int[] table; //a list of stacks of Pieces
    // list->positions of board, stack->pieces in each position
    
   // Checkers on bar (that have been hit).  eaten[0] is green player, eaten[1] is red player.
    private int[] eaten;
    
    private int pos; //starting position //--> pou xrisimopoiountai?
    private int move; //target position //---> pou xrisimopoiountai?
//    /private Player player;
    
    //private int[][] lastPlayedMove;
    private ArrayList<Move> lastPlayedMoves;
    
    Dice dice;
    
    private int[] freedPieces; //if 15, then you win
    //[0] for green, [1] for red

    private Player winner = Player.NONE;
    
    public Board(){
        table = new int[24];
        initBoard();
        eaten = new int[2];
        lastPlayedMoves = new ArrayList<Move>();
        
       /* for(int i=0; i<lastPlayedMove.length; i++)
        	for(int j=0; j<lastPlayedMove[0].length; j++)
        		lastPlayedMove[i][j] = -1;*/
       // player = Player.NONE;
        dice = new Dice();
    }
    /*
    public Board(Player player){
    	 table = new int[24];
         initBoard();
         eaten = new int[2];
         this.player = player;
         dice = new Dice();
    }*/
	
	public Board(Board board){
		table = new int[24];
		setTable(board.getTable());
		eaten = new int[2];
		
		lastPlayedMoves = new ArrayList<Move>();
		
		/* for(int i=0; i<lastPlayedMove.length; i++)
	        	for(int j=0; j<lastPlayedMove[0].length; j++)
	        		lastPlayedMove[i][j] = -1;*/
		//player = board.getPlayer();
		dice = new Dice();
	}
	
	public void setTable(int[] t){ 
		try {
		  System.arraycopy(t, 0, table, 0, t.length);
		}
		catch(Exception ex){
			ex.getMessage();
		}
	}
	
	/*
	public Player getPlayer(){
		return player;
	}
	
	public void setPlayer(Player player){
		this.player = player;
	} */
	
	public int[] getTable() { return table; }
	
	//if returned list is empty, no valid moves can be done
	public ArrayList<Board> getChildren(Dice dice, Player player){
		ArrayList<Board> children = new ArrayList<Board>();
		byte move[] = dice.getValues();
		int pN = (player == Player.RED)? RED_START_POS : GREEN_START_POS;
		
		_getChildren(move, children, pN, player);
		return children;
		
	}
	
	//pN-> 0 when reds play, 23 when greens play
	// ---> isws fainetai pio mperdemeno, alla toulaxiston den yparxei idios kwdikas, whatever
	private void _getChildren(byte[] move, ArrayList<Board> children, int pN, Player player){
		// PROSOXI!! An 8ewrousame dedomeno oti kaleitai mono apo tn Max 8a itan poli pio apli oson afora
		//ellegxous gia ton paikti
		Board child;
		int[][] playedMove = new int[4][2];
		// initialize moves we won't use with invalid values
		playedMove[2][0] = -1;
		playedMove[2][1] = -1;
		playedMove[3][0] = -1;
		playedMove[3][1] = -1;
		int n = player.getSign();
		int fMove = pN + n; //fMove -> first move
		int sMove = pN + n; //sMove -> second move
		int playerNum = (n == -1) ? 0 : 1; // xreiazetai gia to eaten
		int posOfEaten = pN + n;
		
		if(move[0] == move[1]){ // this means double move
			// all the calculations use only move[0]  because move[0] and move[1] have the same value

			int tMove = pN + n; //tMove -> third move
			int foMove = pN + n; //lMove -> fourth move
			
			if(eaten[playerNum] != 0){ // case we have eaten pieces 
				int i;
				for(i=1; (i< eaten[playerNum] || i < 5); i++){
					if(isValidMove(posOfEaten, n*move[0],player)){
						playedMove[i-1][0] = posOfEaten;
						playedMove[i-1][1] = posOfEaten + n*move[0];
					}
				}
				
				switch(eaten[playerNum]){
				case 1: // we have 3 more moves to make
					//kai  pali gamietai to simpan!
					// #dn_einai_zwi_auti_kante_kati!!!
					for(int j = 0; j < 24 - move[0]; ++j){
						fMove -= n;
						
						// case we move only one piece for 3*move[0] steps
						if(j < 24 - 3*move[0]){
							if(isValidMove(fMove, 3*n*move[0], player)){
								playedMove[1][0] = fMove;
								playedMove[1][1] = fMove + n*move[0];
								playedMove[2][0] = fMove + n*move[0];
								playedMove[2][1] = fMove + 2*n*move[0];
								playedMove[3][0] = fMove + 2*n*move[0];
								playedMove[3][1] = fMove + 3*n*move[0];
								Move moveToMake = new Move(playedMove);
								child = new Board(this);
								child.makeMove(moveToMake, n);
								child.setLastPlayedMove(moveToMake);
								children.add(child);
							}
						}
						
						if(!isValidMove(fMove, n*move[0], player)) continue;
						
						for(int k = 0; k < 24 - move[0]; ++k){
							sMove -= n;
							
							// case we move one piece for move[0] steps and another piece for 2*move[0] steps
							if(k < 24 - 2*move[0]){
								if(isValidMove(sMove, 2*n*move[0], player)){
									playedMove[1][0] = fMove;
									playedMove[1][1] = fMove + n*move[0];
									playedMove[2][0] = sMove;
									playedMove[2][1] = sMove + n*move[0];
									playedMove[3][0] = sMove + n*move[0];
									playedMove[3][1] = sMove + 2*n*move[0];
									Move moveToMake = new Move(playedMove);
									child = new Board(this);
									child.makeMove(moveToMake, n);
									child.setLastPlayedMove(moveToMake);
									children.add(child);
								}
							}
							
							if(!isValidMove(sMove, n*move[0], player)) continue;
							
							for(int l=0; l < 24-move[0]; ++l){
								tMove -= n;
								
								if(!isValidMove(tMove, n*move[0], player)) continue;
								
								// case we move three pieces for move[0] steps each
								playedMove[1][0] = fMove;
								playedMove[1][1] = fMove + n*move[0];
								playedMove[2][0] = sMove;
								playedMove[2][1] = sMove + n*move[0];
								playedMove[3][0] = tMove;
								playedMove[3][1] = tMove + n*move[0];
								Move moveToMake = new Move(playedMove);
								child = new Board(this);
								child.makeMove(moveToMake, n);
								child.setLastPlayedMove(moveToMake);
								children.add(child);
							}
						}
					}
					
					break;
				case 2: // we have 2 more moves to make
					for(int j = 0; j < 24 - move[0]; ++j){
						fMove -= n;
						
						// case we move only one piece for 2*move[0] steps
						if(j < 24 - 2*n*move[0]){
							if(isValidMove(fMove, 2*n*move[0], player)){
								playedMove[2][0] = fMove;
								playedMove[2][1] = fMove + n*move[0];
								playedMove[3][0] = fMove + n*move[0];
								playedMove[3][1] = fMove + 2*n*move[0];
								Move moveToMake = new Move(playedMove);
								child = new Board(this);
								child.makeMove(moveToMake, n);
								child.setLastPlayedMove(moveToMake);
								children.add(child);
							}
						}
						
						if(!isValidMove(fMove, n*move[0], player)) continue;
						
						for(int k = 0; k < 24 - move[0]; ++k){
							sMove -= n;
							
							if(!isValidMove(sMove, n*move[0], player)) continue;
							// case we move two pieces for move[0] steps each
							playedMove[2][0] = fMove;
							playedMove[2][1] = fMove + n*move[0];
							playedMove[3][0] = sMove;
							playedMove[3][1] = sMove + n*move[0];
							Move moveToMake = new Move(playedMove);
							child = new Board(this);
							child.makeMove(moveToMake, n);
							child.setLastPlayedMove(moveToMake);
							children.add(child);
						}
					}
					break;
				case 3: // we have 1 more move to make
					for(int j = 0; j < 24 - move[0]; ++j){
						fMove -= n;
						if(!isValidMove(fMove, n*move[0], player)) continue;
						//we have one last move to make
						playedMove[3][0] = fMove;
						playedMove[3][1] = fMove + n*move[0];
						Move moveToMake = new Move(playedMove);
						child = new Board(this);
						child.makeMove(moveToMake, n);
						child.setLastPlayedMove(moveToMake);
						children.add(child);
					}
					break;
				default:
					break;
				}
				
				eaten[playerNum] -= i;
			}else{
				
				for(int i = 0; i < 24 - move[0]; ++i){ //first move
					
					fMove -= n;
	
					// case we move only one piece four times of the value of the dice
					if(i < 24 - (move[0]*4))
					{
						if(isValidMove(fMove, 4*n*move[0], player)){
							child = new Board(this);
							// the move is happening step by step but with only one piece
							playedMove[0][0] = fMove;
							playedMove[0][1] = fMove + n*move[0];
							playedMove[1][0] = fMove + n*move[0];
							playedMove[1][1] = fMove + 2*n*move[0];
							playedMove[2][0] = fMove + 2*n*move[0];
							playedMove[2][1] = fMove + 3*n*move[0];
							playedMove[3][0] = fMove + 3*n*move[0];
							playedMove[3][1] = fMove + 4*n*move[0];
							Move moveToMake = new Move(playedMove);
							child.makeMove(moveToMake, n);
							
							child.setLastPlayedMove(moveToMake);
							children.add(child);
						}
		
					}
					
					if(!isValidMove(fMove, n*move[0], player)) continue;
					
					for(int j = 0; j < 24 - move[0]; ++i){ //second move
						sMove -= n;
						
						// case we move one piece for move[0] steps and another piece for 3*move[0] steps
						if(i < 24 - (move[0]*3))
						{
							if(isValidMove(sMove, 3*n*move[0], player)){
								child = new Board(this);
								playedMove[0][0] = fMove;
								playedMove[0][1] = fMove + n*move[0];
								//first piece moved
								playedMove[1][0] = sMove;
								playedMove[1][1] = sMove + n*move[0];
								playedMove[2][0] = sMove + n*move[0];
								playedMove[2][1] = sMove + 2*n*move[0];
								playedMove[3][0] = sMove + 2*n*move[0];
								playedMove[3][1] = sMove + 3*n*move[0];
								//second piece moved
								Move moveToMake = new Move(playedMove);
								child.makeMove(moveToMake, n);
								
								child.setLastPlayedMove(moveToMake);
								children.add(child);
							}
			
						}
						
						if(!isValidMove(sMove, n*move[0], player)) continue;
						
						for(int k = 0; k < 24 - move[0]; ++i){ // third move
							tMove -= n;
							
							// case we two pieces for move[0] steps each and one piece for 2*move[0] steps
							if(i < 24 - (move[0]*2))
							{
								if(isValidMove(tMove, 2*n*move[0], player)){
									//--> arxika metakinw ta 2 prwta poulia kanonika kai to 3o kata 2*move[0]
									child = new Board(this);
									playedMove[0][0] = fMove;
									playedMove[0][1] = fMove + n*move[0];
									//first piece moved
									playedMove[1][0] = sMove;
									playedMove[1][1] = sMove + n*move[0];
									//secont piece moved
									playedMove[2][0] = tMove;
									playedMove[2][1] = tMove + n*move[0];
									playedMove[3][0] = tMove + n*move[0];
									playedMove[3][1] = tMove + 2*n*move[0];
									//third piece moved
									Move moveToMake = new Move(playedMove);
									child.makeMove(moveToMake, n);
									
									child.setLastPlayedMove(moveToMake);
									children.add(child);
									
									// case we move two pieces for 2*move[0] steps each
									if(isValidMove(fMove, 2*n*move[0], player))
									{
										child = new Board(this);
										playedMove[0][0] = fMove; //its the same if i take fMove or sMove
										playedMove[0][1] = fMove + n*move[0];
										playedMove[1][0] = fMove + n*move[0];
										playedMove[1][1] = fMove + 2*n*move[0];
										//first piece moved
										playedMove[2][0] = tMove;
										playedMove[2][1] = tMove + n*move[0];
										playedMove[3][0] = tMove + n*move[0];
										playedMove[3][1] = tMove + 2*n*move[0];
										//second piece moved
										Move moveToMake1 = new Move(playedMove);
										child.makeMove(moveToMake1, n); //--> edw tn onomasa moveToMake1 giati ipirxe conflict me tn apopanw
										//--> epeidi itan sto idio scope
										
										child.setLastPlayedMove(moveToMake1);
										children.add(child);
									}
								}
				
							}
							
							if(!isValidMove(tMove, n*move[0], player)) continue;
							
							for(int l = 0; l < 24 - move[0]; ++i){ //fourth move
								foMove -= n;
								
								// case we move four (different) pieces for move[0] steps each
								if(!isValidMove(foMove, n*move[0], player)) continue;
								
								child = new Board(this);
								playedMove[0][0] = fMove;
								playedMove[0][1] = fMove + n*move[0];
								//first piece moved 
								playedMove[1][0] = sMove;
								playedMove[1][1] = sMove + n*move[0];
								//second piece moved
								playedMove[2][0] = tMove;
								playedMove[2][1] = tMove + n*move[0];
								//third piece moved
								playedMove[3][0] = foMove;
								playedMove[3][1] = foMove + n*move[0];//stn Move exw 8ewrisei oti oi kiniseis ginontai
								//fourth piece moved
								Move moveToMake = new Move(playedMove);
								child.makeMove(moveToMake, n);
								
								child.setLastPlayedMove(moveToMake);
								children.add(child);
							
							}
						}	
					}
				}
			}
		}else{ //normal move
			
			if(eaten[playerNum] != 0){ // case we have eaten pieces 
				
				if(eaten[playerNum] >= 2){
					if(isValidMove(posOfEaten, n*move[0], player)){
						playedMove[0][0] = posOfEaten;
						playedMove[0][1] = posOfEaten + n*move[0];
					}
					
					if(isValidMove(posOfEaten, n*move[1], player)){
						playedMove[1][0] = posOfEaten;
						playedMove[1][1] = posOfEaten + n*move[0];
					}else{
						playedMove[1][0] = -1;
						playedMove[1][1] = -1;
					}
					
					Move moveToMake = new Move(playedMove);
					child = new Board(this);
					child.makeMove(moveToMake, n);
					child.setLastPlayedMove(moveToMake);
					children.add(child);
				}else{ //else eaten[playerNum] == 1 because eaten[playerNum] > 0
					
				}
			}else{
				//first move
				for(int i = 0; i < 24 - move[0]; ++i){ //not taking into account the children states where moves lead out of board
													//at this moment
					fMove -= n; //increasing when reds, decreasing when greens
					
					//case we move one piece for move[0] + move[1] steps
					if(i < 24 - (move[0] + move[1]))
					{
						if(isValidMove(fMove, n*(move[0] + move[1]), player)){
							child = new Board(this);
							playedMove[0][0] = fMove;
							playedMove[0][1] = fMove + n*move[0];
							playedMove[1][0] = fMove + n*move[0];
							playedMove[1][1] = fMove + n*(move[0] + move[1]);
							// the move is made sequentially
							Move moveToMake = new Move(playedMove);
							child.makeMove(moveToMake, n);
							
							child.setLastPlayedMove(moveToMake);
							children.add(child);
						}
		
					}
					
					if(!isValidMove(fMove, n*move[0], player)) continue;
					
					//second move
					for(int j = 0; j < 24 - move[1]; ++j){ //not taking into account the children states where moves lead out of board
														//at this moment
						sMove -= n; //increasing when reds, decreasing when greens
						if(!isValidMove(sMove, n*move[1], player)) continue;
					
						//case we move two pieces, the first for move[0] steps and the second for move[1] steps
						child = new Board(this); //clone this state
						playedMove[0][0] = fMove;
						playedMove[0][1] = fMove + n*move[0];
						//first piece moved
						playedMove[1][0] = sMove;
						playedMove[1][1] = sMove + n*move[1];
						//second piece moved
						Move moveToMake = new Move(playedMove);
						child.makeMove(moveToMake, n);
						
						child.setLastPlayedMove(moveToMake);
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
        //GREEN: 0->23 +integer
        //RED: 23->0 -integer
		table[POS_NUM_0] = 2;
		table[POS_NUM_5] = -5;
		table[POS_NUM_7] = -3;
		table[POS_NUM_11] = 5;
		table[POS_NUM_12] = -5;
		table[POS_NUM_16] = 3;
		table[POS_NUM_18] = 5;
		table[POS_NUM_23] = -2;
	}
	
	/** Checks if the move is legal
	 * @param pos current position of the piece to move
	 * @param move step the piece has to move
	 * @param n n = -1 for RED, n = 1 for GREEN
	 * @return true is move is legal
	 */
	protected boolean isValidMove(int pos, int move, Player player){
		//if(player == null) player = this.player;
		
		// if wrong direction false
		if(!checkDirection(pos,pos + move, player)) return false;

		if(!isValidPick(pos, player)) return false;
		//this position does not contain any of the player's pieces
		
		if(!isValidTarget(pos + move, player)) return false;

		return true;
	}
	
	
	
	/**
	 * Checks if you picked a correct piece
	 */
	public boolean isValidPick(int pos, Player player){
		//if (player == null) player = this.player;
		if(pos >= 0 || pos <= 23){;
			System.out.println(Math.signum(table[pos]));
			if(Math.signum(table[pos]) == player.getSign()){
				System.out.println(Math.signum(table[pos]));
				//setStatus("Got your piece, mate!");
				return true;
			} else {
				//setStatus("Wrong color, bro!");
				return false;
			}
		}else if (pos == -1){ // start position is the bar with eaten pieces
			return (eaten[0] > 0);
		}else if (pos == 24){
			return (eaten[1] > 0);
		}else{
			return false;
		}
	}
	
	/** Performs the move
	 * @param pos current position of the piece to move
	 * @param steps steps the piece has to move
	 * @param n n = -1 for RED, n = 1 for GREEN
	 */
	protected void makeMove(Move m, int n){
		//validity of the move is already checked
		
		int[][] moveToMake = m.getMove();
		
		for(int i=0; i< moveToMake.length; i++)
		{	
			if((moveToMake[i][0] != -1) || (moveToMake[i][0] != 24)){ 
				//normal move
				table[moveToMake[i][0]] -= n; //decrease the absolute value
				int prev = table[moveToMake[i][1]];
				table[moveToMake[i][1]] += n;
				if(Math.abs(prev) == 1){
					table[moveToMake[i][1]] += n;
					if (prev < 0) eaten[1]++;
					else if (prev > 0) eaten[0]++;
				}
			}else{
				//---> Mporei na xrisimopoii8ei gia na metakinoume poulia ektos board!
				//---> An moveToMake[i][0] == -1 kai moveToMake != -1
				//---> tote metakinise ena pouli apo ta fagwmena sto table[moveToMake[i][1]]
				return;
			}
		}
		
	}
	
	public int getNumberOfPiecesAt(int pos){
		return Math.abs(table[pos]);
	}
	
	public boolean isValidTarget(int moveTarget, Player player){
		//if(player == null) player = this.player;
		if(moveTarget >= 0 || moveTarget <= 23){
			int n = player.getSign();
			int signm = (int) Math.signum(table[moveTarget]);
			if(signm == n || table[moveTarget]+n == 0 || table[moveTarget] == 0)
				//this position either contains player's pieces, or it contains just one of opponent's pieces, either is empty
				return true;
			else 
				return false;
		}else if (moveTarget == -1){ // o kokkinos mazeuei
			return hasRedReachedDestination();
		}else if (moveTarget == 24){ // o prasinos mazeuei
			return hasGreenReachedDestination();
		}else{
			return false;
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
    
    /**
     * Checks if there is a green door in that position.
     */
    public boolean isGreenDoor(short position){
        return table[position] > 1;
    }
    
    public Player colorAt(int pos){
    	if(table[pos] < 0) return Player.RED;
    	else if (table[pos] > 0) return Player.GREEN;
    	else return Player.NONE;
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
    
    public int getGreensEaten(){
    	return eaten[0];
    }
    
    public int getRedsEaten(){
    	return eaten[1];
    }
    
    public int getRedsLeft(){
    	return PIECE_TOTAL_NUM - eaten[0];
    }
    
    public int getGreensLeft(){
    	return PIECE_TOTAL_NUM - eaten[1];
    }

	
	protected int piecesOnBar(Player player){
		
		if (player == Player.GREEN && eaten[0] > 0)
			return eaten[0];
		else if (player == Player.RED && eaten[1] > 0)
			return eaten[1];
		else return 0;
	}
  
    public ArrayList<Move> getLastPlayedMoves(){ return lastPlayedMoves; }
    
    public void setLastPlayedMove(Move move){
    	/*for(int i=0; i<lastPlayedMove.length; i++)
    		for(int j=0; j<lastPlayedMove[0].length; j++)
    			lastPlayedMove[i][j] = move[i][j];*/
    	lastPlayedMoves.add(move);
    }
    
    //It returns the number of checkers 
    //from the pos1-pos2 area for the player I chose
    public int numberOfcheckers(int pos1,int pos2,int player)
    {   
    	int sum=0;
    	for(int i=pos1;i<=pos2;i++)
        {
          sum+=player*getNumberOfPiecesAt(i)+(1-player)*getNumberOfPiecesAt(i);
    			
        }
    	return sum;	
    	
    }
    
    /**
     * Checks if this board is a terminal state.
     * If it is then someone has won the game.
     * @return the winner if any
     */
    public Player isTerminal(){
    	if(freedPieces[0] == 15) return Player.GREEN;
    	else if (freedPieces[1] == -15) return Player.RED;
    	else return Player.NONE;
    }
    
    
    //xreiazontai gia to gui
    //prepei na vlepei ta zaria autou tou stigmiotypou
    //an ginetai na mpoun allou, tha mpoun ok
    
    public byte[] rollDice(){
		return dice.roll();
    }
    
    public byte[] getDiceValues(){
    	return dice.getValues();
    }
    
    public Dice getDice(){
    	return dice;
    }
    
    public byte[] getDiceMoves(Dice dice){
    	
    	byte[] moves = new byte[4];
    	moves[0] = dice.getValues()[0];
    	moves[1] = dice.getValues()[1];
    	
    	if(dice.isDouble()){
    		moves[2] = moves[0];
    		moves[3] = moves[0];
    	}
    	
    	return moves;
    }
    
    public ArrayList<Integer> getDiceMoveset(Dice dice){
    	byte[] ms = getDiceMoves(dice);
    	ArrayList<Integer> arms = new ArrayList<Integer>();
    	
    	if(dice.isDouble()){
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

	public Player getWinner() {
		return winner;
	}

}
