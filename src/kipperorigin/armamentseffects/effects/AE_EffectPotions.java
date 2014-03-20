package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AE_EffectPotions extends AE_EffectParent {

	@Override
	public void run(AE_DamageEvent event) {
		LivingEntity target = event.getVictim();
		String[] args = event.getArgs();
		String potion;
		int amp = 1;
		int time = 5;
		if (args.length == 0)
			return;

		potion = args[0];
		if (args.length > 1) {
			try {
				amp = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return;
			}
		}
		if (args.length > 2) {
			try {
				time = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return;
			}
		}

		PotionEffectType type = PotionEffectType.getByName(potion);
		if (type == null)
			return;
		if (type.isInstant())
			time = 1;

		target.addPotionEffect(new PotionEffect(type, time * 20, amp - 1));
		return;
	}

	@Override
	public void run(AE_ProjectileHitEvent event) {
		Location location = event.getLocation();
		String[] args = event.getArgs();
		String potion;
		int amp = 1;
		int time = 5;
		String splash = "false";
		if (args.length == 0)
			return;

		potion = args[0];
		if (args.length > 1) {
			try {
				amp = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return;
			}
		}
		if (args.length > 2) {
			try {
				time = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return;
			}
		}
		if (args.length > 3) {
			if (args[3].equalsIgnoreCase("splash"))
				splash = "true";
			else
				return;
		}

		PotionEffectType type = PotionEffectType.getByName(potion);
		if (type == null)
			return;
		if (type.isInstant())
			time = 1;
		ThrownPotion pot = (ThrownPotion) event.getLocation().getWorld().spawnEntity(location, EntityType.SPLASH_POTION);
		pot.getEffects().add(new PotionEffect(type, time * 20, amp - 1));
		if ((pot == null) || (splash.equalsIgnoreCase("false")))
			return;
		ItemStack item = new ItemStack(Material.POTION);
		Potion potionx = Potion.fromItemStack(item);
		potionx.getEffects().add((new PotionEffect(type, time * 20, amp - 1)));
		event.getProjectile().setPassenger(pot);
		pot.leaveVehicle();
		pot.setVelocity(event.getProjectile().getVelocity());
		return;
	}
}