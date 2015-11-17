import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BgButton extends JButton implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private int number;
	private static int jumpsYet;

	public BgButton(String text){
		super(text);
		number = Integer.parseInt(text);
		addActionListener(this);
	}
	
	public void highlight(){
		setBackground(Color.BLUE);
	}
	
	public void cleanse(){
		setBackground(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) { 
		System.out.println("acc");//DEBUG
		BackgammonPanel panel = (BackgammonPanel) this.getParent();
		//mono otan paizei o an8rwpos prepei na exei dikaiwma na patisei..
		if(panel.isMyTurn()){ 
			if(!panel.isPicked()) panel.pick(number-1);
			else{
				panel.jump(number-1);
			}
			if(panel.getJumpsYet() == panel.getGameboard().getDice().getTotalJumpsFromDice()){
				panel.setMyTurn(false);
			}
		
		}
	}
	
}
