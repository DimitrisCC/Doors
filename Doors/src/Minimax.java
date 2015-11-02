import java.util.ArrayList;

public class Minimax {
	
	private static int MAX_LENGTH = 2; // 3ekinaei apo to 0 (opote en teli 3 einai ta epipeda...)
	private static int INF = 100000;
	private ArrayList<Dice> possibleRolls;
	
	/*
	public int minValue(Board b, Dice d,PieceEnum player, int treeLength){
		
		
		return 0;
	}
	
	public int maxValue(Board b,Dice d,PieceEnum player, int treeLength){
		
		if(treeLength == MAX_LENGTH){
			//ginetai return i timi tis euretikis gia to b
		}
		
		int max = -INF;
		
		ArrayList<Board> succ = b.getChildren(d, player);
		
		for(Board current : succ){
			
		}
		
		return 0;
	} */
	
	//pros to paron exw 8ewrisei dedomeno oti o min einai panta o kokkinos
	//o max einai panta o prasinos kai o chance einai chance telos pantwn
	//opote mesa sts sinartiseis xrisimopoiw fixed PieceEnums...parola auta epeidi to 8ema isws
	//mporei na veltiw8ei genika to afisa stn dilwsi kai vlepoume
	public Board MinimaxAlgorithm(Board root, Dice d, PieceEnum player)
	{
		possibleRolls = Dice.allPossibleRolls();
		int bestValue = expMinimax(root, d, PieceEnum.GREEN, 0);
		return new Board();
	}
	
	//ola ta kanw edw mesa....epeidi imoun sto proccessing na katalavw ti mou ginetai
	//dn exw prospa8isei na to veltiwsw akoma omws isws mporei na ginei kai kalitero...
	//pros stigmin oles oi leitourgies ginontai se autin edw tn sinartisi
	private int expMinimax(Board b, Dice d, PieceEnum player, int treeLength){
		//dedomenou oti panta o ipologistis paizei prasino tote to prasino einai o max
		//kai to kokkino einai to min...sinepws:
		
		if(treeLength == MAX_LENGTH){
			//TODO
			//epistrefei to apotelesma tis euretikis sto b
		}
		//mia alli lisi edw 8a itan  isws kapoio enum pou na exei times max, min kai chance alla
		//den 3erw pws 8a sisxetizotan me ta alla....
		if(player == PieceEnum.GREEN){ //max
			
			int max = -INF;
			ArrayList<Board> succ = b.getChildren(d, player);
			int value;
			for(Board current : succ){
				value = expMinimax(current, d, PieceEnum.CHANCE, treeLength+1);
				if (value > max) max = value;
			}
			return max;
			
		}else if (player == PieceEnum.RED){ //min
			
			int min = INF;
			ArrayList<Board> succ = b.getChildren(d, player);
			int value;
			for(Board current : succ){
				value = expMinimax(current, d, PieceEnum.CHANCE, treeLength+1);
				if (value < min) min = value;
			}
			return min;
			
		}else{ //chance
			//twra edw iparxei to e3is 8ema: o komvos chance paizei anamesa se komvous
			//min kai max....etsi opws exei ginei i ilopoiisi den 3ereis amesa an prin
			//eixe pai3ei max i min gia na 3ereis ti "paizei" epomenw wste na kaleseis tn
			//expMinimax me swsto orisma....ena trick edw einai to length.
			//Ean lenght mod 3 (epeidi oi 8ewritikoi paiktes einai 3) einai 0 tote paizei o max
			//ean einai 1 o komvos tixis kai an einai 2 o min. Opote gia na vreis poios paizei meta
			//mporeis na kaneis (lenght+1)%3....parola auta emena den m aresei ka8olou auto
			//alla pros stigmin to evala etsi....
			//episis ena akoma mperdema to prokalei to zari.....
			int  nextPlayer = (treeLength+1)%3;
			float expectedValue = 0; //PROSOXIIIIIIII!!!!! edw isws exoume provlima!!!
				//kanonika i timi auti pou 8 prokipsei einai float! opote i 8a valoume na epistrefetai genika
				//float i 8a kanoume stroggilopoiisi!!! edw kanw stroggilopoiisi an einai omws to allazoume
			if(nextPlayer == 0){
				
				for(Dice roll: possibleRolls){
					expectedValue += expMinimax(b, roll, PieceEnum.GREEN, treeLength+1)/(roll.isDouble()? 36:18);
				}
				
			}else if(nextPlayer == 2){
				
				for(Dice roll: possibleRolls){
					expectedValue += expMinimax(b, roll, PieceEnum.RED, treeLength+1)/(roll.isDouble()? 36:18);
				}
			}// gia to == 3 den mas endiaferei giati DEN 8A EINAI ==3....episis dn m aresei to if edw
			// giati isws ginetai stn morfi a? b : c omws epeidi kanonika mporei na einai 3 pragmata
			//to evala etsi...
			

			return Math.round(expectedValue);
			
		}
		
	}
/*	
 * auto den xrisimopoieitai pros to paron...to vasiko m 8ema itan pws dn i3era pws sto telos 
 * 8a psa3w to paidi me tin kaliteri va8mologia...gi auto kai skeftika pws an ginotan xrisi 
 * mias private klassis san tin parakatw isws itan pio eukolo na vroume pio paidi eixe poio score...
 * parola auta olo auto einai mia idea tin opoia den sinexisa mipws stn poreia evriska alli lisi...
	private class Node{
		
		private Board state;
		private int score;
		
		private Node(Board st){
			state = st;
		}
		
		private void setScore(int sc){ score = sc;}
		
		private int getScore(){return score;}
	}
*/
	
	}
