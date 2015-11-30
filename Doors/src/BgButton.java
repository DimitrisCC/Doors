import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BgButton extends JButton implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private int number;
	private boolean highlighted;

	public BgButton(String text){
		super(text);
		try{
			number = Integer.parseInt(text);
		} catch (NumberFormatException e){
		}
		addActionListener(this);
		highlighted = false;
	}
	
	public void highlight(){
		this.setBackground(Color.BLUE);
		highlighted = true;
	}
	
	public boolean isHighlighted(){
		return highlighted || getBackground() == Color.BLUE;
	}
	
	public void cleanse(){
		this.setBackground(null);
		highlighted = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) { 
		
		BackgammonPanel panel = (BackgammonPanel) this.getParent();
		
		if(!panel.hasPlayerRolled()){
			panel.getStatusBar().setStatus("Please, roll the dice first.");
			return;
		}
		
		if(panel.isMyTurn()){ 
			
			if(panel.getGameboard().getEaten()[0] > 0 && !this.isHighlighted()){ //you have to get the eaten in first
				panel.getStatusBar().setStatus("Please, enter the eaten pieces first.");
				return; //don't enter the picking phase unless all eaten are entered
			}
			
			panel.getStatusBar().setStatus("Pick'n'jump!");
			
			if(!panel.isPicked()) panel.pick(number-1);
			else{
				panel.jump(number-1);
			}
			
		}
		
	}
	
}
