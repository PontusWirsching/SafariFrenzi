package com.pontus.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pontus.core.graphics.gui.Button;
import com.pontus.core.graphics.gui.ButtonInterface;
import com.pontus.core.graphics.gui.CustomRenderer;
import com.pontus.core.graphics.gui.GUIElement;
import com.pontus.core.graphics.gui.GUIHandler;
import com.pontus.core.graphics.gui.Sprite;
import com.pontus.core.graphics.screen.GameScreen;
import com.pontus.core.graphics.screen.ScreenManager;
import com.pontus.core.resources.Resources;
import com.pontus.game.entities.mobs.friends.FriendManager;
import com.pontus.game.entities.mobs.friends.FriendType;
import com.pontus.game.level.LevelHandler;

public class PrepareToPlay extends GameScreen {

	public static GUIHandler gui;

	public static final float PADDING_BOTTOM = 75.0f;
	public static final float PADDING_TOP = 75.0f;
	public static final float PADDING_LEFT = 100.0f;
	public static final float PADDING_RIGHT = 100.0f;

	public TextureRegion background;

	public PrepareToPlay(String name) {
		super(name);

		background = Resources.get("background:gameplay_sat");

		gui = new GUIHandler();

		int circley = -100;
		gui.add(new Button("friendsCircle0", 0, circley, 128, 128, new ButtonInterface() {

			@Override
			public void onHover(Button b) {

			}

			@Override
			public void onClicked(Button b) {
				increase(0);
			}

			@Override
			public void notHover(Button b) {

			}
		}).setValue(0).setTexture(Resources.get("gui:button:green_circle")).setCustomRenderer(new CustomRenderer() {

			@Override
			public void customDraw(SpriteBatch sb, Sprite sprite) {

			}
		}));
		gui.add(new Button("friendsCircle1", 0, circley, 128, 128, new ButtonInterface() {

			@Override
			public void onHover(Button b) {

			}

			@Override
			public void onClicked(Button b) {
				increase(1);
			}

			@Override
			public void notHover(Button b) {

			}
		}).setValue(0).setTexture(Resources.get("gui:button:green_circle")).setCustomRenderer(new CustomRenderer() {

			@Override
			public void customDraw(SpriteBatch sb, Sprite sprite) {

			}
		}));
		gui.add(new Button("friendsCircle2", 0, circley, 128, 128, new ButtonInterface() {

			@Override
			public void onHover(Button b) {

			}

			@Override
			public void onClicked(Button b) {
				increase(2);
			}

			@Override
			public void notHover(Button b) {

			}
		}).setValue(0).setTexture(Resources.get("gui:button:green_circle")).setCustomRenderer(new CustomRenderer() {

			@Override
			public void customDraw(SpriteBatch sb, Sprite sprite) {

			}
		}));

		int stary = 100;
		gui.add(new GUIElement("star0", 0, stary, 128, 128).setTexture(Resources.get("gui:star:filled")));
		gui.add(new GUIElement("star1", 0, stary, 128, 128).setTexture(Resources.get("gui:star:filled")));
		gui.add(new GUIElement("star2", 0, stary, 128, 128).setTexture(Resources.get("gui:star:filled")));

		gui.add(new GUIElement("heart", 0, camera.viewportHeight / 2 - PADDING_TOP, 64, 64).setTexture(Resources.get("gui:button:heart")));
		gui.add(new GUIElement("star", 0, camera.viewportHeight / 2 - PADDING_TOP - gui.get("heart").originalHeight - 15, 96, 96).setTexture(Resources.get("gui:star:filled")));

		gui.add(new Button("back", 0, -camera.viewportHeight / 2 + PADDING_BOTTOM, 100, 310 / 5, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				b.setTexture(Resources.get("gui:button:back:pressed"));
			}

			@Override
			public void onClicked(Button b) {

			}

			@Override
			public void notHover(Button b) {
				b.setTexture(Resources.get("gui:button:back:notpressed"));
			}
		}).setEnableHoverEvent(true).setHitboxScale(1).setPriceBoxVisible(false));

		gui.add(new Button("play", 0, -camera.viewportHeight / 2 + PADDING_BOTTOM, 100, 100, new ButtonInterface() {

			@Override
			public void onHover(Button b) {
				b.setTexture(Resources.get("gui:button:play:pressed"));
			}

			@Override
			public void onClicked(Button b) {
				FriendManager.add(FriendType.values()[selections[0] - 1]);
				FriendManager.add(FriendType.values()[selections[1] - 1]);
				FriendManager.add(FriendType.values()[selections[2] - 1]);
				ScreenManager.setSelected("GAME");
				LevelHandler.setSelected("Test Level");
			}

			@Override
			public void notHover(Button b) {
				b.setTexture(Resources.get("gui:button:play:notpressed"));
			}
		}).setEnableHoverEvent(true).setHitboxScale(1).setPriceBoxVisible(false));

	}

	private void increase(int index) {

		if (selections[index] < FriendType.values().length) {
			selections[index]++;
		} else {
			selections[index] = 1;
		}
		for (int i = 0; i < selections.length; i++) {
			if (i == index) continue;
			if (selections[index] == selections[i]) {
				increase(index);
			}
		}
	}

	public int[] selections = new int[]{1, 2, 3};

	@Override
	public void render(float delta) {
		super.render(delta);

		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		float s = 1.8f;
		sr.rect(-background.getRegionWidth() / s / 2, -background.getRegionHeight() / s / 2, background.getRegionWidth() / s, background.getRegionHeight() / s);
		sr.end();
		
		sb.begin();
		{

			for (int i = 0; i < 3; i++) {
				GUIElement g = gui.get("star" + i);
				g.x = (i - 1) * (g.originalWidth + 20);
			}

			gui.get("back").x = -camera.viewportWidth / 2 + PADDING_LEFT;
			gui.get("play").x = camera.viewportWidth / 2 - PADDING_RIGHT;
			gui.get("heart").x = -camera.viewportWidth / 2 + PADDING_LEFT;
			gui.get("star").x = -camera.viewportWidth / 2 + PADDING_LEFT;

			sb.setColor(new Color(1, 1, 1, 0.5f));
//			sb.draw(background, -background.getRegionWidth() / s / 2, -background.getRegionHeight() / s / 2, background.getRegionWidth() / s, background.getRegionHeight() / s);
			sb.setColor(Color.WHITE);
			gui.render(sb);

			for (int i = 0; i < 3; i++) {
				GUIElement g = gui.get("friendsCircle" + i);
				g.x = (i - 1) * (g.originalWidth + 20);
				SafariFrenzi.font.setColor(Color.WHITE);
				SafariFrenzi.font.setScale(5);
				SafariFrenzi.font.draw(sb, "" + selections[i], (i - 1) * (g.originalWidth + 20) - 20, -65);
			}

		}
		sb.end();

	}

}
