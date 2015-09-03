package com.pontus.game.entities;

/**
 * This defines what kind of entity the entity is. <br>
 * 
 * @author Pontus Wirsching
 * @since 2015-09-01
 */
public enum EntityTypes {

	/**
	 * The HOSTILE mob will try to hunt other mobs if and only if they are of
	 * the type TARGET.
	 */
	HOSTILE,
	
	/**
	 * If the entity is of the type TARGET it can be attacked by HOSTILEs.
	 */
	TARGET,

	/**
	 * A PEACEFUL entity will be ignored by HOSTILES.
	 */
	PEACEFUL

}
