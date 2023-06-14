package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import org.cubeville.effects.Effects;

public class BrokenWandEffect extends EffectWithLivingEntity
{
    static Random rnd = new Random();
    int webProbability;
    int webDuration;
    double horizontalVelocity;
    double verticalVelocity;
    
    public BrokenWandEffect(String name, int webProbability, int webDuration, double horizontalVelocity, double verticalVelocity) {
        setName(name);
        this.webProbability = webProbability;
        this.webDuration = webDuration;
        this.horizontalVelocity = horizontalVelocity;
        this.verticalVelocity = verticalVelocity;
    }

    public BrokenWandEffect(Map<String, Object> config) {
        setName((String) config.get("name"));
        webProbability = (Integer) config.get("webProbability");
        webDuration = (Integer) config.get("webDuration");
        horizontalVelocity = (Double) config.get("horizontalVelocity");
        verticalVelocity = (Double) config.get("verticalVelocity");
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("webProbability", webProbability);
        ret.put("webDuration", webDuration);
        ret.put("horizontalVelocity", horizontalVelocity);
        ret.put("verticalVelocity", verticalVelocity);
        return ret;
    }

    public void play(Player player, LivingEntity entity) {
        if(!(entity instanceof Player)) return;
        if(rnd.nextInt(100) < webProbability) { // webbed
            final int x = entity.getLocation().getBlockX();
            final int y = entity.getLocation().getBlockY();
            final int z = entity.getLocation().getBlockZ();
            World world = entity.getWorld();
            final String worldName = world.getName();
            final boolean lbs;
            final boolean hbs;
            {
                Block lblock = world.getBlockAt(x, y, z);
                Block hblock = world.getBlockAt(x, y + 1, z);
                lbs = lblock.isEmpty();
                hbs = hblock.isEmpty();
                //if(lbs) lblock.setType(Material.WEB);
                //if(hbs) hblock.setType(Material.WEB);
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(Effects.getInstance(), new Runnable() {
                    public void run() {
                        World world = Bukkit.getWorld(worldName);
                        if(world != null) {
                            if(lbs) world.getBlockAt(x, y, z).setType(Material.AIR);
                            if(hbs) world.getBlockAt(x, y + 1, z).setType(Material.AIR);
                        }
                    }
                }, webDuration);
        }
        else { // knockbacked
            Location loc = entity.getLocation();
            Vector direction = loc.getDirection();
            Vector newDirection = new Vector(-direction.getX() * horizontalVelocity,
                                             verticalVelocity,
                                             -direction.getZ() * horizontalVelocity);
            System.out.println("Set velocity on player: " + newDirection);
            ((Player) entity).setVelocity(newDirection);
        }
    }

    public List<String> getInfo(boolean detailed) {
        List<String> ret = getInfoBase();
        return ret;
    }

    public String getType() {
        return "BrokenWand";
    }
}
