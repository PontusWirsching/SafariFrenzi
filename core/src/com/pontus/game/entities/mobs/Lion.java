package com.pontus.game.entities.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Array;
import com.pontus.core.resources.Resources;
import com.pontus.game.ai.Behaviors;
import com.pontus.game.entities.EntityTypes;

public class Lion extends Mob {

	Animation a;
	float stateTime = (float) Math.random();
	TextureRegion currentFrame;

	public Lion(float x, float y, float w, float h) {
		super(x, y, w, h);
		ai.setBehavior(Behaviors.HUNT_NEAREST);
		
		type = EntityTypes.HOSTILE;
		
		hitboxScale = 1.3f;

		hostile = true;

		speed = 2.5f;

		Array<TextureRegion> frames = new Array<TextureRegion>();

		frames.add(new TextureRegion(Resources.get("lion:walk_01")));
		frames.add(new TextureRegion(Resources.get("lion:walk_02")));
		frames.add(new TextureRegion(Resources.get("lion:walk_03")));
		frames.add(new TextureRegion(Resources.get("lion:walk_04")));

		a = new Animation(0.10f, frames);
		a.setPlayMode(PlayMode.LOOP);

	}

	@Override
	public void touched() {
		health--;
	}
	
	@Override
	public void draw(SpriteBatch sb) {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = a.getKeyFrame(stateTime);
		drawFrame(sb, currentFrame);
	}

}
