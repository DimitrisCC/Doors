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
	public void actionPerformed(ActionEvent e) {
		System.out.println("acc");
		BackgammonPanel panel = (BackgammonPanel) this.getParent();
		if(!panel.isPicked()) panel.pick(number-1);
		else panel.jump(number-1);
	}
	
}
