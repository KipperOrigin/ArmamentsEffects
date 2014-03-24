package kipperorigin.armamentseffects.resources;

import org.bukkit.Location;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

public class AE_LaunchProjectile {

	public void fireTnT(Vector v, Location l, int i) {
		TNTPrimed tnt = l.getWorld().spawn(l, TNTPrimed.class);
		tnt.setVelocity(v);
		if (i > 0) tnt.setFuseTicks(i*20);
	}
	
	public void fireArrow(Vector v, Location l) {
		l.getWorld().spawnArrow(l, v, (float) .6, 12);
	}

}
