package components;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

public class JMultiDataBox extends JTextField implements FocusListener,KeyListener{
	public static final String EMAIL_REGEX = "[A-Za-z0-9]{1}[A-Za-z0-9-_.]{5,20}[@]{1}[A-Za-z]{4,15}[.]{1}[A-Za-z]{2,3}";
	public static final String TEL_REGEX = "[0-9]{3}[-]{1}[0-9]{7}";
	public static final String RFC_REGEX = "[A-Z]{4}[0-9]{6}[A-Z0-9]{3}";
	private static final int TEL_LENGTH = 11;
	private static final int RFC_LENGTH = 13;
	
	private String currentRegex;
	
	public  JMultiDataBox(String regEx) {
		super(30);
		currentRegex = regEx;
		setListener();
	}
	
	public void setListener() {
		this.addFocusListener(this);
		this.addKeyListener(this);
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

	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent evt) {
		char typedChar = evt.getKeyChar();
		if(currentRegex.equals(TEL_REGEX)) {
			if(!Character.isDigit(typedChar))
				return;
			adjust(typedChar);
			return;
		}
		if(currentRegex.equals(RFC_REGEX)) {
			if(!Character.isLetter(typedChar))
				return;
			System.out.println(typedChar);
			setText(getText()+Character.toUpperCase(typedChar));
			evt.consume();
			return;
		}
		
	}
	
	private void adjust(char newChar) {
		int length = getText().length();
		if(length < 3)
			return;
		
		String formatN = "["+getText().substring(0,3)+"]";
		formatN += (length == 3)? "-":"";
		setText(formatN);
	}
	
	
}
