package org.cubeville.effects.util;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PlayerUtil
{
    public static LivingEntity findTargetEntity(Player player, List<Entity> entityList, double minAngle) {
        LivingEntity ret = null;
        for(Entity e: entityList) {
            if(!e.isDead()) {
                double dist = e.getLocation().distance(player.getLocation());
                if(dist < 10) {
                    if(e instanceof LivingEntity) {
                        if(e.getType() == EntityType.PLAYER || e.getType() == EntityType.VILLAGER || e.getType() == EntityType.PIG || e.getType() == EntityType.COW || e.getType() == EntityType.SHEEP || e.getType() == EntityType.CHICKEN || e.getType() == EntityType.HORSE || e.getType() == EntityType.MUSHROOM_COW || e.getType() == EntityType.WOLF || e.getType() == EntityType.OCELOT || e.getType() == EntityType.DONKEY || e.getType() == EntityType.SNOWMAN) {
                            Vector targetDirection = e.getLocation().subtract(player.getLocation()).toVector();
                            Vector playerDirection = player.getLocation().getDirection();
                            double angle = playerDirection.angle(targetDirection);
                            if(angle < 0.25 && angle < minAngle) {
                                ret = (LivingEntity) e;
                                minAngle = angle;
                            }
                        }
		    }
		}
	    }
        }
        return ret;
    }
}
