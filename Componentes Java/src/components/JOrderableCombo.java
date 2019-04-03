package components;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class JOrderableCombo extends JPanel implements ActionListener, KeyListener{
	private JButton btnOrderItems,btnOriginalItems;
	private JComboBox<String> combo;
	private JPanel buttonsContainer;
	private Vector<String> elements,sortedElements;
	private ComboBoxModel<String> model;
	private JTextComponent editor;

	
	public JOrderableCombo() {
		elements = new Vector<String>();
		sortedElements = new Vector<String>();
		btnOrderItems = new JButton("Ordenados");
		btnOriginalItems = new JButton("Original");
		combo = new JComboBox<String>();
		combo.setEditable(true);
		editor = (JTextComponent)combo.getEditor().getEditorComponent();
		
		createView();
		addListeners();
		setVisible(true);
	}
	
	private void createView() {
		add(combo);
		buttonsContainer = new JPanel(new GridLayout(0,1,5,5));
		btnOriginalItems.setEnabled(false);
		buttonsContainer.add(btnOrderItems);
		buttonsContainer.add(btnOriginalItems);
		add(buttonsContainer);
	}
	
	private void addListeners() {
		btnOrderItems.addActionListener(this);
		btnOriginalItems.addActionListener(this);
		editor.addKeyListener(this);
	}
	
	 public void addItem(String obj){
		 combo.addItem(obj);
		 elements.add(obj);
		 sortedElements.add(obj);
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOrderItems) {
			Collections.sort(sortedElements);
			model = new DefaultComboBoxModel(sortedElements);
			combo.setModel(model);
			btnOriginalItems.setEnabled(true);
			btnOrderItems.setEnabled(false);
		}
		if(e.getSource() == btnOriginalItems) {
			model = new DefaultComboBoxModel(elements);
			combo.setModel(model);
			btnOriginalItems.setEnabled(false);
			btnOrderItems.setEnabled(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		if(evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE)
			return;
		
		String filter = editor.getText();
		System.out.println(filter);
		Vector<String> aux = (btnOriginalItems.isEnabled())? elements : sortedElements,
				filterArray = new Vector<String>();
		System.out.println(aux.size());
		for(String i: aux) {
			if(matches(i,filter))
				filterArray.add(i);
		}
		System.out.println("Datos filtrados: "+filterArray.size());
		model = new DefaultComboBoxModel<String>(filterArray);
		combo.setModel(model);
		editor.setText(filter);
	}

	@Override
	public void keyTyped(KeyEvent evt) {

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
