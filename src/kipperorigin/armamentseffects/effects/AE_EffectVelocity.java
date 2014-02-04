package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public class AE_EffectVelocity extends AE_EffectParent {

	private AE_Main plugin;

	public AE_EffectVelocity(AE_Main plugin) {
		this.plugin = plugin;
	}
	
	AE_RemoveItem AE_RI = new AE_RemoveItem(plugin);

	@Override
	public void run(AE_ProjectileEvent event) {
		Projectile projectile = event.getProjectile();
		String[] args = event.getArgs();

		int vec;
		if (args.length == 0)
			return;

		try {
			vec = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return;
		}

		Vector v = projectile.getVelocity();
		Vector z = v.multiply(new Vector(vec, (vec / 2), vec));
		projectile.setVelocity(z);
		AE_RI.removeItem(event.getPlayer());
		return;
	}
}
