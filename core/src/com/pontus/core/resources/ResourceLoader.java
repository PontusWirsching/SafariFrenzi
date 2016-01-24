package com.pontus.core.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;


/**
 * @deprecated
 * @author Pontus Wirsching
 */
public class ResourceLoader {

	
	
	/**
	 * Loads a JSON file with resources.<br>
	 * This is made so modding is easier.
	 */
	public static void load(String path) {
		JsonReader r = new JsonReader();
		
		JsonValue v = r.parse(Gdx.files.internal(path));
		
		for (int i = 0; i < v.size; i++) {
			JsonValue jv = v.get(i);
			if (jv != null) {
					Resources.textures.put(jv.getString("name"), new TextureRegion(new Texture(Gdx.files.internal(jv.getString("path")))));
			}
		}
	}
	
	
	
	
}
