package com.pontus.game.entities;

import java.util.ArrayList;

public class SpawnerManager {

	public static ArrayList<Spawner> spawners = new ArrayList<Spawner>();

	public static void add(Spawner s) {
		spawners.add(s);
	}

	public static Spawner get(int index) {
		return spawners.get(index);
	}

	public static void update() {
		for (int i = 0; i < spawners.size(); i++) {
			Spawner s = spawners.get(i);
			if (s == null)
				continue;
			s.update();
		}
	}

}
