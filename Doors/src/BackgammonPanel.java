
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.*;

/**
 * Draws the GUI of the game: background, pieces, dice
 */

public class BackgammonPanel extends JPanel implements MouseMotionListener {

	private static final long serialVersionUID = 1L; // --> pou xrisimopoieitai?

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
	private byte[] moves;
	private byte jumpsYet;
	private int lastPick; // to cleanse the highlight
	private int doneMove;

	private ArrayList<BgButton> buttons;
	private JButton buttonRoll;
	private BgButton btnBearOff;
	private StatusBar statusBar;

	private boolean picked;
	private boolean hasPlayerRolled;

	private boolean isItMyTurn; // is it the turn of the player?

	private int numOfMoves = 0;
	private int numOfMovesDone = 0;

	public BackgammonPanel(Board game) {
		this.game = game;
		// setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this.setLayout(null);
		setBackground(Toolkit.getDefaultToolkit().createImage("resources//background.png"));
		setDice1(Toolkit.getDefaultToolkit().createImage("resources//dice1.png"));
		setDice2(Toolkit.getDefaultToolkit().createImage("resources//dice2.png"));
		setDice3(Toolkit.getDefaultToolkit().createImage("resources//dice3.png"));
		setDice4(Toolkit.getDefaultToolkit().createImage("resources//dice4.png"));
		setDice5(Toolkit.getDefaultToolkit().createImage("resources//dice5.png"));
		setDice6(Toolkit.getDefaultToolkit().createImage("resources//dice6.png"));
		setGreenPiece(Toolkit.getDefaultToolkit().createImage("resources//green.png"));// -->
																						// GreenPeace....
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

	private void drawStatusBar() {
		statusBar = new StatusBar("LET THE GAME BEGIN!!!");
		statusBar.setBounds(0, 640, 720, 20);
		this.add(statusBar);
	}

	public StatusBar getStatusBar() {
		return this.statusBar;
	}

	private void drawButtons() {

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

		// ******************************************************************
		// THELEI FTIAXIMO..AFORA MONO TON GREEN
		btnBearOff = new BgButton("Bear off");
		btnBearOff.setBounds(720, 308, 75, 23);
		this.add(btnBearOff);
		/*
		 * if(isMyTurn() && game.hasGreenReachedDestination())//green { byte[]
		 * dices= game.dice.getValues(); if(dices[0]==(24-number) ||
		 * dices[1]==(24-number)) { if(game.colorAt(24-number)==Player.GREEN) {
		 * setBackground(Color.YELLOW); btnBearOff.addActionListener(new
		 * BearOffListener(this)); } else { System.out.println(
		 * "You do not have checkers at this position!"); } } }
		 */
	}

	class BearOffListener implements ActionListener {
		BackgammonPanel b;

		BearOffListener(BackgammonPanel b) {
			this.b = b;
		}

		public void actionPerformed(
				ActionEvent e) { /*
									 * int unbearedOff=0; byte[] dices=
									 * b.game.dice.getValues();
									 * if(dices[0]==dices[1])//diples {
									 * if(b.getNumberOfPieceAt(24-number)>=4)//
									 * an uparxoun perissotera apo 4 poulia se
									 * ayth th thesh {
									 * b.game.getTable()[24-number]+=-4;//
									 * afairesh pouliou apo th board
									 * b.game.getfreedPieces()[0]+=4;//prosthiki
									 * sto freedpieces table } else {
									 * b.game.getTable()[24-number]+=-b.
									 * getNumberOfPieceAt(24-number);//afairesh
									 * pouliou apo th board
									 * b.game.getfreedPieces()[0]+=b.
									 * getNumberOfPieceAt(24-number);//prosthiki
									 * sto freedpieces table //GIA TA POULIA POU
									 * EXOUN MEINEI KAI DEN EXOUN
									 * MAZEUTEI:unbearedOff //Ayta tha xreiastei
									 * na ta pairnw apo tis megaluteres theseis
									 * unbearedOff =
									 * 4-b.getNumberOfPieceAt(24-number); } }
									 * //mones else {
									 * b.game.getTable()[24-number]+=-1;//
									 * afairesh pouliou apo th board
									 * b.game.getfreedPieces()[0]+=1;//prosthiki
									 * sto freedpieces table }
									 * btnBearOff.setBackground(null);
									 */
		}
	}
	// *************************************************************

	class RollButtonListener implements ActionListener {
		BackgammonPanel b;

		RollButtonListener(BackgammonPanel b) {
			this.b = b;
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(buttonRoll)) {
				byte[] m = b.getGameboard().getDice().roll();
				buttonRoll.setOpaque(false);
				buttonRoll.setContentAreaFilled(false);
				buttonRoll.setBorderPainted(false);
				buttonRoll.setVerticalAlignment(SwingConstants.BOTTOM);
				buttonRoll.setForeground(Color.WHITE);
				HashSet<Board> ch = b.getGameboard().getChildren(b.getGameboard().getDice(), Player.GREEN);
				if(ch.isEmpty()){
					b.getStatusBar().setStatus("You have no moves to make. You loose your turn. Opponet plays!");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					b.setMyTurn(false);
				}else{
					Board child = ch.iterator().next();
					int[][] move = child.getLastPlayedMove().getMove();
					int i =1;
					for(; i < move.length; ++i){
						if(move[i-1][0] == move[i-1][1]) b.setNumOfMoves(i-1); //i-1 giati an px ginei true to if stn i=2
						//tote mporouses na kaneis mono mia kinisi opote i-1
					}
					b.getStatusBar().setMoveValues(m);
				}
				b.repaint();
				b.setRoll(false);
				b.setPlayerRolled(true);
			}
		}
	}

