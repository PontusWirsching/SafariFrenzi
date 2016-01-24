package com.pontus.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.pontus.core.Game;
import com.pontus.core.Util;
import com.pontus.core.graphics.Renderable;
import com.pontus.core.scripts.Script;
import com.pontus.game.entities.mobs.Elephant;
import com.pontus.game.entities.mobs.Mob;
import com.pontus.game.level.LevelHandler;

/**
 * Entity is something made to be rendered on the screen.<br>
 * Most likely to be rendered with an EntityManager.
 * 
 * @author Pontus Wirsching
 */
public class Entity implements Renderable {

	/**
	 * How much damage an entity can do to another.
	 */
	public float attackDamage = 1;
	
	/**
	 * Is the entity hostile?
	 */
	public boolean hostile = false;

	/**
	 * What speed does the entity have.
	 */
	public double speed = 5.0f;

	/**
	 * Script used for simple movement.
	 */
	public Script script;

	/**
	 * Position for entity to be rendered at.
	 */
	public Vector2 position = new Vector2(0, 0);

	/**
	 * Size for entity to be rendered with.
	 */
	public Vector2 size = new Vector2(0, 0);

	/**
	 * Pixels per second.
	 */
	public Vector2 velocity = new Vector2(0, 0);

	/**
	 * Texture to display.
	 */
	public TextureRegion texture;

	/**
	 * This mob's target.
	 */
	public Entity target;

	/**
	 * 
	 */
	public String entityID = "UNDEFINED";

	/**
	 * 
	 */
	public EntityTypes type = EntityTypes.TARGET;

	/**
	 * How many attacks per second.
	 */
	public float attackRate = 1.0f;

	/**
	 * A boolean that is set by the attackTimer and attackRate values. Basicly
	 * limit the attacks..
	 */
	private boolean canAttack = true;

	/**
	 * The health is also like the hit points, so it would take 'health' amount
	 * of touches to kill a lion.
	 */
	public int health = 10;

	/**
	 * The max health variable is auto set from the health variable in the
	 * constructor.
	 */
	public int maxHealth = health;

	/**
	 * How scaled the hit box should be relative to the actual size. Example:
	 * 1.5f would make the hit box 50% bigger.
	 */
	public float hitboxScale = 1.0f;

	/**
	 * Should the entity be flipped, all moving mobs should be drawn facing left
	 * by default.
	 */
	public boolean flip = false;

	/**
	 * This will reverse the flip variable.
	 */
	public boolean flipOffset = false;

	/**
	 * The Mob can't move above this y position.
	 */
	public int topBounds = 100;

	public Entity(float x, float y, float w, float h) {
		position = new Vector2(x, y);
		size = new Vector2(w, h);
	}

	/**
	 * Sets the texture and returns this entity.
	 */
	public Entity setTexture(TextureRegion t) {
		texture = t;
		return this;
	}

	public Entity setHealth(int health) {
		this.health = health;
		this.maxHealth = health;
		this.prevHealth = health;
		return this;
	}

	/**
	 * Sets the entityID and returns this entity.
	 */
	public Entity setID(String id) {
		entityID = id;
		return this;
	}

	/**
	 * This method gets called once the entity just died.
	 */
	public void died() {

	}

	/**
	 * Returns the distance to a specific entity from this entity.
	 */
	public float getDistanceTo(Entity target) {
		return (float) Util.getDistance(position.x, position.y, target.position.x, target.position.y);
	}

	public void update(float delta) {
		position.x += velocity.x;
		position.y += velocity.y;
		if (script != null) script.update(delta, position, velocity, size);

		if (attackTimer >= attackRate && !canAttack) {

			canAttack = true;

			attackTimer = 0.0f; // Reset timer.
		} else {
			attackTimer += Gdx.graphics.getDeltaTime();
		}

		if (health <= 0) {
			died();
			LevelHandler.getSelected().entityHandler.remove(this);
		}

		if (velocity.x < 0) {
			flip = !flipOffset;
		}
		if (velocity.x > 0) {
			flip = flipOffset;
		}

		if (this instanceof Mob) {
			if (position.y >= topBounds) {
				position.y = topBounds;
			}
		}

	}

	float attackTimer = attackRate;

	/**
	 * Will attack this entities target.
	 */
	public void attack() {
		if (canAttack) {
			if (target instanceof Elephant) if (target != null) target.health -= 1 * -Game.elephantHyde; 
			if (target != null) target.health -= attackDamage;
			canAttack = false;
		}
	}

	/**
	 * Gets called when user touches this entity with mouse / finger.
	 */
	public void touched() {

	}

	int prevHealth = health;
	float a = 0;

	@Override
	public void draw(SpriteBatch sb) {

		if (texture != null) {
			if (flip) {
				sb.draw(texture, position.x + size.x / 2, position.y - size.y / 2, -size.x, size.y);
			} else {
				sb.draw(texture, position.x - size.x / 2, position.y - size.y / 2, size.x, size.y);
			}
		}
	}

	public void drawFrame(SpriteBatch sb, TextureRegion frame) {

		if (flip) {
			sb.draw(frame, position.x + size.x / 2, position.y - size.y / 2, -size.x, size.y);
		} else {
			sb.draw(frame, position.x - size.x / 2, position.y - size.y / 2, size.x, size.y);
		}

		if (health < prevHealth) a = 1;
		a -= 0.15f;
		if (a <= 0) a = 0;
		sb.setColor(1, 0, 0, a);

		if (flip) {
			sb.draw(frame, position.x + size.x / 2, position.y - size.y / 2, -size.x, size.y);
		} else {
			sb.draw(frame, position.x - size.x / 2, position.y - size.y / 2, size.x, size.y);
		}

		sb.setColor(Color.WHITE);

		prevHealth = health;
	}

}
