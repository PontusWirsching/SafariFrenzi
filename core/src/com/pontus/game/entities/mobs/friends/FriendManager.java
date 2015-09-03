package com.pontus.game.entities.mobs.friends;

/**
 * This class will make sure that the selected friends get loaded in to the
 * game.
 * 
 * @author Pontus Wirsching
 * @since 2015-08-29
 */
public class FriendManager {

	public FriendManager() {
		
		FriendType f = FriendType.valueOf("RANGER_RON");
		
		int i = f.i;
		
		System.out.println(f + ", " + i);
		
	}

}
