package com.pontus.core.graphics.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pontus.core.Game;
import com.pontus.core.Input;
import com.pontus.game.entities.Entity;

public class GUIHandler {

	public ArrayList<GUIElement> elements = new ArrayList<GUIElement>();

	public void add(Object object) {
		elements.add((GUIElement) object);
	}

	public GUIElement get(int index) {
		return elements.get(index);
	}

	public GUIElement get(String id) {
		for (GUIElement e : elements) {
			if (e != null) {
				if (e.id.equals(id)) {
					return e;
				}
			}
		}
		return null;
	}

	public void remove(GUIElement e) {
		elements.remove(e);
	}

	public boolean toggle = false;

	public void render(SpriteBatch sb) {

		Collections.sort(elements, new Comparator<GUIElement>() {

			@Override
			public int compare(GUIElement o1, GUIElement o2) {
				return Integer.compare((int) o1.z, (int) o2.z);
			}

		});

		// Collections.reverse(elements);

		for (int i = 0; i < elements.size(); i++) {
			if (i >= elements.size()) break;
			GUIElement e = get(i);
			if (e == null) continue;

			e.draw(sb);
			
			if (e instanceof Button) {
				Button b = (Button) e;
				if (Game.pause) {
					if (!b.workWhilePaused) {
						continue;
					}
				}
				Rectangle r = new Rectangle(e.x - (e.width / 2) * b.hitboxScale, e.y - (e.height / 2) * b.hitboxScale, e.width * b.hitboxScale, e.height * b.hitboxScale);
				if (r.contains(Input.getX(), Input.getY())) {
					if (b.enableHoverEvent) b.buttonInterface.onHover(b);
				} else {
					if (b.enableHoverEvent) b.buttonInterface.notHover(b);
				}
			}
		}

		if (Gdx.input.isTouched() && toggle) {
			toggle = false;
			for (int i = 0; i < elements.size(); i++) {
				if (i >= elements.size()) break;
				GUIElement e = get(i);
				if (e == null) continue;
				
				if (e instanceof Button) {
					Button b = (Button) e;
					if (Game.pause) {
						if (!b.workWhilePaused) {
							continue;
						}
					}
					Rectangle r = new Rectangle(e.x - (e.width / 2) * b.hitboxScale, e.y - (e.height / 2) * b.hitboxScale, e.width * b.hitboxScale, e.height * b.hitboxScale);
					if (r.contains(Input.getX(), Input.getY())) {
						b.buttonInterface.onClicked(b);
					} else {
					}
				}
			}
		} else if (!Gdx.input.isTouched() && !toggle) {
			toggle = true;
		}

	}

}
