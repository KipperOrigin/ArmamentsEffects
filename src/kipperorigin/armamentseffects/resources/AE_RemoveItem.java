package kipperorigin.armamentseffects.resources;

import java.util.List;

import kipperorigin.armamentseffects.AE_Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AE_RemoveItem {

	private AE_Main plugin;

	public AE_RemoveItem(AE_Main plugin) {
		this.plugin = plugin;
	}
	
	public String stripColors(String line) {
		return line.replaceAll("(\u00A7|&)[0-9A-Fa-fK-Ok-oRr]", "");
	}

	public String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

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
			String line = stripColors(linex);
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
						lore.set(i, color("&r" + partsx[0] + " " + num));
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
}
