package org.cubeville.effects.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

@SerializableAs("SitEffect")
public class SitEffect extends EffectWithLivingEntity
{
    public SitEffect(String name) {
        setName(name);
    }

    public Map<String, Object> serialize() {
        return new HashMap<>();
    }

    public void play(Player player, LivingEntity entity) {
        if(entity.isInsideVehicle()) {
            Entity carrier = entity.getVehicle();
            //if(carrier instanceof Arrow) {
            //entity.leaveVehicle();
                carrier.remove();
                //}
        }
        else {
            Location loc = entity.getLocation();
            //loc.setY(loc.getY() - 0.5);
            //Arrow arrow = entity.getLocation().getWorld().spawnArrow(loc, new Vector(0, -1, 0), 0, 0);
            //arrow.setPassenger(entity);
            // entity.teleport(loc);
            Entity e = loc.getWorld().spawn(loc, ArmorStand.class);
            e.setPassenger(entity);
        }
    }

    public List<String> getInfo(boolean detailed) {
        return getInfoBase();
    }

    public String getType() {
        return "Sit";
    }
}
