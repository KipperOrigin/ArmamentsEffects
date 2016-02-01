package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;
import kipperorigin.armamentseffects.resources.AE_CheckFireworkColor;
import kipperorigin.armamentseffects.resources.AE_FireworkEffectPlayer;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class AE_EffectFireworkParticle extends AE_EffectParent implements Listener {

    private AE_Main plugin;

    public AE_EffectFireworkParticle(AE_Main plugin) {

	this.plugin = plugin;
    }

    AE_RemoveItem AE_RI = new AE_RemoveItem();
    AE_FireworkEffectPlayer fplayer = new AE_FireworkEffectPlayer();
    AE_CheckFireworkColor colorCheck = new AE_CheckFireworkColor();
	
    private FireworkEffect getEffect(Type type, Color finalColor) {
	return FireworkEffect.builder().with(type).withColor(finalColor).build();
    }
	
    @Override
    public void run(final AE_ProjectileEvent event) {
	try {
	    final Projectile projectile = event.getProjectile();
	    String[] args = event.getArgs();
	    int timer = 1;
	    
	    if (args.length > 3 || args[0].isEmpty())
	    	return;
	    
	    final Type type = Type.valueOf(args[0].toUpperCase());
	    final Color color = colorCheck.getFireworkColorByString(args[1]);
	    
	    if (args.length == 3) {
	    	try {
	    		timer = Integer.parseInt(args[2]);
	    	} catch (NumberFormatException e) {
	    		return;
	    	}
	    }
	    final int taskId = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
		    @Override
		    public void run() {
			try {
			    fplayer.playFirework(event.getPlayer().getWorld(), projectile.getLocation(), getEffect(type, color));
			} catch (Exception e) {
			    return;
			}
		    }
		}, 0L, timer).getTaskId();
	    MetadataValue x = new FixedMetadataValue(plugin, taskId);
		
	    int i = 0;
		
		while(projectile.hasMetadata("Data " + String.valueOf(i))) 
			i++;
		
		projectile.setMetadata("Data " + String.valueOf(i), x);
	}
	catch(RuntimeException e) {
	    return;
	}
    }

    @Override
    public void run(AE_ProjectileHitEvent event) {
	final Projectile projectile = event.getProjectile();
	String[] args = event.getArgs();

	Bukkit.getScheduler().cancelTasks(plugin);
	
	if (args.length != 2 || args[0].isEmpty())
	    return;

	final Type type;
	try {
	    type = Type.valueOf(args[0].toUpperCase());
	} catch  (IllegalArgumentException e) {
	    return;
	}

	final Color color;
	try {
	    color = colorCheck.getFireworkColorByString(args[1]);
	} catch (NullPointerException e) {
	    return;
	}

	//try {
	    // fplayer.playFirework(event.getPlayer().getWorld(), projectile.getLocation(), getEffect(type, color));
	//} catch (Exception e) {
	//    return;
	//}
	AE_RI.removeItem(event.getPlayer());
	return;
    }

    @Override
    public void run(AE_DamageEvent event) {
	final LivingEntity victim = event.getVictim();
	String[] args = event.getArgs();

	if (args.length != 2 || args[0].isEmpty())
	    return;

	final Type type;
	try {
	    type = Type.valueOf(args[0].toUpperCase());
	} catch  (IllegalArgumentException e) {
	    return;
	}
		
	final Color color;
	try {
	    color = colorCheck.getFireworkColorByString(args[1]);
	} catch (NullPointerException e) {
	    return;
	}
		
	try {
	    fplayer.playFirework(event.getPlayer().getWorld(), victim.getLocation(), getEffect(type, color));
	} catch (Exception e) {
	    return;
	}
	AE_RI.removeItem(event.getPlayer());
	return;
    }
}
