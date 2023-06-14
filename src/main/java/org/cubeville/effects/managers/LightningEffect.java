package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class LightningEffect extends EffectWithLivingEntity
{
    public LightningEffect(String name) {
        setName(name);
    }

    public LightningEffect(Map<String, Object> config) { 
        setName((String) config.get("name"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        return ret;
    }

    public void play(Player player, LivingEntity entity) {
        entity.getLocation().getWorld().strikeLightningEffect(entity.getLocation());
        if(entity instanceof Player) {
            Player target = (Player) entity;
            if(target.getHealth() > target.getMaxHealth() * 0.7) {
                target.damage(target.getMaxHealth() * 0.05);
            }
        }
    }

    public List<String> getInfo(boolean detailed) {
        List<String> ret = getInfoBase();
        return ret;
    }

    public String getType() {
        return "Lightning";
    }
}
