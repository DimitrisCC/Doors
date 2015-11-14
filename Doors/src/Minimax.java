import java.util.ArrayList;

public class Minimax {
	
	// auta 8a figoun apo dw! deite tin ilopoiisi tou chanceValue
	private static int Vmax = 100;
	private static int Vmin = 0;
	
	private static int MAX_LENGTH = 2; // 3ekinaei apo to 0 (opote en teli 3 einai ta epipeda...)
	private static int INF = 100000;
	private ArrayList<Dice> possibleRolls; // isws kalo na alla3ei se oura proteraiotitas me pio panw osa exoun pi8anotita 1/18 kai pio katw osa 1/36
	
	//pros to paron exw 8ewrisei dedomeno oti o min einai panta o kokkinos
	//o max einai panta o prasinos kai o chance einai chance telos pantwn
	//opote mesa sts sinartiseis xrisimopoiw fixed PieceEnums...parola auta epeidi to 8ema isws
	//mporei na veltiw8ei genika to afisa stn dilwsi kai vlepoume
	public Move MinimaxAlgorithm(Board root, Dice d, Player player)
	{
		possibleRolls = Dice.allPossibleRolls(); //initialize possibleRolls
		Move bestMove = maxValue(root, d, 0, -INF, INF);
		return bestMove;
	}
	
	public Move minValue(Board b, Dice d, int treeLength,int alpha, int beta){
		
		if(treeLength == MAX_LENGTH){
			//TODO
			//epistrefei to apotelesma tis euretikis sto b
			//return new Move(lastPlayedMove, <timi euretikis>); //----> edw xreiazetai i proigoumeni kinisi...
		}
		
		Move min = new Move(INF);
		ArrayList<Board> succ = b.getChildren(d, Player.RED);
		int value;
		for(Board current : succ){
			value = chanceValue(current, d, Player.GREEN, treeLength+1, alpha, beta);
			if (value < min.getScore()){
				min.setMove(current.getLastPlayedMoves().get(current.getLastPlayedMoves().size()-1).getMove()); //---->>>DEN KSERW POIA KINHSH ENNOEIS EDW!!!!
				min.setScore(value);
			}
		}
		return min;
	}
	
	public Move maxValue(Board b, Dice d, int treeLength, int alpha, int beta){
		
		if(treeLength == MAX_LENGTH){
			//TODO
			//epistrefei to apotelesma tis euretikis sto b
		}
		
		Move max = new Move(-INF);
		ArrayList<Board> succ = b.getChildren(d, Player.GREEN);
		int value;
		for(Board current : succ){
			value = chanceValue(current, d, Player.RED, treeLength+1, alpha, beta);
			if (value > max.getScore()) {
				max.setMove(current.getLastPlayedMoves().get(current.getLastPlayedMoves().size()-1).getMove()); //---->>>DEN KSERW POIA KINHSH ENNOEIS EDW!!!!
				max.setScore(value);
			}
		}
		return max;
	} 
	
	public int chanceValue(Board b, Dice d, Player green, int treeLength, int alpha, int beta){
		
		float expectedValue = 0; //PROSOXIIIIIIII!!!!! edw isws exoume provlima!!!
		float s = 0;
		float p = 0;
		float currentP = 0;
		int roundedValue = 0;
			//kanonika i timi auti pou 8 prokipsei einai float! opote i 8a valoume na epistrefetai genika
			//float i 8a kanoume stroggilopoiisi!!! edw kanw stroggilopoiisi an einai omws to allazoume
		if(green == Player.GREEN){ //8ewrisa oti to if edw einai pio grigoro (p einai logika) apo to na to eixa enswmatwmeno sto for
			
			for(Dice roll: possibleRolls){
				currentP  = 1/(roll.isDouble()? 36:18);
				Move max = maxValue(b, roll, treeLength+1, alpha, beta);
				s += max.getScore()*currentP;
				p += currentP;
				expectedValue = s + (1-currentP)*Vmax; //----> auto kanonika 8a oristei opws kai to Vmin stn klassi tis euretikis..
				//---> antiproswpeuei tin kaliteri dinati timi p mporei na parei o max (isws mesw tis euretikis dld poia 8a itan i kaliteri timi genika
				//---> pou 8a epestrefe gia auton)
				//---> gia na mn vgazei error pros t paron to dilwnw kapws edw
				roundedValue = Math.round(expectedValue); // gia na mn to ipologizw sinexeia
				if(roundedValue < alpha) return roundedValue; //prionisma a
				//alpha = (roundedValue > alpha) ? roundedValue : alpha;
				//pio apla
				if(roundedValue > alpha) alpha = roundedValue;
			}
			
		}else if(green == Player.RED){
			
			for(Dice roll: possibleRolls){
				currentP  = 1/(roll.isDouble()? 36:18);
				Move min = minValue(b, roll, treeLength+1, alpha, beta);
				expectedValue += min.getScore()*currentP;
				p += currentP;
				expectedValue = s + (1-currentP)*Vmin;
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
