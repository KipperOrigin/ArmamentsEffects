package kipperorigin.armamentseffects.effects;

import kipperorigin.armamentseffects.AE_Main;
import kipperorigin.armamentseffects.event.AE_InteractEvent;
import kipperorigin.armamentseffects.event.AE_PlayerInteractEntityEvent;
import kipperorigin.armamentseffects.resources.AE_RemoveItem;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AE_EffectPotionRightClick extends AE_EffectParent {

	private AE_Main plugin;

	public AE_EffectPotionRightClick(AE_Main plugin) {
		this.plugin = plugin;
	}
	
	AE_RemoveItem AE_RI = new AE_RemoveItem(plugin);

	@Override
	public void run(AE_InteractEvent event) {
		Player player = event.getPlayer();
		String[] args = event.getArgs();
		String potion;
		int amp = 1;
		int time = 5;
		if (args.length == 0)
			return;
		if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			potion = args[0];
			if (args.length > 1) {
				try {
					amp = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					return;
				}
			}
			if (args.length > 2) {
				try {
					time = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					return;
				}
			}

			PotionEffectType type = PotionEffectType.getByName(potion);
			if (type == null)
				return;
			if (type.isInstant())
				time = 1;
			player.addPotionEffect(new PotionEffect(type, time * 20, amp - 1));
			AE_RI.removeItem(event.getPlayer());
			return;
		}
	}

	@Override
	public void run(AE_PlayerInteractEntityEvent event) {
		String args[] = event.getArgs();
		String potion;
		int amp = 1;
		int time = 5;
		Player player = (Player) event.getEntity();
		if (args.length == 0)
			return;

		potion = args[0];
		if (args.length > 1) {
			try {
				amp = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return;
			}
		}
		if (args.length > 2) {
			try {
				time = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return;
			}
		}

		PotionEffectType type = PotionEffectType.getByName(potion);
		if (type == null)
			return;
		if (type.isInstant())
			time = 1;

		player.addPotionEffect(new PotionEffect(type, time * 20, amp - 1));
		AE_RI.removeItem(event.getPlayer());
		return;
	}
}
