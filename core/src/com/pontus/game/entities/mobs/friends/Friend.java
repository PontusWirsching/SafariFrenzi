package com.pontus.game.entities.mobs.friends;

import com.pontus.game.entities.EntityTypes;
import com.pontus.game.entities.mobs.Mob;

public class Friend extends Mob {

	public Friend(float x, float y, float w, float h) {
		super(x, y, w, h);
		
		type = EntityTypes.PEACEFUL;
		
	}

}
