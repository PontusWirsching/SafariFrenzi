package com.pontus.core.resources;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Resources {

	public static void load() {

		// Backgrounds
		put("background:grasslands", "textures/backgrounds/Background_Grasslands.png");
		put("background:gameplay_sat", "textures/backgrounds/StretchedBackgroundContrasted.png");
		put("background:gameplay", "textures/backgrounds/gameplay_background.png");


		put("gui:particle:growth", "textures/gui/growthParticle.png");

		/** ----------------- Entities ------------ */
		put("tree", "textures/entities/static/TreeSheet.png");

	}

	public static HashMap<String, TextureRegion> textures = new HashMap<String, TextureRegion>();

	public static TextureRegion get(String key) {
		return textures.get(key);
	}

	
	
	
	
	
	
	
	public static void loadAnimation(String path) {
		try {
			
//			XmlReader reader = new XmlReader();
//			Element root = reader.parse(Gdx.files.internal(path));
//			
//			Array<Element> animations = root.getChildrenByName("Animation");
//			
//			for (Element a : animations) {
//				String name = a.get("name");
//				String p = a.get("path");
//				float fps = Float.parseFloat(a.get("fps"));
//				int frameWidth = Integer.parseInt(a.get("frameWidth"));
//				int frameHeight = Integer.parseInt(a.get("frameHeight"));
//			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void loadSheet(String path) {
		try {
			String image = path + ".png";
			String data = path + ".xml";

			Texture texture = new Texture(image);

			XmlReader reader = new XmlReader();
			Element root = reader.parse(Gdx.files.internal(data));
			
			Array<Element> items = root.getChildrenByName("Sprite");
			
			for (Element e : items) {
				String name = e.get("name");
				int x = Integer.parseInt(e.get("x"));
				int y = Integer.parseInt(e.get("y"));
				int width = Integer.parseInt(e.get("width"));
				int height = Integer.parseInt(e.get("height"));
				put(name, new TextureRegion(texture, x, y, width, height));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void put(String key, TextureRegion texture) {
		textures.put(key, texture);
	}

	public static void put(String key, String path) {
		textures.put(key, new TextureRegion(new Texture(path)));
	}
}
