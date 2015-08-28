package com.pontus.game.entities.level;

import com.pontus.core.resources.Resources;
import com.pontus.game.entities.Entity;

public class Tree extends Entity {

	public Tree(float x, float y, float w, float h) {
		super(x, y, w, h);
		texture = Resources.get("tree");
	}

}
