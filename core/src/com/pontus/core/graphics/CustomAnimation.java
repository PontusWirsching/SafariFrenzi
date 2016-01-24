package com.pontus.core.graphics;

import java.awt.Dimension;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class CustomAnimation {

	private Animation anim;

	private float stateTime = 0.0f;

	private TextureRegion currentFrame = null;

	private String name = "undefined";

	private float fps = 0.0f;

	private int frameWidth, frameHeight;

	public CustomAnimation(String name, String path, float fps, int frameWidth, int frameHeight) {
		this.name = name;
		this.fps = fps;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;

		Texture image = new Texture(path);

		int framesWide = image.getWidth() / frameWidth;
		int framesHigh = image.getHeight() / frameHeight;

		Array<TextureRegion> frames = new Array<TextureRegion>();

		for (int y = 0; y < framesHigh; y++) {
			for (int x = 0; x < framesWide; x++) {
				frames.add(new TextureRegion(image, x * frameWidth, y * frameHeight, frameWidth, frameHeight));
			}
		}

		anim = new Animation(1 / fps, frames);
		anim.setPlayMode(PlayMode.LOOP);
		currentFrame = anim.getKeyFrame(stateTime);
	}

	public void update() {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = anim.getKeyFrame(stateTime);
	}

	public String getName() {
		return name;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public float getFPS() {
		return fps;
	}

	public void setFPS(float fps) {
		this.fps = fps;
	}

	public Dimension getSize() {
		return new Dimension(frameWidth, frameHeight);
	}

}
