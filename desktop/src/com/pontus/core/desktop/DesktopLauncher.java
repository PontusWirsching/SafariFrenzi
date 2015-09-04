package com.pontus.core.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pontus.core.SafariFrenzi;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920;
		config.height = 1080;
		config.vSyncEnabled = true;
		config.fullscreen = true;
		new LwjglApplication(new SafariFrenzi(), config);
	}
}
