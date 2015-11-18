
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Draws the GUI of the game: background, pieces, dice
 */

public class BackgammonPanel extends JPanel implements MouseMotionListener  {
	
	private static final long serialVersionUID = 1L; //--> pou xrisimopoieitai?
	
	private static final int BORDER = 30;
	private static final int PIECE_STEP = 25;
	private static final int GREENS_EATEN = 24;
	private static final int POS_END_GREEN = 26;
	private static final int POS_SIZE_PXL = 50;
	private static final int GOTTEN_OUT_BORDER = 690;
	private static final int EATEN_LEFT_BORDER = 340;
	private static final int TOTAL_POS = 24;
	private static final int DICE_X = 480;
	private static final int DICE_Y = 295;
	private static final int DICE_SIZE = 60;
	
	private Image dice1;
	private Image dice2;
	private Image dice3;
	private Image dice4;
	private Image dice5;
	private Image dice6;
	private Image green;
	private Image red;
	private Image background;
	private Board game;
	private Player player;
	private int position;
	private ArrayList<Integer> moveset;
	private byte jumpsYet;
	private int lastPick; //to cleanse the highlight
	private int doneMove; 
	
	private ArrayList<BgButton> buttons;
	private JButton buttonRoll;
	private JLabel statusBar;
	
	private boolean picked;
	private boolean hasPlayerRolled;
	
	private boolean isItMyTurn; //is it the turn of the player?

	public BackgammonPanel(Board game) {
		this.game = game;
		//setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this.setLayout(null);
		setBackground(Toolkit.getDefaultToolkit().createImage("resources//background.png"));
		setDice1(Toolkit.getDefaultToolkit().createImage("resources//dice1.png"));
		setDice2(Toolkit.getDefaultToolkit().createImage("resources//dice2.png"));
		setDice3(Toolkit.getDefaultToolkit().createImage("resources//dice3.png"));
		setDice4(Toolkit.getDefaultToolkit().createImage("resources//dice4.png"));
		setDice5(Toolkit.getDefaultToolkit().createImage("resources//dice5.png"));
		setDice6(Toolkit.getDefaultToolkit().createImage("resources//dice6.png"));
		setGreenPiece(Toolkit.getDefaultToolkit().createImage("resources//green.png"));//--> GreenPeace....
		setRedPiece(Toolkit.getDefaultToolkit().createImage("resources//red.png"));
		
		buttons = new ArrayList<BgButton>();
		picked = false;
		lastPick = -1;
		isItMyTurn = true;
		hasPlayerRolled = false;
		addMouseMotionListener(this);
		drawButtons();
		drawStatusBar();
	}
	
	private void drawStatusBar(){
		statusBar = new JLabel("LET THE GAME BEGIN!!!", SwingConstants.CENTER);
		statusBar.setBounds(0, 640, 720, 20);
		statusBar.setOpaque(true);
		statusBar.setBackground(Color.BLACK);
		statusBar.setForeground(Color.GREEN);
		this.add(statusBar);
	}
	
	public void setStatus(String status){
		statusBar.setText(status);
		statusBar.repaint();
	}
	
