

import java.awt.event.ActionEvent;

/**
 * A button for bearing of pieces.
 */

public class BearOffButton extends BgButton {

	private static final long serialVersionUID = 2664337448358920058L;

	public BearOffButton(String text) {
		super(text);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { 
		
		BackgammonPanel panel = (BackgammonPanel) this.getParent();
		
		if(!panel.getGameboard().hasGreenReachedDestination()){
			panel.getStatusBar().setStatus("Hasn't reached destination yet, sir. Don't be impatient.");
			panel.getStatusBar().clearDice();
		}
		
		if(!panel.hasPlayerRolled()){
			panel.getStatusBar().setStatus("Please, roll the dice first.");
			panel.getStatusBar().clearDice();
			return;
		}
		
		if(panel.isMyTurn()){
			if(this.isHighlighted())
				//if(panel.isPicked())
					panel.jump(24);
			
		}
		
	}

}
