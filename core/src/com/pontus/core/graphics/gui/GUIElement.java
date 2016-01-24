package com.pontus.core.graphics.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GUIElement {

	/**
	 * Elements id used to get values from this.
	 */
	public String id = "DEFAULT";

	/**
	 * Elements center position.
	 */
	public float x, y, z; // Z is the element "depth".

	/**
	 * Elements total width and height, not from center but from edge to edge.
	 */
	public float width, height;

	public float originalWidth, originalHeight;

	/**
	 * The texture to be displayed for the element. This can be changed runtime
	 * from children to animate and/or just change for lets say a button.
	 */
	public TextureRegion texture;

	public GUIElement(String id, float x, float y, float width, float height) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		originalWidth = width;
		originalHeight = height;
	}

	public Object setDepth(int z) {
		this.z = z;
		return this;
	}

	/**
	 * Sets the texture and returns this object.
	 */
	public GUIElement setTexture(TextureRegion texture) {
		this.texture = texture;
		return this;
	}

	public void draw(SpriteBatch sb) {
		if (texture != null) sb.draw(texture, x - width / 2, y - height / 2, width, height);
	}

}
