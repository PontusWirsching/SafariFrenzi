package com.pontus.game.ai;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pontus.core.Util;
import com.pontus.game.entities.Entity;
import com.pontus.game.entities.mobs.Mob;
import com.pontus.game.level.LevelHandler;

public class AI {

	public Rectangle roamBounds = new Rectangle(-100, -100, 200, 200);

	/**
	 * What behavior does this AI act by.
	 * 
	 * @see Behaviors
	 */
	public Behaviors behavior = null;

	/**
	 * The entity that has this AI attached to it.
	 * 
	 * @see Entity
	 */
	public Entity entity;

	public Random random = new Random();

	public AI(Entity e) {
		entity = e;
		target = new Vector2(entity.position);
	}

	public void setBehavior(Behaviors b) {
		behavior = b;
	}

	Entity hostile = null;

	public float timer = 1.0f;
	public float actionsPerSecond = 0.5f; // How many times per second should
											// the mob change action.

	public Vector2 target;

	public Entity entityTarget = null;

	/**
	 * Will return an array of all mobs that has this mob as a target.
	 */
	public ArrayList<Entity> getHunters(Entity target) {
		ArrayList<Entity> hunters = new ArrayList<Entity>();
		for (Entity e : LevelHandler.getSelected().entityHandler.entities) {
			if (e.target == target) {
				hunters.add(e);
			}
		}
		return hunters;
	}

	public float getDistance(Entity e1, Entity e2) {
		float d = (float) Util.getDistance(e1.position.x, e1.position.y, e2.position.x, e2.position.y);
		return d;
	}

	private void getNearestFruit() {
		double nearest = 3000;
		target = null;
		entityTarget = null;
		for (int i = 0; i < LevelHandler.getSelected().entityHandler.entities.size(); i++) {
			if (i >= LevelHandler.getSelected().entityHandler.entities.size()) break;
			Entity e = LevelHandler.getSelected().entityHandler.get(i);
			if (e == null) continue;
			if (e.entityID.equals("FRUIT")) {
				double d = Util.getDistance(entity.position.x, entity.position.y, e.position.x, e.position.y);
				if (d <= nearest) {
					nearest = d;
					target = new Vector2(e.position);
					entityTarget = e;
				}
			}
		}
	}
	
	public double updateHostile() {
		double distanceRecord = 150;
		hostile = null;
		for (int i = 0; i < LevelHandler.getSelected().entityHandler.entities.size(); i++) {
			if (i >= LevelHandler.getSelected().entityHandler.entities.size()) break;
			Entity e = LevelHandler.getSelected().entityHandler.get(i);
			if (e.hostile) {
				if (e != entity) {
					double distance = Util.getDistance(entity.position.x, entity.position.y, e.position.x, e.position.y);
					if (distance <= distanceRecord) {
						distanceRecord = distance;
						hostile = e;
					}
				}
			} else continue;
		}
		return distanceRecord;
	}

	public void update(float delta) {
		
		if (behavior == Behaviors.PANIC) {
			
			// 1. get angle to target.
			
			// 2. rotate that angle 180 degrees.
			
			// 3. run that way
			
			updateHostile();
			if (hostile == null) {
				setBehavior(Behaviors.ROAM);
				return;
			}
			double angleToTarget = Util.getAngle(entity.position.x, entity.position.y, hostile.position.x, hostile.position.y);
			moveTo(Util.getPosFromAngle((int) entity.position.x, (int) entity.position.y, (int) entity.speed, Math.toRadians(angleToTarget)));
			
		}

		if (behavior == Behaviors.FETCH) {

			// 1. look for any fruit drop.

			// 2. Move to it and pick it up!

			getNearestFruit();

			if (target != null) {
				if (getHunters(entityTarget).size() > 0) {
					getNearestFruit();
				}
				entity.target = entityTarget;
				moveTo(target);
			} else {
				target = new Vector2(entity.position);
				behavior = Behaviors.ROAM;
			}
			
			if (updateHostile() < 100) {
				setBehavior(Behaviors.PANIC);
			}
			

		}

		if (behavior == Behaviors.HUNT_NEAREST) {

			// Get nearest non hostile mob.

			double distanceRecord = 1000;

			// Loop trough all entities and get the closest one, set is as this
			// entities target.
			for (int i = 0; i < LevelHandler.getSelected().entityHandler.entities.size(); i++) {
				if (i >= LevelHandler.getSelected().entityHandler.entities.size()) break;
				Entity e = LevelHandler.getSelected().entityHandler.get(i);
				if (!(e instanceof Mob)) continue;

				if (!e.hostile) {

					if (e != entity) {
						double distance = Util.getDistance(entity.position.x, entity.position.y, e.position.x, e.position.y);
						if (distance <= distanceRecord) {
							distanceRecord = distance;
							entity.target = e;
						}
					}
				} else continue;
			}

			if (entity.target != null) {
				double distance = Util.getDistance(entity.position.x, entity.position.y, entity.target.position.x, entity.target.position.y);
				if (distance >= 100) {
					moveTo(entity.target.position);
				} else {
					entity.velocity.set(0, 0);
					entity.attack();
				}
			}
		}

		// If this is a roaming entity.
		if (behavior == Behaviors.ROAM) {

			Entity drop = null;

			float d = 10000;
			for (Entity dd : LevelHandler.getSelected().entityHandler.getMultipleByID("FRUIT")) {
				if (dd == null) continue;
				float distance = getDistance(entity, dd);
				if (distance <= d) {
					d = distance;
					drop = dd;
				}
			}

			if (drop != null) {

				float ddd = 10000;
				Entity allowedElephant = null;
				for (Entity elephant : LevelHandler.getSelected().entityHandler.getMultipleByID("spawnedElephant")) {
					if (elephant == null) continue;
					float distance = getDistance(drop, elephant);
					if (distance <= ddd) {
						ddd = distance;
						allowedElephant = elephant;
					}
				}

				if (entity == allowedElephant) if (getHunters(drop).size() == 0) {
					entity.target = drop;
					behavior = Behaviors.FETCH;

				}
			}

			// Check if hostile mob is nearby, if so get the fuck out of there..
			// (switch to PANIC state.)


			updateHostile();

			timer += Gdx.graphics.getDeltaTime();

			if (timer >= 1 / actionsPerSecond) {

				float randomX = random.nextFloat() * roamBounds.width + roamBounds.x;
				float randomY = random.nextFloat() * roamBounds.height + roamBounds.y;

				target = new Vector2(randomX, randomY);

				timer = 0.0f;
			}

			moveTo(target);

			// Hostile mob found, escape!
			if (hostile != null) {
				behavior = Behaviors.PANIC; // Set behavior to panic, hostile
			}

		}
	}

	public void moveTo(Vector2 target) {
		double distance = Util.getDistance(entity.position.x, entity.position.y, target.x, target.y);
		double angle = Util.getAngle(entity.position.x, entity.position.y, target.x, target.y);

		entity.velocity.x = (float) -(Math.sin(Math.toRadians(angle)) * entity.speed);
		entity.velocity.y = (float) -(Math.cos(Math.toRadians(angle)) * entity.speed);

		distance = Util.getDistance(entity.position.x, entity.position.y, target.x, target.y);

		if (distance <= entity.speed) {
			entity.position.x = target.x;

			entity.velocity.x = 0;
			entity.velocity.y = 0;

		}
	}

}
