package com.pontus.core.graphics.screen;

import java.util.ArrayList;

public class ScreenManager {

	public static ArrayList<GameScreen> screens = new ArrayList<GameScreen>();
	
	public static int selected = 0;
	
	public static void add(GameScreen screen) {
		screens.add(screen);
	}
	
	public static void setSelected(String name) {
		for (GameScreen s : screens) {
			if (name.equals(s.name)) {
				selected = screens.indexOf(s);
			}
		}
	}
	
	public static void resize(int width, int height) {
		getSelected().resize(width, height);
	}
	
	public static GameScreen get(int index) {
		return screens.get(index);
	}
	
	public static GameScreen getSelected() {
		return get(selected);
	}
	
	public static void render(float delta) {
		getSelected().render(delta);
	}
	
}
