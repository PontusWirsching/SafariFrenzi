package com.pontus.game.entities.mobs;

import com.pontus.game.ai.AI;
import com.pontus.game.entities.Entity;

public class Mob extends Entity {

	public AI ai;
	
	
	public Mob(float x, float y, float w, float h) {
		super(x, y, w, h);
		ai = new AI(this);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		ai.update(delta);
	}
	
}
