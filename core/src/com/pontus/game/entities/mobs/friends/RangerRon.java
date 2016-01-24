package com.pontus.game.entities.mobs.friends;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pontus.core.Game;
import com.pontus.core.graphics.screen.ScreenManager;
import com.pontus.core.resources.Resources;
import com.pontus.game.ai.Behaviors;
import com.pontus.game.entities.collectables.gems.GemPurple;
import com.pontus.game.level.LevelHandler;

public class RangerRon extends Friend {

	Animation a;
	float stateTime = 0;
	TextureRegion currentFrame;
	
	public RangerRon(float x, float y, float w, float h) {
		super(x, y, w, h);
		ai.setBehavior(Behaviors.ROAM);
		
		
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
	public float dropRate = 0.2f;
	
	@Override
	public void update(float delta) {
		super.update(delta);
		float width = ScreenManager.getSelected().camera.viewportWidth;
		ai.roamBounds = new Rectangle(-(width * 0.8f) / 2, 100, (width * 0.6f), 50);
		
		dropRate = 0.2f + (LevelHandler.getSelected().friendsUpgradeLevel / 10f);
		
		if (!Game.pause) timer += Gdx.graphics.getDeltaTime();
		if (timer >= 1 / dropRate) {
			timer = 0;
			LevelHandler.getSelected().entityHandler.add(new GemPurple(position.x, position.y - size.y / 2, 50, 50).setHealth(1));
		}
		
		
	}
	
	@Override
	public void draw(SpriteBatch sb) {
		if (!Game.pause) stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = a.getKeyFrame(stateTime);
		drawFrame(sb, currentFrame);
	}

}
