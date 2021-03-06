package com.pontus.core.graphics.gui.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pontus.core.Game;
import com.pontus.core.graphics.gui.Sprite;
import com.pontus.game.level.LevelHandler;

public class Topbar extends Sprite {

	public Topbar(String id, float x, float y, float width, float height) {
		super(id, x, y, width, height);
	}

	@Override
	public void draw(SpriteBatch sb) {
		super.draw(sb);

		
		String s = String.valueOf((int) LevelHandler.getSelected().score);
		
		Game.font.setScale(2);
		Game.font.setColor(Color.WHITE);
		Game.font.draw(sb, s, 0 + x - Game.getTextWidth(s) / 2, 0 + y + Game.getTextHeight(s) / 1.4f);

	}

}
