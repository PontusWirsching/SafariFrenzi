

/**
 *	Elephant script.
 *
 *	By Pontus Wirsching.
 */

var timeBetweenActions = 15;



var timer = 0;

function update(delta, pos, vel, size) {
	
	print(vel.x);

	pos.x += vel.x;
	pos.y += vel.y;

	timer++;
	if (timer >= timeBetweenActions) {
		timer = 0;
		
		var action = Math.round(Math.random() * 10);
		
		print(action);

		switch(action) {

			case 0:
				vel.x = -5;
				break;
			case 1:
			
				break;
			case 2:
			
				break;
			case 3:
			
				break;
			case 4:
			
				break;
			default:

				break;
		}
		
	}
	
}