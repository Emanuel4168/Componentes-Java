package components;

import java.awt.GridLayout;
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

	}

	@Override
	public void keyReleased(KeyEvent evt) {
		Collections.sort(this.sortedElements);
		String filter = editor.getText();
		char aux = evt.getKeyChar();
		Vector<String> auxV = (btnOriginalItems.isEnabled())? sortedElements : elements ,
				filterArray = new Vector<String>();
		

		if(!Character.isLetter(aux) && !Character.isDigit(aux) &&  !Character.isWhitespace(aux) && evt.getKeyCode() != KeyEvent.VK_BACK_SPACE) {		
			return;
		}

		for(String i: auxV) {
			if(matches(i,filter))
				filterArray.add(i);
		}

		model = new DefaultComboBoxModel<String>(( filter.length() > 0)? filterArray:auxV);
		combo.setModel(model);
		editor.setText(filter);
		combo.showPopup();
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
	
	public String getSelectedItem() {
		return (String)combo.getSelectedItem();
	}

	public int getSelectedIndex() {
		return combo.getSelectedIndex();
	}
	
	public void setItem(String item, int pos) {
		combo.insertItemAt(item, pos);
		elements.set(pos, item);
		sortedElements.add(item);
	}
	
	public List<String> getElementsAsList() {
		return (List<String>) this.elements.clone();
	}
	
	public List<String> getSortedElementsAsList() {
		return (List<String>) this.sortedElements.clone();
	}
	
	public String[] getElementsAsVector() {
		String []elementsVec = new String[elements.size()];
		for (int i = 0; i < elementsVec.length; i++) 
			elementsVec[i] = new String(elements.get(i));
		
		return elementsVec;
	}
	
	public String[] getSortedElensAsVector(){
		String []elementsVec = new String[sortedElements.size()];
		for (int i = 0; i < elementsVec.length; i++) 
			elementsVec[i] = new String(sortedElements.get(i));
		
		return elementsVec;
	}
}
