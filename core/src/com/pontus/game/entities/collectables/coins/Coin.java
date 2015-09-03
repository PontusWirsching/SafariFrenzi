package com.pontus.game.entities.collectables.coins;

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
import com.pontus.game.entities.collectibles.Collectible;

public class Coin extends Collectible {

	public Vector2 origin;

	public Coin(float x, float y, float w, float h, Vector2 v) {
		super(x, y, w, h);

		setID("coin_gold");

		origin = new Vector2(position);

		hitboxScale = 1.5f;

		velocity = v;

		addFrame(Resources.get("coin:gold:1_shadow"));
		addFrame(Resources.get("coin:gold:2_shadow"));
		addFrame(Resources.get("coin:gold:3_shadow"));
		addFrame(Resources.get("coin:gold:4_shadow"));
		addFrame(Resources.get("coin:gold:5_shadow"));
		addFrame(Resources.get("coin:gold:6_shadow"));
		addFrame(Resources.get("coin:gold:7_shadow"));
		addFrame(Resources.get("coin:gold:8_shadow"));

		setupAnimation(0.15f);

	}

	@Override
	public void spawnGhost() {
		GUIHandler.add(new CoinSprite("coin_sprite", position.x, position.y, size.x, size.y).setStateTime(stateTime));
	}

	public Coin(float x, float y, float w, float h) {
		super(x, y, w, h);
		origin = new Vector2(position);
		setID("coin_sprite");

		hitboxScale = 1.5f;

		velocity = new Vector2(0, 0);

		addFrame(Resources.get("coin:gold:1_shadow"));
		addFrame(Resources.get("coin:gold:2_shadow"));
		addFrame(Resources.get("coin:gold:3_shadow"));
		addFrame(Resources.get("coin:gold:4_shadow"));
		addFrame(Resources.get("coin:gold:5_shadow"));
		addFrame(Resources.get("coin:gold:6_shadow"));
		addFrame(Resources.get("coin:gold:7_shadow"));
		addFrame(Resources.get("coin:gold:8_shadow"));

		setupAnimation(0.15f);

	}

	class CoinSprite extends Sprite {

		Animation a;
		float stateTime = (float) Math.random();
		TextureRegion currentFrame;

		public Vector2 velocity = new Vector2();

		public CoinSprite(String id, float x, float y, float width, float height) {
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
		public CoinSprite setStateTime(float stateTime) {
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

}