	private void drawButtons(){
		
		BgButton button0 = new BgButton("1");
		button0.setBounds(642, 0, 50, 23);
		this.add(button0);
		
		BgButton button1 = new BgButton("2");
		button1.setBounds(591, 0, 50, 23);
		this.add(button1);
		
		BgButton button2 = new BgButton("3");
		button2.setBounds(540, 0, 50, 23);
		this.add(button2);
		
		BgButton button3 = new BgButton("4");
		button3.setBounds(489, 0, 50, 23);
		this.add(button3);
		
		BgButton button4 = new BgButton("5");
		button4.setBounds(438, 0, 50, 23);
		this.add(button4);
		
		BgButton button5 = new BgButton("6");
		button5.setBounds(387, 0, 50, 23);
		this.add(button5);
		
		BgButton button6 = new BgButton("7");
		button6.setBounds(282, 0, 50, 23);
		this.add(button6);
		
		BgButton button7 = new BgButton("8");
		button7.setBounds(231, 0, 50, 23);
		this.add(button7);
		
		BgButton button8 = new BgButton("9");
		button8.setBounds(180, 0, 50, 23);
		this.add(button8);
		
		BgButton button9 = new BgButton("10");
		button9.setBounds(129, 0, 50, 23);
		this.add(button9);
		
		BgButton button10 = new BgButton("11");
		button10.setBounds(78, 0, 50, 23);
		this.add(button10);
		
		BgButton button11 = new BgButton("12");
		button11.setBounds(27, 0, 50, 23);
		this.add(button11);
		
		BgButton button12 = new BgButton("13");
		button12.setBounds(27, 618, 50, 23);
		this.add(button12);
		
		BgButton button13 = new BgButton("14");
		button13.setBounds(78, 618, 50, 23);
		this.add(button13);
		
		BgButton button14 = new BgButton("15");
		button14.setBounds(129, 618, 50, 23);
		this.add(button14);
		
		BgButton button15 = new BgButton("16");
		button15.setBounds(179, 618, 50, 23);
		this.add(button15);
		
		BgButton button16 = new BgButton("17");
		button16.setBounds(230, 618, 50, 23);
		this.add(button16);
		
		BgButton button17 = new BgButton("18");
		button17.setBounds(281, 618, 50, 23);
		this.add(button17);
		
		BgButton button18 = new BgButton("19");
		button18.setBounds(386, 618, 50, 23);
		this.add(button18);
		
		BgButton button19 = new BgButton("20");
		button19.setBounds(437, 618, 50, 23);
		this.add(button19);
		
		BgButton button20 = new BgButton("21");
		button20.setBounds(488, 618, 50, 23);
		this.add(button20);
		
		BgButton button21 = new BgButton("22");
		button21.setBounds(539, 618, 50, 23);
		this.add(button21);
		
		BgButton button22 = new BgButton("23");
		button22.setBounds(590, 618, 50, 23);
		this.add(button22);
		
		BgButton button23 = new BgButton("24");
		button23.setBounds(641, 618, 50, 23);
		this.add(button23);
		
		buttons.add(button0);
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		buttons.add(button4);
		buttons.add(button5);
		buttons.add(button6);
		buttons.add(button7);
		buttons.add(button8);
		buttons.add(button9);
		buttons.add(button10);
		buttons.add(button11);
		buttons.add(button12);
		buttons.add(button13);
		buttons.add(button14);
		buttons.add(button15);
		buttons.add(button16);
		buttons.add(button17);
		buttons.add(button18);
		buttons.add(button19);
		buttons.add(button20);
		buttons.add(button21);
		buttons.add(button22);
		buttons.add(button23);
		
		buttonRoll = new JButton("Roll!");
		buttonRoll.setBounds(475, 280, 123, 95);
		
		buttonRoll.setVerticalAlignment(SwingConstants.CENTER);
		buttonRoll.setForeground(Color.BLACK);
		buttonRoll.setFont(new Font("Serif", Font.BOLD, 14));
		
		buttonRoll.addActionListener(new RollButtonListener(this));
		add(buttonRoll);
	}
	
	
	class RollButtonListener implements ActionListener {
		  BackgammonPanel b;
		  RollButtonListener(BackgammonPanel b) { 
			  this.b = b;
		  }

		  public void actionPerformed(ActionEvent e) {
		    if (e.getSource().equals(buttonRoll)) {
		    	b.getGameboard().getDice().roll();
		    	buttonRoll.setOpaque(false);
				buttonRoll.setContentAreaFilled(false);
				buttonRoll.setBorderPainted(false);
				buttonRoll.setVerticalAlignment(SwingConstants.BOTTOM);
				buttonRoll.setForeground(Color.WHITE);
		        b.repaint();
		        b.setRoll(false);
		        b.setPlayerRolled(true);
		    }
		  }    
	}
	
	private void drawPieces(Graphics g){
		Image image;
		for (int i = 0; i < TOTAL_POS; i++) {
			if (game.colorAt(i) == Player.RED) {
				image = getRedPiece();
			} else if (game.colorAt(i) == Player.GREEN) {
				image = getGreenPiece();
			} else { continue; } 
			getCoordinates(g, image, i);
		}
	}

