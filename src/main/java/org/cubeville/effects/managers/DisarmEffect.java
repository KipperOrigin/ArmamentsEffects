package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DisarmEffect extends EffectWithLivingEntity {

    boolean offhand = false;
    
    public DisarmEffect(String name, boolean offhand) {
        setName(name);
        this.offhand = offhand;
    }
    
    public DisarmEffect(Map<String, Object> config) {
        offhand = (boolean) config.get("offhand");
        setName((String) config.get("name"));
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("offhand", offhand);
        return ret;
    }

    @Override
    public List<String> getInfo(boolean detailed) {
        List<String> ret = getInfoBase();
        if (offhand) ret.add("Disarm: offhand");
        else ret.add("Disarm: mainhand");
        return null;
    }

    @Override
    public String getType() {
        return "Disarm";
    }

    @Override
    public void play(Player p, LivingEntity entity) {
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;
        if (offhand) {
            player.getInventory().setItem(player.getInventory().firstEmpty(), player.getInventory().getItemInOffHand());
            player.getInventory().setItemInOffHand(null);
        } else {
            player.getInventory().setItem(player.getInventory().firstEmpty(), player.getInventory().getItemInMainHand());
            player.getInventory().setItemInMainHand(null);
        }
    }

}
