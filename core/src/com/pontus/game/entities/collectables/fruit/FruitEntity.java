package com.pontus.game.entities.collectables.fruit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pontus.core.Game;
import com.pontus.core.resources.Resources;
import com.pontus.game.entities.Entity;
import com.pontus.game.level.LevelHandler;

public class FruitEntity extends Entity {

	public float foodValue = 0.10f;

	public FruitEntity(float x, float y, float w, float h) {
		super(x, y, w, h);
		setID("FRUIT");
		setTexture(Resources.get("gui:fruit:effect"));
	}

	float time = 0.0f;
	float decayTime = 8.0f;
	boolean decaying = false;
	float secondsToWarn = 2.0f;
	boolean show = true;
	float flickerTimer = 0.0f;
	float flickerRate = 15.0f;

	public void draw(SpriteBatch sb) {

		if (!Game.pause) time += Gdx.graphics.getDeltaTime();
		if (time >= decayTime) {
			LevelHandler.getSelected().entityHandler.remove(this);
		}

		if (time >= decayTime - secondsToWarn) {
			decaying = true;
		}

		if (decaying) {
			if (!Game.pause) flickerTimer += Gdx.graphics.getDeltaTime();
			if (flickerTimer >= 1 / flickerRate) {
				flickerTimer = 0.0f;
				show = !show;
			}
		}

		if (show) drawFrame(sb, texture);

	}

}
