
var runtime = 0.0;

function update(delta, pos, vel, size) {
	runtime += delta;
	
	pos.x = Math.sin(runtime * 4) * 200;
	
	
}
