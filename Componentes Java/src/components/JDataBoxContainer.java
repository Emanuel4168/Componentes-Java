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
	public Color successColor,errorColor;
	
	public JDataBoxContainer() {
		this(10,new Color(204, 204, 255),new Color(255, 128, 128));
	}
	
	public JDataBoxContainer(int noBoxes, Color success,Color error) {
		this.noBoxes = noBoxes;
		successColor = success;
		errorColor = error;
		panelRadios = new JPanel();
		panelButton = new JPanel();
		panelBoxes = new JPanel();
		
		rbEmail = new JRadioButton("E-mail");
		rbRFC = new JRadioButton("RFC");
		rbTel = new JRadioButton("Teléfono");
		
		radios = new ButtonGroup();
		radios.add(rbEmail);
		radios.add(rbRFC);
		radios.add(rbTel);
		rbEmail.setSelected(true);
		
		btnNuevaCaja = new JButton("Nueva caja");
		btnNuevaCaja.setBackground(successColor);
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
		
		panelBoxes.setLayout(new GridLayout(0,1,5,5));
		
		add(panelRadios);
		add(panelButton);
		add(panelBoxes);
	}
	
	public String getText(int pos) {
		return vectorBoxes.get(pos).getText();
	}
	
	public String[] getAllTexts() {
		String[] texts = new String[vectorBoxes.size()];
		for(int i = 0; i < texts.length; i++)
			texts[i] = vectorBoxes.get(i).getText();
	
		return texts;
	}
	
	public void addBox() {
		System.out.println(vectorBoxes.size());
		JPanel auxPanel = new JPanel();
		
		if(vectorBoxes.size()>=noBoxes)
			return;
		
		String regEx = (rbEmail.isSelected())? JMultiDataBox.EMAIL_REGEX : (rbRFC.isSelected())? JMultiDataBox.RFC_REGEX : JMultiDataBox.TEL_REGEX;
		JMultiDataBox box = new JMultiDataBox(regEx);
		JButton btn = new JButton();
		btn.setBackground(errorColor);
		
		vectorBoxes.add(box);
		vectorButtons.add(btn);
		
		int pos = vectorBoxes.size()-1;
	
		auxPanel.add(box);
		auxPanel.add(btn);
		panelBoxes.add(auxPanel);
		
		
		vectorButtons.elementAt(pos).addActionListener(this);
		SwingUtilities.updateComponentTreeUI(panelBoxes);
		btn.setIcon(changeSize("delete_field.png",20,10));
	}
	
	public void removeBox(JButton btn) {
		//Buscamos la posición en que se encuentra el elemento a eliminar
		int pos;
		for (pos = 0 ; pos < vectorButtons.size() ; pos++)
			if(vectorButtons.elementAt(pos)==btn)
				break;
		//Lo eliminamos del panel
		panelBoxes.remove(pos);
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
	
	private static ImageIcon changeSize(String imgName, int width, int height) {
		ImageIcon img = new ImageIcon(imgName);
		Image imagenConvertir = img.getImage();
		img.setImage(imagenConvertir.getScaledInstance(width, height, Image.SCALE_SMOOTH));
		return img;
	}
}
