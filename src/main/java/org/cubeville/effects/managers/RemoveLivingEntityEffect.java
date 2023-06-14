package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@SerializableAs("RemoveLivingEntityEffect")
public class RemoveLivingEntityEffect extends EffectWithLivingEntity
{
    public RemoveLivingEntityEffect(String name) {
        setName(name);
    }

    public RemoveLivingEntityEffect(Map<String, Object> config) {
        setName((String) config.get("name"));
    }
    
    public Map<String, Object> serialize() {
        return getSerializationBase();
    }

    public void play(Player player, LivingEntity entity) {
        if(!(entity instanceof Player))
            entity.remove();
    }

    public List<String> getInfo(boolean detailed) {
        return getInfoBase();
    }

    public String getType() {
        return "RemoveLivingEntity";
    }
}
