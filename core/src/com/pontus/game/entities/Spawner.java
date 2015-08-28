package com.pontus.game.entities;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.pontus.core.resources.Resources;
import com.pontus.core.scripts.Script;
import com.pontus.game.entities.mobs.Elephant;
import com.pontus.game.level.LevelHandler;

public class Spawner {

	public float spawnRate = 0.0f; // Number of spawns every second.

	public float timer = 0.0f;

	public float chance = 1.0f;

	public JsonValue entity; // Entity to spawn.

	public Vector2 minPos = new Vector2();

	public Vector2 maxPos = new Vector2();

	public Random r = new Random();

	public Spawner(float spawnRate, float chance) {
		this.spawnRate = spawnRate;
		this.chance = chance;
	}

	public void setBounds(float minX, float minY, float maxX, float maxY) {
		minPos.set(minX, minY);
		maxPos.set(maxX, maxY);
	}

	/**
	 * 
	 * @param object
	 *            - Java object to set value to.
	 * @param value
	 *            - Name of the value that should be "fetched".
	 * @param parent
	 *            - Parent JSON value to fetch from.
	 */
	public static String getValue(String value, JsonValue parent) {
		return parent.get(value).asString();
	}
	
	public void update() {

		timer += Gdx.graphics.getDeltaTime();
		if (timer >= 1 / spawnRate) {

			// Percentage chance of the mob to spawn.
			if (r.nextFloat() <= chance) {

				try {
					float x = r.nextFloat() * (maxPos.x - minPos.x) + minPos.x;
					float y = r.nextFloat() * (maxPos.y - minPos.y) + minPos.y;

					Entity e;
					
					Class<?> t = Class.forName("com.pontus.game.entities." + getValue("type", entity));
					int w = Integer.parseInt(getValue("width", entity));
					int h = Integer.parseInt(getValue("height", entity));
					e = (Entity) t.getConstructor(float.class, float.class, float.class, float.class).newInstance(x, y, w, h);
					
					try {
						e.entityID = getValue("id", entity);
					} catch(Exception ee) {
						
					}

					try {
						e.setHealth(Integer.parseInt(getValue("health", entity)));
					} catch(Exception ee) {
						
					}
					
					try {
						e.script = new Script("scripts/" + getValue("script", entity));
					} catch (Exception ee) {

					}
					try {
						e.texture = Resources.get(getValue("texture", entity));
					} catch (Exception ee) {

					}
					try {
						if (e instanceof Elephant) {
							Elephant ee = (Elephant) e;
							ee.dropRate = Float.parseFloat(getValue("dropRate", entity));
						}
					} catch (Exception ee) {

					}
					
					LevelHandler.getSelected().entityHandler.add(e);
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				
				

			}

			timer = 0.0f;
		}

	}
}
