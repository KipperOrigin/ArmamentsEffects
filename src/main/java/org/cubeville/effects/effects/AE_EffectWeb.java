package org.cubeville.effects.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.cubeville.effects.Effects;
import org.cubeville.effects.event.DamageEvent;
import org.cubeville.effects.resources.AE_Color;

public class AE_EffectWeb extends AE_EffectParent {

    private Effects plugin;

    public AE_EffectWeb(Effects plugin) {
        this.plugin = plugin;
    }

    AE_Color color = new AE_Color();

    @Override
    public void run(DamageEvent event) {
        LivingEntity target = event.getVictim();
        String[] args = event.getArgs();

        int timer;
        if (args.length == 0 || args[0].isEmpty())
            timer = 5;
        else {
            try {
                timer = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                return;
            }
        }

        Location loc = target.getLocation().add(0, 1, 0);
        Location loc2 = target.getLocation();
        final Block x;
        final Block y;
        final Material matx = loc.getBlock().getType();
        final Material maty = loc2.getBlock().getType();
        boolean messageSent = false;

        if (matx != null) {
            x = loc.getBlock();
            if (matx.equals(Material.WEB))
                return;
            if (matx.equals(Material.SIGN))
                return;
            if (matx.equals(Material.TORCH))
                return;
            if (matx.equals(Material.CHEST))
                return;
            if (matx.equals(Material.WOOD_DOOR))
                return;
            if (matx.equals(Material.LADDER))
                return;
            x.setType(Material.WEB);
            if (target instanceof Player) {
                ((Player) target).sendMessage(color.color("&4You've been caught in a web!"));
                messageSent = true;
            }
        } else
            x = null;

        if (maty != null) {
            y = loc2.getBlock();
            if (maty.equals(Material.WEB))
                return;
            if (maty.equals(Material.SIGN))
                return;
            if (maty.equals(Material.TORCH))
                return;
            if (maty.equals(Material.CHEST))
                return;
            if (maty.equals(Material.WOOD_DOOR))
                return;
            if (maty.equals(Material.LADDER))
                return;
            y.setType(Material.WEB);
            if (target instanceof Player && !messageSent)
                ((Player) target).sendMessage(color.color("&4You've been caught in a web!"));
        } else
            y = null;

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if (x != null)
                    x.setType(matx);
                if (y != null)
                    y.setType(maty);
            }
        }, timer * 20L);
        return;
    }

}
