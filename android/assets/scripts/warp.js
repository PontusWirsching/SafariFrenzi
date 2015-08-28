/*
 *	========== Warp Script ==========
 *	The warp script is a quick
 *	example of the kinds of scripts
 *	that can be added to any entities  
 * 	=================================
 * 
 * 	By Pontus Wirsching
 */


var runtime = 10.0;

var ox, oy;

var t = false;

function update(delta, pos, vel, size) {
	runtime += delta;
	if (!t) {
		t = true;
		ox = pos.x;
		oy = pos.y;
	}
	size.x = Math.sin(runtime * ((runtime + 2) / 4)) * 200;
	pos.x = (-Math.sin(runtime * ((runtime + 2) / 4)) * 100) + ox;
	size.y = Math.cos(runtime * ((runtime + 2) / 4)) * 200;
	pos.y = (-Math.cos(runtime * ((runtime + 2) / 4)) * 100) + oy;
}
