package components;

import javax.swing.JFrame;

public class test extends JFrame{

	private test() {
		JOrderableCombo c = new JOrderableCombo();
		c.addItem("Z");
		c.addItem("A");
		c.addItem("B");
		add(c);
		setSize(200,200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new test();
	}

}
