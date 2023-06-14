package org.cubeville.effects.managers;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@SerializableAs("CommandWithLivingEntityEffect")
public class CommandWithLivingEntityEffect extends EffectWithLivingEntity
{
    private String command;
    
    public CommandWithLivingEntityEffect(String name, String command) {
        setName(name);
        this.command = command;
    }

    public CommandWithLivingEntityEffect(Map<String, Object> config) {
        command = (String) config.get("command");
        setName((String) config.get("name"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = getSerializationBase();
        ret.put("command", command);
        return ret;
    }

    public void play(Player player, LivingEntity entity) {
        String c = command;        
        if(c.contains("%player%") && (entity instanceof Player)) {
            Player p = (Player) entity;
            c = c.replace("%player%", p.getName());
        }
        System.out.println("Running command: " + c);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c);
    }

    public List<String> getInfo(boolean detailed) {
        List<String> ret = getInfoBase();
        ret.add("Command: " + command);
        return ret;
    }

    public String getType() {
        return "Command";
    }
}
