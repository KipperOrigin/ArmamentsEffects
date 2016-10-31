package kipperorigin.armamentseffects.effects;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_Event;
import kipperorigin.armamentseffects.event.AE_PlayerInteractEntityEvent;

public class AE_EffectGravity extends AE_EffectParent {
	
	@Override
	public void run (AE_DamageEvent event) {
		Entity e = event.getVictim();
		if (e instanceof Player)
			return;
		//e.setGravity(this.gravity(event));
		
	}
	
	@Override
	public void run (AE_PlayerInteractEntityEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Player)
			return;
		//e.setGravity(this.gravity(event));
	}
	
	private boolean gravity(AE_Event event) {
		String[] args = event.getArgs();
		if (args[0].equalsIgnoreCase("on")) {
			return true;
		} else if (args[0].equalsIgnoreCase("off")) {
			return false;
		}
		return true;
	}

}
