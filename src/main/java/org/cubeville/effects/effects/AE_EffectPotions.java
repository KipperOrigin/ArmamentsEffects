package org.cubeville.effects.effects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.cubeville.effects.event.DamageEvent;
import org.cubeville.effects.event.AE_ProjectileHitEvent;

@SuppressWarnings("deprecation")
public class AE_EffectPotions extends AE_EffectParent {

    @Override
    public void run(DamageEvent event) {
        LivingEntity target = event.getVictim();
        String[] args = event.getArgs();
        String potion;
        int amp = 1;
        int time = 5;

        if (args.length == 0)
            return;

        potion = args[0];

        try {
            if (args.length > 1) {
                amp = Integer.parseInt(args[1]);
            }
            if (args.length > 2) {
                time = Integer.parseInt(args[2]);
            }
        } catch (NumberFormatException e) {
            return;
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
        try {
            if (args.length > 1) {
                amp = Integer.parseInt(args[1]);
            }
            if (args.length > 2) {
                time = Integer.parseInt(args[2]);
            }
        } catch (NumberFormatException e) {
            return;
        }

        if (args.length > 3) {
            if (args[3].equalsIgnoreCase("splash"))
                splash = "true";
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
