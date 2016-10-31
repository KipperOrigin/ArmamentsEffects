package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class AE_EffectLightning extends AE_EffectParent implements Listener {

    int running;

    private AE_Main plugin;

    public AE_EffectLightning(AE_Main plugin) {

        this.plugin = plugin;
    }

    @Override
    public void run(final AE_ProjectileEvent event) {
        final Projectile projectile = event.getProjectile();
        String[] args = event.getArgs();
        int timer = 1;
        
        if (args.length == 0)
            return;
        
        if (args.length > 2  || !args[0].equalsIgnoreCase("effect"))
            return;
        
        if (args.length == 2) {
            try {
                Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                return;
            }
        }
        
        final int taskId = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                event.getProjectile().getLocation().getWorld().strikeLightningEffect(event.getProjectile().getLocation());
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

    /* @Override
    public void run(AE_ProjectileHitEvent event) {
        String[] args = event.getArgs();
        if (args.length == 0)
            event.getLocation().getWorld().strikeLightning(event.getLocation());
        else if (args.length <= 2 && args[0].equalsIgnoreCase("effect"))
            event.getLocation().getWorld().strikeLightningEffect(event.getLocation());
        else return;


    } */

    @Override
    public void run(AE_DamageEvent event) {
    	if (event.getRawEvent().getDamager() instanceof Projectile)
    		return;
        String[] args = event.getArgs();
        if (args.length == 0)
            event.getVictim().getLocation().getWorld().strikeLightning(event.getVictim().getLocation());
        else if (args.length <= 2 && args[0].equalsIgnoreCase("effect"))
            event.getVictim().getLocation().getWorld().strikeLightningEffect(event.getVictim().getLocation());
        else return;
    }
}
