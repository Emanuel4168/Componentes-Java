package components;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jdataBoxContainer.*;
import jemc_combo.JEMCComboView;

public class test extends JFrame{

	private test() {
		JOrderableCombo c = new JOrderableCombo();
		c.addItem("ZXY");
		c.addItem("ABC");
		c.addItem("DEF");
		c.addItem("AZZ");
		add(c,BorderLayout.SOUTH);
		add(new JEMCCombo("Aguascalientes","aguascalientes"),BorderLayout.NORTH);
		
		JPanel Panel = new JPanel();
//		Panel.add(new JMultiDataBox(JMultiDataBox.EMAIL_REGEX),BorderLayout.CENTER);
//		Panel.add(new JMultiDataBox(JMultiDataBox.RFC_regex),BorderLayout.CENTER);
//		Panel.add(new JMultiDataBox(JMultiDataBox.TEL_REGEX),BorderLayout.CENTER);
//		Panel.add(new JDataBoxContainer());
		add(new JDataBoxContainer(), BorderLayout.CENTER);
		
//		System.out.println(matches("ABC","A"));
		setSize(800,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
