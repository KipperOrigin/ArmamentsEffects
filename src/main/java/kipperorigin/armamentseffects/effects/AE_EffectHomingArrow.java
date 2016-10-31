package kipperorigin.armamentseffects.effects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.resources.AE_EntityGetter;

public class AE_EffectHomingArrow extends AE_EffectParent implements Listener {
	
    private AE_Main plugin;

    public AE_EffectHomingArrow(AE_Main plugin) {

        this.plugin = plugin;
    }
    
    private AE_EntityGetter eg = new AE_EntityGetter();
	
    @Override
    public void run(final AE_ProjectileEvent event) {
        final Projectile projectile = event.getProjectile();
        Player player = event.getPlayer();
        final Player cPlayer = eg.getClosestPlayerInSight(player);
        String[] args = event.getArgs();
        int timer = 1;
        
        if (args.length > 1)
        	return;
        else
        	try {
        		timer = (Integer.valueOf(args[0]));
        	} catch (NumberFormatException e) {
        		player.sendMessage("ARGUMENT MUST BE AN INTEGER");
        		return;
        	}
        
        final int taskId = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
            	Vector from = new Vector(projectile.getLocation().getX(), projectile.getLocation().getY(), projectile.getLocation().getZ());
            	Vector to  = new Vector(cPlayer.getLocation().getX(), cPlayer.getLocation().getY(), cPlayer.getLocation().getZ());
            	projectile.setVelocity(to.subtract(from));
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
        }, 0L, timer).getTaskId();
        MetadataValue x = new FixedMetadataValue(plugin, taskId);
        projectile.setMetadata("Data", x);
		int i = 0;
		
		while(projectile.hasMetadata("Data " + String.valueOf(i))) 
			i++;
		
		projectile.setMetadata("Data " + String.valueOf(i), x);
        if (event.getRawEvent().isCancelled() && projectile.hasMetadata("Data"))
            Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data").get(0).asInt());
    }

}
