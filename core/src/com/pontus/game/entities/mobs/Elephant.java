package com.pontus.game.entities.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pontus.core.Values;
import com.pontus.core.resources.Resources;
import com.pontus.game.ai.Behaviors;
import com.pontus.game.entities.Entity;
import com.pontus.game.entities.collectables.fruit.FruitEntity;
import com.pontus.game.level.LevelHandler;

public class Elephant extends Mob {
	
	Animation a;
	float stateTime = (float)Math.random();
	TextureRegion currentFrame;

	public Elephant(float x, float y, float w, float h) {
		super(x, y, w, h);
		ai.setBehavior(Behaviors.ROAM);
		ai.roamBounds = new Rectangle(-300, -250, 600, 350);
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		frames.add(new TextureRegion(Resources.get("elephant:walk_01")));
		frames.add(new TextureRegion(Resources.get("elephant:walk_02")));
		frames.add(new TextureRegion(Resources.get("elephant:walk_03")));
		frames.add(new TextureRegion(Resources.get("elephant:walk_04")));

		speed = 2.5f;

		a = new Animation(0.15f, frames);
		a.setPlayMode(PlayMode.LOOP);
		
		growRate *= (float) (Math.random() + 1.0) - 0.5f;
		
	}

	/**
	 * The elephants hunger in percentage, 0 is not hungry and 1 is starving.
	 */
	public float hunger = 0;

	/**
	 * The hungerRate is how much more hungry the elephant gets every second.
	 */
	public float hungerRate = 0.01f; // TODO - Change back to 0.05
	
	/**
	 * The elephants max hunger rate, this is implemented so that the elephant gets hungrier as it grows.
	 */
	public float maxHungerRate = 0.03f;

	/**
	 * How many coins should the elephant drop every second.
	 */
	public float dropRate = 1.0f;
	
	/**
	 * Private drop rate timer.
	 */
	private float time = (float) Math.random();

	/**
	 * Growth timer, has to exceed growthRate to "clock".
	 */
	public float growthTimer = 0.0f;

	/**
	 * How often should the elephant check variables and possibly grow?
	 */
	public float growRate = 5f;

	/**
	 * Growth of the elephant, value between 0 -> 1, 1 is full size.
	 */
	public float growth = 0.0f;
	
	/**
	 * Hunger must be greater than this number or the elephant wont grow.
	 */
	public float growThreashold = 0.20f;

	/**
	 * The elephant will grow and display a particle effect of some sort.
	 */
	public void grow() {
		growth += 0.33f;
		if (growth > 1) {
			growth = 1;
		} else {
			setHealth((int) (maxHealth * (growth + 1)));
			size.set(size.x + (extraSize * growth), size.y + (extraSize * growth));
			hungerRate = maxHungerRate * growth;
		}
	}

	public float extraSize = 30;

	@Override
	public void died() {
		super.died();
		LevelHandler.getSelected().score -= Values.ELEPHANT_DEATH;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);

		hunger += hungerRate * Gdx.graphics.getDeltaTime();
		if (hunger >= 1.0f) {
			health = 0;
		}

		for (Entity fruit : LevelHandler.getSelected().entityHandler.getMultipleByID("FRUIT")) {
			if (fruit == null)
				continue;
			if (getDistanceTo(fruit) <= 50) {
				if (fruit instanceof FruitEntity) {
					FruitEntity f = (FruitEntity) fruit;
					hunger -= f.foodValue;
					if (hunger <= 0)
						hunger = 0;
					LevelHandler.getSelected().entityHandler.remove(fruit);
				}
			}
		}

		 growthTimer += (1 / growRate) * Gdx.graphics.getDeltaTime();

		// System.out.println(health + ", " + maxHealth + ", " + hunger + ", " +
		// growth);

		if (growthTimer >= 1.0f) {
			if (health / maxHealth == 1) {
				if (hunger <= growThreashold) {
					grow();
				}
			}
			growthTimer = 0.0f;
		}

		time += Gdx.graphics.getDeltaTime();
		if (time >= dropRate) {

			for (int i = 0; i < 1; i++) {
				LevelHandler.getSelected().spawnCoin(position);
			}

			time = 0.0f;
		}

	}

	@Override
	public void draw(SpriteBatch sb) {
		
//		if (texture != null){
			
			stateTime += Gdx.graphics.getDeltaTime();
			
			currentFrame = a.getKeyFrame(stateTime);
			drawFrame(sb, currentFrame);
			sb.setColor(new Color(0.5f, 1.0f, 0.4f, hunger));
			drawFrame(sb, currentFrame);
			sb.setColor(Color.WHITE);
			
//			Game.font.draw(sb, "HP: " + health, position.x, position.y);
//			Game.font.draw(sb, "MH: " + maxHealth, position.x, position.y - 15);
//			Game.font.draw(sb, "HU: " + hunger, position.x, position.y - 30);

			

//		}
	}

}
