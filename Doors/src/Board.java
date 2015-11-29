import java.util.ArrayList;
import java.util.HashSet;
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
	
	private static final int RED_START_POS = 23;
	private static final int GREEN_START_POS = 0;
	
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
	
	public int[] getHomeCheckers() { return piecesATdestination; }
	
	public int[][] getTotalMove(){ return this.totalMove; }
	
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
		//System.out.println("table before pick "+table[pos]);
		if(!child.isValidMove(pos, n*move, player)) return false; //in getChildren: if (!breed(...)) continue

		//System.out.println("pick "+pos+" table "+table[pos]);
		int target = pos + n*move; 
		
		child.makeMove(pos, target, n);
		
		child.getTotalMove()[where][0] = pos;
		child.getTotalMove()[where][1] = target;
		
		return true;
	}
	
	//if returned list is empty, no valid moves can be done
	public HashSet<Board> getChildren(Dice dice, Player player){
		HashSet<Board> children = new HashSet<Board>();
		/*int pN = (player == Player.RED)? RED_START_POS : GREEN_START_POS;
		
		if(eaten[player.ordinal()] > 0 ){
			System.out.println("eeeeeeaten"); //DEBUG
			Eaten_getChildren(dice.getValues(), children, pN, player);
		}else if((player == player.GREEN)? hasGreenReachedDestination() : hasRedReachedDestination()){
			pN = (player == Player.RED)? 5 : 18;
			System.out.println("bearOffffff"); //DEBUG
			BearOff_getChildren(dice.getValues(), children, pN, player);
		}else{
			System.out.println("NORMALLLLLLLLLLLLLLLl"); //DEBUG
			Normal_getChildren(dice.getValues(), children, pN, player);
		}*/
		
		yolo_getChildren(dice.getValues(), children, player);
		
		System.out.println("Children num: "+children.size());
		return children;
		
	}
	
	
	
	private int getEaten(Player p) {
		
		return eaten[p.ordinal()];
	}
	
	private boolean hasReachedDestination(Player p) {
			
		return (p == Player.GREEN)? _hasGreenReachedDestination() : _hasRedReachedDestination();
	}
	
	private void multiBreed(int move, Board parent, Player player, int where, HashSet<Board> level, boolean inDestination){
		Board child;
		int n = player.getSign();
		int pos = (inDestination? player.getBearOffPos() : player.getStart()) - n; //-->>> pN - n
		
		if(parent.getEaten(player) > 0){
			child = new Board(parent);
			if(!breed(child, pos, move, player, child.getTotalMove(), where)) return;
			child.setLastPlayedMove(new Move( child.getTotalMove()));
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
	
	private void yolo_getChildren(int[] move, HashSet<Board> children, Player player){
		
		HashSet<Board> level1 = new HashSet<Board>();
		HashSet<Board> level1_r = new HashSet<Board>(); //reversed
		HashSet<Board> level2 = new HashSet<Board>();
	//	HashSet<Board> level2_r = new HashSet<Board>();
		
		HashSet<Board> level3 = new HashSet<Board>();
		HashSet<Board> level4 = new HashSet<Board>();
		
		Move.resetMove(this.totalMove, 0);
		
		while(true){
			//first move
			multiBreed(move[0], this, player, 0, level1, hasReachedDestination(player));
			
			System.out.println("************* LEVEL 1 " + level1.size());
			
			//second move
			if(!level1.isEmpty()){
				for(Board parent : level1){
					multiBreed(move[1], parent, player, 1, level2, hasReachedDestination(player));
				}
				System.out.println("************* LEVEL 2 " + level2.size());
			}
			
			if(dice.isDouble()){
				//third move
				if(!level2.isEmpty()){
					for(Board parent : level2){
						multiBreed(move[0], parent, player, 2, level3, hasReachedDestination(player));
					}
					System.out.println("************* LEVEL 3 " + level3.size());
				} else { children.addAll(level1); break; }
				
				//fourth move
				if(!level3.isEmpty()){
					for(Board parent : level3){
						multiBreed(move[0], parent, player, 3, level4, hasReachedDestination(player));
					}
					System.out.println("************* LEVEL 4 " + level4.size());
				} else { children.addAll(level2); break; }
				
				if(level4.isEmpty()){ children.addAll(level3); break; }
				
				//finally
				children.addAll(level4);
				break;
				
			} else { //do the reverse
				
				Move.resetMove(this.getTotalMove(), 0);
				//first move - reversed
				multiBreed(move[1], this, player, 0, level1_r, hasReachedDestination(player));
				
				System.out.println("************* LEVEL 1_r " + level1_r.size());
				
				//second move
				if(!level1_r.isEmpty()){
					for(Board parent : level1_r){
						multiBreed(move[0], parent, player, 1, level2, hasReachedDestination(player));
					}
					System.out.println("************* LEVEL 2_r " + level2.size());
				}
				
				if(level2.isEmpty()){ children.addAll(level1); children.addAll(level1_r); break; }
				
				//finally
				children.addAll(level2);
				break;
			}
		}
		
	}
	
	
	
	//pN->18 when green playes, 5 when red plays
	private void BearOff_getChildren(int[] move, HashSet<Board> children, int pN, Player player){
		
		//-----> o kwdikas moiazei poli me tn Normal_getChildren
		//parola auta iparxoun merikes megales diafores
		//1) se opoiodipote for mporei na vre8oume se teliki katastasi...opote prepei na mporoume na diakopsoume tn diadikasia se opoiodipote
		//for kai na pros8esoume to paidi, akoma ki an eixame ki alles kiniseis na kanoume pou profanws dn ginontai
		//2) ola ta for pane mexri to 6 giati stn perioxi mazematos prepei na elegxoume ola ta poulia an mporoume na ta mazepsoume...
		//isws se merikes periptwseis (px se gemati perioxi mazematos) einai perrito auto giati px ta perissotera isValidMove 8a vgainoun false/
		//opote dn xreiazetai na kanoume kai ta panta, omws etsi einai pio aplo.....an skefteite allo pio grigoro tropo ilopoiisis alla3te to
		//3) epeidi elegxontai kai oi 6 8eseis xwris kati tou stil 6-move[0] px, mporoume to reversed na to ilopoiisoume opws t exw edw
		//Auti i morfi dn efarmovetai stn Normal_getChildren epeidi akrivws exoume auta ta oria sto for.....parola auta autos o tropos dn einai aparaitita apodotikos
		//giati 8a dimiourgisei pi8anwn polla paidia p dn 8a proste8oun pote stn children....ki auto mporei na alla3ei, dn exei kai poli simasia...i logiki omws einai idia
		
		System.out.println("ax"); //DEBUG
		
		Board child;
		Board child2;
		int[][] totalMove = new int[4][2];
		Move.resetMove(totalMove,0);
		int n = player.getSign();
		int fMove = pN - n; //fMove -> first move
		int sMove = pN - n; //sMove -> second move
		int tMove = pN - n;
		int foMove = pN - n;
		
		for(int i=0; i < 6; ++i){
			
			fMove += n;
			
			child = new Board(this);
			
			if(!breed(child, fMove, move[0], player, totalMove, 0)) continue;
			
			if(child.isTerminal()){
				//totalMove[1][0] = -99;
				//totalMove[1][1] = -99;
				child.setLastPlayedMove(new Move(totalMove));
				children.add(child);
				continue;
			}
			
		
			for(int j=0; j < 6; ++j){
				
				sMove += n;
				
				child2 = new Board(child);
				if(!breed(child2, sMove, move[1], player, totalMove, 1)) continue;
				
				if(child2.isTerminal()){
					child2.setLastPlayedMove(new Move(totalMove));
					children.add(child2);
					continue; //pianei an einai mesa sto if????
				}
				
				if(dice.isDouble()){  //-->twra autos o elegxos prepei na ginetai sinexeia...
					Board child3;
					Board child4;
					for(int k=0; k <6; ++k){
						
						tMove += n;
						
						child3 = new Board(child2);
						if(!breed(child3, tMove, move[0], player, totalMove, 2)) continue;
						
						
						if(child3.isTerminal()){

							child3.setLastPlayedMove(new Move(totalMove));
							children.add(child3);
							continue; //pianei an einai mesa sto if????
						}
						
						for(int h=0; h < 6; ++h){
							
							foMove += n;
							
							child4 = new Board(child3);
							if(!breed(child4, foMove, move[0], player, totalMove, 3)) continue;
							
							//edw kai terminal na einai to paidi prosti8etai kanonika, dn xreiazetai elegxos, giati exoun ginei 2 kiniseis...
							//-----> prepei na ginetai kai edw!
							child4.setLastPlayedMove(new Move(totalMove));
							children.add(child4);
						}
						foMove = pN - n;
						Move.resetMove(totalMove,3);
					}
					
					tMove = pN-n;
				} else {
					child2.setLastPlayedMove(new Move(totalMove));
					children.add(child2);
				}

				Move.resetMove(totalMove,2);
				
			}
			sMove = pN-n;
			Move.resetMove(totalMove,1);
		}
		
		if(!dice.isDouble()){
			System.out.println("NOT DOUBLE");
			fMove = pN - n; //fMove -> first move
			sMove = pN - n; //sMove -> second move

			Move.resetMove(totalMove,0);
			//******************************* reverse child *******
		
			for(int i=0; i < 6; ++i){
			
				fMove += n;
				child = new Board(this);
				
				if(!breed(child, fMove, move[1], player, totalMove, 0)) continue;
				
				if(child.isTerminal()){
					child.setLastPlayedMove(new Move(totalMove));
					children.add(child);
					continue; //pianei an einai mesa sto if????
				}
				
				for(int j=0; j < 6; ++j){
					
					sMove += n;
					child2 = new Board(this);
					
					if(!breed(child, sMove, move[0], player, totalMove, 1)) continue;
					
					child2.setLastPlayedMove(new Move(totalMove));
					children.add(child2);
				}

				Move.resetMove(totalMove,1);
				sMove = pN - n;
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
		/*table[POS_NUM_0] = 2;
		table[POS_NUM_5] = -5;
		table[POS_NUM_7] = -3;
		table[POS_NUM_11] = 5;
		table[POS_NUM_12] = -5;
		table[POS_NUM_16] = 3;
		table[POS_NUM_18] = 5;
		table[POS_NUM_23] = -2;*/
		
		//TEST CODE
		table[1] = -5;
		table[2] = -2;
		table[3] = -3;
		table[5] = -5;
		//table[9] = -1;
		
		table[23] = 5;
		table[22] = 2;
		table[21] = 3;
		table[19] = 4;
		table[14] = 1;
		
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

			System.out.println("wrong direction bro?");
			return false;
		}
		
		if(!isValidPick(pos, player)){

			System.out.println("not valid pick bro?");
			return false;
		}
		//this position does not contain any of the player's pieces
		
		if(!isValidTarget(pos + move, player)){

			System.out.println("wrong target bro?");
			return false;
		}
		
		if(hasReachedDestination(player)){

			System.out.println("are u valid bear off bro??");
			return isValidBearOff(pos, pos+move, player);
			
		}
		
		return true;
	}
	
	public boolean isValidBearOff(int pos, int finalPos, Player player){
		
		int move = player.getSign()*(finalPos - pos);

		System.out.println("ur move is "+move);
		int[] possibleMoves = dice.getValues();
		
		if(finalPos >= 24){
			System.out.println("final position > 24 : "+finalPos);
			//wrong bear off area
			if(!(player == Player.GREEN)){
				
				System.out.println("wrong player/direction");
				
				return false;
			}
			
			if(colorAt(24 - move) == player){
				if(pos != 24 - move) return false;
			}else{
				if((pos > 24 - move) && hasPreviousNeighbours(pos-1, player)){
					System.out.println("neighborz "+hasPreviousNeighbours(pos-1, player));
					return false;
				}
			}
			
		} else if (finalPos <= -1 ) {
			//wrong bear off area
			if(!(player == Player.RED)){

				System.out.println("wrong player/direction");
				
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
		if(pos >= 0 && pos <= 23){;
			if(colorAt(pos) == player){
				return true;
			} else {
	
				if(player == Player.GREEN) System.out.println("WTF");
				return false;
			}
		}else if (pos == -1){ // start position is the bar with eaten pieces for the green player
	
			System.out.println("green eaten wants to get in board!!! hide");
			if(player != Player.GREEN) return false;
			
			return (eaten[0] > 0);
		}else if (pos == 24){ // start position is the bar with eaten pieces for the red player
	
			System.out.println("red eaten wants to get in board!!! hide");
			if(player != Player.RED) return false;
			//System.out.println(pos+" "+eaten[1]);
			return (eaten[1] > 0);
		}else{
			return false;
		}
	}
	
	/** Performs the move
	 * @param pos current position of the piece to move
	 * @param target the target the piece has to move
	 * @param n n = -1 for RED, n = 1 for GREEN
	 */
	//****allagi ths makeMove giati pleon apla kanei mia kinisi kai telos *****//
	
	protected void makeMove(int pos, int target, int n){
		//validity of the move is already checked
		System.out.println("pos "+pos+" target "+target);
		if((pos > -1) && (pos < 24)){
			

			System.out.println("table before pick "+table[pos]);
			//normal move
			table[pos] -= n; //pick that piece 

			System.out.println("pick "+pos+" table "+table[pos]);
		
		} else if (pos < -1) {
			return; //no other moves //possibly it's a single move by the player
		} else { 
			if(n == 1){//GREEN-PLAYER
				if(pos == -1){
					eaten[0]--;
					System.out.println("eaten gotten. green eatens left: "+ eaten[0]);
				}
					//else no move happens...positions wrong
				//but the validity is checked already...
			}else{//n == -1 RED-CPU
				if(pos == 24){
					eaten[1]--;
					System.out.println("eaten gotten. reds eatens left: "+ eaten[1]);
				}
				//else no move happens...positions wrong
				//but the validity is checked already...
			}
		}
		
		if((target > -1) && (target < 24)){
			
			table[target] += n; //move that piece here
			System.out.println("target "+target+" table "+table[target]);
			if(table[target] == 0){//because of the checked validity this means one of the opponents piece was hit
				table[target] += n;

				System.out.println(" table now "+table[target]);
				eaten[(n+1)/2]++; //eating time

				System.out.println("eaten!!! player eaten: "+(n+1)/2+" his eatens now: "+eaten[(n+1)/2]);
				if((n+1)/2 == 1){
					if(target < 6)
						piecesATdestination[1]--; //one red piece from this area got eaten
				}else{ //-->dn to xes valei.....
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
		
		//ne
		isTerminal();
	}
	
	protected void makeTotalMove(Move m, int n){ //----> 8a mporouse na einai boolean kai na epistrefei an einai termatiki i katastasi
		//---> stn opoia pige i oxi
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
	
	public boolean isValidTarget(int moveTarget, Player player){
		
		if(moveTarget >= 0 && moveTarget <= 23){
			int n = player.getSign();
			int signm = (int) Math.signum(table[moveTarget]);
			if(signm == n || table[moveTarget]+n == 0 || table[moveTarget] == 0)
				//this position either contains player's pieces, or it contains just one of opponent's pieces, either is empty
				return true;
			else 
				return false;
		}else if (moveTarget < 0){ // o kokkinos mazeuei
			if(player != Player.RED) return false;
			return _hasRedReachedDestination();
		}else if (moveTarget > 23){ // o prasinos mazeuei

			if(player != Player.GREEN){

				System.out.println("la8os paiktis");
				return false;
			}

			System.out.println("STON PROORISMO: "+_hasGreenReachedDestination());
			return _hasGreenReachedDestination();
		}else{
			return false;
		}
	}
	
	/**
	 * Check if the moving direction is correct
	 */
	protected boolean checkDirection(int pos, int target, Player player) {
		if ((player == Player.RED && target >= pos) || (player == Player.GREEN && target <= pos)) {
			//setStatus("You're going in the wrong direction!");
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
    
    public Player colorAt(int pos){
    	if(table[pos] < 0) return Player.RED;
    	else if (table[pos] > 0) return Player.GREEN;
    	else return Player.NONE;
    }
    
    //DEBUG
    public boolean _hasRedReachedDestination(){
    	short reds = 0;
		for(int i = 0; i < 6; ++i){
			if(table[i] < 0)
				reds -= table[i];
		}
		return (reds+freedPieces[1] == 15)? true : false;
    }
    
    //DEBUG
    public boolean _hasGreenReachedDestination(){
    	short greens = 0;
		for(int i = 18; i < 24; ++i){
			if(table[i] > 0)
				greens += table[i];
		}
		return (greens+freedPieces[0] == 15)? true : false;
    }
    
	
    /**
     * @return true if Red has reached the final destination on the board
     */
    public boolean hasRedReachedDestination(){
    	/*short reds = 0;
		for(int i = 0; i < 6; ++i){
			if(table[i] < 0)
				reds += table[i];
		}*/
        return piecesATdestination[1]+freedPieces[1] == 15;
    }

    /**
     * @return true if Green has reached the final destination on the board
     */
    public boolean hasGreenReachedDestination(){
       /* short greens = 0;
        for(int i = 18; i < 24; ++i){
        	if(table[i] > 0)
				greens += table[i];
        }*/
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

    	//return PIECE_TOTAL_NUM - eaten[0];
    }
    
    public int getGreensLeft(){
    	return PIECE_TOTAL_NUM - eaten[1] - freedPieces[1];

    	//return PIECE_TOTAL_NUM - eaten[1];
    }

	
	protected int piecesOnBar(Player player){
		
		if (player == Player.GREEN && eaten[0] > 0)
			return eaten[0];
		else if (player == Player.RED && eaten[1] > 0)
			return eaten[1];
		else return 0;
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
    		//--> afou by default o winner einai None edw dn to vaze na kanei setWinner(Player.NONE)
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