	private void drawPieces(Graphics g) {
		Image image;
		for (int i = 0; i < TOTAL_POS; i++) {
			if (game.colorAt(i) == Player.RED) {
				image = getRedPiece();
			} else if (game.colorAt(i) == Player.GREEN) {
				image = getGreenPiece();
			} else {
				continue;
			}
			getCoordinates(g, image, i);
		}
		int eaten[] = game.getEaten();
		int freed[] = game.getFreedPieces();

		image = getGreenPiece();
		for (int i = 0; i < eaten[0]; ++i) {
			getCoordinates(g, image, 24);
		}
		for (int i = 0; i < freed[0]; ++i) {
			getCoordinates(g, image, 26);
		}

		image = getRedPiece();
		for (int i = 0; i < eaten[1]; ++i) {
			getCoordinates(g, image, 25);
		}
		for (int i = 0; i < freed[1]; ++i) {
			getCoordinates(g, image, 27);
		}
	}

	public void setNumOfMoves(int i) {
		numOfMoves = i;
		
	}

	private void getCoordinates(Graphics g, Image image, int pos) {

		int xCoordinate = 645, yCoordinate;

		if (pos >= 6 && pos < 18) {
			xCoordinate -= 2 * BORDER;
		} else {
			switch (pos) {
			case 24:
				paintPieces(g, image, EATEN_LEFT_BORDER, 35, pos, game.getEaten()[0], false);
				break;
			case 25:
				paintPieces(g, image, EATEN_LEFT_BORDER, 575, pos, game.getEaten()[1], false);
				break;
			case 26:
				paintPieces(g, image, GOTTEN_OUT_BORDER, 35, pos, game.getFreedPieces()[0], false);
				break;
			case 27:
				paintPieces(g, image, GOTTEN_OUT_BORDER, 575, pos, game.getFreedPieces()[1], false);
				break;
			default:
				// something from outer space
				break;
			}
		}

		if (pos < 12) {
			yCoordinate = 35;
			xCoordinate -= (pos % 12) * POS_SIZE_PXL;
			paintPieces(g, image, xCoordinate, yCoordinate, pos, 0, true);
		} else if (pos > 11 && pos < 24) {
			yCoordinate = 565;
			xCoordinate -= ((23 - pos) % 12) * POS_SIZE_PXL;
			paintPieces(g, image, xCoordinate, yCoordinate, pos, 0, true);
		}

	}

	public void paintPieces(Graphics g, Image image, int x, int y, int pos, int num, boolean ingame) {
		int until;
		if (ingame)
			until = game.getNumberOfPiecesAt(pos);
		else
			until = num;

		for (int i = 0; i < until; i++) {
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

		for (int i = 0; i < 2; i++) {
			drawDices(g, i);
		}

		this.repaint();
	}

	private void drawDices(Graphics g, int i) {

		int diceX = DICE_X + i * DICE_SIZE;

		byte[] dices = game.getDice().getValues();

		if (dices[0] == 0)
			dices = game.getDice().roll(); // should be gone by the end

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
			System.out
					.println("BackgammonPanel:drawDices, something's not ok. You are totally noob at Doors sorry! :/ ");
		}
	}
	
