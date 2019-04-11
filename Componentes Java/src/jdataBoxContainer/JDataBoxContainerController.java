package jdataBoxContainer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class JDataBoxContainerController implements ActionListener{
	private JDataBoxContainerView view;
	
	public JDataBoxContainerController(JDataBoxContainerView view) {
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(view.isBtnNuevaCaja((JButton) evt.getSource())) {
			view.addBox();
			return; 
		}
		view.removeBox((JButton) evt.getSource());
	}

}
