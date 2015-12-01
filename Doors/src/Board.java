


import java.util.HashSet;

/**
 * Backgammon board class.
 */

public class Board {
	
	private static final int POS_NUM_0 = 0;
	private static final int POS_NUM_5 = 5;
	private static final int POS_NUM_7 = 7;
	private static final int POS_NUM_11 = 11;
	private static final int POS_NUM_16 = 16;
	private static final int POS_NUM_12 = 12;
	private static final int POS_NUM_18 = 18;
	private static final int POS_NUM_23 = 23;
	
	private static final int PIECE_TOTAL_NUM = 15;
	
	//the table that represents the board of the game
    //every position has a number that represents how many checkers are there.
    //if this number is positive the checkers there are green, else the checkers there are red.
    private int[] table; 
    
    //Checkers on bar (that have been hit).  eaten[0] is green player, eaten[1] is red player.
    private int[] eaten;
    
    //how many pieces of each player have reached destination
    //[0] for green, [1] for red
    private int[] piecesATdestination;
    
    //private int[][] lastPlayedMove;
    private Move lastPlayedMove;
    
    //the dice of the game
    Dice dice;
    
    //if 15, then you win
    //[0] for green, [1] for red
    private int[] freedPieces; 

    private Player winner = Player.NONE;
    
    private int[][] totalMove;
    
    public Board(){
        table = new int[24];
        totalMove = new int[4][2];
        initBoard();
        eaten = new int[2];
        piecesATdestination = new int[2];
        piecesATdestination[0] = 5;
        piecesATdestination[1] = 5;
        freedPieces = new int[2];
        lastPlayedMove = new Move();
        
        dice = new Dice();
    }
	
    public Board(Board board){
		table = new int[24];
		setTable(board.getTable());
		//init eaten
		eaten = new int[2];
		eaten[0] = board.getGreensEaten();
		eaten[1] = board.getRedsEaten();
		//init piecesATdestination
        piecesATdestination = new int[2];
        piecesATdestination[0] = board.getHomeCheckers()[0];
        piecesATdestination[1] = board.getHomeCheckers()[1];
        //init freedPieces
        freedPieces = new int[2];
		freedPieces[0] = board.getFreedPieces()[0];
		freedPieces[1] = board.getFreedPieces()[1];
        //init lastPlayedMove
		lastPlayedMove = new Move();
		lastPlayedMove.setMove(board.getLastPlayedMove().getMove());
		//int totalMove (moves until now for the children)
		totalMove = new int[4][2];
		
		for(int i=0; i < totalMove.length; ++i){

			totalMove[i][0] = board.getTotalMove()[i][0];
			totalMove[i][1] = board.getTotalMove()[i][1];
		}
		
		dice = new Dice();
		dice.setValues(board.getDice().getValues());
	}
	
	
	public void setTable(int[] t){ 
		try {
		  System.arraycopy(t, 0, table, 0, t.length);
		}
		catch(Exception ex){
			ex.getMessage();
		}
	}
	
	public int[] getTable() { return table; }
	
	public int[] getEaten() { return eaten; }
	
	public int[] getHomeCheckers() {
		return piecesATdestination;
	}
	
	public int[][] getTotalMove(){ return this.totalMove; }
	
