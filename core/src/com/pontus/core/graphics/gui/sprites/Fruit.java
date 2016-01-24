package com.pontus.core.graphics.gui.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pontus.core.Game;
import com.pontus.core.graphics.gui.GUIElement;
import com.pontus.core.graphics.gui.GUIHandler;
import com.pontus.core.resources.Resources;
import com.pontus.game.entities.collectables.fruit.FruitEntity;
import com.pontus.game.level.LevelHandler;

/**
 * The "fruit" object is what the player sees when he/she just dropped <br>
 * a fruit. As the fruit falls to the spot touched, this will display. <br>
 * Once at the position touched it will turn in to a FruitEntity as the elephant can eat. <br>
 * 
 * @author Pontus Wirsching
 * 
 */
public class Fruit extends GUIElement {
	
	float target = 0;

	public Fruit(String id, float x, float y, float t) {
		super(id, x, y, 75, 75);
		target = t;
		setTexture(Resources.get("gui:fruit:noeffect"));
	}
	
	float v = -10;
	
	@Override
	public void draw(SpriteBatch sb) {
		super.draw(sb);
		v -= 10 * Gdx.graphics.getDeltaTime();
		y += v;
		if (y <= target) {
			y = target;
			LevelHandler.getSelected().entityHandler.add(new FruitEntity(x, y, width, height));
			Game.gui.remove(this);
		}
	}

}
