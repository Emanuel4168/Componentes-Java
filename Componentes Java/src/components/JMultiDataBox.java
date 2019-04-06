package components;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

public class JMultiDataBox extends JTextField implements FocusListener{
	public static final String EMAIL_REGEX = "[A-Za-z0-9]{1}[A-Za-z0-9-_.]{5,20}[@]{1}[A-Za-z]{4,15}[.]{1}[A-Za-z]{2,3}";
	public static final String TEL_REGEX = "[0-9]{3}[-]{1}[0-9]{7}";
	public static final String RFC_regex = "[A-Z]{4}[0-9]{6}[A-Z0-9]{3}";
	
	private String currentRegex;
	
	public  JMultiDataBox(String regEx) {
		super(30);
		currentRegex = regEx;
		setListener();
	}
	
	public void setListener() {
		this.addFocusListener(this);
	}
	
	public boolean matches() {
		return getText().matches(currentRegex);
	}

	public String getCurrentRegex() {
		return currentRegex;
	}

	public void setCurrentRegex(String currentRegex) {
		this.currentRegex = currentRegex;
	}

	@Override
	public void focusGained(FocusEvent arg0) {}

	@Override
	public void focusLost(FocusEvent evt) {
		if(matches()) {
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			return;
		}
		
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
	}
	
	
}
