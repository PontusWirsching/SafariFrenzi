package com.pontus.core.graphics.gui.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pontus.core.Game;
import com.pontus.core.Util;
import com.pontus.core.graphics.gui.GUIHandler;
import com.pontus.core.graphics.gui.Sprite;
import com.pontus.core.resources.Resources;

/**
 * Coin that flies over screen to the chest!
 * 
 *  
 * @author Pontus Wirsching
 *
 */
public class Coin extends Sprite {
	
	Animation a;
	float stateTime = (float) Math.random();
	TextureRegion currentFrame;
	
	public Vector2 velocity = new Vector2();

	public Coin(String id, float x, float y, float width, float height) {
		super(id, x, y, width, height);

		Array<TextureRegion> frames = new Array<TextureRegion>();

		frames.add(new TextureRegion(Resources.get("coin:gold:1")));
		frames.add(new TextureRegion(Resources.get("coin:gold:2")));
		frames.add(new TextureRegion(Resources.get("coin:gold:3")));
		frames.add(new TextureRegion(Resources.get("coin:gold:4")));
		frames.add(new TextureRegion(Resources.get("coin:gold:5")));
		frames.add(new TextureRegion(Resources.get("coin:gold:6")));
		frames.add(new TextureRegion(Resources.get("coin:gold:7")));
		frames.add(new TextureRegion(Resources.get("coin:gold:8")));

		velocity.set(0, 0);
		
		a = new Animation(0.15f, frames);
		a.setPlayMode(PlayMode.LOOP);

	}
	
	/**
	 * Used to sync animations!
	 */
	public Coin setStateTime(float stateTime) {
		this.stateTime = stateTime;
		return this;
	}

	public void remove() {
		
		GUIHandler.remove(this);
	}
	
	@Override
	public void draw(SpriteBatch sb) {
		stateTime += Gdx.graphics.getDeltaTime();
		
		double angle = Util.getAngle(x, y, GUIHandler.get("chest").x, GUIHandler.get("chest").y);

		velocity.set((float) -(Math.sin(Math.toRadians(angle)) * 5), (float) -(Math.cos(Math.toRadians(angle)) * 5));
		
		if (Util.getDistance(x, y, GUIHandler.get("chest").x, GUIHandler.get("chest").y) < 20) {
			Game.score++;
			remove();
		}
		
		x += velocity.x;
		y += velocity.y;
		
		currentFrame = a.getKeyFrame(stateTime);
		sb.draw(currentFrame, x - width / 2, y - height / 2, width, height);

	}

}
