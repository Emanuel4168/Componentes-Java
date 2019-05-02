package jdataBoxContainer;

import java.awt.ScrollPane;

public class JDataBoxContainer extends ScrollPane {
	private JDataBoxContainerView view;
	private JDataBoxContainerController controller;
	public JDataBoxContainer() {
		super();
		view = new JDataBoxContainerView();
		controller = new JDataBoxContainerController(view);
		view.setController(controller);
		this.add(view);
	}
}
