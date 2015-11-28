public class Evaluation {
  
	private static final int  positions= 24;

	public static final int Vmax = 10000;
	public static final int Vmin = -10000;
	
	/*
	 * player->0 for green checkers 
     *       ->1 for red checkers 
	 */
	 public static float boardScore(Board b, int player)
	 {
	   int score=evaluateCheckers(b,player);//tha trexei se kathe board.An px mou faei ena pouli xekinaei apo thn arxh.
	   score-=threatenedCheckers(b,player);
	  
	   //******NEW**********************************************************
	   //DYSTYXVWS KALWNTAS TH getNumberOf_OpponentsCheckers PROSTHETW OUTWS H ALLWS POLUPLOKOTHTA.SKEFTOMAI AN 
	   //TYXEI KAI TON PIASW THA HTAN KALO AN DEN EXW MAZEPSEI OLA TA POULIA STH PERIOXH MOU NA EXW TOULAXISTON PORTES GIA NA TON
	   //EMPODISW NA FYGEI.WSTOSO DINONTAS VAROS STIS PORTES ISWS KATHUSTERW TA POULIA NA FTASOUN STH HOME AREA POU DEN ME 
	   //SUMFEREI IDIWS AN DEN EXW PIASEI POULI TOU ANTIPALOU.--->AYTES TIS SKEPSEIS EKANA KAI EVALA TH PARAKATW SUNTHIKI.
	   if(getNumberOf_OpponentsCheckers(b,player)>0 && b.getHomeCheckers()[player]<11)//check if player has at least one opponent's checker in his home area 
	   {                                                                              // and if he/she has less than 11 checkers in his/her home area which means that he/she is 
		                                                                              //not in 'final' situation.
		   score+=evaluateDoors(b,player); 
	   }
	   //********************************************************************
      
	   if(b.getEaten()[1-player]>0)//check if player has hit at least an opponent's checker
	   {
    	   score-= penalisedHitting_1(b,player);
    	 //*****NEW*************
    	   if(penalisedHitting_2(b,player)==0)//Check if player loses the game
    	   {
    		   score+=(b.getEaten()[1-player]*80);//Player's score increases depending on the number of eaten checkers. 
    	   }                                      //because player delays opponent's move. 
    	   else
    	   {
    		   score-=(b.getEaten()[1-player]*penalisedHitting_2(b,player));////Player's score decreases depending on the number of eaten checkers.    
    	   }
	       //************NEW******
    	   
    	   score+= (getNumberOf_FinalBlocks(b,player)*5);
        }
	   	score+=b.getFreedPieces()[player]*20;
        return score;
	 } 
    
	 /*
	  * This function weights your checkers more if they are closer to your home
	  * and penalises you for the opponent's checkers that are close to his/her home.
	  * Also it gives more weight for the checkers that are at home and reduces your  
	  * score more for the opponent's checkers that are at his/her home.
	  * It also  penalises you for the checkers you have on bar.
	  * player->0 for green checkers 
	  *       ->1 for red checkers
	  */    
	 private static int evaluateCheckers(Board b, int player)
	 {
		 int score1=0;
		 int [] board = b.getTable();
		 for (int i=0;i<positions;i++)
	     {
	        if(board[i]>0)
	           score1= score1+(1-player)*board[i]*i-player*board[i]*i;
	        else
	           score1= score1+(1-player)*board[i]*(positions-i-1)-player*board[i]*(positions-i-1);
	      }
		  return score1+(20*b.getHomeCheckers()[player])-(20*b.getHomeCheckers()[1-player])-(20*b.getEaten()[player]);//----->NEW prosthesa varos gia ta poulia pou einai sth home area 
	 }                                                                                   //******kai afairw gia ta poulia tou antipalou pou einai sth dikh tou home
	                                                                                      //kai afairw gia ta poulia pou m exei piasei o antipalos
	 /*
	  * 
	  * atLeastTwoCheckersPerPoint-> each time contains the number of doors that are in row.If there's a gap it becomes zero
	  * doors-> numbers of green checkers doors at 13-19 region(player_0) or 
	  *         numbers of red checkers doors at 4-10 region(player_1)  
	  * inrow-> contains the maximum number of doors that  are in row 
	  * 
	  * This function calculates the score depending on the maximum number 
	  * of doors in row and the total number of doors.
	  *
	  * 
	  */
	 private static int evaluateDoors(Board b, int player)
	 {
		 int doors=0;
	     int inrow=0;
	     int atLeastTwoCheckersPerPoint=0;
	     int [] board = b.getTable();
	     for(int i=(player*4+13*(1-player));i<(player*10+19*(1-player));i++)
	     {
	    	 if((player*board[i]+1-player)<(-player+(1-player)*board[i]))
	    	 {
	    		 atLeastTwoCheckersPerPoint++;
	    	     doors++;
	    	 }
	    	 else
	         {
	                if(atLeastTwoCheckersPerPoint>inrow)
	                	inrow=atLeastTwoCheckersPerPoint;
	                atLeastTwoCheckersPerPoint=0;
	         }
	     
	     }
	     if(atLeastTwoCheckersPerPoint>inrow)
	            inrow=atLeastTwoCheckersPerPoint;
	     
	     return (doors+inrow)*15;
	 }
     
	 //Returns the number of threatened checkers.Threatened checkers are the checkers that can be hit by the opponent.
	 //So if there's a single checker and there is not any opponent's checkers before it, this single checker does not affect the score.  
	 private static int threatenedCheckers(Board b, int player)
	 {
		 int singles=0;
		 int [] board = b.getTable();
		 if(player==0)
	     {
	            int i=positions-1;
	            //skip checkers that cannot be threatened
	            while(i>=0 && board[i]>=0)
	                i--;
	            for(int y=0;y<i;y++)
	            {
	                if(board[i]==1)
	                	singles++;
	            }
	      }
	      else
	        {
	            int i=0;
	            //skip checkers that cannot be threatened
	            while(i<positions && board[i]<=0)
	                i++;
	            for(int y=(i+1);y<positions;y++)
	            {
	                if(board[i]==-1)
	                    singles++;
	            }
	        }
	        return singles;
	 }
	 
	 
	 
	 /*
	  * Returns the penalty of player's single checkers that are in his/her home area depending on the total number of  
	  * his/her checkers that are in his/her home  area
	  */
      private static int penalisedHitting_1(Board b,int player)
	 {  
    	int [] home = b.getHomeCheckers();
    	int singles=0;
		if(1<=home[player] && home[player]<=10)//If player has 1-10 checkers at his/her area
		{
		   singles = numberOfSingles(b,player);//take the player's single checkers of his/her home area 
		   return singles*20;
			
		} 
		else if(home[player]>10)//Now player has picked most of his/her checkers in his/her home area
		{
		   singles = numberOfSingles(b,player);
		   return singles*50;
		}
		return singles;
		 
	 }
      
      /*
       * 
       *Returns a penalty for a wrong move when player leads.
       */
      private static int penalisedHitting_2(Board b,int player)
      {
    	  int penalty=0;
    	  int [] home = b.getHomeCheckers();
    	  
    	  if(home[player]>home[1-player])//check If player has more checkers than his/her opponent in his/her home area 
    	  {
    		 if(numberOfcheckers(b,player)>numberOfcheckers(b,1-player))
    			 {
    				 penalty=80;
    			 }
    	   }
    	   return penalty;
    	 }
      
      /*
      *
      *It has a lot in common with the evaluateDoors function.
      *It returns the number of blocks in the player's home area. 
      *
      */
      private static int getNumberOf_FinalBlocks(Board b,int player)
      {
    	  int blocks=0;
    	  int [] board = b.getTable();
    	  int [] home = b.getHomeCheckers();
    	  if(home[player]>=5)
    	  {
    		  for(int i=(player*0+18*(1-player));i<(player*5+23*(1-player));i++)
    		  {
    			  if((player*board[i]+1-player)<(-player+(1-player)*board[i]))
    			  {
    				  blocks++;
    			  }//if
    			  
    		   }//for  
    	  }
    	  return blocks;
       }
      
      //******************NEW********************************
       //It returns the number of opponent's checkers 
      //from the home area of player 
      public static int getNumberOf_OpponentsCheckers(Board b,int player)
      {   
      	int sum=0;
      	for(int i=(player*0+18*(1-player));i<(player*5+23*(1-player));i++)
          {
      		if (colorAt(b,i)==(1-player))
      		{
      			sum+=Math.abs(player*b.getNumberOfPiecesAt(i)+(1-player)*b.getNumberOfPiecesAt(i));
       		}	
          }
      	return sum;	
      }
      //************NEW******************************
      
      
    //It returns the number of checkers 
      //from the 12-17 area for the player_0(green) or from the 6-11 area for the player_1(red) 
      public static int numberOfcheckers(Board b,int player)
      {   
      	int sum=0;
      	for(int i=(player*6+12*(1-player));i<(player*11+17*(1-player));i++)
          {
      		if (colorAt(b,i)==player){
              sum+=player*b.getNumberOfPiecesAt(i)+(1-player)*b.getNumberOfPiecesAt(i);
      		}	
          }
      	return sum;	
      }
      
      
    //It returns the number of green single checkers from the 18-23 area for the player_0(green)
    // or the number of red singles from the 0-5 area for the player_1(red)
      public static int numberOfSingles(Board b,int player)
      {
      	int sum=0;	
      	for(int i=(player*0+18*(1-player));i<(player*5+23*(1-player));i++)
        { 
      		if (colorAt(b,i)==player)
      		{
      		  sum+=Math.abs(player*b.getNumberOfPiecesAt(i)+(1-player)*b.getNumberOfPiecesAt(i))==1?1:0;
      		} 
      	}
      	return sum;	
       }
      
      
      //*************NEW******************
      //Return 1 for red or 0 for green or -1 for none 
      public static int colorAt(Board b,int pos)
      {
    	int [] board = b.getTable();  
      	if(board[pos] < 0) 
      		return 1;//red
      	else if (board[pos] > 0) 
      		return 0;//green
      	else return -1;//none
      }
      
      
    }

