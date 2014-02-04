package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;

public class AE_EffectSpawn extends AE_EffectParent {

	private AE_Main plugin;

	public AE_EffectSpawn(AE_Main plugin) {
		this.plugin = plugin;
	}
	
	AE_RemoveItem AE_RI = new AE_RemoveItem(plugin);

	@Override
	public void run(AE_InteractEvent event) {
		Player player = event.getPlayer();
		Location location = event.getClickedLocation().add(0, 1, 0);
		String[] args = event.getArgs();

		if (args.length == 0)
			return;

		String mob = args[0].toUpperCase();
		EntityType type = EntityType.valueOf(mob);
		World world = location.getWorld();

		if (type == null || !type.isSpawnable())
			return;

		if (Tameable.class.isAssignableFrom(type.getEntityClass())) {
			Tameable entity = (Tameable) world.spawnEntity(location, type);
			entity.setOwner(player);
		} else {
			world.spawnEntity(location, type);
		}

		AE_RI.removeItem(player);
		return;
	}
}
