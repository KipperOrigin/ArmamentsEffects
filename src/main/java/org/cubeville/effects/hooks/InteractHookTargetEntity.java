package org.cubeville.effects.hooks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.EffectWithLivingEntity;
import org.cubeville.effects.util.PlayerUtil;

public class InteractHookTargetEntity implements InteractHook
{
    EffectWithLivingEntity effect;

    public InteractHookTargetEntity(Effect effect) {
        this.effect = (EffectWithLivingEntity) effect;
    }

    public InteractHookTargetEntity(Map<String, Object> config) {
        effect = (EffectWithLivingEntity) EffectManager.getInstance().getEffectByName((String) config.get("effect"));
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effect", effect.getName());
        return ret;
    }

    public String getInfo() {
        return "TargetEntity: " + effect.getName();
    }

    public void process(PlayerInteractEvent event) {
	Player player = event.getPlayer();
        LivingEntity target = PlayerUtil.findTargetEntity(player, player.getNearbyEntities(10, 10, 10), 1000);

        if(target == null) return;

        effect.play(player, target);
    }

    public boolean usesEffect(Effect effect) {
        return(this.effect == effect);
    }

    public boolean alwaysActive() {
        return false;
    }
    
}
