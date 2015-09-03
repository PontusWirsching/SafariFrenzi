package com.pontus.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.pontus.core.graphics.RenderTypes;
import com.pontus.core.graphics.screen.GameScreen;
import com.pontus.core.graphics.screen.ScreenManager;
import com.pontus.core.resources.Resources;

/**
 * Main application file.
 * 
 * @author Pontus Wirsching
 */
public class SafariFrenzi implements ApplicationListener {
	
	public static int RENDER_TYPE = RenderTypes.LAYERED;
	
	public GameScreen game;
	
	@Override
	public void create () {
		Resources.load();
		
		
		
		Resources.loadSheet("textures/coins");
		Resources.loadSheet("textures/elephant");
		Resources.loadSheet("textures/lion");
		Resources.loadSheet("textures/gui");
		Resources.loadSheet("textures/ranger_ron");


		
		game = new Game("GAME");
		ScreenManager.add(game);
		

		
		
		ScreenManager.setSelected("GAME");
		
		Gdx.input.setInputProcessor(new Input());

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		ScreenManager.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		ScreenManager.resize(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}
}
