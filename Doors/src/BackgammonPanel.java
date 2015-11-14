
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Draws the GUI of the game: background, pieces, dice
 */

public class BackgammonPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
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
	
	private ArrayList<JButton> buttons;

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
		setGreenPiece(Toolkit.getDefaultToolkit().createImage("resources//green.png"));
		setRedPiece(Toolkit.getDefaultToolkit().createImage("resources//red.png"));
		
		buttons = new ArrayList<JButton>();
		drawButtons();
	}
	
	private void drawButtons(){
		JButton button11 = new JButton("12");
		button11.setBounds(27, 618, 50, 23);
		this.add(button11);
		buttons.add(button11);
		
		JButton button10 = new JButton("11");
		button10.setBounds(78, 618, 50, 23);
		this.add(button10);
		buttons.add(button10);
		
		JButton button9 = new JButton("10");
		button9.setBounds(129, 618, 50, 23);
		this.add(button9);
		buttons.add(button9);
		
		JButton button8 = new JButton("9");
		button8.setBounds(180, 618, 50, 23);
		this.add(button8);
		buttons.add(button8);
		
		JButton button7 = new JButton("8");
		button7.setBounds(231, 618, 50, 23);
		this.add(button7);
		buttons.add(button7);
		
		JButton button6 = new JButton("7");
		button6.setBounds(282, 618, 50, 23);
		this.add(button6);
		buttons.add(button6);
		
		JButton button5 = new JButton("6");
		button5.setBounds(387, 618, 50, 23);
		this.add(button5);
		buttons.add(button5);
		
		JButton button4 = new JButton("5");
		button4.setBounds(438, 618, 50, 23);
		this.add(button4);
		buttons.add(button4);
		
		JButton button3 = new JButton("4");
		button3.setBounds(489, 618, 50, 23);
		this.add(button3);
		buttons.add(button3);
		
		JButton button2 = new JButton("3");
		button2.setBounds(540, 618, 50, 23);
		this.add(button2);
		buttons.add(button2);
		
		JButton button1 = new JButton("2");
		button1.setBounds(591, 618, 50, 23);
		this.add(button1);
		buttons.add(button1);
		
		JButton button0 = new JButton("1");
		button0.setBounds(642, 618, 50, 23);
		this.add(button0);
		buttons.add(button0);
		
		JButton button12 = new JButton("13");
		button12.setBounds(27, 0, 50, 23);
		this.add(button12);
		buttons.add(button12);
		
		JButton button13 = new JButton("14");
		button13.setBounds(78, 0, 50, 23);
		this.add(button13);
		buttons.add(button13);
		
		JButton button14 = new JButton("15");
		button14.setBounds(129, 0, 50, 23);
		this.add(button14);
		buttons.add(button14);
		
		JButton button15 = new JButton("16");
		button15.setBounds(179, 0, 50, 23);
		this.add(button15);
		buttons.add(button15);
		
		JButton button16 = new JButton("17");
		button16.setBounds(230, 0, 50, 23);
		this.add(button16);
		buttons.add(button16);
		
		JButton button17 = new JButton("18");
		button17.setBounds(281, 0, 50, 23);
		this.add(button17);
		buttons.add(button17);
		
		JButton button18 = new JButton("19");
		button18.setBounds(387, 0, 50, 23);
		this.add(button18);
		buttons.add(button18);
		
		JButton button19 = new JButton("20");
		button19.setBounds(438, 0, 50, 23);
		this.add(button19);
		buttons.add(button19);
		
		JButton button20 = new JButton("21");
		button20.setBounds(489, 0, 50, 23);
		this.add(button20);
		buttons.add(button20);
		
		JButton button21 = new JButton("22");
		button21.setBounds(540, 0, 50, 23);
		this.add(button21);
		buttons.add(button21);
		
		JButton button22 = new JButton("23");
		button22.setBounds(591, 0, 50, 23);
		this.add(button22);
		buttons.add(button22);
		
		JButton button24 = new JButton("24");
		button24.setBounds(642, 0, 50, 23);
		this.add(button24);
		buttons.add(button24);
	}
	
	private void drawPieces(Graphics g){
		Image image;
		for (int i = 0; i < TOTAL_POS; i++) {
			if (game.colorAt(i) == Player.RED) {
				image = getRedPiece();
			} else if (game.colorAt(i) == Player.GREEN) {
				image = greenPiece();
			} else { image = null; } ///!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			getCoordinates(g, image, i);
		}
	}

	private void getCoordinates(Graphics g, Image image, int pos) {
		
		int xCoordinate = 645, yCoordinate;

		if (pos >= 6 && pos < 18) {
			xCoordinate -= 2 * BORDER;
		} else {
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
		}

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
	}
	
	private void drawDices(Graphics g, int i){
		
		int diceX = DICE_X + i * DICE_SIZE;
		
		byte[] dices = game.getDice();
		if(dices[0]==0) dices = game.rollDice(); //should be gone by the end
		
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
			System.out.println("BackgammonPanel:drawDices, something's not ok :/ ");
		}
	}

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

	private Image greenPiece() {
		return green;
	}

	private Image getRedPiece() {
		return red;
	}

	private Image getBoardBackground() {
		return background;
	}

}
