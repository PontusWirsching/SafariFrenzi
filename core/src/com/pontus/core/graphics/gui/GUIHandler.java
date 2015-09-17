package com.pontus.core.graphics.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pontus.core.Input;

public class GUIHandler {

	public static ArrayList<GUIElement> elements = new ArrayList<GUIElement>();

	public static void add(GUIElement element) {
		elements.add(element);
	}

	public static GUIElement get(int index) {
		return elements.get(index);
	}

	public static GUIElement get(String id) {
		for (GUIElement e : elements) {
			if (e != null) {
				if (e.id.equals(id)) {
					return e;
				}
			}
		}
		return null;
	}
	
	public static void remove(GUIElement e) {
		elements.remove(e);
	}

	public static void render(SpriteBatch sb) {

		for (int i = 0; i < elements.size(); i++) {
			if (i >= elements.size())
				break;
			GUIElement e = get(i);
			if (e == null)
				continue;
			if (e instanceof Button) {
				Button b = (Button) e;
				Rectangle r = new Rectangle(e.x - e.width / 2, e.y - e.height / 2, e.width, e.height);
				if (r.contains(Input.getX(), Input.getY())) {
					b.buttonInterface.onHover(b);
					if (Gdx.input.isTouched() && b.toggle) {
						b.toggle = false;
						b.buttonInterface.onClicked(b);
					} else if (!Gdx.input.isTouched() && !b.toggle) {
						b.toggle = true;
					}
				} else {
					b.buttonInterface.notHover(b);
				}
			}

			e.draw(sb);

		}
	}

}
