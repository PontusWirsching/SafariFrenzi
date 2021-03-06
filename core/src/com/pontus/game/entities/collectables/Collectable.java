package com.pontus.game.entities.collectables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.pontus.core.Game;
import com.pontus.core.sound.SoundEffect;
import com.pontus.game.entities.Entity;
import com.pontus.game.entities.collectables.coins.CoinBronze;
import com.pontus.game.entities.collectables.coins.CoinGold;
import com.pontus.game.entities.collectables.coins.CoinSilver;
import com.pontus.game.entities.collectables.gems.GemBlue;
import com.pontus.game.entities.collectables.gems.GemLightBlue;
import com.pontus.game.entities.collectables.gems.GemPurple;
import com.pontus.game.level.LevelHandler;

public class Collectable extends Entity {

	/* Animation stuff: */
	Array<TextureRegion> frames = new Array<TextureRegion>();
	Animation a;
	protected float stateTime = (float) Math.random();
	TextureRegion currentFrame;

	/* Collectible properties: */
	float time = 0.0f;
	float decayTime = 4.0f;
	boolean decaying = false;
	float secondsToWarn = 2.0f;
	boolean show = true;
	float flickerTimer = 0.0f;
	float flickerRate = 15.0f;

	public Collectable(float x, float y, float w, float h) {
		super(x, y, w, h);

	}

	public void addFrame(TextureRegion t) {
		frames.add(t);
	}

	public void setupAnimation(float spf) {
		a = new Animation(spf, frames);
		a.setPlayMode(PlayMode.LOOP);
	}

	/**
	 * This will spawn a gui sprite.
	 */
	public void spawnGhost() {

	}

	@Override
	public void draw(SpriteBatch sb) {

		// Decay timer
		if (!Game.pause) time += Gdx.graphics.getDeltaTime();
		if (time >= decayTime) {
			LevelHandler.getSelected().entityHandler.remove(this);
		}

		// Decay timer minus the seconds to warn.
		if (time >= decayTime - secondsToWarn) {
			decaying = true;
		}

		// If health is not 1 then apply gravity.
		if (health != 1) velocity.y += -Gdx.graphics.getDeltaTime() * 10;

		// aa is a vertical drag value.
		float aa = 0.5f;

		// Apply drag.
		if (velocity.x > 0) {
			velocity.x -= aa * Gdx.graphics.getDeltaTime();
		}
		if (velocity.x < 0) {
			velocity.x += aa * Gdx.graphics.getDeltaTime();
		}

		// If the collectible is decaying then make it flicker.
		if (decaying) {
			if (!Game.pause) flickerTimer += Gdx.graphics.getDeltaTime();
			if (flickerTimer >= 1 / flickerRate) {
				flickerTimer = 0.0f;
				show = !show;
			}
		}

		// If there's no texture to be found then render animation.
		if (texture == null) {

			// Increase state time.
			if (!Game.pause) stateTime += Gdx.graphics.getDeltaTime();

			// Get current frame.
			currentFrame = a.getKeyFrame(stateTime);

			// Render the collectible only if show is true.
			if (show) sb.draw(currentFrame, position.x - size.x / 2, position.y - size.y / 2, size.x, size.y);
		} else {

			// Render the collectible only if show is true.
			if (show) sb.draw(texture, position.x - size.x / 2, position.y - size.y / 2, size.x, size.y);
		}

	}

	@Override
	public void touched() {
		health = 0; // Kill item.
		spawnGhost(); // Spawns a flying gui sprite.

		if (this instanceof CoinBronze || this instanceof CoinSilver || this instanceof CoinGold) {
			SoundEffect.COIN_PICKUP.play();
		}
		
		if (this instanceof GemBlue || this instanceof GemLightBlue || this instanceof GemPurple) {
			SoundEffect.GEM_PICKUP.play();
		}
		
		super.touched();
	}

}
