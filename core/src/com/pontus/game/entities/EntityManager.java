package com.pontus.game.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pontus.core.SafariFrenzi;
import com.pontus.core.graphics.RenderTypes;

public class EntityManager {

	public ArrayList<Entity> entities = new ArrayList<Entity>();

	public EntityManager() {

	}

	public ArrayList<Entity> getMultipleByID(String id) {
		ArrayList<Entity> e = new ArrayList<Entity>();

		for (Entity entity : entities) {
			if (entity == null) continue;
			if (entity.entityID.equals(id)) {
				e.add(entity);
			}
		}

		return e;
	}

	public void remove(Entity e) {
		entities.remove(e);
	}

	public void add(Entity e) {
		entities.add(e);
	}

	public Entity get(int index) {
		return entities.get(index);
	}

	/**
	 * Returns an entity by its id.
	 * 
	 * @param entitiyID
	 * @return
	 */
	public Entity getByID(String entitiyID) {
		for (Entity e : entities) {
			if (e == null) continue;
			if (e.entityID.equals(entitiyID)) {
				return e;
			}
		}
		return null;
	}

	public Entity getByY(float y) {
		for (Entity e : entities) {
			if (e == null) continue;
			if (e.position.y == y) {
				return e;
			}
		}
		return null;
	}

	public void update(float delta) {
		for (int i = 0; i < entities.size(); i++) {
			if (i >= entities.size()) break;
			Entity e = entities.get(i);
			if (e != null) {
				e.update(delta);
			}
		}
	}

	public ArrayList<Entity> render = new ArrayList<Entity>();

	public void render(SpriteBatch sb) {
		if (SafariFrenzi.RENDER_TYPE == RenderTypes.DEFAULT) {
			for (int i = 0; i < entities.size(); i++) {
				if (i >= entities.size()) break;
				Entity e = entities.get(i);
				if (e != null) {
					e.draw(sb);
				}
			}
		}
		if (SafariFrenzi.RENDER_TYPE == RenderTypes.LAYERED) {

			Collections.sort(entities, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					return Integer.compare((int) (e1.position.y - e1.size.y / 2), (int) (e2.position.y - e2.size.y / 2));
				}

			});

			Collections.reverse(entities);

			for (int i = 0; i < entities.size(); i++) {
				if (i >= entities.size()) break;
				Entity e = entities.get(i);
				if (e == null) continue;
				e.draw(sb);
			}

		}

	}
}
