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
import com.pontus.core.graphics.gui.Icon;
import com.pontus.core.graphics.gui.Sprite;
import com.pontus.core.graphics.gui.sprites.Fruit;
import com.pontus.core.graphics.gui.sprites.StarBar;
import com.pontus.core.graphics.gui.sprites.Topbar;
import com.pontus.core.graphics.screen.GameScreen;
import com.pontus.core.resources.Resources;
import com.pontus.game.entities.Entity;
import com.pontus.game.entities.SpawnerManager;
import com.pontus.game.entities.mobs.friends.FriendManager;
import com.pontus.game.entities.mobs.friends.FriendType;
import com.pontus.game.level.Level;
import com.pontus.game.level.LevelHandler;

public class Game extends GameScreen {

	public static boolean DEBUG_ENTITY = false;

	public static BitmapFont font;

	public static float topBarPosition;

	/**
	 * The drop rate will be multiplied by this value.
	 */
	public static float dropRateMultiplier = 1.0f;

	/**
	 * Add all GUI elements here!
	 */
	public Game(String name) {
		super(name);

		font = new BitmapFont();

		FriendManager.add(FriendType.RANGER_RON);
		FriendManager.add(FriendType.MONKEY);
		FriendManager.add(FriendType.RHINO);

		topBarPosition = camera.viewportHeight / 2 - Resources.get("gui:element:top_bar").getRegionHeight() / 1.6f;

		LevelHandler.add(new Level("levels/test.json"));

		GUIHandler.add(new Topbar("top_bar", 0, topBarPosition, 50, Resources.get("gui:element:top_bar").getRegionHeight() / 1.2f).setTexture(Resources.get("gui:element:top_bar")));

		GUIHandler.add(new Button("chest", camera.viewportWidth / 1.8f, topBarPosition, 100, 100, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				b.texture = Resources.get("gui:chest:open");
			}

			@Override
			public void notHover(Button b) {

			}

			@Override
			public void onClicked(Button b) {

			}

		}).setTexture(Resources.get("gui:chest:closed")).setCustomRenderer(new CustomRenderer() {

			TextureRegion priceBox = Resources.get("gui:element:price_box");

			@Override
			public void customDraw(SpriteBatch sb, Sprite sprite) {
				sb.draw(priceBox, sprite.x - 75 / 2, sprite.y - sprite.height / 1.8f, 75, 30);
				font.setScale(1.2f);
				font.setColor(Color.BLACK);
				String s = String.valueOf(Money.chest);
				font.draw(sb, s, sprite.x - font.getBounds(s).width / 2, sprite.y - sprite.height / 3f);
			}

		}));

		GUIHandler.add(new StarBar("starbar", 0, topBarPosition - 4, 521 / 2, 71 / 2).setTexture(Resources.get("gui:starbar:empty")));

		GUIHandler.add(new Icon("fence", 100, topBarPosition - 4, 30, 30).setValue(100).setTexture(Resources.get("gui:icon:fence_purchase")));
		GUIHandler.add(new Icon("fruit_quality", 100, topBarPosition - 4, 35, 35).setTexture(Resources.get("gui:icon:fruit_quality")));
		GUIHandler.add(new Icon("fruit_droprate", 100, topBarPosition - 4, 35, 35).setTexture(Resources.get("gui:icon:fruit_droprate")));
		GUIHandler.add(new Icon("hyde_thickness", 100, topBarPosition - 4, 35, 35).setTexture(Resources.get("gui:icon:hyde_thickness")));
		GUIHandler.add(new Icon("stamina", 100, topBarPosition - 4, 35, 35).setTexture(Resources.get("gui:icon:stamina_increase")));
		GUIHandler.add(new Button("money_droprate", 100, topBarPosition - 4, 35, 35, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				b.width = b.originalWidth * 1.2f;
				b.height = b.originalHeight * 1.2f;
			}

			@Override
			public void onClicked(Button b) {
				if (b.getValue() <= Money.chest) {
					Money.chest -= b.getValue();
					b.increaseValue(b.getValue());
					dropRateMultiplier *= 1.2f;
				}
			}

			@Override
			public void notHover(Button b) {
				b.width = b.originalWidth;
				b.height = b.originalHeight;
			}

		}).setTexture(Resources.get("gui:icon:money_droprate")));

	}

	boolean limitTouch = false;

	@Override
	public void render(float delta) {
		super.render(delta);

		// Do gui resize here:
		GUIHandler.get("top_bar").width = camera.viewportWidth;
		GUIHandler.get("chest").x = camera.viewportWidth / 2f - GUIHandler.get("chest").width / 2;
		GUIHandler.get("starbar").x = -camera.viewportWidth / 2 + GUIHandler.get("starbar").width / 2;
		GUIHandler.get("fence").x = camera.viewportWidth / 2f - GUIHandler.get("fence").width * -0.1f - GUIHandler.get("starbar").width / 2;

		GUIElement[] buttons = new GUIElement[6];
		buttons[0] = GUIHandler.get("fence");
		buttons[1] = GUIHandler.get("fruit_quality");
		buttons[2] = GUIHandler.get("fruit_droprate");
		buttons[3] = GUIHandler.get("hyde_thickness");
		buttons[4] = GUIHandler.get("stamina");
		buttons[5] = GUIHandler.get("money_droprate");

		for (int i = 1; i < buttons.length; i++) {
			GUIElement button = buttons[i];
			button.x = -i * (35 + 25) + buttons[0].x;
		}

		GUIHandler.get("chest").texture = Resources.get("gui:chest:closed");
		for (int i = 0; i < GUIHandler.elements.size(); i++) {
			GUIElement e = GUIHandler.get(i);
			if (e.id == "VALUEABLE") {
				double distance = Util.getDistance(e.x, e.y, GUIHandler.get("chest").x, GUIHandler.get("chest").y);
				if (distance < 100) {
					GUIHandler.get("chest").texture = Resources.get("gui:chest:open");
					break;
				}
			}
		}

		LevelHandler.update(delta);
		SpawnerManager.update();

		if (Input.lastTouchWas(250)) {
			Gdx.input.vibrate(100);
			GUIHandler.add(new Fruit("dropped_fruit", Input.x, Input.y, Input.y - 100));
		}

		// boolean touchedAnEntity = false;
		if (Gdx.input.isTouched() && !limitTouch) {

			// Check if any entity is touched if so, call entity.touched();
			for (Entity e : LevelHandler.getSelected().entityHandler.entities) {
				if (new Rectangle(e.position.x - (e.size.x * e.hitboxScale) / 2, e.position.y - (e.size.y * e.hitboxScale) / 2, e.size.x * e.hitboxScale, e.size.y * e.hitboxScale).contains(Input.x, Input.y)) {
					// touchedAnEntity = true;
					e.touched();
				}
			}

			// // If no entity is touched, drop a fruit.
			// if (!touchedAnEntity) {
			//
			// // GUIHandler.add(new Fruit("dropped_fruit", Input.x,
			// // Game.height / 2 + 100, Input.y));
			// GUIHandler.add(new Fruit("dropped_fruit", Input.x, Input.y,
			// Input.y - 100));
			//
			// }

			limitTouch = true;
		} else if (!Gdx.input.isTouched() && limitTouch) {
			limitTouch = false;
		}

		sb.begin();
		{
			LevelHandler.render(sb);

			Input.update();

			GUIHandler.render(sb);

		}
		sb.end();

		if (DEBUG_ENTITY) {
			sr.begin(ShapeType.Line);
			{
				sr.setColor(Color.RED);
				for (Entity e : LevelHandler.getSelected().entityHandler.entities) {
					sr.rect(e.position.x - (e.size.x * e.hitboxScale) / 2, e.position.y - (e.size.y * e.hitboxScale) / 2, e.size.x * e.hitboxScale, e.size.y * e.hitboxScale);
				}
			}
			sr.end();
		}
	}

}
