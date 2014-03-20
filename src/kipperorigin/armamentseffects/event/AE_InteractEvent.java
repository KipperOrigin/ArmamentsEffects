package kipperorigin.armamentseffects.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AE_InteractEvent extends AE_Event {

	private final Location location;
	private final PlayerInteractEvent event;

	public AE_InteractEvent(Player player, Location location, PlayerInteractEvent event) {
		super(player);
		
        if ((player.getItemInHand().getType() == Material.BOW) || (player.getItemInHand().getType() == Material.SNOW_BALL) || (player.getItemInHand().getType() == Material.POTION))
            event.setCancelled(true);
		this.location = location;
		this.event = event;
	}

	public Location getClickedLocation() {
		return location;
	}

	public PlayerInteractEvent getRawEvent() {
		return event;
	}
	
	public Action getAction() {
		return event.getAction();
	}

}
