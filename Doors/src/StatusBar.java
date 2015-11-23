
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	public final void setMoveValues(final byte[] dice) {
		this.moveVals = "-> Moves = (" + dice[0] + ",";
		if(dice[0]==dice[1]) {
			this.moveVals += dice[0]*2 + "," + dice[0]*3 + "," + dice[0]*4;
		}
		else {
			this.moveVals += dice[1] + "," + ((int)dice[0]+dice[1]);
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