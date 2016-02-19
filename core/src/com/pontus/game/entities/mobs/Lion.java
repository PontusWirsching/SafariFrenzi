package com.pontus.game.entities.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pontus.core.Game;
import com.pontus.core.graphics.CustomAnimation;
import com.pontus.core.sound.SoundEffect;
import com.pontus.game.ai.Behaviors;
import com.pontus.game.entities.EntityTypes;

public class Lion extends Mob {

	public CustomAnimation animation;

	public Lion(float x, float y, float w, float h) {
		super(x, y, w, h);
		ai.setBehavior(Behaviors.HUNT_NEAREST);

		type = EntityTypes.HOSTILE;

		hitboxScale = 1.3f;

		hostile = true;

		speed = (2.5f * 1.2f) * (2.0f / 3.0f);

		animation = new CustomAnimation("lion:walk", "textures/animations/lion/walk.png", 60, 219, 154);

	}

	@Override
	public void touched() {
		health -= Game.tapDamage;
		Gdx.input.vibrate(100);
		SoundEffect.PLAYER_HIT_LION.play();
	}

	@Override
	public void draw(SpriteBatch sb) {
		if (!Game.pause)
			animation.update();
		drawFrame(sb, animation.getCurrentFrame());
	}

}
