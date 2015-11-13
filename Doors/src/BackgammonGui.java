import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class BackgammonGui {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BackgammonGui window = new BackgammonGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BackgammonGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("BACKGAMMON");
		frame.getContentPane().setBackground(new Color(189, 183, 107));
		frame.setSize(900, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel label = new JLabel("");
		label.setBounds(10, 11, 726, 690);
		label.setAlignmentX(0);
		label.setAlignmentY(0);
		//Border border = BorderFactory.createLineBorder(Color.BLACK);
	    frame.getContentPane().setLayout(null);
	    //frame.getContentPane().setLayout(null);
	    
	    //label.setBorder(border);
		label.setPreferredSize(new Dimension(720, 641));
		
		label.setIcon(new ImageIcon("C:\\Users\\FOTINI\\git\\Doors\\Doors\\resources\\background.png"));
		
		frame.getContentPane().add(label);
		
		JButton btnNewButton = new JButton("23");
		btnNewButton.setBounds(39, 678, 50, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("22");
		btnNewButton_1.setBounds(90, 678, 50, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("21");
		btnNewButton_2.setBounds(141, 678, 50, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("20");
		btnNewButton_3.setBounds(192, 678, 50, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("19");
		btnNewButton_4.setBounds(243, 678, 50, 23);
		frame.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("18");
		btnNewButton_5.setBounds(294, 678, 50, 23);
		frame.getContentPane().add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("17");
		btnNewButton_6.setBounds(400, 678, 50, 23);
		frame.getContentPane().add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("16");
		btnNewButton_7.setBounds(451, 678, 50, 23);
		frame.getContentPane().add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("15");
		btnNewButton_8.setBounds(502, 678, 50, 23);
		frame.getContentPane().add(btnNewButton_8);
		
		JButton btnNewButton_9 = new JButton("14");
		btnNewButton_9.setBounds(553, 678, 50, 23);
		frame.getContentPane().add(btnNewButton_9);
		
		JButton btnNewButton_10 = new JButton("13");
		btnNewButton_10.setBounds(604, 678, 50, 23);
		frame.getContentPane().add(btnNewButton_10);
		
		JButton btnNewButton_11 = new JButton("12");
		btnNewButton_11.setBounds(655, 678, 50, 23);
		frame.getContentPane().add(btnNewButton_11);
		
		JButton btnNewButton_12 = new JButton("0");
		btnNewButton_12.setBounds(39, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_12);
		
		JButton btnNewButton_13 = new JButton("1");
		btnNewButton_13.setBounds(90, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_13);
		
		JButton btnNewButton_14 = new JButton("2");
		btnNewButton_14.setBounds(141, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_14);
		
		JButton btnNewButton_15 = new JButton("3");
		btnNewButton_15.setBounds(191, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_15);
		
		JButton btnNewButton_16 = new JButton("4");
		btnNewButton_16.setBounds(242, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_16);
		
		JButton btnNewButton_17 = new JButton("5");
		btnNewButton_17.setBounds(293, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_17);
		
		JButton btnNewButton_18 = new JButton("6");
		btnNewButton_18.setBounds(399, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_18);
		
		JButton btnNewButton_19 = new JButton("7");
		btnNewButton_19.setBounds(450, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_19);
		
		JButton btnNewButton_20 = new JButton("8");
		btnNewButton_20.setBounds(501, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_20);
		
		JButton btnNewButton_21 = new JButton("9");
		btnNewButton_21.setBounds(552, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_21);
		
		JButton btnNewButton_22 = new JButton("10");
		btnNewButton_22.setBounds(603, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_22);
		
		JButton btnNewButton_23 = new JButton("11");
		btnNewButton_23.setBounds(654, 11, 50, 23);
		frame.getContentPane().add(btnNewButton_23);
		
		frame.setVisible(true);
		
	}
}
