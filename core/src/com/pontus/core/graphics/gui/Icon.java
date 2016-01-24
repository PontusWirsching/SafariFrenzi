package com.pontus.core.graphics.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pontus.core.Game;
import com.pontus.core.Money;
import com.pontus.core.resources.Resources;

public class Icon extends Sprite {

	public TextureRegion priceBox;

	public boolean showPriceBox = true;

	public Icon setPriceBoxVisible(boolean t) {
		showPriceBox = t;
		return this;
	}

	private float value = 9999.0f;

	public float getValue() {
		return value;
	}

	public int getValueAsInt() {
		return Math.round(value);
	}

	public Icon setValue(float f) {
		value = f;
		return this;
	}

	public void increaseValue(float f) {
		value += f;
	}

	/**
	 * Sets the texture and returns this object.
	 */
	public Icon setTexture(TextureRegion texture) {
		this.texture = texture;
		return this;
	}

	public Icon(String id, float x, float y, float width, float height) {
		super(id, x, y, width, height);
		priceBox = Resources.get("gui:element:price_box");
		setCustomRenderer(new CustomRenderer() {
			@Override
			public void customDraw(SpriteBatch sb, Sprite sprite) {
				if (showPriceBox) {
					sb.draw(priceBox, sprite.x - 25, Game.topBarPosition - 40, 50, 20);
					String v = Integer.toString(getValueAsInt());
					Game.font.setScale(0.95f);
					Game.font.setColor(Color.BLACK);
					Game.font.draw(sb, v, sprite.x - Game.getTextWidth(v) / 2, Game.topBarPosition - 23.5f);
				}
			}
		});
	}

	@Override
	public void draw(SpriteBatch sb) {
		super.draw(sb);
		if (texture != null) {
			if (value > Money.chest) {
				sb.setColor(new Color(0, 0, 0, 0.5f));
				sb.draw(texture, x - width / 2, y - height / 2, width, height);
				sb.setColor(Color.WHITE);
			} else {
				sb.draw(texture, x - width / 2, y - height / 2, width, height);
			}
		}
		if (customRenderer != null)
			customRenderer.customDraw(sb, this);
	}

}
