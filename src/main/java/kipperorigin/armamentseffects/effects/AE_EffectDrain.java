package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.resources.AE_Color;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AE_EffectDrain extends AE_EffectParent {

    private AE_Main plugin;

    public AE_EffectDrain(AE_Main plugin) {
        this.plugin = plugin;
    }
    
    private AE_Color color = new AE_Color();

    @Override
    public void run(AE_DamageEvent event) {
        final Player player = event.getPlayer();
        // final LivingEntity target = event.getVictim();
        String[] args = event.getArgs();
        double health = player.getHealth();
        double originalhealth = player.getHealth();
        double maxhealth = player.getMaxHealth();
        double perc = 0;
        
        try {
        	perc = Double.parseDouble(args[0]) * .01;
        } catch (NumberFormatException e) {
        	player.sendMessage("Arg 1 must be a number!");
        	return;
        }
        health = health + (event.getRawEvent().getFinalDamage() * perc);
        
        if (health > maxhealth)
        	player.setHealth(maxhealth);
        else
        	player.setHealth(health);
        
        if (args.length == 2)
        	if (args[1].equalsIgnoreCase("stats")) {
        		player.sendMessage(color.color("&AOriginal Health&f = " + originalhealth));
        		player.sendMessage(color.color("&AEnding Health&f = " + health));
        		player.sendMessage(color.color("&AHealth Gained = " + (health - originalhealth)));
        	}
        		
        
        /* final double heal = target.getHealth();
        if (args.length == 0 || args[0].isEmpty()) {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    double healx = target.getHealth();
                    double healy = heal - healx;
                    if (player.getHealth() + healy > 20)
                        player.setHealth(20d);
                    else
                        player.setHealth(player.getHealth() + healy);
                }
            }, 1L);
        } else if (args.length == 1) {
            try {
                Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                return;
            }
            int amp = Integer.parseInt(args[0]);
            if (player.getHealth() + amp > 20)
                player.setHealth(20d);
            else
                player.setHealth(player.getHealth() + amp);
        } */
    }
}
