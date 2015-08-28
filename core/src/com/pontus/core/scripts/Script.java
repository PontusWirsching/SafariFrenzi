package com.pontus.core.scripts;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Script {

	public String path;
	
	public ScriptEngine engine;
	
	public Invocable inv;
	
	public Script(String path) {
		this.path = path;
		engine = new ScriptEngineManager().getEngineByName("JavaScript");

		try {
			engine.eval(Gdx.files.internal(path).reader());
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
		
		inv = (Invocable) engine;
		
		
	}

	public void update(float delta, Vector2 pos, Vector2 vel, Vector2 size) {
		try {
			inv.invokeFunction("update", delta, pos, vel, size);
		} catch (NoSuchMethodException e) {
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
}
