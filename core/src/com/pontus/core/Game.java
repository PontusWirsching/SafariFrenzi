package com.pontus.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.pontus.core.graphics.gui.Button;
import com.pontus.core.graphics.gui.ButtonInterface;
import com.pontus.core.graphics.gui.CustomRenderer;
import com.pontus.core.graphics.gui.GUIElement;
import com.pontus.core.graphics.gui.GUIHandler;
import com.pontus.core.graphics.gui.Sprite;
import com.pontus.core.graphics.gui.sprites.Fruit;
import com.pontus.core.graphics.gui.sprites.StarBar;
import com.pontus.core.graphics.gui.sprites.Topbar;
import com.pontus.core.graphics.screen.GameScreen;
import com.pontus.core.resources.Resources;
import com.pontus.game.entities.Entity;
import com.pontus.game.entities.SpawnerManager;
import com.pontus.game.entities.mobs.Elephant;
import com.pontus.game.entities.mobs.friends.RangerRon;
import com.pontus.game.entities.mobs.friends.Rhino;
import com.pontus.game.level.Level;
import com.pontus.game.level.LevelHandler;

public class Game extends GameScreen {

	public static boolean DEBUG_ENTITY = false;
	public static boolean DEBUG_GUI = false;

	public static int vib = 100;
	
	public static BitmapFont font;

	public static float topBarPosition;

	public static boolean pause = false;

	/**
	 * The drop rate will be multiplied by this value.
	 */
	public static float dropRateMultiplier = 1.0f;

	/**
	 * How much damage each tap does.
	 */
	public static float tapDamage = 1.0f;

	/**
	 * The fruit quality multiplier.
	 */
	public static float fruitQualityMultiplier = 1.0f;

	/**
	 * Increases the max hunger.
	 */
	public static float elephantSaturation = 0.1f;

	public static float elephantHyde = 0.1f;

	public static float elephantStamina = 1.0f;

	public static GUIHandler gui;

	public static float getTextWidth(String s) {
		return font.getBounds(s).width;
	}

	public static float getTextHeight(String s) {
		return font.getBounds(s).height;
	}

	/**
	 * Add all GUI elements here!
	 */
	public Game(String name) {
		super(name);

		font = new BitmapFont();

		gui = new GUIHandler();
		//
		// FriendManager.add(FriendType.RANGER_RON);
		// FriendManager.add(FriendType.MONKEY);
		// FriendManager.add(FriendType.RHINO);

		topBarPosition = camera.viewportHeight / 2 - Resources.get("gui:element:top_bar").getRegionHeight() / 1.6f;

		LevelHandler.add(new Level("levels/test.json"));

		gui.add(new Button("pause", 500, -250, 64, 64, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onClicked(Button b) {
				pause = !pause;
				Gdx.input.vibrate(vib);
			}

			@Override
			public void notHover(Button b) {
				// TODO Auto-generated method stub

			}
		}).setHitboxScale(1).setValue(-100).setWorkWhilePaused(true).setCustomRenderer(new CustomRenderer() {

			boolean t = false;
			@SuppressWarnings("unused")
			float startDistance = 0;

			@Override
			public void customDraw(SpriteBatch sb, Sprite sprite) {

				if (pause) {
					for (int i = 0; i < 3; i++) {
						float time = 0.5f;
						float distance = (float) Util.getDistance(sprite.x, sprite.y, 0, 0);
						float speed = distance / time;
						if (!t) {
							t = true;
							startDistance = distance;
						}

						// sprite.width = 64 + 10 * (startDistance / (distance +
						// 15));
						// sprite.height = 64 + 10 * (startDistance / (distance
						// +
						// 15));

						double angle = Util.getAngle(sprite.x, sprite.y, 0, 0);

						sprite.y += Math.cos(angle) * speed * Gdx.graphics.getDeltaTime();
						if (sprite.y >= 0)
							sprite.y = 0;
						sprite.x += Math.sin(angle) * speed * Gdx.graphics.getDeltaTime();
						if (sprite.x <= 0)
							sprite.x = 0;
						if (distance < 2.5) {
							sprite.x = 0;
							sprite.y = 0;
						}
					}
				} else {
					t = false;
					for (int i = 0; i < 4; i++) {
						float time = 0.5f;
						float distance = (float) Util.getDistance(sprite.x, sprite.y, camera.viewportWidth / 2f - 50, -250);

						float speed = (distance * 1) / time;
						if (speed <= 1)
							speed = 0;

						double angle = Util.getAngle(sprite.x, sprite.y, camera.viewportWidth / 2f - 50, -250);

						sprite.y += Math.cos(angle) * speed * Gdx.graphics.getDeltaTime();
						// if (sprite.y >= 0) sprite.y = 0;
						sprite.x += Math.sin(angle) * speed * Gdx.graphics.getDeltaTime();
						// if (sprite.x >= camera.viewportWidth / 2f - 50)
						// sprite.x
						// = camera.viewportWidth / 2f - 50;

						// GUIHandler.get("pause").x = camera.viewportWidth / 2f
						// -
						// 50;
					}
				}

				float distance = (float) Util.getDistance(sprite.x, sprite.y, camera.viewportWidth / 2f - 50, -250);
				sprite.width = 64 + distance / 2;
				sprite.height = 64 + distance / 2;
				if (distance > 100) {
					sprite.setTexture(Resources.get("gui:element:play"));
				} else {
					sprite.setTexture(Resources.get("gui:element:pause"));
				}

			}
		}).setTexture(Resources.get("gui:element:pause")).setDepth(2));

