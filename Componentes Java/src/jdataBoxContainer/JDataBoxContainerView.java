package jdataBoxContainer;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

import components.JMultiDataBox;

public class JDataBoxContainerView extends JPanel {

	private  JPanel panelRadios, panelButton, panelBoxes;
	private JRadioButton rbEmail, rbRFC, rbTel;
	private ButtonGroup radios;
	private JButton btnNuevaCaja;
	private int noBoxes;
	private Vector<JMultiDataBox> vectorBoxes;
	private Vector<JButton> vectorButtons;
	private Color primaryColor,dangerColor,fieldErrorColor,fieldSuccessColor;
	private JDataBoxContainerController controller;

	protected JDataBoxContainerView() {
		this(10,new Color(204, 204, 255),new Color(255, 128, 128));
	}

	public JDataBoxContainerView(int noBoxes, Color success,Color error) {
		this.noBoxes = noBoxes;
		primaryColor = success;
		dangerColor = error;
		this.setBorder((BorderFactory.createLineBorder(Color.black)));

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
		btnNuevaCaja.setBackground(primaryColor);

		vectorBoxes = new Vector<JMultiDataBox>();
		vectorButtons = new Vector<JButton>();

		createViews();
	}

	private void createViews() {
		//		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS)); 
		setLayout(new BorderLayout());

		panelRadios.setLayout(new GridLayout(0,3,5,5));
		panelRadios.add(rbEmail);
		panelRadios.add(rbRFC);
		panelRadios.add(rbTel);

		panelButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnNuevaCaja.setPreferredSize(new Dimension(200,20));
		panelButton.add(btnNuevaCaja);

		panelBoxes.setLayout(new GridLayout(0,1,5,5));

		add(panelRadios,BorderLayout.NORTH);
		add(panelButton,BorderLayout.CENTER);
		add(panelBoxes,BorderLayout.SOUTH);
	}

	protected String getText(int pos) {
		return vectorBoxes.get(pos).getText();
	}

	protected String[] getAllTexts() {
		String[] texts = new String[vectorBoxes.size()];
		for(int i = 0; i < texts.length; i++)
			texts[i] = vectorBoxes.get(i).getText();

		return texts;
	}

	protected void addBox() {
		System.out.println(vectorBoxes.size());
		JPanel auxPanel = new JPanel();

		if(vectorBoxes.size()>=noBoxes)
			return;

		if(vectorBoxes.size() == 0)
			disableRadios();

		String regEx = (rbEmail.isSelected())? JMultiDataBox.EMAIL_REGEX : (rbRFC.isSelected())? JMultiDataBox.RFC_REGEX : JMultiDataBox.TEL_REGEX;
		JMultiDataBox box = new JMultiDataBox(regEx);
		JButton btn = new JButton();
		btn.setBackground(dangerColor);

		vectorBoxes.add(box);
		vectorButtons.add(btn);

		int pos = vectorBoxes.size()-1;

		auxPanel.add(box);
		auxPanel.add(btn);
		panelBoxes.add(auxPanel);


		vectorButtons.elementAt(pos).addActionListener(controller);
		SwingUtilities.updateComponentTreeUI(panelBoxes);
		btn.setIcon(changeSize("delete_field.png",20,10));
	}

	protected void removeBox(JButton btn) {
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

		if(vectorButtons.size() < 1){
			rbEmail.setEnabled(true);
			rbRFC.setEnabled(true);
			rbTel.setEnabled(true);
		}
		SwingUtilities.updateComponentTreeUI(panelBoxes);
	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if(e.getSource() == btnNuevaCaja) {
//			addBox();
//			return;
//		}
//		removeBox((JButton)e.getSource());
//	}

	private static ImageIcon changeSize(String imgName, int width, int height) {
		ImageIcon img = new ImageIcon(imgName);
		Image imagenConvertir = img.getImage();
		img.setImage(imagenConvertir.getScaledInstance(width, height, Image.SCALE_SMOOTH));
		return img;
	}

	private void disableRadios() {
		JRadioButton[] radioButtons = {rbEmail,rbRFC,rbTel};
		for(int i = 0; i < radioButtons.length; i++) {
			if(!radioButtons[i].isSelected())
				radioButtons[i].setEnabled(false);
		}
	}

	protected void setController(JDataBoxContainerController controller) {
		this.controller = controller;
		btnNuevaCaja.addActionListener(controller);
	}
	
	protected boolean  isBtnNuevaCaja(JButton btnEvent) {
		return this.btnNuevaCaja == btnEvent;
	}

}