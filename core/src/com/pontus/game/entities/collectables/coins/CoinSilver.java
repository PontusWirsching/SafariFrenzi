package com.pontus.game.entities.collectables.coins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pontus.core.Game;
import com.pontus.core.Money;
import com.pontus.core.Util;
import com.pontus.core.Values;
import com.pontus.core.graphics.gui.Sprite;
import com.pontus.core.resources.Resources;
import com.pontus.game.entities.collectibles.Collectible;
import com.pontus.game.level.LevelHandler;

public class CoinSilver extends Collectible {

	public Vector2 origin;

	public CoinSilver(float x, float y, float w, float h, Vector2 v) {
		super(x, y, w, h);

		setID("VALUEABLE");

		origin = new Vector2(position);

		hitboxScale = 1.8f;

		velocity = v;

		addFrame(Resources.get("coin:silver:1_shadow"));
		addFrame(Resources.get("coin:silver:2_shadow"));
		addFrame(Resources.get("coin:silver:3_shadow"));
		addFrame(Resources.get("coin:silver:4_shadow"));
		addFrame(Resources.get("coin:silver:5_shadow"));
		addFrame(Resources.get("coin:silver:6_shadow"));
		addFrame(Resources.get("coin:silver:7_shadow"));
		addFrame(Resources.get("coin:silver:8_shadow"));

		setupAnimation(0.15f);

	}

	@Override
	public void spawnGhost() {
		Game.gui.add(new CoinSprite("VALUEABLE", position.x, position.y, size.x, size.y).setStateTime(stateTime));
	}

	public CoinSilver(float x, float y, float w, float h) {
		super(x, y, w, h);
		origin = new Vector2(position);
		setID("VALUEABLE");

		hitboxScale = 1.8f;

		velocity = new Vector2(0, 0);

		addFrame(Resources.get("coin:silver:1_shadow"));
		addFrame(Resources.get("coin:silver:2_shadow"));
		addFrame(Resources.get("coin:silver:3_shadow"));
		addFrame(Resources.get("coin:silver:4_shadow"));
		addFrame(Resources.get("coin:silver:5_shadow"));
		addFrame(Resources.get("coin:silver:6_shadow"));
		addFrame(Resources.get("coin:silver:7_shadow"));
		addFrame(Resources.get("coin:silver:8_shadow"));

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

			frames.add(new TextureRegion(Resources.get("coin:silver:1")));
			frames.add(new TextureRegion(Resources.get("coin:silver:2")));
			frames.add(new TextureRegion(Resources.get("coin:silver:3")));
			frames.add(new TextureRegion(Resources.get("coin:silver:4")));
			frames.add(new TextureRegion(Resources.get("coin:silver:5")));
			frames.add(new TextureRegion(Resources.get("coin:silver:6")));
			frames.add(new TextureRegion(Resources.get("coin:silver:7")));
			frames.add(new TextureRegion(Resources.get("coin:silver:8")));

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
			Game.gui.remove(this);
		}

		@Override
		public void draw(SpriteBatch sb) {
			if (!Game.pause) stateTime += Gdx.graphics.getDeltaTime();

			double angle = Util.getAngle(x, y, Game.gui.get("chest").x, Game.gui.get("chest").y);

			velocity.set((float) -(Math.sin(Math.toRadians(angle)) * 5), (float) -(Math.cos(Math.toRadians(angle)) * 5));

			if (Util.getDistance(x, y, Game.gui.get("chest").x, Game.gui.get("chest").y) < 20) {
				LevelHandler.getSelected().score += Values.COIN_SILVER;
				Money.add(Values.COIN_SILVER);
				remove();
			}

			if (!Game.pause) x += velocity.x;
			if (!Game.pause) y += velocity.y;

			currentFrame = a.getKeyFrame(stateTime);
			sb.draw(currentFrame, x - width / 2, y - height / 2, width, height);

		}

	}

}
