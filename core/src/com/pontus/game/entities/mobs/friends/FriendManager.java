package com.pontus.game.entities.mobs.friends;

/**
 * This class will make sure that the selected friends get loaded in to the
 * game.
 * 
 * @author Pontus Wirsching
 * @since 2015-08-29
 */
public class FriendManager {

	/* This will be the types of the selected friends. */
	private static FriendType[] selected = new FriendType[3];

	/**
	 * Clears the array of selected friends.
	 */
	public static void clear() {
		for (int i = 0; i < selected.length; i++) {
			selected[i] = null;
		}
	}

	public static FriendType[] getData() {
		return selected;
	}

	/**
	 * Adds a new friend to the list.
	 */
	public static void add(FriendType type) {
		for (int i = 0; i < selected.length; i++) {
			System.out.println(i);
			if (selected[i] == null) {
				selected[i] = type;
				return;
			}

		}
	}

	public static int size() {
		return selected.length;
	}

	public static void remove(int index) {
		if (index < size()) selected[index] = null;
	}

	public static FriendType get(int index) {
		if (index < size()) return selected[index];
		else return null;
	}
}
