package com.pontus.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.pontus.core.graphics.screen.ScreenManager;

/**
 * Offsets the Gdx.input.getX(), to fit the camera.
 * 
 * @author Pontus Wirsching
 *
 */
public class Input implements InputProcessor {

	static float x, y;

	public static void update() {

		Vector3 input = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		ScreenManager.getSelected().camera.unproject(input);

		x = input.x;
		y = input.y;

	}

	public static float getX() {
		return x;
	}

	public static float getY() {
		return y;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	static long touchTime = 0;
	long start = 0;
	long end = 0;

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchTime = 0;
		start = System.currentTimeMillis();
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		end = System.currentTimeMillis();
		touchTime = end - start;
		return false;
	}

	public static boolean lastTouchWas(long ms) {
		if (touchTime > ms) {
			touchTime = 0;
			return true;
		} else
			return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
