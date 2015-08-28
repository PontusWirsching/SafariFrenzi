package com.pontus.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Sprite extends Entity {

	public Texture texture;
	
	public Sprite(Texture texture, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.texture = texture;
		velocity = new Vector2(0, 0);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	
	@Override
	public void draw(SpriteBatch sb) {
		if (texture != null) {
			sb.draw(texture, position.x, position.y, size.x, size.y);
		}
	}

}
