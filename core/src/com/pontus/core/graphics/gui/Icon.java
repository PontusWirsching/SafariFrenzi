package com.pontus.core.graphics.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pontus.core.Game;
import com.pontus.core.resources.Resources;

public class Icon extends Sprite {

	private TextureRegion priceBox;
	
	private float value = 10.0f;
	
	public float getValue() {
		return value;
	}
	
	public int getValueAsInt() {
		return Math.round(value);
	}
	
	public void setValue(float f) {
		value = f;
	}
	
	public Icon(String id, float x, float y, float width, float height) {
		super(id, x, y, width, height);
		priceBox = Resources.get("gui:element:price_box");
	}
	
	@Override
	public void draw(SpriteBatch sb) {
		super.draw(sb);
		sb.draw(priceBox, x - 25, Game.topBarPosition - 40, 50, 20);
		String v = Integer.toString(getValueAsInt());
		Game.font.setScale(0.95f);
		Game.font.draw(sb, v, x - Game.font.getBounds(v).width / 2, Game.topBarPosition - 23.5f);
	}

}