	/**
	 * Returns a set of all the states/boards/children that a bred/produced by a dice roll
	 * @param dice the rolled dice
	 * @param player the player that rolled
	 * @return the set of children
	 */
	public HashSet<Board> getChildren(Dice dice, Player player){
		
		HashSet<Board> children = new HashSet<Board>();
		
		HashSet<Board> level1 = new HashSet<Board>();
		HashSet<Board> level1_r = new HashSet<Board>(); //reversed
		HashSet<Board> level2 = new HashSet<Board>();
		
		HashSet<Board> level3 = new HashSet<Board>();
		HashSet<Board> level4 = new HashSet<Board>();
		
		int move[] = dice.getValues();
		
		Move.resetMove(this.totalMove, 0);
		
		while(true){
			//first move
			multiBreed(move[0], this, player, 0, level1, hasReachedDestination(player));
			
			//second move
			if(!level1.isEmpty()){
				for(Board parent : level1){
					multiBreed(move[1], parent, player, 1, level2, hasReachedDestination(player));
				}
			}
			
			if(dice.isDouble()){
				//third move
				if(!level2.isEmpty()){
					for(Board parent : level2){
						multiBreed(move[0], parent, player, 2, level3, hasReachedDestination(player));
					}
				} else { children.addAll(level1); break; }
				
				//fourth move
				if(!level3.isEmpty()){
					for(Board parent : level3){
						multiBreed(move[0], parent, player, 3, level4, hasReachedDestination(player));
					}
				} else { children.addAll(level2); break; }
				
				if(level4.isEmpty()){ children.addAll(level3); break; }
				
				//finally
				children.addAll(level4);
				break;
				
			} else { //do the reverse
				
				Move.resetMove(this.getTotalMove(), 0);
				//first move - reversed
				multiBreed(move[1], this, player, 0, level1_r, hasReachedDestination(player));
				
				//second move
				if(!level1_r.isEmpty()){
					for(Board parent : level1_r){
						multiBreed(move[0], parent, player, 1, level2, hasReachedDestination(player));
					}
				}
				
				if(level2.isEmpty()){ children.addAll(level1); children.addAll(level1_r); break; }
				
				//finally
				children.addAll(level2);
				break;
			}
		}
		return children;
	}
	
	/**
	 * Breeds all the children of a specific move. Built for creating bread first tree layers of children.
	 * @param move the move to make
	 * @param parent the parent node/state/board/child
	 * @param player the current player
	 * @param where what's the position of this move in the total move queue
	 * @param level the hash set of the layer to be created 
	 * @param inDestination true if pieces have arrived at destination
	 */
	protected void multiBreed(int move, Board parent, Player player, int where, HashSet<Board> level, boolean inDestination){
		Board child;
		int n = player.getSign();
		int pos = (inDestination? player.getBearOffPos() : player.getStart()) - n;
		
		if(parent.getEaten(player) > 0){
			child = new Board(parent);
			if(!breed(child, pos, move, player, child.getTotalMove(), where)) return;
			child.setLastPlayedMove(new Move(child.getTotalMove()));
			level.add(child);
		} else {
			int iterateUntil = inDestination? 6 : 24-move;
			for(int i = 0; i < iterateUntil; ++i){
				pos += n;
				child = new Board(parent);
				
				if(!breed(child, pos, move, player, child.getTotalMove(), where)) continue;
				
				child.setLastPlayedMove(new Move(child.getTotalMove()));
				level.add(child);
			}
		}
	}
	
	/**
	 * Breeds a new child board
	 * @param child the child to breed from
	 * 			the manipulation acts directly on this instance
	 * 			if someone wants a new instance of the Board to be bred
	 * 			he/she must call the function as breed(new Board(someChild),...)
	 * @param pos the position of the piece
	 * @param move the move to make
	 * @param player the player
	 * @param totalMove the totalMove of the player
	 * @param where what position stands for the move
	 * @return false if the move is not valid
	 */
	private boolean breed(Board child, int pos, int move, Player player, int[][] totalMove, int where){
		int n = player.getSign();
	
		if(!child.isValidMove(pos, n*move, player)) return false;

		int target = pos + n*move; 
		
		child.makeMove(pos, target, n);
		
		child.getTotalMove()[where][0] = pos;
		child.getTotalMove()[where][1] = target;
		
		return true;
	}
	
	private int getEaten(Player p) {
		return eaten[p.ordinal()];
	}
	
	private boolean hasReachedDestination(Player p) {		
		return (p == Player.GREEN)? hasGreenReachedDestination() : hasRedReachedDestination();
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

		if(!checkDirection(pos, pos + move, player)) {
			return false;
		}
		
		if(!isValidPick(pos, player)){
			return false;
		}
		//this position does not contain any of the player's pieces
		
		if(!isValidTarget(pos + move, player)){
			return false;
		}
		
		if(hasReachedDestination(player)){
			return isValidBearOff(pos, pos+move, player);
			
		}
		
		return true;
	}
	