	/**
	 * Sets the current player of the game
	 * @param the current player of the game
	 */
	public void setPlayer(Player p) {
		player = p;
	}

	/**
	 * Returns the current player of the game
	 * @return the current player of the game
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the image for the dice with number 1
	 * @param dice's first image
	 */
	private void setDice1(Image newImage) {
		dice1 = newImage;
	}

	/**
	 * Sets the image for the dice with number 2
	 * @param dice's second image
	 */
	private void setDice2(Image newImage) {
		dice2 = newImage;
	}

	/**
	 * Sets the image for the dice with number 3
	 * @param dice's third image
	 */
	private void setDice3(Image newImage) {
		dice3 = newImage;
	}

	/**
	 * Sets the image for the dice with number 4
	 * @param dice's fourth image
	 */
	private void setDice4(Image newImage) {
		dice4 = newImage;
	}

	/**
	 * Sets the image for the dice with number 5
	 * @param dice's fifth image
	 */
	private void setDice5(Image newImage) {
		dice5 = newImage;
	}

	/**
	 * Sets the image for the dice with number 6
	 * @param dice's sixth image
	 */
	private void setDice6(Image newImage) {
		dice6 = newImage;
	}

	/**
	 * Sets the image for the green checker
	 * @param green checker's image
	 */
	private void setGreenPiece(Image newImage) {
		green = newImage;
	}

	/**
	 * Sets the image for the red checker
	 * @param red checker's image
	 */
	private void setRedPiece(Image newImage) {
		red = newImage;
	}

	/**
	 * Sets the image for the game's background
	 * @param background's image
	 */
	private void setBackground(Image newImage) {
		background = newImage;
	}

	/**
	 * Returns the image for the dice with number 1
	 * @return dice's first image
	 */
	private Image getDice1() {
		return dice1;
	}

	/**
	 * Returns the image for the dice with number 2
	 * @return dice's second image
	 */
	private Image getDice2() {
		return dice2;
	}

	/**
	 * Returns the image for the dice with number 3
	 * @return dice's third image
	 */
	private Image getDice3() {
		return dice3;
	}

	/**
	 * Returns the image for the dice with number 4
	 * @return dice's fourth image
	 */
	private Image getDice4() {
		return dice4;
	}
	/**
	 * Returns the image for the dice with number 5
	 * @return dice's fifth image
	 */
	private Image getDice5() {
		return dice5;
	}

	/**
	 * Returns the image for the dice with number 6
	 * @return dice's sixth image
	 */
	private Image getDice6() {
		return dice6;
	}
	
	/**
	 * Returns the image for the green checker
	 * @return green checker's image
	 */
	private Image getGreenPiece() {
		return green;
	}
	
	/**
	 * Returns the image for the red checker
	 * @return red checker's image
	 */
	private Image getRedPiece() {
		return red;
	}

	/**
	 * Returns the image for the game's background
	 * @return background's image
	 */
	private Image getBoardBackground() {
		return background;
	}

	/**
	 * Returns the buttons' array
	 * @return the buttons' array
	 */
	public ArrayList<BgButton> getButtons() {
		return buttons;
	}

	/**Returns the board of the game
	 * @return the game board
	 */
	public Board getGameboard() {
		return game;
	}

	/**
	 * Setter that sets whether it's the player's turn to play
	 * @return true if it's the player's turn to play, otherwise false
	 */
	public void setMyTurn(boolean flag) {
		isItMyTurn = flag;
	}

	/**
	 * Returns if it's the player's turn to play or not
	 * @return true if it's the player's turn to play, otherwise false
	 */
	public boolean isMyTurn() {
		return isItMyTurn;
	}

