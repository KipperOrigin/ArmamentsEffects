package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;

import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.CommandResponse;

import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.MountEffect;

public class EffectCreateMountCommand extends Command
{
    public EffectCreateMountCommand() {
        super("effect create mount");
        addBaseParameter(new CommandParameterString());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) throws CommandExecutionException {

        String name = (String) baseParameters.get(0);
        if(EffectManager.getInstance().getEffectByName(name) != null) {
            throw new CommandExecutionException("Effect with name " + name + " already exists!");
        };

        MountEffect effect = new MountEffect(name);
        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();
        return null;
    }

}
