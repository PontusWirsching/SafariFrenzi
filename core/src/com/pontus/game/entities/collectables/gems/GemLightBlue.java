package com.pontus.game.entities.collectables.gems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.pontus.core.Game;
import com.pontus.core.Money;
import com.pontus.core.Util;
import com.pontus.core.Values;
import com.pontus.core.graphics.gui.Sprite;
import com.pontus.core.resources.Resources;
import com.pontus.core.sound.SoundEffect;
import com.pontus.game.entities.collectables.Collectable;
import com.pontus.game.level.LevelHandler;

public class GemLightBlue extends Collectable {

	public GemLightBlue(float x, float y, float w, float h) {
		super(x, y, w, h);

		setID("VALUEABLE");

		hitboxScale = 2.0f;

		setTexture(Resources.get("gem:cyan_shadow"));

	}

	@Override
	public void spawnGhost() {
		Game.gui.add(new GemSprite("VALUEABLE", position.x, position.y, size.x, size.y));
	}
	
	class GemSprite extends Sprite {

		public Vector2 velocity = new Vector2();

		
		public GemSprite(String id, float x, float y, float width, float height) {
			super(id, x, y, width, height);
			

			setTexture(Resources.get("gem:cyan"));

			velocity.set(0, 0);


		}

		public void remove() {
			Game.gui.remove(this);
		}
		

		@Override
		public void draw(SpriteBatch sb) {

			
			double angle = Util.getAngle(x, y, Game.gui.get("chest").x, Game.gui.get("chest").y);

			velocity.set((float) -(Math.sin(Math.toRadians(angle)) * 5), (float) -(Math.cos(Math.toRadians(angle)) * 5));

			if (Util.getDistance(x, y, Game.gui.get("chest").x, Game.gui.get("chest").y) < 20) {
				LevelHandler.getSelected().score += Values.GEM_LIGHTBLUE;
				Money.add(Values.GEM_LIGHTBLUE);
				SoundEffect.COIN_IN_CHEST.play();
				remove();
			}

			if (!Game.pause) x += velocity.x;
			if (!Game.pause) y += velocity.y;
			
			
			sb.draw(texture, x - width / 2, y - height / 2, width, height);


		}

	}

}
