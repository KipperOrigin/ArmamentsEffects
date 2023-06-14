package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class MountEffect extends EffectWithLivingEntity
{
    public MountEffect(String name) {
        setName(name);
    }

    public MountEffect(Map<String, Object> config) {
        setName((String) config.get("name"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        return ret;
    }

    public void play(Player player, LivingEntity entity) {
        entity.addPassenger(player);
    }

    public List<String> getInfo(boolean detailed) {
        List<String> ret = getInfoBase();
        return ret;
    }

    public String getType() {
        return "Mount";
    }
}
