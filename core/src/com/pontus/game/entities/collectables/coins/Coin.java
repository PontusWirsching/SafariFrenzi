package com.pontus.game.entities.collectables.coins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pontus.core.graphics.gui.GUIHandler;
import com.pontus.core.resources.Resources;
import com.pontus.game.entities.Entity;
import com.pontus.game.level.LevelHandler;

public class Coin extends Entity {

	Animation a;
	float stateTime = (float) Math.random();
	TextureRegion currentFrame;
	public Vector2 origin;

	float time = 0.0f;
	float decayTime = 4.0f;
	boolean decaying = false;
	float secondsToWarn = 2.0f;
	boolean show = true;
	float flickerTimer = 0.0f;
	float flickerRate = 15.0f;

	public Coin(float x, float y, float w, float h, Vector2 v) {
		super(x, y, w, h);

		origin = new Vector2(position);

		hitboxScale = 1.5f;

		velocity = v;

		Array<TextureRegion> frames = new Array<TextureRegion>();

		frames.add(new TextureRegion(Resources.get("coin:gold:1_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:2_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:3_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:4_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:5_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:6_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:7_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:8_shadow")));

		a = new Animation(0.15f, frames);
		a.setPlayMode(PlayMode.LOOP);

	}

	public Coin(float x, float y, float w, float h) {
		super(x, y, w, h);
		origin = new Vector2(position);

		hitboxScale = 1.5f;

		velocity = new Vector2(0, 0);

		Array<TextureRegion> frames = new Array<TextureRegion>();

		frames.add(new TextureRegion(Resources.get("coin:gold:1_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:2_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:3_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:4_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:5_shadow")));
		frames.add(new TextureRegion(Resources.get("coin:gold:6_shadow")));

		a = new Animation(0.15f, frames);
		a.setPlayMode(PlayMode.LOOP);

	}

	public void draw(SpriteBatch sb) {

		time += Gdx.graphics.getDeltaTime();
		if (time >= decayTime) {
			LevelHandler.getSelected().entityHandler.remove(this);
		}

		if (time >= decayTime - secondsToWarn) {
			decaying = true;
		}

		if (health != 1) velocity.y += -Gdx.graphics.getDeltaTime() * 10;
		if (position.y <= origin.y) {
			// position.y = origin.y;

		}

		float aa = 0.5f;

		if (velocity.x > 0) {
			velocity.x -= aa * Gdx.graphics.getDeltaTime();
		}
		if (velocity.x < 0) {
			velocity.x += aa * Gdx.graphics.getDeltaTime();
		}

		stateTime += Gdx.graphics.getDeltaTime();

		currentFrame = a.getKeyFrame(stateTime);

		if (decaying) {
			flickerTimer += Gdx.graphics.getDeltaTime();
			if (flickerTimer >= 1 / flickerRate) {
				flickerTimer = 0.0f;
				show = !show;
			}
		}

		if (show) sb.draw(currentFrame, position.x - size.x / 2, position.y - size.y / 2, size.x, size.y);

	}

	

	@Override
	public void touched() {
		health = 0;
		GUIHandler.add(new com.pontus.core.graphics.gui.sprites.Coin("coin_sprite", position.x, position.y, size.x, size.y).setStateTime(stateTime));
		super.touched();
	}

}
