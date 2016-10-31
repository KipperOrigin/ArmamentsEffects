package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class AE_EffectParticle extends AE_EffectParent implements Listener {

    int running;

    private AE_Main plugin;

    public AE_EffectParticle(AE_Main plugin) {

        this.plugin = plugin;
    }

    @Override
    public void run(final AE_ProjectileEvent event) {

        try {
            
            final Player player = event.getPlayer();
            final Projectile projectile = event.getProjectile();
            String[] args = event.getArgs();
            int timer = 1;
            long delay = 0;
        
            if (args.length == 0 || args[0].isEmpty())
                return;
            else if (args.length > 4 || args.length < 2) {
                return;
            }

            if (args.length >= 2) {
            	try {
            		timer = Integer.parseInt(args[2]);  
            	} catch (NumberFormatException e) {
            		player.sendMessage("ARGUMENT 3 MUST BE AN INTEGER");
            		return;
            	}
            }
            /* if (args.length >= 3) {
            	try {
            		delay = Long.parseLong(args[2]);
            	} catch (NumberFormatException e) {
            		player.sendMessage("ARGUMENT 4 MUST BE AN INTEGER");
            	}
            } */
            
            final String particle = args[0].toUpperCase();

            try {
                Effect.valueOf(particle);
            } catch (IllegalArgumentException | NullPointerException e) {
                player.sendMessage("Invalid Particle!");
                return;
            }
            
            final Effect effect = Effect.valueOf(particle);
        
            if (effect == Effect.ITEM_BREAK || effect == Effect.TILE_BREAK || effect == Effect.TILE_DUST) {
                return;

            } else {

                final int data = Integer.parseInt(args[1]);
            
                if (data == 0)
                    return;
            
                final int taskId = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
                        @Override
                        public void run() {
                            final Location loc = event.getProjectile().getLocation();
                            projectile.getWorld().playEffect(loc, effect, data, 100);
                            if (projectile.getLocation().getY() <= 0) {
                            	int i = 0;
                            	while(projectile.hasMetadata("Data " + String.valueOf(i))) {
                            		Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data " + String.valueOf(i)).get(0).asInt());
                            		i++;
                            	}
                            	projectile.eject();
                            	projectile.remove();
                    		}
                        }
                    }, delay, timer).getTaskId();
                MetadataValue x = new FixedMetadataValue(plugin, taskId);

                if (args.length == 4 && args[3].equalsIgnoreCase("permanent")) {
    	    		event.getPlayer().sendMessage("taskId = " + String.valueOf(taskId));
                } else {
        			int i = 0;
        			
        			while(projectile.hasMetadata("Data " + String.valueOf(i))) 
        				i++;
        			
        			projectile.setMetadata("Data " + String.valueOf(i), x);
                    if (event.getRawEvent().isCancelled() && projectile.hasMetadata("Data"))
                        Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data").get(0).asInt());
                }
            }

        } catch (NumberFormatException e) {
        }
        
    }

    @Override
    public void run(AE_ProjectileHitEvent event) {
        final Projectile projectile = event.getProjectile();
        String[] args = event.getArgs();
        final Location loc = event.getProjectile().getLocation();
        int data = 0;
        
        if (args.length == 0 || args[0].isEmpty())
            return;
        else if (args.length > 4 || args.length < 2) {
            return;
        }
        try {
        	data = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
        	return;
        }

        final String particle = args[0].toUpperCase();

        try {
            Effect.valueOf(particle);
        } catch (IllegalArgumentException | NullPointerException e) {
            return;
        }

        final Effect effect = Effect.valueOf(particle);
        
        if (effect == Effect.ITEM_BREAK || effect == Effect.TILE_BREAK || effect == Effect.TILE_DUST) {
            return;
            
        } else {
            if (data == 0)
                return;

            projectile.getWorld().playEffect(loc, effect, data, 100);
        }

    }

    @Override
    public void run(AE_DamageEvent event) {

        try {
        
            final LivingEntity victim = event.getVictim();
            String[] args = event.getArgs();
            final Location loc = victim.getLocation();
            
            if (args.length == 0 || args[0].isEmpty())
                return;
            else if (args.length != 2 && args.length != 3) {
                return;
            }
            
            final String particle = args[0].toUpperCase();
            
            Effect.valueOf(particle);
            
            final Effect effect = Effect.valueOf(particle);
            
            if (effect == Effect.ITEM_BREAK || effect == Effect.TILE_BREAK || effect == Effect.TILE_DUST)
            	return; 
            int data = Integer.parseInt(args[1]);
                
            if (data == 0)
            	return;
                
            victim.getWorld().playEffect(loc, effect, data, 100);
        } catch (NullPointerException | IllegalArgumentException e) {
        }
    }
}
