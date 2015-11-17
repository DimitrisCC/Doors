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
	public void actionPerformed(ActionEvent e) { //--> giati actionPerformed ki oxi onClick??
		//--> nomizw 8a exei provlima giati oti action kai na ginetai 8a kanei kinisi px
		System.out.println("acc");//DEBUG
		BackgammonPanel panel = (BackgammonPanel) this.getParent();
		//mono otan paizei o an8rwpos prepei na exei dikaiwma na patisei..
		if(panel.isMyTurn()){ //kapws prepei na valoume sta8eres poion 8ewroume pc kai poion an8rwpo
			//dn to xw valei giati dn exw apofasisei p prepei na mpei
			if(!panel.isPicked()) panel.pick(number-1);
			else{
				panel.jump(number-1);
			}
			
			//if(panel.getGameboard().getDice().isDouble()){
				//if(numOfCheckersMoved == 4){
					//panel.setMyTurn(false);
					//numOfCheckersMoved = 0;
				//}
			//}else{
				if(panel.getJumpsYet() == panel.getGameboard().getDice().getTotalJumpsFromDice()){
					panel.setMyTurn(false);
				}
			//}
		}
	}
	
}
