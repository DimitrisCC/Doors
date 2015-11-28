import java.awt.event.ActionEvent;

public class BearOffButton extends BgButton {

	private static final long serialVersionUID = 2664337448358920058L;

	public BearOffButton(String text) {
		super(text);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { 
		
		BackgammonPanel panel = (BackgammonPanel) this.getParent();
		
		if(!panel.getGameboard()._hasGreenReachedDestination()){
			panel.getStatusBar().setStatus("Hasn't reached destination yet, sir. Don't be impatient.");
		}
		
		if(!panel.hasPlayerRolled()){
			panel.getStatusBar().setStatus("Please, roll the dice first.");
			return;
		}
		
		if(panel.isMyTurn()){
			if(this.isHighlighted())
				//if(panel.isPicked())
					panel.jump(24);
			
		}
		
	}

}
