package org.cubeville.effects.managers;

import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;

public abstract class EffectWithLivingEntity extends Effect
{
    public abstract void play(Player player, LivingEntity entity);
}
