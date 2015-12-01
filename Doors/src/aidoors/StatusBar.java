package aidoors;



import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 * A status bar below the playing frame which shows information about the game:
 * current player, moveset, etc
 */

public class StatusBar extends JLabel {

	private String status;
	private String moveVals;
	private static final long serialVersionUID = 1L;
	
	public StatusBar(String text) {
		super(text, SwingConstants.CENTER);
		status = text;
		moveVals = "()";
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		setText(text);
		setOpaque(true);
		setBackground(Color.BLACK);
		setForeground(Color.GREEN);
	}

	public final void setStatus(final String status) {
		this.status = status;
		setText(status + "     " + moveVals);
		this.repaint();
	}
	
	public final void setMoveValues(final int[] m) {
		this.moveVals = "-> Moves = (" + m[0] + ",";
		if(m[0]==m[1]) {
			this.moveVals += m[0]*2 + "," + m[0]*3 + "," + m[0]*4;
		}
		else {
			this.moveVals += m[1] + "," + ((int)m[0]+m[1]);
		}
		this.moveVals += ")";
		setText(status + "     " + moveVals);
		this.repaint();
	}

	public void clear() {
		setText(" ");
		this.repaint();
	}
	
	public void clearDice() {
		setText(status);
		this.repaint();
	}
}