package com.pontus.game.level;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LevelHandler {

	public static ArrayList<Level> levels = new ArrayList<Level>();
	
	public static int selected = 0;
	
	public static void add(Level level) {
		levels.add(level);
	}
	
	public static Level get(int index) {
		return levels.get(index);
	}
	
	public static Level getSelected() {
		return get(selected);
	}
	
	public static void update(float delta) {
		getSelected().update(delta);
	}
	
	public static void render(SpriteBatch sb) {
		getSelected().render(sb);
	}
	
}
