import java.util.ArrayList;
import java.util.HashSet;

public class Minimax {
	
	
	private static int MAX_LENGTH = 4; // 3ekinaei apo to 0 (opote en teli 3 einai ta epipeda...)
	private static int INF = 100000;
	private static ArrayList<Dice> possibleRolls; // isws kalo na alla3ei se oura proteraiotitas me pio panw osa exoun pi8anotita 1/18 kai pio katw osa 1/36
	
	//pros to paron exw 8ewrisei dedomeno oti o min einai panta o kokkinos
	//o max einai panta o prasinos kai o chance einai chance telos pantwn
	//opote mesa sts sinartiseis xrisimopoiw fixed PieceEnums...parola auta epeidi to 8ema isws
	//mporei na veltiw8ei genika to afisa stn dilwsi kai vlepoume
	public static Move MinimaxAlgorithm(Board root, Dice d, Player player) //--> AUTO T PLAYER DN XRISIMOPOIEITAI 
	{System.out.println("minimaxAlgo");//DEBUG
		possibleRolls = Dice.allPossibleRolls(); //initialize possibleRolls
		Move bestMove = maxValue(root, d, 0, player, -INF, INF);
		System.out.println("CHILD USED");
		for(int i=0; i<4; i++){
			System.out.println("from "+bestMove.getMove()[i][0]+" to "+bestMove.getMove()[i][1]);
		}
		return bestMove;
	}
	
	private static Move minValue(Board b, Dice d, int treeLength, Player player, int alpha, int beta){
		//System.out.println("min");//DEBUG
		if(treeLength == MAX_LENGTH){
			//epistrefei to apotelesma tis euretikis sto b
			return new Move(b.getLastPlayedMove().getMove(), (int)Evaluation.boardScore(b,0)); 
		}
		
		Move min = new Move(INF);
		HashSet<Board> succ = b.getChildren(d, player);
		int value;
		for(Board current : succ){
			value = chanceValue(current, d, player.getOpponent(), treeLength+1, alpha, beta);
			if (value < min.getScore()){
				min.setMove(current.getLastPlayedMove().getMove());
				min.setScore(value);
			}
		}
		return min;
	}
	
	private static Move maxValue(Board b, Dice d, int treeLength, Player player, int alpha, int beta){
		//System.out.println("max");//DEBUG
		if(treeLength == MAX_LENGTH){
			//epistrefei to apotelesma tis euretikis sto b
			return new Move(b.getLastPlayedMove().getMove(), (int)Evaluation.boardScore(b,1)); 
		}
		
		Move max = new Move(-INF);
		HashSet<Board> succ = b.getChildren(d, player);
		int value;
		for(Board current : succ){
			value = chanceValue(current, d, player.getOpponent(), treeLength+1, alpha, beta);
			if (value > max.getScore()) {
				max.setMove(current.getLastPlayedMove().getMove());
				max.setScore(value);
			}
		}
		return max;
	} 
	
	private static int chanceValue(Board b, Dice d, Player P, int treeLength, int alpha, int beta){
		System.out.println("gamw tn tixi m"); //DEBUG
		float expectedValue = 0; //PROSOXIIIIIIII!!!!! edw isws exoume provlima!!!
		float s = 0;
		float p = 0;
		float currentP = 0;
		int roundedValue = 0;
			//kanonika i timi auti pou 8 prokipsei einai float! opote i 8a valoume na epistrefetai genika
			//float i 8a kanoume stroggilopoiisi!!! edw kanw stroggilopoiisi an einai omws to allazoume
		if(P == Player.RED){ //8ewrisa oti to if edw einai pio grigoro (p einai logika) apo to na to eixa enswmatwmeno sto for
			
			for(Dice roll: possibleRolls){
				currentP  = 1/(roll.isDouble()? 36:18);
				Move max = maxValue(b, roll, treeLength+1, P, alpha, beta);
				s += max.getScore()*currentP;
				p += currentP;
				expectedValue = s + (1-currentP)*Evaluation.Vmax; //----> auto kanonika 8a oristei opws kai to Vmin stn klassi tis euretikis..
				//---> antiproswpeuei tin kaliteri dinati timi p mporei na parei o max (isws mesw tis euretikis dld poia 8a itan i kaliteri timi genika
				//---> pou 8a epestrefe gia auton)
				//---> gia na mn vgazei error pros t paron to dilwnw kapws edw
				roundedValue = Math.round(expectedValue); // gia na mn to ipologizw sinexeia
				if(roundedValue < alpha) return roundedValue; //prionisma a
				////alpha = (roundedValue > alpha) ? roundedValue : alpha;
				//pio apla
				if(roundedValue > alpha) alpha = roundedValue;
			}
			
		}else if(P == Player.GREEN){
			
			for(Dice roll: possibleRolls){
				currentP  = 1/(roll.isDouble()? 36:18);
				Move min = minValue(b, roll, treeLength+1,P, alpha, beta);
				s += min.getScore()*currentP;
				p += currentP;
				expectedValue = s + (1-currentP)*Evaluation.Vmin;
				roundedValue = Math.round(expectedValue); // gia na mn to ipologizw sinexeia
				if(roundedValue > beta) return roundedValue; //prionisma a
				//alpha = (roundedValue > alpha) ? roundedValue : alpha;
				//pio apla
				if(roundedValue < beta) beta = roundedValue;
			}
		}
		
		return roundedValue; 
	}
	

}

