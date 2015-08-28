package com.pontus.core.graphics.gui;

public class Button extends Sprite {

	
	/**
	 * The ButtonInterface is used to callback when certain actions occur.
	 */
	public ButtonInterface buttonInterface;

	public Button(String id, float x, float y, float width, float height, ButtonInterface buttonInterface) {
		super(id, x, y, width, height);
		this.buttonInterface = buttonInterface;
	}
	
	
	

}
