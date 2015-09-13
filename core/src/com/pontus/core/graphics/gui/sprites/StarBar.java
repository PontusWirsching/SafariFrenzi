package com.pontus.core.graphics.gui.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pontus.core.graphics.gui.Sprite;
import com.pontus.core.resources.Resources;
import com.pontus.game.level.Level;
import com.pontus.game.level.LevelHandler;

public class StarBar extends Sprite {

	/**
	 * Starbar states: <br>
	 * 0 = Empty <br>
	 * 1 = One Section Filled <br>
	 * 2 = Two Sections Filled <br>
	 * 3 = Three Sections Filled <br>
	 */
	public int state = 2;

	TextureRegion empty = null;
	TextureRegion one = null;
	TextureRegion two = null;
	TextureRegion three = null;

	public StarBar(String id, float x, float y, float width, float height) {
		super(id, x, y, width, height);
		empty = Resources.get("gui:starbar:empty");
		one = Resources.get("gui:starbar:first");
		two = Resources.get("gui:starbar:second");
		three = Resources.get("gui:starbar:third");
	}

	@Override
	public void draw(SpriteBatch sb) {

		Level currentLevel = LevelHandler.getSelected();

		if (currentLevel.score > currentLevel.starThree) {
			state = 3;
		} else if (currentLevel.score > currentLevel.starTwo) {
			state = 2;
		} else if (currentLevel.score > currentLevel.starOne) {
			state = 1;
		} else {
			state = 0;

		}

		switch (state) {
			case 0:
				sb.draw(empty, x - width / 2, y - height / 2, width, height);
				break;
			case 1:
				sb.draw(one, x - width / 2, y - height / 2, width, height);
				break;
			case 2:
				sb.draw(two, x - width / 2, y - height / 2, width, height);
				break;
			case 3:
				sb.draw(three, x - width / 2, y - height / 2, width, height);
				break;

		}

	}

}
