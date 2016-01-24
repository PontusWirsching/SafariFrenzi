package com.pontus.game.entities.mobs.friends;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pontus.core.Game;
import com.pontus.core.graphics.CustomAnimation;
import com.pontus.game.ai.Behaviors;
import com.pontus.game.level.LevelHandler;

public class Monkey extends Friend {

	public CustomAnimation animation;

	public Monkey(float x, float y, float w, float h) {
		super(x, y, w, h);

		ai.setBehavior(Behaviors.FETCH);

		flipOffset = true;
		
		speed = 2;
		

		animation = new CustomAnimation("monkey:walk", "textures/animations/monkey/walk.png", 40, 163, 173);
		
	}
	
	@Override
	public void draw(SpriteBatch sb) {
		
		
		speed = 2 + (int) (LevelHandler.getSelected().friendsUpgradeLevel / 2.0);
		
		if (!Game.pause) animation.update();
		drawFrame(sb, animation.getCurrentFrame());
	}

}
