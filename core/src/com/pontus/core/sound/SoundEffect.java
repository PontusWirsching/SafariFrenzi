package com.pontus.core.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public enum SoundEffect {

	COIN_IN_CHEST("sound/coin_in_chest.mp3"),
	COIN_PICKUP("sound/Coin_pickup.mp3"),
	DROP_FRUIT("sound/Drop_fruit.mp3"),
	GAIN_STAR("sound/Gain_star.mp3"),
	GEM_PICKUP("sound/Gem_pickup.mp3"),
	GEM_PICKUP_2("sound/Gem_pickup2.mp3"),
	LION_ATTACK_ELEPHANT("sound/lion_attack_elephant.mp3"),
	LOSE_ELEPHANT("sound/Lose_elephant.mp3"),
	LOSE_GAME_1("sound/Lose_game1.mp3"),
	LOSE_GAME_2("sound/Lose_game2.mp3"),
	LOSE_GAME_3("sound/Lose_game3.mp3"),
	LOSE_STAR("sound/Lose_star.mp3"),
	MISC_1("sound/MISC_1.mp3"),
	MISC_2("sound/Misc_2.mp3"),
	PAUSE_UNPAUSE_MENU("sound/pause_unpause_menu_select.mp3"),
	PLAYER_HIT_LION("sound/player_hit_lion.mp3"),
	PLAYER_HIT_LION_2("sound/Player_hit_lion2.mp3");
	

	
	String path = "default";
	

	Sound sound;
	
	float volume = 1.0f;
	
	SoundEffect(String path) {
		this.path = path;
		sound = Gdx.audio.newSound(Gdx.files.internal(path));
	}
	
	public void play() {
		sound.play(volume);
	}
	
	public void stop() {
		sound.stop();
	}
	
	public void loop() {
		sound.loop(volume);
	}
	
}
