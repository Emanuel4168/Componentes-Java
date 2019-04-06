package components;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import jemc_combo.JEMCComboView;

public class test extends JFrame{

	private test() {
		JOrderableCombo c = new JOrderableCombo();
		c.addItem("ZXY");
		c.addItem("ABC");
		c.addItem("DEF");
		c.addItem("AZZ");
		add(c,BorderLayout.SOUTH);
		add(new JEMCComboView(),BorderLayout.NORTH);
//		System.out.println(matches("ABC","A"));
		setSize(200,200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new test();
	}
	
	private boolean matches(String str, String regEx) {
		try {
			for(int i = 0; i < regEx.length(); i++)
				if(str.charAt(i) != regEx.charAt(i))
					return false;
			return true;
			
		}catch(Exception e) {return false;}
	}

}
