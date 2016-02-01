package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class AE_EffectParticle extends AE_EffectParent implements Listener {

	int running;

	private AE_Main plugin;

	public AE_EffectParticle(AE_Main plugin) {

		this.plugin = plugin;
	}

	@Override
	public void run(final AE_ProjectileEvent event) {

		final Player player = event.getPlayer();
		final Projectile projectile = event.getProjectile();
		String[] args = event.getArgs();
		int timer = 1;
		
		if (args.length < 2 || args.length > 4)
			return;

		if (args.length == 3) {
			try {
				Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return;
			}
			timer = Integer.parseInt(args[2]);	
		}
		
		final String particle = args[0].toUpperCase();

		try {
			Effect.valueOf(particle);
		} catch (IllegalArgumentException e) {
			player.sendMessage("Invalid Particle!");
			return;
		} catch (NullPointerException e) {
			player.sendMessage("Invalid Particle!");
			return;
		}

		final Effect effect = Effect.valueOf(particle);
		
		if (effect == Effect.ITEM_BREAK || effect == Effect.TILE_BREAK || effect == Effect.TILE_DUST) {
			return;
		} else {
			try {
				Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return;
			}

			final int data = Integer.parseInt(args[1]);
			
			if (data == 0)
				return;
			
			final int taskId = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
				@Override
				public void run() {
					final Location loc = event.getProjectile().getLocation();
					projectile.getWorld().playEffect(loc, effect, data, 100);
				}
			}, 0L, timer).getTaskId();
			MetadataValue x = new FixedMetadataValue(plugin, taskId);
			
			
	    	if (args.length >= 4 && args[3].equalsIgnoreCase("permanent")) {
	    		event.getPlayer().sendMessage("taskId = " + String.valueOf(taskId));
	    		return;
	    	}
	    	
	    	
			int i = 0;
			
			while(projectile.hasMetadata("Data " + String.valueOf(i))) 
				i++;
			
			projectile.setMetadata("Data " + String.valueOf(i), x);
		}


	}

	@Override
	public void run(AE_ProjectileHitEvent event) {
		final Projectile projectile = event.getProjectile();
		String[] args = event.getArgs();
		final Location loc = event.getProjectile().getLocation();

		if (args.length < 2 || args.length > 4)
			return;

		final String particle = args[0].toUpperCase();

		try {
			Effect.valueOf(particle);
		} catch (IllegalArgumentException e) {
			return;
		} catch (NullPointerException e) {
			return;
		}	

		final Effect effect = Effect.valueOf(particle);
		
		

		
		
		if (effect == Effect.ITEM_BREAK || effect == Effect.TILE_BREAK || effect == Effect.TILE_DUST) {
			return;
			
		} else {
			try {
				Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return;
			}

			int data = Integer.parseInt(args[1]);
			
			if (data == 0)
				return;

			projectile.getWorld().playEffect(loc, effect, data, 100);
		}

	}

	@Override
	public void run(AE_DamageEvent event) {
		final LivingEntity victim = event.getVictim();
		String[] args = event.getArgs();
		final Location loc = victim.getLocation();

		if (args.length < 2 || args.length > 4)
			return;
		
		else if (args.length != 2 && args.length != 3) {
			return;
		}

		final String particle = args[0].toUpperCase();

		try {
			Effect.valueOf(particle);
		} catch (IllegalArgumentException e) {
			return;
		} catch (NullPointerException e) {
			return;
		}

		final Effect effect = Effect.valueOf(particle);

		if (effect == Effect.ITEM_BREAK || effect == Effect.TILE_BREAK || effect == Effect.TILE_DUST) {
			return;	
		} else {
			try {
				Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return;
			}

			int data = Integer.parseInt(args[1]);
			
			if (data == 0)
				return;
			
			victim.getWorld().playEffect(loc, effect, data, 100);
		}
	}
}