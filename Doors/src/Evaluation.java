

public class Evaluation {
  
	private static final int  positions= 24;
	
	   
	 public float boardScore(Board b, int player)
	 {
	   int score=evaluateCheckers(b,player);
	   score+=evaluateDoors(b,player);
	   score-=threatenedCheckers(b,player);
	   return score;
	 } 
    
	 /*
	  * This function weights your checkers more if they are closer to your home
	  * and penalises you for the opponent's checkers that are close to his/her home.
	  * player->0 for green checkers 
	  *       ->1 for red checkers
	  */    
	 private int evaluateCheckers(Board b, int player)
	 {
		 int score1=0;
		 int [] board = b.getTable();
		 for (int i=0;i<positions;i++)
	     {
	        if(board[i]<0)
	           score1= score1+player*board[i]*i-(1-player)*board[i]*i;
	        else
	           score1= score1-player*board[i]*(positions-i-1)+(1-player)*board[i]*(positions-i-1);
	      }
		  return score1;
	 }
	 
	 /*
	  * 
	  * atLeastTwoCheckersPerPoint-> each time contains the number of doors that are in row.If there's a gap it becomes zero
	  * doors-> numbers of green checkers doors at 4-10 region(player_0) or 
	  *         numbers of red checkers doors at 13-19 region(player_1)  
	  * inrow-> contains the maximum number of doors that  are in row 
	  * 
	  * This function calculates the score depending on the number 
	  * of doors in row and the total number of doors
	  * 
	  */
	 private int evaluateDoors(Board b, int player)
	 {
		 int doors=0;
	     int inrow=0;
	     int atLeastTwoCheckersPerPoint=0;
	     int [] board = b.getTable();
	     for(int i=((1-player)*4+13*player);i<((1-player)*10+19*player);i++)
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
	     
	     return (doors+inrow)*20;
	 }
     
	 //Returns the number of threatened checkers
	 private int threatenedCheckers(Board b, int player)
	 {
		 int singles=0;
		 int [] board = b.getTable();
		 if(player==1)
	        {
	            int i=positions-1;
	            //skip checkers that cannot be threatened
	            while(i>=0 && board[i]<=0)
	                i--;
	            for(int y=0;y<i;y++)
	            {
	                if(board[i]==-1)
	                	singles++;
	            }
	        }
	        else
	        {
	            int i=0;
	            //skip checkers that cannot be threatened
	            while(i<positions && board[i]>=0)
	                i++;
	            for(int y=(i+1);y<positions;y++)
	            {
	                if(board[i]==1)
	                    singles++;
	            }
	        }
	        return singles;
	 }
}
