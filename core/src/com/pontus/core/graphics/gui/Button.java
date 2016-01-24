package com.pontus.core.graphics.gui;

public class Button extends Icon {

	
	/**
	 * The ButtonInterface is used to callback when certain actions occur.
	 */
	public ButtonInterface buttonInterface;
	
	public boolean toggle = false;
	
	public boolean enableHoverEvent = false;
	
	public boolean workWhilePaused = false;
	
	public float hitboxScale = 1.5f;
	
	public Button setHitboxScale(float hitboxScale) {
		this.hitboxScale = hitboxScale;
		return this;
	}
	
	public Button setEnableHoverEvent(boolean b) {
		enableHoverEvent = b;
		return this;
	}
	
	public Button setValue(float f) {
		super.setValue(f);
		return this;
	}
	
	public Button setWorkWhilePaused(boolean b) {
		workWhilePaused = b;
		return this;
	}

	public Button(String id, float x, float y, float width, float height, ButtonInterface buttonInterface) {
		super(id, x, y, width, height);
		this.buttonInterface = buttonInterface;
	}
	
	
	

}
