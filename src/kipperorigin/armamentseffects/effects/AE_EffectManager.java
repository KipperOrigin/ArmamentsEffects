package kipperorigin.armamentseffects.effects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kipperorigin.armamentseffects.event.AE_DamageEvent;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class AE_EffectManager implements Listener {
	
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

		int y = 1;

		LivingEntity injured = (LivingEntity) event.getEntity();
		Entity assailant = event.getDamager();

		if ((assailant instanceof Projectile)) {
			assailant = ((Projectile) assailant).getShooter();
			y = 0;
		}

		if (!(assailant instanceof Player))
			return;

		Player damager = (Player) assailant;

		ItemStack item = damager.getItemInHand();

		if (!item.hasItemMeta())
			return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore())
			return;
		List<String> lore = meta.getLore();

		String exempt = "false";

		for (String line : lore) {
			line = stripColors(line);
			if ((line.contains("play")) || (line.contains("playsound"))) {
				exempt = "true";
				break;
			}
		}

		if (item.getType().equals(Material.BOW) && (y == 1))
			return;

		if (event.isCancelled() && !damager.hasPermission("ae.admin") && !exempt.equalsIgnoreCase("false"))
			return;

		if ((assailant instanceof Projectile)) {
			int x = damager.getInventory().first(Material.ARROW);
			item = damager.getInventory().getItem(x);
		}

		if (item == null || item.getType() == Material.AIR)
			return;

		runEvent(new AE_DamageEvent(damager, injured, event));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void process(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Location loc = player.getLocation();

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			runEvent(new AE_InteractEvent(player, event.getClickedBlock().getLocation(), event));
		} else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
			runEvent(new AE_InteractEvent(player, loc, event));
		} else {
			runEvent(new AE_InteractEvent(player, loc, event));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void process(ProjectileLaunchEvent event) {
		Projectile projectile = event.getEntity();

		LivingEntity entity = projectile.getShooter();

		if (!(entity instanceof Player))
			return;

		Player attacker = (Player) entity;

		if (event.isCancelled())
			return;

		runEvent(new AE_ProjectileEvent(attacker, projectile, event));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void process(ProjectileHitEvent event) {
		
		Projectile projectile = event.getEntity();
		LivingEntity entity = projectile.getShooter();

		if (!(entity instanceof Player))
			return;

		Player attacker = (Player) entity;

		Location location = event.getEntity().getLocation();

		int i = 0;
		
		while(projectile.hasMetadata("Data " + String.valueOf(i))) {
			Bukkit.getScheduler().cancelTask(projectile.getMetadata("Data " + String.valueOf(i)).get(0).asInt());
			i++;
		}

		projectile.eject();

		runEvent(new AE_ProjectileHitEvent(attacker, projectile, event, location));
	}

	private void runEvent(AE_DamageEvent data) {
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
			effect.run(data);
		}
	}

	private void runEvent(AE_InteractEvent data) {
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
			effect.run(data);
		}
	}

	private void runEvent(AE_ProjectileEvent data) {
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
			effect.run(data);
		}
	}

	private void runEvent(AE_ProjectileHitEvent data) {
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
			effect.run(data);
		}
	}
}