		gui.add(new Topbar("top_bar", 0, topBarPosition, 50, Resources.get("gui:element:top_bar").getRegionHeight() / 1.2f).setTexture(Resources.get("gui:element:top_bar")).setDepth(-1));

		gui.add(new Button("chest", camera.viewportWidth / 1.8f, topBarPosition, 100, 100, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				if (SafariFrenzi.DESKTOP)
					b.texture = Resources.get("gui:chest:open");
			}

			@Override
			public void notHover(Button b) {

			}

			@Override
			public void onClicked(Button b) {

			}

		}).setValue(0).setTexture(Resources.get("gui:chest:closed")).setCustomRenderer(new CustomRenderer() {

			TextureRegion priceBox = Resources.get("gui:element:price_box");

			@Override
			public void customDraw(SpriteBatch sb, Sprite sprite) {
				sb.draw(priceBox, sprite.x - 75 / 2, sprite.y - sprite.height / 1.8f, 75, 30);
				font.setScale(1.2f);
				font.setColor(Color.BLACK);
				String s = String.valueOf(Money.chest);
				font.draw(sb, s, sprite.x - getTextWidth(s) / 2, sprite.y - sprite.height / 3f);
			}

		}));

		gui.add(new StarBar("starbar", 0, topBarPosition - 4, 521 / 2, 71 / 2).setTexture(Resources.get("gui:starbar:empty")));

		final Button fence = (Button) new Button("fence", 100, topBarPosition - 4, 30, 30, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				if (SafariFrenzi.DESKTOP) {
					b.width = b.originalWidth * 1.3f;
					b.height = b.originalHeight * 1.3f;
				}
			}

			@Override
			public void onClicked(Button b) {
				if (b.getValue() <= Money.chest) {
					if (LevelHandler.getSelected().fence >= 3) {
						return;
					}

					Money.chest -= b.getValue();
					LevelHandler.getSelected().fence++;
					b.setValue(LevelHandler.getSelected().getFencePrice());
				}
				Gdx.input.vibrate(vib);

			}

			@Override
			public void notHover(Button b) {
				b.width = b.originalWidth;
				b.height = b.originalHeight;
			}
		}).setHitboxScale(2.0f).setValue(LevelHandler.getSelected().getFencePrice()).setTexture(Resources.get("gui:icon:fence_purchase"));

		fence.setCustomRenderer(new CustomRenderer() {

			@Override
			public void customDraw(SpriteBatch sb, Sprite sprite) {
				sb.draw(fence.priceBox, sprite.x - 25, Game.topBarPosition - 40, 50, 20);
				String v = Integer.toString(fence.getValueAsInt());
				Game.font.setScale(0.95f);
				Game.font.setColor(Color.BLACK);
				Game.font.draw(sb, v, sprite.x - getTextWidth(v) / 2, Game.topBarPosition - 23.5f);

				sb.draw(fence.priceBox, sprite.x - 25, Game.topBarPosition + 10, 50, 20);
				String v2 = Integer.toString(fence.getValueAsInt());
				Game.font.draw(sb, v2, sprite.x - getTextWidth(v2) / 2, Game.topBarPosition + 26);

			}
		});

		gui.add(fence);
		gui.add(new Button("fruit_quality", 100, topBarPosition - 4, 35, 35, new ButtonInterface() {
			@Override
			public void onHover(Button b) {
				if (SafariFrenzi.DESKTOP) {
					b.width = b.originalWidth * 1.2f;
					b.height = b.originalHeight * 1.2f;
				}
			}

			@Override
			public void onClicked(Button b) {
				if (b.getValue() <= Money.chest) {
					Money.chest -= b.getValue();
					b.increaseValue(b.getValue());
					fruitQualityMultiplier *= 1.1f;
					elephantSaturation *= 1.025f;
					// System.out.println((0.10f * fruitQualityMultiplier) +
					// ", " + elephantSaturation);
				}
				Gdx.input.vibrate(vib);

			}

			@Override
			public void notHover(Button b) {
				b.width = b.originalWidth;
				b.height = b.originalHeight;
			}
		}).setHitboxScale(2.0f).setTexture(Resources.get("gui:icon:fruit_quality")).setValue(25.0f));