	/**
	 * Checks if a bearing off move is valid
	 * @param pos the initial position
	 * @param finalPos pos+the_move/the_die_value
	 * @param player the current player
	 * @return true if valid
	 */
	public boolean isValidBearOff(int pos, int finalPos, Player player){
		
		int move = player.getSign()*(finalPos - pos);
		
		if(finalPos >= 24){
			
			//wrong bear off area
			if(!(player == Player.GREEN)){
				return false;
			}
			
			if(colorAt(24 - move) == player){
				if(pos != 24 - move) return false;
			}else{
				if((pos > 24 - move) && hasPreviousNeighbours(pos-1, player)){
					return false;
				}
			}
			
		} else if (finalPos <= -1 ) {
			//wrong bear off area
			if(!(player == Player.RED)){
				return false;
			}
			
			if(colorAt(move - 1) == player){
				if(pos != move - 1) return false;
			}else{
				if((pos < move - 1) && hasPreviousNeighbours(pos+1, player)) return false;
			}
			
		}
		
		return true;
	}
	
	/**
	 * Checks if the are higher neighbouring position that contain pieces
	 * @param i initial position to search
	 * @param player current player
	 * @return true is there are
	 */
	private boolean hasPreviousNeighbours(int i, Player player) {
		
		if(player == Player.GREEN){
			for(; i > 17; --i){
				if(colorAt(i) == player) return true;
			}
		}else{//red
			for(; i < 6; ++i){
				if(colorAt(i) == player) return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if you picked a correct piece
	 */
	public boolean isValidPick(int pos, Player player){
		if(pos >= 0 && pos <= 23){
			if(colorAt(pos) == player){
				return true;
			} else {
				return false;
			}
			
		}else if (pos == -1){ // start position is the bar with eaten pieces for the green player
	
			if(player != Player.GREEN) return false;
			return (eaten[0] > 0);
			
		}else if (pos == 24){ // start position is the bar with eaten pieces for the red player
	
			if(player != Player.RED) return false;
			
			return (eaten[1] > 0);		
		}else{
			return false;
		}
	}
	
	/** Performs the move (a single jump)
	 * @param pos current position of the piece to move
	 * @param target the target the piece has to move
	 * @param n n = -1 for RED, n = 1 for GREEN
	 */
	protected void makeMove(int pos, int target, int n){
		//validity of the move is already checked
	
		if((pos > -1) && (pos < 24)){	
			//normal move
			table[pos] -= n; //pick that piece 
			
			if(n == -1){
				if(pos < 6)
					piecesATdestination[1]--;
			}else{
				if(pos > 17)
					piecesATdestination[0]--;
			}
		
		} else if (pos < -1) {
			return; //no other moves //possibly it's a single move by the player
		} else { 
			
			if(n == 1){//GREEN-PLAYER
				if(pos == -1){
					eaten[0]--;
				}
					//else no move happens...positions wrong
				//but the validity is checked already...
			}else{//n == -1 RED-CPU
				if(pos == 24){
					eaten[1]--;
				}
				//else no move happens...positions wrong
				//but the validity is checked already...
			}
		}
		
		if((target > -1) && (target < 24)){
			
			table[target] += n; //move that piece here

			if(table[target] == 0){//because of the checked validity this means one of the opponents piece was hit
				table[target] += n;

				eaten[(n+1)/2]++; //eating time

				if((n+1)/2 == 1){
					if(target < 6)
						piecesATdestination[1]--; //one red piece from this area got eaten
				}else{
					if(target > 17)
						piecesATdestination[0]--;
				}
			}
			
			//check if a piece reached destination
			if(n == 1){ 
				if( target > 17 ) piecesATdestination[0]++;
			} else {
				if( target < 6 ) piecesATdestination[1]++;
			}
			
		} else { //time to free some pieces
			if(n == 1){
				if(target >= 24)
					freedPieces[0]++;
			} else { // n==-1
				if(target <= -1)
					freedPieces[1]++;
			}
		}
		
		isTerminal();
	}
	
	/**
	 * Performs a total move, not just a jump
	 * @param m the move to perform
	 * @param n current player signum
	 */
	protected void makeTotalMove(Move m, int n){
		//validity of the move is already checked
		
		int[][] moveToMake = m.getMove();
		
		for(int i=0; i< moveToMake.length; i++)
		{	
			if(moveToMake[i][0] == moveToMake[i][1]) break; //if this conditions is true then moveToMake[i][0]= moveToMake[i][1]=-99
			
			makeMove(moveToMake[i][0], moveToMake[i][1], n);
		}
		
		isTerminal();
		
	}
	
	public int getNumberOfPiecesAt(int pos){
		return Math.abs(table[pos]);
	}
	
	/**
	 * Checks if the target is valid for a move to be made
	 */
	public boolean isValidTarget(int moveTarget, Player player){
		
		if(moveTarget >= 0 && moveTarget <= 23){
			int n = player.getSign();
			int signm = (int) Math.signum(table[moveTarget]);
			if(signm == n || table[moveTarget]+n == 0 || table[moveTarget] == 0)
				//this position either contains player's pieces, or it contains just one of opponent's pieces, either is empty
				return true;
			else 
				return false;
		}else if (moveTarget < 0){ // red frees
			if(player != Player.RED) return false;
			return hasRedReachedDestination();
		}else if (moveTarget > 23){ // green frees

			if(player != Player.GREEN){
				return false;
			}
			return hasGreenReachedDestination();
		}else{
			return false;
		}
	}
	
	/**
	 * Check if the moving direction is correct
	 */
	protected boolean checkDirection(int pos, int target, Player player) {
		if ((player == Player.RED && target >= pos) || (player == Player.GREEN && target <= pos)) {
			return false;
		}
		return true;
	}

    /**
     * Checks if there is a red door in that position.
     */
    public boolean isRedDoor(short position){ 
        return table[position] < -1; 
    }
    
    /**
     * Checks if there is a green door in that position.
     */
    public boolean isGreenDoor(short position){
        return table[position] > 1;
    }
    
    /**
     * Checks which player has pieces in that position
     * @param pos the position
     * @return the player
     */
    public Player colorAt(int pos){
    	if(table[pos] < 0) return Player.RED;
    	else if (table[pos] > 0) return Player.GREEN;
    	else return Player.NONE;
    }
    
    /**
     * @return true if Red has reached the final destination on the board
     */
    public boolean hasRedReachedDestination(){
        return piecesATdestination[1]+freedPieces[1] == 15;
    }

    /**
     * @return true if Green has reached the final destination on the board
     */
    public boolean hasGreenReachedDestination(){
        return piecesATdestination[0]+freedPieces[0] == 15;
    }
    
    public int getGreensEaten(){
    	return eaten[0];
    }
    
    public int getRedsEaten(){
    	return eaten[1];
    }
    
    public int getRedsLeft(){
    	return PIECE_TOTAL_NUM - eaten[0] - freedPieces[0];
    }
    
    public int getGreensLeft(){
    	return PIECE_TOTAL_NUM - eaten[1] - freedPieces[1];
    }

    public Move getLastPlayedMove(){ return lastPlayedMove; }
    
    public void setLastPlayedMove(Move move){
    	lastPlayedMove = move; //*mipws 8elei copy consrtuctor?
    }
      
    /**
     * Checks if this board is a terminal state.
     * If it is then someone has won the game.
     * @return the winner if any
     */
    public boolean isTerminal(){
    	if(freedPieces[0] == 15){ 
    		setWinner(Player.GREEN);
    		return true;
    	}else if (freedPieces[1] == 15){
    		setWinner(Player.RED);
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public int piecesAtDestination(Player p){
    	return piecesATdestination[p.ordinal()];
    }
    
    public Dice getDice(){
    	return dice;
    }
    
	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player w){
		winner = w;
	}

	public int[] getFreedPieces() {
		return this.freedPieces;
	}
}