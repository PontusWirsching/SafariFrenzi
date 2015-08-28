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

		// GUI
//		put("gui:topbar", "textures/gui/GUI_Over.png");
//
//		put("gui:fruit:noeffect", "textures/gui/fruit/fruit_noeffect.png");
//		put("gui:fruit:effect", "textures/gui/fruit/fruit_effect.png");
//
//		put("gui:chest:open", "textures/gui/chest/Money Box Openned_Updated-01.png");
//		put("gui:chest:closed", "textures/gui/chest/Money Box_Closed-01.png");

		put("gui:particle:growth", "textures/gui/growthParticle.png");

		/** ----------------- Entities ------------ */

		// Elephant
		{
//			put("elephant:walk_01", "textures/entities/mobs/elephant/walk/Elephantedits-01.png");
//			put("elephant:walk_02", "textures/entities/mobs/elephant/walk/Elephantedits-02.png");
//			put("elephant:walk_03", "textures/entities/mobs/elephant/walk/Elephantedits-03.png");
//			put("elephant:walk_04", "textures/entities/mobs/elephant/walk/Elephantedits-04.png");
		}

		// Lion
		{
//			put("lion:walk_01", "textures/entities/mobs/lion/walk/lionedit-01.png");
//			put("lion:walk_02", "textures/entities/mobs/lion/walk/lionedit-02.png");
//			put("lion:walk_03", "textures/entities/mobs/lion/walk/lionedit-03.png");
//			put("lion:walk_04", "textures/entities/mobs/lion/walk/lionedit-04.png");
		}

		// Gold Coin
		{
			// Normal
			{
//				put("coin:gold:1", "textures/entities/random/coin/gold/Gold Coin_Frame 1-04.png");
//				put("coin:gold:2", "textures/entities/random/coin/gold/Gold Coin_Frame 2-04.png");
//				put("coin:gold:3", "textures/entities/random/coin/gold/Gold Coin_Frame 5-06.png");
//				put("coin:gold:4", "textures/entities/random/coin/gold/Gold Coin_Frame 3-04.png");
//				put("coin:gold:5", "textures/entities/random/coin/gold/Gold Coin_Frame 6-06.png");
//				put("coin:gold:6", "textures/entities/random/coin/gold/Gold Coin_Frame 4-04.png");
			}

			// Shadow
			{
//				put("coin:gold:1_shadow", "textures/entities/random/coin/gold/With Shadows/Gold Coin_Frame 1-04.png");
//				put("coin:gold:2_shadow", "textures/entities/random/coin/gold/With Shadows/Gold Coin_Frame 2-04.png");
//				put("coin:gold:3_shadow", "textures/entities/random/coin/gold/With Shadows/Gold Coin_Frame 5-04.png");
//				put("coin:gold:4_shadow", "textures/entities/random/coin/gold/With Shadows/Gold Coin_Frame 3-04.png");
//				put("coin:gold:5_shadow", "textures/entities/random/coin/gold/With Shadows/Gold Coin_Frame 6-04.png");
//				put("coin:gold:6_shadow", "textures/entities/random/coin/gold/With Shadows/Gold Coin_Frame 4-04.png");
			}
		}

		// Tree
		put("tree", "textures/entities/static/TreeSheet.png");

	}

	public static HashMap<String, TextureRegion> textures = new HashMap<String, TextureRegion>();

	public static TextureRegion get(String key) {
		return textures.get(key);
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