//		gui.add(new Button("tap_power", 100, topBarPosition - 4, 35, 35, new ButtonInterface() {
//			@Override
//			public void onHover(Button b) {
//				if (SafariFrenzi.DESKTOP) {
//					b.width = b.originalWidth * 1.2f;
//					b.height = b.originalHeight * 1.2f;
//				}
//			}
//
//			@Override
//			public void onClicked(Button b) {
//				if (b.getValue() <= Money.chest) {
//					Money.chest -= b.getValue();
//					b.increaseValue(b.getValue());
//
//					// Increase tap damage by 10%
//					tapDamage *= 1.10;
//				}
//				Gdx.input.vibrate(vib);
//
//			}
//
//			@Override
//			public void notHover(Button b) {
//				b.width = b.originalWidth;
//				b.height = b.originalHeight;
//			}
//
//		}).setTexture(Resources.get("gui:icon:increase_tap")).setValue(100.0f));

		gui.add(new Button("hyde_thickness", 100, topBarPosition - 4, 35, 35, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				if (SafariFrenzi.DESKTOP) {
					b.width = b.originalWidth * 1.2f;
					b.height = b.originalHeight * 1.2f;
				}
			}

			@Override
			public void onClicked(Button b) {
				if (b.getValue() <= Money.chest) {
					Money.chest -= b.getValue();
					b.increaseValue(b.getValue());
					elephantHyde *= 1.05f;
					// System.out.println((1 - elephantHyde));
				}
				Gdx.input.vibrate(vib);

			}

			@Override
			public void notHover(Button b) {
				b.width = b.originalWidth;
				b.height = b.originalHeight;
			}
		}).setHitboxScale(2.0f).setTexture(Resources.get("gui:icon:hyde_thickness")).setValue(25.0f));

		gui.add(new Button("stamina", 100, topBarPosition - 4, 42, 35, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				if (SafariFrenzi.DESKTOP) {
					b.width = b.originalWidth * 1.2f;
					b.height = b.originalHeight * 1.2f;
				}
			}

			@Override
			public void notHover(Button b) {
				b.width = b.originalWidth;
				b.height = b.originalHeight;
			}

			@Override
			public void onClicked(Button b) {

				if (elephantStamina * 0.90 <= 0.25f) {
					return;
				}

				if (b.getValue() <= Money.chest) {
					Money.chest -= b.getValue();
					b.increaseValue(b.getValue());
					elephantStamina *= 0.90;

				}
				Gdx.input.vibrate(vib);

			}

		}).setHitboxScale(2.0f).setTexture(Resources.get("gui:icon:stamina_increase")).setValue(25.0f));

		gui.add(new Button("money_droprate", 100, topBarPosition - 4, 35, 35, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				if (SafariFrenzi.DESKTOP) {
					b.width = b.originalWidth * 1.2f;
					b.height = b.originalHeight * 1.2f;
				}
			}

			@Override
			public void onClicked(Button b) {
				if (b.getValue() <= Money.chest) {
					Money.chest -= b.getValue();
					b.increaseValue(b.getValue());
					dropRateMultiplier *= 1.2f;
				}
				Gdx.input.vibrate(vib);

			}

			@Override
			public void notHover(Button b) {
				b.width = b.originalWidth;
				b.height = b.originalHeight;
			}

		}).setHitboxScale(2.0f).setTexture(Resources.get("gui:icon:money_droprate")).setValue(20.0f));

		gui.add(new Button("elephant_purcahse", 0, topBarPosition - 4, 50, 50, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				if (SafariFrenzi.DESKTOP) {
					b.width = b.originalWidth * 1.2f;
					b.height = b.originalHeight * 1.2f;
				}
			}

			@Override
			public void onClicked(Button b) {
				if (b.getValue() <= Money.chest) {
					Money.chest -= b.getValue();
					LevelHandler.getSelected().spawnElephant();
				}
				Gdx.input.vibrate(vib);

			}

			@Override
			public void notHover(Button b) {
				b.width = b.originalWidth;
				b.height = b.originalHeight;
			}
		}).setTexture(Resources.get("gui:icon:elephantPurchase")));

		gui.add(new Button("friends_upgrade", 0, topBarPosition - 4, 50, 50, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				if (SafariFrenzi.DESKTOP) {
					b.width = b.originalWidth * 1.2f;
					b.height = b.originalHeight * 1.2f;
				}
			}

			@Override
			public void onClicked(Button b) {
				if (b.getValue() <= Money.chest) {
					Money.chest -= b.getValue();
					LevelHandler.getSelected().friendsUpgradeLevel++;
				}
				Gdx.input.vibrate(vib);
			}

			@Override
			public void notHover(Button b) {
				b.width = b.originalWidth;
				b.height = b.originalHeight;
			}
		}).setTexture(Resources.get("gui:icon:friendsUpgrade")));

	}

	boolean limitTouch = false;

	@Override
	public void render(float delta) {

		super.render(delta);

		// Do gui resize here:
		gui.get("top_bar").width = camera.viewportWidth;
		gui.get("chest").x = camera.viewportWidth / 2f - gui.get("chest").width / 2;
		gui.get("starbar").x = -camera.viewportWidth / 2 + gui.get("starbar").width / 2;
		gui.get("fence").x = camera.viewportWidth / 2f - gui.get("fence").originalWidth * -0.1f - gui.get("starbar").originalWidth / 2;

		Button spawnElephantBtn = (Button) gui.get("elephant_purcahse");
		spawnElephantBtn.x = -camera.viewportWidth / 2 + gui.get("starbar").width + spawnElephantBtn.originalWidth;

		Button friendsUpgradeBtn = (Button) gui.get("friends_upgrade");
		friendsUpgradeBtn.x = -camera.viewportWidth / 2 + gui.get("starbar").width + spawnElephantBtn.originalWidth + friendsUpgradeBtn.originalWidth + 30;

		spawnElephantBtn.setValue(250 * LevelHandler.getSelected().entityHandler.getMultipleByID("spawnedElephant").size());
		friendsUpgradeBtn.setValue(500 + 200 * (int) Math.pow(LevelHandler.getSelected().friendsUpgradeLevel, 2));

		GUIElement[] buttons = new GUIElement[5];
		buttons[0] = gui.get("fence");
		buttons[1] = gui.get("fruit_quality");
		buttons[2] = gui.get("hyde_thickness");
		buttons[3] = gui.get("stamina");
		buttons[4] = gui.get("money_droprate");

		// GUIElement p = GUIHandler.get("pause");
		//
		// System.out.println("First: " + GUIHandler.elements.get(0).id);
		// System.out.println("Last: " +
		// GUIHandler.elements.get(GUIHandler.elements.size() - 1).id);
		//
		// if (GUIHandler.elements.indexOf(p) != GUIHandler.elements.size() - 1)
		// {
		// Collections.swap(GUIHandler.elements, GUIHandler.elements.indexOf(p),
		// GUIHandler.elements.size() - 1);
		// }

		// GUIHandler.elements.set(0,
		// GUIHandler.elements.get(GUIHandler.elements.size() - 1));
		// GUIHandler.elements.set(GUIHandler.elements.size() - 1, p);

		for (int i = 1; i < buttons.length; i++) {
			GUIElement button = buttons[i];
			button.x = -i * (80) + buttons[0].x;
		}

		gui.get("chest").texture = Resources.get("gui:chest:closed");
		for (int i = 0; i < gui.elements.size(); i++) {
			GUIElement e = gui.get(i);
			if (e.id == "VALUEABLE") {
				double distance = Util.getDistance(e.x, e.y, gui.get("chest").x, gui.get("chest").y);
				if (distance < 100) {
					gui.get("chest").texture = Resources.get("gui:chest:open");
					break;
				}
			}
		}

		if (!pause)
			LevelHandler.update(delta);
		if (!pause)
			SpawnerManager.update();

		if (!pause)
			if (Input.lastTouchWas(200)) {
				Gdx.input.vibrate(100);
				gui.add(new Fruit("dropped_fruit", Input.x, Input.y, Input.y - 100));
			}

		if (!pause)
			if (Gdx.input.isTouched() && !limitTouch) {

				// Check if any entity is touched if so, call entity.touched();
				for (Entity e : LevelHandler.getSelected().entityHandler.entities) {
					if (new Rectangle(e.position.x - (e.size.x * e.hitboxScale) / 2, e.position.y - (e.size.y * e.hitboxScale) / 2, e.size.x * e.hitboxScale, e.size.y * e.hitboxScale).contains(Input.x, Input.y)) {
						e.touched();
					}
				}

				limitTouch = true;
			} else if (!Gdx.input.isTouched() && limitTouch) {
				limitTouch = false;
			}

		sb.begin();
		{
			LevelHandler.render(sb);

			gui.render(sb);

		}
		sb.end();

		if (DEBUG_GUI) {
			sr.begin(ShapeType.Line);
			{
				Gdx.gl20.glLineWidth(2);
				for (GUIElement g : gui.elements) {
					if (g == null)
						continue;

					if (g instanceof Button) {
						Button b = (Button) g;
						sr.setColor(Color.GREEN);
						sr.rect(b.x - b.width / 2 * b.hitboxScale, b.y - b.height / 2 * b.hitboxScale, b.width * b.hitboxScale, b.height * b.hitboxScale);
					} else {
						sr.setColor(Color.BLUE);
						sr.rect(g.x - g.width / 2, g.y - g.height / 2, g.width, g.height);
					}

				}
			}
			sr.end();
		}


		if (DEBUG_ENTITY) {
			sr.begin(ShapeType.Filled);
			{
				sb.begin();
				sr.setColor(Color.RED);
				font.setColor(Color.RED);
				for (Entity e : LevelHandler.getSelected().entityHandler.entities) {
					sr.rect(e.position.x - (e.size.x * e.hitboxScale) / 2, e.position.y - (e.size.y * e.hitboxScale) / 2, e.size.x * e.hitboxScale, e.size.y * e.hitboxScale);
					font.draw(sb, "HP: " + e.health + " / " + e.maxHealth, e.position.x, e.position.y);
					if (e instanceof Elephant) {
						Elephant ee = (Elephant) e;
						font.draw(sb, "DropRate: " + ee.dropRate, e.position.x, e.position.y - 12);
						font.draw(sb, "GrowRate: " + ee.growRate, e.position.x, e.position.y - 24);
						font.draw(sb, "Growth: " + ee.growth, e.position.x, e.position.y - 36);
						font.draw(sb, "GrowThreashold: " + ee.growThreashold, e.position.x, e.position.y - 48);
						font.draw(sb, "GrowTimer: " + ee.growthTimer, e.position.x, e.position.y - 60);
						font.draw(sb, "Hunger: " + ee.hunger, e.position.x, e.position.y - 72);
					}
					if (e instanceof Rhino) {
						Rhino r = (Rhino) e;
						font.draw(sb, "Attack Damage: " + r.attackDamage, e.position.x, e.position.y - 12);
					}
					if (e instanceof RangerRon) {
						RangerRon r = (RangerRon) e;
						font.draw(sb, "Droprate: " + r.dropRate, e.position.x, e.position.y - 12);
					}
				}
				sb.end();
			}
			sr.end();
		}
		
		

		
	}

}
