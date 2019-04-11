package jdataBoxContainer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JDataBoxContainer {
	private JDataBoxContainerView view;
	private JDataBoxContainerController controller;
	
	public JDataBoxContainer() {
		view = new JDataBoxContainerView();
		controller = new JDataBoxContainerController(view);
		
		view.setController(controller);
	}
	
	public JPanel getView() {
		return this.view;
	}
	
	public String getTextAt(int pos) {
		return view.getText(pos);
	}
	
	public String[] getAllTexts(int pos) {
		return view.getAllTexts();
	}
	
	
}
