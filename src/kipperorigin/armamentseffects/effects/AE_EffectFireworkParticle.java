package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;
import kipperorigin.armamentseffects.resources.AE_CheckFireworkColor;
import kipperorigin.armamentseffects.resources.AE_FireworkEffectPlayer;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class AE_EffectFireworkParticle extends AE_EffectParent implements Listener {

	private AE_Main plugin;

	public AE_EffectFireworkParticle(AE_Main plugin) {
		this.plugin = plugin;
	}

	AE_RemoveItem AE_RI = new AE_RemoveItem(plugin);
	AE_FireworkEffectPlayer fplayer = new AE_FireworkEffectPlayer();
	AE_CheckFireworkColor colorCheck = new AE_CheckFireworkColor();
	
	/* return FireworkEffect.builder().withType(Type.BALL).withColor(Color.RED).build();
	fplayer.playFirework(event.getPlayer().getWorld(), event.getPlayer.getLocation(), Util.getRandomFireworkEffect());
	*/
	
	private FireworkEffect getEffect(Type type, Color finalColor) {
		return FireworkEffect.builder().with(type).withColor(finalColor).build();
	}
	
	@Override
	public void run(final AE_ProjectileEvent event) {
		final Projectile projectile = event.getProjectile();
		String[] args = event.getArgs();

		if (args.length == 0 || args[0].isEmpty())
			return;
		else if (args.length != 2) {
			return;
		}

		try {
			Type.valueOf(args[0].toUpperCase());
		} catch  (IllegalArgumentException e) {
			return;
		}
		
		try {
			colorCheck.getFireworkColorByString(args[1]);
		} catch (NullPointerException e) {
			return;
		}

		final Type type = Type.valueOf(args[0].toUpperCase());
		final Color color = colorCheck.getFireworkColorByString(args[1]);
		
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
				try {
					fplayer.playFirework(event.getPlayer().getWorld(), projectile.getLocation(), getEffect(type, color));
				} catch (Exception e) {
					return;
				}
			}
		}, 0L, 1L).getTaskId();
		MetadataValue x = new FixedMetadataValue(plugin, taskId);
		projectile.setMetadata("Data", x);
	}

	@Override
	public void run(AE_ProjectileHitEvent event) {
		final Projectile projectile = event.getProjectile();
		String[] args = event.getArgs();

		if (args.length == 0 || args[0].isEmpty())
			return;
		else if (args.length != 2) {
			return;
		}

		try {
			Type.valueOf(args[0].toUpperCase());
		} catch  (IllegalArgumentException e) {
			return;
		}
		
		try {
			colorCheck.getFireworkColorByString(args[1]);
		} catch (NullPointerException e) {
			return;
		}

		final Type type = Type.valueOf(args[0].toUpperCase());
		final Color color = colorCheck.getFireworkColorByString(args[1]);
		
		try {
			Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			return;
		}

		final int data = Integer.parseInt(args[1]);
		if (data == 0)
			return;
		
		try {
			fplayer.playFirework(event.getPlayer().getWorld(), projectile.getLocation(), getEffect(type, color));
		} catch (Exception e) {
			return;
		}
		AE_RI.removeItem(event.getPlayer());
		return;
	}

	@Override
	public void run(AE_DamageEvent event) {
		final LivingEntity victim = event.getVictim();
		String[] args = event.getArgs();

		if (args.length == 0 || args[0].isEmpty())
			return;
		else if (args.length != 2) {
			return;
		}

		try {
			Type.valueOf(args[0].toUpperCase());
		} catch  (IllegalArgumentException e) {
			return;
		}
		
		try {
			colorCheck.getFireworkColorByString(args[1]);
		} catch (NullPointerException e) {
			return;
		}

		final Type type = Type.valueOf(args[0].toUpperCase());
		final Color color = colorCheck.getFireworkColorByString(args[1]);
		
		try {
			Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			return;
		}

		final int data = Integer.parseInt(args[1]);
		if (data == 0)
			return;
		
		try {
			fplayer.playFirework(event.getPlayer().getWorld(), victim.getLocation(), getEffect(type, color));
		} catch (Exception e) {
			return;
		}
		AE_RI.removeItem(event.getPlayer());
		return;
	}
}