package components;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

public class JDataBoxContainer extends JPanel implements ActionListener {
	
	JPanel panelRadios, panelButton, panelBoxes;
	JRadioButton rbEmail, rbRFC, rbTel;
	ButtonGroup radios;
	JButton btnNuevaCaja;
	int noBoxes;
	Vector<JMultiDataBox> vectorBoxes;
	Vector<JButton> vectorButtons;
	
	public JDataBoxContainer() {
		this(10);
	}
	
	public JDataBoxContainer(int noBoxes) {
		this.noBoxes = noBoxes;
		panelRadios = new JPanel();
		panelButton = new JPanel();
		panelBoxes = new JPanel();
		
		rbEmail = new JRadioButton("e-mail");
		rbRFC = new JRadioButton("RFC");
		rbTel = new JRadioButton("Teléfono");
		
		radios = new ButtonGroup();
		radios.add(rbEmail);
		radios.add(rbRFC);
		radios.add(rbTel);
		rbEmail.setSelected(true);
		
		btnNuevaCaja = new JButton("Nueva caja");
		btnNuevaCaja.addActionListener(this);
		
		vectorBoxes = new Vector<JMultiDataBox>();
		vectorButtons = new Vector<JButton>();
		
		createViews();
	}
	
	public void createViews() {
		setSize(700,700);
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		panelRadios.add(rbEmail);
		panelRadios.add(rbRFC);
		panelRadios.add(rbTel);
		
		panelButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelButton.add(btnNuevaCaja);
		
		add(panelRadios);
		add(panelButton);
		add(panelBoxes);
	}
	
	public void addBox() {
		System.out.println(vectorBoxes.size());
		
		if(vectorBoxes.size()==noBoxes)
			return;
		//Buscamos la 
		String regEx = (rbEmail.isSelected())? JMultiDataBox.EMAIL_REGEX : (rbRFC.isSelected())? JMultiDataBox.RFC_REGEX : JMultiDataBox.TEL_REGEX;
		JMultiDataBox box = new JMultiDataBox(regEx);
		JButton btn = new JButton("X");
		
		vectorBoxes.add(box);
		vectorButtons.add(btn);
		int pos = vectorBoxes.size()-1;
		
//		box.setHorizontalAlignment(SwingConstants.LEFT);
//		btn.setHorizontalAlignment(SwingConstants.LEFT);
		panelBoxes.add(box);
		panelBoxes.add(btn);
		
		
		vectorButtons.elementAt(pos).addActionListener(this);
		SwingUtilities.updateComponentTreeUI(panelBoxes);
	}
	
	public void removeBox(JButton btn) {
		//Buscamos la posición en que se encuentra el elemento a eliminar
		int pos;
		for (pos = 0 ; pos < vectorButtons.size() ; pos++)
			if(vectorButtons.elementAt(pos)==btn)
				break;
		//Lo eliminamos del panel
		panelBoxes.remove(vectorButtons.elementAt(pos));
		panelBoxes.remove(vectorBoxes.elementAt(pos));
		//Lo eliminamos de los vectores
		vectorButtons.removeElementAt(pos);
		vectorBoxes.removeElementAt(pos);
		SwingUtilities.updateComponentTreeUI(panelBoxes);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnNuevaCaja) {
			addBox();
			return;
		}
		removeBox((JButton)e.getSource());
	}
}
