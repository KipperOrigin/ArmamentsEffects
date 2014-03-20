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

		if (args.length == 0 || args[0].isEmpty())
			return;
		else if (args.length != 2) {
			return;
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
		}, 0L, 1L).getTaskId();
		MetadataValue x = new FixedMetadataValue(plugin, taskId);
		projectile.setMetadata("Data", x);
	}

	@Override
	public void run(AE_ProjectileHitEvent event) {
		final Projectile projectile = event.getProjectile();
		String[] args = event.getArgs();
		final Location loc = event.getProjectile().getLocation();

		if (args.length == 0 || args[0].isEmpty())
			return;
		else if (args.length != 2) {
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

		try {
			Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			return;
		}

		final int data = Integer.parseInt(args[1]);

		if (projectile.hasMetadata("Data")) {
			Bukkit.getScheduler().cancelTasks(plugin);
		}

		if (data == 0)
			return;
		projectile.getWorld().playEffect(loc, effect, data, 100);
		return;
	}

	@Override
	public void run(AE_DamageEvent event) {
		final LivingEntity victim = event.getVictim();
		String[] args = event.getArgs();
		final Location loc = victim.getLocation();

		if (args.length == 0 || args[0].isEmpty())
			return;
		else if (args.length != 2) {
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

		try {
			Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			return;
		}

		final int data = Integer.parseInt(args[1]);

		if (data == 0)
			return;
		victim.getWorld().playEffect(loc, effect, data, 100);
		return;
	}
}