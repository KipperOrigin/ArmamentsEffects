package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.configuration.serialization.SerializableAs;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.EffectWithLivingEntity;

@SerializableAs("InteractHookPlayer")
public class InteractHookPlayer implements InteractHook
{
    EffectWithLivingEntity effect;

    public InteractHookPlayer(Effect effect) {
        this.effect = (EffectWithLivingEntity) effect;
    }

    public InteractHookPlayer(Map<String, Object> config) {
	effect = (EffectWithLivingEntity) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
	ret.put("effect", effect.getName());
        return ret;
    }

    public String getInfo() {
        return "Player: " + effect.getName();
    }

    public void process(PlayerInteractEvent event) {
        effect.play(event.getPlayer(), event.getPlayer());
    }

    public boolean usesEffect(Effect effect) {
        return effect == this.effect;
    }

    public boolean alwaysActive() {
        return false;
    }

}
