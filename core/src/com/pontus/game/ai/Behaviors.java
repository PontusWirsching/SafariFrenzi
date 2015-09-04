package com.pontus.game.ai;

/**
 * This class contains different behaviors that can be used for <br>
 * different animals and entities.
 * 
 * 
 * @author Pontus Wirsching
 * @since 2015-07-03
 */
public enum Behaviors {

	/**
	 * The ROAM behavior makes the mob simply roam around on the screen. <br>
	 * The mob will not move outside the screen area and / or not move into
	 * other mobs. <br>
	 * The mob will also try to keep somewhere in the center. <br>
	 * The mob can switch to PANIC behavior if needed, this occurs if a <br>
	 * hostile mob is detected nearby. <br>
	 * 
	 * @author Pontus Wirsching
	 * @since 2015-07-03
	 * @see com.pontus.game.ai.Behaviors.PANIC
	 */
	ROAM,

	/**
	 * The HUNT_WEAKEST behavior will make the mob in question hunt the weakest
	 * mob <br>
	 * currently in game. <br>
	 * The mob will switch to HUNT_NEARSET if there is already more than 50% of
	 * mobs attacking <br>
	 * the weakest mob. <br>
	 * This is so that the mobs will spread out and hunt different mobs. <br>
	 * 
	 * @author Pontus Wirsching
	 * @since 2015-07-03
	 * @see com.pontus.game.ai.Behaviors.HUNT_NEAREST
	 */
	HUNT_WEAKEST,

	/**
	 * As the name suggests this behavior will make the mob hunt the nearest
	 * mob. <br>
	 * If more than 50% of the hostile mobs is attacking a mob the hostile mob
	 * that this behavior is attached <br>
	 * to will switch to the HUNT_WEAKEST behavior. <br>
	 * 
	 * @author Pontus Wirsching
	 * @since 2015-07-03
	 * @see com.pontus.game.ai.Behaviors.HUNT_WEAKEST
	 */
	HUNT_NEAREST,

	/**
	 * The PANIC behavior will make the mob run away from the nearest hostile
	 * mob. <br>
	 * Most of the rules from the ROAM behavior is still applied such as the mob
	 * will <br>
	 * not run outside the screen no matter what. Though the mob will ignore if
	 * it is in <br>
	 * the center as it will just try to outrun the hostile mob. <br>
	 * 
	 * @author Pontus Wirsching
	 * @since 2015-07-03
	 * @see com.pontus.game.ai.Behaviors.ROAM
	 */
	PANIC,

	/**
	 * The fetch behavior can be applied to different mobs, mostly elephants. <br>
	 * If this behavior is active the mob will go and fetch the nearest food drop. <br>
	 * The mob can only switch to FETCH if its current behavior is ROAM. <br>
	 * 
	 * @author Pontus Wirsching
	 * @since 2015-07-28
	 * @see com.pontus.game.ai.Behaviors.ROAM
	 */
	FETCH,
	
	/**
	 * The DEFEND behavior will make the AI attack hostile mobs, <br>
	 * this is used in the Rhino etc. <br>
	 */
	DEFEND

}
