package components.jdataBoxContainer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

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
		int confirm = JOptionPane.showConfirmDialog(view, "¿Seguro que deseas eliminarlo?");
		if(confirm!=0)
			return;
		view.removeBox((JButton) evt.getSource());
	}

}
