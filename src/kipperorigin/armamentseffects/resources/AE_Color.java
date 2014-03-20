package kipperorigin.armamentseffects.resources;

import org.bukkit.ChatColor;

public class AE_Color {

	public String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public String stripColors(String line) {
		return line.replaceAll("(\u00A7|&)[0-9A-Fa-fK-Ok-oRr]", "");
	}
	
}
