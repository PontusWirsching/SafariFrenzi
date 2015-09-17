package com.pontus.core.graphics.gui.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
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
	
	TextureRegion star_filled = null;
	TextureRegion star_empty = null;

	TextureRegion bar = null;

	public StarBar(String id, float x, float y, float width, float height) {
		super(id, x, y, width, height);
		empty = Resources.get("gui:starbar:empty");
		one = Resources.get("gui:starbar:first");
		two = Resources.get("gui:starbar:second");
		three = Resources.get("gui:starbar:third");
		bar = Resources.get("gui:starbar:fill");
		star_filled = Resources.get("gui:star:filled");
		star_empty = Resources.get("gui:star:empty");
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

		Rectangle a = new Rectangle(x - width / 2 * 0.27f * 3, y - height * 0.32f / 2, width * 0.27f, height * 0.32f);
		Rectangle b = new Rectangle(x - width / 2 * 0.27f, y - height * 0.32f / 2, width * 0.27f, height * 0.32f);
		Rectangle c = new Rectangle(x - width / 2 * 0.27f * -1, y - height * 0.32f / 2, width * 0.27f, height * 0.32f);

		sb.draw(empty, x - width / 2, y - height / 2, width, height);

		float on = (currentLevel.score - currentLevel.starTwo) / (currentLevel.starThree - currentLevel.starTwo);
		float tw = (currentLevel.score - currentLevel.starOne) / (currentLevel.starTwo - currentLevel.starOne);
		float th = (currentLevel.score) / (currentLevel.starOne);

		if (currentLevel.score > currentLevel.starThree) {
			on = 1;
		}
		if (currentLevel.score > currentLevel.starTwo) {
			tw = 1;
		}
		if (currentLevel.score > currentLevel.starOne) {
			th = 1;
		}
		if (currentLevel.score < currentLevel.starTwo) {
			on = 0;
		}
		if (currentLevel.score < currentLevel.starOne) {
			tw = 0;
		}
		if (currentLevel.score < 0) {
			th = 0;
		}

		sb.draw(bar, a.x, a.y, 0, 0, a.width * th, a.height, 1, 1, 0);
		sb.draw(bar, b.x, b.y, 0, 0, b.width * tw, b.height, 1, 1, 0);
		sb.draw(bar, c.x, c.y, 0, 0, c.width * on, c.height, 1, 1, 0);
		
		float starSize = 30;
		
		float star1X = -35;
		float star1Y = 20;
		sb.draw(star_empty, x - starSize / 2 + star1X, y - starSize / 2 + star1Y, starSize, starSize);
		if (currentLevel.score > currentLevel.starOne) sb.draw(star_filled, x - starSize / 2 + star1X, y - starSize / 2 + star1Y, starSize, starSize);
		
		float star2X = 35;
		float star2Y = 20;
		sb.draw(star_empty, x - starSize / 2 + star2X, y - starSize / 2 + star2Y, starSize, starSize);
		if (currentLevel.score > currentLevel.starTwo) sb.draw(star_filled, x - starSize / 2 + star2X, y - starSize / 2 + star2Y, starSize, starSize);
		
		float star3X = 105;
		float star3Y = 20;
		sb.draw(star_empty, x - starSize / 2 + star3X, y - starSize / 2 + star3Y, starSize, starSize);
		if (currentLevel.score > currentLevel.starThree) sb.draw(star_filled, x - starSize / 2 + star3X, y - starSize / 2 + star3Y, starSize, starSize);
		
		
	}

}
