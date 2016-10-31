package kipperorigin.armamentseffects.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_Event;
import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AE_EffectManager implements Listener {
	
	private AE_Main plugin;
	
    public AE_EffectManager(AE_Main plugin) {
    	this.plugin = plugin;
    }
    
    public String stripColors(String line) {
        return line.replaceAll("(\u00A7|&)[0-9A-Fa-fK-Ok-oRr]", "");
    }

    Map<String, AE_EffectParent> effects = new HashMap<String, AE_EffectParent>();

    public void registerEffect(String name, AE_EffectParent effect) {
        name = name.toLowerCase();
        effects.put(name, effect);
    }

    private AE_EffectParent getEffect(String name) {
        name = name.toLowerCase();
        return effects.get(name);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void process(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity))
            return;

        boolean sourceIsPlayer = true; // Source was a player?

        LivingEntity injured = (LivingEntity) event.getEntity();

        Player damager;
        {
            Entity assailant = event.getDamager();
            if ((assailant instanceof Projectile)) {
            	Projectile projectile = (Projectile) assailant;
                int i = 0;
        		while(projectile.hasMetadata("Data " + String.valueOf(i))) {
        			Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data " + String.valueOf(i)).get(0).asInt());
        			i++;
        		}  	
        		projectile.eject();
        		ProjectileSource source = ((Projectile)assailant).getShooter();
        		if(!(source instanceof Player)) {
        			return;
        		}
        		damager = (Player)source;
        		sourceIsPlayer = false;
            } else if(assailant instanceof Player) {
            	damager = (Player)assailant;
                ItemStack item = damager.getInventory().getItemInMainHand(); // TODO: Test: this might have changed since a projectile started it's journey? Opportunity to cheat for the player?

                if (!item.hasItemMeta())
                    return;
                ItemMeta meta = item.getItemMeta();
                if (!meta.hasLore())
                    return;
                List<String> lore = meta.getLore();

                boolean exempt = false;

                for (String line : lore) {
                    line = stripColors(line);
                    if ((line.contains("play")) || (line.contains("playsound"))) {
                        exempt = true;
                        break;
                    }
                }

                if (item.getType().equals(Material.BOW) && sourceIsPlayer)
                    return;
                if (event.isCancelled() && !damager.hasPermission("ae.admin") && exempt)
                    return;

                if (!sourceIsPlayer) {
                    int x = damager.getInventory().first(Material.ARROW);
                    if (x != -1)
                    	item = damager.getInventory().getItem(x);
                }
            } else {
            	return;
            }
        }
        
        runEvent(new AE_DamageEvent(damager, injured, event));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void process(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();

        if(event.getHand() != EquipmentSlot.HAND) return;
        
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            runEvent(new AE_InteractEvent(player, event.getClickedBlock().getLocation(), event));
        } else {
            runEvent(new AE_InteractEvent(player, loc, event));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void process(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        
		if (projectile.hasMetadata("Ignore"))
			event.setCancelled(true);
		
        ProjectileSource source = projectile.getShooter();

        if (!(source instanceof Player))
            return;

        Player attacker = (Player) source;

        if (event.isCancelled())
            return;

        runEvent(new AE_ProjectileEvent(attacker, projectile, event));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void process(ProjectileHitEvent event) {

        Projectile projectile = event.getEntity();

        ProjectileSource source = projectile.getShooter();

        if (!(source instanceof Player))
            return;

        Player attacker = (Player) source;

        Location location = event.getEntity().getLocation();

        int i = 0;
		while(projectile.hasMetadata("Data " + String.valueOf(i))) {
			Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data " + String.valueOf(i)).get(0).asInt());
			i++;
		}

        projectile.eject();

        runEvent(new AE_ProjectileHitEvent(attacker, projectile, event, location));
    }

    private void runEvent(AE_Event data) {
    	if(data instanceof AE_DamageEvent) {
    		if (((AE_DamageEvent)data).getRawEvent().getDamager() instanceof Projectile) {
    			Projectile projectile = (Projectile) ((AE_DamageEvent) data).getRawEvent().getDamager();
    			this.projectileEvent(data, projectile);
    		}
    	}

    	if(data instanceof AE_ProjectileHitEvent){
    		this.projectileEvent(data, ((AE_ProjectileHitEvent)data).getProjectile());
    		return;
    	}
    	
    	ItemStack item = data.getItem();
    		
    	if (!item.hasItemMeta())
    		return;
    	ItemMeta meta = item.getItemMeta();
    	if (!meta.hasLore())
    		return;
    	List<String> lore = meta.getLore();
    	
    	for (String line : lore) {
    		line = stripColors(line);
    		
    		String[] parts = line.split(" +", 2);
    		String name = parts[0];
    		
    		AE_EffectParent effect = getEffect(name);
    		if (effect == null)
    			continue;
    		
    		String[] args;
    		if (parts.length == 2)
    			args = parts[1].split(" +");
    		else
    			args = new String[0];
    		
    		data.setArgs(args);
    		if(data instanceof AE_DamageEvent) {
    			if(((AE_DamageEvent)data).getRawEvent().getDamager() instanceof Projectile)
    				return;
    			effect.run((AE_DamageEvent)data);
    		}
    		else if(data instanceof AE_ProjectileEvent) {
    			effect.run((AE_ProjectileEvent)data);
    			int i = 0;
    			MetadataValue x = new FixedMetadataValue(plugin, line);
    			
    			while(((AE_ProjectileEvent)data).getProjectile().hasMetadata("Effect " + String.valueOf(i))) 
    				i++;
    			
    			((AE_ProjectileEvent)data).getProjectile().setMetadata("Effect " + String.valueOf(i), x);
    		}
    		else if(data instanceof AE_InteractEvent) {
    			effect.run((AE_InteractEvent)data);
    		}
    	}
    }
    
    private void projectileEvent(AE_Event data, Projectile projectile) {
    	int i = 0;
		List<String> lore = new ArrayList<String>();
		while(projectile.hasMetadata("Effect " + String.valueOf(i))) {
			lore.add(projectile.getMetadata("Effect " + String.valueOf(i)).get(0).asString());
			i++;
		}
		if (lore.isEmpty()) return;
		
		for (String line : lore) {
			line = stripColors(line);
			
			String[]parts = line.split(" +", 2);
			String name = parts[0];
			
			AE_EffectParent effect = getEffect(name);
			
			if (effect == null) {
				continue;
			}
			
			String[] args;
			
			if (parts.length == 2) {
				args = parts[1].split(" +");
			} else
				args = new String[0];
			data.setArgs(args);
			
			if (data instanceof AE_ProjectileHitEvent) {
				effect.run((AE_ProjectileHitEvent)data);
			} else if (data instanceof AE_DamageEvent) {
				effect.run((AE_DamageEvent)data);
			}
		}
    }
    
}
