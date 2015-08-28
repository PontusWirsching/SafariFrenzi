package com.pontus.core.graphics.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite extends GUIElement {

	public CustomRenderer customRenderer;

	public Sprite(String id, float x, float y, float width, float height) {
		super(id, x, y, width, height);
	}

	public Sprite setCustomRenderer(CustomRenderer cr) {
		customRenderer = cr;
		return this;
	}

	/**
	 * Sets the texture and returns this object.
	 */
	public Sprite setTexture(TextureRegion texture) {
		this.texture = texture;
		return this;
	}

	@Override
	public void draw(SpriteBatch sb) {
		super.draw(sb);
		if (customRenderer != null)
			customRenderer.customDraw(sb, this);
	}

}
