package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

@SerializableAs("PotionEffect")
public class PotionEffect extends EffectWithLivingEntity
{
    PotionEffectType effectType;
    int duration;
    int amplifier;
    
    public PotionEffect(String name, PotionEffectType effectType, int duration, int amplifier) {
        setName(name);
        this.effectType = effectType;
        this.duration = duration;
        this.amplifier = amplifier;
        printinfo();
    }

    public void modify(PotionEffectType effectType, int duration, int amplifier) {
        this.effectType = effectType;
        this.duration = duration;
        this.amplifier = amplifier;
    }
    
    private void printinfo()
    {
        System.out.println("EFFECT TYPE: " + effectType);
    }
    public PotionEffect(Map<String, Object> config) {
        this.effectType = PotionEffectType.getByName((String) config.get("effecttype"));
        this.duration = (int) config.get("duration");
        this.amplifier = (int) config.get("amplifier");
        setName((String) config.get("name"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("effecttype", effectType.getName());
        ret.put("duration", duration);
        ret.put("amplifier", amplifier);
        return ret;
    }

    public List<String> getInfo(boolean detailed) {
        List<String> ret = getInfoBase();
        ret.add("PotionEffect: " + effectType.getName());
        return ret;
    }

    public void play(Player player, LivingEntity entity) {
        entity.addPotionEffect(new org.bukkit.potion.PotionEffect(effectType, duration, amplifier));
    }

    public String getType() {
        return "PotionEffect";
    }

}
