package com.pontus.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pontus.core.graphics.screen.GameScreen;

public class Menu extends GameScreen {

	public Menu(String name) {
		super(name);
	}
	
	@Override
	public void render(float delta) {
		sr.begin(ShapeType.Line);
		{
			sr.setColor(Color.RED);
			sr.rect(0, 0, 50, 50);
		}
		sr.end();
	}

}
