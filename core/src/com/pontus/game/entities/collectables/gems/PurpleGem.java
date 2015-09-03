package com.pontus.game.entities.collectables.gems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.pontus.core.Game;
import com.pontus.core.Util;
import com.pontus.core.graphics.gui.GUIHandler;
import com.pontus.core.graphics.gui.Sprite;
import com.pontus.core.resources.Resources;
import com.pontus.game.entities.collectibles.Collectible;

public class PurpleGem extends Collectible {

	public PurpleGem(float x, float y, float w, float h) {
		super(x, y, w, h);

		setID("gem_purple");

		hitboxScale = 1.5f;

		setTexture(Resources.get("gem:purple_shadow"));

	}

	@Override
	public void spawnGhost() {
		GUIHandler.add(new GemSprite("gem:purple_sprite", position.x, position.y, size.x, size.y));
	}
	
	class GemSprite extends Sprite {

		public Vector2 velocity = new Vector2();

		
		public GemSprite(String id, float x, float y, float width, float height) {
			super(id, x, y, width, height);
			

			setTexture(Resources.get("gem:purple"));

			velocity.set(0, 0);


		}

		public void remove() {
			GUIHandler.remove(this);
		}
		

		@Override
		public void draw(SpriteBatch sb) {

			
			double angle = Util.getAngle(x, y, GUIHandler.get("chest").x, GUIHandler.get("chest").y);

			velocity.set((float) -(Math.sin(Math.toRadians(angle)) * 5), (float) -(Math.cos(Math.toRadians(angle)) * 5));

			if (Util.getDistance(x, y, GUIHandler.get("chest").x, GUIHandler.get("chest").y) < 20) {
				
				// Increase coins
				Game.score += 10;
				remove();
			}

			x += velocity.x;
			y += velocity.y;
			
			
			sb.draw(texture, x - width / 2, y - height / 2, width, height);


		}

	}

}
