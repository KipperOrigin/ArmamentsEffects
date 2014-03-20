package kipperorigin.armamentseffects.effects;

import java.util.List;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_DamageEvent;
import kipperorigin.armamentseffects.event.AE_DamageNoEntityEvent;
import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.event.AE_PlayerInteractEntityEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileEvent;
import kipperorigin.armamentseffects.event.AE_ProjectileHitEvent;
import kipperorigin.armamentseffects.resources.AE_Color;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AE_EffectDurability extends AE_EffectParent {

	private AE_Main plugin;

	public AE_EffectDurability(AE_Main plugin) {
		this.plugin = plugin;
	}
	
	AE_Color color = new AE_Color();
	
	@SuppressWarnings("deprecation")
	
	public void removeItem(Player player) {
		final ItemStack item = player.getItemInHand();
		if (!item.hasItemMeta())
			return;
		ItemMeta meta = item.getItemMeta();
		if (!meta.hasLore())
			return;
		List<String> lore = meta.getLore();
		for (int i = 0; i < lore.size(); i++) {
			String linex = lore.get(i);
			String line = color.stripColors(linex);
			String[] parts = line.split(" +", 2);
			String[] partsx = linex.split(" +", 2);
			String name = parts[0];
			if (name.equalsIgnoreCase("Durability") || name.equalsIgnoreCase("Uses")) {
				if (parts.length != 2) {
					return;
				} else {
					int x;
					try {
						x = Integer.parseInt(parts[1]);
					} catch (NumberFormatException e) {
						return;
					}
					x = Integer.parseInt(parts[1]);
					x = x - 1;
					if (x <= 0) {
						player.setItemInHand(null);
					} else {
						String num = String.valueOf(x);
						lore.set(i, color.color("&r" + partsx[0] + " " + num));
						meta.setLore(lore);
						item.setItemMeta(meta);
						final short dur = (short) (item.getDurability() - item.getType().getMaxDurability());
						Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
							@Override
							public void run() {
								item.setDurability(dur);
							}
						}, 1L);
					}
				}
			}
		}
		player.updateInventory();
	}
	
	int counter;
	
	@Override
	public void run(AE_ProjectileEvent event) {
		if (event.getRawEvent().isCancelled())
			return;
		if (counter > 1)
			return;
		counter += 1;
		this.removeItem(event.getPlayer());
	}
	
	@Override
	public void run(AE_PlayerInteractEntityEvent event) {
		if (event.getRawEvent().isCancelled())
			return;
		if (counter > 1)
			return;
		counter += 1;
		this.removeItem(event.getPlayer());
	}
	
	@Override
	public void run(AE_InteractEvent event) {
		if (counter > 1)
			return;
		counter += 1;
		this.removeItem(event.getPlayer());
	}
	
	@Override
	public void run(AE_DamageNoEntityEvent event) {
		if (counter > 1)
			return;
		counter += 1;
		this.removeItem(event.getPlayer());
	}
	
	@Override
	public void run(AE_DamageEvent event) {
		if (counter > 1)
			return;
		counter += 1;
		this.removeItem(event.getPlayer());
	}
	
	@Override
	public void run(AE_ProjectileHitEvent event) {
		if (counter > 1)
			return;
		counter += 1;
		this.removeItem(event.getPlayer());
	}

}
