package com.pontus.game.entities.mobs.friends;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.pontus.core.resources.Resources;
import com.pontus.game.ai.Behaviors;

public class Monkey extends Friend {

	Animation a;
	float stateTime = 0;
	TextureRegion currentFrame;

	public Monkey(float x, float y, float w, float h) {
		super(x, y, w, h);

		ai.setBehavior(Behaviors.FETCH);

		flipOffset = true;
		
		speed = 2;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();

		frames.add(Resources.get("monkey:walk_01"));
		frames.add(Resources.get("monkey:walk_02"));
		frames.add(Resources.get("monkey:walk_03"));
		frames.add(Resources.get("monkey:walk_04"));

		a = new Animation(0.15f, frames);
		a.setPlayMode(PlayMode.LOOP);

	}
	
	@Override
	public void draw(SpriteBatch sb) {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = a.getKeyFrame(stateTime);
		drawFrame(sb, currentFrame);
	}

}
