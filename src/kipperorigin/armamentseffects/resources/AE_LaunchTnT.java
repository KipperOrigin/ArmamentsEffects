package kipperorigin.armamentseffects.resources;

import org.bukkit.Location;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

public class AE_LaunchTnT {

	public void fireTnT(Vector v, Location l, int i) {
		TNTPrimed tnt = l.getWorld().spawn(l, TNTPrimed.class);
		tnt.setVelocity(v);
		if (i > 0) tnt.setFuseTicks(i*20);
	}

}
