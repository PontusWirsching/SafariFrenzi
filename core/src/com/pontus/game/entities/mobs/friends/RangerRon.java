package com.pontus.game.entities.mobs.friends;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pontus.core.resources.Resources;
import com.pontus.game.ai.Behaviors;
import com.pontus.game.entities.collectables.gems.PurpleGem;
import com.pontus.game.level.LevelHandler;

public class RangerRon extends Friend {

	Animation a;
	float stateTime = 0;
	TextureRegion currentFrame;
	
	public RangerRon(float x, float y, float w, float h) {
		super(x, y, w, h);
		ai.setBehavior(Behaviors.ROAM);
		ai.roamBounds = new Rectangle(-150, 125, 300, 50);
		
		topBounds = 175;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		frames.add(Resources.get("ranger_ron:walk_01"));
		frames.add(Resources.get("ranger_ron:walk_02"));
		frames.add(Resources.get("ranger_ron:walk_03"));
		frames.add(Resources.get("ranger_ron:walk_04"));
		frames.add(Resources.get("ranger_ron:still"));
		
		a = new Animation(0.15f, frames);
		a.setPlayMode(PlayMode.LOOP);
		
		flipOffset = true;
		
	}
	

	float timer = 0;
	float dropRate = 0.2f;
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		timer += Gdx.graphics.getDeltaTime();
		if (timer >= 1 / dropRate) {
			timer = 0;
			LevelHandler.getSelected().entityHandler.add(new PurpleGem(position.x, position.y - size.y / 2, 50, 50).setHealth(1));
		}
		
		
	}
	
	@Override
	public void draw(SpriteBatch sb) {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = a.getKeyFrame(stateTime);
		drawFrame(sb, currentFrame);
	}

}
