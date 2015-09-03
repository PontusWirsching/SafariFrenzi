package com.pontus.game.level;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.pontus.core.resources.Resources;
import com.pontus.core.scripts.Script;
import com.pontus.game.entities.Entity;
import com.pontus.game.entities.EntityManager;
import com.pontus.game.entities.Spawner;
import com.pontus.game.entities.SpawnerManager;
import com.pontus.game.entities.collectables.coins.Coin;
import com.pontus.game.entities.mobs.Elephant;

public class Level {

	public String path = "";
	public String name = "NOT_SET";

	public TextureRegion background;

	public EntityManager entityHandler;

	public Level(String path) {
		this.path = path;
		entityHandler = new EntityManager();
		
		
		load();

	}

	private void load() {

		JsonReader r = new JsonReader();

		JsonValue v = r.parse(Gdx.files.internal(path));

		JsonValue properties = v.get("properties");
		JsonValue entities = v.get("entities");
		JsonValue spawners = v.get("spawners");

		boolean fail = false;

		/*
		 * Check if level file is valid.
		 */
		if (properties == null) {
			System.err.println("[ERROR] Level: " + path + ", does not contain a properties section!!");
			fail = true;
		}
		if (entities == null) {
			System.err.println("[ERROR] Level: " + path + ", does not contain an entities section!!");
			fail = true;
		}
		if (spawners == null) {
			System.err.println("[ERROR] Level: " + path + ", does not contain a spawners section!!");
			fail = true;
		}
		if (fail)
			return;

		name = getValue("name", properties);
		background = Resources.get(getValue("background", properties));

		// ======== Load Spawners =========== //

		for (int i = 0; i < spawners.size; i++) {
			JsonValue spawner = spawners.get(i);

			JsonValue entity = null;

			entity = spawner.get("entity");

			float spawnRate = Float.parseFloat(getValue("spawnRate", spawner));
			float chance = Float.parseFloat(getValue("chance", spawner));

			Spawner s = new Spawner(spawnRate, chance);

			JsonValue bounds = spawner.get("bounds");

			float minX = Float.parseFloat(getValue("minX", bounds));
			float minY = Float.parseFloat(getValue("minY", bounds));
			float maxX = Float.parseFloat(getValue("maxX", bounds));
			float maxY = Float.parseFloat(getValue("maxY", bounds));


			s.setBounds(minX, minY, maxX, maxY);
			s.entity = entity;
			SpawnerManager.add(s);

		}

		// ======== Load Entities =========== //

		for (int i = 0; i < entities.size; i++) {
			JsonValue entity = entities.get(i);

			try {
				Class<?> t = Class.forName("com.pontus.game.entities." + getValue("type", entity));

				int x = Integer.parseInt(getValue("x", entity));
				int y = Integer.parseInt(getValue("y", entity));
				int w = Integer.parseInt(getValue("width", entity));
				int h = Integer.parseInt(getValue("height", entity));
				Entity o = (Entity) t.getConstructor(float.class, float.class, float.class, float.class).newInstance(x, y, w, h);
				
				try {
					o.entityID = getValue("id", entity);
				} catch(Exception e) {
					
				}
				
				try {
					if (o instanceof Elephant) {
						Elephant ee = (Elephant) o;
						ee.dropRate = Float.parseFloat(getValue("dropRate", entity));
					}
				} catch(Exception e) {
					
				}
				
				try {
					o.setHealth(Integer.parseInt(getValue("health", entity)));
				} catch(Exception e) {
					
				}
				
				try {
					o.script = new Script("scripts/" + getValue("script", entity));
				} catch (Exception e) {

				}
				try {
					o.texture = Resources.get(getValue("texture", entity));
				} catch (Exception e) {

				}

				this.entityHandler.add(o);

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

			// if (getValue("type", entity).equals("sprite")) {
			// Texture t = Resources.get(getValue("texture", entity));
			// int x = Integer.parseInt(getValue("x", entity));
			// int y = Integer.parseInt(getValue("y", entity));
			// int w = Integer.parseInt(getValue("width", entity));
			// int h = Integer.parseInt(getValue("height", entity));
			// Sprite e = new Sprite(t, x, y, w, h);
			// String scriptPath;
			// try {
			// if ((scriptPath = getValue("script", entity)) != null) {
			// e.script = new Script(scriptPath);
			// }
			// } catch (Exception ee) {
			//
			// }
			//
			// this.entities.add(e);
			// }
		}

	}
	
	/**
	 * Spawns a coin from a certain position.
	 * 
	 * @param position - place for new coin to spawn from.
	 */
	public void spawnCoin(Vector2 position) {
		
		float r = 20 * (float) Math.random();
		float w = 40 + r;
		float h = 40 + r;
		
		Vector2 velocity = new Vector2((float) (Math.random() - 0.5) * 2, 5 + (2 * (float) Math.random()));
		velocity.set(0, 0);
		entityHandler.add(new Coin(position.x, position.y - 50, w, h, velocity).setHealth(1));
		
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

	public void update(float delta) {
		entityHandler.update(delta);
	}
	
	public void render(SpriteBatch sb) {

		if (background != null) {
			float s = 1.8f;
			sb.draw(background, -background.getRegionWidth() / s / 2, -background.getRegionHeight() / s / 2, background.getRegionWidth() / s, background.getRegionHeight() / s);
		} else {
			System.err.println("Background is null!");
		}
		entityHandler.render(sb);
		
//		ShapeRenderer sr = ScreenManager.getSelected().sr;
//
//		sr.begin(ShapeType.Line);
//		{
//			sr.setColor(Color.RED);
//			for (Entity e : entityHandler.entities) {
//				sr.rect(e.position.x - (e.size.x * e.hitboxScale) / 2, e.position.y - (e.size.y * e.hitboxScale) / 2, e.size.x * e.hitboxScale, e.size.y * e.hitboxScale);
//			}
//		}
//		sr.end();
		
	}

}