	private void getCoordinates(Graphics g, Image image, int pos) {
		
		int xCoordinate = 645, yCoordinate;

		if (pos >= 6 && pos < 18) 
			xCoordinate -= 2 * BORDER;
		/*} else {
			switch (pos) {
			case 24:
				paintPieces(g, image, EATEN_LEFT_BORDER, 35, pos);
				break;
			case 25:
				paintPieces(g, image, EATEN_LEFT_BORDER, 575, pos);
				break;
			case 26:
				paintPieces(g, image, GOTTEN_OUT_BORDER, 35, pos);
				break;
			case 27:
				paintPieces(g, image, GOTTEN_OUT_BORDER, 575, pos);
				break;
			default:
				//something from outer space
				break;
			}
		}*/

		if (pos < 12) {
			yCoordinate = 35;
			xCoordinate -= (pos % 12) * POS_SIZE_PXL;
			paintPieces(g, image, xCoordinate, yCoordinate, pos);
		} else if (pos > 11 && pos < 24) {
			yCoordinate = 565;
			xCoordinate -= ((23 - pos) % 12) * POS_SIZE_PXL;
			paintPieces(g, image, xCoordinate, yCoordinate, pos);
		}

	}
	
	public void paintPieces(Graphics g, Image image, int x, int y, int pos) {
		for (int i = 0; i < game.getNumberOfPiecesAt(pos); i++) {
			if (pos < 12 || pos == GREENS_EATEN || pos == POS_END_GREEN) {
				g.drawImage(image, x, y + i * PIECE_STEP, null);
			} else {
				g.drawImage(image, x, y - i * PIECE_STEP, null);
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(getBoardBackground(), 0, 0, null);
		drawPieces(g);

		for (int i = 0; i < 2; i++){
			drawDices(g, i);}
		
		this.repaint();
	}
	
	private void drawDices(Graphics g, int i){
		
		int diceX = DICE_X + i * DICE_SIZE;
		
		byte[] dices = game.getDice().getValues();

		if(dices[0]==0) dices = game.getDice().roll(); //should be gone by the end
	
		try {
			switch (dices[i]) {
			case 1:
				g.drawImage(getDice1(), diceX, DICE_Y, null);
				break;
			case 2:
				g.drawImage(getDice2(), diceX, DICE_Y, null);
				break;
			case 3:
				g.drawImage(getDice3(), diceX, DICE_Y, null);
				break;
			case 4:
				g.drawImage(getDice4(), diceX, DICE_Y, null);
				break;
			case 5:
				g.drawImage(getDice5(), diceX, DICE_Y, null);
				break;
			case 6:
				g.drawImage(getDice6(), diceX, DICE_Y, null);
				break;
			default:
				// something is really wrong with this dice
				break;
			}
		} catch (Exception e) {
			System.out.println("BackgammonPanel:drawDices, something's not ok. You are totally noob at Doors sorry! :/ ");
		}
	}
	
	public void setPlayer(Player p){
		player = p;
	}
	
	public Player getPlayer(){ return player;}

	private void setDice1(Image newImage) {
		dice1 = newImage;
	}

	private void setDice2(Image newImage) {
		dice2 = newImage;
	}

	private void setDice3(Image newImage) {
		dice3 = newImage;
	}

	private void setDice4(Image newImage) {
		dice4 = newImage;
	}

	private void setDice5(Image newImage) {
		dice5 = newImage;
	}

	private void setDice6(Image newImage) {
		dice6 = newImage;
	}

	private void setGreenPiece(Image newImage) {
		green = newImage;
	}

	private void setRedPiece(Image newImage) {
		red = newImage;
	}

	private void setBackground(Image newImage) {
		background = newImage;
	}

	private Image getDice1() {
		return dice1;
	}

	private Image getDice2() {
		return dice2;
	}

	private Image getDice3() {
		return dice3;
	}

	private Image getDice4() {
		return dice4;
	}

	private Image getDice5() {
		return dice5;
	}

	private Image getDice6() {
		return dice6;
	}

	private Image getGreenPiece() {
		return green;
	}

	private Image getRedPiece() {
		return red;
	}

	private Image getBoardBackground() {
		return background;
	}
	
	public ArrayList<BgButton> getButtons(){
		return buttons;
	}
	
	public Board getGameboard(){
		return game;
	}
	
	public void setMyTurn(boolean flag){ isItMyTurn = flag; }
	
	public boolean isMyTurn(){ return isItMyTurn; } 
	
	public void pick(int index){
		
		picked = false; //for when you reconsider the pick
		//maybe unnecessary

		if(jumpsYet < game.getDice().getTotalJumpsFromDice()){ 
			if(game.isValidPick(index, player)){
				setStatus("Got your piece, mate!");
				this.position = index;
				moveset = game.getDice().getDiceMoveset();
				if(game.getDice().isDouble()){
					for(int i = 0; i < moveset.size(); ++i){
						if(moveset.get(i) == 0) continue;
						if(game.isValidTarget(index+moveset.get(i), player)){
							if(moveset.get(i)  <= (game.getDice().getTotalJumpsFromDice() - jumpsYet))
								buttons.get(index+moveset.get(i)).highlight();
							else
								continue;
						}
					}
				} else {
					for(int i = 0; i < moveset.size(); ++i){
						if(moveset.get(i) == 0) continue;
						if(game.isValidTarget(index+moveset.get(i), player)){
							if((moveset.get(i)!= doneMove) && (jumpsYet + moveset.get(i) <= game.getDice().getTotalJumpsFromDice()))
								buttons.get(index+moveset.get(i)).highlight();
							else
								continue;
						}
					}
				}
				picked = true;
				lastPick = index;
			}else{
				if(game.getTable()[index] < 0)
					setStatus("Wrong color, bro!");
				else
					setStatus("No piece there. Are you blind or something?");
			}
		}
	}
	
	public void jump(int index){ 
		
		int m = 0;
		if(picked){
			for(int i = 0; i < moveset.size(); ++i){
				m = moveset.get(i);
				if (position + m == index &&
						m + jumpsYet <= game.getDice().getTotalJumpsFromDice())
				{//valid move according to number of left jumps and the dice
					if(game.isValidTarget(index, player) && buttons.get(index).isHighlihted()){ //check the gameboard validity of the move
						int[][] ms = new int[4][2];
						ms[0][0] = position; ms[0][1] = index;
						ms[1][0] = -99; ms[1][1] = -99;
						ms[2][0] = -99; ms[2][1] = -99;
						ms[3][0] = -99; ms[3][1] = -99;
						game.makeMove(new Move(ms), player.getSign()); //make the move finally
						if(m == 0){} //afairese prwth kinhsh apo statusBar, omoiws gia kathe i
						jumpsYet += m;
						doneMove = m;
						setStatus("Wow. Nice move! ^_^");
						
						if((game.getDice().getTotalJumpsFromDice()) == jumpsYet){
							jumpsYet = 0;
							doneMove = 0;
							setMyTurn(false);
							setStatus("Nice! You're done.");
							//break: well the if's end, so the outer break does the work
						}
					} else { setStatus("Can't jump there. You've been overoptimistic :/"); }
					break;
				}
			}
		
			//in any case, cleanse the highlights
			for(int i = 0; i < moveset.size(); ++i){
				if(lastPick+moveset.get(i) < 24)
					buttons.get(lastPick+moveset.get(i)).cleanse();
			}
			//and remove the pick
			//false move results to backrolling so its helpful
			//we should mention it in the manual :P //--MAKE DREAMS
			picked = false;
			lastPick = -1;

		}
	}
	
	public boolean isPicked(){
		return picked;
	}
	
	public int getJumpsYet(){ return jumpsYet; }

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setRoll(boolean b) {
		buttonRoll.setVisible(b);
		buttonRoll.setEnabled(b);
		this.repaint();
	}
	
	public boolean hasPlayerRolled(){
		return hasPlayerRolled;
	}
	
	public void setPlayerRolled(boolean b){
		hasPlayerRolled = b;
	}

}