	public void pick(int index) { // if index = -1, the pick stands for the
									// highlighting of the eaten

		picked = false; // for when you reconsider the pick
		// maybe unnecessary

		// if the player picked a valid checker then the buttons he can
		if (game.isValidPick(index, player)) {
			statusBar.setStatus("Got your piece, mate!");
			this.position = index;
			boolean getInTheGame = false;
			moves = game.getDice().getValues();
			for (int i = 0; i < moves.length; ++i) {
				// if(moves[0] == 0) continue;
				if (game.isValidTarget(index + moves[i], player)) {
					if (((!game.getDice().isDouble()) && moves[i] != doneMove)
							|| (game.getDice().isDouble() && jumpsYet < game.getDice().getTotalJumpsFromDice())) {

						if (index + moves[i] < 24 && index + moves[i] > -1){
							if(index == -1) getInTheGame = true;
							buttons.get(index + moves[i]).highlight();
						}else if (index + moves[i] == 24) {
							// ((BgButton) btnBearOff).highlight();
						}

					}

				}
			}
			
			System.out.println(getInTheGame);
			
			if(!getInTheGame && (index == -1)){
				System.out.println("well u lost ur turn fucking asswhole");
				setMyTurn(false);
			}
			
			picked = true;
			lastPick = index;

		} else if (game.getTable()[index] < 0)
			statusBar.setStatus("Wrong color, bro!");
		else
			statusBar.setStatus("No piece there. Are you blind or something?");

		// statusBar.setStatus("Pick a correct piece, already");
	}

	public void jump(int index) {

		int m = 0;
		if (picked) {
			for (int i = 0; i < moves.length; ++i) {
				m = moves[i];
				if (position + m == index) {// valid move according to number of
											// left jumps and the dice
					if (buttons.get(index).isHighlighted()) { // check the
																// gameboard
																// validity of
																// the move
						game.makeMove(this.position, index, player.getSign()); // make
																				// the
																				// move
						numOfMovesDone++;													// finally
						jumpsYet += m;
						doneMove = m;
						statusBar.setStatus("Wow. Nice move! ^_^");

						if (((game.getDice().getTotalJumpsFromDice()) == jumpsYet) || numOfMovesDone == numOfMoves) {
							jumpsYet = 0;
							doneMove = 0;
							numOfMovesDone = 0;
							numOfMoves = 0;//dn eimai sigouri edw
							
							for (int j = 0; j < moves.length; ++j) {
								if (lastPick + moves[i] < 24 && lastPick + moves[i] > -1){
									System.out.println("jusst cleansed "+(lastPick +moves[i])+" last Pick "+lastPick);
									buttons.get(lastPick + moves[i]).cleanse();
								}
							}
							setMyTurn(false);
							statusBar.setStatus("Nice! You're done.");
							// break: well the if's end, so the outer break does
							// the work
						}
					} else {
						statusBar.setStatus("Can't jump there. You've been overoptimistic :/");
					}
					break;
				}
			}

			// in any case, cleanse the highlights
			for (int i = 0; i < moves.length; ++i) {
				if (lastPick + moves[i] < 24 && lastPick + moves[i] > -1){
					System.out.println("jusst cleansed "+(lastPick +moves[i])+" last Pick "+lastPick);
					buttons.get(lastPick + moves[i]).cleanse();
				}
			}
			// and remove the pick
			// false move results to backrolling so its helpful
			// we should mention it in the manual :P //--MAKE DREAMS
			picked = false;
			lastPick = -99;

			if (game.getGreensEaten() > 0)
				pick(-1);

		}
	}

	/**
	 * Returns if there is a picked checker or not
	 * @return true if the player has picked a checker, otherwise false
	 */
	public boolean isPicked() {
		return picked;
	}

	/**
	 * Getter for how many jumps has the player done yet
	 * @return the number of jumps done 
	 */
	public int getJumpsYet() {
		return jumpsYet;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Setter that sets whether the dice will be rollable or not
	 * @param true if the dice can be rolled, otherwise false
	 */
	public void setRoll(boolean b) {
		buttonRoll.setVisible(b);
		buttonRoll.setEnabled(b);
		this.repaint();
	}
	
	/**
	 * Returns if the player has rolled the dice
	 * @return true if the player has rolled the dice, otherwise false
	 */
	public boolean hasPlayerRolled() {
		return hasPlayerRolled;
	}
	
	/**
	 * Setter that sets whether the player rolled the dice or not
	 * @param true if the player rolled the dice, otherwise false
	 */
	public void setPlayerRolled(boolean b) {
		hasPlayerRolled = b;
		if (b && player == Player.GREEN) {
			if (game.getGreensEaten() > 0)
				pick(-1);
		}
	}

}
