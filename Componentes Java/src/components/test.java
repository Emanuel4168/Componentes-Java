package components;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jdataBoxContainer.JDataBoxContainer;
import jemc_combo.JEMCCombo;



public class test extends JFrame{

	private test() {
//COMBO 1
		JOrderableCombo c = new JOrderableCombo();
		c.addItem("ZXY");
		c.addItem("ABC");
		c.addItem("DEF");
		c.addItem("AZZ");
		c.setItem("ITEM", 2);
		add(c,BorderLayout.SOUTH);
		
//COMBO 2
		add(new JEMCCombo(JEMCCombo.ORIENTACION_HORIZONTAL),BorderLayout.NORTH);
//		add(new JEMCCombo(JEMCCombo.ORIENTACION_VERTICAL, "Aguascalientes"),BorderLayout.NORTH);
		//add(new JEMCCombo(JEMCCombo.ORIENTACION_HORIZONTAL, "Aguascalientes", "aguascalientes"),BorderLayout.NORTH);
		
//COMBO 3

		add(new JDataBoxContainer(), BorderLayout.CENTER);
		
		setSize(800,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new test();
	}
}