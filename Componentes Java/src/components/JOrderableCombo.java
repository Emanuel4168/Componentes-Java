package components;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class JOrderableCombo extends JPanel implements ActionListener{
	private JButton btnOrderItems,btnOriginalItems;
	private JComboBox<String> combo;
	private JPanel buttonsContainer;
	private Vector<String> elements,sortedElements;
	private ComboBoxModel<String> model;

	
	public JOrderableCombo() {
		elements = new Vector<String>();
		sortedElements = new Vector<String>();
		btnOrderItems = new JButton("Ordenados");
		btnOriginalItems = new JButton("Original");
		combo = new JComboBox<String>();
		
		createView();
		addListeners();
		setVisible(true);
	}
	
	private void createView() {
		add(combo);
		buttonsContainer = new JPanel(new GridLayout(0,1,5,5));
		buttonsContainer.add(btnOrderItems);
		buttonsContainer.add(btnOriginalItems);
		add(buttonsContainer);
	}
	
	private void addListeners() {
		btnOrderItems.addActionListener(this);
		btnOriginalItems.addActionListener(this);
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
		}
		if(e.getSource() == btnOriginalItems) {
			model = new DefaultComboBoxModel(elements);
			combo.setModel(model);
		}
	} 
	
}
