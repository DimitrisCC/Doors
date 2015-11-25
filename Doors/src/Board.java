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
    private ArrayList<Move> lastPlayedMoves;
    
    //the dice of the game
    Dice dice;
    
    //if 15, then you win
    //[0] for green, [1] for red
    private int[] freedPieces; 

    private Player winner = Player.NONE;
    
    public Board(){
        table = new int[24];
        initBoard();
        eaten = new int[2];
        piecesATdestination = new int[2];
        freedPieces = new int[2];
        lastPlayedMoves = new ArrayList<Move>();
        
       /* for(int i=0; i<lastPlayedMove.length; i++)
        	for(int j=0; j<lastPlayedMove[0].length; j++)
        		lastPlayedMove[i][j] = -99;*/
       // player = Player.NONE;
        dice = new Dice();
    }
	
    public Board(Board board){
		table = new int[24];
		setTable(board.getTable());
		eaten = new int[2];
        piecesATdestination = new int[2];
        freedPieces = new int[2];
		
		lastPlayedMoves = new ArrayList<Move>();
		lastPlayedMoves.addAll(board.getLastPlayedMoves());
		
		/* for(int i=0; i<lastPlayedMove.length; i++)
	        	for(int j=0; j<lastPlayedMove[0].length; j++)
	        		lastPlayedMove[i][j] = -99;*/
		//player = board.getPlayer();
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
	
	public void fluctuateEaten(Player player, int amount, int index){
		if(isValidTarget(index, player))
			eaten[player.ordinal()] += amount;
	}
	

	
	//if returned list is empty, no valid moves can be done
	public HashSet<Board> getChildren(Dice dice, Player player){
		HashSet<Board> children = new HashSet<Board>();
		int pN = (player == Player.RED)? RED_START_POS : GREEN_START_POS;
		
		if(eaten[player.ordinal()] > 0 ){
			Eaten_getChildren(dice.getValues(), children, pN, player);
		}else if((player == player.GREEN)? hasGreenReachedDestination() : hasRedReachedDestination()){
			pN = (player == Player.RED)? 5 : 18;
			BearOff_getChildren(dice.getValues(), children, pN, player);
		}else{
			System.out.println("NORMALLLLLLLLLLLLLLLl"); //DEBUG
			Normal_getChildren(dice.getValues(), children, pN, player);
		}
		
		return children;
		
	}
	
	//pN-> 0 when green plays, 23 when red plays
	private void Eaten_getChildren(byte[] move, HashSet<Board> children, int pN, Player player){
		
		//-----> auti ti stigmi dn kanei to e3is: an dn mporeis na kaneis px 4 kiniseis alla mono mexri 3 me oti tropo ki an prospa8eis
		//tote dn s dinei kamia kinisi! (la8os dld alla dn antexa na to dw ki auto tr)
		
		System.out.println("ax"); //DEBUG
	
		Board child;
		//Move theMove = new Move();
		//int[][] playedMove = theMove.getMove();
		//int[][] totalMove = theMove.getMove();
		int[][] totalMove = new int[4][2];
		int pos = 0;
		int target = 0;
		int n = player.getSign();
		int fMove = pN - n; //fMove -> first move
		int sMove = pN - n; //sMove -> second move
		int tMove = pN - n;
		int foMove = pN - n;
		int playerNum = player.ordinal(); // xreiazetai gia to eaten
		int posOfEaten = pN - n;
		
		int[] allMoves = {move[0], move[1], 0, 0};
		if(dice.isDouble()){
			allMoves[2] = allMoves[3] = move[0];
		}
		
		child = new Board(this);
		int i=0;
		int j=0;

		while((eaten[playerNum] > 0 || i < 4)){
			//---> PROSOXI!!! To eaten meiwnetai stn makeMove!!!!
			if(allMoves[i] == 0) break; //this means that for simple moves you will stop iterations with i=3 and you wont try more moves...
			//this will help you to decide whether you ignored one move or the other in the next step (see if statement that follows)
			
			if(child.isValidMove(posOfEaten, posOfEaten + n*allMoves[i], player)){
				pos  = posOfEaten;
				target = posOfEaten + n*allMoves[i];
				
				//theMove.setMove(playedMove);
				child.makeMove(pos, target, n);

				totalMove[j][0] = pos;
				totalMove[j][1] = target;
				
				++j;
			}
			
			++i;
		}
		
		if(eaten[playerNum]==0){
			//maybe u have more moves to add!!!
			if(dice.isDouble()){
				if(j == 4){ // u moved exactly four checkers, the ones eaten
					//u dont have moves to add...
					child.setLastPlayedMove(new Move(totalMove));
					children.add(child);
				}else{
					//vres na kaneis kati edw......exoun meinei alles 4 - (j-1) kiniseis akoma na ginoun alla gia idio zari.....
				}
				
			}else{
				// j will never be 3....(see while statement above)
				if(j == 2){ // u moved exactly two checkers, the ones eaten
					//u dont have moves to add...
					child.setLastPlayedMove(new Move(totalMove));
					children.add(child);
				}else{// j==1 because you had just only one checker eaten
					if(i==1){ //this means you stopped iterations at the first move
						//therefore you must continue the move and then see if the reversed move can be done
						boolean one_at_leat = false;
						for(int k=0; k < 24-allMoves[1]; ++k){
							fMove += n;
							if(child.isValidMove(fMove, fMove + n*allMoves[1], player)){
								one_at_leat = true;
								Board child2 = new Board(child);
								pos = fMove;
								target = fMove + n*allMoves[1];
								
								//theMove.setMove(playedMove);
								child2.makeMove(pos, target, n);

								totalMove[j][0] = pos;
								totalMove[j][1] = target;
								
								child2.setLastPlayedMove(new Move(totalMove));
								children.add(child2);
							}
						}
						
						if(!one_at_leat){ // if no other move could be done you shoud still add child in children
							child.setLastPlayedMove(new Move(totalMove));
							children.add(child);
						}
						
						//the reversed move
						if(child.isValidMove(posOfEaten, posOfEaten + n*allMoves[1], player)){
							pos = posOfEaten;
							target = posOfEaten + n*allMoves[1];
							
							//theMove.setMove(playedMove);
							child.makeMove(pos, target, n);

							totalMove[0][0] = pos;
							totalMove[0][1] = target;
							
							fMove = pN - n; //init again
							one_at_leat = false;
							for(int k=0; k < 24-allMoves[0]; ++k){
								fMove += n;
								if(child.isValidMove(fMove, fMove + n*allMoves[0],player)){
									one_at_leat = true;
									Board child2 = new Board(child);
									pos = fMove;
									target = fMove + n*allMoves[0];
									
									//theMove.setMove(playedMove);
									child2.makeMove(pos, target, n);

									totalMove[1][0] = pos;
									totalMove[1][1] = target;
									
									child2.setLastPlayedMove(new Move(totalMove));
									children.add(child2);
								}
							}
							
							if(!one_at_leat){ // if no other move could be done you shoud still add child in children
								child.setLastPlayedMove(new Move(totalMove));
								children.add(child);
							}
						}
						
					}else{ //i==2 (see comments in while statement above)
						//this  means that you skiped one move because it was not valid, so you must find which one you skiped and make the other one
						
						int skipedMove = (totalMove[0][0] == allMoves[1])? allMoves[0] : allMoves[1];
						boolean one_at_leat = false;
						for(int k=0; k < 24-skipedMove; ++k){
							fMove += n;
							if(child.isValidMove(fMove, fMove + n*skipedMove, player)){
								one_at_leat = true;
								Board child2 = new Board(child);
								pos = fMove;
								target = fMove + n*skipedMove;
								
								//theMove.setMove(playedMove);
								child2.makeMove(pos, target, n);

								totalMove[j][0] = pos;
								totalMove[j][1] = target;
								
								child2.setLastPlayedMove(new Move(totalMove));
								children.add(child2);
							}
						}
						
						if(!one_at_leat){ // if no other move could be done you shoud still add child in children
							child.setLastPlayedMove(new Move(totalMove));
							children.add(child);
						}
					}
				}
				
			}
			
		}else{
			child.setLastPlayedMove(new Move(totalMove));
			children.add(child);
			
		} 
		
	}
	
	//pN-> 0 when green plays, 23 when red plays
	private void Normal_getChildren(byte[] move, HashSet<Board> children, int pN, Player player){
		
		//-----> auti ti stigmi dn kanei to e3is: an dn mporeis na kaneis px 4 kiniseis alla mono mexri 3 me oti tropo ki an prospa8eis
		//tote dn s dinei kamia kinisi! (la8os dld alla dn antexa na to dw ki auto tr)
		
		System.out.println("ax"); //DEBUG
	
		Board child;
		//Move theMove = new Move();
		//int[][] playedMove = theMove.getMove(); //---> den katalavainw ti prospatheis na kaneis
		//int[][] totalMove = theMove.getMove(); //---> giati idio antikeimeno??
		//--> twra oti kai na allazeis sta dyo de tha allazei sto idio antikeimeno?
		//int[][] playedMove = new int[4][2];
		int pos = 0;
		int target = 0;
		int[][] totalMove = new int[4][2];
		
		int n = player.getSign();
		int fMove = pN - n; //fMove -> first move
		int sMove = pN - n; //sMove -> second move
		int tMove = pN - n;
		int foMove = pN - n;
		//int playerNum = player.ordinal(); // xreiazetai gia to eaten
		//int posOfEaten = pN - n;
		
		
		for(int i=0; i < 24-move[0]; ++i){

			fMove += n;
			
			child = new Board(this);
			
			if(!child.isValidMove(fMove, n*move[0], player)) continue; //-->ithele sketo n*move[0] kai oxi thesi
			
			pos = fMove;
			target = fMove + n*move[0];
			
			//theMove.setMove(playedMove);
			child.makeMove(pos, target, n);

			totalMove[0][0] = pos;
			totalMove[0][1] = target;
			
			for(int j=0; j < 24-move[1]; ++j){
	
				sMove += n;
				
				if(!child.isValidMove(sMove, n*move[1], player)) continue;
				
				pos = sMove;
				target = sMove + n*move[0];
				
				//theMove.setMove(playedMove);
				child.makeMove(pos, target, n);
				
				totalMove[1][0] = pos;
				totalMove[1][1] = target;
				
				
				if(dice.isDouble()){  //-->twra autos o elegxos prepei na ginetai sinexeia...

					for(int k=0; k < 24-move[0]; ++k){
						
						tMove += n;
						
						if(!child.isValidMove(tMove, n*move[0], player)) continue;
						
						pos = tMove;
						target = tMove + n*move[0];
						
						//theMove.setMove(playedMove);
						child.makeMove(pos, target, n);
						
						totalMove[2][0] = pos;
						totalMove[2][1] = target;
						
						for(int h=0; h < 24-move[0]; ++h){
							
							foMove += n;
							
							if(!child.isValidMove(foMove, n*move[0], player)) continue;
							
							pos = foMove;
							target = foMove + n*move[0];
							
							//theMove.setMove(playedMove);
							child.makeMove(pos, target, n);
							
							totalMove[3][0] = pos;
							totalMove[3][1] = target;
							
							//-----> prepei na ginetai kai edw!
							child.setLastPlayedMove(new Move(totalMove));
							children.add(child);
						}
					}
					
				} else {
					child.setLastPlayedMove(new Move(totalMove));
					children.add(child);
				}
			}
			
		}
		
		//children with the reversed move
		if(!dice.isDouble()){
			
			fMove = pN - n; //fMove -> first move
			sMove = pN - n; //sMove -> second move
			//******************************* reverse child *******
		
			for(int i=0; i < 24-move[1]; ++i){
			
				fMove += n;
				
				child = new Board(this);
				
				if(!child.isValidMove(fMove, n*move[1], player)) continue;
				
				pos = fMove;
				target = fMove + n*move[1];
				
				//theMove.setMove(playedMove);
				child.makeMove(pos, target, n);

				totalMove[0][0] = pos;
				totalMove[0][1] = target;
				
				for(int j=0; j < 24-move[0]; ++j){
					
					sMove += n;
					
					if(!child.isValidMove(sMove, n*move[0], player)) continue;
					
					pos = sMove;
					target = sMove + n*move[0];
				
					//theMove.setMove(playedMove);
					child.makeMove(pos, target, n);

					totalMove[1][0] = pos;
					totalMove[1][1] = target;
					
					child.setLastPlayedMove(new Move(totalMove));
					children.add(child);
				}
			}
	
		}
		
	}
	
	//pN->18 when green playes, 5 when red plays
	private void BearOff_getChildren(byte[] move, HashSet<Board> children, int pN, Player player){
		
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
		Move theMove = new Move();
		int[][] playedMove = theMove.getMove();
		int[][] totalMove = theMove.getMove();
		int n = player.getSign();
		int fMove = pN - n; //fMove -> first move
		int sMove = pN - n; //sMove -> second move
		int tMove = pN - n;
		int foMove = pN - n;
		
		for(int i=0; i < 6; ++i){
			
			fMove += n;
			
			child = new Board(this);
			
			if(!child.isValidMove(fMove, n*move[0], player)) continue;
			
			int pos = fMove;
			int target = fMove + n*move[0];
			
			//theMove.setMove(playedMove);
			child.makeMove(pos, target, n);

			totalMove[0][0] = pos;
			totalMove[0][1] = target;
			
			
			if(child.isTerminal()){
				child.setLastPlayedMove(new Move(totalMove));
				children.add(child);
				continue; //pianei an einai mesa sto if????
			}
			
		
			for(int j=0; j < 6; ++j){
				
				sMove += n;
				
				if(!child.isValidMove(sMove, n*move[1], player)) continue;
				
				pos = sMove;
				target = sMove + n*move[0];
				
				//theMove.setMove(playedMove);
				child.makeMove(pos, target, n);
				
				totalMove[1][0] = pos;
				totalMove[1][1] = target;
				
				if(child.isTerminal()){
					child.setLastPlayedMove(new Move(totalMove));
					children.add(child);
					continue; //pianei an einai mesa sto if????
				}
				
				if(dice.isDouble()){  //-->twra autos o elegxos prepei na ginetai sinexeia...
					for(int k=0; k <6; ++k){
						
						tMove += n;
						
						if(!child.isValidMove(tMove, n*move[0], player)) continue;
						
						pos = tMove;
						target = tMove + n*move[0];
						
						//theMove.setMove(playedMove);
						child.makeMove(pos, target, n);
						
						totalMove[2][0] = pos;
						totalMove[2][1] = target;
						
						if(child.isTerminal()){
							child.setLastPlayedMove(new Move(totalMove));
							children.add(child);
							continue; //pianei an einai mesa sto if????
						}
						
						for(int h=0; h < 6; ++h){
							
							foMove += n;
							
							if(!child.isValidMove(foMove, n*move[0], player)) continue;
							
							pos = foMove;
							target = n*move[0];
							
							//theMove.setMove(playedMove);
							child.makeMove(pos, target, n);
							
							totalMove[3][0] = pos;
							totalMove[3][1] = target;
							
							//edw kai terminal na einai to paidi prosti8etai kanonika, dn xreiazetai elegxos, giati exoun ginei 2 kiniseis...
							//-----> prepei na ginetai kai edw!
							child.setLastPlayedMove(new Move(totalMove));
							children.add(child);
						}
					}
					
				}else{
					child.setLastPlayedMove(new Move(totalMove));
					children.add(child);
					
					if(isValidMove(fMove, n*move[1], player)){
						
						pos = fMove;
						target = fMove + n*move[1];
						
						//theMove.setMove(playedMove);
						child.makeMove(pos, target, n);
						
						totalMove[0][0] = pos;
						totalMove[0][1] = target;
						
						if(child.isTerminal()){
							child.setLastPlayedMove(new Move(totalMove));
							children.add(child);
						}
						
						if(child.isValidMove(sMove, n*move[0], player)){
							Board reversedChild = new Board(this);
							
							pos = sMove;
							target = sMove + n*move[0];
							
							//theMove.setMove(playedMove);
							child.makeMove(pos, target, n);
							
							totalMove[1][0] = pos;
							totalMove[1][1] = target;
						}
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
		if(!checkDirection(pos, pos + move, player)) return false;
		
		if(!isValidPick(pos, player)) return false;
		//this position does not contain any of the player's pieces
		
		if(!isValidTarget(pos + move, player)) return false;
		
		byte[] possibleMoves = dice.getValues();
		if(((possibleMoves[0]+possibleMoves[1])== move)&&
				!isValidTarget(pos + possibleMoves[0], player) && 
					!isValidTarget(pos + possibleMoves[0], player) )  return false;
		
		if((pos+move <= -1)||(pos+move >= 24)) return isValidBearOff(pos, pos+move, player);
		
		return true;
	}
	
	private boolean isValidBearOff(int pos, int finalPos, Player player){
		
		
		int move = finalPos + player.getSign()*pos;
		byte[] possibleMoves = dice.getValues();
		
		if(finalPos >= 24){
			if(!(player == Player.GREEN)||!hasGreenReachedDestination()) return false;
			
			if ((finalPos == 24) && ((move == possibleMoves[0]) || (move == possibleMoves[1]))) return true;
			
			for(int i = pos-1 ; i > 17; --i ){
				if(colorAt(i) != player) return false;
			}
			
			if((pos + possibleMoves[0] < 24)|| (pos + possibleMoves[1] < 24)) return false;
			
		}else{
			if(!(player == Player.RED)||!hasRedReachedDestination()) return false;
			
			if ((finalPos == -1) && ((move == possibleMoves[0]) || (move == possibleMoves[1]))) return true;
			
			for(int i =  pos+1 ; i < 6; ++i ){
				if(colorAt(i) != player) return false;
			}
			
			if((pos - possibleMoves[0] > -1)|| (pos - possibleMoves[1] > -1)) return false;
			
		}
		
		return true;
	}
	
	
	
	
	
	/**
	 * Checks if you picked a correct piece
	 */
	public boolean isValidPick(int pos, Player player){
		if(pos >= 0 && pos <= 23){;
			if(Math.signum(table[pos]) == player.getSign()){
				return true;
			} else {
				return false;
			}
		}else if (pos == -1){ // start position is the bar with eaten pieces for the green player
			return (eaten[0] > 0);
		}else if (pos == 24){ // start position is the bar with eaten pieces for the red player
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
		
		if((pos > -1) && (pos < 24)){
			//normal move
			table[pos] -= n; //pick that piece 
		
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
				eaten[(n+1)/2]++;

				//eating time
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
			}else{
				if( target < 6 ) piecesATdestination[1]++;
			}
		}else{
			if(n == 1){
				if(target == 24)
					freedPieces[0]++;
			}else{ // n==-1
				if(target == -1)
					freedPieces[1]++;
			}
		}
		isTerminal();
	}
	
	protected void makeTotalMove(Move m, int n){ //----> 8a mporouse na einai boolean kai na epistrefei an einai termatiki i katastasi
		//---> stn opoia pige i oxi
		//validity of the move is already checked
		
		int[][] moveToMake = m.getMove();
		
		for(int i=0; i< moveToMake.length; i++)
		{	
			if(moveToMake[i][0] == moveToMake[i][1]) break; //if this conditions is true then moveToMake[i][0]= moveToMake[i][1]=-99
			
			if((moveToMake[i][0] > -1) && (moveToMake[i][0] < 24)){ //-->> prepei kai ta dyo na alitheuoun -->>>SWSTOS!!!
				//normal move
				table[moveToMake[i][0]] -= n; //pick that piece 
				System.out.println(table[moveToMake[i][0]]+" MAKE MOVE!!!!!!!!!!!!!!!!!");//DEBUG
			
			} else if (moveToMake[i][0] < -1) {
				break; //no other moves //possibly it's a single move by the player
			} else { 
				if(n == 1){//GREEN-PLAYER
					if(moveToMake[i][0] == -1){
						eaten[0]--;
					}
						//else no move happens...positions wrong
					//but the validity is checked already...
				}else{//n == -1 RED-CPU
					if(moveToMake[i][0] == 24){
						eaten[1]--;
					}
					//else no move happens...positions wrong
					//but the validity is checked already...
				}
			}
			
			if((moveToMake[i][1] > -1) && (moveToMake[i][1] < 24)){
				
				table[moveToMake[i][1]] += n; //move that piece here
				if(table[moveToMake[i][1]] == 0){//because of the checked validity this means one of the opponents piece was hit
					table[moveToMake[i][1]] += n;
					eaten[(n+1)/2]++;

					//eating time
					if((n+1)/2 == 1){
						if(moveToMake[i][1] < 6)
							piecesATdestination[1]--; //one red piece from this area got eaten
					}else{ //-->dn to xes valei.....
						if(moveToMake[i][1] > 17)
							piecesATdestination[0]--;
					}
				}
				
				//check if a piece reached destination
				if(n == 1){ 
					if( moveToMake[i][1] > 17 ) piecesATdestination[0]++;
				}else{
					if( moveToMake[i][1] < 6 ) piecesATdestination[1]++;
				}
			}else{
				if(n == 1){
					if(moveToMake[i][1] == 24)
						freedPieces[0]++;
				}else{ // n==-1
					if(moveToMake[i][1] == -1)
						freedPieces[1]++;
				}
			}
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
		if ((player == Player.RED && move >= pos) || (player == Player.GREEN && move <= pos)) {
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
  
    public ArrayList<Move> getLastPlayedMoves(){ return lastPlayedMoves; }
    
    public void setLastPlayedMove(Move move){
    	/*for(int i=0; i<lastPlayedMove.length; i++)
    		for(int j=0; j<lastPlayedMove[0].length; j++)
    			lastPlayedMove[i][j] = move[i][j];*/
    	lastPlayedMoves.add(move);
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
