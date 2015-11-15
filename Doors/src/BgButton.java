import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BgButton extends JButton implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private int number;

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
		System.out.println("acc");
		BackgammonPanel panel = (BackgammonPanel) this.getParent();
		if(panel.getPlayer() == Player.GREEN){ //kapws prepei na valoume sta8eres poion 8ewroume pc kai poion an8rwpo
			//dn to xw valei giati dn exw apofasisei p prepei na mpei
			if(!panel.isPicked()) panel.pick(number-1);
			else panel.jump(number-1);
		}
	}
	
}
